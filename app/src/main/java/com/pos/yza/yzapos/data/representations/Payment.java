package com.pos.yza.yzapos.data.representations;

import java.util.Date;

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


    public Payment(Date dateTime, double amount, int branchId, Transaction transaction) {
        this.paymentId = -1;
        this.dateTime = dateTime;
        this.amount = amount;
        this.branchId = branchId;
        this.transaction = transaction;
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

    public String toString(){
        return "Id: " + paymentId + " Amount: " + amount + " Branch: " + branchId;
    }
}
