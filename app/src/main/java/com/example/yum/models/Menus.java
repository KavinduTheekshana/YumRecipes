package com.example.yum.models;

public class Menus {
    String id,recipename,recipeingredients,recipedescription,recipeimage,recipestatus,
            categoryname,userimage,username,recipeslikes,date;

    public Menus(){}

    public Menus(String id, String recipename, String recipeingredients, String recipedescription,
                 String recipeimage, String recipestatus, String categoryname, String userimage,
                 String username, String recipeslikes, String date) {
        this.id = id;
        this.recipename = recipename;
        this.recipeingredients = recipeingredients;
        this.recipedescription = recipedescription;
        this.recipeimage = recipeimage;
        this.recipestatus = recipestatus;
        this.categoryname = categoryname;
        this.userimage = userimage;
        this.username = username;
        this.recipeslikes = recipeslikes;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipename() {
        return recipename;
    }

    public void setRecipename(String recipename) {
        this.recipename = recipename;
    }

    public String getRecipeingredients() {
        return recipeingredients;
    }

    public void setRecipeingredients(String recipeingredients) {
        this.recipeingredients = recipeingredients;
    }

    public String getRecipedescription() {
        return recipedescription;
    }

    public void setRecipedescription(String recipedescription) {
        this.recipedescription = recipedescription;
    }

    public String getRecipeimage() {
        return recipeimage;
    }

    public void setRecipeimage(String recipeimage) {
        this.recipeimage = recipeimage;
    }

    public String getRecipestatus() {
        return recipestatus;
    }

    public void setRecipestatus(String recipestatus) {
        this.recipestatus = recipestatus;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRecipeslikes() {
        return recipeslikes;
    }

    public void setRecipeslikes(String recipeslikes) {
        this.recipeslikes = recipeslikes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
