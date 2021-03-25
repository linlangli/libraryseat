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
public class MainPostDialog extends Dialog implements PostViewInter,View.OnClickListener {
    private PostPreInter postPreInter;
    private EditText titleEdit,contentEdit;
    private Context context;
    private PostViewInter postViewInter;

    public MainPostDialog(Context context,PostViewInter postViewInter) {
        super(context);
        this.context=context;
        this.postViewInter=postViewInter;
        postPreInter=new PostPreImpL(postViewInter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_main_post);
        titleEdit=findViewById(R.id.edit_title);
        contentEdit=findViewById(R.id.edi_content);
        Button createButt=findViewById(R.id.butt_create);
        createButt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.butt_create:
                postPreInter.createPost(UserStorage.getUser(context).getNumber(),titleEdit.getText().toString(),contentEdit.getText().toString());
                break;
        }
    }
    @Override
    public void showResult(String result) {
        Toaster.showShort(context,result);
        dismiss();
    }
    @Override
    public void initPost(List<MainPost> mainPosts) {
        postViewInter.initPost(mainPosts);
    }
}
