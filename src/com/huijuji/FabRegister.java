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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.fab.db.FabDBAdapter;
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

public class FabRegister extends Activity
{
  private String TAG = "FAB_REGISTER";
  private AlertDialog.Builder alertbox;
  private FabButton btnRegister;
  private FabButton buttonRegister;
  private Context context;
  private ProgressDialog dialog;
  private Bundle extras;
  protected FabFBClient fbClient;
  private FabButton fbConnect;
  private InputMethodManager inputManager;
  private boolean isActivityRunning;
  protected boolean isFBConnectInProcess;
  private boolean isFromGuest;
  private Handler mHandler;
  private HashMap<String, String> parameters;
  private FabTextView registerHeader;
  private TitleWidget titleWidget;
  private CheckBox tncCheckbox;
  private GoogleAnalyticsTracker tracker;
  private FabTextView underlinedText;
  private FabEditText userEmail;
  private FabEditText userPassword;

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
    setContentView(2130903056);
    this.context = this;
    this.isActivityRunning = true;
    this.isFBConnectInProcess = false;
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("fabRegister"));
    this.extras = getIntent().getExtras();
    if (this.extras != null)
      this.isFromGuest = this.extras.getBoolean("IS_FROM_GUEST");
    this.registerHeader = ((FabTextView)findViewById(2131361886));
    this.registerHeader.setFont("HelveticaNeu_bold.ttf");
    this.userEmail = ((FabEditText)findViewById(2131361830));
    this.userPassword = ((FabEditText)findViewById(2131361887));
    this.buttonRegister = ((FabButton)findViewById(2131361891));
    this.buttonRegister.setFont("HelveticaNeu_bold.ttf");
    this.tncCheckbox = ((CheckBox)findViewById(2131361890));
    this.underlinedText = ((FabTextView)findViewById(2131361889));
    this.titleWidget = ((TitleWidget)findViewById(2131361810));
    this.titleWidget.getCartButton().setVisibility(8);
    this.titleWidget.getBrowseButton().setVisibility(8);
    this.btnRegister = this.titleWidget.getRegisterButton();
    this.btnRegister.setVisibility(0);
    this.btnRegister.setText("Sign-In");
    this.btnRegister.setBackgroundDrawable(getResources().getDrawable(2130837723));
    this.btnRegister.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        FabRegister.this.finish();
      }
    });
    BitmapDrawable localBitmapDrawable = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), 2130837515));
    localBitmapDrawable.setTileModeX(Shader.TileMode.REPEAT);
    ((ScrollView)findViewById(2131361827)).setBackgroundDrawable(localBitmapDrawable);
    this.inputManager = ((InputMethodManager)getSystemService("input_method"));
    this.dialog = new ProgressDialog(this);
    this.alertbox = new AlertDialog.Builder(this);
    this.underlinedText.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        FabRegister.this.inputManager.hideSoftInputFromWindow(FabRegister.this.userEmail.getWindowToken(), 0);
        Intent localIntent = new Intent(FabRegister.this.context, MoreWebViewActivity.class);
        localIntent.putExtra("URL", FabUtils.requestUrl(false, "/mobile/terms", ""));
        FabRegister.this.startActivity(localIntent);
      }
    });
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
        FabRegister.this.validateForm();
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
        FabRegister.this.validateForm();
      }
    });
    this.tncCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
      {
        FabRegister.this.validateForm();
      }
    });
    this.fbConnect = ((FabButton)findViewById(2131361892));
    this.fbConnect.setFont("HelveticaNeu_bold.ttf");
    this.fbConnect.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        FabRegister.this.isFBConnectInProcess = true;
        FabRegister.this.fbClient = FabHelper.connectToFacebook(FabRegister.this.context, false, FabRegister.this.mHandler, FabRegister.this.isFromGuest);
      }
    });
    this.mHandler = new Handler()
    {
      private ProgressDialog dialog;

      public void handleMessage(Message paramAnonymousMessage)
      {
        Bundle localBundle = paramAnonymousMessage.getData();
        if (paramAnonymousMessage.what == 2)
          if (FabRegister.this.fbClient != null)
            FabRegister.this.fbClient.buildFBConnectScene3Dialog(new AlertDialog.Builder(FabRegister.this.context));
        while (true)
        {
          return;
          if (paramAnonymousMessage.what == 8)
          {
            FabRegister.this.isFBConnectInProcess = false;
            if (FabRegister.this.isActivityRunning)
            {
              this.dialog = new ProgressDialog(FabRegister.this.context);
              this.dialog.setCancelable(true);
              this.dialog.setMessage("Signing in...");
              this.dialog.show();
            }
          }
          else if (paramAnonymousMessage.what == 9)
          {
            FabRegister.this.isFBConnectInProcess = false;
            if ((FabRegister.this.isActivityRunning) && (this.dialog != null) && (this.dialog.isShowing()))
              this.dialog.cancel();
          }
          else if (paramAnonymousMessage.what == 6)
          {
            FabRegister.this.finish();
          }
          else if (paramAnonymousMessage.what == 12)
          {
            FabRegister.this.isFBConnectInProcess = false;
            if (FabRegister.this.isActivityRunning)
            {
              AlertDialog.Builder localBuilder = new AlertDialog.Builder(FabRegister.this.context);
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
    if ((FabUtils.isLoggedIn(this.context)) && (!FabSharedPrefs.isGuestUser()) && (!this.isFBConnectInProcess))
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

  protected void registerUser()
  {
    if (this.isActivityRunning)
    {
      this.dialog.setCancelable(true);
      this.dialog.setMessage("Registering...");
      this.dialog.show();
    }
    final ExecuteRequestAsyncTask localExecuteRequestAsyncTask = new ExecuteRequestAsyncTask(this.context, "POST");
    this.parameters = new HashMap();
    this.parameters.put("d", FabSharedPrefs.getDeviceDensity());
    this.parameters.put("app_version", FabApplication.APP_VERSION);
    this.parameters.put("device_model", Build.MODEL);
    this.parameters.put("device_os", Build.VERSION.RELEASE);
    this.parameters.put("email", this.userEmail.getText().toString().trim());
    this.parameters.put("password", this.userPassword.getText().toString().trim());
    this.parameters.put("tnc", "1");
    localExecuteRequestAsyncTask.setParameters(this.parameters);
    localExecuteRequestAsyncTask.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      public void onTaskComplete(Object paramAnonymousObject)
      {
        if ((FabRegister.this.isActivityRunning) && (FabRegister.this.dialog != null) && (FabRegister.this.dialog.isShowing()))
          FabRegister.this.dialog.cancel();
        JSONObject localJSONObject1;
        if ((paramAnonymousObject != null) && ((paramAnonymousObject instanceof JSONObject)))
        {
          localJSONObject1 = (JSONObject)paramAnonymousObject;
          FabUtils.log(FabRegister.this.TAG, localJSONObject1.toString());
        }
        try
        {
          if ((TextUtils.isEmpty(localJSONObject1.getString("err_msg"))) && (FabUtils.isLoggedIn(FabRegister.this.context)))
          {
            FabRegister.this.finish();
            FabSharedPrefs.setGuestUser(false);
            JSONObject localJSONObject2 = localJSONObject1.getJSONObject("user");
            FabDBAdapter localFabDBAdapter = new FabDBAdapter(FabRegister.this.context);
            localFabDBAdapter.open();
            if (!TextUtils.isEmpty(FabSharedPrefs.getFabCookies()))
              localFabDBAdapter.insertUserInfo(localJSONObject2.getInt("id"), localJSONObject2.getString("username"), FabSharedPrefs.getFabCookies(), localJSONObject2.getString("email"));
            localFabDBAdapter.close();
            FabRegister.this.inputManager.hideSoftInputFromWindow(FabRegister.this.userEmail.getWindowToken(), 0);
            FabUtils.setAlarmForPullNotification(FabRegister.this.getApplicationContext(), 0L);
            FabUtils.setAlarm(FabRegister.this.context);
            if (FabRegister.this.isFromGuest)
              FabRegister.this.finish();
          }
          while (true)
          {
            label245: if (FabRegister.this.parameters != null)
              FabRegister.this.parameters.clear();
            return;
            Intent localIntent = new Intent(FabRegister.this.context, FabHome.class);
            FabRegister.this.startActivity(localIntent);
            continue;
            FabRegister.this.alertbox.setIcon(2130837504);
            FabRegister.this.alertbox.setTitle("Oops!");
            FabRegister.this.alertbox.setMessage(localJSONObject1.getString("err_msg"));
            FabRegister.this.alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
              {
                FabRegister.this.userEmail.setText("");
                FabRegister.this.userPassword.setText("");
                FabRegister.this.userPassword.clearFocus();
                FabRegister.this.validateForm();
                FabRegister.this.inputManager.toggleSoftInput(2, 1);
              }
            });
            if (FabRegister.this.isActivityRunning)
            {
              FabRegister.this.alertbox.show();
              continue;
              FabUtils.showAlert(FabRegister.this.context, localExecuteRequestAsyncTask.getErrMsg());
            }
          }
        }
        catch (JSONException localJSONException)
        {
          break label245;
        }
      }
    });
    String[] arrayOfString = new String[1];
    arrayOfString[0] = FabUtils.requestPostUrl(true, "/mobile/sign-up/");
    localExecuteRequestAsyncTask.execute(arrayOfString);
  }

  protected void validateForm()
  {
    if ((TextUtils.isEmpty(this.userEmail.getText().toString())) || (TextUtils.isEmpty(this.userPassword.getText().toString())) || (!this.tncCheckbox.isChecked()))
    {
      this.buttonRegister.setBackgroundResource(2130837615);
      this.buttonRegister.setOnClickListener(null);
      this.userPassword.setOnEditorActionListener(null);
      this.buttonRegister.setTextColor(-8356226);
    }
    while (true)
    {
      return;
      this.buttonRegister.setBackgroundResource(2130837714);
      this.buttonRegister.setTextColor(-1);
      this.userPassword.setOnEditorActionListener(new TextView.OnEditorActionListener()
      {
        public boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          FabRegister.this.registerUser();
          return false;
        }
      });
      this.buttonRegister.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          FabRegister.this.registerUser();
        }
      });
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.FabRegister
 * JD-Core Version:    0.6.2
 */