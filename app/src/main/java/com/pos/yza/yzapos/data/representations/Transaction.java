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
    private Branch branch;
    private ArrayList<LineItem> lineItems;
    private ArrayList<Payment> payments;

    private enum Status {OK, CANCEL, REFUND};

    public Transaction(int transactionId, String clientFirstName,
                       String clientSurname, Branch branch){
        this.transactionId = transactionId;
        this.clientFirstName = clientFirstName;
        this.clientSurname = clientSurname;
        this.branch = branch;
        this.lineItems = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    public Transaction(int transactionId, String clientFirstName,
                       String clientSurname, Branch branch,
                       ArrayList<LineItem> lineItems,
                       ArrayList<Payment> payments){
        this.transactionId = transactionId;
        this.clientFirstName = clientFirstName;
        this.clientSurname = clientSurname;
        this.branch = branch;
        this.lineItems = lineItems;
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

    public Branch getBranch() {
        return branch;
    }

    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }
}
