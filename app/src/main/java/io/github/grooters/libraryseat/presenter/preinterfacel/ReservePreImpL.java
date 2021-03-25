package io.github.grooters.libraryseat.presenter.preinterfacel;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import io.github.grooters.libraryseat.api.ReserveApi;
import io.github.grooters.libraryseat.api.SeatInfoApi;
import io.github.grooters.libraryseat.base.UserStorage;
import io.github.grooters.libraryseat.model.AllSeat;
import io.github.grooters.libraryseat.model.Seat;
import io.github.grooters.libraryseat.model.User;
import io.github.grooters.libraryseat.presenter.preinterface.ReservePreInter;
import io.github.grooters.libraryseat.view.viewinter.ReserveViewInter;
import io.github.grooters.lller.quote.fastjson.FastJsoNer;
import io.github.grooters.lller.quote.okhttp.OkHttPer;

public class ReservePreImpL implements ReservePreInter{

    private static final String TAG=ReservePreImpL.class.getSimpleName();
    private ReserveViewInter reserveViewInter;
    private List<Seat> seats;
    private AllSeat allSeat;
    private Context context;

    public ReservePreImpL(Context context, ReserveViewInter reserveViewInter) {
        this.reserveViewInter=reserveViewInter;
        this.context=context;
    }


    @Override
    public void getSeatInfo() {
        Log.i(TAG,"SeatInfoApi.GET_INFO_URL:"+SeatInfoApi.GET_INFO_URL);
        OkHttPer.getOkHeePer().getByAsync(SeatInfoApi.GET_INFO_URL, new OkHttPer.CallBackOKInter() {
            @Override
            public void onFailure(String error, IOException e) {
                Log.i(TAG,"initInfo_failure:"+error);
            }
            @Override
            public void onResponse(String json) throws IOException {
                Log.i(TAG,"json:"+json);
                allSeat=new AllSeat();
                allSeat=(AllSeat) FastJsoNer.getFastJsoNer().toObject(json,allSeat);
                seats=allSeat.getSeats();
                Log.i(TAG,"seats.size: "+seats.size());

                List<Seat[]> seatsArray=new ArrayList<>();
                Seat[] seatLine=new Seat[5];
                int i=0;
                int j=1;
                int type_temp=1;
                for(Seat seat:seats){
                    Log.i(TAG,"seat.getFloor(): "+seat.getFloor());
                    //满一行或换楼层
                    if(i>4||seat.getFloor()!=type_temp){
                        Log.i(TAG,"i: "+i);
                        seatsArray.add(seatLine);
                        type_temp=seat.getFloor();
                        seatLine=new Seat[5];
                        i=0;
                    }
                    seatLine[i]=seat;
                    //座位添加完成
                    if(j==seatsArray.size()){
                        Log.i(TAG,"j==seatsArray.size(): "+j);
                        seatsArray.add(seatLine);
                    }
                    ++i;
                    ++j;
                }
                Log.i(TAG,"seatsArray.size(): "+seatsArray.size());
                seatsArray.add(seatLine);
                reserveViewInter.initSeat(seatsArray);
            }
        });
    }

    @Override
    public void requestReser(Seat seat) {
        User user= UserStorage.getUser(context);
        OkHttPer.getOkHeePer().getByAsync(ReserveApi.REQUEST_RESERVE+"?seatid="+seat.getId()+"&usernumber="+user.getNumber(), new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                if(json.equals("1")){
                    reserveViewInter.showReserveResult("预约成功");
                    reserveViewInter.freshData();
                }else{
                    reserveViewInter.showReserveResult("预约失败");
                }
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                reserveViewInter.showReserveResult(errorInfo);
            }
        });
    }

    @Override
    public void unorderseat(int seatId) {
        User user= UserStorage.getUser(context);
        OkHttPer.getOkHeePer().getByAsync(ReserveApi.CANCLE_RESERVE+"?usernumber="+user.getNumber()+"&seatid="+seatId, new OkHttPer.CallBackOKInter() {
            @Override
            public void onResponse(String json) throws IOException {
                if(json.equals("1")){
                    reserveViewInter.showReserveResult("取消预约成功");
                    reserveViewInter.freshData();
                }else{
                    reserveViewInter.showReserveResult("取消失败");
                }
            }
            @Override
            public void onFailure(String errorInfo, IOException e) {
                reserveViewInter.showReserveResult(errorInfo);
            }
        });
    }
}
