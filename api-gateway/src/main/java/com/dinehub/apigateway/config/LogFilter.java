package com.dinehub.apigateway.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFilterFunction;

import org.springframework.web.servlet.function.ServerResponse;

@Component
public class LogFilter {

    public HandlerFilterFunction<ServerResponse, ServerResponse> filter() {

        return (request, next) -> {

            System.out.println("Pre Processing Logic: "+ request.uri());
            ServerResponse response = next.handle(request);
            System.out.println("Post Processing Logic: "+ response.statusCode());

            return response;
        };
    }
}