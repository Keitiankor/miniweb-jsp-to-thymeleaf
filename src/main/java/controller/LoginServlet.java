package controller;

import bean.MemberDAO;
import controller.config.ThymeleafConfiguration;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String password = req.getParameter("pw");

        boolean result = MemberDAO.getInstance().login(id, password);
        if (result) {
            req.getSession().setAttribute("id", id);
            resp.getWriter().write(String.valueOf(HttpServletResponse.SC_OK));
        } else {
            resp.getWriter().write(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext()
            .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);

        IWebExchange webExchange = JakartaServletWebApplication
            .buildApplication(getServletContext())
            .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        context.setVariable("success", "success");

        templateEngine.process("login", context, resp.getWriter());
    }
}
