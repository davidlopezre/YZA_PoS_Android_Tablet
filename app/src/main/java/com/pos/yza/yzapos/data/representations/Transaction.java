package com.pos.yza.yzapos.data.representations;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dalzy Mendoza on 12/1/18.
 */

public final class Transaction {

    private int transactionId;
    private String clientFirstName;
    private String clientSurname;
    private Date dateTime;
//    private Branch branch;
    private int branchId;
    private ArrayList<LineItem> lineItems;
    private ArrayList<Payment> payments;
    private Status status;
    private double amount;

    public enum Status {OK, CANCEL, REFUND};

    public Transaction(int transactionId, String clientFirstName,
                       String clientSurname, int branchId, double amount){
        this.transactionId = transactionId;
        this.clientFirstName = clientFirstName;
        this.clientSurname = clientSurname;
        this.branchId = branchId;
        this.lineItems = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.amount = amount;
    }

    public Transaction(int transactionId, String clientFirstName,
                       String clientSurname, int branchId,
                       Date dateTime, double amount){
        this.transactionId = transactionId;
        this.clientFirstName = clientFirstName;
        this.clientSurname = clientSurname;
        this.branchId = branchId;
        this.lineItems = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public Transaction(int transactionId, String clientFirstName,
                       String clientSurname, int branchId,
                       ArrayList<LineItem> lineItems,
                       ArrayList<Payment> payments, double amount){
        this.transactionId = transactionId;
        this.clientFirstName = clientFirstName;
        this.clientSurname = clientSurname;
        this.branchId = branchId;
        this.lineItems = lineItems;
        this.payments = payments;
        this.amount = amount;
    }

    public Transaction(int transactionId, String clientFirstName,
                       String clientSurname, Date dateTime,
                       int branchId, Status status, double amount) {
        this.transactionId = transactionId;
        this.clientFirstName = clientFirstName;
        this.clientSurname = clientSurname;
        this.dateTime = dateTime;
        this.branchId = branchId;
        this.status = status;
        this.amount = amount;
    }

    public void setLineItems(ArrayList<LineItem> lineItems){
        this.lineItems = lineItems;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }

    public void addLineItem(LineItem lineItem){
        lineItems.add(lineItem);
    }

    public void addPayment(Payment payment){
        payments.add(payment);
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public int getBranchId() {
        return branchId;
    }

    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public double getAmount() {
        return amount;
    }

    public static Status getStatus(String status){
        if(status.equals("OK")){
            return Status.OK;
        }
        else if(status.equals("REFUND")){
            return Status.REFUND;
        }
        else if(status.equals("CANCEL")){
            return Status.CANCEL;
        }
        else
            return null;
    }

    public String toString(){
        String toReturn = "Id: " + transactionId + " Client: " + clientFirstName +
                          " " + clientSurname + "\n";

        toReturn += "Items: ";
        for(LineItem l : lineItems){
            toReturn += l.toString() + "\n";
        }

        toReturn += "Payments: ";
        for(Payment p : payments){
            toReturn += p.toString() + "\n";
        }

        return toReturn;
    }
}
