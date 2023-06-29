package controller;

import bean.MemberDAO;
import bean.MemberDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MemberDTO dto = new MemberDTO();
        dto.setId(req.getParameter("id"));
        dto.setPw(req.getParameter("pw"));
        dto.setName(req.getParameter("name"));
        dto.setTel(req.getParameter("tel"));
        resp.setContentType("");
        resp.setCharacterEncoding("UTF-8");
        if (!MemberDAO.getInstance().idCheck(dto.getId())) {
            MemberDAO.getInstance().insert(dto);
            resp.getWriter().write(String.valueOf(HttpServletResponse.SC_CREATED));
        } else {
            resp.getWriter().write(String.valueOf(HttpServletResponse.SC_CONFLICT));
        }
    }
}
