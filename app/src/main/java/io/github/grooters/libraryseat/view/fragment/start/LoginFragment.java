package io.github.grooters.libraryseat.view.fragment.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.grooters.libraryseat.R;
import io.github.grooters.libraryseat.api.IpAddress;
import io.github.grooters.libraryseat.base.UserStorage;
import io.github.grooters.libraryseat.presenter.preinterface.LoginPreInter;
import io.github.grooters.libraryseat.presenter.preinterfacel.LoginPreImpL;
import io.github.grooters.libraryseat.view.activity.MainActivity;
import io.github.grooters.libraryseat.view.dialog.ChangeIpDialog;
import io.github.grooters.libraryseat.view.viewinter.LoginViewInter;
import io.github.grooters.lller.quote.jumpingbeans.JumpinGer;
import io.github.grooters.lller.tool.Toaster;

public class LoginFragment extends Fragment implements LoginViewInter,View.OnClickListener{
    private static final String TAG=LoginFragment.class.getSimpleName();
    private View view;
    private LoginPreInter loginPreInter;
    private EditText userEdit,passEdit;
    private Button loginButton;
    private ImageView loginImage;
    private int count;
    private TextView loginText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_login,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginButton=view.findViewById(R.id.butt_login);
        userEdit=view.findViewById(R.id.edit_user);
        passEdit=view.findViewById(R.id.edit_pass);
        loginImage=view.findViewById(R.id.img_login);
        loginText=view.findViewById(R.id.text_login);
        loginButton.setOnClickListener(this);
        loginImage.setOnClickListener(this);
        loginPreInter=new LoginPreImpL(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.butt_login:
                String ip=UserStorage.getIP(getContext());
                if(ip!=null){
                    IpAddress.URL= ip;
                    Log.i(TAG,"url:"+ip);
                }else {
                    Log.i(TAG,"ip==nul");
                }
                loginButton.setEnabled(false);
                loginPreInter.getUserInfo(getContext(),userEdit.getText().toString(),passEdit.getText().toString());
                break;
            case R.id.img_login:
                ++count;
                if(count==5){
                    ChangeIpDialog changeIpDialog=new ChangeIpDialog(getContext());
                    changeIpDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    changeIpDialog.show();
                    count=0;
                }
                break;
        }
    }

    @Override
    public void userError() {
        Toaster.showShort(getContext(),"账号不存在");
    }

    @Override
    public void passError() {
        Toaster.showShort(getContext(),"密码错误");
    }

    @Override
    public void loginSuccess() {
        Toaster.showShort(getContext(),"登录成功");
        Intent intent=new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void loginFalse() {
        Toaster.showShort(getContext(),"服务器故障，请稍后再试");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginButton.setEnabled(true);
            }
        });
    }

    @Override
    public void loadAnimation(final boolean b) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(b){
                    loginText.setVisibility(View.VISIBLE);
                    JumpinGer.getJumpinGer().setJump(loginText);
                }else{
                    loginText.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}
