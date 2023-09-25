package com.nativefirebase;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactActivityDelegate;

import android.content.Intent;
import android.os.Bundle;
import com.emekalites.react.alarm.notification.BundleJSONConverter;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import org.json.JSONObject;

public class MainActivity extends ReactActivity {

  /**
   * Returns the name of the main component registered from JavaScript. This is
   * used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "nativefirebase";
  }

  /**
   * Returns the instance of the {@link ReactActivityDelegate}. Here we use a util
   * class {@link
   * DefaultReactActivityDelegate} which allows you to easily enable Fabric and
   * Concurrent React
   * (aka React 18) with two boolean flags.
   */
  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new DefaultReactActivityDelegate(
        this,
        getMainComponentName(),
        // If you opted-in for the New Architecture, we enable the Fabric Renderer.
        DefaultNewArchitectureEntryPoint.getFabricEnabled());
  }

  @Override
  public void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    try {
      Bundle bundle = intent.getExtras();
      if (bundle != null) {
        JSONObject data = BundleJSONConverter.convertToJSON(bundle);
        getReactInstanceManager().getCurrentReactContext()
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit("OnNotificationOpened", data.toString());
      }
    } catch (Exception e) {
      System.err.println("Exception when handling notification opened. " + e);
    }
  }
}