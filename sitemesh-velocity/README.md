# Using Sitemesh with Velocity

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

## How can I test this?
Maven installed and do a mvn jetty:run. Go to [http://localhost:8080/sitemesh-velocity](http://localhost:8080/sitemesh-velocity)

Watch how the src/main/webapp/index.html has been decorated by
[https://raw.github.com/judoole/sitemesh-examples/master/sitemesh-velocity/example-of-external-velocity.vm](https://raw.github.com/judoole/sitemesh-examples/master/sitemesh-velocity/example-of-external-velocity.vm)