package com.pos.yza.yzapos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.pos.yza.yzapos.adminoptions.AdminOptionsActivity;

import com.pos.yza.yzapos.data.source.TransactionsRepository;
import com.pos.yza.yzapos.managetransactions.ManageTransactionsActivity;

import com.pos.yza.yzapos.newtransaction.NewTransactionActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private final String TAG = "MAIN_ACTIVITY";
    private TransactionsRepository mTransactionsRepository;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTransactionsRepository = Injection.provideTransactionsRepository(this);
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(
                this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void newTransaction(View view){
        Intent intent = new Intent(getApplicationContext(), NewTransactionActivity.class);
        startActivity(intent);
    }

    public void admin(View view){
        showDialog(this);

    }

    public void manageTransactions (View view) {
        Intent intent = new Intent(getApplicationContext(), ManageTransactionsActivity.class);
        startActivity(intent);

    }

    private void startAdminOptions() {
        Intent intent = new Intent(getApplicationContext(), AdminOptionsActivity.class);
        startActivity(intent);
    }

    private void showDialog(Context context) {
        Log.i(TAG, "showing dialog");
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.password_prompt, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.enter_password);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = promptsView.findViewById(R.id.password);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Go",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                /** DO THE METHOD HERE WHEN PROCEED IS CLICKED*/
                                String user_text = (userInput.getText()).toString();

                                /** CHECK FOR USER'S INPUT **/
                                if (user_text.equals("bulaklaksabukid"))
                                {
                                    Log.d(user_text, "HELLO THIS IS THE MESSAGE CAUGHT :)");
                                    startAdminOptions();

                                }
                                else{
                                    Log.d(user_text,"input is wrong");
                                    String message = "The password you have entered is incorrect.";
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle(message);
                                    builder.setNegativeButton("Cancel", null);
                                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            showDialog(context);
                                        }
                                    });
                                    builder.create().show();

                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.dismiss();
                            }

                        }

                );

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public void generateReport(View view) {
        datePickerDialog.show();
    }

    public void onDateSet(DatePicker picker, int year, int month, int day) {
        Log.i("sendReport", "called");
        // month - zero based
        mTransactionsRepository.sendReport(SessionStorage.getBranch(), year, month + 1, day);
    }

}
