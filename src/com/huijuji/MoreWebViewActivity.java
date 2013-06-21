package com.fab;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import com.fab.environment.FabEnvironment;
import com.fab.utils.FabHelper;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;
import com.fab.views.FabButton;
import com.fab.views.FabTextView;
import com.fab.views.TitleWidget;
import com.fab.webviews.ButtonListener;
import com.fab.webviews.FabWebChromeClient;
import com.fab.webviews.FabWebViewActivity;
import com.fab.webviews.FabWebViewClient;
import com.fab.webviews.JavaScriptInterface;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import org.json.JSONException;
import org.json.JSONObject;

public class MoreWebViewActivity extends Activity
  implements ButtonListener, View.OnClickListener
{
  protected static final String TAG = "MoreWebViewActivity";
  public static boolean cancelRelogin;
  public static boolean isActivityRunning;
  protected AlertDialog.Builder alertbox;
  Context context;
  private CookieManager cookieManager;
  private CookieSyncManager cookieSyncManager;
  private String[] cookiesArray;
  protected ProgressDialog dialog;
  private Bundle extras;
  private FabButton headerCartButton;
  private boolean isActivityAlive;
  private ButtonListener listener;
  private Handler mHandler;
  String sClassName = "android.webkit.WebSettings";
  private FabTextView timer;
  private TitleWidget titleWidget;
  private GoogleAnalyticsTracker tracker;
  private String url;
  private WebView webView;

  private void invokeNativeMethod(String paramString, JSONObject paramJSONObject)
  {
    if (paramString.equalsIgnoreCase("goToSalePage"))
    {
      this.webView.stopLoading();
      Intent localIntent3 = new Intent(this.context, FabHome.class);
      localIntent3.setFlags(67108864);
      startActivity(localIntent3);
      finish();
    }
    while (true)
    {
      return;
      Intent localIntent2;
      if ((paramString.equalsIgnoreCase("showReLoginPage")) && (!cancelRelogin))
        localIntent2 = new Intent(this.context, FabReSignin.class);
      try
      {
        localIntent2.putExtra("HAS_PWD", paramJSONObject.getInt("has_password"));
        try
        {
          label94: localIntent2.putExtra("HAS_FB_CONNECT", paramJSONObject.getInt("has_fb_connect"));
          label108: startActivity(localIntent2);
          continue;
          if (paramString.equalsIgnoreCase("rateMyApp"))
          {
            FabUtils.showRateAppDialog(this.context);
            continue;
          }
          if (!paramString.equalsIgnoreCase("displayInviteComposerSheet"))
            continue;
          try
          {
            String str = paramJSONObject.getString("to");
            Intent localIntent1 = new Intent("android.intent.action.SEND");
            localIntent1.setType("text/html");
            String[] arrayOfString = new String[1];
            arrayOfString[0] = str;
            localIntent1.putExtra("android.intent.extra.EMAIL", arrayOfString);
            startActivity(localIntent1);
          }
          catch (JSONException localJSONException1)
          {
          }
        }
        catch (JSONException localJSONException3)
        {
          break label108;
        }
      }
      catch (JSONException localJSONException2)
      {
        break label94;
      }
    }
  }

  private void resetNativeElements()
  {
    if (this.webView != null)
      this.webView.setVisibility(8);
  }

  public void onClick(View paramView)
  {
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.context = this;
    isActivityRunning = true;
    cancelRelogin = false;
    setContentView(2130903076);
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("more/webview"));
    getWindow().setFeatureInt(2, -1);
    this.extras = getIntent().getExtras();
    String str2;
    label212: String[] arrayOfString;
    int i;
    int j;
    if (this.extras != null)
    {
      if (this.extras.getString("URL") != null)
      {
        str2 = this.extras.getString("URL");
        this.url = str2;
      }
    }
    else
    {
      this.titleWidget = ((TitleWidget)findViewById(2131361810));
      this.headerCartButton = this.titleWidget.getCartButton();
      if (this.url.contains(FabEnvironment.baseUrl() + "/mobile/terms?"))
        break label555;
      if (!FabUtils.isLoggedIn(this.context))
      {
        finish();
        Intent localIntent = new Intent(this.context, FabIndex.class);
        localIntent.setFlags(67108864);
        startActivity(localIntent);
      }
      this.webView = ((WebView)findViewById(2131361959));
      this.webView.setBackgroundColor(0);
      this.timer = ((FabTextView)findViewById(2131361795));
      this.timer.setFont("georgiai.ttf");
      this.alertbox = new AlertDialog.Builder(this);
      updateCartCounter(FabHelper.getCartCount());
      this.cookieSyncManager = CookieSyncManager.createInstance(this.webView.getContext());
      this.cookieManager = CookieManager.getInstance();
      this.cookieManager.setAcceptCookie(true);
      this.cookiesArray = FabSharedPrefs.getFabCookies().split("<==>");
      this.cookieManager.setCookie(FabEnvironment.baseDomain(), "tla=; domain=fab.com");
      arrayOfString = this.cookiesArray;
      i = arrayOfString.length;
      j = 0;
    }
    while (true)
    {
      if (j >= i)
      {
        CookieSyncManager.getInstance().sync();
        this.alertbox = new AlertDialog.Builder(this);
        this.webView.setWebChromeClient(new FabWebChromeClient());
        this.webView.setWebViewClient(new FabWebViewClient(this, this.context, true, true));
        this.webView.setScrollBarStyle(33554432);
        JavaScriptInterface localJavaScriptInterface = new JavaScriptInterface(this);
        setButtonListener(this);
        this.webView.addJavascriptInterface(localJavaScriptInterface, "jsInterface");
        this.webView.setFocusable(true);
        this.webView.setFocusableInTouchMode(true);
        this.webView.requestFocus(130);
        this.webView.setOnTouchListener(new View.OnTouchListener()
        {
          public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
          {
            switch (paramAnonymousMotionEvent.getAction())
            {
            default:
            case 0:
            case 1:
            }
            while (true)
            {
              return false;
              if (!paramAnonymousView.hasFocus())
                paramAnonymousView.requestFocus();
            }
          }
        });
        WebSettings localWebSettings = this.webView.getSettings();
        localWebSettings.setSavePassword(true);
        localWebSettings.setSaveFormData(true);
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setSupportZoom(false);
        localWebSettings.setUseWideViewPort(true);
        this.mHandler = new Handler()
        {
          public void handleMessage(Message paramAnonymousMessage)
          {
            Bundle localBundle = paramAnonymousMessage.getData();
            FabUtils.log("MoreWebViewActivity", localBundle.toString());
            int i = localBundle.getInt("Type");
            String str = localBundle.getString("Value");
            if (paramAnonymousMessage.what == 8)
            {
              MoreWebViewActivity.this.dialog = new ProgressDialog(MoreWebViewActivity.this.context);
              MoreWebViewActivity.this.dialog.setCancelable(true);
              MoreWebViewActivity.this.dialog.setMessage("Loading...");
              MoreWebViewActivity.this.dialog.show();
              switch (i)
              {
              default:
              case 2:
              case 1:
              }
            }
            while (true)
            {
              return;
              if ((paramAnonymousMessage.what != 9) || (!MoreWebViewActivity.isActivityRunning) || (MoreWebViewActivity.this.dialog == null) || (!MoreWebViewActivity.this.dialog.isShowing()))
                break;
              MoreWebViewActivity.this.dialog.cancel();
              break;
              if (localBundle.getBoolean("Expire"))
                MoreWebViewActivity.this.timer.setVisibility(4);
              if (MoreWebViewActivity.this.isActivityAlive)
              {
                AlertDialog.Builder localBuilder = MoreWebViewActivity.this.alertbox;
                if (localBundle.getInt("Error") == 1);
                for (int j = 2130837504; ; j = 2130837507)
                {
                  localBuilder.setIcon(j);
                  MoreWebViewActivity.this.alertbox.setTitle(localBundle.getString("Title"));
                  MoreWebViewActivity.this.alertbox.setMessage(localBundle.getString("Message"));
                  MoreWebViewActivity.this.alertbox.setNeutralButton("OK", null);
                  MoreWebViewActivity.this.alertbox.show();
                  break;
                }
                MoreWebViewActivity.this.timer.setVisibility(0);
                MoreWebViewActivity.this.timer.setText(str);
              }
            }
          }
        };
        FabHelper.stopAndRestartCartTimer(getApplicationContext(), FabHelper.getCartTime(), this.mHandler);
        return;
        str2 = "";
        break;
        label555: this.titleWidget.getCartButton().setVisibility(8);
        this.titleWidget.getBrowseButton().setVisibility(8);
        break label212;
      }
      String str1 = arrayOfString[j];
      this.cookieManager.setCookie(FabEnvironment.baseDomain(), str1.split("<@@>")[0]);
      j += 1;
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    if (!this.url.contains(FabEnvironment.baseUrl() + "/mobile/terms?"))
    {
      getMenuInflater().inflate(2131296256, paramMenu);
      MenuItem localMenuItem1 = paramMenu.findItem(2131362109);
      localMenuItem1.setVisible(true);
      localMenuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
      {
        public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
        {
          Intent localIntent = new Intent(MoreWebViewActivity.this.context, FabWebViewActivity.class);
          MoreWebViewActivity.this.startActivity(localIntent);
          return false;
        }
      });
      MenuItem localMenuItem2 = paramMenu.findItem(2131361908);
      localMenuItem2.setVisible(true);
      localMenuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
      {
        public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
        {
          if (FabSharedPrefs.isGuestUser())
            FabUtils.showGuestAlert(MoreWebViewActivity.this.context, MoreWebViewActivity.this.context.getResources().getString(2131165208), MoreWebViewActivity.this.context.getResources().getString(2131165209), false);
          while (true)
          {
            return false;
            Intent localIntent = new Intent(MoreWebViewActivity.this.context, InviteActivity.class);
            MoreWebViewActivity.this.startActivity(localIntent);
          }
        }
      });
      MenuItem localMenuItem3 = paramMenu.findItem(2131362103);
      localMenuItem3.setVisible(true);
      localMenuItem3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
      {
        public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
        {
          MoreWebViewActivity.this.webView.reload();
          return false;
        }
      });
    }
    return super.onCreateOptionsMenu(paramMenu);
  }

  protected void onDestroy()
  {
    this.tracker.stopSession();
    this.webView.clearCache(true);
    isActivityRunning = false;
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (this.webView.canGoBack()))
      this.webView.goBack();
    for (boolean bool = true; ; bool = super.onKeyDown(paramInt, paramKeyEvent))
      return bool;
  }

  protected void onPause()
  {
    isActivityRunning = false;
    super.onPause();
  }

  protected void onRestart()
  {
    if (cancelRelogin)
      finish();
    super.onRestart();
    if (this.mHandler != null)
      this.mHandler.sendEmptyMessage(8);
    isActivityRunning = true;
    if (!this.url.contains(FabEnvironment.baseUrl() + "/mobile/terms?"))
    {
      if (!FabUtils.isLoggedIn(this.context))
      {
        finish();
        Intent localIntent = new Intent(this.context, FabIndex.class);
        localIntent.setFlags(67108864);
        startActivity(localIntent);
        return;
      }
      this.cookieManager = CookieManager.getInstance();
      this.cookiesArray = FabSharedPrefs.getFabCookies().split("<==>");
      String[] arrayOfString2 = this.cookiesArray;
      int k = arrayOfString2.length;
      for (int m = 0; ; m++)
      {
        if (m >= k)
        {
          CookieSyncManager.getInstance().sync();
          this.webView.loadUrl(this.url);
          break;
        }
        String str2 = arrayOfString2[m];
        this.cookieManager.setCookie(FabEnvironment.baseDomain(), str2.split("<@@>")[0]);
      }
    }
    this.titleWidget.getCartButton().setVisibility(8);
    this.titleWidget.getBrowseButton().setVisibility(8);
    this.cookieManager = CookieManager.getInstance();
    this.cookiesArray = FabSharedPrefs.getFabCookies().split("<==>");
    String[] arrayOfString1 = this.cookiesArray;
    int i = arrayOfString1.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        CookieSyncManager.getInstance().sync();
        this.webView.loadUrl(this.url);
        break;
      }
      String str1 = arrayOfString1[j];
      this.cookieManager.setCookie(FabEnvironment.baseDomain(), str1.split("<@@>")[0]);
    }
  }

  protected void onResume()
  {
    super.onResume();
    isActivityRunning = true;
    if ((FabWebViewClient.hasPageLoaded) && (FabWebViewClient.dialog != null))
      FabWebViewClient.dialog.cancel();
    if (!this.url.contains(FabEnvironment.baseUrl() + "/mobile/terms?"))
    {
      if (!FabUtils.isLoggedIn(this.context))
      {
        finish();
        Intent localIntent = new Intent(this.context, FabIndex.class);
        localIntent.setFlags(67108864);
        startActivity(localIntent);
        return;
      }
      this.cookieManager = CookieManager.getInstance();
      this.cookiesArray = FabSharedPrefs.getFabCookies().split("<==>");
      String[] arrayOfString2 = this.cookiesArray;
      int k = arrayOfString2.length;
      for (int m = 0; ; m++)
      {
        if (m >= k)
        {
          CookieSyncManager.getInstance().sync();
          this.webView.loadUrl(this.url);
          break;
        }
        String str2 = arrayOfString2[m];
        this.cookieManager.setCookie(FabEnvironment.baseDomain(), str2.split("<@@>")[0]);
      }
    }
    this.cookieManager = CookieManager.getInstance();
    this.cookiesArray = FabSharedPrefs.getFabCookies().split("<==>");
    String[] arrayOfString1 = this.cookiesArray;
    int i = arrayOfString1.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        CookieSyncManager.getInstance().sync();
        this.webView.loadUrl(this.url);
        break;
      }
      String str1 = arrayOfString1[j];
      this.cookieManager.setCookie(FabEnvironment.baseDomain(), str1.split("<@@>")[0]);
    }
  }

  public void onSetButtonData(String paramString)
  {
    FabUtils.log("MoreWebViewActivity", paramString);
    try
    {
      JSONObject localJSONObject1 = new JSONObject(paramString);
      String str = localJSONObject1.getString("type");
      JSONObject localJSONObject2 = localJSONObject1.getJSONObject("params");
      if (str.equalsIgnoreCase("native"))
      {
        invokeNativeMethod(localJSONObject2.getString("method"), localJSONObject2.getJSONObject("params"));
        FabUtils.log("MoreWebViewActivity", paramString);
      }
      else
      {
        str.equalsIgnoreCase("data");
      }
    }
    catch (JSONException localJSONException)
    {
    }
  }

  public void setButtonListener(ButtonListener paramButtonListener)
  {
    this.listener = paramButtonListener;
  }

  public void updateCartCounter(int paramInt)
  {
    if (paramInt > 0)
    {
      this.headerCartButton.setBackgroundResource(this.context.getResources().getIdentifier("cart_counter_button_sel", "drawable", "com.fab"));
      this.headerCartButton.setText(paramInt);
    }
    while (true)
    {
      return;
      this.headerCartButton.setBackgroundResource(this.context.getResources().getIdentifier("cart_button_sel", "drawable", "com.fab"));
      this.headerCartButton.setText("");
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.MoreWebViewActivity
 * JD-Core Version:    0.6.2
 */