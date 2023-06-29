package bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import mylib.ConnectionInf;
import mylib.PSF;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyDAO {

    private static ReplyDAO instance;

    private static final ConnectionInf connInf = new ConnectionInf("jdbc:mysql://localhost:3306/shop", "study", "1234");

    public static ReplyDAO getInstance() {
        if (instance == null) {
            synchronized (ReplyDAO.class) {
                if (instance == null) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        instance = new ReplyDAO();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public ArrayList<ReplyDTO> list(String id) {
        ArrayList<ReplyDTO> list = new ArrayList<>();

        String sql = "select * from reply where oriid = ? order by id asc ";
        try (PSF psf = PSF.getPreparedStatement(sql, connInf, id)) {
            ResultSet rs = psf.executeQuery();
            while (rs.next()) {
                ReplyDTO dto = new ReplyDTO();
                dto.setId(rs.getString(1));
                dto.setContent(rs.getString(3));
                dto.setWriter(rs.getString(4));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public int insert(String oriid, String comment, String writer) {
        String sql = "insert into reply (oriid, content, writer) values (?, ?, ?)";
        try (PSF psf = PSF.getPreparedStatement(sql, connInf, oriid, comment, writer)) {
            psf.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }
}
