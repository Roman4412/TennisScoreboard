package com.pustovalov.servlet;

import com.pustovalov.service.MatchScoringService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.pustovalov.util.ReqParamValidator.*;

@WebServlet("/match-score")
public class MatchScoringServlet extends HttpServlet {
  private MatchScoringService matchScoringService;
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String uuidParam = req.getParameter("uuid");
    validateUuid(uuidParam);

    req.setAttribute("resp", matchScoringService.getScoreState(UUID.fromString(uuidParam)));
    req.getRequestDispatcher("WEB-INF/jsp/match-score.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String uuidParam = req.getParameter("uuid");
    String playerIdParam = req.getParameter("player-id");

    validateUuid(uuidParam);
    validatePlayerId(playerIdParam);

    UUID uuid = UUID.fromString(uuidParam);
    Long playerId = Long.parseLong(playerIdParam);

    matchScoringService.count(uuid, playerId);
    req.setAttribute("resp", matchScoringService.getScoreState(uuid));
    req.getRequestDispatcher("WEB-INF/jsp/match-score.jsp").forward(req, resp);
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    matchScoringService = MatchScoringService.getInstance();
  }
}
