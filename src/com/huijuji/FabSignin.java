package com.fab;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import com.fab.facebook.FabFBClient;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabHelper;
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

public class FabSignin extends Activity
{
  private String TAG = "FAB_SIGNIN";
  private AlertDialog.Builder alertbox;
  private FabButton btnRegister;
  private FabButton buttonSignin;
  private Context context;
  private ProgressDialog dialog;
  private Bundle extras;
  protected FabFBClient fbClient;
  private FabButton fbConnect;
  private String fbEmail;
  private FabButton forgotPassword;
  private InputMethodManager inputManager;
  private boolean isActivityRunning;
  protected boolean isFBConnectInProcess;
  private boolean isFromFbScene3;
  private boolean isFromGuest;
  private Handler mHandler;
  private HashMap<String, String> parameters;
  private boolean refreshPreviousActivity;
  private FabTextView signupHeader;
  private int tempId;
  private TitleWidget titleWidget;
  private GoogleAnalyticsTracker tracker;
  private FabEditText userEmail;
  private FabEditText userPassword;

  protected void loginUser()
  {
    if (this.isActivityRunning)
    {
      this.dialog.setCancelable(true);
      this.dialog.setMessage("Signing in...");
      this.dialog.show();
    }
    final ExecuteRequestAsyncTask localExecuteRequestAsyncTask = new ExecuteRequestAsyncTask(this.context, "POST");
    this.parameters = new HashMap();
    this.parameters.put("d", FabSharedPrefs.getDeviceDensity());
    this.parameters.put("app_version", FabApplication.APP_VERSION);
    this.parameters.put("device_model", Build.MODEL);
    this.parameters.put("device_os", Build.VERSION.RELEASE);
    this.parameters.put("un_or_email", this.userEmail.getText().toString().trim());
    this.parameters.put("password", this.userPassword.getText().toString().trim());
    if (this.isFromFbScene3)
    {
      this.parameters.put("temp_row_id", this.tempId);
      this.parameters.put("fb_email", this.fbEmail);
    }
    localExecuteRequestAsyncTask.setParameters(this.parameters);
    localExecuteRequestAsyncTask.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      public void onTaskComplete(Object paramAnonymousObject)
      {
        if ((FabSignin.this.isActivityRunning) && (FabSignin.this.dialog != null) && (FabSignin.this.dialog.isShowing()))
          FabSignin.this.dialog.cancel();
        JSONObject localJSONObject;
        if ((paramAnonymousObject != null) && ((paramAnonymousObject instanceof JSONObject)))
        {
          localJSONObject = (JSONObject)paramAnonymousObject;
          FabUtils.log(FabSignin.this.TAG, localJSONObject.toString());
        }
        try
        {
          if ((TextUtils.isEmpty(localJSONObject.getString("err_msg"))) && (FabUtils.isLoggedIn(FabSignin.this.context)))
          {
            FabSignin.this.finish();
            FabHelper.onLoginComplete(localJSONObject.getJSONObject("user"), FabSignin.this.context);
            FabSignin.this.inputManager.hideSoftInputFromWindow(FabSignin.this.userEmail.getWindowToken(), 0);
            if (FabSignin.this.isFromGuest)
            {
              if (FabSignin.this.refreshPreviousActivity)
                ((FabApplication)FabSignin.this.getApplication()).setRefreshActivityOnRestart(true);
              FabSignin.this.finish();
            }
          }
          while (true)
          {
            label184: if (FabSignin.this.parameters != null)
              FabSignin.this.parameters.clear();
            return;
            Intent localIntent = new Intent(FabSignin.this.context, FabHome.class);
            boolean bool = FabSignin.this.isFromFbScene3;
            if (bool);
            try
            {
              localIntent.putExtra("SUCCESS_MSG", localJSONObject.getString("success_msg"));
              try
              {
                label251: localIntent.putExtra("SUCCESS_MSG_TITLE", localJSONObject.getString("success_msg_header"));
                label265: FabSignin.this.startActivity(localIntent);
                continue;
                FabSignin.this.alertbox.setIcon(2130837504);
                FabSignin.this.alertbox.setTitle("Oops!");
                FabSignin.this.alertbox.setMessage(localJSONObject.getString("err_msg"));
                FabSignin.this.alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener()
                {
                  public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                  {
                    FabSignin.this.userPassword.setText("");
                    FabSignin.this.userPassword.clearFocus();
                    FabSignin.this.validateForm();
                    FabSignin.this.inputManager.toggleSoftInput(2, 1);
                  }
                });
                if (!FabSignin.this.isActivityRunning)
                  continue;
                FabSignin.this.alertbox.show();
                continue;
                FabUtils.showAlert(FabSignin.this.context, localExecuteRequestAsyncTask.getErrMsg());
              }
              catch (JSONException localJSONException3)
              {
                break label265;
              }
            }
            catch (JSONException localJSONException2)
            {
              break label251;
            }
          }
        }
        catch (JSONException localJSONException1)
        {
          break label184;
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
    setContentView(2130903058);
    this.context = this;
    this.isActivityRunning = true;
    this.isFBConnectInProcess = false;
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("fabSignin"));
    this.extras = getIntent().getExtras();
    if (this.extras != null)
    {
      this.isFromGuest = this.extras.getBoolean("IS_FROM_GUEST");
      this.refreshPreviousActivity = this.extras.getBoolean("REFRESH_PREVIOUS_ACTIVITY");
      this.isFromFbScene3 = this.extras.getBoolean("IS_FROM_FB_SCENE3");
      this.tempId = this.extras.getInt("TEMP_ID");
      this.fbEmail = this.extras.getString("FB_EMAIL");
    }
    this.titleWidget = ((TitleWidget)findViewById(2131361810));
    this.titleWidget.getCartButton().setVisibility(8);
    this.titleWidget.getBrowseButton().setVisibility(8);
    this.btnRegister = this.titleWidget.getRegisterButton();
    this.btnRegister.setVisibility(0);
    this.btnRegister.setText("Register");
    this.btnRegister.setBackgroundDrawable(getResources().getDrawable(2130837723));
    this.btnRegister.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent(FabSignin.this.context, FabRegister.class);
        localIntent.putExtra("IS_FROM_GUEST", FabSignin.this.isFromGuest);
        FabSignin.this.startActivity(localIntent);
      }
    });
    this.signupHeader = ((FabTextView)findViewById(2131361886));
    this.signupHeader.setFont("HelveticaNeu_bold.ttf");
    this.userEmail = ((FabEditText)findViewById(2131361830));
    this.userPassword = ((FabEditText)findViewById(2131361887));
    this.buttonSignin = ((FabButton)findViewById(2131361896));
    this.buttonSignin.setFont("HelveticaNeu_bold.ttf");
    this.fbConnect = ((FabButton)findViewById(2131361892));
    this.fbConnect.setFont("HelveticaNeu_bold.ttf");
    this.forgotPassword = ((FabButton)findViewById(2131361900));
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
        FabSignin.this.validateForm();
      }
    });
    this.userPassword.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        FabSignin.this.validateForm();
      }
    });
    this.forgotPassword.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent(FabSignin.this.context, FabForgotPassword.class);
        FabSignin.this.startActivity(localIntent);
      }
    });
    if (this.isFromFbScene3)
    {
      this.buttonSignin.setVisibility(8);
      this.fbConnect.setBackgroundDrawable(getResources().getDrawable(2130837604));
    }
    while (true)
    {
      this.mHandler = new Handler()
      {
        private ProgressDialog dialog;

        public void handleMessage(Message paramAnonymousMessage)
        {
          if (paramAnonymousMessage.what == 2)
            if (FabSignin.this.fbClient != null)
              FabSignin.this.fbClient.buildFBConnectScene3Dialog(new AlertDialog.Builder(FabSignin.this.context));
          while (true)
          {
            return;
            if (paramAnonymousMessage.what == 8)
            {
              FabSignin.this.isFBConnectInProcess = false;
              if (FabSignin.this.isActivityRunning)
              {
                this.dialog = new ProgressDialog(FabSignin.this.context);
                this.dialog.setCancelable(true);
                this.dialog.setMessage("Signing in...");
                this.dialog.show();
              }
            }
            else if (paramAnonymousMessage.what == 9)
            {
              FabSignin.this.isFBConnectInProcess = false;
              if ((FabSignin.this.isActivityRunning) && (this.dialog != null) && (this.dialog.isShowing()))
                this.dialog.cancel();
            }
            else if (paramAnonymousMessage.what == 6)
            {
              FabSignin.this.finish();
            }
            else if (paramAnonymousMessage.what == 12)
            {
              FabSignin.this.isFBConnectInProcess = false;
              if (FabSignin.this.isActivityRunning)
              {
                Bundle localBundle = paramAnonymousMessage.getData();
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(FabSignin.this.context);
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
      return;
      this.fbConnect.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          FabSignin.this.isFBConnectInProcess = true;
          FabSignin.this.fbClient = FabHelper.connectToFacebook(FabSignin.this.context, false, FabSignin.this.mHandler, FabSignin.this.isFromGuest);
        }
      });
    }
  }

  protected void onDestroy()
  {
    this.isActivityRunning = false;
    this.tracker.stopSession();
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.cancel();
    super.onDestroy();
  }

  protected void onPause()
  {
    this.isActivityRunning = false;
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.cancel();
    this.inputManager.hideSoftInputFromWindow(this.userEmail.getWindowToken(), 0);
    super.onPause();
  }

  protected void onRestart()
  {
    this.isActivityRunning = true;
    super.onRestart();
    if ((FabUtils.isLoggedIn(this.context)) && (!FabSharedPrefs.isGuestUser()) && (!this.isFromFbScene3) && (!this.isFBConnectInProcess))
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

  protected void onResume()
  {
    super.onResume();
    this.isActivityRunning = true;
    if ((FabUtils.isLoggedIn(this.context)) && (!this.isFromGuest) && (!this.isFromFbScene3) && (!this.isFBConnectInProcess))
      finish();
    if ((FabUtils.isLoggedIn(this.context)) && (!FabSharedPrefs.isGuestUser()) && (!this.isFromFbScene3) && (!this.isFBConnectInProcess))
      finish();
  }

  protected void validateForm()
  {
    if (this.isFromFbScene3)
      if ((TextUtils.isEmpty(this.userEmail.getText().toString())) || (TextUtils.isEmpty(this.userPassword.getText().toString())))
        this.userPassword.setOnEditorActionListener(null);
    while (true)
    {
      return;
      this.fbConnect.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          FabSignin.this.loginUser();
        }
      });
      this.userPassword.setOnEditorActionListener(new TextView.OnEditorActionListener()
      {
        public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          FabSignin.this.loginUser();
          return false;
        }
      });
      continue;
      if ((TextUtils.isEmpty(this.userEmail.getText().toString())) || (TextUtils.isEmpty(this.userPassword.getText().toString())))
      {
        this.buttonSignin.setTextColor(-8356226);
        this.buttonSignin.setBackgroundResource(2130837708);
        this.buttonSignin.setOnClickListener(null);
        this.userPassword.setOnEditorActionListener(null);
      }
      else
      {
        this.buttonSignin.setTextColor(-1);
        this.buttonSignin.setBackgroundResource(2130837713);
        this.buttonSignin.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            FabSignin.this.loginUser();
          }
        });
        this.userPassword.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
          public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
          {
            FabSignin.this.loginUser();
            return false;
          }
        });
      }
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.FabSignin
 * JD-Core Version:    0.6.2
 */