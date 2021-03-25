package io.github.grooters.libraryseat.presenter.preinterfacel;

import android.content.Context;
import android.util.Log;
import java.io.IOException;

import io.github.grooters.libraryseat.api.IpAddress;
import io.github.grooters.libraryseat.api.LoginApi;
import io.github.grooters.libraryseat.base.UserStorage;
import io.github.grooters.libraryseat.model.User;
import io.github.grooters.libraryseat.presenter.preinterface.LoginPreInter;
import io.github.grooters.libraryseat.view.viewinter.LoginViewInter;
import io.github.grooters.lller.quote.okhttp.OkHttPer;

public class LoginPreImpL implements LoginPreInter{
    private LoginViewInter view;
    private static final String TAG=LoginPreImpL.class.getSimpleName();

    public LoginPreImpL(LoginViewInter view) {
        this.view = view;
    }

    @Override
    public void getUserInfo(final Context context, final String number, String pass) {
        view.loadAnimation(true);
        Log.i(TAG,"IpAddress.URL+:"+IpAddress.URL);
        Log.i(TAG,"LoginApi.GET_USER_INFO+:"+ LoginApi.GET_USER_INFO);
        OkHttPer.getOkHeePer().getByAsync(LoginApi.GET_USER_INFO+"?number="+number+"&pass="+pass, new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                view.loadAnimation(false);
                Log.i(TAG,"json:"+json);
                UserStorage.setUser(context,json);
                if(json.equals("-1")){
                    view.userError();
                }else if(json.equals("0")){
                    view.passError();
                }else if(json.equals("2")){
                    view.loginFalse();
                }else if(json.contains("department")){
                    view.loginSuccess();
                }else{
                    view.loginFalse();
                }
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                view.loadAnimation(false);
                Log.i(TAG,"errorInfo:"+errorInfo);
                view.loginFalse();
            }
        });
    }
}
