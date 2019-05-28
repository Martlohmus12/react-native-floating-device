package com.thebylito;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.graphics.PixelFormat;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.floatingwidget.MainActivity;
import com.floatingwidget.R;
import com.squareup.picasso.Picasso;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;

import java.util.Date;
import java.util.Random;


public class FloatingWidgetShowService extends Service {


    WindowManager windowManager;
    View floatingView;
    View closeAreaView;
    TextView widgetTitle, widgetBody;
    ImageView closeArea;
    ImageButton imageButton;
    ImageButton button;
    WindowManager.LayoutParams params;
    WindowManager.LayoutParams closeAreaViewParams;
    ReactContext reactContext = null;
    private GestureDetector gestureDetector;

    public FloatingWidgetShowService() {
    }

    private void openWidget() {
        floatingView.setVisibility(View.VISIBLE);
    }

    private void closeWidget() {
        floatingView.setVisibility(View.GONE);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case "ACTION_OPEN_WIDGET": {
                    openWidget();
                    break;
                }
                case "ACTION_CLOSE_WIDGET": {
                    closeWidget();
                    break;
                }
            }
        }
        return START_STICKY;
    }

    // private void show(View view, int left, int top) {
    //     if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
    //         preOreoShow(view, left, top);
    //     } else {
    //         oreoShow(view, left, top);
    //     }
    // }

    // private void preOreoShow(View view, int left, int top) {
    //     WindowManager.LayoutParams params = new WindowManager.LayoutParams(
    //             WindowManager.LayoutParams.WRAP_CONTENT,
    //             WindowManager.LayoutParams.WRAP_CONTENT,
    //             WindowManager.LayoutParams.TYPE_PHONE,
    //             WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    //                     | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
    //                     | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
    //                     | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
    //             PixelFormat.TRANSLUCENT);
    //     params.gravity = Gravity.START | Gravity.TOP;
    //     params.x = left;
    //     params.y = top;
    //     windowManager.addView(view, params);
    // }

    // @TargetApi(Build.VERSION_CODES.O)
    // private void oreoShow(View view, int left, int top) {
    //     WindowManager.LayoutParams params = new WindowManager.LayoutParams(
    //             WindowManager.LayoutParams.WRAP_CONTENT,
    //             WindowManager.LayoutParams.WRAP_CONTENT,
    //             WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
    //             WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    //                     | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
    //                     | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
    //                     | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
    //             PixelFormat.TRANSLUCENT);


    //     params.gravity = Gravity.START | Gravity.TOP;
    //     params.x = left;
    //     params.y = top;
    //     windowManager.addView(view, params);
    // }

    @Override
    public void onCreate() {
        super.onCreate();
        final ReactInstanceManager reactInstanceManager =
                getReactNativeHost().getReactInstanceManager();
        ReactContext getReactContext = reactInstanceManager.getCurrentReactContext();
        reactContext = getReactContext;

        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_widget_layout, null);
        closeAreaView = LayoutInflater.from(this).inflate(R.layout.close_area_layout, null);
        
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        closeAreaViewParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        closeAreaViewParams.x = 0;
        closeAreaViewParams.y = 1200;
        closeAreaView.setVisibility(View.GONE);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        assert windowManager != null;
        windowManager.addView(floatingView, params);
        windowManager.addView(closeAreaView, closeAreaViewParams);

        gestureDetector = new GestureDetector(this, new SingleTapConfirm());

        // ImageButton closeArea = (ImageButton) closeAreaView.findViewById(R.id.closeView);

        floatingView.findViewById(R.id.button1).setOnTouchListener(new View.OnTouchListener() {
            int X_Axis, Y_Axis;
            float TouchX, TouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

               if (gestureDetector.onTouchEvent(event)) {

                    return true;
                } else {
                    switch (event.getAction()) {


                        case MotionEvent.ACTION_DOWN:
                            X_Axis = params.x;
                            Y_Axis = params.y;
                            TouchX = event.getRawX();
                            TouchY = event.getRawY();
                            return false;

                        case MotionEvent.ACTION_UP:
                            closeAreaView.setVisibility(View.GONE);

                            return false;

                        case MotionEvent.ACTION_MOVE:
                            //half the screen size
                            if (event.getRawY() > 1000) {
                                closeAreaView.setVisibility(View.VISIBLE);
                            } else {
                                closeAreaView.setVisibility(View.GONE);
                            }

                            if (event.getRawY() > 1300) {
                                // Toast.makeText(FloatingWidgetShowService.this, "Close button " + event.getRawY(), Toast.LENGTH_SHORT).show();
                                params.x = 100;
                                params.y = 100;
                                if (floatingView != null) closeWidget();
                            } else {
                                params.x = X_Axis + (int) (event.getRawX() - TouchX);
                                params.y = Y_Axis + (int) (event.getRawY() - TouchY);
                            }

                            windowManager.updateViewLayout(floatingView, params);
                            return true;
                    }
                    
                    return false;
                }

            }
        });
    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null) windowManager.removeView(floatingView);
    }

    protected ReactNativeHost getReactNativeHost() {
        return ((ReactApplication) getApplication()).getReactNativeHost();
    }

    private class SingleTapConfirm extends SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }
}
