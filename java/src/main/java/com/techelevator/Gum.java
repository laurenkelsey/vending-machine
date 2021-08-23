package com.techelevator;

import com.techelevator.Inventory;

import java.math.BigDecimal;

public class Gum extends Inventory {

    public Gum(String slotLocation, String product, BigDecimal price, String type) {
        super(slotLocation, product,price,"Gum", "Chew Chew, Yum!");
    }
}
