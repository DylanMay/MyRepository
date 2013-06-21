package com.fab;

import android.app.Activity;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.FabButton;
import com.fab.views.FabEditText;
import com.fab.views.FabTextView;
import com.fab.views.TitleWidget;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class FabForgotPassword extends Activity
{
  private String TAG = "FAB_FORGOT_PASSWORD";
  private AlertDialog.Builder alertbox;
  private FabButton buttonSubmit;
  private Context context;
  private ProgressDialog dialog;
  private FabTextView fpTitle;
  private InputMethodManager inputManager;
  private boolean isActivityRunning;
  private HashMap<String, String> parameters;
  private TitleWidget titleWidget;
  private GoogleAnalyticsTracker tracker;
  private FabEditText userEmail;

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
    this.parameters.put("email", this.userEmail.getText().toString().trim());
    localExecuteRequestAsyncTask.setParameters(this.parameters);
    localExecuteRequestAsyncTask.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      public void onTaskComplete(Object paramAnonymousObject)
      {
        if ((FabForgotPassword.this.isActivityRunning) && (FabForgotPassword.this.dialog != null) && (FabForgotPassword.this.dialog.isShowing()))
          FabForgotPassword.this.dialog.cancel();
        JSONObject localJSONObject;
        if ((paramAnonymousObject != null) && ((paramAnonymousObject instanceof JSONObject)))
        {
          localJSONObject = (JSONObject)paramAnonymousObject;
          FabUtils.log(FabForgotPassword.this.TAG, localJSONObject.toString());
        }
        while (true)
        {
          try
          {
            if ((TextUtils.isEmpty(localJSONObject.getString("err_msg"))) && (!TextUtils.isEmpty(localJSONObject.getString("success_msg"))))
            {
              FabForgotPassword.this.alertbox.setIcon(2130837507);
              FabForgotPassword.this.alertbox.setTitle("Success!");
              FabForgotPassword.this.alertbox.setMessage(localJSONObject.getString("success_msg"));
              FabForgotPassword.this.alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                {
                  FabForgotPassword.this.finish();
                  FabForgotPassword.this.inputManager.hideSoftInputFromWindow(FabForgotPassword.this.userEmail.getWindowToken(), 0);
                }
              });
              if (FabForgotPassword.this.isActivityRunning)
                FabForgotPassword.this.alertbox.show();
              if (FabForgotPassword.this.parameters != null)
                FabForgotPassword.this.parameters.clear();
              return;
            }
            if (TextUtils.isEmpty(localJSONObject.getString("err_msg")))
              continue;
            FabForgotPassword.this.alertbox.setIcon(2130837504);
            FabForgotPassword.this.alertbox.setTitle("Oops!");
            FabForgotPassword.this.alertbox.setMessage(localJSONObject.getString("err_msg"));
            FabForgotPassword.this.alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
              {
                FabForgotPassword.this.userEmail.setText("");
                FabForgotPassword.this.validateForm();
                FabForgotPassword.this.inputManager.toggleSoftInput(2, 1);
              }
            });
            if (!FabForgotPassword.this.isActivityRunning)
              continue;
            FabForgotPassword.this.alertbox.show();
            continue;
          }
          catch (JSONException localJSONException)
          {
            continue;
          }
          FabUtils.showAlert(FabForgotPassword.this.context, localExecuteRequestAsyncTask.getErrMsg());
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
    setContentView(2130903061);
    this.context = this;
    this.isActivityRunning = true;
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("forgotPassword"));
    this.titleWidget = ((TitleWidget)findViewById(2131361810));
    this.titleWidget.getCartButton().setVisibility(8);
    this.titleWidget.getBrowseButton().setVisibility(8);
    this.fpTitle = ((FabTextView)findViewById(2131361903));
    this.fpTitle.setFont("HelveticaNeu_bold.ttf");
    this.userEmail = ((FabEditText)findViewById(2131361830));
    this.buttonSubmit = ((FabButton)findViewById(2131361905));
    this.buttonSubmit.setFont("HelveticaNeu_bold.ttf");
    BitmapDrawable localBitmapDrawable = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), 2130837515));
    localBitmapDrawable.setTileModeX(Shader.TileMode.REPEAT);
    ((ScrollView)findViewById(2131361827)).setBackgroundDrawable(localBitmapDrawable);
    this.inputManager = ((InputMethodManager)getSystemService("input_method"));
    this.dialog = new ProgressDialog(this);
    this.alertbox = new AlertDialog.Builder(this);
    this.inputManager.toggleSoftInput(2, 1);
    this.userEmail.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        FabForgotPassword.this.validateForm();
      }
    });
  }

  protected void onDestroy()
  {
    this.isActivityRunning = false;
    this.tracker.stopSession();
    super.onDestroy();
  }

  protected void onPause()
  {
    this.isActivityRunning = false;
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.cancel();
    super.onPause();
    this.inputManager.hideSoftInputFromWindow(this.userEmail.getWindowToken(), 0);
  }

  protected void onRestart()
  {
    this.isActivityRunning = true;
    super.onRestart();
    if (FabUtils.isLoggedIn(this.context))
    {
      finish();
      this.inputManager.hideSoftInputFromWindow(this.userEmail.getWindowToken(), 0);
    }
    while (true)
    {
      return;
      this.inputManager.toggleSoftInput(2, 1);
    }
  }

  protected void validateForm()
  {
    if (TextUtils.isEmpty(this.userEmail.getText().toString()))
    {
      this.buttonSubmit.setTextColor(-3947581);
      this.buttonSubmit.setBackgroundResource(2130837619);
      this.buttonSubmit.setOnClickListener(null);
      this.userEmail.setOnEditorActionListener(null);
    }
    while (true)
    {
      return;
      this.buttonSubmit.setTextColor(-1);
      this.buttonSubmit.setBackgroundResource(2130837617);
      this.buttonSubmit.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          FabForgotPassword.this.forgotPassword();
        }
      });
      this.userEmail.setOnEditorActionListener(new TextView.OnEditorActionListener()
      {
        public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          FabForgotPassword.this.forgotPassword();
          return false;
        }
      });
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.FabForgotPassword
 * JD-Core Version:    0.6.2
 */