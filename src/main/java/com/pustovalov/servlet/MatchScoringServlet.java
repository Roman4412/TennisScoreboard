package com.pustovalov.servlet;

import com.pustovalov.service.MatchScoringService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoringServlet extends BaseServlet {
  private MatchScoringService matchScoringService;
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    UUID uuid = UUID.fromString(req.getParameter("uuid"));
    req.setAttribute("resp", matchScoringService.getScoreState(uuid));
    req.getRequestDispatcher("WEB-INF/jsp/match-score.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    UUID uuid = UUID.fromString(req.getParameter("uuid"));
    Long playerId = Long.parseLong(req.getParameter("player-id"));

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
