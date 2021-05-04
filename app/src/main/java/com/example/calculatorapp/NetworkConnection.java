package com.example.calculatorapp;


import android.app.Activity;
import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NetworkConnection extends AppCompatActivity {

    public static ProgressDialog progressDialog;

    public NetworkConnection(Activity activity) {
        progressDialog = new ProgressDialog(activity);
    }



    public static void showLoader(final Activity activity) {
        if (progressDialog == null) {

            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Loading....");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
        } else {
            try {
                progressDialog.dismiss();

                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Loading....");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
