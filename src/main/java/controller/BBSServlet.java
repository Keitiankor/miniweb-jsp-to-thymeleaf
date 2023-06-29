package controller;

import bean.BbsDAO;
import bean.BbsDTO;
import controller.config.ThymeleafConfiguration;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebServlet("/bbs")
public class BBSServlet extends HttpServlet {

    private int maxpage;

    public BBSServlet() {
        maxpage = BbsDAO.getInstance().page() / 10;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext()
            .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);

        IWebExchange webExchange = JakartaServletWebApplication
            .buildApplication(getServletContext())
            .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        int limit = 20;
        int offset = 0;
        String offsets = (String) req.getParameter("p");
        if (offsets != null) {
            offset = Integer.parseInt(offsets);
            offset = (offset - 1) * limit;
        }
        ArrayList<BbsDTO> list = BbsDAO.getInstance().listPage(limit, offset);

        String sessionid = (String) req.getSession().getAttribute("id");

        context.setVariable("sessionid", sessionid);
        context.setVariable("bbsList", list);
        context.setVariable("offset", offset);
        context.setVariable("maxpage", maxpage * 10);

        templateEngine.process("bbs", context, resp.getWriter());
    }
}
