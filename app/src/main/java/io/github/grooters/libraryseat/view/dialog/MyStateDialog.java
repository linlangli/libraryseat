package io.github.grooters.libraryseat.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import io.github.grooters.libraryseat.R;
import io.github.grooters.libraryseat.presenter.preinterface.MyStaDiaPreInter;
import io.github.grooters.libraryseat.presenter.preinterfacel.MyStaDiaPreImpL;
import io.github.grooters.libraryseat.view.viewinter.MyStaDiaViewInter;
import io.github.grooters.lller.tool.Toaster;

/**
 * Create by 李林浪 in 2018/9/19
 * Elegant Code...
 */
public class MyStateDialog extends Dialog implements MyStaDiaViewInter,View.OnClickListener{

    private static final String TAG=MyStateDialog.class.getSimpleName();
    private TextView nameText,totalTimeText,leaveTimeText,seatIdText;
    private Context context;
    private MyStaDiaPreInter myStaDiaPreInter;

    public MyStateDialog(@NonNull Context context) {
        super(context);
        this.context=context;
        myStaDiaPreInter=new MyStaDiaPreImpL(context,this);
        myStaDiaPreInter.requestState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_state);
        nameText=findViewById(R.id.text_state_name);
        totalTimeText=findViewById(R.id.text_state_time);
        leaveTimeText=findViewById(R.id.text_state_endTime);
        seatIdText=findViewById(R.id.text_state_seatNum);
        Button leaveButton=findViewById(R.id.butt_leave);
        Button pauseButton=findViewById(R.id.butt_pause);
        Button backButton=findViewById(R.id.butt_back);
        leaveButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.butt_leave:
                myStaDiaPreInter.requestLeave();
                break;
            case R.id.butt_pause:
                myStaDiaPreInter.requestPause();
                break;
            case R.id.butt_back:
                myStaDiaPreInter.canclePause();
                break;
        }
    }

    @Override
    public void refreshData() {
        nameText.setText("");
        totalTimeText.setText("");
        leaveTimeText.setText("");
        seatIdText.setText("");
    }

    @Override
    public void showResult(String result) {
        Toaster.showShort(context,result);
    }

    @Override
    public void initView(final String name, final String totalTime, final int seatId, final String leaveTime) {
        Log.i(TAG,"initView");
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"Handler");
                nameText.setText(name);
                totalTimeText.setText(totalTime);
                leaveTimeText.setText(leaveTime);
                seatIdText.setText(String.valueOf(seatId));
            }
        });
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                Log.i(TAG,"Handler");
//                nameText.setText(name);
//                totalTimeText.setText(totalTime);
//                leaveTimeText.setText(leaveTime);
//                seatIdText.setText(seatId);
//            }
//        });
    }
}
