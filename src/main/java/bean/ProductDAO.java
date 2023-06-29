package bean;

import java.sql.*;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import mylib.ConnectionInf;
import mylib.PSF;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDAO {

    private static ProductDAO instance;

    private static final ConnectionInf connInf = new ConnectionInf("jdbc:mysql://localhost:3306/shop", "study", "1234");

    public static ProductDAO getInstance() {
        if (instance == null) {
            synchronized (ProductDAO.class) {
                if (instance == null) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        instance = new ProductDAO();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public ArrayList<ProductDTO> listLimit(int limit, int offset) {
        ArrayList<ProductDTO> list = new ArrayList<>();

        String sql = "select * from product order by id asc limit ? offset ?";
        try (PSF psf = PSF.getPreparedStatement(sql, connInf, limit, offset)) {
            ResultSet rs = psf.executeQuery();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO();

                dto.setId(rs.getString(1));
                dto.setTitle(rs.getString(2));
                dto.setContent(rs.getString(3));
                dto.setPrice(rs.getString(4));
                dto.setImg(rs.getString(6));

                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<ProductDTO> list() {
        ArrayList<ProductDTO> list = new ArrayList<>();

        String sql = "select * from product";
        try (PSF psf = PSF.getPreparedStatement(sql, connInf)) {
            ResultSet rs = psf.executeQuery();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO();

                dto.setId(rs.getString(1));
                dto.setTitle(rs.getString(2));
                dto.setContent(rs.getString(3));
                dto.setPrice(rs.getString(4));
                dto.setImg(rs.getString(6));

                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ProductDTO one(String id) {
        ProductDTO dto2 = new ProductDTO();

        String sql = "select * from product where id=?";
        try (PSF psf = PSF.getPreparedStatement(sql, connInf, id)) {
            ResultSet rs = psf.executeQuery();
            if (rs.next()) {
                dto2.setId(rs.getString(1));
                dto2.setTitle(rs.getString(2));
                dto2.setContent(rs.getString(3));
                dto2.setPrice(rs.getString(4));
                dto2.setImg(rs.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dto2;
    }

    public int page() {
        String sql = "select count(*) from product";
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
