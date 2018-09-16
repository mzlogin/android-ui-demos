package org.mazhuang.androiduidemos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.Toast;

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

        listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "background toast", Toast.LENGTH_SHORT).show();
            }
        }, 5000);
    }

}
