package org.mazhuang.androiduidemos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.mazhuang.androiduidemos.R;
import org.mazhuang.androiduidemos.activity.FragmentUpdateUIActivity;
import org.mazhuang.androiduidemos.activity.NotificationDemoActivity;
import org.mazhuang.androiduidemos.activity.PieChartActivity;
import org.mazhuang.androiduidemos.activity.ScaleTypeTestActivity;
import org.mazhuang.androiduidemos.activity.SkewTestActivity;
import org.mazhuang.androiduidemos.activity.TrafficBarViewActivity;
import org.mazhuang.androiduidemos.activity.XfermodeTestActivity;

/**
 * Created by mazhuang on 4/19.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {
    private Context mContext;

    private String[] mGroups = {
            "Fragments",
            "Notifications",
            "Custom Views",
            "Original Views"
    };

    private ListItem[][] mChildren = {
            { // Fragments
                    new ListItem("Update UI after popBackStack", FragmentUpdateUIActivity.class)
            },
            { // Notifications
                    new ListItem("Test Notifications", NotificationDemoActivity.class)
            },
            {// Show Custom Views
                    new ListItem("TrafficBarView", TrafficBarViewActivity.class),
                    new ListItem("PieChartView", PieChartActivity.class),
                    new ListItem("SkewTestView", SkewTestActivity.class)
            },
            {
                    new ListItem("scaleTypeTest", ScaleTypeTestActivity.class),
                    new ListItem("XferModeTest", XfermodeTestActivity.class)
            }
    };

    public MyExpandableListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return mGroups.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildren[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildren[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.group_index, null);
            holder = new GroupViewHolder();
            holder.mGroupName = (TextView) convertView.findViewById(R.id.groupName);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        String groupName = mGroups[groupPosition];
        holder.mGroupName.setText(groupName);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_index, null);
            holder = new ChildViewHolder();
            holder.mChildName = (TextView) convertView.findViewById(R.id.childName);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        String childName = mChildren[groupPosition][childPosition].mText;
        holder.mChildName.setText(childName);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        ListItem item = mChildren[groupPosition][childPosition];
        Intent intent = new Intent(mContext, item.mActivityClass);
        mContext.startActivity(intent);
        return true;
    }

    class ListItem {
        private String mText;
        private Class<? extends Activity> mActivityClass;

        public ListItem(String text, Class<? extends Activity> activity) {
            mText = text;
            mActivityClass = activity;
        }
    }

    class GroupViewHolder {
        public TextView mGroupName;
    }

    class ChildViewHolder {
        public TextView mChildName;
    }
}
