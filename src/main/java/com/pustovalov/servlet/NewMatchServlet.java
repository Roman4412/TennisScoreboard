package com.pustovalov.servlet;

import com.pustovalov.entity.Match;
import com.pustovalov.service.CurrentMatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/new-match")
public class NewMatchServlet extends BaseServlet {
    CurrentMatchService currentMatchService = new CurrentMatchService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> params = getParamsFromBody(req);
        String playerOneName = params.get("player-one-name");
        String playerTwoName = params.get("player-two-name");
        //validateStringParams(playerOneName, playerTwoName);
        Match save = currentMatchService.save(playerOneName, playerTwoName);
        resp.getWriter().print(save);
        resp.setStatus(HttpServletResponse.SC_SEE_OTHER);

        //TODO редирект на /match-score?uuid=$match_id

    }

}
