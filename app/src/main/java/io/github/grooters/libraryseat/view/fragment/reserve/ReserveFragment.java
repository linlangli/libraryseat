package io.github.grooters.libraryseat.view.fragment.reserve;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import io.github.grooters.libraryseat.R;
import io.github.grooters.libraryseat.model.Seat;
import io.github.grooters.libraryseat.presenter.preinterface.ReservePreInter;
import io.github.grooters.libraryseat.presenter.preinterfacel.ReservePreImpL;
import io.github.grooters.libraryseat.view.viewinter.ReserveViewInter;
import io.github.grooters.lller.tool.Toaster;
import io.github.grooters.lller.widget.recycler.adapter.GeneralAdapter;
import io.github.grooters.lller.widget.recycler.holder.GeneralHolder;

public class ReserveFragment extends Fragment implements ReserveViewInter{
    private View view;
    private ReservePreInter reservePreInter;
    private int type_temp;
    private static String TAG=ReserveFragment.class.getSimpleName();
    private boolean isFirst;
    private GeneralAdapter<Seat[]> adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_seat,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reservePreInter=new ReservePreImpL(getContext(),this);
        reservePreInter.getSeatInfo();
    }

    @Override
    public void initSeat(final List<Seat[]> seats) {
        type_temp=1;
        isFirst=true;
        final RecyclerView recyclerView=view.findViewById(R.id.recycler_oneFloor);
        adapter=new GeneralAdapter<Seat[]>(getContext(),R.layout.recycler_reserve_item,seats) {
            @Override
            public void handle(GeneralHolder viewHolder, final Seat[] data) {
                class ButtonListenner implements View.OnClickListener{
                    @Override
                    public void onClick(View v) {
                        switch(v.getId()){
                            case R.id.imgButt_seat_one:
                                if(data[0].getIdle()==0){
                                    reservePreInter.requestReser(data[0]);
                                }else if(data[0].getIdle()==2){
                                    reservePreInter.unorderseat(data[0].getId());
                                }
                                break;
                            case R.id.imgButt_seat_two:
                                if(data[1].getIdle()==0){
                                    reservePreInter.requestReser(data[1]);
                                }else if(data[1].getIdle()==2){
                                    reservePreInter.unorderseat(data[1].getId());
                                }
                                break;
                            case R.id.imgButt_seat_three:
                                if(data[2].getIdle()==0){
                                    reservePreInter.requestReser(data[2]);
                                }else if(data[2].getIdle()==2){
                                    reservePreInter.unorderseat(data[2].getId());
                                }
                                break;
                            case R.id.imgButt_seat_fourth:
                                if(data[3].getIdle()==0){
                                    reservePreInter.requestReser(data[3]);
                                }else if(data[3].getIdle()==2){
                                    reservePreInter.unorderseat(data[3].getId());
                                }
                                break;
                            case R.id.imgButt_seat_five:
                                if(data[4].getIdle()==0){
                                    reservePreInter.requestReser(data[4]);
                                }else if(data[4].getIdle()==2){
                                    reservePreInter.unorderseat(data[4].getId());
                                }
                                break;
                        }
                    }
                }
                //为不同楼层设置title
                if(type_temp!=data[0].getFloor()||isFirst){
                    Log.i(TAG,"datas[0].getFloor():"+data[0].getFloor());
                    TextView titleText=viewHolder.handleView(R.id.text_title);
                    titleText.setText(String.valueOf(data[0].getFloor()+"楼"));
                    viewHolder.handleView(R.id.recycler_line).setVisibility(View.VISIBLE);
                    titleText.setVisibility(View.VISIBLE);
                    type_temp=data[0].getFloor();
                    isFirst=false;
                }else {
                    //同一楼层隐藏title
                    Log.i(TAG,"else datas[0].getFloor():"+data[0].getFloor());
                    viewHolder.handleView(R.id.text_title).setVisibility(View.GONE);
                    viewHolder.handleView(R.id.recycler_line).setVisibility(View.GONE);
                }
                ImageButton[] seatButts=new ImageButton[]{viewHolder.handleView(R.id.imgButt_seat_one),viewHolder.handleView(R.id.imgButt_seat_two),
                        viewHolder.handleView(R.id.imgButt_seat_three), viewHolder.handleView(R.id.imgButt_seat_fourth),viewHolder.handleView(R.id.imgButt_seat_five)};
                for(ImageButton imageButton:seatButts){
                    imageButton.setOnClickListener(new ButtonListenner());
                }
                TextView[] seatNumTexts=new TextView[]{viewHolder.handleView(R.id.text_seat_num_one),viewHolder.handleView(R.id.text_seat_num_two),viewHolder.handleView(R.id.text_seat_num_three),
                        viewHolder.handleView(R.id.text_seat_num_fourth),viewHolder.handleView(R.id.text_seat_num_five)};
                TextView[] seatTimeTexts=new TextView[]{viewHolder.handleView(R.id.text_time_one),viewHolder.handleView(R.id.text_time_two),viewHolder.handleView(R.id.text_time_three),
                        viewHolder.handleView(R.id.text_time_fourth),viewHolder.handleView(R.id.text_time_five)};
                int i=0;
                for(Seat seat:data){
                    if(seat==null)
                        return;
                    seatButts[i].setVisibility(View.VISIBLE);
                    seatNumTexts[i].setVisibility(View.VISIBLE);
                    seatTimeTexts[i].setVisibility(View.VISIBLE);
                    switch (seat.getIdle()){
                        case 0:
                            seatButts[i].setBackground(getResources().getDrawable(R.drawable.seat,null));
                            seatNumTexts[i].setText(String.valueOf(seat.getNumber()));
                            seatTimeTexts[i].setText(String.valueOf(seat.getLeavetime()));
                            break;
                        case 1:
                            seatButts[i].setBackground(getResources().getDrawable(R.drawable.seat_reserve,null));
                            seatNumTexts[i].setText(String.valueOf(seat.getNumber()));
                            seatTimeTexts[i].setText(String.valueOf(seat.getLeavetime()));
                            break;
                        case 2:
                            //预约
                            seatButts[i].setBackground(getResources().getDrawable(R.drawable.seat_leave,null));
                            seatNumTexts[i].setText(String.valueOf(seat.getNumber()));
                            seatTimeTexts[i].setText(String.valueOf(seat.getLeavetime()));
                            break;
                        case 3:
                            //暂离
                            seatButts[i].setBackground(getResources().getDrawable(R.drawable.seat_leave,null));
                            seatNumTexts[i].setText(String.valueOf(seat.getNumber()));
                            seatTimeTexts[i].setText(String.valueOf(seat.getLeavetime()));
                            break;
                    }
                    ++i;
                }
            }
        };
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void freshData() {
        reservePreInter.getSeatInfo();
    }

    @Override
    public void showReserveResult(String result) {
        Toaster.showShort(getContext(),result);
    }


}
