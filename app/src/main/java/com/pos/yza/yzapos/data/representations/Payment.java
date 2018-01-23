package com.pos.yza.yzapos.data.representations;

import java.time.LocalDateTime;

/**
 * Created by Dalzy Mendoza on 16/1/18.
 */

public class Payment {
    public enum State {OK, REFUND};
    int paymentId;
    LocalDateTime dateTime;
    double amount;
    Branch branch;
    Transaction transaction;


}
