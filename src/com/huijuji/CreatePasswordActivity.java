package com.huijuji;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.FabButton;
import com.fab.views.FabTextView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class CreatePasswordActivity extends Activity
{
  private AlertDialog.Builder alertbox;
  private Context context;
  private FabTextView createPwdtxt;
  private ProgressDialog dialog;
  private String email;
  private FabTextView emailAddress;
  private FabTextView emailLable;
  private boolean isActivityRunning;
  private FabTextView message;
  private HashMap<String, String> parameters;
  private FabButton submit;
  private GoogleAnalyticsTracker tracker;

  private void initilizeUI()
  {
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("createPassword"));
    this.isActivityRunning = true;
    this.dialog = new ProgressDialog(this);
    this.alertbox = new AlertDialog.Builder(this);
    BitmapDrawable localBitmapDrawable = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), 2130837515));
    localBitmapDrawable.setTileModeX(Shader.TileMode.REPEAT);
    ((RelativeLayout)findViewById(2131361827)).setBackgroundDrawable(localBitmapDrawable);
    this.createPwdtxt = ((FabTextView)findViewById(2131361828));
    this.createPwdtxt.setFont("HelveticaNeu_bold.ttf");
    this.message = ((FabTextView)findViewById(2131361829));
    this.message.setFont("HelveticaNeu_light.ttf");
    this.emailLable = ((FabTextView)findViewById(2131361830));
    this.emailLable.setFont("HelveticaNeu_bold.ttf");
    this.emailAddress = ((FabTextView)findViewById(2131361831));
    this.emailAddress.setFont("HelveticaNeu_bold.ttf");
    try
    {
      this.email = FabUtils.getUserInfo(this.context).getString("email");
      this.emailAddress.setText(this.email);
      this.submit = ((FabButton)findViewById(2131361832));
      this.submit.setFont("HelveticaNeu_bold.ttf");
      this.submit.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          CreatePasswordActivity.this.forgotPassword();
        }
      });
      return;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  protected void forgotPassword()
  {
    if (this.isActivityRunning)
    {
      this.dialog.setCancelable(true);
      this.dialog.setMessage("Submitting...");
      this.dialog.show();
    }
    final ExecuteRequestAsyncTask localExecuteRequestAsyncTask = new ExecuteRequestAsyncTask(this.context, "POST");
    this.parameters = new HashMap();
    this.parameters.put("d", FabSharedPrefs.getDeviceDensity());
    this.parameters.put("app_version", FabApplication.APP_VERSION);
    this.parameters.put("device_model", Build.MODEL);
    this.parameters.put("device_os", Build.VERSION.RELEASE);
    this.parameters.put("email", this.email);
    localExecuteRequestAsyncTask.setParameters(this.parameters);
    localExecuteRequestAsyncTask.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      public void onTaskComplete(Object paramAnonymousObject)
      {
        if ((CreatePasswordActivity.this.isActivityRunning) && (CreatePasswordActivity.this.dialog != null) && (CreatePasswordActivity.this.dialog.isShowing()))
          CreatePasswordActivity.this.dialog.cancel();
        JSONObject localJSONObject;
        if ((paramAnonymousObject != null) && ((paramAnonymousObject instanceof JSONObject)))
          localJSONObject = (JSONObject)paramAnonymousObject;
        while (true)
        {
          try
          {
            if ((TextUtils.isEmpty(localJSONObject.getString("err_msg"))) && (!TextUtils.isEmpty(localJSONObject.getString("success_msg"))))
            {
              CreatePasswordActivity.this.alertbox.setIcon(2130837507);
              CreatePasswordActivity.this.alertbox.setTitle("Success!");
              CreatePasswordActivity.this.alertbox.setMessage(localJSONObject.getString("success_msg"));
              CreatePasswordActivity.this.alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                {
                  CreatePasswordActivity.this.finish();
                }
              });
              if (CreatePasswordActivity.this.isActivityRunning)
                CreatePasswordActivity.this.alertbox.show();
              if (CreatePasswordActivity.this.parameters != null)
                CreatePasswordActivity.this.parameters.clear();
              return;
            }
            if (TextUtils.isEmpty(localJSONObject.getString("err_msg")))
              continue;
            CreatePasswordActivity.this.alertbox.setIcon(2130837504);
            CreatePasswordActivity.this.alertbox.setTitle("Oops!");
            CreatePasswordActivity.this.alertbox.setMessage(localJSONObject.getString("err_msg"));
            CreatePasswordActivity.this.alertbox.setNeutralButton("OK", null);
            if (!CreatePasswordActivity.this.isActivityRunning)
              continue;
            CreatePasswordActivity.this.alertbox.show();
            continue;
          }
          catch (JSONException localJSONException)
          {
            continue;
          }
          FabUtils.showAlert(CreatePasswordActivity.this.context, localExecuteRequestAsyncTask.getErrMsg());
        }
      }
    });
    String[] arrayOfString = new String[1];
    arrayOfString[0] = FabUtils.requestPostUrl(false, "/mobile/password-reset/");
    localExecuteRequestAsyncTask.execute(arrayOfString);
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903046);
    this.context = this;
    initilizeUI();
  }

  protected void onDestroy()
  {
    this.tracker.stopSession();
    super.onDestroy();
  }

  protected void onPause()
  {
    this.isActivityRunning = false;
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.cancel();
    super.onPause();
  }

  protected void onRestart()
  {
    this.isActivityRunning = true;
    super.onRestart();
    if (FabUtils.isLoggedIn(this.context))
      finish();
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.CreatePasswordActivity
 * JD-Core Version:    0.6.2
 */