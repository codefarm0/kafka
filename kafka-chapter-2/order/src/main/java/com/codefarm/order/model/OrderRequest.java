package com.codefarm.order.model;

public class OrderRequest {
    private String id;//items, amount, paymentstatus
    private String userId;//will call user service , name, email, mob

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
