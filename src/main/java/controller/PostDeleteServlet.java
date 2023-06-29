package controller;

import bean.BbsDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;

@WebServlet("/deletepost")
public class PostDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {
        int res = -1;
        String id = req.getParameter("id");
        String account = (String) req.getSession().getAttribute("id");
        String writer = req.getParameter("writer");
        System.out.println(id);
        System.out.println(account);
        System.out.println(writer);
        if (account.equals(writer) && id != null) {
            res = BbsDAO.getInstance().delete(id);
            resp.getWriter().write(String.valueOf(res));
        }
    }
}
