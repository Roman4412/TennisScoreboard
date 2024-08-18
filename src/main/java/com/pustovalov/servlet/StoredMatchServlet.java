package com.pustovalov.servlet;

import com.pustovalov.dao.HibernateMatchDao;
import com.pustovalov.dto.StoredMatchRespDto;
import com.pustovalov.service.StoredMatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static java.lang.Integer.parseInt;

@WebServlet("/matches")
public class StoredMatchServlet extends BaseServlet {
    public static final int DEFAULT_PAGE = 0;
    private StoredMatchService storedMatchService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filterByPlayerName = req.getParameter("filter-by-player-name");
        String page = req.getParameter("page");
        int preparedPage = page == null ? DEFAULT_PAGE : parseInt(page);
        StoredMatchRespDto response;

        if (filterByPlayerName == null || filterByPlayerName.isBlank()) {
            response = storedMatchService.findAll(preparedPage);
        } else {
            response = storedMatchService.findAll(preparedPage, filterByPlayerName);
        }

        req.setAttribute("resp", response);
        req.getRequestDispatcher("WEB-INF/jsp/matches.jsp").forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        storedMatchService = new StoredMatchService(HibernateMatchDao.getInstance());
    }

}
