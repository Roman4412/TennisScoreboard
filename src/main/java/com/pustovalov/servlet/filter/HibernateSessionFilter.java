package com.pustovalov.servlet.filter;

import com.pustovalov.HibernateUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

@WebFilter("/*")
public class HibernateSessionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {

            transaction = session.beginTransaction();
            chain.doFilter(request, response);
            transaction.commit();

        } catch(Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

}
