package com.qc.square;

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
import com.qc.entity.Comment;

public class CommentActivity extends ListActivity {

    private EfficientAdapter adap;
    private Comment[] comments = MockUtil.getComments();
    private ListView listView = null;
    private int[] images = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        listView = getListView();

        LayoutInflater factory = getLayoutInflater();
        LinearLayout footer = (LinearLayout) factory.inflate(R.layout.comment_footer, null);
        listView.addFooterView(footer);

        setupPostContent();

        adap = new EfficientAdapter(this);
        setListAdapter(adap);

        Util.setListViewHeightBasedOnChildren(listView);
    }

    private void setupPostContent() {
        Bundle payload = this.getIntent().getExtras();

        ImageView postUserIcon = (ImageView) findViewById(R.id.cmnt_post_user_icon);
        postUserIcon.setImageResource(payload.getInt("cmnt_post_user_icon"));

        TextView postUserName = (TextView) findViewById(R.id.cmnt_post_user_name);
        postUserName.setText(payload.getString("cmnt_post_user_name"));

        TextView pollDesc = (TextView) findViewById(R.id.cmnt_poll_desc);
        pollDesc.setText(payload.getString("cmnt_poll_desc"));

        ImageView pollImg1 = (ImageView) findViewById(R.id.cmnt_poll_img1);
        pollImg1.setImageResource(payload.getInt("cmnt_poll_img1"));

        ImageView pollImg2 = (ImageView) findViewById(R.id.cmnt_poll_img2);
        pollImg2.setImageResource(payload.getInt("cmnt_poll_img2"));

        images = new int[2];
        images[0] = payload.getInt("cmnt_poll_img1");
        images[1] = payload.getInt("cmnt_poll_img2");

        pollImg1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Util.startActivity((Activity) CommentActivity.this,
                        "com.qc/com.qc.imageswitcher.PollImageSwitcher",
                        createBundleForPollImages(images, 0));
            }
        });

        pollImg2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Util.startActivity((Activity) CommentActivity.this,
                        "com.qc/com.qc.imageswitcher.PollImageSwitcher",
                        createBundleForPollImages(images, 1));
            }
        });
    }

    private static Bundle createBundleForPollImages(int[] images, int currentIndex) {
        Bundle bundle = new Bundle();
        bundle.putIntArray("imageIds", images);
        bundle.putInt("index", currentIndex);
        return bundle;
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(this, "Click-" + String.valueOf(position), Toast.LENGTH_SHORT).show();
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
                convertView = mInflater.inflate(R.layout.comment_cell, null);

                // Creates a ViewHolder and store references to the two children
                // views
                // we want to bind data to.
                holder = new ViewHolder();

                holder.commentUserIcon = (ImageView) convertView
                        .findViewById(R.id.cmnt_comment_user_icon);
                holder.commentUserName = (TextView) convertView
                        .findViewById(R.id.cmnt_comment_user_name);
                holder.commentContent = (TextView) convertView
                        .findViewById(R.id.cmnt_comment_content);
                holder.replyButton = (Button) convertView.findViewById(R.id.mark_read_button);

                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            // Bind the data efficiently with the holder.
            holder.commentUserIcon.setImageResource(comments[position].getUserIcon());
            holder.commentUserName.setText(comments[position].getUsername());
            holder.commentContent.setText(comments[position].getComment());

            return convertView;
        }

        class ViewHolder {
            ImageView commentUserIcon;
            TextView commentUserName;
            TextView commentContent;
            Button replyButton;
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
            return comments.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return comments[position];
        }

    }
}