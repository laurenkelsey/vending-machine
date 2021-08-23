package com.techelevator;

import java.math.BigDecimal;

public class Inventory implements Sounds{
    private String slotLocation;
    private String product;
    private BigDecimal price;
    private int quantityATS = 5;
    private String type;
    private String sound;

    public Inventory(String slotLocation, String product, BigDecimal price, String type, String sound) {
        this.slotLocation = slotLocation;
        this.product = product;
        this.price = price;
        this.type = type;
        this.sound = sound;
    }

    public void lowerQuantity() {
        quantityATS--;
    }

    public Inventory(BigDecimal price) {
        this.price = price;
    }

    public static void main (String[] args){

    }


    public String getSlotLocation() {
        return slotLocation;
    }

    public void setSlotLocation(String slotLocation) {
        this.slotLocation = slotLocation;
    }

    @Override
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantityATS() {
        return quantityATS;
    }

    public void setQuantityATS(int quantityATS) {
        this.quantityATS = quantityATS;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

}
