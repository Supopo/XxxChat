package com.xxx.xxx.ui.activity;


import android.os.Bundle;

import com.hongyu.zorelib.mvp.view.BaseActivity;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.xxx.xxx.R;

public class ChatActivity extends BaseActivity {

    private String userId;

    @Override
    protected void logicBeforeInitView() {
        userId = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void logicAfterInitView() {
        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);//单人聊天模式
        args.putString(EaseConstant.EXTRA_USER_ID, userId);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_chat, chatFragment).commit();
    }
}
