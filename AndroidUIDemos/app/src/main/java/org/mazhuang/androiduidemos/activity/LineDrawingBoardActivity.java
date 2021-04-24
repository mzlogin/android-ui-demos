package org.mazhuang.androiduidemos.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import org.mazhuang.androiduidemos.R;
import org.mazhuang.androiduidemos.view.LineDrawingBoardView;

public class LineDrawingBoardActivity extends BaseActivity {

    private LineDrawingBoardView lineDrawingBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_drawing_board);

        init();
    }

    private void init() {
        lineDrawingBoardView = findViewById(R.id.line_drawing_board);
        Switch lineTypeSwitch = findViewById(R.id.line_type_switch);
        lineTypeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lineDrawingBoardView.onDrawTypeChanged(isChecked);
            }
        });
    }
}