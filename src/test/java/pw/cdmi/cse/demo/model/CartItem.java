package pw.cdmi.cse.demo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartItem implements Serializable {
    private String customerId;
    private String goodsId;
    private String goodsName;
    private Float goodsPrice;
    private Integer amount;
}
