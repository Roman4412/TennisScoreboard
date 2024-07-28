package com.pustovalov.servlet;

import com.pustovalov.dao.HibernateMatchDao;
import com.pustovalov.service.StoredMatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/matches")
public class StoredMatchServlet extends BaseServlet {
    private StoredMatchService storedMatchService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String filterParam = req.getParameter("filter_by_player_name");
        String playerNameParam = req.getParameter("player_name");
        int offsetParam = Integer.parseInt(req.getParameter("offset"));
        int limitParam = Integer.parseInt(req.getParameter("limit"));
        //TODO  validateParams();

        if (filterParam == null || filterParam.isBlank()) {
            System.out.println(storedMatchService.findAll(offsetParam, limitParam));
        } else {
//            storedMatchService.getMatchesFilterByName(10, 1, playerNameParam);
        }
    }
    @Override
    public void init() throws ServletException {
        super.init();
        storedMatchService = new StoredMatchService(HibernateMatchDao.getInstance());
    }

}
