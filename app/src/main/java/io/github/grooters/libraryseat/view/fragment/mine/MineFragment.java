package io.github.grooters.libraryseat.view.fragment.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import io.github.grooters.libraryseat.R;
import io.github.grooters.libraryseat.model.User;
import io.github.grooters.libraryseat.presenter.preinterface.MinePreInter;
import io.github.grooters.libraryseat.presenter.preinterfacel.MinePreImpL;
import io.github.grooters.libraryseat.view.dialog.EditNickDialog;
import io.github.grooters.libraryseat.view.dialog.EditSignaDialog;
import io.github.grooters.libraryseat.view.viewinter.MineViewInter;
import io.github.grooters.lller.quote.glide.Glider;
import io.github.grooters.lller.tool.Toaster;

public class MineFragment extends Fragment implements MineViewInter,View.OnClickListener {
    private View view;
    private TextView nickNameText,nameText,signatureText,departmentText,majorText,numberText,timeText,numText;
    private ImageView avatarImg;
    private final int SELECT_PICTURE_FROM_ALBUM=1;
    private MinePreInter minePreInter;
    private static final String TAG="MineFragment.class";
//    private View dialogNickView,dialogSignatureView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_mine,null);
//        dialogNickView=inflater.inflate(R.layout.dialog_edit_nick,null);
//        dialogSignatureView=inflater.inflate(R.layout.dialog_edit_signature,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        minePreInter=new MinePreImpL(this,getContext());
        nickNameText=view.findViewById(R.id.text_nickName);
        nameText=view.findViewById(R.id.text_name);
        signatureText=view.findViewById(R.id.text_signature);
        departmentText=view.findViewById(R.id.text_department);
        majorText=view.findViewById(R.id.text_major);
        numberText=view.findViewById(R.id.text_number);
        timeText=view.findViewById(R.id.text_time);
        avatarImg=view.findViewById(R.id.img_avatar);
        numText=view.findViewById(R.id.text_num);
        Button changeAvatorButt=view.findViewById(R.id.butt_changeAvatar);
        changeAvatorButt.setOnClickListener(this);
        ImageButton editNickButt=view.findViewById(R.id.imgButt_editNick);
        ImageButton editSignatureButt=view.findViewById(R.id.imgButt_editIntro);
        editNickButt.setOnClickListener(this);
        editSignatureButt.setOnClickListener(this);
        minePreInter.getUser();
    }

    @Override
    public void setInfo(final User user) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nickNameText.setText(user.getNickName());
                nameText.setText(user.getName());
                signatureText.setText(user.getSignature());
                departmentText.setText(user.getDepartment());
                majorText.setText(user.getMajor());
                numberText.setText(String.valueOf(user.getNum()));
                numText.setText(String.valueOf(user.getNumber()));
                timeText.setText(String.valueOf(user.getTime()));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_avatar:
                break;
            case R.id.imgButt_editNick:
                EditNickDialog editNickDialog=new EditNickDialog(getContext(),this);
                editNickDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                editNickDialog.show();
                break;
            case R.id.imgButt_editIntro:
                EditSignaDialog editSignaDialog=new EditSignaDialog(getContext(),this);
                editSignaDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                editSignaDialog.show();
                break;
            case R.id.butt_changeAvatar:
                Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,SELECT_PICTURE_FROM_ALBUM);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path=minePreInter.loadImage(requestCode,resultCode,data);
        Log.d(TAG,"path:"+path);
        Glider.getLllGlider().loadLocalImg(getContext(),path,avatarImg);
    }

    @Override
    public void showResult(String result) {
        Toaster.showShort(getContext(),result);
    }
}
