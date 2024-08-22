package com.pustovalov.servlet;

import com.pustovalov.dto.MatchResultsResponse;
import com.pustovalov.dto.MatchScoreResponse;
import com.pustovalov.entity.Match;
import com.pustovalov.entity.Player;
import com.pustovalov.enums.ScoreUnits;
import com.pustovalov.service.FinishedMatchPersistenceService;
import com.pustovalov.service.MatchScoringService;
import com.pustovalov.service.OngoingMatchService;
import com.pustovalov.strategy.Score;
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
    private FinishedMatchPersistenceService finishedMatchPersistenceService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));

        Match match = ongoingMatchService.get(uuid);
        Score score = match.getScore();
        Player playerOne = match.getPlayerOne();
        Player playerTwo = match.getPlayerTwo();
        MatchScoreResponse response = MatchScoreResponse.builder()
                .uuid(match.getExternalId().toString()).playerOne(playerOne)
                .playerOneGamePts(score.getPoints(playerOne.getId(), ScoreUnits.GAME))
                .playerOneSetPts(score.getPoints(playerOne.getId(), ScoreUnits.SET))
                .playerOneMatchPts(score.getPoints(playerOne.getId(), ScoreUnits.MATCH))
                .playerOneTiebreakPts(score.getPoints(playerOne.getId(), ScoreUnits.TIEBREAK))
                .playerTwo(playerTwo).playerTwoGamePts(score.getPoints(playerTwo.getId(), ScoreUnits.GAME))
                .playerTwoSetPts(score.getPoints(playerTwo.getId(), ScoreUnits.SET))
                .playerTwoMatchPts(score.getPoints(playerTwo.getId(), ScoreUnits.MATCH))
                .playerTwoTiebreakPts(score.getPoints(playerTwo.getId(), ScoreUnits.TIEBREAK))
                .isFinished(match.isFinished()).build();

        req.setAttribute("resp", response);
        req.getRequestDispatcher("WEB-INF/jsp/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        Long playerId = Long.parseLong(req.getParameter("player-id"));
        Match match = ongoingMatchService.get(uuid);

        matchScoringService.countPoint(playerId, uuid);


        if (match.isFinished()) {
            finishedMatchPersistenceService.persist(match);
            ongoingMatchService.delete(uuid);

            Score score = match.getScore();
            Player playerOne = match.getPlayerOne();
            Player playerTwo = match.getPlayerTwo();
            MatchResultsResponse response = MatchResultsResponse.builder()
                    .playerOneName(playerOne.getName())
                    .playerOneSetPts(score.getResultPoints(playerOne.getId(),ScoreUnits.SET).get(0))
                    .playerOneMatchPts(score.getResultPoints(playerOne.getId(),ScoreUnits.MATCH).get(0))
                    .playerTwoName(playerTwo.getName())
                    .playerTwoSetPts(score.getResultPoints(playerTwo.getId(),ScoreUnits.SET).get(0))
                    .playerTwoMatchPts(score.getResultPoints(playerTwo.getId(),ScoreUnits.MATCH).get(0))
                    .build();

            req.setAttribute("resp", response);
            req.getRequestDispatcher("WEB-INF/jsp/match-results.jsp").forward(req, resp);
        } else {

            Score score = match.getScore();
            Player playerOne = match.getPlayerOne();
            Player playerTwo = match.getPlayerTwo();
            MatchScoreResponse response = MatchScoreResponse.builder()
                    .uuid(match.getExternalId().toString()).playerOne(playerOne)
                    .playerOneGamePts(score.getPoints(playerOne.getId(), ScoreUnits.GAME))
                    .playerOneSetPts(score.getPoints(playerOne.getId(), ScoreUnits.SET))
                    .playerOneMatchPts(score.getPoints(playerOne.getId(), ScoreUnits.MATCH))
                    .playerOneTiebreakPts(score.getPoints(playerOne.getId(), ScoreUnits.TIEBREAK))
                    .playerTwo(playerTwo).playerTwoGamePts(score.getPoints(playerTwo.getId(), ScoreUnits.GAME))
                    .playerTwoSetPts(score.getPoints(playerTwo.getId(), ScoreUnits.SET))
                    .playerTwoMatchPts(score.getPoints(playerTwo.getId(), ScoreUnits.MATCH))
                    .playerTwoTiebreakPts(score.getPoints(playerTwo.getId(), ScoreUnits.TIEBREAK))
                    .isFinished(match.isFinished()).build();

            req.setAttribute("resp", response);
            req.getRequestDispatcher("WEB-INF/jsp/match-score.jsp").forward(req, resp);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        matchScoringService = MatchScoringService.getInstance();
        ongoingMatchService = OngoingMatchService.getInstance();
        finishedMatchPersistenceService = FinishedMatchPersistenceService.getInstance();
    }

}
