package com.techelevator;

import java.math.BigDecimal;

public class Drink extends Inventory {

    public Drink(String slotLocation, String product, BigDecimal price, String type) {
        super (slotLocation, product,price,"Drink", "Glug Glug, Yum!");


    }
}
