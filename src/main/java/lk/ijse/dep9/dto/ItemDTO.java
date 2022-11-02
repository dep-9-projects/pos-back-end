package lk.ijse.dep9.dto;

import java.io.Serializable;

public class ItemDTO implements Serializable {
    private int stock;
    private double unit_price;
    private String description;

    public ItemDTO() {
    }

    public ItemDTO(int stock, double unit_price, String description, String code) {
        this.stock = stock;
        this.unit_price = unit_price;
        this.description = description;
        this.code = code;
    }

    private String code;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
