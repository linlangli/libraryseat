package io.github.grooters.libraryseat.view.fragment.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import io.github.grooters.libraryseat.R;
import io.github.grooters.libraryseat.base.UserStorage;
import io.github.grooters.libraryseat.model.ChildPost;
import io.github.grooters.libraryseat.model.MainPost;
import io.github.grooters.libraryseat.presenter.preinterface.ChildPosPretInter;
import io.github.grooters.libraryseat.presenter.preinterfacel.ChildPostPreImpL;
import io.github.grooters.libraryseat.view.viewinter.ChildPostViewInter;
import io.github.grooters.lller.tool.Toaster;
import io.github.grooters.lller.widget.recycler.adapter.HeaderAdapter;
import io.github.grooters.lller.widget.recycler.adapter.HeaderInterface;
import io.github.grooters.lller.widget.recycler.holder.GeneralHolder;

/**
 * Create by 李林浪 in 2018/11/12
 * Elegant Code...
 */
public class ChildPostFragment extends Fragment implements ChildPostViewInter,View.OnClickListener,View.OnFocusChangeListener {
    private static final String TAG=ChildPostFragment.class.getSimpleName();
    private ChildPosPretInter childPosPretInter;
    private Button replayButton;
    private EditText replayEdit;
    private View view;
    private MainPost mainPost;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        childPosPretInter=new ChildPostPreImpL(this);
        return view=inflater.inflate(R.layout.fagment_child_post,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent=getActivity().getIntent();
        int mainPostId=intent.getIntExtra("MAIN_POST_ID",1);
        Log.d(TAG,"MAIN_POST_ID："+mainPostId);
        mainPost=(MainPost) intent.getExtras().getSerializable("BUNDLE_MAIN_POST");
        childPosPretInter.requestPost(mainPostId);
        replayEdit=view.findViewById(R.id.edit_replay);
        replayButton=view.findViewById(R.id.butt_replay);
        replayEdit.setOnFocusChangeListener(this);
        replayButton.setOnClickListener(this);
    }

    @Override
    public void initPost(final List<ChildPost> childPosts) {
        childPosts.add(0,new ChildPost());
        Log.d(TAG,"childPosts:"+childPosts.size());
        final RecyclerView recyclerView= view.findViewById(R.id.recycler_child_post);
        final HeaderAdapter<ChildPost> adapter=new HeaderAdapter<ChildPost>(getContext(), new HeaderInterface<ChildPost>() {
            @Override
            public int getLayoutId(int itemType) {
                switch (itemType){
                    case 0:
                        return R.layout.item_child_post_header;
                    case 1:
                        return R.layout.item_child_post;
                }
                return -1;
            }

            @Override
            public int getItemType(int position, List<ChildPost> datas) {
                if(position==0){
                    return 0;
                }else {
                    return 1;
                }
            }
        },childPosts,true) {
            @Override
            public void header(GeneralHolder viewHolder, ChildPost data) {
                TextView nameText=viewHolder.handleView(R.id.text_child_post_name);
                TextView dateText=viewHolder.handleView(R.id.text_child_date);
                TextView contentText=viewHolder.handleView(R.id.text_child_post_content);
                TextView titleText=viewHolder.handleView(R.id.text_child_post_title);
                ImageButton praiseImgButt=viewHolder.handleView(R.id.imgButt_child_praisePost);
                praiseImgButt.setOnClickListener(new ImgButtonListener());
                nameText.setText(mainPost.getUserName());
                dateText.setText(mainPost.getDate());
                contentText.setText(mainPost.getContent());
                titleText.setText(mainPost.getTitle());
            }
            @Override
            public void handle(GeneralHolder viewHolder, ChildPost data) {
                TextView nameText=viewHolder.handleView(R.id.text_child_post_name);
                TextView dateText=viewHolder.handleView(R.id.text_child_date);
                TextView contentText=viewHolder.handleView(R.id.text_child_post_content);
                ImageButton praiseImgButt=viewHolder.handleView(R.id.imgButt_child_praisePost);
                praiseImgButt.setOnClickListener(new ImgButtonListener());
                nameText.setText(data.getUserName());
                dateText.setText(data.getDate());
                contentText.setText(data.getContent());
            }
            class ImgButtonListener implements View.OnClickListener{
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.imgButt_child_praisePost:
                            replayEdit.requestFocus();
                            break;
                    }
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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.butt_replay:
                String content=replayEdit.getText().toString();
                if(content.equals("")){
                    Toaster.showShort(getContext(),"内容不能为空");
                }else {
//                    childPosPretInter.replayPost(UserStorage.getUser(getContext()).getNumber(),content);
                }
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.edit_replay:
                if(hasFocus){
                    Log.d(TAG,"hasFocus=true");
                    replayButton.setVisibility(View.VISIBLE);
                }else {
                    replayButton.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    @Override
    public void showResult(String result) {
        Toaster.showShort(getContext(),result);
    }
}
