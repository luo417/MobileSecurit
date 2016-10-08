package com.holy.mobilesecurity.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by holy on 2016/5/7.
 */
public abstract class SetupBaseActivity extends Activity {

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //手势识别器
        mGestureDetector =  new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(e2.getRawX()-e1.getRawX()>200){
                    //上一页
                    showPreviousPage();
                }else if(e2.getRawX()-e1.getRawX()<200){
                    //下一页
                    showNextPage();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    abstract void showNextPage();
    abstract void showPreviousPage();

    public void previousPage(View view){
        showPreviousPage();
    }

    public void nextPage(View view){
        showNextPage();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
