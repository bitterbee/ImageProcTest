package com.netease.imageprocess.normal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by zyl06 on 12/13/15.
 */
public class NormalProcActivity extends Activity {
    public static void start(Context context) {
        Intent intent = new Intent(context, NormalProcActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
