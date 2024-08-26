package com.pustovalov.servlet;

import com.pustovalov.service.OngoingMatchService;
import com.pustovalov.service.mapper.MatchMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends BaseServlet {
    private OngoingMatchService ongoingMatchService;
    private MatchMapper mapper;

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String> params = getParamsFromBody(req);
        UUID uuid = ongoingMatchService.create(mapper.toCreateMatchDto(params));
        resp.sendRedirect("/match-score?uuid=" + uuid);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ongoingMatchService = OngoingMatchService.getInstance();
        mapper = MatchMapper.INSTANCE;
    }

}
