package com.pos.yza.yzapos.data.representations;

import com.pos.yza.yzapos.data.source.remote.TransactionsRemoteDataSource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Dalzy Mendoza on 16/1/18.
 */

public class Payment {
    public enum State {OK, REFUND};

    private int paymentId;
    private Date dateTime;
    private double amount;
//  private Branch branch;
    private int branchId;
    private Transaction transaction;
    private State state;

    public Payment(double amount, int branchId, Transaction transaction) {
        this.paymentId = -1;
        this.dateTime = new Date(0,0,0);
        this.amount = amount;
        this.branchId = branchId;
        this.transaction = transaction;
        this.state = State.OK;
    }


    public Payment(Date dateTime, double amount, int branchId, Transaction transaction) {
        this.paymentId = -1;
        this.dateTime = dateTime;
        this.amount = amount;
        this.branchId = branchId;
        this.transaction = transaction;
        this.state = State.OK;
    }

    public Payment(int paymentId, Date dateTime, double amount, int branchId, Transaction transaction, State state) {
        this.paymentId = paymentId;
        this.dateTime = dateTime;
        this.amount = amount;
        this.branchId = branchId;
        this.transaction = transaction;
        this.state = state;
    }

    public static State getState(String state){
        if(state.equals("OK")){
            return State.OK;
        }
        if(state.equals("REFUND")){
            return State.REFUND;
        }
        return null;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public int getBranchId() {
        return branchId;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public State getState() {
        return state;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String toString(){
        return "Id: " + paymentId + " Amount: " + amount + " Branch: " + branchId;
    }

    public HashMap<String,String> toHashMap(){
        HashMap<String, String> toReturn = new HashMap<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        toReturn.put(TransactionsRemoteDataSource.PAYMENT_DATE_TIME, df.format(dateTime));
        toReturn.put(TransactionsRemoteDataSource.PAYMENT_AMOUNT, amount + "");
        toReturn.put(TransactionsRemoteDataSource.PAYMENT_BRANCH_ID, branchId + "");

        // default doesnt include state; assumes state is OK for this function

        return toReturn;
    }
}
