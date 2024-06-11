package com.pustovalov.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class BaseServlet extends HttpServlet {
    public static final String NAME_VALUE_DELIMITER = "=";
    public static final String PARAM_DELIMITER = "&";

    boolean validateStringParams(String pattern, String... strings) {
        for(String s : strings) {
            if (s == null || !Pattern.matches(pattern, s)) {
                return false;
            }
        }
        return true;
    }

    protected Map<String, String> getParamsFromBody(HttpServletRequest request) {
        //TODO сценарий при отсутствии параметров в теле
        Map<String, String> params = new HashMap<>();
        try(BufferedReader reader = request.getReader()) {
            String[] strings = reader.readLine().split(PARAM_DELIMITER);
            for(String s : strings) {
                String[] split = s.split(NAME_VALUE_DELIMITER);
                params.put(split[0], URLDecoder.decode(split[1], StandardCharsets.UTF_8));
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return params;
    }

}
