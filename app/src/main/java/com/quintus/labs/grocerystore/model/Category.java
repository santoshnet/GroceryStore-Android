package com.quintus.labs.grocerystore.model;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class Category {
    String id;
    String category;
    String cateimg;
    String token;

    public Category(String id, String category, String cateimg) {
        this.id = id;
        this.category = category;
        this.cateimg = cateimg;
    }

    public Category(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCateimg() {
        return cateimg;
    }

    public void setCateimg(String cateimg) {
        this.cateimg = cateimg;
    }
}
