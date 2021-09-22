package com.example.nisargpatel.trainerhunt.Pojo;

public class
category_pojo {

    /*private int id;
    private String title;
    private String shortdesc;
    private double rating;
    private double price;*/
    private String image;
    private int id;
    private String name;
    private String quotes;


    public category_pojo() {
        //this.id = id;
        //this.title = title;
        //this.shortdesc = shortdesc;
        //this.rating = rating;
        // this.price = price;


        this.setId(id);
        this.setName(name);
        this.setQuotes(quotes);


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

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }


}
