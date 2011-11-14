package com.qc;

import com.qc.entity.Comment;

public class MockUtil {
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

    private static String[] comments = new String[] { "这个还不错啊！", "一般一般，世界第三！", "尼玛，这两个都挺牛比的呀！",
            "好吧，就这样吧。。。", "yo!~~~", "还是第一个比较好看些，支持第一个！！！第二个显得比较抵挡比较山寨。" };

    public static Comment[] getComments() {
        Comment[] comments = new Comment[6];

        for (int i = 0; i < 3; ++i) {
            Comment comment = new Comment();
            comment.setUserIcon(postUserIcons[i]);
            comment.setUsername(postUserNames[i]);
            comment.setComment(MockUtil.comments[i]);
            comments[i] = comment;
        }

        for (int i = 3; i < 6; ++i) {
            Comment comment = new Comment();
            comment.setUserIcon(commentUserIcons[i - 3]);
            comment.setUsername(commentUserNames[i - 3]);
            comment.setComment(MockUtil.comments[i]);
            comments[i] = comment;
        }

        return comments;
    }
}
