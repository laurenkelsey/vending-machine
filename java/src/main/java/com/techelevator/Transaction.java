package com.techelevator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Transaction{
    private BigDecimal balance;

    public Transaction() {

        this.balance = BigDecimal.ZERO;
    }

    public BigDecimal getBalance() {

        return balance;
    }

   public void addBalance(BigDecimal fedMoney) {
        if (fedMoney.equals(BigDecimal.valueOf(1))) {
            balance = balance.add(fedMoney);
        } else if (fedMoney.equals(BigDecimal.valueOf(5))) {
            balance = balance.add(fedMoney);
        } else if (fedMoney.equals(BigDecimal.valueOf(10))) {
            balance = balance.add(fedMoney);
        } else {
            System.out.println("Please insert $1, $5, or $10 bill");
        }
    }
    // not working
    //tried switching from Big decimal to void -> 
    public BigDecimal lowerBalance(BigDecimal price) {
        balance = balance.subtract(price);
        return balance;
    }
    public Map<String,Integer> makeChange(){
        BigDecimal quarters = BigDecimal.ZERO;
        BigDecimal dimes = BigDecimal.ZERO;
        BigDecimal nickels = BigDecimal.ZERO;
        Map<String,Integer> change = new HashMap<String, Integer>();
        quarters = balance.divide(BigDecimal.valueOf(0.25));
        change.put("Quarters", quarters.intValue());
        balance = balance.remainder(BigDecimal.valueOf(0.25));
        dimes = balance.divide(BigDecimal.valueOf(0.10));
        change.put("Dimes", dimes.intValue());
        balance = balance.remainder(BigDecimal.valueOf(0.10));
        nickels = balance.divide(BigDecimal.valueOf(0.05));
        change.put("Nickels", nickels.intValue());
        balance = balance.remainder(BigDecimal.valueOf(0.05));
        return change;
    }

}
