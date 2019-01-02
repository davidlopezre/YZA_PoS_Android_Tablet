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
    private int staffId;
    private Status status;
    private double amount;

    public enum Status {OK, CANCEL, REFUND};

    public Transaction(int transactionId){
        this.transactionId = transactionId;
        this.clientFirstName = "";
        this.clientSurname = "";
        this.dateTime = new Date(0,0,0);
        this.branchId = -1;
        this.lineItems = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.amount = 0;
        this.staffId = -1;
        this.status = Status.OK;
    }

    public Transaction(String state){
        this.transactionId = -1;
        this.clientFirstName = "";
        this.clientSurname = "";
        this.dateTime = new Date(0,0,0);
        this.branchId = -1;
        this.lineItems = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.amount = 0;
        this.staffId = -1;
        this.status = Transaction.getStatus(state);
    }

    public Transaction(String clientFirstName, String clientSurname, int branchId){
        this.transactionId = -1;
        this.clientFirstName = clientFirstName;
        this.clientSurname = clientSurname;
        this.dateTime = new Date(0,0,0);
        this.branchId = branchId;
        this.lineItems = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.amount = 0;
        this.staffId = -1;
        this.status = Status.OK;
    }

    public Transaction(String clientFirstName, String clientSurname, int branchId, int staffId){
        this.transactionId = -1;
        this.clientFirstName = clientFirstName;
        this.clientSurname = clientSurname;
        this.dateTime = new Date(0,0,0);
        this.branchId = branchId;
        this.lineItems = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.amount = 0;
        this.staffId = staffId;
        this.status = Status.OK;
    }

    public Transaction(int transactionId, String clientFirstName,
                       String clientSurname, int branchId){
        this.transactionId = transactionId;
        this.clientFirstName = clientFirstName;
        this.clientSurname = clientSurname;
        this.dateTime = new Date(0,0,0);
        this.branchId = branchId;
        this.lineItems = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.amount = 0;
        this.staffId = -1;
        this.status = Status.OK;
    }

    public Transaction(int transactionId, String clientFirstName,
                       String clientSurname, int branchId, double amount){
        this.transactionId = transactionId;
        this.clientFirstName = clientFirstName;
        this.clientSurname = clientSurname;
        this.dateTime = new Date(0,0,0);
        this.branchId = branchId;
        this.lineItems = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.amount = amount;
        this.staffId = -1;
        this.status = Status.OK;
    }

    public Transaction(int transactionId, String clientFirstName,
                       String clientSurname, int branchId,
                       Date dateTime, double amount){
        this.transactionId = transactionId;
        this.clientFirstName = clientFirstName;
        this.clientSurname = clientSurname;
        this.dateTime = dateTime;
        this.branchId = branchId;
        this.lineItems = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.amount = amount;
        this.staffId = -1;
        this.status = Status.OK;
    }

    public Transaction(int transactionId, String clientFirstName,
                       String clientSurname, int branchId,
                       ArrayList<LineItem> lineItems,
                       ArrayList<Payment> payments, double amount){
        this.transactionId = transactionId;
        this.clientFirstName = clientFirstName;
        this.clientSurname = clientSurname;
        this.dateTime = dateTime;
        this.branchId = branchId;
        this.lineItems = lineItems;
        this.payments = payments;
        this.staffId = -1;
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
        this.lineItems = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.status = status;
        this.staffId = -1;
        this.amount = amount;
    }

    public void setLineItems(ArrayList<LineItem> lineItems){
        this.amount = 0;

        for(LineItem lineItem : lineItems){
            this.amount += lineItem.getAmount();
        }

        this.lineItems = lineItems;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }

    public void addLineItem(LineItem lineItem){
        this.amount += lineItem.getAmount();
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

    public int getStaffId() {
        return staffId;
    }

    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public double getAmount() {
        double amount = 0;

        for(LineItem lineItem : lineItems){
            amount += lineItem.getAmount();
        }

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
