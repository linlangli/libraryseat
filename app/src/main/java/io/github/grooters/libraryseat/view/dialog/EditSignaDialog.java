package io.github.grooters.libraryseat.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.github.grooters.libraryseat.R;
import io.github.grooters.libraryseat.base.UserStorage;
import io.github.grooters.libraryseat.presenter.preinterface.MinePreInter;
import io.github.grooters.libraryseat.presenter.preinterfacel.MinePreImpL;
import io.github.grooters.libraryseat.view.viewinter.MineViewInter;

/**
 * Create by 李林浪 in 2018/11/25
 * Elegant Code...
 */
public class EditSignaDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private MinePreInter minePreInter;
    private EditText editSignatureEdit;

    public EditSignaDialog(Context context, MineViewInter mineViewInter) {
        super(context);
        this.context=context;
        minePreInter=new MinePreImpL(mineViewInter,context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_signature);
        Button editSignatureButt=findViewById(R.id.butt_changeSignature);
        editSignatureButt.setOnClickListener(this);
        editSignatureEdit=findViewById(R.id.edit_changeSignature);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.butt_changeSignature:
                minePreInter.editSignature(UserStorage.getUser(getContext()).getNumber(),editSignatureEdit.getText().toString());
                break;
        }
    }
}
