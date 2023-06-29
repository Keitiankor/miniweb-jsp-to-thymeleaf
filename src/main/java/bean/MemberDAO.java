package bean;

import java.sql.*;
import mylib.ConnectionInf;
import mylib.PSF;

//db처리 전담 클래스
public class MemberDAO {

    private static MemberDAO instance;

    private static final ConnectionInf connInf = new ConnectionInf("jdbc:mysql://localhost:3306/shop", "study", "1234");

    public static MemberDAO getInstance() {
        if (instance == null) {
            synchronized (MemberDAO.class) {
                if (instance == null) {
                    try {
                        instance = new MemberDAO();
                        Class.forName("com.mysql.cj.jdbc.Driver");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public boolean idCheck(String id) {
        boolean result = false;

        String sql = "select * from member where id = ?";
        try (PSF psf = PSF.getPreparedStatement(sql, connInf, id)) {
            ResultSet rs = psf.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    public boolean login(String id, String pw) {
        boolean result = false;

        String sql = "select * from member where id = ? and pw = ?";
        try (PSF psf = PSF.getPreparedStatement(sql, connInf, id, pw)) {
            ResultSet rs = psf.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    public int insert(MemberDTO dto) {
        int result = 0;

        String sql = "insert into member values (?,?,?,?)";
        try (PSF psf = PSF.getPreparedStatement(sql, connInf, dto.getId(), dto.getPw(), dto.getName(), dto.getTel())) {
            result = psf.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }
}
