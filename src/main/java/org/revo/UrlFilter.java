package org.revo;

/**
 * Created by ashraf on 15/09/16.
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.revo.Function.Fun;
import org.revo.Function.Fun1;
import org.revo.Function.Fun2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.revo.Fire.getFun;
import static org.revo.Fire.postFun;
import static org.revo.Matcher.match;

public class UrlFilter implements Filter {
    private static ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        if (Objects.equals(req.getMethod(), "GET")) {

            Optional<Entry<String, Fun>> any = getFun.entrySet().stream().filter(s -> match(s.getKey(), req.getRequestURI()).isMatch()).findAny();
            any.ifPresent(stringFunEntry -> {
                Fun1 value = (Fun1) stringFunEntry.getValue();
                Object apply = value.apply(match(stringFunEntry.getKey(), req.getRequestURI()).getUrlParams());
                if (!(apply instanceof Empty))
                    try {
                        res.getWriter().print(ow.writeValueAsString(apply));
                    } catch (JsonProcessingException ignored) {

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            });


        } else if (Objects.equals(req.getMethod(), "POST")) {


            Optional<Entry<String, RequestHolder>> any = postFun.entrySet().stream().filter(s -> match(s.getKey(), req.getRequestURI()).isMatch()).findAny();
            any.ifPresent(stringFunEntry -> {
                RequestHolder requestHolder = stringFunEntry.getValue();
                Fun2 fun = (Fun2) requestHolder.fun;
                Object bb=null;
                if (requestHolder.type != Empty.class) {
                    try {
                         bb = bb(req, requestHolder.getType());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Object apply = fun.apply(match(stringFunEntry.getKey(), req.getRequestURI()).getUrlParams(), bb==null?new Empty():bb);
                if (!(apply instanceof Empty))
                    try {

                        res.getWriter().print(ow.writeValueAsString(apply));
                    } catch (JsonProcessingException ignored) {

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            });


        }


    }

    @Override
    public void destroy() {
    }

    static Object bb(HttpServletRequest req, Class b) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        return mapper.readValue(br.lines().collect(Collectors.joining()), b);
    }
}