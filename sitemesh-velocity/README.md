# Using Sitemesh 2 with Velocity

Ever wanted to have your template decorator outside of your webapp? For example have your template stored in a cms system so that it's easily maintainable for all webapps?

Well Velocity+Sitemesh can help with that. Simple example setup here where, which goes a little as follows:
![overview](https://raw.github.com/judoole/sitemesh-examples/master/sitemesh-velocity/sitemesh_and_velocity.gif)

The path to the actual Velocity template is configured in velocity.properties.
```properties
url.resource.loader.class = org.apache.velocity.runtime.resource.loader.URLResourceLoader
url.resource.loader.root = https://raw.github.com/judoole/sitemesh-examples/master/sitemesh-velocity
url.resource.loader.cache = true
url.resource.loader.modificationCheckInterval = 3600
```

### How do I change where Sitemesh looks for my template?
This is done i [WEB-INF/decorators.xml](https://raw.github.com/judoole/sitemesh-examples/master/sitemesh-velocity/src/main/webapp/WEB-INF/decorators.xml) and [web.xml](https://raw.github.com/judoole/sitemesh-examples/master/sitemesh-velocity/src/main/webapp/WEB-INF/web.xml)
Normally you would like to route everything to the VelocityServlet (<pattern>/*</pattern>) in decorators.xml. In web.xml you can choose to do as in the example and use *.vm as your url-pattern, or maybe change it to a spesific url e.g. MySiteMeshTemplate if it's in CMS or something.

The full url which Velocity will use will combine the decorators.xml page and [WEB-INF/velocity.properties](https://raw.github.com/judoole/sitemesh-examples/master/sitemesh-velocity/src/main/webapp/WEB-INF/velocity-DEVELOPMENT.properties) url.resource.loader.root

### What if I want to use a different root-url in development, test, QA?
That's a bit tricky. In this example I had to override the Sitemesh [VelocityDecoratorServlet](http://www.opensymphony.com/sitemesh/api/com/opensymphony/module/sitemesh/velocity/VelocityDecoratorServlet.html) to make it aware of SystemProperties and System environment variables so that I can do this i [web.xml](https://raw.github.com/judoole/sitemesh-examples/master/sitemesh-velocity/src/main/webapp/WEB-INF/web.xml):
````xml
<servlet>
    <servlet-name>velocity</servlet-name>
    <servlet-class>com.opensymphony.module.sitemesh.velocity.VelocityDecoratorServletSystemPropertiesAware</servlet-class>
    <load-on-startup>10</load-on-startup>
    <init-param>
        <param-name>org.apache.velocity.properties</param-name>
        <param-value>/WEB-INF/velocity-${ENVIRONMENT}.properties</param-value>
    </init-param>
</servlet>
````
The ${ENVIRONMENT} will now be substituded with a System property or System environment variable if it exists either on the application container or the server it self.
This is much like how Spring does it with ContextLoaderListener and the likes.

### How can I test this?
Maven installed and do a mvn jetty:run. Go to [http://localhost:8080/sitemesh-velocity](http://localhost:8080/sitemesh-velocity)

Watch how the src/main/webapp/index.html has been decorated by
[https://raw.github.com/judoole/sitemesh-examples/master/sitemesh-velocity/example-of-external-velocity.vm](https://raw.github.com/judoole/sitemesh-examples/master/sitemesh-velocity/example-of-external-velocity.vm)