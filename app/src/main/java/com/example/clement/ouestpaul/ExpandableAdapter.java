package com.example.clement.ouestpaul;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by Adrien on 14/01/2015.
 */
@SuppressWarnings("unchecked")
public class ExpandableAdapter extends BaseExpandableListAdapter{

        public ArrayList<String> groupItem, tempChild;
        public ArrayList<Object> Childtem = new ArrayList<Object>();
        public LayoutInflater minflater;
        public Activity activity;
    public TypedArray listIcon;
        private final Context context;

        public ExpandableAdapter(Context _context,ArrayList<String> grList, ArrayList<Object> childItem) {
            this.context = _context;
            groupItem = grList;
            this.Childtem = childItem;
            listIcon = context.getResources().obtainTypedArray(R.array.icons_array);
        }

        public void setInflater(LayoutInflater mInflater, Activity act) {
            this.minflater = mInflater;
            activity = act;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
           // return this.Childtem.get(this.groupItem.get(groupPosition)).get(childPosition);
            return null;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            tempChild = (ArrayList<String>) Childtem.get(groupPosition);
            if (tempChild.size() > 0) {
                if (convertView == null) {
                    convertView = minflater.inflate(R.layout.drawer_list_item_child, parent, false);
                }

                TextView text = (TextView) convertView;
                text.setText(tempChild.get(childPosition));
//		convertView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(activity, tempChild.get(childPosition),
//						Toast.LENGTH_SHORT).show();
//			}
//		});
                convertView.setTag(tempChild.get(childPosition));
            }
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return ((ArrayList<String>) Childtem.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public int getGroupCount() {
            return groupItem.size();
        }

        @Override
        public void onGroupCollapsed(int groupPosition) {
            super.onGroupCollapsed(groupPosition);
        }

        @Override
        public void onGroupExpanded(int groupPosition) {
            super.onGroupExpanded(groupPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            Log.d("MyApp", "getGroupView group : " + groupPosition+ " size : "+isExpanded);
           //if (groupPosition >= getGroupCount())
             //     groupPosition = getGroupCount() - 1;
               if (convertView == null) {
                   convertView = minflater.inflate(R.layout.drawer_list_item, parent, false);
               }
            ImageView icon = (ImageView)convertView.findViewById(R.id.groupImg);
            TextView text = (TextView) convertView.findViewById(R.id.text1);
            icon.setImageDrawable(listIcon.getDrawable(groupPosition));
               text.setText(groupItem.get(groupPosition));
               convertView.setTag(groupItem.get(groupPosition));
         //  }
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

}
