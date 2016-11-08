package org.mazhuang.androiduidemos.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.mazhuang.androiduidemos.R;

/**
 * Created by mazhuang on 2016/11/8.
 */

public class ScaleTypeTestActivity extends BaseActivity {
    private ImageView mImageView;
    private EditText mWidthEdit;
    private EditText mHeightEdit;

    private int mImageViewWidth;
    private int mImageViewHeight;

    private int mDefaultSize = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_type_test);

        initViews();
    }

    private void initViews() {
        mImageView = (ImageView) findViewById(R.id.image);
        mWidthEdit = (EditText) findViewById(R.id.view_width);
        mHeightEdit = (EditText) findViewById(R.id.view_height);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ViewGroup.LayoutParams lp = mImageView.getLayoutParams();
                if (lp != null) {
                    try {
                        mImageViewWidth = Integer.valueOf(mWidthEdit.getText().toString());
                    } catch (NumberFormatException e) {
                        mImageViewWidth = mDefaultSize;
                    }

                    try {
                        mImageViewHeight = Integer.valueOf(mHeightEdit.getText().toString());
                    } catch (NumberFormatException e) {
                        mImageViewHeight = mDefaultSize;
                    }

                    lp.width = mImageViewWidth;
                    lp.height = mImageViewHeight;
                    mImageView.setLayoutParams(lp);
                }
            }
        };
        mWidthEdit.addTextChangedListener(textWatcher);
        mHeightEdit.addTextChangedListener(textWatcher);

        Spinner scaleTypeSpinner = (Spinner) findViewById(R.id.scale_type);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.scale_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scaleTypeSpinner.setAdapter(adapter);

        final SparseArray<ImageView.ScaleType> scaleTypeArray = new SparseArray<>();
        scaleTypeArray.put(0, ImageView.ScaleType.MATRIX);
        scaleTypeArray.put(1, ImageView.ScaleType.FIT_XY);
        scaleTypeArray.put(2, ImageView.ScaleType.FIT_START);
        scaleTypeArray.put(3, ImageView.ScaleType.FIT_CENTER);
        scaleTypeArray.put(4, ImageView.ScaleType.FIT_END);
        scaleTypeArray.put(5, ImageView.ScaleType.CENTER);
        scaleTypeArray.put(6, ImageView.ScaleType.CENTER_CROP);
        scaleTypeArray.put(7, ImageView.ScaleType.CENTER_INSIDE);

        scaleTypeSpinner.setSelection(scaleTypeArray.indexOfValue(mImageView.getScaleType()));
        scaleTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageView.ScaleType type = scaleTypeArray.get(position);
                mImageView.setScaleType(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
