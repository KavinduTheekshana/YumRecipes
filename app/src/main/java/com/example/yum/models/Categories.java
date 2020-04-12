package com.example.yum.models;

public class Categories {
    String id,image,name;

    public Categories(){

    }

    public Categories(String id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
