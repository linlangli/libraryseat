package io.github.grooters.lller.quote.jumpingbeans;

import android.widget.TextView;

import net.frakbot.jumpingbeans.JumpingBeans;

import io.github.grooters.lller.R;

/**
 * Create by 李林浪 in 2018/10/22
 * Elegant Code...
 */
public class JumpinGer {
    private static final String TAG=JumpinGer.class.getSimpleName();

    private JumpinGer(){ }

    public static JumpinGer getJumpinGer(){
        return SingleHolder.jumpinGer;
    }

    private static class SingleHolder{
        private static final JumpinGer jumpinGer=new JumpinGer();
    }

    public void setJump(TextView textView){
        JumpingBeans jumpingBeans = JumpingBeans.with(textView)
                .makeTextJump(0, textView.getText().length())
                .setIsWave(true)
                .setLoopDuration(3000)
                .build();
    }

}
