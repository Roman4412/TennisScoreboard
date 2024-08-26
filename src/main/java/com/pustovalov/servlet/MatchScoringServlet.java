package com.pustovalov.servlet;

import com.pustovalov.dto.response.MatchScoreDto;
import com.pustovalov.service.MatchScoringService;
import com.pustovalov.service.OngoingMatchService;
import com.pustovalov.service.PersistenceMatchService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoringServlet extends BaseServlet {
    private OngoingMatchService ongoingMatchService;
    private MatchScoringService matchScoringService;
    private PersistenceMatchService persistenceMatchService;

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        req.setAttribute("resp", ongoingMatchService.getMatchForView(uuid));
        req.getRequestDispatcher("WEB-INF/jsp/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        Long playerId = Long.parseLong(req.getParameter("player-id"));

        MatchScoreDto response = matchScoringService.countPoint(playerId, uuid);

        if (response.isFinished()) {
            req.setAttribute("resp", persistenceMatchService.save(uuid));
            req.getRequestDispatcher("WEB-INF/jsp/match-result.jsp").forward(req, resp);
        } else {
            req.setAttribute("resp", response);
            req.getRequestDispatcher("WEB-INF/jsp/match-score.jsp").forward(req, resp);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        matchScoringService = MatchScoringService.getInstance();
        ongoingMatchService = OngoingMatchService.getInstance();
        persistenceMatchService = PersistenceMatchService.getInstance();
    }

}
