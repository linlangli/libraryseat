package io.github.grooters.libraryseat.presenter.preinterfacel;


import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import io.github.grooters.libraryseat.api.ReserveApi;
import io.github.grooters.libraryseat.api.SitingApi;
import io.github.grooters.libraryseat.base.UserStorage;
import io.github.grooters.libraryseat.model.Seat;
import io.github.grooters.libraryseat.model.User;
import io.github.grooters.libraryseat.presenter.preinterface.SitingDiaPreInter;
import io.github.grooters.libraryseat.view.viewinter.SitingDiaViewInter;
import io.github.grooters.lller.quote.fastjson.FastJsoNer;
import io.github.grooters.lller.quote.okhttp.OkHttPer;

/**
 * Create by 李林浪 in 2018/9/17
 * Elegant Code...
 */
public class SitingDiaPreInterL implements SitingDiaPreInter {
    private static final String TAG=SitingDiaPreInterL.class.getSimpleName();
    private SitingDiaViewInter sitingDiaViewInter;
    private int seatid;
    private User user;
    private Context context;
    public SitingDiaPreInterL(Context context, SitingDiaViewInter resDiaViewInter) {
        this.sitingDiaViewInter=resDiaViewInter;
        this.context=context;
    }
    @Override
    public void requestSeat(int time) {
//        Map<String,String> map=new HashMap<>();
//        map.put("JSON",json);
//        OkHttPer.getOkHeePer().postByOk(SitingApi.REQUEST_SIT_URL, map, new OkHttPer.CallBackOKInter() {
//            @Override
//            public void onResponse(String json) throws IOException {
//                sitingDiaViewInter.showResult(json);
//            }
//            @Override
//            public void onFailure(String errorInfo, IOException e) {
//                sitingDiaViewInter.showResult("服务器异常，请稍后再试");
//            }
//        });
        if(time<10||time>720){
            sitingDiaViewInter.showResult("有效值为10-720");
            return;
        }
        OkHttPer.getOkHeePer().getByAsync(SitingApi.REQUEST_SIT_URL+"?seatid="+seatid+"&usernumber="+user.getNumber()+"&mins="+time, new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                if (json.equals("0")){
                    sitingDiaViewInter.showResult("上座失败");
                }else if(json.equals("-1")){
                    sitingDiaViewInter.showResult("该座位不存在");
                }else{
                    User user=new User();
                    user=(User)FastJsoNer.getFastJsoNer().toObject(json,user);
                    UserStorage.setSeat(context,String.valueOf(user.getSeat_id()));
                    UserStorage.setUser(context,json);
                    sitingDiaViewInter.showResult("上座成功");
                }
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                sitingDiaViewInter.showResult("服务器异常，请稍后再试");
            }
        });
    }
    @Override
    public void provideInfo(int seatid,User user) {
        this.seatid=seatid;
        Log.i(TAG,"seatid:"+seatid);
        this.user=user;
    }

}
