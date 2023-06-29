package controller;

import bean.BbsDAO;
import controller.config.ThymeleafConfiguration;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebServlet("/posting")
public class PostingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String writer = req.getSession().getAttribute("id").toString();
        BbsDAO.getInstance().insert(title, content, writer);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext()
            .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);

        IWebExchange webExchange = JakartaServletWebApplication
            .buildApplication(getServletContext())
            .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        templateEngine.process("posting", context, resp.getWriter());
    }
}
