package com.pos.yza.yzapos.util;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.pos.yza.yzapos.R;


public class DialogFragmentUtils {
    public static void giveFeedback(DialogFragment df, Context context, String subject){
        String realSubject = context.getString(R.string.something_created);
        Toast.makeText(context, subject + " " + realSubject , Toast.LENGTH_LONG)
                .show();
        df.dismiss();
    }
}
