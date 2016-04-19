package org.mazhuang.androiduidemos;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MainActivity extends ExpandableListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListAdapter(new MyExpandableListAdapter());
    }

    class MyExpandableListAdapter extends BaseExpandableListAdapter {
        private String[] mGroups = {
            "Fragments"
        };

        private ListItem[][] mChildren = {
            {
                new ListItem("update ui after popBackStack", MainActivity.class)
            }
        };

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
                convertView = LayoutInflater.from(MainActivity.this)
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
                convertView = LayoutInflater.from(MainActivity.this)
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
            return false;
        }
    }

    class ListItem {
        private String mText;
        private Class<? extends Activity> mActivity;
        public ListItem(String text, Class<? extends Activity> activity) {
            mText = text;
            mActivity = activity;
        }
    }

    class GroupViewHolder {
        public TextView mGroupName;
    }

    class ChildViewHolder {
        public TextView mChildName;
    }
}
