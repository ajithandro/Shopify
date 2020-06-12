package com.shopify.shopify.Helper;

import android.app.ProgressDialog;
import android.content.Context;

public class ShowProgress {
    private static ProgressDialog  progressDialog;

    public static void show(String message,Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void dismiss(){
        progressDialog.dismiss();
    }

}
