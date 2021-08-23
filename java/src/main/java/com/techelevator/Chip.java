package com.techelevator;

import com.techelevator.Inventory;

import java.math.BigDecimal;

public class Chip extends Inventory {

    public Chip(String slotLocation, String product, BigDecimal price, String type) {
        super(slotLocation, product,price,"Chip", "Crunch Crunch, Yum!");
    }
}
