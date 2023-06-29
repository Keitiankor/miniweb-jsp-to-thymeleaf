package controller;

import bean.ProductDAO;
import bean.ProductDTO;
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

@WebServlet("/product")
public class ProductsServlet extends HttpServlet {

    private int maxpage;

    public ProductsServlet() {
        maxpage = ProductDAO.getInstance().page() / 10;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext()
            .getAttribute(ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);

        IWebExchange webExchange = JakartaServletWebApplication
            .buildApplication(getServletContext())
            .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        int limit = 10;
        int offset = 0;
        String offsets = (String) req.getParameter("p");
        if (offsets != null) {
            offset = Integer.parseInt(offsets);
            offset = (offset - 1) * limit;
        }
        ArrayList<ProductDTO> list = ProductDAO.getInstance().listLimit(limit, offset);
        context.setVariable("productList", list);
        context.setVariable("offset", offset);
        context.setVariable("maxpage", maxpage * 10);

        templateEngine.process("product", context, resp.getWriter());
    }
}
