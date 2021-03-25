package io.github.grooters.libraryseat.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.List;
import io.github.grooters.libraryseat.R;
import io.github.grooters.libraryseat.base.UserStorage;
import io.github.grooters.libraryseat.model.MainPost;
import io.github.grooters.libraryseat.presenter.preinterface.PostPreInter;
import io.github.grooters.libraryseat.presenter.preinterfacel.PostPreImpL;
import io.github.grooters.libraryseat.view.viewinter.PostViewInter;
import io.github.grooters.lller.tool.Toaster;

/**
 * Create by 李林浪 in 2018/11/24
 * Elegant Code...
 */
public class ChildPostDialog extends Dialog implements PostViewInter,View.OnClickListener {
    private PostPreInter postPreInter;
    private Context context;
    private EditText replayEdit;
    private int mainPostId;


    public ChildPostDialog( Context context,int mainPostId) {
        super(context);
        this.context=context;
        this.mainPostId=mainPostId;
        postPreInter=new PostPreImpL(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_child_post);
        replayEdit=findViewById(R.id.edi_replay_dialog);
        Button replayButt=findViewById(R.id.butt_replay_dialog);
        replayButt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.butt_replay_dialog:
                postPreInter.replayPost(UserStorage.getUser(context).getNumber(),replayEdit.getText().toString(),mainPostId);
                break;
        }
    }

    @Override
    public void initPost(List<MainPost> mainPosts) {

    }

    @Override
    public void showResult(String result) {
        Toaster.showShort(context,result);
    }
}
