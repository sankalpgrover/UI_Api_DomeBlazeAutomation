
package com.adidas.api.requestmodals.createpets;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;


public class Pet {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("category")
    @Expose
    private Category category;
    @JsonEnumDefaultValue
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photoUrls")
    @Expose
    private List<String> photoUrls = null;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("status")
    @Expose
    private String status;


    public Pet(){
        setCategory(new Category(0,"string"));
        setPhotoUrls(Arrays.asList("string"));
        setTags(Arrays.asList(new Tag(0,"string")));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = (name!=null)?name:"string";
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
