package controller;

import bean.BbsDAO;
import bean.BbsDTO;
import bean.ReplyDAO;
import bean.ReplyDTO;
import controller.config.ThymeleafConfiguration;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.ArrayList;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebServlet("/post")
public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext()
            .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);

        IWebExchange webExchange = JakartaServletWebApplication
            .buildApplication(getServletContext())
            .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        String accout = (String) req.getSession().getAttribute("id");
        context.setVariable("sessionid", accout);

        String id = req.getParameter("id");
        BbsDTO dto = BbsDAO.getInstance().one(id);
        ArrayList<ReplyDTO> list = ReplyDAO.getInstance().list(id);

        context.setVariable("post", dto);
        context.setVariable("replyList", list);
        context.setVariable("newLineChar", "\n");

        templateEngine.process("post", context, resp.getWriter());
    }
}
