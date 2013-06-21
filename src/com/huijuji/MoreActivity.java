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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.fab.bo.MoreBo;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabHelper;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.ErrorMessage;
import com.fab.views.FabButton;
import com.fab.views.FabTextView;
import com.fab.views.TitleWidget;
import com.fab.webviews.FabWebViewActivity;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import java.util.ArrayList;
import org.json.JSONObject;

public class MoreActivity extends Activity
{
  protected static final String TAG = "MoreActivity";
  protected static boolean isActivityAlive;
  private MoreAdapter adapter;
  protected AlertDialog.Builder alertbox;
  Context context;
  private ProgressDialog dialog;
  private ErrorMessage error;
  private Bundle extras;
  private View footerView;
  private FabButton headerCartButton;
  private boolean isGuestUser;
  private FabButton logoutBtn;
  private Handler mHandler;
  private FabTextView more;
  private ListView moreLV;
  private ProgressBar pBar;
  private String pageTile;
  private String requestUrl;
  private ArrayList<MoreBo> theItems;
  private FabTextView timer;
  private TitleWidget titleWidget;
  private GoogleAnalyticsTracker tracker;
  private FabTextView version;

  private void loadData()
  {
    final ExecuteRequestAsyncTask localExecuteRequestAsyncTask = new ExecuteRequestAsyncTask(this.context, "GET");
    localExecuteRequestAsyncTask.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      public void onTaskComplete(Object paramAnonymousObject)
      {
        JSONObject localJSONObject = null;
        if ((paramAnonymousObject != null) && ((paramAnonymousObject instanceof JSONObject)))
          localJSONObject = (JSONObject)paramAnonymousObject;
        MoreActivity.this.populateResponse(localJSONObject, localExecuteRequestAsyncTask.getErrMsg());
      }
    });
    String[] arrayOfString = new String[1];
    if (TextUtils.isEmpty(this.requestUrl));
    for (String str = "/mobile/more-options/"; ; str = this.requestUrl)
    {
      arrayOfString[0] = FabUtils.requestUrl(false, str, "");
      localExecuteRequestAsyncTask.execute(arrayOfString);
      return;
    }
  }

  private void populateUI()
  {
    if (FabSharedPrefs.isGuestUser())
    {
      this.isGuestUser = true;
      this.pBar.setVisibility(0);
      this.error.setVisibility(8);
      this.moreLV.setVisibility(8);
      this.logoutBtn.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (MoreActivity.this.isGuestUser)
          {
            Intent localIntent = new Intent(MoreActivity.this.context, FabSignin.class);
            localIntent.setFlags(603979776);
            localIntent.putExtra("IS_FROM_GUEST", true);
            MoreActivity.this.startActivity(localIntent);
          }
          while (true)
          {
            return;
            if (MoreActivity.isActivityAlive)
            {
              MoreActivity.this.dialog.show();
              MoreActivity.this.dialog.setMessage("Logging out...");
              MoreActivity.this.dialog.setCancelable(true);
            }
            new Thread(new Runnable()
            {
              public void run()
              {
                FabUtils.logoutUser(MoreActivity.this.context, MoreActivity.this.mHandler);
              }
            }).start();
          }
        }
      });
      if (!this.isGuestUser)
        break label86;
      this.logoutBtn.setBackgroundResource(2130837652);
      this.logoutBtn.setText("Sign-in");
    }
    while (true)
    {
      return;
      this.isGuestUser = false;
      break;
      label86: this.logoutBtn.setBackgroundResource(2130837659);
      this.logoutBtn.setText("Logout");
    }
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903070);
    this.context = this;
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("morePage"));
    if (!FabUtils.isLoggedIn(this.context))
    {
      finish();
      Intent localIntent = new Intent(this.context, FabIndex.class);
      localIntent.setFlags(67108864);
      startActivity(localIntent);
    }
    this.extras = getIntent().getExtras();
    if (this.extras != null)
    {
      this.requestUrl = this.extras.getString("REQUEST_URL");
      this.pageTile = this.extras.getString("TITLE");
    }
    this.dialog = new ProgressDialog(this.context);
    this.titleWidget = ((TitleWidget)findViewById(2131361810));
    this.timer = ((FabTextView)findViewById(2131361795));
    this.timer.setFont("georgiai.ttf");
    isActivityAlive = true;
    this.alertbox = new AlertDialog.Builder(this);
    this.more = ((FabTextView)findViewById(2131361948));
    this.more.setFont("HelveticaNeu_bold.ttf");
    this.logoutBtn = ((FabButton)findViewById(2131361949));
    this.pBar = ((ProgressBar)findViewById(2131361874));
    this.error = ((ErrorMessage)findViewById(2131361811));
    this.moreLV = ((ListView)findViewById(2131361950));
    this.theItems = new ArrayList();
    if (!TextUtils.isEmpty(this.pageTile))
    {
      this.more.setText(this.pageTile);
      this.logoutBtn.setVisibility(8);
    }
    while (true)
    {
      this.adapter = new MoreAdapter(this, this.theItems);
      this.moreLV.setAdapter(this.adapter);
      populateUI();
      loadData();
      this.mHandler = new Handler()
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          Bundle localBundle = paramAnonymousMessage.getData();
          if (FabUtils.isLogEnabled())
            FabUtils.log("MoreActivity", localBundle.toString());
          int i = localBundle.getInt("Type");
          String str = localBundle.getString("Value");
          if (paramAnonymousMessage.what == 1)
          {
            if ((MoreActivity.this.dialog != null) && (MoreActivity.this.dialog.isShowing()))
              MoreActivity.this.dialog.dismiss();
            MoreActivity.this.finish();
            Intent localIntent = new Intent(MoreActivity.this.context, FabIndex.class);
            localIntent.setFlags(67108864);
            MoreActivity.this.startActivity(localIntent);
          }
          switch (i)
          {
          default:
          case 2:
          case 1:
          }
          while (true)
          {
            return;
            if (localBundle.getBoolean("Expire"))
              MoreActivity.this.timer.setVisibility(4);
            if (MoreActivity.isActivityAlive)
            {
              AlertDialog.Builder localBuilder = MoreActivity.this.alertbox;
              if (localBundle.getInt("Error") == 1);
              for (int j = 2130837504; ; j = 2130837507)
              {
                localBuilder.setIcon(j);
                MoreActivity.this.alertbox.setTitle(localBundle.getString("Title"));
                MoreActivity.this.alertbox.setMessage(localBundle.getString("Message"));
                MoreActivity.this.alertbox.setNeutralButton("OK", null);
                MoreActivity.this.alertbox.show();
                break;
              }
              MoreActivity.this.timer.setVisibility(0);
              MoreActivity.this.timer.setText(str);
            }
          }
        }
      };
      this.headerCartButton = this.titleWidget.getCartButton();
      return;
      this.footerView = ((LayoutInflater)getSystemService("layout_inflater")).inflate(2130903071, null);
      this.version = ((FabTextView)this.footerView.findViewById(2131361951));
      this.version.setFont("HelveticaNeu_normal.ttf");
      this.version.setText(getResources().getString(2131165206) + FabApplication.APP_VERSION);
      this.moreLV.addFooterView(this.footerView);
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131296256, paramMenu);
    MenuItem localMenuItem1 = paramMenu.findItem(2131362109);
    localMenuItem1.setVisible(true);
    localMenuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(MoreActivity.this.context, FabWebViewActivity.class);
        MoreActivity.this.startActivity(localIntent);
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
          FabUtils.showGuestAlert(MoreActivity.this.context, MoreActivity.this.context.getResources().getString(2131165208), MoreActivity.this.context.getResources().getString(2131165209), false);
        while (true)
        {
          return false;
          Intent localIntent = new Intent(MoreActivity.this.context, InviteActivity.class);
          MoreActivity.this.startActivity(localIntent);
        }
      }
    });
    return super.onCreateOptionsMenu(paramMenu);
  }

  protected void onDestroy()
  {
    this.tracker.stopSession();
    if (TextUtils.isEmpty(this.requestUrl))
      isActivityAlive = false;
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.cancel();
    super.onDestroy();
  }

  protected void onPause()
  {
    isActivityAlive = false;
    if ((this.adapter != null) && (this.adapter.getDialog() != null) && (this.adapter.getDialog().isShowing()))
      this.adapter.getDialog().cancel();
    super.onPause();
  }

  protected void onRestart()
  {
    isActivityAlive = true;
    FabHelper.stopAndRestartCartTimer(getApplicationContext(), FabHelper.getCartTime(), this.mHandler);
    updateCartCounter(FabHelper.getCartCount());
    super.onRestart();
    if (!FabUtils.isLoggedIn(this.context))
    {
      finish();
      Intent localIntent = new Intent(this.context, FabIndex.class);
      localIntent.setFlags(67108864);
      startActivity(localIntent);
    }
    if (this.isGuestUser != FabSharedPrefs.isGuestUser())
    {
      populateUI();
      loadData();
    }
  }

  protected void onResume()
  {
    if (!FabUtils.isLoggedIn(this.context))
    {
      finish();
      Intent localIntent = new Intent(this.context, FabIndex.class);
      localIntent.setFlags(67108864);
      startActivity(localIntent);
    }
    this.error.setVisibility(8);
    FabHelper.stopAndRestartCartTimer(getApplicationContext(), FabHelper.getCartTime(), this.mHandler);
    updateCartCounter(FabHelper.getCartCount());
    isActivityAlive = true;
    super.onResume();
  }

  // ERROR //
  public void populateResponse(JSONObject paramJSONObject, String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +582 -> 583
    //   4: ldc 19
    //   6: aload_1
    //   7: invokevirtual 458	org/json/JSONObject:toString	()Ljava/lang/String;
    //   10: invokestatic 462	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
    //   13: iconst_0
    //   14: istore_3
    //   15: aload_1
    //   16: ldc_w 464
    //   19: invokevirtual 468	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
    //   22: istore 44
    //   24: iload 44
    //   26: istore_3
    //   27: iload_3
    //   28: ifeq +18 -> 46
    //   31: aload_0
    //   32: getfield 83	com/fab/MoreActivity:context	Landroid/content/Context;
    //   35: aload_0
    //   36: getfield 78	com/fab/MoreActivity:mHandler	Landroid/os/Handler;
    //   39: invokestatic 472	com/fab/utils/FabUtils:logoutUser	(Landroid/content/Context;Landroid/os/Handler;)V
    //   42: aload_0
    //   43: invokevirtual 209	com/fab/MoreActivity:finish	()V
    //   46: aload_1
    //   47: ldc_w 474
    //   50: invokevirtual 478	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   53: astore 43
    //   55: aload_0
    //   56: aload 43
    //   58: ldc_w 480
    //   61: invokevirtual 484	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   64: invokevirtual 446	com/fab/MoreActivity:updateCartCounter	(I)V
    //   67: aload 43
    //   69: ldc_w 486
    //   72: invokevirtual 484	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   75: i2l
    //   76: new 488	java/util/GregorianCalendar
    //   79: dup
    //   80: invokespecial 489	java/util/GregorianCalendar:<init>	()V
    //   83: invokestatic 493	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
    //   86: ldc2_w 494
    //   89: lcmp
    //   90: iflt +35 -> 125
    //   93: aload 43
    //   95: ldc_w 486
    //   98: invokevirtual 484	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   101: i2l
    //   102: invokestatic 499	com/fab/utils/FabHelper:setCartTime	(J)V
    //   105: aload_0
    //   106: invokevirtual 429	com/fab/MoreActivity:getApplicationContext	()Landroid/content/Context;
    //   109: aload 43
    //   111: ldc_w 486
    //   114: invokevirtual 484	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   117: i2l
    //   118: aload_0
    //   119: getfield 78	com/fab/MoreActivity:mHandler	Landroid/os/Handler;
    //   122: invokestatic 439	com/fab/utils/FabHelper:stopAndRestartCartTimer	(Landroid/content/Context;JLandroid/os/Handler;)V
    //   125: new 457	org/json/JSONObject
    //   128: dup
    //   129: invokespecial 500	org/json/JSONObject:<init>	()V
    //   132: astore 6
    //   134: new 502	org/json/JSONArray
    //   137: dup
    //   138: invokespecial 503	org/json/JSONArray:<init>	()V
    //   141: astore 7
    //   143: aload_1
    //   144: ldc_w 505
    //   147: invokevirtual 509	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   150: astore 42
    //   152: aload 42
    //   154: astore 7
    //   156: new 502	org/json/JSONArray
    //   159: dup
    //   160: invokespecial 503	org/json/JSONArray:<init>	()V
    //   163: astore 9
    //   165: aload_1
    //   166: ldc_w 511
    //   169: invokevirtual 509	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   172: astore 41
    //   174: aload 41
    //   176: astore 9
    //   178: new 502	org/json/JSONArray
    //   181: dup
    //   182: invokespecial 503	org/json/JSONArray:<init>	()V
    //   185: astore 11
    //   187: aload_1
    //   188: ldc_w 513
    //   191: invokevirtual 509	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   194: astore 40
    //   196: aload 40
    //   198: astore 11
    //   200: new 502	org/json/JSONArray
    //   203: dup
    //   204: invokespecial 503	org/json/JSONArray:<init>	()V
    //   207: astore 13
    //   209: aload_0
    //   210: getfield 289	com/fab/MoreActivity:theItems	Ljava/util/ArrayList;
    //   213: invokevirtual 516	java/util/ArrayList:clear	()V
    //   216: iconst_0
    //   217: istore 14
    //   219: aload 9
    //   221: invokevirtual 519	org/json/JSONArray:length	()I
    //   224: istore 15
    //   226: iload 14
    //   228: iload 15
    //   230: if_icmplt +4 -> 234
    //   233: return
    //   234: aload 9
    //   236: iload 14
    //   238: invokevirtual 522	org/json/JSONArray:getJSONArray	(I)Lorg/json/JSONArray;
    //   241: astore 39
    //   243: aload 39
    //   245: astore 13
    //   247: aload 11
    //   249: iload 14
    //   251: invokevirtual 525	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   254: ldc_w 527
    //   257: invokevirtual 528	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   260: astore 36
    //   262: new 530	com/fab/bo/MoreBo
    //   265: dup
    //   266: invokespecial 531	com/fab/bo/MoreBo:<init>	()V
    //   269: astore 37
    //   271: aload 37
    //   273: aload 36
    //   275: invokevirtual 534	com/fab/bo/MoreBo:setSectionSeparatorText	(Ljava/lang/String;)V
    //   278: aload 37
    //   280: iconst_1
    //   281: invokevirtual 538	com/fab/bo/MoreBo:setSectionSeparator	(Z)V
    //   284: aload_0
    //   285: getfield 289	com/fab/MoreActivity:theItems	Ljava/util/ArrayList;
    //   288: aload 37
    //   290: invokevirtual 542	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   293: pop
    //   294: iconst_0
    //   295: istore 18
    //   297: aload 13
    //   299: invokevirtual 519	org/json/JSONArray:length	()I
    //   302: istore 19
    //   304: iload 18
    //   306: iload 19
    //   308: if_icmplt +60 -> 368
    //   311: aload_0
    //   312: getfield 298	com/fab/MoreActivity:adapter	Lcom/fab/MoreAdapter;
    //   315: invokevirtual 545	com/fab/MoreAdapter:notifyDataSetChanged	()V
    //   318: aload_0
    //   319: getfield 126	com/fab/MoreActivity:pBar	Landroid/widget/ProgressBar;
    //   322: bipush 8
    //   324: invokevirtual 132	android/widget/ProgressBar:setVisibility	(I)V
    //   327: aload_0
    //   328: getfield 139	com/fab/MoreActivity:moreLV	Landroid/widget/ListView;
    //   331: iconst_0
    //   332: invokevirtual 142	android/widget/ListView:setVisibility	(I)V
    //   335: aload_1
    //   336: ldc_w 547
    //   339: invokevirtual 528	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   342: invokestatic 105	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   345: ifne +17 -> 362
    //   348: aload_0
    //   349: getfield 83	com/fab/MoreActivity:context	Landroid/content/Context;
    //   352: aload_1
    //   353: ldc_w 547
    //   356: invokevirtual 528	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   359: invokestatic 550	com/fab/utils/FabUtils:showAlert	(Landroid/content/Context;Ljava/lang/String;)V
    //   362: iinc 14 1
    //   365: goto -139 -> 226
    //   368: new 530	com/fab/bo/MoreBo
    //   371: dup
    //   372: invokespecial 531	com/fab/bo/MoreBo:<init>	()V
    //   375: astore 20
    //   377: aload 7
    //   379: aload 13
    //   381: iload 18
    //   383: invokevirtual 553	org/json/JSONArray:getInt	(I)I
    //   386: invokevirtual 525	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   389: astore 34
    //   391: aload 34
    //   393: astore 6
    //   395: aload 20
    //   397: aload 6
    //   399: ldc_w 555
    //   402: invokevirtual 528	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   405: invokevirtual 558	com/fab/bo/MoreBo:setHeading	(Ljava/lang/String;)V
    //   408: aload 6
    //   410: ldc_w 560
    //   413: invokevirtual 528	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   416: astore 33
    //   418: aload 33
    //   420: astore 24
    //   422: aload 20
    //   424: aload 24
    //   426: invokevirtual 563	com/fab/bo/MoreBo:setSubHeading	(Ljava/lang/String;)V
    //   429: aload 20
    //   431: aload 6
    //   433: ldc_w 565
    //   436: invokevirtual 528	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   439: invokevirtual 568	com/fab/bo/MoreBo:setUrl	(Ljava/lang/String;)V
    //   442: aload 6
    //   444: ldc_w 570
    //   447: invokevirtual 484	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   450: ifne +97 -> 547
    //   453: iconst_0
    //   454: istore 32
    //   456: aload 20
    //   458: iload 32
    //   460: invokevirtual 573	com/fab/bo/MoreBo:setSecure	(Z)V
    //   463: aload 20
    //   465: aload 6
    //   467: ldc_w 575
    //   470: invokevirtual 468	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
    //   473: invokevirtual 578	com/fab/bo/MoreBo:setBackButtonControlled	(Z)V
    //   476: aload 6
    //   478: ldc_w 580
    //   481: invokevirtual 484	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   484: iconst_1
    //   485: if_icmpne +68 -> 553
    //   488: aload 20
    //   490: ldc_w 580
    //   493: invokevirtual 583	com/fab/bo/MoreBo:setSettingType	(Ljava/lang/String;)V
    //   496: aload 20
    //   498: aload 6
    //   500: ldc_w 585
    //   503: invokevirtual 468	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
    //   506: invokevirtual 588	com/fab/bo/MoreBo:setCBEnabled	(Z)V
    //   509: aload 20
    //   511: aload 6
    //   513: ldc_w 590
    //   516: invokevirtual 484	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   519: invokevirtual 593	com/fab/bo/MoreBo:setNotificationType	(I)V
    //   522: aload_0
    //   523: getfield 289	com/fab/MoreActivity:theItems	Ljava/util/ArrayList;
    //   526: aload 20
    //   528: invokevirtual 542	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   531: pop
    //   532: iinc 18 1
    //   535: goto -231 -> 304
    //   538: astore 23
    //   540: ldc 109
    //   542: astore 24
    //   544: goto -122 -> 422
    //   547: iconst_1
    //   548: istore 32
    //   550: goto -94 -> 456
    //   553: aload 24
    //   555: invokestatic 105	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   558: ifne +14 -> 572
    //   561: aload 20
    //   563: ldc_w 595
    //   566: invokevirtual 583	com/fab/bo/MoreBo:setSettingType	(Ljava/lang/String;)V
    //   569: goto -73 -> 496
    //   572: aload 20
    //   574: ldc_w 597
    //   577: invokevirtual 583	com/fab/bo/MoreBo:setSettingType	(Ljava/lang/String;)V
    //   580: goto -84 -> 496
    //   583: aload_0
    //   584: getfield 126	com/fab/MoreActivity:pBar	Landroid/widget/ProgressBar;
    //   587: bipush 8
    //   589: invokevirtual 132	android/widget/ProgressBar:setVisibility	(I)V
    //   592: aload_2
    //   593: invokestatic 105	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   596: ifne -363 -> 233
    //   599: aload_0
    //   600: getfield 134	com/fab/MoreActivity:error	Lcom/fab/views/ErrorMessage;
    //   603: iconst_0
    //   604: invokevirtual 137	com/fab/views/ErrorMessage:setVisibility	(I)V
    //   607: aload_0
    //   608: getfield 134	com/fab/MoreActivity:error	Lcom/fab/views/ErrorMessage;
    //   611: aload_2
    //   612: invokevirtual 600	com/fab/views/ErrorMessage:setErrorMsg	(Ljava/lang/String;)V
    //   615: goto -382 -> 233
    //   618: astore 16
    //   620: goto -373 -> 247
    //   623: astore 35
    //   625: goto -263 -> 362
    //   628: astore 30
    //   630: goto -108 -> 522
    //   633: astore 29
    //   635: goto -126 -> 509
    //   638: astore 27
    //   640: goto -164 -> 476
    //   643: astore 26
    //   645: goto -182 -> 463
    //   648: astore 25
    //   650: goto -208 -> 442
    //   653: astore 22
    //   655: goto -247 -> 408
    //   658: astore 21
    //   660: goto -265 -> 395
    //   663: astore 17
    //   665: goto -371 -> 294
    //   668: astore 12
    //   670: goto -470 -> 200
    //   673: astore 10
    //   675: goto -497 -> 178
    //   678: astore 8
    //   680: goto -524 -> 156
    //   683: astore 5
    //   685: goto -560 -> 125
    //   688: astore 4
    //   690: goto -663 -> 27
    //   693: astore 28
    //   695: goto -199 -> 496
    //
    // Exception table:
    //   from	to	target	type
    //   408	418	538	org/json/JSONException
    //   234	243	618	org/json/JSONException
    //   335	362	623	org/json/JSONException
    //   509	522	628	org/json/JSONException
    //   496	509	633	org/json/JSONException
    //   463	476	638	org/json/JSONException
    //   442	463	643	org/json/JSONException
    //   429	442	648	org/json/JSONException
    //   395	408	653	org/json/JSONException
    //   377	391	658	org/json/JSONException
    //   247	294	663	org/json/JSONException
    //   187	196	668	org/json/JSONException
    //   165	174	673	org/json/JSONException
    //   143	152	678	org/json/JSONException
    //   46	125	683	org/json/JSONException
    //   15	24	688	org/json/JSONException
    //   476	496	693	org/json/JSONException
    //   553	580	693	org/json/JSONException
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
      FabHelper.setCartCount(paramInt);
      return;
      this.headerCartButton.setBackgroundResource(this.context.getResources().getIdentifier("cart_button_sel", "drawable", "com.fab"));
      this.headerCartButton.setText("");
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.MoreActivity
 * JD-Core Version:    0.6.2
 */