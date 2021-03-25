package io.github.grooters.libraryseat.presenter.preinterfacel;

import android.content.Context;
import android.util.Log;

import org.litepal.LitePal;

import java.io.IOException;

import io.github.grooters.libraryseat.api.SeatInfoApi;
import io.github.grooters.libraryseat.api.SitingApi;
import io.github.grooters.libraryseat.base.UserStorage;
import io.github.grooters.libraryseat.model.Seat;
import io.github.grooters.libraryseat.model.State;
import io.github.grooters.libraryseat.model.User;
import io.github.grooters.libraryseat.presenter.preinterface.MyStaDiaPreInter;
import io.github.grooters.libraryseat.view.viewinter.MyStaDiaViewInter;
import io.github.grooters.lller.quote.fastjson.FastJsoNer;
import io.github.grooters.lller.quote.okhttp.OkHttPer;

/**
 * Create by 李林浪 in 2018/9/27
 * Elegant Code...
 */
public class MyStaDiaPreImpL implements MyStaDiaPreInter{
    private MyStaDiaViewInter viewInter;
    private Context context;
    private static final String TAG=MyStaDiaPreImpL.class.getSimpleName();

    public MyStaDiaPreImpL(Context context,MyStaDiaViewInter viewInter) {
        this.viewInter = viewInter;
        this.context=context;
    }

    @Override
    public void requestLeave() {
        User user=UserStorage.getUser(context);
        OkHttPer.getOkHeePer().getByAsync(SitingApi.REQUEST_LEAVE_SIT_URL + "?usernumber="+user.getNumber(), new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                if(json.equals("0")){
                    viewInter.showResult("当前未上座");
                }else{
                    viewInter.showResult("离座成功");
                    viewInter.refreshData();
                    Log.d(TAG,"json:"+json);
                    UserStorage.setUser(context,json);
                }
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                viewInter.showResult("服务器异常");
            }
        });
    }

    @Override
    public void requestState() {
        User user=UserStorage.getUser(context);
        OkHttPer.getOkHeePer().getByAsync(SeatInfoApi.GET_STATE_URL + "?usernumber="+user.getNumber(), new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                State state=new State();
                if(!json.equals("0")){
                    state=(State)FastJsoNer.getFastJsoNer().toObject(json,state);
                    if(state.getSeatId()!=0){
                        viewInter.initView(state.getName(),state.getTotalTime(),state.getSeatId(),state.getLeaveTime());
                    }
                }
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                viewInter.showResult("服务器异常");
            }
        });
    }

    @Override
    public void requestPause() {
        User user=UserStorage.getUser(context);
        Log.i(TAG,"seatd:"+user.getSeat_id());
        OkHttPer.getOkHeePer().getByAsync(SitingApi.REQUEST_PAUSE_URL + "?seatid="+user.getSeat_id(), new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                if(json.equals("1")){
                    viewInter.showResult("请在15分钟内返回座位");
                }else{
                    viewInter.showResult("当前未上座");
                }
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                viewInter.showResult("服务器异常");
            }
        });
    }

    @Override
    public void canclePause() {
        User user=UserStorage.getUser(context);
        OkHttPer.getOkHeePer().getByAsync(SitingApi.REQUEST_CANCLE_PAUSE_URL + "?seatid="+user.getSeat_id(), new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                if(json.equals("1")){
                    viewInter.showResult("取消暂离成功");
                }else{
                    viewInter.showResult("当前未上座");
                }
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                viewInter.showResult("服务器异常");
            }
        });
    }
}
