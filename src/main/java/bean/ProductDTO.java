package bean;

import lombok.Data;

@Data
public class ProductDTO {

    //DTO의 멤버변수는 db테이블의 컬럼수와 동일해야함.
    private String id;
    private String title;
    private String content;
    private String price;
    private String img;
}
