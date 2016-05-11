package com.miedificio.miedificio.ui;

import android.content.Context;
import android.widget.Toast;

public class ErrorsHandler {

    private Context mContext;

    public ErrorsHandler(Context context) {
        mContext = context;
    }

    public void handle(Throwable throwable) {
        Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }
}
