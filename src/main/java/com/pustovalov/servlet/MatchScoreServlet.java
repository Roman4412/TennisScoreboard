package com.pustovalov.servlet;

import com.pustovalov.entity.Match;
import com.pustovalov.service.FinishedMatchService;
import com.pustovalov.service.OngoingMatchService;
import com.pustovalov.service.MatchScoringService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends BaseServlet {
    private OngoingMatchService ongoingMatchService;
    private MatchScoringService matchScoringService;
    private FinishedMatchService finishedMatchService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        req.setAttribute("match", ongoingMatchService.get(UUID.fromString(uuid)));
        req.getRequestDispatcher("WEB-INF/jsp/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        Match match = ongoingMatchService.get(uuid);
        Long playerId = Long.parseLong(req.getParameter("player-id"));

        matchScoringService.countPoint(playerId, match.getExternalId());

        if(match.isFinished()) {
            finishedMatchService.persist(match);
            ongoingMatchService.delete(uuid);
        } else {
            req.setAttribute("match", match);
            req.getRequestDispatcher("WEB-INF/jsp/match-score.jsp").forward(req, resp);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        matchScoringService = MatchScoringService.getInstance();
        ongoingMatchService = OngoingMatchService.getInstance();
        finishedMatchService = FinishedMatchService.getInstance();
    }

}
