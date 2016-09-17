package org.mazhuang.androiduidemos.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.mazhuang.androiduidemos.R;
import org.mazhuang.androiduidemos.view.SkewTestView;

public class SkewTestActivity extends AppCompatActivity {

    private SkewTestView mSkewTestView;
    private EditText mSkewXEdit;
    private EditText mSkewYEdit;
    private Button mSetParametersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skew_test);

        initViews();
    }

    private void initViews() {
        mSkewTestView = (SkewTestView) findViewById(R.id.skew_test);
        mSkewXEdit = (EditText) findViewById(R.id.skewx);
        mSkewYEdit = (EditText) findViewById(R.id.skewy);
        mSetParametersButton = (Button) findViewById(R.id.set_parameters);
        mSetParametersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable skewX = mSkewXEdit.getText();
                Editable skewY = mSkewYEdit.getText();
                float sx = TextUtils.isEmpty(skewX) ? 0 : Float.valueOf(skewX.toString());
                float sy = TextUtils.isEmpty(skewY) ? 0 : Float.valueOf(skewY.toString());
                mSkewTestView.setData(sx, sy);
            }
        });
    }
}
