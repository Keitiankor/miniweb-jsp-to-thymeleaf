package controller;

import bean.BbsDAO;
import bean.BbsDTO;
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

@WebServlet("/editpost")
public class PostEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext()
            .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);

        IWebExchange webExchange = JakartaServletWebApplication
            .buildApplication(getServletContext())
            .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        BbsDTO dto = BbsDAO.getInstance().one(req.getParameter("id"));
        context.setVariable("post", dto);

        templateEngine.process("editpost", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {
        BbsDTO dto = new BbsDTO();
        dto.setId(req.getParameter("id"));
        dto.setTitle(req.getParameter("title"));
        dto.setContent(req.getParameter("content"));

        int res = BbsDAO.getInstance().update(dto);

        resp.getWriter().write(String.valueOf(res));
    }
}
