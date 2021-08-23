package com.techelevator;

import com.techelevator.Inventory;

import java.math.BigDecimal;

public class Candy extends Inventory {

    public Candy(String slotLocation, String product, BigDecimal price, String type) {
        super(slotLocation, product,price,"Candy", "Munch Munch, Yum!");
    }
}
