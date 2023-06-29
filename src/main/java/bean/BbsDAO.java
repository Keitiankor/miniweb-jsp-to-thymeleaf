package bean;

import java.sql.*;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import mylib.ConnectionInf;
import mylib.PSF;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BbsDAO {

    private static BbsDAO instance;

    private static final ConnectionInf connInf = new ConnectionInf("jdbc:mysql://localhost:3306/shop", "study", "1234");

    public static BbsDAO getInstance() {
        if (instance == null) {
            synchronized (BbsDAO.class) {
                if (instance == null) {
                    try {
                        instance = new BbsDAO();
                        Class.forName("com.mysql.cj.jdbc.Driver");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public int insert(String... s) {
        int result = 0;

        String sql = "insert into bbs values (null,?,?,?)";
        try (PSF psf = PSF.getPreparedStatement(sql, connInf, s[0], s[1], s[2])) {
            psf.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int delete(String id) {
        int result = 0;

        String sql = """
            DELETE FROM bbs WHERE (id = ?);
            """;

        try (PSF psf = PSF.getPreparedStatement(sql, connInf, id)) {
            result += psf.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return result;
    }

    public int update(BbsDTO dto) {
        int result = 0;

        String sql = """
            UPDATE bbs SET title = ?, content = ? WHERE (id = ?);
            """;

        try (PSF psf = PSF.getPreparedStatement(sql, connInf, dto.getTitle(), dto.getContent(), dto.getId())) {
            result += psf.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return result;
    }

    public ArrayList<BbsDTO> list() {
        ArrayList<BbsDTO> list = new ArrayList<>();

        String sql = "select * from bbs order by id desc";
        try (PSF psf = PSF.getPreparedStatement(sql, connInf)) {
            ResultSet rs = psf.executeQuery();
            while (rs.next()) {
                BbsDTO dto = new BbsDTO();
                dto.setId(rs.getString(1));
                dto.setTitle(rs.getString(2));
                dto.setContent(rs.getString(3));
                dto.setWriter(rs.getString(4));

                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<BbsDTO> listPage(int limit, int offset) {
        ArrayList<BbsDTO> list = new ArrayList<>();

        String sql = "select * from bbs order by id desc limit ? offset ?";
        try (PSF psf = PSF.getPreparedStatement(sql, connInf, limit, offset)) {
            ResultSet rs = psf.executeQuery();
            while (rs.next()) {
                BbsDTO dto = new BbsDTO();
                dto.setId(rs.getString(1));
                dto.setTitle(rs.getString(2));
                dto.setContent(rs.getString(3));
                dto.setWriter(rs.getString(4));

                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public BbsDTO one(String id) {
        BbsDTO dto2 = new BbsDTO();

        String sql = "select * from bbs where id = ?";

        try (PSF psf = PSF.getPreparedStatement(sql, connInf, id)) {
            ResultSet rs = psf.executeQuery();
            if (rs.next()) {
                dto2.setId(rs.getString(1));
                dto2.setTitle(rs.getString(2));
                dto2.setContent(rs.getString(3));
                dto2.setWriter(rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dto2;
    }

    public int page() {
        String sql = "select count(*) from bbs";
        try (PSF psf = PSF.getPreparedStatement(sql, connInf)) {
            ResultSet rs = psf.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return -1;
    }
}
