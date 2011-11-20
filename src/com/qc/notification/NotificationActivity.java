package com.qc.notification;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qc.MockUtil;
import com.qc.R;
import com.qc.Util;
import com.qc.entity.Notification;
import com.qc.square.CommentActivity;

public class NotificationActivity extends ListActivity {

    private EfficientAdapter adap = null;
    private Notification[] notifications = MockUtil.getNotifications();
    private ListView listView = null;
    private Button clearNotificationButton = null;
    private Button markNotificationButton = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        listView = getListView();

        LayoutInflater factory = getLayoutInflater();
        LinearLayout footer = (LinearLayout) factory.inflate(R.layout.notification_footer, null);
        listView.addFooterView(footer);

        adap = new EfficientAdapter(this);
        setListAdapter(adap);

        Util.setListViewHeightBasedOnChildren(listView);

        setupButtons();
    }

    private void setupButtons() {
        clearNotificationButton = (Button) findViewById(R.id.clear_notification_button);
        markNotificationButton = (Button) findViewById(R.id.notification_mark_read_button);

        clearNotificationButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                notifications = new Notification[0];
                adap.notifyDataSetChanged();
            }
        });

        markNotificationButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                notifications = MockUtil.getNotifications();
                adap.notifyDataSetChanged();
            }
        });
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Toast.makeText(this, "Click-" + String.valueOf(position),
        // Toast.LENGTH_SHORT).show();
        Util.launchNativeApp((Activity) NotificationActivity.this,
                "com.qc/com.qc.square.CommentActivity", createBundle(position));
    }

    private Bundle createBundle(int position) {
        position = (position + 1) % 3;
        Bundle bundle = new Bundle();

        bundle.putInt("cmnt_post_user_icon", MockUtil.postUserIcons[position]);
        bundle.putString("cmnt_post_user_name", MockUtil.postUserNames[position]);
        bundle.putString("cmnt_poll_desc", MockUtil.pollDescs[position]);
        bundle.putInt("cmnt_poll_img1", MockUtil.postPollImages1[position]);
        bundle.putInt("cmnt_poll_img2", MockUtil.postPollImages2[position]);

        return bundle;
    }

    public class EfficientAdapter extends BaseAdapter implements Filterable {
        private LayoutInflater mInflater;
        private Context context;

        public EfficientAdapter(Context context) {
            // Cache the LayoutInflate to avoid asking for a new one each time.
            mInflater = LayoutInflater.from(context);
            this.context = context;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            // A ViewHolder keeps references to children views to avoid
            // unneccessary calls
            // to findViewById() on each row.
            ViewHolder holder;

            // When convertView is not null, we can reuse it directly, there is
            // no need
            // to reinflate it. We only inflate a new View when the convertView
            // supplied
            // by ListView is null.
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.notification_cell, null);

                // Creates a ViewHolder and store references to the two children
                // views
                // we want to bind data to.
                holder = new ViewHolder();

                holder.notificationUserIcon = (ImageView) convertView
                        .findViewById(R.id.notification_user_icon);
                holder.notificationUserName = (TextView) convertView
                        .findViewById(R.id.notification_user_name);
                holder.notificationContent = (TextView) convertView
                        .findViewById(R.id.notification_content);

                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            // Bind the data efficiently with the holder.
            holder.notificationUserIcon.setImageResource(notifications[position].getUserIcon());
            holder.notificationUserName.setText(notifications[position].getUsername());
            holder.notificationContent.setText(notifications[position].getContent());

            return convertView;
        }

        class ViewHolder {
            ImageView notificationUserIcon;
            TextView notificationUserName;
            TextView notificationContent;
        }

        @Override
        public Filter getFilter() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return notifications.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return notifications[position];
        }

    }
}