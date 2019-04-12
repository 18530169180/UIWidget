package com.aries.ui.widget.demo.module.title;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aries.ui.view.tab.SlidingTabLayout;
import com.aries.ui.view.title.TitleBarView;
import com.aries.ui.widget.demo.R;
import com.aries.ui.widget.demo.base.BaseActivity;
import com.aries.ui.widget.demo.manager.GlideManager;
import com.aries.ui.widget.demo.manager.TabLayoutManager;
import com.aries.ui.widget.demo.module.TabFragment;
import com.aries.ui.widget.demo.util.SizeUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * @Author: AriesHoo on 2019/4/11 15:08
 * @E-Mail: AriesHoo@126.com
 * @Function: Title 自定义左边、中间、右边View
 * @Description:
 */
public class TitleActionActivity extends BaseActivity {

    @BindView(R.id.title_searchTitleAction) TitleBarView mTitleSearch;
    @BindView(R.id.title_qqTitleAction) TitleBarView mTitleQQ;
    @BindView(R.id.title_weChatTitleAction) TitleBarView mTitleWeChat;
    @BindView(R.id.title_loadingTitleAction) TitleBarView mTitleLoading;
    @BindView(R.id.title_tabTitleAction) TitleBarView mTitleTab;
    @BindView(R.id.vp_contentTitleAction) ViewPager mVpContent;

    private SlidingTabLayout mSlidingTab;
    private List<Fragment> mListFragment = new ArrayList<>();
    private List<String> mListTitle = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_title_action;
    }

    @Override
    protected void setTitleBar() {
        titleBar.setTitleSubText("提供部分常用场景,开发者可根据实际场景组合")
                .setTitleSubTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f);
    }

    @Override
    protected void initView(Bundle var1) {

        //中间搜索框样式
        View view = View.inflate(mContext, R.layout.layout_search, null);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtil.dp2px(32));
        params.rightMargin = SizeUtil.dp2px(4);
        view.setLayoutParams(params);
        mTitleSearch.addCenterAction(
                mTitleSearch.new ViewAction(view));
        //QQ TitleBar样式
        View viewQq = View.inflate(mContext, R.layout.layout_qq_header, null);
        ImageView imgQq = viewQq.findViewById(R.id.iv_headQQ);
        GlideManager.loadCircleImg("https://avatars3.githubusercontent.com/u/19605922?v=4&s=460", imgQq);
        mTitleQQ.addLeftAction(mTitleQQ.new ViewAction(viewQq), 0, new ViewGroup.LayoutParams(SizeUtil.dp2px(42), SizeUtil.dp2px(42)));

        //微信TitleBar样式
        mTitleWeChat.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "打开操作菜单", Toast.LENGTH_SHORT).show();
            }
        })
                .addRightAction(mTitleWeChat.new ImageAction(R.drawable.ic_search_normal, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "打开搜索页", Toast.LENGTH_SHORT).show();
                    }
                }), 0);

        //中间loading效果
        ProgressBar progressBar = new ProgressBar(mContext);
        progressBar.setIndeterminateDrawable(ContextCompat.getDrawable(mContext, R.drawable.dialog_loading_wei_bo));
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(SizeUtil.dp2px(20), SizeUtil.dp2px(20));
        margin.rightMargin = SizeUtil.dp2px(10);
        mTitleLoading.addCenterAction(mTitleLoading.new ViewAction(progressBar), 0, margin);

        //中间Tab效果
        mSlidingTab = (SlidingTabLayout) View.inflate(mContext, R.layout.layout_tab_sliding, null);
        mTitleTab.addCenterAction(mTitleTab.new ViewAction(mSlidingTab), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setTab();
    }


    private void setTab() {
        for (int i = 0; i < 20; i++) {
            mListFragment.add(TabFragment.newInstance());
            mListTitle.add("Tab" + i);
        }
        TabLayoutManager.getInstance().setSlidingTabData(this, mSlidingTab, mVpContent, mListTitle, mListFragment);
        mSlidingTab.setCurrentTab(0);
    }
}
