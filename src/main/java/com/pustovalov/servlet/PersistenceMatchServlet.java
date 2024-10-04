package com.pustovalov.servlet;

import com.pustovalov.dao.HibernateMatchDao;
import com.pustovalov.dto.response.StoredMatchesDto;
import com.pustovalov.exception.MatchAlreadyPersistException;
import com.pustovalov.service.OngoingMatchService;
import com.pustovalov.service.PersistenceMatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

import static com.pustovalov.util.ReqParamValidator.*;
import static java.lang.Integer.parseInt;

@WebServlet("/matches")
public class PersistenceMatchServlet extends HttpServlet {

  private PersistenceMatchService persistenceMatchService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String filterByPlayerName = req.getParameter("filter-by-player-name");
    String pageParam = req.getParameter("page");
    StoredMatchesDto response;
    int page;

    if (pageParam == null || pageParam.isBlank()) {
      page = 0;
    } else {
      validatePageNumber(pageParam);
      page = parseInt(pageParam);
    }

    if (filterByPlayerName != null && !filterByPlayerName.isBlank()) {
      validatePlayerNameFilter(filterByPlayerName);
      response = persistenceMatchService.findAll(page, filterByPlayerName);
    } else {
      response = persistenceMatchService.findAll(page);
    }

    req.setAttribute("resp", response);
    req.getRequestDispatcher("WEB-INF/jsp/matches.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String uuid = req.getParameter("uuid");
    validateUuid(uuid);

    try {
      persistenceMatchService.save(UUID.fromString(uuid));
      resp.sendRedirect("/matches");
    } catch (MatchAlreadyPersistException e) {
      resp.sendRedirect("/matches");
    }
  }

  @Override
  public void init() throws ServletException {
    super.init();
    persistenceMatchService = new PersistenceMatchService(HibernateMatchDao.getInstance(),
            OngoingMatchService.getInstance());
  }
}