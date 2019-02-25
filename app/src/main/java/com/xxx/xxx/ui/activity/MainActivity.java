package com.xxx.xxx.ui.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.library.layoutView.BottomNavigationViewEx;
import com.github.library.layoutView.NoScrollViewPager;
import com.hongyu.zorelib.mvp.view.BaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.xxx.xxx.BaseFragmentPagerAdapter;
import com.xxx.xxx.Fragment1;
import com.xxx.xxx.Fragment2;
import com.xxx.xxx.Fragment3;
import com.xxx.xxx.Fragment4;
import com.xxx.xxx.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.vp_content)
    NoScrollViewPager mViewPager;
    @BindView(R.id.bnv_bottom)
    BottomNavigationViewEx mBnvEx;
    @BindView(R.id.tv_title)
    TextView tvTitle;


    private List<Fragment> fragmentList = new ArrayList<>();
    private BaseFragmentPagerAdapter mAdapter;
    private EaseConversationListFragment conversationListFragment;
    private EaseContactListFragment contactListFragment;

    @Override
    protected void logicBeforeInitView() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void logicAfterInitView() {
        tvTitle.setText("首页");

        fragmentList.add(new Fragment1());
        conversationListFragment = new EaseConversationListFragment();//聊天列表
        fragmentList.add(conversationListFragment);
        contactListFragment = new EaseContactListFragment();//通讯录列表
        fragmentList.add(contactListFragment);
        fragmentList.add(new Fragment4());

        mAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, null);
        mViewPager.setAdapter(mAdapter);

        initBottomMenu();

        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });
        //需要设置联系人列表才能启动fragment
        //需要异步操作
        new Thread() {
            @Override
            public void run() {
                super.run();
                contactListFragment.setContactsMap(getContacts());
            }
        }.start();
        //设置item点击事件
        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {

            @Override
            public void onListItemClicked(EaseUser user) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername()));
            }
        });
    }

    private Map<String, EaseUser> getContacts() {
        Map<String, EaseUser> stringEaseUserHashMap = new HashMap<>();
        try {
            // 其实应该是id，文档写的是usernames
            List<String> usersId = EMClient.getInstance().contactManager().getAllContactsFromServer();
            for (String userId : usersId) {
                EaseUser easeUser = new EaseUser(userId);
                stringEaseUserHashMap.put(userId, easeUser);
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        return stringEaseUserHashMap;
    }

    private void initBottomMenu() {
        mBnvEx.enableAnimation(false);
        mBnvEx.enableShiftingMode(false);
        mBnvEx.enableItemShiftingMode(false);
        mBnvEx.setupWithViewPager(mViewPager);
        mBnvEx.setIconSize(20, 20);

        // 底部菜单栏图标字体点击颜色变化在这里修改
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };
        int[] colors = new int[]{getResources().getColor(R.color.black),
                getResources().getColor(R.color.blue)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        mBnvEx.setItemTextColor(csl);
        mBnvEx.setItemIconTintList(csl);

        mBnvEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.bottom_menu1:
                        tvTitle.setText("首页");
                        mViewPager.setCurrentItem(0, false);
                        return true;//返回true,否则tab按钮不变色,未被选中
                    case R.id.bottom_menu2:
                        mViewPager.setCurrentItem(1, false);
                        return true;
                    case R.id.bottom_menu3:
                        mViewPager.setCurrentItem(2, false);
                        return true;
                    case R.id.bottom_menu4:
                        tvTitle.setText("我的");
                        mViewPager.setCurrentItem(3, false);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
