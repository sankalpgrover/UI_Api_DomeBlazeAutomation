
package com.adidas.api.requestmodals.createpets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public Category() {

    }

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id!=null?id:0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name!=null?name:"string";
    }

}
