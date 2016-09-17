package org.mazhuang.androiduidemos.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.mazhuang.androiduidemos.R;
import org.mazhuang.androiduidemos.view.PieChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PieChartActivity extends BaseActivity {

    private PieChartView mPieChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        initViews();

        fakeData();
    }

    private void initViews() {
        mPieChartView = (PieChartView) findViewById(R.id.pie_chart);
    }

    private void fakeData() {
        List<PieChartView.PieData> data = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            PieChartView.PieData pie = new PieChartView.PieData("", random.nextInt(100));
            data.add(pie);
        }

        mPieChartView.setData(data);
    }
}
