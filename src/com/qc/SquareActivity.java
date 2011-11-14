package com.qc;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SquareActivity extends ListActivity {

    private EfficientAdapter adap;
    private static String[] data = new String[] { "0", "1", "2" };
    private static Integer[] postUserIcons = new Integer[] { R.drawable.ouyang, R.drawable.qc,
            R.drawable.rc };
    private static String[] postUserNames = new String[] { "Ouyang", "Chao Qin", "Chao Ruan" };
    private static String[] pollDescs = new String[] { "帮我选选哪个好？", "今天应该买个什么？", "我穿这两个哪个更美？" };
    private static Integer[] commentUserIcons = new Integer[] { R.drawable.hss, R.drawable.sz,
            R.drawable.ss };
    private static String[] commentUserNames = new String[] { "Hu Shuishui", "Shao Zhen",
            "Song Shuo" };
    private static String[] commentContents = new String[] { "牛必", "尼玛", "还不错喔！" };
    private static Integer[] postPollImages1 = new Integer[] { R.drawable.bag1, R.drawable.full1,
            R.drawable.chanel1 };
    private static Integer[] postPollImages2 = new Integer[] { R.drawable.bag2, R.drawable.full2,
            R.drawable.chanel2 };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.square);
        adap = new EfficientAdapter(this);
        setListAdapter(adap);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(this, "Click-" + String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

    public static class EfficientAdapter extends BaseAdapter implements Filterable {
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
                convertView = mInflater.inflate(R.layout.square_list_cell, null);

                // Creates a ViewHolder and store references to the two children
                // views
                // we want to bind data to.
                holder = new ViewHolder();

                holder.postUserIcon = (ImageView) convertView.findViewById(R.id.post_user_icon);
                holder.postUserName = (TextView) convertView.findViewById(R.id.post_user_name);

                holder.pollImg1 = (ImageView) convertView.findViewById(R.id.poll_img1);
                holder.pollImg2 = (ImageView) convertView.findViewById(R.id.poll_img2);
                holder.pollDesc = (TextView) convertView.findViewById(R.id.poll_desc);
                holder.commentUserIcon = (ImageView) convertView
                        .findViewById(R.id.comment_user_icon);
                holder.commentUserName = (TextView) convertView
                        .findViewById(R.id.comment_user_name);
                holder.commentContent = (TextView) convertView.findViewById(R.id.comment_content);
                holder.moreCommentButton = (Button) convertView
                        .findViewById(R.id.more_comment_button);

                /*
                 * convertView.setOnClickListener(new OnClickListener() {
                 * private int pos = position;
                 *
                 * @Override public void onClick(View v) {
                 * Toast.makeText(context, "Click-" + String.valueOf(pos),
                 * Toast.LENGTH_SHORT) .show(); } });
                 */
                holder.pollImg1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util.launchNativeApp((Activity) context,
                                "com.qc/com.qc.imageswitcher.ImageSwitcherA",
                                createBundle(position, 0));
                    }
                });

                holder.pollImg2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util.launchNativeApp((Activity) context,
                                "com.qc/com.qc.imageswitcher.ImageSwitcherA",
                                createBundle(position, 1));
                    }
                });

                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            // Bind the data efficiently with the holder.
            holder.postUserIcon.setImageResource(postUserIcons[position]);
            holder.postUserName.setText(postUserNames[position]);

            holder.pollImg1.setImageResource(postPollImages1[position]);
            holder.pollImg2.setImageResource(postPollImages2[position]);
            holder.pollDesc.setText(pollDescs[position]);

            holder.commentUserIcon.setImageResource(commentUserIcons[position]);
            holder.commentUserName.setText(commentUserNames[position]);
            holder.commentContent.setText(commentContents[position]);

            return convertView;
        }

        static private Bundle createBundle(int position, int currentIndex) {
            Bundle bundle = new Bundle();
            bundle.putIntArray("imageIds", new int[] { postPollImages1[position],
                    postPollImages2[position] });
            bundle.putInt("index", currentIndex);
            return bundle;
        }

        static class ViewHolder {
            ImageView postUserIcon;
            TextView postUserName;

            ImageView pollImg1;
            ImageView pollImg2;
            TextView pollDesc;

            ImageView commentUserIcon;
            TextView commentUserName;
            TextView commentContent;
            Button moreCommentButton;

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
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data[position];
        }

    }
}