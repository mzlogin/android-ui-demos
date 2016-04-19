package org.mazhuang.androiduidemos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import org.mazhuang.androiduidemos.adapter.MyExpandableListAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this);
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.list);
        if (listView != null) {
            listView.setAdapter(adapter);
            listView.setOnChildClickListener(adapter);
        }
    }

}
