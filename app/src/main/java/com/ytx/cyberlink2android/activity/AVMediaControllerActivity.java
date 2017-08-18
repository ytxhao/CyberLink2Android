package com.ytx.cyberlink2android.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ytx.cyberlink2android.R;
import com.ytx.cyberlink2android.adapter.BaseRecyclerAdapter;
import com.ytx.cyberlink2android.utils.YtxLog;
import com.ytx.cyberlink2android.view.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/8/18.
 */

public class AVMediaControllerActivity extends SimpleBarRootActivity implements BaseRecyclerAdapter.OnItemClickListener{



    private final static int STATE_HEADER_REFRESH_COMPLETE = 0;
    private final static int STATE_FOOTER_REFRESH_COMPLETE = 1;

    private List<String> mDatas= new ArrayList<String>();
    private PullToRefreshLayout recyclerRefresh;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private BaseRecyclerAdapter adapter;

    public Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case STATE_HEADER_REFRESH_COMPLETE:
                    adapter.notifyDataSetChanged();
                    recyclerRefresh.onHeaderRefreshComplete();
                    break;
                case STATE_FOOTER_REFRESH_COMPLETE:
                    adapter.notifyDataSetChanged();
                    recyclerRefresh.onFooterRefreshComplete();
                    break;

            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setTitleTextColor(getResources().getColor(R.color.white));
        setTitle(R.string.av_controller_title);
        setContentView(R.layout.activity_media_controller);
        initView();
    }



    private void initData() {
        // TODO Auto-generated method stub
        for(int i=0;i<20;i++){
            mDatas.add("listView原来的数据-"+i);
        }
    }

    private void initView(){
        recyclerRefresh = findView(R.id.recyclerRefresh);


        recyclerRefresh.setOnFooterRefreshListener(new PullToRefreshLayout.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshLayout view) {
                getOldDevice();

            }
        });
        recyclerRefresh.setOnHeaderRefreshListener(new PullToRefreshLayout.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshLayout view) {
                getNewDevice();
            }
        });
        recyclerRefresh.setonHeaderUpdateTextListener(new PullToRefreshLayout.OnHeaderUpdateTextListener() {
            @Override
            public void onUpdateHeaderText() {

            }
        });

        recyclerView = findView(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new BaseRecyclerAdapter(R.layout.item_device_login_message){

            @Override
            public int getItemCount() {
                return mDatas.size();
            }

            @Override
            public void onBindViewData(AntsViewHolder holder, int position) {

            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return super.onCreateViewHolder(parent, viewType);
            }
        };

        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);

        recyclerRefresh.setIsHeaderLoad(true);
        recyclerRefresh.setIsFooterLoad(true);
        recyclerRefresh.setPermitToRefreshNoChildView(true);
        recyclerRefresh.setmHeaderTextId(R.string.refresh_layout_load);
        recyclerRefresh.setmFooterTextId(R.string.refresh_layout_load);
    }

    private void getNewDevice() {

        YtxLog.d("test","ytxhao test getNewDevice");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    mHandler.sendEmptyMessage(STATE_HEADER_REFRESH_COMPLETE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getOldDevice() {
        YtxLog.d("test","ytxhao test getOldDevice");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    mHandler.sendEmptyMessage(STATE_FOOTER_REFRESH_COMPLETE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
