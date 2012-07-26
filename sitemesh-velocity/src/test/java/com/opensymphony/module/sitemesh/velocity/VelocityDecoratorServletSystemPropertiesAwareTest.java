package com.opensymphony.module.sitemesh.velocity;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class VelocityDecoratorServletSystemPropertiesAwareTest {
    @Test
    public void should_replace_placeholder_with_system_property_when_found(){
        given_we_have_system_property_and_value("SOME_WEIRD_PROPERTY", "system_property_value");
        when_I_try_to_find_parameter("prefix-${SOME_WEIRD_PROPERTY}-suffix");
        then_the_resolve_parameter_should_be("prefix-system_property_value-suffix");
    }

    @Test
    public void should_have_no_problems_with_missing_suffix(){
        given_we_have_system_property_and_value("SOME_WEIRD_PROPERTY", "system_property_value");
        when_I_try_to_find_parameter("prefix-${SOME_WEIRD_PROPERTY}");
        then_the_resolve_parameter_should_be("prefix-system_property_value");
    }

    @Test
    public void should_return_the_original_value_if_system_property_not_found(){
        when_I_try_to_find_parameter("prefix-${SOME_WEIRD_PROPERTY}-suffix");
        then_the_resolve_parameter_should_be("prefix-${SOME_WEIRD_PROPERTY}-suffix");
    }

    @Test
    public void should_return_original_value_if_no_replaceholder(){
        when_I_try_to_find_parameter("you_should_not_replace_me");
        then_the_resolve_parameter_should_be("you_should_not_replace_me");
    }

    @Test
    public void should_return_original_value_if_replaceholder_is_missing_suffix(){
        when_I_try_to_find_parameter("prefix-${SOME_WEIRD_PROPERTY");
        then_the_resolve_parameter_should_be("prefix-${SOME_WEIRD_PROPERTY");
    }

    @Test
    public void should_return_original_value_if_replaceholder_is_missing_prefix(){
        when_I_try_to_find_parameter("prefix-SOME_WEIRD_PROPERTY}-suffix");
        then_the_resolve_parameter_should_be("prefix-SOME_WEIRD_PROPERTY}-suffix");
    }

    @Test
    public void should_replace_placeholder_with_environment_variable_when_found(){
        given_we_have_environment_variable_and_value("SOME_WEIRD_PROPERTY", "environment_variable_value");
        when_I_try_to_find_parameter("prefix-${SOME_WEIRD_PROPERTY}-suffix");
        then_the_resolve_parameter_should_be("prefix-environment_variable_value-suffix");
    }
    
    @Test
    public void should_replace_placeholder_with_system_property_before_environment_variable(){
        given_we_have_system_property_and_value("SOME_WEIRD_PROPERTY", "system_property_value");
        given_we_have_environment_variable_and_value("SOME_WEIRD_PROPERTY", "environment_variable_value");
        when_I_try_to_find_parameter("prefix-${SOME_WEIRD_PROPERTY}-suffix");
        then_the_resolve_parameter_should_be("prefix-system_property_value-suffix");
    }
    
    

    private void given_we_have_environment_variable_and_value(String key, String value) {
        environmentVariables.put(key, value);
    }

    private void given_we_have_system_property_and_value(String property, String value) {
        systemProperties.setProperty(property, value);
    }

    private void when_I_try_to_find_parameter(String findParameter) {
        this.resolvedParameter = new VelocityDecoratorServletSystemPropertiesAware().getInitParameterReplacePlaceholder(findParameter, systemProperties, environmentVariables);
    }

    private void then_the_resolve_parameter_should_be(String value) {
        assertThat(resolvedParameter, equalTo(value));
    }

    String resolvedParameter;
    Properties systemProperties = new Properties();
    Map<String, String> environmentVariables = new HashMap<String, String>();
}
