package io.github.grooters.libraryseat.view.activity;

import android.Manifest;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;
import org.litepal.LitePal;
import io.github.grooters.libraryseat.R;
import io.github.grooters.libraryseat.base.BaseActivity;
import io.github.grooters.libraryseat.base.UserStorage;
import io.github.grooters.libraryseat.model.Seat;
import io.github.grooters.libraryseat.model.User;
import io.github.grooters.libraryseat.presenter.preinterface.MainPreInter;
import io.github.grooters.libraryseat.presenter.preinterfacel.MainPreImpL;
import io.github.grooters.libraryseat.view.dialog.CreateQBDialog;
import io.github.grooters.libraryseat.view.dialog.HelpDialog;
import io.github.grooters.libraryseat.view.dialog.MyStateDialog;
import io.github.grooters.libraryseat.view.dialog.SitingDialog;
import io.github.grooters.libraryseat.view.viewinter.MainViewInter;
import io.github.grooters.lller.tool.PermissioNer;
import io.github.grooters.lller.tool.StreaMer;
import io.github.grooters.lller.tool.Toaster;

public class MainActivity extends BaseActivity implements MainViewInter,View.OnClickListener {
    private static final String TAG=MainActivity.class.getSimpleName();
    private MainPreInter mainPreInter;
    private Intent intent;
    private static final int REQUEST_SCANCODE=1;
    private PermissioNer permissioNer;
    private ImageButton mineImgButt,reserveImgButt,postImgButt,checkStateButt;
    private Button scanQBButt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPreInter=new MainPreImpL(this);
        mineImgButt=findViewById(R.id.imgButt_mine);
        reserveImgButt=findViewById(R.id.imgButt_reserve);
        postImgButt=findViewById(R.id.imgButt_post);
        ImageButton createQBImgButt=findViewById(R.id.imgButt_createQB);
        ImageButton helpImagButt=findViewById(R.id.imgButt_help);
        scanQBButt=findViewById(R.id.butt_scan);
        checkStateButt=findViewById(R.id.imgButt_checkState);
        mainPreInter.startAnimation();
        mineImgButt.setOnClickListener(this);
        reserveImgButt.setOnClickListener(this);
        postImgButt.setOnClickListener(this);
        createQBImgButt.setOnClickListener(this);
        scanQBButt.setOnClickListener(this);
        checkStateButt.setOnClickListener(this);
        helpImagButt.setOnClickListener(this);
    }

    @Override
    public void loadView() {
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.5f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(3000);
        mineImgButt.startAnimation(mShowAction);
        mineImgButt.setVisibility(View.VISIBLE);
        reserveImgButt.startAnimation(mShowAction);
        reserveImgButt.setVisibility(View.VISIBLE);
        postImgButt.startAnimation(mShowAction);
        postImgButt.setVisibility(View.VISIBLE);
//        createQBImgButt.startAnimation(mShowAction);
//        createQBImgButt.setVisibility(View.VISIBLE);
        checkStateButt.startAnimation(mShowAction);
        checkStateButt.setVisibility(View.VISIBLE);
        ValueAnimator animator=ValueAnimator.ofInt(1,100);
        animator.setDuration(3000);
        final IntEvaluator intEvaluator=new IntEvaluator();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                float fraction=value/200f;
                //评估出当前的宽度其设置
                scanQBButt.getLayoutParams().width=intEvaluator.evaluate(fraction, scanQBButt.getWidth(), 700);
                scanQBButt.requestLayout();
                if(scanQBButt.getWidth()>500){
                    scanQBButt.setText(getResources().getString(R.string.scan_sit));
                }
            }
        });
        animator.start();
    }

    @Override
    public Fragment createFragment() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgButt_mine:
                intent=new Intent(this,MineActivity.class);
                startActivity(intent);
                break;
            case R.id.imgButt_reserve:
                intent=new Intent(this,ReserveActivity.class);
                startActivity(intent);
                break;
            case R.id.imgButt_post:
                intent=new Intent(this,PostActivity.class);
                startActivity(intent);
                break;
            case R.id.imgButt_createQB:
                mainPreInter.createQB();
                break;
            case R.id.butt_scan:
                mainPreInter.scanQB();
                break;
            case R.id.imgButt_checkState:
                mainPreInter.checkMyState();
                break;
            case R.id.imgButt_help:
                HelpDialog helpDialog=new HelpDialog(this);
                helpDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                helpDialog.show();
                break;

        }
    }


    @Override
    public void startScanQB() {
        permissioNer= PermissioNer.getLllPer();
        permissioNer.requestPer(this, Manifest.permission.CAMERA, REQUEST_SCANCODE, new PermissioNer.LllPermissionInter() {
            @Override
            public void execute() {
                Intent intent=new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent,REQUEST_SCANCODE);
            }
        });
    }

    @Override
    public void showQBDialog() {
        CreateQBDialog createQBDialog=new CreateQBDialog(this);
        createQBDialog.show();
    }


    //返回扫码结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_SCANCODE:
                    if (data != null) {
                        User user=UserStorage.getUser(this);
                        int content = Integer.valueOf(data.getStringExtra(Constant.CODED_CONTENT));
                        SitingDialog dialog=new SitingDialog(this);
                        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                        dialog.sitingDiaPreInter.provideInfo(content,user);
                        dialog.show();
                    }
                    break;
            }
        }
    }

    @Override
    public void showStateDialog() {
        MyStateDialog myStateDialog=new MyStateDialog(this);
        myStateDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        myStateDialog.show();
    }

    @Override
    public void showSitResult(String reslut) {
        Toaster.showShort(this,reslut);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
        StreaMer.getStreaMer().deleteFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/LibrarySeat/User.ini");
    }
}
