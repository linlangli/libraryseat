package io.github.grooters.lller.tool;

import android.Manifest;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Create by 李林浪 in 2018/9/21
 * Elegant Code...
 */
public class StreaMer {

    private static final String TAG=StreaMer.class.getSimpleName();

    private StreaMer(){ }

    private static class SingletonHolder{
        private static final StreaMer streaMer=new StreaMer();
    }
    public static StreaMer getStreaMer(){
        return SingletonHolder.streaMer;
    }

    public void outTxt(Context context,final String path,final String name,final String content){
        PermissioNer.getLllPer().requestPer(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, 1, new PermissioNer.LllPermissionInter() {
            @Override
            public void execute() {
                try {
                    File dir=new File(path);
                    if(!dir.exists()){
                        Log.i(TAG,"!dir.exists()");
                        dir.mkdir();
                    }
                    File file=new File(path+"/"+name);
                    if(file.exists()){
                        file.delete();
                    }
                    Log.i(TAG,file.getAbsolutePath());
                    file.createNewFile();
                    PrintStream out=new PrintStream(new FileOutputStream(file));
                    out.print(content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String inText(String name){
        try {
            File file=new File(name);
            if(!file.exists()){
                Log.i(TAG,"file is not exit");
            }
            BufferedReader reader=new BufferedReader(new FileReader(file));
            return  reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int deleteFile(String name){
        File file=new File(name);
        if(file.exists()){
            file.delete();
            return 1;
        }else{
            return 0;
        }
    }

    public void outFile(Context context, final String path, final String name, final InputStream stream){
        try {
            File dir=new File(path);
            if(!dir.exists()){
                Log.i(TAG,"!dir.exists()");
                dir.mkdir();
            }
            File file=new File(path+"/"+name);
            if(file.exists()){
                file.delete();
            }
            Log.i(TAG,file.getAbsolutePath());
            file.createNewFile();
            FileOutputStream outputStream=new FileOutputStream(file);
            byte[] bytes=new byte[1024];
            int len=0;
            while((len=stream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
