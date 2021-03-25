package io.github.grooters.libraryseat.presenter.preinterface;

import android.content.Intent;

public interface MinePreInter {
    public void getUser();
    public void editNickName(String number,String nickName);
    public void editSignature(String number,String signature);
    public String loadImage(int requestCode, int resultCode, Intent data);
}
