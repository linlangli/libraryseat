package io.github.grooters.libraryseat.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.grooters.libraryseat.R;
import io.github.grooters.libraryseat.api.IpAddress;
import io.github.grooters.libraryseat.base.UserStorage;
import io.github.grooters.lller.tool.Toaster;

/**
 * Create by 李林浪 in 2018/10/22
 * Elegant Code...
 */
public class ChangeIpDialog extends Dialog implements View.OnClickListener{
    private static final String TAG=ChangeIpDialog.class.getSimpleName();
    private EditText ipEdit;
    private Context context;
    public ChangeIpDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ip);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button changeButton=findViewById(R.id.butt_confirm);
        ipEdit=findViewById(R.id.edit_ip);
        changeButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String ip=ipEdit.getText().toString();
        String regex=".*[a-zA-Z]+.*";
        Matcher matcher= Pattern.compile(regex).matcher(ip);
        if(matcher.matches()){
            ip="http://"+ip+".natappfree.cc";
            IpAddress.URL=ip;
            Log.i(TAG,ip);
        }else {
            IpAddress.IP=ip;
        }
        UserStorage.setIP(context,ip);
        Toaster.showShort(context,"修改成功");
    }
}
