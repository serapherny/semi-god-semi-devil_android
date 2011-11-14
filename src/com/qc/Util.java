package com.qc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class Util {
    public static void launchNativeApp(Activity activity, String component, Bundle payload) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(ComponentName.unflattenFromString(component));
        intent.addCategory("android.intent.category.LAUNCHER");

        if (payload != null) {
            intent.putExtras(payload);
        }

        activity.startActivity(intent);
    }
}
