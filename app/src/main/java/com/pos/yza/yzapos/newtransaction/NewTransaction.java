package com.pos.yza.yzapos.newtransaction;

import android.util.Log;

import com.pos.yza.yzapos.data.representations.LineItem;
import com.pos.yza.yzapos.data.representations.Payment;
import com.pos.yza.yzapos.data.representations.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dlolpez on 6/2/18.
 */

public class NewTransaction {
    List<LineItem> cart;
    double paymentAmount;
    HashMap<String,String> customerDetails;
    int staffId;

    public NewTransaction() {
        cart = new ArrayList<>();
        paymentAmount = 0.0;
    }

    public void setCart(Object data) {

        ArrayList<LineItem> lineItems = (ArrayList) data;

        Log.i("NewTransactionClass", "Adding to productsInCart!");
        for (LineItem l : lineItems) {
            cart.add(l);
            Log.i("NewTransactionClass", l.toString());
        }
    }

    public void setPayment(Object data) {
        paymentAmount = (double) data;
        Log.i("NewTransactionClass", "successfully set payment!");
        Log.i("NewTransactionClass", Double.toString(paymentAmount));
    }

    public void setCustomerDetails(Object data) {
        
        this.customerDetails = (HashMap)data;
        Log.i("NewTransactionClass", "successfully set customer details!");
        Log.i("NewTransactionClass", customerDetails.get("firstname") + "");
        Log.i("NewTransactionClass", customerDetails.get("surname") + "");
    }

    public void setStaff(int data){
        this.staffId =  data;
        Log.i("NewTransactionClass", "successfully set staff!");
        Log.i("NewTransactionClass", Integer.toString(staffId));
    }

    public Transaction createTransaction (){
        if (isComplete()) {
            String firstName = customerDetails.get("firstname");
            String surname = customerDetails.get("surname");

            Transaction transaction = new Transaction(firstName, surname, 1, staffId);

            if (paymentAmount > getTotalDue()) {
                paymentAmount = getTotalDue();
            }

            Payment payment = new Payment(new Date(), paymentAmount, 1, transaction);

            transaction.setLineItems(new ArrayList<LineItem>(processCart(transaction)));
            transaction.addPayment(payment);

            Log.i("NewTransactionClass", "successfully created transaction!");
            Log.i("NewTransactionClass", transaction.toString());

            return transaction;
        }
        return null;
    }

    public List<LineItem> processCart (Transaction transaction) {

        Log.i("NewTransactionClass", "Creating line item arraylist!");

        for (LineItem i : cart) {
           i.setTransaction(transaction);
            Log.i("NewTransactionClass", "Processed " + i.toString() );
        }

        return cart;
    }

    public boolean isComplete(){
//      return (productsInCart != null) && (paymentAmount <= 0.0) && (customerDetails != null);
        return true;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public int getNumberOfItems() {
        int numberOfItems = 0;
        for (LineItem i: cart) {
            numberOfItems += i.getQuantity();
        }
        return numberOfItems;
    }

    public double getTotalDue() {
        double totalDue = 0;
        for (LineItem i: cart) {
            totalDue += i.getAmount();
        }
        return totalDue;
    }

    public double getChange() {
        return paymentAmount - getTotalDue();
    }
}
