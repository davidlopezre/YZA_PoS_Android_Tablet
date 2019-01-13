package com.pos.yza.yzapos;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.pos.yza.yzapos.adminoptions.AdminOptionsActivity;

import com.pos.yza.yzapos.managetransactions.ManageTransactionsActivity;

import com.pos.yza.yzapos.newtransaction.NewTransactionActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newTransaction(View view){
        Intent intent = new Intent(getApplicationContext(), NewTransactionActivity.class);
        startActivity(intent);
    }

    public void admin(View view){
        Intent intent = new Intent(getApplicationContext(), AdminOptionsActivity.class);
        startActivity(intent);
    }

    public void manageTransactions (View view) {
        Intent intent = new Intent(getApplicationContext(), ManageTransactionsActivity.class);
        startActivity(intent);
    }

    public void showDialog()
    {

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.password_prompt, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);

//        final EditText userInput = (EditText) promptsView
//                .findViewById(R.id.user_input);
//
//
//        // set dialog message
//        alertDialogBuilder
//                .setCancelable(false)
//                .setNegativeButton("Go",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                /** DO THE METHOD HERE WHEN PROCEED IS CLICKED*/
//                                String user_text = (userInput.getText()).toString();
//
//                                /** CHECK FOR USER'S INPUT **/
//                                if (user_text.equals("oeg"))
//                                {
//                                    Log.d(user_text, "HELLO THIS IS THE MESSAGE CAUGHT :)");
//                                    Search_Tips(user_text);
//
//                                }
//                                else{
//                                    Log.d(user_text,"string is empty");
//                                    String message = "The password you have entered is incorrect." + " \n \n" + "Please try again!";
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                                    builder.setTitle("Error");
//                                    builder.setMessage(message);
//                                    builder.setPositiveButton("Cancel", null);
//                                    builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            showDialog();
//                                        }
//                                    });
//                                    builder.create().show();
//
//                                }
//                            }
//                        })
//                .setPositiveButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                dialog.dismiss();
//                            }
//
//                        }
//
//                );

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


}
