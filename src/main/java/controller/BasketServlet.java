package controller;

import bean.ProductDAO;
import bean.ProductDTO;
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

@WebServlet("/basket")
public class BasketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext()
            .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);

        IWebExchange webExchange = JakartaServletWebApplication
            .buildApplication(getServletContext())
            .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);
        ArrayList<ProductDTO> basketList = new ArrayList<>();

        Object obj = req.getSession().getAttribute("basket");
        if (obj != null) {
            for (Object string : (ArrayList<?>) obj) {
                if (string instanceof String) {
                    basketList.add(ProductDAO.getInstance().one((String) string));
                }
            }
        }
        context.setVariable("productList", basketList);

        templateEngine.process("basket", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<String> basketList = new ArrayList<>();
        Object obj = req.getSession().getAttribute("basket");
        if (obj != null) {
            for (Object string : (ArrayList<?>) obj) {
                if (string instanceof String) {
                    basketList.add((String) string);
                }
            }
        }
        String id = req.getParameter("id");
        basketList.add(id);

        req.getSession().setAttribute("basket", basketList);
        resp.getWriter().write(String.valueOf(HttpServletResponse.SC_OK));
        System.out.println(req.getSession().getAttribute("basket"));
    }
}
