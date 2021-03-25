package io.github.grooters.libraryseat.view.fragment.post;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import io.github.grooters.libraryseat.R;
import io.github.grooters.libraryseat.model.MainPost;
import io.github.grooters.libraryseat.presenter.preinterface.PostPreInter;
import io.github.grooters.libraryseat.presenter.preinterfacel.PostPreImpL;
import io.github.grooters.libraryseat.view.activity.ChildPostActivity;
import io.github.grooters.libraryseat.view.dialog.ChildPostDialog;
import io.github.grooters.libraryseat.view.dialog.MainPostDialog;
import io.github.grooters.libraryseat.view.viewinter.PostViewInter;
import io.github.grooters.lller.tool.Toaster;
import io.github.grooters.lller.widget.recycler.adapter.GeneralAdapter;
import io.github.grooters.lller.widget.recycler.holder.GeneralHolder;

public class PostFragment extends Fragment implements PostViewInter,View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG=PostFragment.class.getSimpleName();
    private PostPreInter postPreInter;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mainPostRecycler;
    private LinearLayoutManager layoutManager;
    private int id;
    private boolean isLoading;
    private TextView loadingText;
    private int p;
    private boolean isStop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postPreInter=new PostPreImpL(this);
        id=0;
        p=0;
        isLoading=false;
        return view=inflater.inflate(R.layout.fragment_main_post,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageButton addPostImgButt=view.findViewById(R.id.imgButt_addPost);
        layoutManager=new LinearLayoutManager(getContext());
        swipeRefreshLayout=view.findViewById(R.id.swipe_main_post);
        swipeRefreshLayout.setOnRefreshListener(this);
        mainPostRecycler=view.findViewById(R.id.recycler_main_post);
        loadingText=view.findViewById(R.id.text_loading);
        addPostImgButt.setOnClickListener(this);
        postPreInter.requestPost(id+5);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        postPreInter.requestPost(id+5);
        id=id+5;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgButt_addPost:
                MainPostDialog mainPostDialog=new MainPostDialog(getContext(),PostFragment.this);
                mainPostDialog.show();
        }
    }

    @Override
    public void initPost(List<MainPost> mainPosts) {
        mainPostRecycler= view.findViewById(R.id.recycler_main_post);
        id=mainPosts.size();
        Log.d(TAG,"mainPosts："+mainPosts.size());
        final GeneralAdapter<MainPost> adapter=new GeneralAdapter<MainPost>(getContext(),R.layout.item_main_post, mainPosts) {
            @Override
            public void handle(GeneralHolder viewHolder, final MainPost data) {
                Log.d(TAG,"handle");
                TextView nameText = viewHolder.handleView(R.id.text_main_post_name);
                TextView titleText = viewHolder.handleView(R.id.text_main_post_title);
                TextView praiseNumText = viewHolder.handleView(R.id.text_praise_num);
                TextView dateText = viewHolder.handleView(R.id.text_main_date);
                ImageButton replayImgButt=viewHolder.handleView(R.id.imgButt_main_replyPost);
                ConstraintLayout item=viewHolder.handleView(R.id.item_main_post_layout);
                nameText.setText(data.getUserName());
                titleText.setText(data.getTitle());
                praiseNumText.setText(String.valueOf(data.getPraiseNum()));
                dateText.setText(data.getDate());
                class ImgButtListener implements View.OnClickListener{
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()){
                            case R.id.imgButt_main_replyPost:
                                ChildPostDialog childPostDialog=new ChildPostDialog(getContext(),data.getId());
                                childPostDialog.show();
                                break;
                            case R.id.item_main_post_layout:
                                Log.d(TAG,"data:"+data.getId());
                                Intent intent=new Intent(getContext(),ChildPostActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("BUNDLE_MAIN_POST",data);
                                intent.putExtras(bundle);
                                intent.putExtra("MAIN_POST_ID",data.getId());
                                startActivity(intent);
                                break;

                        }
                    }
                }
                replayImgButt.setOnClickListener(new ImgButtListener());
                item.setOnClickListener(new ImgButtListener());
            }
        };
//        mainPostRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int lastItemPosition=layoutManager.findLastVisibleItemPosition();
//                if(lastItemPosition+1==adapter.getItemCount()){
//                    Log.d(TAG,"lastItemPosition");
//                    boolean isRefreshing=swipeRefreshLayout.isRefreshing();
//                    if(isRefreshing){
//                        Log.d(TAG,"isRefreshing");
//                        return;
//                    }
//                    if(!isLoading){
//                        isLoading=true;
//                        isStop=false;
//                        loadingText.setVisibility(View.VISIBLE);
//                        final String loading=loadingText.getText().toString();
//                        final ForegroundColorSpan foregroundColorSpan=new ForegroundColorSpan(Color.parseColor("#000000"));
//                        final SpannableStringBuilder builder=new SpannableStringBuilder(loading);
//                        final AbsoluteSizeSpan absoluteSizeSpan=new AbsoluteSizeSpan(200);
//                        final HandleLoading handle=new HandleLoading();
//                        Log.d(TAG,"test1");
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                while(!isStop){
//                                    Log.d(TAG,"test2");
//                                    try {
//                                        Thread.sleep(2000);
//                                        Message msg=new Message();
//                                        builder.setSpan(foregroundColorSpan,p,p+1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                        builder.setSpan(absoluteSizeSpan,p,p+1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                        ++p;
//                                        if(p==loading.length()){
//                                            p=0;
//                                            Log.d(TAG,"test3");
//                                            isStop=true;
//                                        }
//                                        msg.obj=builder;
//                                        handle.sendMessage(msg);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        }).start();
//                        Log.d(TAG,"isLoading");
//                        isLoading=false;
//                    }
//                }
//            }
//        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainPostRecycler.setLayoutManager(layoutManager);
                mainPostRecycler.setAdapter(adapter);
            }
        });
    }

    private class HandleLoading extends Handler {
        private HandleLoading() {
            super();
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(TAG,"msg.obj:"+msg.obj.toString());
            SpannableStringBuilder builder=(SpannableStringBuilder)msg.obj;
            loadingText.setText(builder);
        }
    }

    @Override
    public void showResult(String result) {
        isStop=true;
        loadingText.setVisibility(View.GONE);
        Toaster.showShort(getContext(),result);
    }
}
