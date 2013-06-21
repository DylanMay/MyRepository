package com.fab;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;
import com.fab.facebook.FabFBClient;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabHelper;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.FabButton;
import com.fab.views.FabTextView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class FabIndex extends Activity
{
  private FabButton btnViewSales;
  private FabButton buttonRegister;
  private Context context;
  private Bundle extras;
  protected FabFBClient fbClient;
  private FabButton fbConnect;
  private ScrollView holderView;
  private boolean isActivityRunning;
  protected boolean isFBConnectInProcess;
  private boolean isFromGuest;
  private Handler mHandler;
  private ProgressBar pBar;
  private HashMap<String, String> parameters;
  private GoogleAnalyticsTracker tracker;

  protected void loginAsGuestUser()
  {
    this.pBar.setVisibility(0);
    final ExecuteRequestAsyncTask localExecuteRequestAsyncTask = new ExecuteRequestAsyncTask(this.context, "POST");
    this.parameters = new HashMap();
    this.parameters.put("d", FabSharedPrefs.getDeviceDensity());
    this.parameters.put("app_version", FabApplication.APP_VERSION);
    this.parameters.put("device_model", Build.MODEL);
    this.parameters.put("device_os", Build.VERSION.RELEASE);
    this.parameters.put("un_or_email", "guestuser@fab.com".trim());
    this.parameters.put("password", "189d054da940e4d07ea80b62e6d0a525".trim());
    localExecuteRequestAsyncTask.setParameters(this.parameters);
    localExecuteRequestAsyncTask.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      public void onTaskComplete(Object paramAnonymousObject)
      {
        FabIndex.this.pBar.setVisibility(8);
        JSONObject localJSONObject;
        if ((paramAnonymousObject != null) && ((paramAnonymousObject instanceof JSONObject)))
          localJSONObject = (JSONObject)paramAnonymousObject;
        while (true)
        {
          try
          {
            if ((TextUtils.isEmpty(localJSONObject.getString("err_msg"))) && (FabUtils.isLoggedIn(FabIndex.this.context)))
            {
              FabSharedPrefs.setGuestUser(true);
              FabSharedPrefs.setGuestUserNotifications(true);
              FabUtils.setAlarmForPullNotification(FabIndex.this.getApplicationContext(), 0L);
              FabUtils.setAlarm(FabIndex.this.context);
              Intent localIntent = new Intent(FabIndex.this.context, FabHome.class);
              FabIndex.this.startActivity(localIntent);
              FabIndex.this.finish();
              if (FabIndex.this.parameters != null)
                FabIndex.this.parameters.clear();
              return;
            }
            Toast.makeText(FabIndex.this.context, localJSONObject.getString("err_msg"), 0).show();
            continue;
          }
          catch (JSONException localJSONException)
          {
            continue;
          }
          FabUtils.showAlert(FabIndex.this.context, localExecuteRequestAsyncTask.getErrMsg());
        }
      }
    });
    String[] arrayOfString = new String[1];
    arrayOfString[0] = FabUtils.requestPostUrl(true, "/mobile/login/");
    localExecuteRequestAsyncTask.execute(arrayOfString);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (this.fbClient != null)
      this.fbClient.authorizeCallback(paramInt1, paramInt2, paramIntent);
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903068);
    this.context = this;
    this.isActivityRunning = true;
    this.isFBConnectInProcess = false;
    if (TextUtils.isEmpty(FabSharedPrefs.getDeviceDensity()))
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
      FabSharedPrefs.setDeviceDensity(localDisplayMetrics.densityDpi);
    }
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("logoutHome"));
    FabEula.show(this);
    this.extras = getIntent().getExtras();
    if (this.extras != null)
      this.isFromGuest = this.extras.getBoolean("IS_FROM_GUEST");
    BitmapDrawable localBitmapDrawable = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), 2130837515));
    localBitmapDrawable.setTileModeX(Shader.TileMode.REPEAT);
    this.holderView = ((ScrollView)findViewById(2131361827));
    this.holderView.setBackgroundDrawable(localBitmapDrawable);
    ((FabTextView)findViewById(2131361941)).setFont("HelveticaNeu_light.ttf");
    FabTextView localFabTextView = (FabTextView)findViewById(2131361942);
    localFabTextView.setFont("HelveticaNeu_bold.ttf");
    localFabTextView.setText(Html.fromHtml(getResources().getString(2131165226)));
    this.pBar = ((ProgressBar)findViewById(2131361946));
    this.btnViewSales = ((FabButton)findViewById(2131361945));
    this.btnViewSales.setFont("HelveticaNeu_bold.ttf");
    this.btnViewSales.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if ((FabSharedPrefs.isGuestUser()) && (FabUtils.isLoggedIn(FabIndex.this.context)))
          if (!FabIndex.this.isFromGuest)
          {
            Intent localIntent = new Intent(FabIndex.this.context, FabHome.class);
            FabIndex.this.startActivity(localIntent);
          }
        while (true)
        {
          return;
          FabIndex.this.finish();
          continue;
          FabIndex.this.loginAsGuestUser();
        }
      }
    });
    this.buttonRegister = ((FabButton)findViewById(2131361891));
    this.buttonRegister.setFont("HelveticaNeu_bold.ttf");
    this.buttonRegister.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent(FabIndex.this.context, FabSignin.class);
        localIntent.putExtra("IS_FROM_GUEST", FabIndex.this.isFromGuest);
        FabIndex.this.startActivity(localIntent);
      }
    });
    this.fbConnect = ((FabButton)findViewById(2131361892));
    this.fbConnect.setFont("HelveticaNeu_bold.ttf");
    this.fbConnect.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        FabIndex.this.isFBConnectInProcess = true;
        FabIndex.this.fbClient = FabHelper.connectToFacebook(FabIndex.this.context, false, FabIndex.this.mHandler, FabIndex.this.isFromGuest);
      }
    });
    this.mHandler = new Handler()
    {
      private ProgressDialog dialog;

      public void handleMessage(Message paramAnonymousMessage)
      {
        Bundle localBundle = paramAnonymousMessage.getData();
        if (paramAnonymousMessage.what == 2)
          FabIndex.this.fbClient.buildFBConnectScene3Dialog(new AlertDialog.Builder(FabIndex.this.context));
        while (true)
        {
          return;
          if (paramAnonymousMessage.what == 8)
          {
            if (FabIndex.this.isActivityRunning)
            {
              this.dialog = new ProgressDialog(FabIndex.this.context);
              this.dialog.setCancelable(true);
              this.dialog.setMessage("Signing in...");
              this.dialog.show();
            }
          }
          else if (paramAnonymousMessage.what == 9)
          {
            FabIndex.this.isFBConnectInProcess = false;
            if ((FabIndex.this.isActivityRunning) && (this.dialog != null) && (this.dialog.isShowing()))
              this.dialog.cancel();
          }
          else if (paramAnonymousMessage.what == 6)
          {
            FabIndex.this.finish();
          }
          else if (paramAnonymousMessage.what == 12)
          {
            FabIndex.this.isFBConnectInProcess = false;
            if (FabIndex.this.isActivityRunning)
            {
              AlertDialog.Builder localBuilder = new AlertDialog.Builder(FabIndex.this.context);
              localBuilder.setIcon(2130837504);
              localBuilder.setTitle("Fab");
              localBuilder.setMessage(localBundle.getString("ERROR_MSG"));
              localBuilder.setPositiveButton("Ok", null);
              localBuilder.show();
            }
          }
        }
      }
    };
  }

  protected void onDestroy()
  {
    this.isActivityRunning = false;
    this.tracker.stopSession();
    super.onDestroy();
  }

  protected void onPause()
  {
    super.onPause();
    this.isActivityRunning = false;
  }

  protected void onRestart()
  {
    super.onRestart();
    this.isActivityRunning = true;
    if ((FabUtils.isLoggedIn(this.context)) && (!this.isFromGuest) && (!this.isFBConnectInProcess))
      finish();
  }

  protected void onResume()
  {
    super.onResume();
    this.isActivityRunning = true;
    if ((FabUtils.isLoggedIn(this.context)) && ((!this.isFromGuest) || (!FabSharedPrefs.isGuestUser())) && (!this.isFBConnectInProcess))
      finish();
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.FabIndex
 * JD-Core Version:    0.6.2
 */