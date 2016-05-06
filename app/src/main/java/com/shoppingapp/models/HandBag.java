package com.shoppingapp.models;

import java.io.Serializable;

/**
 * Created by prerana_katyarmal on 2/16/2016.
 */
public class HandBag implements Serializable{
    private int id;
    private String bagName;
    private int drawableImage;

    public HandBag(int id, String bagName, int drawableImage) {
        this.id = id;
        this.bagName = bagName;
        this.drawableImage = drawableImage;
    }

    public HandBag() {
        super();
    }

    @Override
    public String toString() {
        return "HandBag{" +
                "id=" + id +
                ", bagName='" + bagName + '\'' +
                ", drawableImage=" + drawableImage +
                '}';
    }

    public int getDrawableImage() {
        return drawableImage;
    }

    public void setDrawableImage(int drawableImage) {
        this.drawableImage = drawableImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBagName() {
        return bagName;
    }

    public void setBagName(String bagName) {
        this.bagName = bagName;
    }
}
