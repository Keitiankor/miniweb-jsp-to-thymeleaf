package controller;

import bean.ReplyDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orrid = req.getParameter("id");
        String comment = req.getParameter("content");
        String writer = req.getSession().getAttribute("id").toString();
        if (comment != null && !comment.equals("")) {
            ReplyDAO.getInstance().insert(orrid, comment, writer);
        }
    }
}
