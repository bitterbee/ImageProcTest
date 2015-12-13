package com.netease.imageprocess.gles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.netease.imageprocess.R;
import com.netease.imageprocess.gles.gl.GLRenderer;

/**
 * Created by zyl06 on 12/13/15.
 */
public class GLESProcActivity extends Activity
        implements View.OnClickListener {

    private static final String TAG = "GLESProcActivity";

    private GLSurfaceView glView = null;
    private GLRenderer glRenderer = null;
    private ImageView ivInput = null;
    private ImageView ivOutput = null;

    private Bitmap lastResultBm = null;

    private boolean testsRan = true;

    public static void start(Context context) {
        Intent intent = new Intent(context, GLESProcActivity.class);
        context.startActivity(intent);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // sets defaults
        setContentView(R.layout.activity_gles_process);

        testsRan = false;
        lastResultBm = null;

        // create GL view
        glView = (GLSurfaceView) findViewById(R.id.glview);
        glView.setEGLContextClientVersion(2);
        glRenderer = new GLRenderer(this);
        glView.setRenderer(glRenderer);
        glRenderer.setView(glView);
        glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        glRenderer.runTests();


        // set up image view
        ivInput = (ImageView) findViewById(R.id.iv_input);
        ivInput.setOnClickListener(this);

        ivOutput = (ImageView) findViewById(R.id.iv_output);
    }

    @Override
    protected void onPause() {
        super.onPause();

        glView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        glView.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_input: {
                handleTouchOnImg();
            }
            break;
            case R.id.iv_output: {

            }
            break;
            default:
                break;
        }
    }

    private void handleTouchOnImg() {
        if (glRenderer != null) {
            glRenderer.runTests();
            lastResultBm = glRenderer.getResultBm();
        }

        if (lastResultBm != null) {
            Log.i(TAG, "Updating image view");
            ivOutput.setImageBitmap(lastResultBm);
        }

        Log.i(TAG, "Content view is now: main");
    }
}
