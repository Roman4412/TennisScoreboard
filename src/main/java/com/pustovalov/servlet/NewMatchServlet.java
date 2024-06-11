package com.pustovalov.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/new-match")
public class NewMatchServlet extends GeneralServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerOneName = getParamFromBody(req, "player-one-name");
        String playerTwoName = getParamFromBody(req, "player-two-name");
        validateStringParams(playerOneName, playerTwoName);
        saveMatchService.save(requestMatchDto);
    }
}
