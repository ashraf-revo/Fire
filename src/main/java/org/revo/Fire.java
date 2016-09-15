package org.revo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.revo.Function.Fun;
import org.revo.Function.Fun1;
import org.revo.Function.Fun2;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ashraf on 15/09/16.
 */
public class Fire {
    protected static Map<String, Fun> getFun = new HashMap<>();
    protected static Map<String, RequestHolder> postFun = new HashMap<>();

    static <T> void get(String url, Fun1<Map<String, String>, T> action) {
        getFun.put(url, action);

    }

    static <T, U> void post(String url, Class<U> resType, Fun2<Map<String, String>, U, T> action) {
        postFun.put(url, new RequestHolder(action, resType));
    }

    static void start(int port, String url) throws Exception {
        Server server = new Server(port);
        ServletContextHandler handler = new ServletContextHandler(server, url);
        handler.addFilter(UrlFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        server.start();
    }
}

