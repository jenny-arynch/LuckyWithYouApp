package com.androidtutorialshub.LuckyWithYou.helpers;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog;

public class EndOfGameDialog extends DialogFragment {

    //Supply keys for the Bundle
    public static final String TITLE_ID = "title";
    public static final String MESSAGE_ID = "message";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Get supplied title and message body.
        Bundle messages = getArguments();

        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
       // builder.setPositiveButton(context.getString(R.string.error_ok_button_text), null);

        if (messages != null) {
            //Add the arguments. Supply a default in case the wrong key was used, or only one was set.
            builder.setTitle(messages.getString(TITLE_ID, "Sorry"));
            builder.setMessage(messages.getString(MESSAGE_ID, "There was an error."));
        } else {
            //Supply default text if no arguments were set.
            builder.setTitle("Sorry");
            builder.setMessage("There was an error.");
        }

        AlertDialog dialog = builder.create();
        return dialog;
    }
}