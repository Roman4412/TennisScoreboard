package com.pustovalov.servlet;

import com.pustovalov.model.entity.Match;
import com.pustovalov.model.service.CurrentMatchService;
import com.pustovalov.model.service.ScoringService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends BaseServlet {
    private CurrentMatchService currentMatchService;
    private ScoringService scoringService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        req.setAttribute("match", currentMatchService.get(UUID.fromString(uuid)));
        req.getRequestDispatcher("WEB-INF/jsp/matchScore.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        Match match = currentMatchService.get(UUID.fromString(uuid));
        Long playerId = Long.parseLong(req.getParameter("player-id"));

        scoringService.countPoint(playerId, match.getExternalId());
        req.setAttribute("match",currentMatchService.get(UUID.fromString(uuid)));
        req.getRequestDispatcher("WEB-INF/jsp/matchScore.jsp").forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        scoringService = ScoringService.getInstance();
        currentMatchService = CurrentMatchService.getInstance();
    }

}
