package com.pos.yza.yzapos.data.representations;

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

//    public LineItem(int quantity, double amount,
//                    Transaction transaction, Product product){
//        this.lineItemId = -1;
//        this.quantity = quantity;
//        this.amount = amount;
//        this.transaction = transaction;
//        this.product = product;
//    }
//
//    public LineItem(int lineItemId, int quantity, double amount,
//                    Transaction transaction, Product product){
//        this.lineItemId = lineItemId;
//        this.quantity = quantity;
//        this.amount = amount;
//        this.transaction = transaction;
//        this.product = product;
//    }

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

    public String toString(){
        return "Product: " + productId + " Qty: " + quantity + " Amt: " + amount;
    }

}
