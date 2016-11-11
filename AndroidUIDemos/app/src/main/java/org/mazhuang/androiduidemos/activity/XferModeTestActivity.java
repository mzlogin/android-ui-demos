package org.mazhuang.androiduidemos.activity;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.mazhuang.androiduidemos.R;
import org.mazhuang.androiduidemos.view.XfermodeTestView;

public class XfermodeTestActivity extends BaseActivity {

    private XfermodeTestView mXfermodeTestView;
    private Spinner mXfermodeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfer_mode_test);

        initViews();
    }

    private void initViews() {
        mXfermodeTestView = (XfermodeTestView) findViewById(R.id.test);
        mXfermodeSpinner = (Spinner) findViewById(R.id.options);

        final SparseArray<PorterDuff.Mode> modes = new SparseArray<>();
        modes.append(0, PorterDuff.Mode.CLEAR);
        modes.append(1, PorterDuff.Mode.SRC);
        modes.append(2, PorterDuff.Mode.DST);
        modes.append(3, PorterDuff.Mode.SRC_OVER);
        modes.append(4, PorterDuff.Mode.DST_OVER);
        modes.append(5, PorterDuff.Mode.SRC_IN);
        modes.append(6, PorterDuff.Mode.DST_IN);
        modes.append(7, PorterDuff.Mode.SRC_OUT);
        modes.append(8, PorterDuff.Mode.DST_OUT);
        modes.append(9, PorterDuff.Mode.SRC_ATOP);
        modes.append(10, PorterDuff.Mode.DST_ATOP);
        modes.append(11, PorterDuff.Mode.XOR);
        modes.append(12, PorterDuff.Mode.ADD);
        modes.append(13, PorterDuff.Mode.MULTIPLY);
        modes.append(14, PorterDuff.Mode.SCREEN);
        modes.append(15, PorterDuff.Mode.OVERLAY);
        modes.append(16, PorterDuff.Mode.DARKEN);
        modes.append(17, PorterDuff.Mode.LIGHTEN);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.xfermode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mXfermodeSpinner.setAdapter(adapter);

        mXfermodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mXfermodeTestView.setXferMode(new PorterDuffXfermode(modes.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
