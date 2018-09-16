package org.mazhuang.androiduidemos.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import org.mazhuang.androiduidemos.R;
import org.mazhuang.androiduidemos.view.CustomBottomNavigationView;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class BadgeTestActivity extends BaseActivity {

    private TextView mTitleTextView;
    private CustomBottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge_test);

        mTitleTextView = findViewById(R.id.title);
        mNavigationView = findViewById(R.id.nav);

        initBadge();

        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add:
                        addBadgeAt(0, 1);
                        break;

                    case R.id.save:
                        addBadgeAt(1, 2);
                        break;

                    case R.id.delete:
                        addBadgeAt(2, 3);
                        break;

                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void initBadge() {
        new QBadgeView(this).bindTarget(mTitleTextView).setBadgeNumber(15)
        .setBadgeGravity(Gravity.TOP | Gravity.END);
    }


    private Badge addBadgeAt(int position, int number) {
        // add badge
        return new QBadgeView(this)
                .setBadgeNumber(number)
                .setGravityOffset(12, 2, true)
                .bindTarget(mNavigationView.getBottomNavigationItemView(position));
    }
}
