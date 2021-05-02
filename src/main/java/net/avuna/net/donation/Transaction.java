package net.avuna.net.donation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {

    private String username;
    private String itemName;
    private int productId;
    private int itemAmount;
    private double price;
}
