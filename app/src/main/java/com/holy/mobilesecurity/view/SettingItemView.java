package com.holy.mobilesecurity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.holy.mobilesecurity.R;

/**
 * Created by Holy on 2016/5/1.
 */
public class SettingItemView extends RelativeLayout {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.holy.mobilesecurity";
    private TextView mItemTitle;
    private TextView mItemDesc;
    private CheckBox mItemUpdate;
    private String mTitle;
    private String mDesc_on;
    private String mDesc_off;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_setting_item, this);

        mItemTitle = (TextView) findViewById(R.id.tv_item_title);
        mItemDesc = (TextView) findViewById(R.id.tv_item_desc);
        mItemUpdate = (CheckBox) findViewById(R.id.cb_item_isUpdated);

        mTitle = attrs.getAttributeValue(NAMESPACE, "Title");
        mDesc_on = attrs.getAttributeValue(NAMESPACE, "Desc_on");
        mDesc_off = attrs.getAttributeValue(NAMESPACE, "Desc_off");

        mItemTitle.setText(mTitle);
        mItemDesc.setText(mDesc_off);
    }

    /**
     * 获取CheckBox是否选中
     * @return isChecked
     */
    public boolean isChecked(){
        return mItemUpdate.isChecked();
    }

    /**
     * 设置CheckBox是否选中
     * @param isChecked
     */
    public void setChecked(boolean isChecked){
        mItemUpdate.setChecked(isChecked);
        if(isChecked){
            mItemDesc.setText(mDesc_on);
        }else{
            mItemDesc.setText(mDesc_off);
        }
    }

    /**
     * 为item设置标题
     * @param title
     */
    public void setTitle(String title){
        mItemTitle.setText(title);
    }
}
