package com.pos.yza.yzapos;

import com.pos.yza.yzapos.data.representations.LineItem;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.util.Formatters;

import java.util.Locale;

public class ReceiptLayouts {

    private static final int LINELENGTH = 32;
    public static String getHeader() {
        String header = "\nYZA COPY SYSTEMS AND TRADING\n";
        header += SessionStorage.getBranch().getAddress();
        header += "\n\n";
        return header;
    }
    public static String getLayoutTransaction(Transaction transaction) {
        String staff = SessionStorage.getStaffById(transaction.getStaffId()).getName();
        String lineItemsString = "********************************\n\n";
        for(LineItem lineItem : transaction.getLineItems()) {
            String lineItemString = "";

            Product product = SessionStorage.getProduct(lineItem.getProductId());
            String productName = product.getName();
            Double productUnitPrice = product.getUnitPrice();

            lineItemString += String.format(Locale.getDefault(),
                                    "%d %s @ %.2f",
                                            lineItem.getQuantity(),
                                            productName,
                                            productUnitPrice);

            lineItemString += "\n";
            String amount = String.format("%.2f", lineItem.getAmount());
            lineItemString += String.format(Locale.getDefault(),
                                            "%" + LINELENGTH + "s",
                                            amount);
            lineItemsString += lineItemString + "\n";
        }

        String totalAmount = String.format("%.2f", transaction.getAmount());
        lineItemsString += String.format("\n%" + LINELENGTH + "s\n", "TOTAL: Php " + totalAmount);
        lineItemsString += "********************************\n\n\n";
        return staff + "\n" + lineItemsString;
    }
}
