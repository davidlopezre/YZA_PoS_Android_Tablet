package com.pos.yza.yzapos.data.representations;

/**
 * Created by Dalzy Mendoza on 16/1/18.
 */

public class LineItem {
    int lineItemId;
    int quantity;
    double amount;
    Transaction transaction;
    Product product;

    public LineItem(int quantity, double amount,
                    Transaction transaction, Product product){
        this.lineItemId = -1;
        this.quantity = quantity;
        this.amount = amount;
        this.transaction = transaction;
        this.product = product;
    }
}
