package com.shopify.shopify.Helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.shopify.shopify.R;

public class MovePage {


    public static  void move(Context context1, Class classname){
        context1.startActivity(new Intent(context1,classname));
    }

    public static void showtoast(String message,Context context){
        Toast toast = Toast.makeText(context,
                message + "...!!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 250);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        toast.getView().setBackgroundResource(R.drawable.buttondesign);
        toast.getView().setElevation(10);
        toast.getView().setSoundEffectsEnabled(true);
        toast.getView().setPadding(10, 10, 10, 10);
        v.setTextColor(Color.parseColor("#FFFFFF"));
        v.setTextSize(16);
        v.setGravity(Gravity.CENTER);
        v.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        toast.show();
    }
}
