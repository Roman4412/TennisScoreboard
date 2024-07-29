package com.pustovalov.servlet;

import com.pustovalov.dao.HibernateMatchDao;
import com.pustovalov.entity.Match;
import com.pustovalov.service.StoredMatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/matches")
public class StoredMatchServlet extends BaseServlet {
    public static final int LIMIT = 5;

    private StoredMatchService storedMatchService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filterByNameParam = req.getParameter("player-name");
        //        int limitParam = Integer.parseInt(req.getParameter("limit"));
        int offsetParam = Integer.parseInt(Optional.ofNullable(req.getParameter("offset")).orElse("0"));

        //TODO  validateParams();

        System.out.println("player-name; " + filterByNameParam);
        System.out.println("offset; " + offsetParam);

        if (filterByNameParam == null || filterByNameParam.isBlank()) {
            req.setAttribute("matches", storedMatchService.findAll(offsetParam, LIMIT));
            req.setAttribute("totalPages", storedMatchService.getNumOfPages(LIMIT));
            req.getRequestDispatcher("WEB-INF/jsp/matches.jsp").forward(req, resp);
        } else {
            System.out.println("AAAAAA" +storedMatchService.getNumOfPages(LIMIT,filterByNameParam));
            List<Match> matchesFilterByName = storedMatchService.getMatchesFilterByName(offsetParam, LIMIT,
                    filterByNameParam);
            req.setAttribute("playerName", filterByNameParam);
            req.setAttribute("matches", matchesFilterByName);
            req.setAttribute("totalPages", storedMatchService.getNumOfPages(LIMIT,filterByNameParam));
            req.getRequestDispatcher("WEB-INF/jsp/matches.jsp").forward(req, resp);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        storedMatchService = new StoredMatchService(HibernateMatchDao.getInstance());
    }

}
