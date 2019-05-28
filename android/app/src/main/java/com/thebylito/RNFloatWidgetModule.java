
package com.thebylito;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNFloatWidgetModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactContext;

    public RNFloatWidgetModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNFloatWidget";
    }

    @ReactMethod
    public void start() {
        Intent service = new Intent(reactContext.getApplicationContext(), FloatingWidgetShowService.class);
        reactContext.startService(service);
    }

    @ReactMethod
    public void openWidget() {
        Intent intent = new Intent("ACTION_OPEN_WIDGET", null, reactContext, FloatingWidgetShowService.class);
        reactContext.startService(intent);
    }

    @ReactMethod
    public void closeWidget() {
        Intent intent = new Intent("ACTION_CLOSE_WIDGET", null, reactContext, FloatingWidgetShowService.class);
        reactContext.startService(intent);
    }

    @ReactMethod
    public void createButton(){
        Intent intent = new Intent("ACTION_CREATE_BUTTON", null, reactContext, FloatingWidgetShowService.class);
        reactContext.startService(intent);
    }
}