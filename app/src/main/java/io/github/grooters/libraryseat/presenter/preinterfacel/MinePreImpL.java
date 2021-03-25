package io.github.grooters.libraryseat.presenter.preinterfacel;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import java.io.IOException;
import io.github.grooters.libraryseat.api.UserInfoApi;
import io.github.grooters.libraryseat.base.UserStorage;
import io.github.grooters.libraryseat.model.User;
import io.github.grooters.libraryseat.presenter.preinterface.MinePreInter;
import io.github.grooters.libraryseat.view.viewinter.MineViewInter;
import io.github.grooters.lller.quote.fastjson.FastJsoNer;
import io.github.grooters.lller.quote.okhttp.OkHttPer;
import io.github.grooters.lller.tool.StreaMer;

public class MinePreImpL implements MinePreInter{
    private MineViewInter mineViewInter;
    private Context context;
    private static final String TAG=MinePreImpL.class.getSimpleName();
    private final int SELECT_PICTURE_FROM_ALBUM=1;

    public MinePreImpL(MineViewInter mineViewInter, Context context) {
        this.mineViewInter=mineViewInter;
        this.context=context;
    }
    @Override
    public void getUser() {
        User user=new User();
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String name=Environment.getExternalStorageDirectory().getAbsolutePath()+"/LibrarySeat/User.ini";
            String json=StreaMer.getStreaMer().inText(name);
            Log.i(TAG,"json:"+json);
            user= (User)FastJsoNer.getFastJsoNer().toObject(json,user);
        }
        mineViewInter.setInfo(user);
    }

    @Override
    public void editNickName(String number,String nickName) {
        Log.d(TAG,"url:"+UserInfoApi.EDIT_NICKNAME+"?number="+number+"&nickName="+nickName);
        OkHttPer.getOkHeePer().getByAsync(UserInfoApi.EDIT_NICKNAME+"?number="+number+"&nickName="+nickName, new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                Log.d(TAG,"json:"+json);
                if(json.equals("0")){
                    mineViewInter.showResult("编辑失败");
                }else {
                    mineViewInter.showResult("编辑成功");
                    UserStorage.setUser(context,json);
                    User user=new User();
                    mineViewInter.setInfo((User)FastJsoNer.getFastJsoNer().toObject(json,user));
                    mineViewInter.showResult("");
                }
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                Log.d(TAG,"errorInfo:"+errorInfo);
                mineViewInter.showResult("服务器异常："+errorInfo);
            }
        });
    }

    @Override
    public void editSignature(String number,String signature) {
        OkHttPer.getOkHeePer().getByAsync(UserInfoApi.EDIT_SIGNATURE+"?number="+number+"&signature="+signature, new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                if(json.equals("0")){

                    mineViewInter.showResult("编辑失败");
                }else {
                    mineViewInter.showResult("编辑成功");
                    UserStorage.setUser(context,json);
                    User user=new User();
                    mineViewInter.setInfo((User)FastJsoNer.getFastJsoNer().toObject(json,user));
                }
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                mineViewInter.showResult("服务器异常："+errorInfo);
            }
        });
    }

    @Override
    public String loadImage(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SELECT_PICTURE_FROM_ALBUM:
                Uri uri=data.getData();
                Cursor cursor=context.getContentResolver().query(uri,new String[]{MediaStore.Images.Media.DATA },null,null,null,null);
                if(cursor==null){
                    return "";
                }
                cursor.moveToFirst();
//                Log.d(TAG,cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                return cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            default:
                return "";
        }
    }
}