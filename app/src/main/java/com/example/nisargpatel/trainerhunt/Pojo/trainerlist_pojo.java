package com.example.nisargpatel.trainerhunt.Pojo;

public class
trainerlist_pojo {

    /*private int id;
    private String title;
    private String shortdesc;
    private double rating;*/
    private String price;
    private String image;
    private int id;
    private String name;
    private String address;
    private int likeCount;

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    String like;


    public trainerlist_pojo() {
        //this.id = id;
        //this.title = title;
        //this.shortdesc = shortdesc;
        //this.rating = rating;
        this.price = price;
        this.setImage(image);
        this.setLikeCount(likeCount);
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setLike(like);


    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {

        return price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
