package org.mazhuang.androiduidemos.activity;

import android.os.Handler;
import android.os.Bundle;

import org.mazhuang.androiduidemos.R;
import org.mazhuang.androiduidemos.view.TrafficBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrafficBarViewActivity extends BaseActivity {

    private TrafficBarView mTrafficBar;
    private TrafficBarView mHorizontalTrafficBar;
    private Handler mHandler = new Handler();
    private long mRefreshInterval = 3000;
    private Runnable mUpdateTrafficCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_bar_view);

        initViews();

        mUpdateTrafficCallback = new Runnable() {
            @Override
            public void run() {
                updateData();
                mHandler.postDelayed(this, mRefreshInterval);
            }
        };
        mHandler.post(mUpdateTrafficCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mUpdateTrafficCallback);
    }

    private void initViews() {
        mTrafficBar = (TrafficBarView) findViewById(R.id.traffic);
        mHorizontalTrafficBar = (TrafficBarView) findViewById(R.id.traffic_horizontal);
    }

    private void updateData() {
        List<TrafficBarView.TrafficSegment> trafficSegments = new ArrayList<>();
        Random random = new Random();
        int totalLength = 0;
        int segCount = random.nextInt(6) + 5;
        for (int i = 0; i < segCount; i++) {
            int segLength = random.nextInt(100) + 1;
            TrafficBarView.TrafficSegment segment = new TrafficBarView.TrafficSegment(segLength, random.nextInt(5));
            trafficSegments.add(segment);
            totalLength += segLength;
        }

        int disToEnd = (int)(totalLength*random.nextFloat());
        mTrafficBar.update(trafficSegments, disToEnd);
        mHorizontalTrafficBar.update(trafficSegments, disToEnd);
    }
}
