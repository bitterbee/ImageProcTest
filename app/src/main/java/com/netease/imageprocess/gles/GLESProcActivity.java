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

public class GLESProcActivity extends Activity {
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
        handleTouchOnImg();

        // create GL view
        glView = new GLSurfaceView(this);
        glView.setEGLContextClientVersion(2);
        glRenderer = new GLRenderer(this);
        glView.setRenderer(glRenderer);
        glRenderer.setView(glView);
        glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        glView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTouchOnImg();
            }
        });

        // set up image view
        ivInput = (ImageView) findViewById(R.id.iv_input);
        ivInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTouchOnImg();
            }
        });

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

    private void handleTouchOnImg() {
        Log.i(TAG, "Touch on image");

        if (!testsRan) {
            setContentView(glView);

            runTests();
            testsRan = true;

            Log.i(TAG, "Content view is now: glView");
        } else {
            testsRan = false;
            lastResultBm = null;

            if (glRenderer != null) {
                lastResultBm = glRenderer.getResultBm();
            }

            if (lastResultBm != null) {
                Log.i(TAG, "Updating image view");
                ivOutput.setImageBitmap(lastResultBm);
            }

            setContentView(R.layout.activity_gles_process);

            Log.i(TAG, "Content view is now: main");
        }
    }

    private void runTests() {
        glRenderer.runTests();
    }
}
