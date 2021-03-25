package io.github.grooters.libraryseat.base;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import io.github.grooters.libraryseat.model.Seat;
import io.github.grooters.libraryseat.model.User;
import io.github.grooters.lller.quote.fastjson.FastJsoNer;
import io.github.grooters.lller.tool.StreaMer;
import io.github.grooters.lller.tool.Toaster;

/**
 * Create by 李林浪 in 2018/10/21
 * Elegant Code...
 */
public class UserStorage {
    private static final String TAG = UserStorage.class.getSimpleName();

    public static User getUser(Context context){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String name=Environment.getExternalStorageDirectory().getAbsolutePath()+"/LibrarySeat/User.ini";
            String json= StreaMer.getStreaMer().inText(name);
            Log.i(TAG,"json:"+json);
            User user=new User();
            return (User) FastJsoNer.getFastJsoNer().toObject(json,user);
        }else {
            Toaster.showShort(context,"未发现有效存储路径");
            return null;
        }
    }
    public static void setUser(Context context,String json){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/LibrarySeat";
            StreaMer.getStreaMer().outTxt(context,path,"User.ini",json);
        }
    }

    public static String getIP(Context context){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String name=Environment.getExternalStorageDirectory().getAbsolutePath()+"/LibrarySeat/IP.ini";
            String ip= StreaMer.getStreaMer().inText(name);
            Log.i(TAG,"ip:"+ip);
            return ip;
        }else {
            Toaster.showShort(context,"未发现有效存储路径");
            return null;
        }
    }
    public static void setIP(Context context,String ip){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/LibrarySeat";
            StreaMer.getStreaMer().outTxt(context,path,"IP.ini",ip);
        }
    }

    public static String[] getAccount(Context context){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String name=Environment.getExternalStorageDirectory().getAbsolutePath()+"/LibrarySeat/Account.ini";
            String content= StreaMer.getStreaMer().inText(name);
            Log.i(TAG,"account:"+content);
            return content.split(":");
        }else {
            Toaster.showShort(context,"未发现有效存储路径");
            return null;
        }
    }
    public static void setAccount(Context context,String[] account){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/LibrarySeat";
            StreaMer.getStreaMer().outTxt(context,path,"Account.ini",account[0]+":"+account[1]);
        }
    }

    public static Seat getSeat(Context context){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String name=Environment.getExternalStorageDirectory().getAbsolutePath()+"/LibrarySeat/Seat.ini";
            String json= StreaMer.getStreaMer().inText(name);
            Log.i(TAG,"json:"+json);
            Seat seat=new Seat();
            return (Seat) FastJsoNer.getFastJsoNer().toObject(json,seat);
        }else {
            Toaster.showShort(context,"未发现有效存储路径");
            return null;
        }
    }
    public static void setSeat(Context context,String json){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/LibrarySeat";
            StreaMer.getStreaMer().outTxt(context,path,"Seat.ini",json);
        }
    }
}
