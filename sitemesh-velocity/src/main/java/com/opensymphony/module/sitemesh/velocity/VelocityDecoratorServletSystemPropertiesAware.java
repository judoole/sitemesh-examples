package com.opensymphony.module.sitemesh.velocity;

import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public class VelocityDecoratorServletSystemPropertiesAware extends VelocityDecoratorServlet {
    private static final String PREFIX_REPLACEHOLDER = "${";
    private static final String SUFFIX_REPLACEHOLDER = "}";

    public void init(final ServletConfig config) throws ServletException {
        ServletConfig configSystemPropertiesAware = new ServletConfig() {

            public String getServletName() {
                return config.getServletName();
            }

            public ServletContext getServletContext() {
                return config.getServletContext();
            }

            //Velocity wants to use this somewhere long down in the stack
            public String getInitParameter(String name) {
                String value = config.getInitParameter(name);
                return getInitParameterReplacePlaceholder(value, System.getProperties(), System.getenv());
            }

            public Enumeration getInitParameterNames() {
                return config.getInitParameterNames();
            }
        };
        super.init(configSystemPropertiesAware);
    }

    String getInitParameterReplacePlaceholder(String value, Properties props, Map<String, String> environmentVariables) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }

        int indexOfPrefix = value.indexOf(PREFIX_REPLACEHOLDER);
        int indexOfSuffix = value.indexOf(SUFFIX_REPLACEHOLDER, indexOfPrefix);

        //Could not find prefix AND suffix. Return original value.
        if (indexOfPrefix == -1 || indexOfSuffix == -1) {
            return value;
        }

        String key = value.substring(indexOfPrefix + 2, indexOfSuffix);
        String replaceWithSystemProperty = props.containsKey(key) ? props.getProperty(key) : environmentVariables.get(key) ;

        //No property found. Return the original value.
        if (replaceWithSystemProperty == null) {
            return value;
        }

        //We found a Property with that name. Let's replace it and return the new value!
        return value.substring(0, indexOfPrefix) + replaceWithSystemProperty + value.substring(indexOfSuffix + 1, value.length());
    }

}
