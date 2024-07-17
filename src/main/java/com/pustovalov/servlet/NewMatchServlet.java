package com.pustovalov.servlet;

import com.pustovalov.dto.CreateMatchDto;
import com.pustovalov.entity.Match;
import com.pustovalov.service.OngoingMatchService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/new-match")
public class NewMatchServlet extends BaseServlet {
    private OngoingMatchService ongoingMatchService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/new-match.jsp").forward(req,resp);
    }

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String> params = getParamsFromBody(req);
        CreateMatchDto newMatch = new CreateMatchDto(
                params.get("player-one-name"),
                params.get("player-two-name")
                );

        Match savedMatch = ongoingMatchService.saveInMemory(newMatch);
        resp.sendRedirect("/match-score?uuid=" + savedMatch.getExternalId());
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ongoingMatchService = OngoingMatchService.getInstance();
    }

}
