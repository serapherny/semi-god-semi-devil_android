package com.qc;

import com.qc.entity.Comment;
import com.qc.entity.Notification;

public class MockUtil {

    public static Integer[] postUserIcons = new Integer[] { R.drawable.ouyang, R.drawable.qc,
            R.drawable.rc };
    public static String[] postUserNames = new String[] { "Ouyang", "Chao Qin", "Chao Ruan" };
    public static String[] pollDescs = new String[] { "帮我选选哪个好？", "今天应该买个什么？", "我穿这两个哪个更美？" };
    public static Integer[] commentUserIcons = new Integer[] { R.drawable.hss, R.drawable.sz,
            R.drawable.ss };
    public static String[] commentUserNames = new String[] { "Hu Shuishui", "Shao Zhen",
            "Song Shuo" };
    public static Integer[] postPollImages1 = new Integer[] { R.drawable.bag1, R.drawable.full1,
            R.drawable.chanel1 };
    public static Integer[] postPollImages2 = new Integer[] { R.drawable.bag2, R.drawable.full2,
            R.drawable.chanel2 };

    public static String[] commentContents = new String[] { "这个还不错啊！", "一般一般，世界第三！",
            "尼玛，这两个都挺牛比的呀！", "好吧，就这样吧。。。", "yo!~~~", "还是第一个比较好看些，支持第一个！！！第二个显得比较抵挡比较山寨。" };

    public static Comment[] comments;
    static {
        comments = new Comment[6];

        for (int i = 0; i < 3; ++i) {
            Comment comment = new Comment();
            comment.setId(i);
            comment.setUserIcon(postUserIcons[i]);
            comment.setUsername(postUserNames[i]);
            comment.setComment(MockUtil.commentContents[i]);
            comments[i] = comment;
        }

        for (int i = 3; i < 6; ++i) {
            Comment comment = new Comment();
            comment.setId(i);
            comment.setUserIcon(commentUserIcons[i - 3]);
            comment.setUsername(commentUserNames[i - 3]);
            comment.setComment(MockUtil.commentContents[i]);
            comments[i] = comment;
        }
    }

    public static Notification[] notifications;
    static {
        notifications = new Notification[4];
        notifications[0] = new Notification(0, postUserNames[0], postUserIcons[0], "给你发来一个评价请求", 0);
        notifications[1] = new Notification(1, postUserNames[1], postUserIcons[1], "在您的宝贝投票中留言", 1);
        notifications[2] = new Notification(2, postUserNames[2], postUserIcons[2], "您的宝贝有新的未读评价", 2);
        notifications[3] = new Notification(3, commentUserNames[1], commentUserIcons[1],
                "在您的宝贝投票中留言", 3);
    }

    public static Comment[] getComments() {
        return comments;
    }

    public static Comment getCommentById(int id) {
        return comments[id];
    }

    public static Notification[] getNotifications() {
        return notifications;
    }
}
