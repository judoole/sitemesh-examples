package org.sitemesh.server;

import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ResourceHandler;
import org.mortbay.jetty.webapp.WebAppContext;

import static java.lang.String.format;

/*
Just go ahead and run me. Visit http://localhost:8080/sitemesh-velocity afterwards.
 */
public class JettyWebserver {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        System.out.println(format("Starting Jetty Webserver"));

        Server server = new Server(PORT);
        server.addHandler(webAppContext());
        server.addHandler(createResourceHandle());
        server.start();
        System.out.println(format("Started Jetty Webserver on port:%s", PORT));
    }

    private static Handler createResourceHandle() {
        ResourceHandler handle = new ResourceHandler();
        handle.setResourceBase(".");
        return handle;
    }

    private static WebAppContext webAppContext() {
        WebAppContext webAppContext = new WebAppContext("src/main/webapp", "/sitemesh-velocity");
        //context.getInitParams().put("org.mortbay.jetty.servlet.Default.aliases", "true");
        return webAppContext;
    }

}
