package io.github.grooters.libraryseat.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import io.github.grooters.libraryseat.R;

/**
 * Create by 李林浪 in 2018/10/30
 * Elegant Code...
 */
public class HelpDialog extends Dialog {
    public HelpDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_help);
    }
}
