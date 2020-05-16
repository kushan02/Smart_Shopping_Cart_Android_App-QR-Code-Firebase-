package com.megha.kushan.smartshoppingcart;

import java.util.ArrayList;

public class DB_Cart_Details {
    private Integer product_cost;
    private String product_name;

    public DB_Cart_Details(Integer product_cost, String product_name) {
        this.product_cost = product_cost;
        this.product_name = product_name;
    }

    public String getProduct_cost() {
        return "₹ "+product_cost.toString();
    }

    public void setProduct_cost(Integer product_cost) {
        this.product_cost = product_cost;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
