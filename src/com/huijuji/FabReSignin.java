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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.fab.facebook.FabFBClient;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabHelper;
import com.fab.utils.FabUtils;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.FabButton;
import com.fab.views.FabEditText;
import com.fab.views.FabTextView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import java.net.URLEncoder;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class FabReSignin extends Activity
{
  private String TAG = "FAB_RE_LOGIN";
  private AlertDialog.Builder alertbox;
  private FabButton buttonSignin;
  private Context context;
  private FabButton createPwd;
  private ProgressDialog dialog;
  private FabTextView emailAddress;
  private FabTextView emailText;
  private Bundle extras;
  protected FabFBClient fbClient;
  private FabButton fbConnect;
  private int hasFbConnect;
  private int hasPwd;
  private InputMethodManager inputManager;
  private boolean isActivityRunning;
  protected boolean isFBConnectInProcess;
  private FabButton logoutBtn;
  private Handler mHandler;
  private FabTextView note;
  private FabTextView or;
  private FabTextView reloginText;
  private FabTextView signinText;
  private GoogleAnalyticsTracker tracker;
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
    HashMap localHashMap = new HashMap();
    FabHelper.setDeviceParamsForPost(localHashMap);
    localHashMap.put("password", URLEncoder.encode(this.userPassword.getText().toString().trim()));
    localExecuteRequestAsyncTask.setParameters(localHashMap);
    localExecuteRequestAsyncTask.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      public void onTaskComplete(Object paramAnonymousObject)
      {
        if ((FabReSignin.this.isActivityRunning) && (FabReSignin.this.dialog != null) && (FabReSignin.this.dialog.isShowing()))
          FabReSignin.this.dialog.cancel();
        JSONObject localJSONObject;
        if ((paramAnonymousObject != null) && ((paramAnonymousObject instanceof JSONObject)))
        {
          localJSONObject = (JSONObject)paramAnonymousObject;
          FabUtils.log(FabReSignin.this.TAG, localJSONObject.toString());
        }
        while (true)
        {
          try
          {
            if ((TextUtils.isEmpty(localJSONObject.getString("err_msg"))) && (FabUtils.isLoggedIn(FabReSignin.this.context)))
            {
              FabReSignin.this.finish();
              FabReSignin.this.inputManager.hideSoftInputFromWindow(FabReSignin.this.userPassword.getWindowToken(), 0);
              FabReSignin.this.isFBConnectInProcess = false;
              return;
            }
            FabReSignin.this.alertbox.setIcon(2130837504);
            FabReSignin.this.alertbox.setTitle("Oops!");
            FabReSignin.this.alertbox.setMessage(localJSONObject.getString("err_msg"));
            FabReSignin.this.alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
              {
                FabReSignin.this.userPassword.setText("");
                FabReSignin.this.userPassword.clearFocus();
                FabReSignin.this.validateForm();
                FabReSignin.this.inputManager.toggleSoftInput(2, 1);
              }
            });
            if (!FabReSignin.this.isActivityRunning)
              continue;
            FabReSignin.this.alertbox.show();
            continue;
          }
          catch (JSONException localJSONException)
          {
            continue;
          }
          FabUtils.showAlert(FabReSignin.this.context, localExecuteRequestAsyncTask.getErrMsg());
        }
      }
    });
    String[] arrayOfString = new String[1];
    arrayOfString[0] = FabUtils.requestPostUrl(true, "/mobile/relogin/");
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
    setContentView(2130903057);
    this.context = this;
    this.isActivityRunning = true;
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("fabRelogin"));
    this.extras = getIntent().getExtras();
    if (this.extras != null)
    {
      this.hasPwd = this.extras.getInt("HAS_PWD");
      this.hasFbConnect = this.extras.getInt("HAS_FB_CONNECT");
    }
    this.signinText = ((FabTextView)findViewById(2131361886));
    this.signinText.setFont("HelveticaNeu_bold.ttf");
    this.reloginText = ((FabTextView)findViewById(2131361894));
    this.reloginText.setText(Html.fromHtml("For your security, please <font color='#ffffff'><b>confirm</b></font> your <font color='#ffffff'><b>email and password</b></font> to continue."));
    this.userPassword = ((FabEditText)findViewById(2131361887));
    this.buttonSignin = ((FabButton)findViewById(2131361896));
    this.buttonSignin.setFont("HelveticaNeu_bold.ttf");
    this.logoutBtn = ((FabButton)findViewById(2131361893));
    this.emailText = ((FabTextView)findViewById(2131361830));
    this.emailText.setFont("HelveticaNeu_bold.ttf");
    this.emailAddress = ((FabTextView)findViewById(2131361831));
    this.emailAddress.setFont("HelveticaNeu_bold.ttf");
    try
    {
      this.emailAddress.setText(FabUtils.getUserInfo(this.context).getString("email"));
      label267: this.fbConnect = ((FabButton)findViewById(2131361892));
      this.fbConnect.setFont("HelveticaNeu_bold.ttf");
      this.or = ((FabTextView)findViewById(2131361897));
      this.or.setFont("HelveticaNeu_bold.ttf");
      this.createPwd = ((FabButton)findViewById(2131361898));
      this.createPwd.setFont("HelveticaNeu_bold.ttf");
      if (this.hasFbConnect == 1)
      {
        this.fbConnect.setVisibility(0);
        this.fbConnect.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            FabReSignin.this.isFBConnectInProcess = true;
            FabReSignin.this.fbClient = FabHelper.connectToFacebook(FabReSignin.this.context, true, FabReSignin.this.mHandler, false);
          }
        });
        if (this.hasPwd != 1)
          break label591;
        this.userPassword.setVisibility(0);
        this.buttonSignin.setVisibility(0);
        label394: if ((this.hasFbConnect != 1) || (this.hasPwd != 1))
          break label665;
        this.or.setVisibility(0);
      }
      while (true)
      {
        BitmapDrawable localBitmapDrawable = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), 2130837515));
        localBitmapDrawable.setTileModeX(Shader.TileMode.REPEAT);
        ((ScrollView)findViewById(2131361827)).setBackgroundDrawable(localBitmapDrawable);
        this.inputManager = ((InputMethodManager)getSystemService("input_method"));
        this.dialog = new ProgressDialog(this);
        this.alertbox = new AlertDialog.Builder(this);
        if (this.hasPwd == 1)
          this.inputManager.toggleSoftInput(2, 1);
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
            FabReSignin.this.validateForm();
          }
        });
        this.logoutBtn.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            FabReSignin.this.dialog = new ProgressDialog(FabReSignin.this.context);
            FabReSignin.this.dialog.setCancelable(true);
            FabReSignin.this.dialog.setMessage("Logging out...");
            FabReSignin.this.dialog.show();
            FabUtils.logoutUser(FabReSignin.this.context, FabReSignin.this.mHandler);
          }
        });
        this.mHandler = new Handler()
        {
          public void handleMessage(Message paramAnonymousMessage)
          {
            final Bundle localBundle = paramAnonymousMessage.getData();
            if (paramAnonymousMessage.what == 6)
              FabReSignin.this.finish();
            while (true)
            {
              return;
              if (paramAnonymousMessage.what == 2)
              {
                if (localBundle.getInt("ALERT_ICON") > 0);
                for (int i = localBundle.getInt("ALERT_ICON"); ; i = 2130837504)
                {
                  FabReSignin.this.alertbox.setIcon(i);
                  FabReSignin.this.alertbox.setTitle(localBundle.getString("ALERT_TITLE"));
                  FabReSignin.this.alertbox.setMessage(localBundle.getString("ALERT_MSG"));
                  FabReSignin.this.alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener()
                  {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                    {
                      if (localBundle.getBoolean("MISMATCH"))
                        FabHelper.connectUsingFbOAuthUserAgent(FabReSignin.this.context, FabReSignin.this.mHandler);
                      while (true)
                      {
                        return;
                        FabReSignin.this.userPassword.setText("");
                        FabReSignin.this.userPassword.clearFocus();
                        FabReSignin.this.validateForm();
                        FabReSignin.this.inputManager.toggleSoftInput(2, 1);
                      }
                    }
                  });
                  if (!FabReSignin.this.isActivityRunning)
                    break;
                  FabReSignin.this.alertbox.show();
                  break;
                }
              }
              if (paramAnonymousMessage.what == 7)
              {
                FabReSignin.this.isFBConnectInProcess = false;
              }
              else if (paramAnonymousMessage.what == 8)
              {
                if (FabReSignin.this.isActivityRunning)
                {
                  FabReSignin.this.dialog.setCancelable(true);
                  FabReSignin.this.dialog.setMessage("Signing in...");
                  FabReSignin.this.dialog.show();
                }
              }
              else if (paramAnonymousMessage.what == 9)
              {
                if ((FabReSignin.this.isActivityRunning) && (FabReSignin.this.dialog != null) && (FabReSignin.this.dialog.isShowing()))
                  FabReSignin.this.dialog.cancel();
              }
              else if (paramAnonymousMessage.what == 1)
              {
                if ((FabReSignin.this.isActivityRunning) && (FabReSignin.this.dialog != null) && (FabReSignin.this.dialog.isShowing()))
                  FabReSignin.this.dialog.cancel();
                Intent localIntent = new Intent(FabReSignin.this.context, FabIndex.class);
                FabReSignin.this.startActivity(localIntent);
                FabReSignin.this.finish();
              }
            }
          }
        };
        this.note = ((FabTextView)findViewById(2131361899));
        this.note.setFont("georgiai.ttf");
        return;
        this.fbConnect.setVisibility(8);
        break;
        label591: this.userPassword.setVisibility(8);
        this.buttonSignin.setVisibility(8);
        this.fbConnect.setBackgroundDrawable(getResources().getDrawable(2130837602));
        this.createPwd.setVisibility(0);
        this.reloginText.setText(Html.fromHtml("For your security, please <font color='#ffffff'><b>confirm</b></font> your <font color='#ffffff'><b>account</b></font> to continue."));
        this.createPwd.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            Intent localIntent = new Intent(FabReSignin.this.context, CreatePasswordActivity.class);
            FabReSignin.this.startActivity(localIntent);
          }
        });
        break label394;
        label665: this.or.setVisibility(8);
      }
    }
    catch (JSONException localJSONException)
    {
      break label267;
    }
  }

  protected void onDestroy()
  {
    this.isActivityRunning = false;
    this.tracker.stopSession();
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      MoreWebViewActivity.cancelRelogin = true;
      com.fab.webviews.FabWebViewActivity.cancelRelogin = true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  protected void onPause()
  {
    this.isActivityRunning = false;
    super.onPause();
    this.inputManager.hideSoftInputFromWindow(this.userPassword.getWindowToken(), 0);
  }

  protected void onRestart()
  {
    this.isActivityRunning = true;
    super.onRestart();
    if ((FabUtils.isLoggedIn(this.context)) && (!this.isFBConnectInProcess) && (this.hasPwd != 0))
    {
      finish();
      this.inputManager.hideSoftInputFromWindow(this.userPassword.getWindowToken(), 0);
    }
    while (true)
    {
      return;
      if ((!this.isFBConnectInProcess) && (this.hasPwd != 0))
        this.inputManager.toggleSoftInput(2, 1);
    }
  }

  protected void validateForm()
  {
    if (TextUtils.isEmpty(this.userPassword.getText()))
    {
      this.buttonSignin.setBackgroundResource(2130837708);
      this.buttonSignin.setOnClickListener(null);
      this.buttonSignin.setTextColor(-8356226);
      this.userPassword.setOnEditorActionListener(null);
    }
    while (true)
    {
      return;
      this.buttonSignin.setBackgroundResource(2130837713);
      this.buttonSignin.setTextColor(-1);
      this.buttonSignin.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          FabReSignin.this.loginUser();
        }
      });
      this.userPassword.setOnEditorActionListener(new TextView.OnEditorActionListener()
      {
        public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          FabReSignin.this.loginUser();
          return false;
        }
      });
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.FabReSignin
 * JD-Core Version:    0.6.2
 */