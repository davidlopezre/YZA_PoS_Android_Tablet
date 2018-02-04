package com.pos.yza.yzapos.data.representations;

import com.pos.yza.yzapos.data.source.remote.TransactionsRemoteDataSource;

import java.util.HashMap;

/**
 * Created by Dalzy Mendoza on 16/1/18.
 */

public class LineItem {
    int lineItemId;
    int quantity;
    double amount;
    Transaction transaction;
//    Product product;
    int productId;

    public LineItem(int quantity, double amount,
                    Transaction transaction, int productId){
        this.lineItemId = -1;
        this.quantity = quantity;
        this.amount = amount;
        this.transaction = transaction;
        this.productId = productId;
    }

    public LineItem(int lineItemId, int quantity, double amount,
                    Transaction transaction, int productId){
        this.lineItemId = lineItemId;
        this.quantity = quantity;
        this.amount = amount;
        this.transaction = transaction;
        this.productId = productId;
    }

    public int getLineItemId() {
        return lineItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAmount() {
        return amount;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public int getProductId() {
        return productId;
    }

    public String toString(){
        return "Product: " + productId + " Qty: " + quantity + " Amt: " + amount;
    }

    public HashMap<String, String> toHashMap(){
        HashMap<String, String> toReturn = new HashMap<>();
        toReturn.put(TransactionsRemoteDataSource.LINE_ITEM_AMOUNT, this.amount + "");
        toReturn.put(TransactionsRemoteDataSource.LINE_ITEM_PRODUCT_ID, this.productId + "");
        toReturn.put(TransactionsRemoteDataSource.LINE_ITEM_QUANTITY, this.quantity + "");

        return toReturn;

    }

}
