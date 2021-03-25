package io.github.grooters.libraryseat.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.github.grooters.libraryseat.R;
import io.github.grooters.libraryseat.base.UserStorage;
import io.github.grooters.libraryseat.model.User;
import io.github.grooters.libraryseat.presenter.preinterface.MinePreInter;
import io.github.grooters.libraryseat.presenter.preinterfacel.MinePreImpL;
import io.github.grooters.libraryseat.view.viewinter.MineViewInter;

/**
 * Create by 李林浪 in 2018/11/25
 * Elegant Code...
 */
public class EditNickDialog extends Dialog implements View.OnClickListener,MineViewInter {

    private Context context;
    private MinePreInter minePreInter;
    private EditText editNickEdit;

    public EditNickDialog(Context context, MineViewInter mineViewInter) {
        super(context);
        this.context=context;
        minePreInter=new MinePreImpL(mineViewInter,context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_nick);
        Button editNickButt=findViewById(R.id.butt_changeNick);
        editNickButt.setOnClickListener(this);
        editNickEdit=findViewById(R.id.edit_changeNick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.butt_changeNick:
                minePreInter.editNickName(UserStorage.getUser(getContext()).getNumber(),editNickEdit.getText().toString());
                break;
        }
    }

    @Override
    public void setInfo(User user) {

    }

    @Override
    public void showResult(String result) {
    }
}
