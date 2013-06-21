package com.fab.webviews;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
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
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fab.FabHome;
import com.fab.FabIndex;
import com.fab.FabReSignin;
import com.fab.InviteActivity;
import com.fab.MoreActivity;
import com.fab.environment.FabEnvironment;
import com.fab.facebook.FabFBClient;
import com.fab.facebook.FabFBShareBO;
import com.fab.twitter.FabTwitterClient;
import com.fab.utils.FabHelper;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;
import com.fab.utils.ImageDownloader;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.ErrorMessage;
import com.fab.views.FabButton;
import com.fab.views.FabTextView;
import com.fab.views.TitleWidget;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FabWebViewActivity extends Activity
  implements ButtonListener, View.OnClickListener
{
  public static boolean cancelRelogin;
  public static boolean isActivityRunning;
  public static Handler mHandler;
  private final int ALERT = 2;
  private final int BACK = 5;
  private final int CENTER = 4;
  private final int EXTRA = 3;
  private final int FB_SHARE = 7;
  private final int RIGHT = 6;
  private String TAG = "FAB_WEB_VIEW";
  private final int TIMER = 1;
  private Activity activity;
  private AlertDialog.Builder alertbox;
  private String backUrl;
  private TextView bottomPad;
  private RelativeLayout cartTimerHolder;
  private FabTextView cartTimerTextCenter;
  private FabTextView cartTimerTextLeft;
  private FabTextView cartTimerTextRight;
  FabTextView centerText;
  Context context;
  private CookieManager cookieManager;
  private CookieSyncManager cookieSyncManager;
  private String[] cookiesArray;
  protected ProgressDialog dialog;
  private ErrorMessage error;
  FabButton extraButton;
  private RelativeLayout extraButtonHolder;
  private Bundle extras;
  protected FabFBClient fbClient;
  private FabFBShareBO fbShareData;
  protected boolean hasFBShareToken;
  private boolean isBackActive;
  private boolean isCheckoutProcess = true;
  private String jsOnclickExtra;
  private String jsOnclickRight;
  private ButtonListener listener;
  private String loadUrl;
  private long reservedTime;
  FabButton rightButton;
  private RelativeLayout rightButtonHolder;
  private ImageView rightButtonIcon;
  private boolean shouldGoBack;
  private boolean showTimer;
  private boolean startIndex;
  private TitleWidget titleWidget;
  private GoogleAnalyticsTracker tracker;
  WebView webView;

  private void checkNetworkAndLoadUrl(String paramString)
  {
    if (!FabUtils.checkInternetConnection(this.context))
    {
      this.shouldGoBack = true;
      this.error.setVisibility(0);
      this.error.setErrorMsg(getResources().getString(2131165202));
    }
    while (true)
    {
      return;
      this.webView.loadUrl(paramString);
    }
  }

  private void checkNetworkAndReLoadUrl()
  {
    if (!FabUtils.checkInternetConnection(this.context))
    {
      this.shouldGoBack = true;
      this.error.setVisibility(0);
      this.error.setErrorMsg(getResources().getString(2131165202));
    }
    while (true)
    {
      return;
      this.webView.reload();
    }
  }

  private void invokeNativeMethod(String paramString, JSONObject paramJSONObject)
  {
    if (paramString.equalsIgnoreCase("updateCartCount"));
    try
    {
      updateCartCount(paramJSONObject.getInt("count"), paramJSONObject.getInt("reserved_timestamp"), paramJSONObject.getBoolean("show_timer"));
      while (true)
      {
        label31: return;
        if (paramString.equalsIgnoreCase("goToSalePage"))
        {
          this.webView.stopLoading();
          Intent localIntent5 = new Intent(this.context, FabHome.class);
          localIntent5.setFlags(67108864);
          startActivity(localIntent5);
          finish();
          continue;
        }
        Intent localIntent4;
        if ((paramString.equalsIgnoreCase("showReLoginPage")) && (!cancelRelogin))
        {
          this.startIndex = true;
          localIntent4 = new Intent(this.context, FabReSignin.class);
        }
        try
        {
          localIntent4.putExtra("HAS_PWD", paramJSONObject.getInt("has_password"));
          try
          {
            label140: localIntent4.putExtra("HAS_FB_CONNECT", paramJSONObject.getInt("has_fb_connect"));
            label156: startActivity(localIntent4);
            continue;
            if (paramString.equalsIgnoreCase("rateMyApp"))
            {
              FabUtils.showRateAppDialog(this.context);
              continue;
            }
            int i;
            if (paramString.equalsIgnoreCase("showSharingOptions"))
              i = 0;
            try
            {
              int j = paramJSONObject.getInt("option_type");
              i = j;
              label211: if (i == 1)
              {
                try
                {
                  String str4 = paramJSONObject.getString("tw_share");
                  if (str4 == null)
                    continue;
                  Intent localIntent3 = new Intent(this.context, FabTwitterClient.class);
                  localIntent3.putExtra("TWEET_TEXT", str4);
                  startActivity(localIntent3);
                }
                catch (JSONException localJSONException12)
                {
                }
                continue;
              }
              if (i == 2)
                this.fbShareData = new FabFBShareBO();
              try
              {
                this.fbShareData.setLink(paramJSONObject.getString("u"));
                try
                {
                  label303: this.fbShareData.setCaption(paramJSONObject.getString("c"));
                  try
                  {
                    label317: this.fbShareData.setDescription(paramJSONObject.getString("d"));
                    try
                    {
                      label331: this.fbShareData.setPicture(paramJSONObject.getString("i"));
                      try
                      {
                        label345: this.fbShareData.setName(paramJSONObject.getString("n"));
                        try
                        {
                          label359: this.fbShareData.setName(paramJSONObject.getString("n"));
                          try
                          {
                            label373: this.hasFBShareToken = paramJSONObject.getBoolean("has_fb_share_token");
                            label384: Message localMessage = new Message();
                            Bundle localBundle = new Bundle();
                            localBundle.putInt("Type", 7);
                            localBundle.putBoolean("hasFBShareToken", this.hasFBShareToken);
                            localMessage.setData(localBundle);
                            mHandler.sendMessage(localMessage);
                            continue;
                            if (i != 3)
                              continue;
                            Object localObject1 = null;
                            Object localObject2 = null;
                            try
                            {
                              String str3 = paramJSONObject.getString("sub");
                              localObject1 = str3;
                              try
                              {
                                label468: String str2 = paramJSONObject.getString("body");
                                localObject2 = str2;
                                label481: if ((localObject2 == null) && (localObject1 == null))
                                  continue;
                                Intent localIntent2 = new Intent("android.intent.action.SEND");
                                localIntent2.setType("text/html");
                                localIntent2.putExtra("android.intent.extra.SUBJECT", localObject1);
                                localIntent2.putExtra("android.intent.extra.TEXT", Html.fromHtml(localObject2));
                                startActivity(localIntent2);
                                continue;
                                if (!paramString.equalsIgnoreCase("displayInviteComposerSheet"))
                                  continue;
                                try
                                {
                                  String str1 = paramJSONObject.getString("to");
                                  Intent localIntent1 = new Intent("android.intent.action.SEND");
                                  localIntent1.setType("text/html");
                                  String[] arrayOfString = new String[1];
                                  arrayOfString[0] = str1;
                                  localIntent1.putExtra("android.intent.extra.EMAIL", arrayOfString);
                                  startActivity(localIntent1);
                                }
                                catch (JSONException localJSONException1)
                                {
                                }
                              }
                              catch (JSONException localJSONException4)
                              {
                                break label481;
                              }
                            }
                            catch (JSONException localJSONException3)
                            {
                              break label468;
                            }
                          }
                          catch (JSONException localJSONException11)
                          {
                            break label384;
                          }
                        }
                        catch (JSONException localJSONException10)
                        {
                          break label373;
                        }
                      }
                      catch (JSONException localJSONException9)
                      {
                        break label359;
                      }
                    }
                    catch (JSONException localJSONException8)
                    {
                      break label345;
                    }
                  }
                  catch (JSONException localJSONException7)
                  {
                    break label331;
                  }
                }
                catch (JSONException localJSONException6)
                {
                  break label317;
                }
              }
              catch (JSONException localJSONException5)
              {
                break label303;
              }
            }
            catch (JSONException localJSONException2)
            {
              break label211;
            }
          }
          catch (JSONException localJSONException14)
          {
            break label156;
          }
        }
        catch (JSONException localJSONException13)
        {
          break label140;
        }
      }
    }
    catch (JSONException localJSONException15)
    {
      break label31;
    }
  }

  private void populateUI()
  {
    this.rightButtonHolder = ((RelativeLayout)findViewById(2131361814));
    this.rightButton = ((FabButton)findViewById(2131361815));
    this.rightButton.setFont("HelveticaNeu_bold.ttf");
    this.rightButton.setId(6);
    this.rightButtonIcon = ((ImageView)findViewById(2131361817));
    this.extraButtonHolder = ((RelativeLayout)findViewById(2131361824));
    this.bottomPad = ((TextView)findViewById(2131361822));
    this.extraButton = ((FabButton)findViewById(2131361825));
    this.extraButton.setFont("HelveticaNeu_bold.ttf");
    this.extraButton.setId(3);
    this.centerText = ((FabTextView)findViewById(2131361813));
    this.centerText.setFont("HelveticaNeu_bold.ttf");
    this.cartTimerHolder = ((RelativeLayout)findViewById(2131361818));
    this.cartTimerTextLeft = ((FabTextView)findViewById(2131361819));
    this.cartTimerTextLeft.setFont("georgiai.ttf");
    this.cartTimerTextCenter = ((FabTextView)findViewById(2131361820));
    this.cartTimerTextCenter.setFont("georgiai.ttf");
    this.cartTimerTextRight = ((FabTextView)findViewById(2131361821));
    this.cartTimerTextRight.setFont("georgiai.ttf");
  }

  private void resetNativeElements()
  {
    if (this.webView != null)
      this.webView.setVisibility(8);
    if (this.rightButtonHolder != null)
      this.rightButtonHolder.setVisibility(8);
    if (this.extraButtonHolder != null)
    {
      this.extraButtonHolder.setVisibility(8);
      this.bottomPad.setVisibility(8);
    }
    if (this.centerText != null)
      this.centerText.setVisibility(8);
    if (this.cartTimerHolder != null)
      this.cartTimerHolder.setVisibility(8);
  }

  private void updateCartCount(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.reservedTime = paramInt2;
    FabHelper.setCartTime(this.reservedTime);
    this.showTimer = paramBoolean;
    Message localMessage = new Message();
    Bundle localBundle = new Bundle();
    localBundle.putInt("Type", 1);
    localBundle.putBoolean("IsSet", false);
    localMessage.setData(localBundle);
    mHandler.sendMessage(localMessage);
  }

  public void changeButtonText(FabButton paramFabButton, String paramString1, FabTextView paramFabTextView, int paramInt, Boolean paramBoolean, final String paramString2, final ImageView paramImageView)
  {
    int i;
    if (paramFabButton != null)
    {
      paramFabButton.setText(paramString1);
      paramFabButton.setVisibility(paramInt);
      if ((paramImageView != null) && (!TextUtils.isEmpty(paramString2)))
      {
        i = this.context.getResources().getIdentifier(paramString2, "drawable", "com.fab");
        if (i == 0)
        {
          ImageDownloader localImageDownloader = new ImageDownloader(this.context, null, true, null);
          localImageDownloader.setTaskCompleteListener(new TaskCompleteListener()
          {
            public void onTaskComplete()
            {
              paramImageView.setImageBitmap(FabHelper.getFabHelper().decodeFileForBitmap(FabHelper.getFabHelper().doesFileExists(FabWebViewActivity.this.context, FabUtils.s3MobileIconsUrl(paramString2))));
            }

            public void onTaskComplete(Object paramAnonymousObject)
            {
            }
          });
          String[] arrayOfString = new String[1];
          arrayOfString[0] = FabUtils.s3MobileIconsUrl(paramString2);
          localImageDownloader.execute(arrayOfString);
        }
      }
      else
      {
        paramFabButton.setEnabled(paramBoolean.booleanValue());
        paramFabButton.setOnClickListener(this);
      }
    }
    while (true)
    {
      return;
      paramImageView.setBackgroundResource(i);
      break;
      if (paramFabTextView != null)
      {
        paramFabTextView.setText(paramString1);
        paramFabTextView.setVisibility(paramInt);
      }
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (this.fbClient != null)
      this.fbClient.authorizeCallback(paramInt1, paramInt2, paramIntent);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case 4:
    case 5:
    default:
    case 6:
    case 3:
    }
    while (true)
    {
      return;
      this.webView.loadUrl("javascript:view.webApp.invoke(" + this.jsOnclickRight + ")");
      continue;
      this.webView.loadUrl("javascript:view.webApp.invoke(" + this.jsOnclickExtra + ")");
    }
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    isActivityRunning = true;
    cancelRelogin = false;
    setContentView(2130903044);
    this.context = this;
    if (TextUtils.isEmpty(FabSharedPrefs.getDeviceDensity()))
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
      FabSharedPrefs.setDeviceDensity(localDisplayMetrics.densityDpi);
    }
    if (!FabUtils.isLoggedIn(this.context))
    {
      finish();
      Intent localIntent = new Intent(this.context, FabIndex.class);
      localIntent.setFlags(67108864);
      startActivity(localIntent);
    }
    this.extras = getIntent().getExtras();
    String str2;
    if (this.extras != null)
    {
      if (this.extras.getString("URL") == null)
        break label635;
      str2 = this.extras.getString("URL");
      this.loadUrl = str2;
      if (!TextUtils.isEmpty(this.loadUrl))
        this.isCheckoutProcess = false;
    }
    ((NotificationManager)getSystemService("notification")).cancel(2131165198);
    this.error = ((ErrorMessage)findViewById(2131361811));
    this.titleWidget = ((TitleWidget)findViewById(2131361810));
    this.titleWidget.getCartButton().setVisibility(8);
    this.titleWidget.getBrowseButton().setVisibility(8);
    this.titleWidget.getFabLogo().setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent(FabWebViewActivity.this.context, FabHome.class);
        localIntent.setFlags(67108864);
        FabWebViewActivity.this.startActivity(localIntent);
        FabWebViewActivity.this.finish();
      }
    });
    this.startIndex = false;
    this.shouldGoBack = false;
    this.isBackActive = true;
    this.backUrl = null;
    getWindow().setFeatureInt(2, -1);
    this.webView = ((WebView)findViewById(2131361823));
    this.webView.setBackgroundColor(0);
    this.cookieSyncManager = CookieSyncManager.createInstance(this.webView.getContext());
    this.cookieManager = CookieManager.getInstance();
    this.cookieManager.setAcceptCookie(true);
    this.cookiesArray = FabSharedPrefs.getFabCookies().split("<==>");
    this.cookieManager.setCookie(FabEnvironment.baseDomain(), "tla=; domain=fab.com");
    String[] arrayOfString = this.cookiesArray;
    int i = arrayOfString.length;
    int j = 0;
    label376: if (j >= i)
    {
      CookieSyncManager.getInstance().sync();
      this.alertbox = new AlertDialog.Builder(this);
      this.activity = this;
      this.webView.setWebChromeClient(new FabWebChromeClient());
      this.webView.setWebViewClient(new FabWebViewClient(this.activity, this.context, false, true));
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
      populateUI();
      mHandler = new Handler()
      {
        Calendar cal = new GregorianCalendar();

        public void handleMessage(Message paramAnonymousMessage)
        {
          Bundle localBundle = paramAnonymousMessage.getData();
          if (FabUtils.isLogEnabled())
            FabUtils.log(FabWebViewActivity.this.TAG, localBundle.toString());
          int i = localBundle.getInt("Type");
          String str1 = localBundle.getString("Value");
          int j = localBundle.getInt("Visibility");
          boolean bool1 = localBundle.getBoolean("Enable");
          String str2 = localBundle.getString("url");
          if (paramAnonymousMessage.what == 8)
          {
            if (FabWebViewActivity.isActivityRunning)
            {
              FabWebViewActivity.this.dialog = new ProgressDialog(FabWebViewActivity.this.context);
              FabWebViewActivity.this.dialog.setCancelable(true);
              FabWebViewActivity.this.dialog.setMessage("Loading...");
              FabWebViewActivity.this.dialog.show();
            }
            switch (i)
            {
            default:
            case 5:
            case 6:
            case 3:
            case 4:
            case 2:
            case 1:
            case 7:
            }
          }
          while (true)
          {
            return;
            if ((paramAnonymousMessage.what != 9) || (!FabWebViewActivity.isActivityRunning) || (FabWebViewActivity.this.dialog == null) || (!FabWebViewActivity.this.dialog.isShowing()))
              break;
            FabWebViewActivity.this.dialog.cancel();
            break;
            if (FabUtils.isLogEnabled())
              FabUtils.log(FabWebViewActivity.this.TAG, localBundle.toString());
            FabWebViewActivity.this.backUrl = null;
            FabWebViewActivity.this.shouldGoBack = false;
            FabWebViewActivity.this.isBackActive = true;
            FabWebViewActivity.this.backUrl = str2;
            FabWebViewActivity.this.shouldGoBack = bool1;
            FabWebViewActivity localFabWebViewActivity = FabWebViewActivity.this;
            if (j > 0);
            for (boolean bool2 = true; ; bool2 = false)
            {
              localFabWebViewActivity.isBackActive = bool2;
              break;
            }
            String str3 = localBundle.getString("icon");
            FabWebViewActivity.this.changeButtonText(FabWebViewActivity.this.rightButton, str1, null, j, Boolean.valueOf(bool1), str3, FabWebViewActivity.this.rightButtonIcon);
            FabWebViewActivity.this.rightButtonHolder.setVisibility(j);
            continue;
            FabWebViewActivity.this.changeButtonText(FabWebViewActivity.this.extraButton, str1, null, j, Boolean.valueOf(bool1), null, null);
            FabWebViewActivity.this.extraButtonHolder.setVisibility(j);
            FabWebViewActivity.this.bottomPad.setVisibility(j);
            continue;
            FabWebViewActivity.this.changeButtonText(null, str1, FabWebViewActivity.this.centerText, j, Boolean.valueOf(false), null, null);
            continue;
            if (FabWebViewActivity.isActivityRunning)
            {
              AlertDialog.Builder localBuilder = FabWebViewActivity.this.alertbox;
              if (localBundle.getInt("Error") == 1);
              for (int k = 2130837504; ; k = 2130837507)
              {
                localBuilder.setIcon(k);
                FabWebViewActivity.this.alertbox.setTitle(localBundle.getString("Title"));
                FabWebViewActivity.this.alertbox.setMessage(localBundle.getString("Message"));
                FabWebViewActivity.this.alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener()
                {
                  public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                  {
                    if (FabUtils.getSecondsLeftForTimer(FabWebViewActivity.this.reservedTime, FabWebViewActivity.3.this.cal) == -1L)
                    {
                      FabWebViewActivity.this.resetNativeElements();
                      FabWebViewActivity.this.webView.clearHistory();
                      if (!TextUtils.isEmpty(FabWebViewActivity.this.loadUrl))
                        break label85;
                      FabWebViewActivity.this.checkNetworkAndLoadUrl(FabUtils.requestUrl(false, "/mobile/cart-index/", ""));
                    }
                    while (true)
                    {
                      return;
                      label85: FabWebViewActivity.this.checkNetworkAndLoadUrl(FabWebViewActivity.this.loadUrl);
                    }
                  }
                });
                FabWebViewActivity.this.alertbox.show();
                break;
              }
            }
            if ((FabUtils.getSecondsLeftForTimer(FabWebViewActivity.this.reservedTime, this.cal) == -1L) && (FabWebViewActivity.this.webView != null))
            {
              FabWebViewActivity.this.resetNativeElements();
              FabWebViewActivity.this.webView.clearHistory();
              if (TextUtils.isEmpty(FabWebViewActivity.this.loadUrl))
              {
                FabWebViewActivity.this.checkNetworkAndLoadUrl(FabUtils.requestUrl(false, "/mobile/cart-index/", ""));
              }
              else
              {
                FabWebViewActivity.this.checkNetworkAndLoadUrl(FabWebViewActivity.this.loadUrl);
                continue;
                if (!localBundle.getBoolean("IsSet"))
                {
                  if (FabUtils.getSecondsLeftForTimer(FabWebViewActivity.this.reservedTime, this.cal) > 0L)
                    FabHelper.stopAndRestartCartTimer(FabWebViewActivity.this.getApplicationContext(), FabWebViewActivity.this.reservedTime, FabWebViewActivity.mHandler);
                  if (FabWebViewActivity.this.showTimer)
                    FabWebViewActivity.this.cartTimerHolder.setVisibility(0);
                  else
                    FabWebViewActivity.this.cartTimerHolder.setVisibility(8);
                }
                else
                {
                  FabWebViewActivity.this.cartTimerTextCenter.setText(str1);
                  continue;
                  FabWebViewActivity.this.hasFBShareToken = localBundle.getBoolean("hasFBShareToken");
                  if (FabWebViewActivity.this.fbShareData != null)
                  {
                    FabWebViewActivity.this.fbClient = new FabFBClient(FabWebViewActivity.this.context);
                    FabWebViewActivity.this.fbClient.setFBShareData(FabWebViewActivity.this.fbShareData);
                    FabWebViewActivity.this.fbClient.setHasFBShareToken(FabWebViewActivity.this.hasFBShareToken);
                    FabWebViewActivity.this.fbClient.sendRequest("publish_stream");
                  }
                }
              }
            }
          }
        }
      };
      if (!TextUtils.isEmpty(this.loadUrl))
        break label678;
      checkNetworkAndLoadUrl(FabUtils.requestUrl(false, "/mobile/cart-index/", ""));
    }
    while (true)
    {
      this.tracker = GoogleAnalyticsTracker.getInstance();
      this.tracker.startNewSession("UA-19838455-1", 10, this);
      this.tracker.trackPageView(FabUtils.gATracUrl("shoppingCart"));
      return;
      label635: str2 = "";
      break;
      String str1 = arrayOfString[j];
      this.cookieManager.setCookie(FabEnvironment.baseDomain(), str1.split("<@@>")[0]);
      j += 1;
      break label376;
      label678: checkNetworkAndLoadUrl(this.loadUrl);
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131296256, paramMenu);
    MenuItem localMenuItem1 = paramMenu.findItem(2131362102);
    localMenuItem1.setVisible(true);
    localMenuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(FabWebViewActivity.this.context, FabHome.class);
        localIntent.setFlags(67108864);
        FabWebViewActivity.this.startActivity(localIntent);
        FabWebViewActivity.this.finish();
        return false;
      }
    });
    MenuItem localMenuItem2 = paramMenu.findItem(2131361948);
    localMenuItem2.setVisible(true);
    localMenuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(FabWebViewActivity.this.context, MoreActivity.class);
        FabWebViewActivity.this.startActivity(localIntent);
        FabWebViewActivity.this.finish();
        return false;
      }
    });
    MenuItem localMenuItem3 = paramMenu.findItem(2131361908);
    localMenuItem3.setVisible(true);
    localMenuItem3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(FabWebViewActivity.this.context, InviteActivity.class);
        FabWebViewActivity.this.startActivity(localIntent);
        FabWebViewActivity.this.finish();
        return false;
      }
    });
    MenuItem localMenuItem4 = paramMenu.findItem(2131362103);
    localMenuItem4.setVisible(true);
    localMenuItem4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        FabWebViewActivity.this.resetNativeElements();
        FabWebViewActivity.this.checkNetworkAndReLoadUrl();
        return false;
      }
    });
    return true;
  }

  protected void onDestroy()
  {
    this.tracker.stopSession();
    this.reservedTime = 0L;
    isActivityRunning = false;
    this.webView.clearCache(true);
    this.webView.destroy();
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
      if ((this.shouldGoBack) && (this.isBackActive))
      {
        if (TextUtils.isEmpty(this.backUrl))
          break label41;
        checkNetworkAndLoadUrl(this.backUrl);
      }
    for (boolean bool = true; ; bool = super.onKeyDown(paramInt, paramKeyEvent))
    {
      return bool;
      label41: finish();
      break;
    }
  }

  protected void onPause()
  {
    isActivityRunning = false;
    super.onPause();
  }

  protected void onRestart()
  {
    if (cancelRelogin)
    {
      if (this.isCheckoutProcess)
        cancelRelogin = false;
    }
    else
    {
      super.onRestart();
      if (mHandler != null)
        mHandler.sendEmptyMessage(8);
      isActivityRunning = true;
      if (FabUtils.isLoggedIn(this.context))
        break label93;
      finish();
      Intent localIntent = new Intent(this.context, FabIndex.class);
      localIntent.setFlags(67108864);
      startActivity(localIntent);
    }
    while (true)
    {
      return;
      finish();
      break;
      label93: this.cookieManager = CookieManager.getInstance();
      this.cookiesArray = FabSharedPrefs.getFabCookies().split("<==>");
      String[] arrayOfString = this.cookiesArray;
      int i = arrayOfString.length;
      int j = 0;
      while (true)
      {
        if (j >= i)
        {
          CookieSyncManager.getInstance().sync();
          if (!this.startIndex)
            break label216;
          this.startIndex = false;
          if (!TextUtils.isEmpty(this.loadUrl))
            break label205;
          checkNetworkAndLoadUrl(FabUtils.requestUrl(false, "/mobile/cart-index/", ""));
          break;
        }
        String str = arrayOfString[j];
        this.cookieManager.setCookie(FabEnvironment.baseDomain(), str.split("<@@>")[0]);
        j += 1;
      }
      label205: checkNetworkAndLoadUrl(this.loadUrl);
      continue;
      label216: checkNetworkAndReLoadUrl();
    }
  }

  protected void onResume()
  {
    super.onResume();
    isActivityRunning = true;
    if ((FabWebViewClient.hasPageLoaded) && (FabWebViewClient.dialog != null))
      FabWebViewClient.dialog.cancel();
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
    String[] arrayOfString = this.cookiesArray;
    int i = arrayOfString.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        CookieSyncManager.getInstance().sync();
        break;
      }
      String str = arrayOfString[j];
      this.cookieManager.setCookie(FabEnvironment.baseDomain(), str.split("<@@>")[0]);
    }
  }

  public void onSetButtonData(String paramString)
  {
    FabUtils.log(this.TAG, paramString);
    try
    {
      JSONObject localJSONObject1 = new JSONObject(paramString);
      String str1 = localJSONObject1.getString("type");
      JSONObject localJSONObject2 = localJSONObject1.getJSONObject("params");
      JSONArray localJSONArray;
      int i;
      int j;
      if (str1.equalsIgnoreCase("ui"))
      {
        localJSONArray = new JSONArray();
        if (localJSONObject2.names() != null)
          localJSONArray = localJSONObject2.names();
        i = 0;
        j = localJSONArray.length();
      }
      while (true)
      {
        if (i >= j)
        {
          if (!localJSONObject2.has("centerText"))
          {
            Message localMessage5 = new Message();
            Bundle localBundle5 = new Bundle();
            localBundle5.putInt("Type", 4);
            localBundle5.putString("Value", this.webView.getTitle());
            localBundle5.putInt("Visibility", 0);
            localMessage5.setData(localBundle5);
            mHandler.sendMessage(localMessage5);
          }
          label165: return;
        }
        try
        {
          String str2 = localJSONArray.getString(i);
          JSONObject localJSONObject3 = localJSONObject2.getJSONObject(str2);
          Message localMessage1;
          Bundle localBundle1;
          if (str2.equalsIgnoreCase("backButton"))
          {
            localMessage1 = new Message();
            localBundle1 = new Bundle();
            localBundle1.putInt("Type", 5);
          }
          try
          {
            localBundle1.putString("Value", localJSONObject3.getString("label"));
            try
            {
              label238: int k;
              if (!localJSONObject3.getBoolean("hidden"))
              {
                k = 1;
                label252: localBundle1.putInt("Visibility", k);
              }
              try
              {
                label262: localBundle1.putBoolean("Enable", localJSONObject3.getBoolean("back"));
                try
                {
                  while (true)
                  {
                    label278: Object localObject;
                    if (TextUtils.isEmpty(localJSONObject3.getString("url")))
                      localObject = "";
                    while (true)
                    {
                      localBundle1.putString("url", (String)localObject);
                      label307: localMessage1.setData(localBundle1);
                      mHandler.sendMessage(localMessage1);
                      label323: i++;
                      break;
                      k = 0;
                      break label252;
                      if (localJSONObject3.getString("url").equalsIgnoreCase("/"))
                      {
                        localObject = FabUtils.requestUrl(false, "/mobile/", "");
                      }
                      else
                      {
                        String str3 = FabUtils.requestUrl(false, "/mobile/" + localJSONObject3.getString("url"), "");
                        localObject = str3;
                      }
                    }
                    boolean bool1 = str2.equalsIgnoreCase("rightButton");
                    if (bool1);
                    try
                    {
                      while (true)
                      {
                        this.jsOnclickRight = localJSONObject2.getJSONObject(str2).getJSONObject("onClick").toString();
                        label442: Message localMessage4 = new Message();
                        Bundle localBundle4 = new Bundle();
                        localBundle4.putInt("Type", 6);
                        try
                        {
                          label648: 
                          while (true)
                          {
                            localBundle4.putString("Value", localJSONObject3.getString("label"));
                            label526: label785: 
                            try
                            {
                              label551: label605: 
                              while (true)
                                label486: if (!localJSONObject3.getBoolean("hidden"))
                                {
                                  i1 = 0;
                                  localBundle4.putInt("Visibility", i1);
                                  try
                                  {
                                    while (true)
                                    {
                                      label510: localBundle4.putBoolean("Enable", localJSONObject3.getBoolean("active"));
                                      try
                                      {
                                        while (true)
                                        {
                                          localBundle4.putString("icon", localJSONObject3.getString("icon").replace("-", "_"));
                                          localMessage4.setData(localBundle4);
                                          mHandler.sendMessage(localMessage4);
                                          break label323;
                                          boolean bool2 = str2.equalsIgnoreCase("extraButton");
                                          if (bool2);
                                          try
                                          {
                                            while (true)
                                            {
                                              this.jsOnclickExtra = localJSONObject2.getJSONObject(str2).getJSONObject("onClick").toString();
                                              Message localMessage3 = new Message();
                                              Bundle localBundle3 = new Bundle();
                                              localBundle3.putInt("Type", 3);
                                              try
                                              {
                                                while (true)
                                                {
                                                  localBundle3.putString("Value", localJSONObject3.getString("label"));
                                                  try
                                                  {
                                                    while (true)
                                                      if (!localJSONObject3.getBoolean("hidden"))
                                                      {
                                                        n = 0;
                                                        localBundle3.putInt("Visibility", n);
                                                        try
                                                        {
                                                          while (true)
                                                          {
                                                            localBundle3.putBoolean("Enable", localJSONObject3.getBoolean("active"));
                                                            localMessage3.setData(localBundle3);
                                                            mHandler.sendMessage(localMessage3);
                                                            break label323;
                                                            if (!str2.equalsIgnoreCase("centerText"))
                                                              break label323;
                                                            Message localMessage2 = new Message();
                                                            Bundle localBundle2 = new Bundle();
                                                            localBundle2.putInt("Type", 4);
                                                            try
                                                            {
                                                              while (true)
                                                              {
                                                                localBundle2.putString("Value", localJSONObject3.getString("label"));
                                                                try
                                                                {
                                                                  label761: if (!localJSONObject3.getBoolean("hidden"));
                                                                  for (int m = 0; ; m = 8)
                                                                  {
                                                                    localBundle2.putInt("Visibility", m);
                                                                    localMessage2.setData(localBundle2);
                                                                    mHandler.sendMessage(localMessage2);
                                                                    break;
                                                                  }
                                                                  if (str1.equalsIgnoreCase("native"))
                                                                  {
                                                                    invokeNativeMethod(localJSONObject2.getString("method"), localJSONObject2.getJSONObject("params"));
                                                                    FabUtils.log(this.TAG, paramString);
                                                                    break;
                                                                  }
                                                                  str1.equalsIgnoreCase("data");
                                                                }
                                                                catch (JSONException localJSONException8)
                                                                {
                                                                  break label785;
                                                                }
                                                              }
                                                            }
                                                            catch (JSONException localJSONException7)
                                                            {
                                                              break label761;
                                                            }
                                                          }
                                                        }
                                                        catch (JSONException localJSONException12)
                                                        {
                                                          break label688;
                                                        }
                                                      }
                                                  }
                                                  catch (JSONException localJSONException11)
                                                  {
                                                    break label672;
                                                  }
                                                }
                                              }
                                              catch (JSONException localJSONException10)
                                              {
                                                break label648;
                                              }
                                            }
                                          }
                                          catch (JSONException localJSONException9)
                                          {
                                            break label605;
                                          }
                                        }
                                      }
                                      catch (JSONException localJSONException17)
                                      {
                                        break label551;
                                      }
                                    }
                                  }
                                  catch (JSONException localJSONException16)
                                  {
                                    break label526;
                                  }
                                }
                            }
                            catch (JSONException localJSONException15)
                            {
                              label672: label688: break label510;
                            }
                          }
                        }
                        catch (JSONException localJSONException14)
                        {
                          break label486;
                        }
                      }
                    }
                    catch (JSONException localJSONException13)
                    {
                      break label442;
                    }
                  }
                }
                catch (JSONException localJSONException6)
                {
                  break label307;
                }
              }
              catch (JSONException localJSONException5)
              {
                break label278;
              }
            }
            catch (JSONException localJSONException4)
            {
              break label262;
            }
          }
          catch (JSONException localJSONException3)
          {
            break label238;
          }
        }
        catch (JSONException localJSONException2)
        {
          while (true)
          {
            continue;
            int i1 = 8;
            continue;
            int n = 8;
          }
        }
      }
    }
    catch (JSONException localJSONException1)
    {
      break label165;
    }
  }

  public void setButtonListener(ButtonListener paramButtonListener)
  {
    this.listener = paramButtonListener;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.webviews.FabWebViewActivity
 * JD-Core Version:    0.6.2
 */