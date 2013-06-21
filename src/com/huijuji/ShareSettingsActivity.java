package com.fab;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.fab.bo.FBPreferencesBo;
import com.fab.facebook.FabFBClient;
import com.fab.twitter.FabTwitterClient;
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
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import twitter4j.http.AccessToken;

public class ShareSettingsActivity extends Activity
{
  protected static boolean isActivityAlive;
  private int FB_LOGOUT_FAILURE = 2;
  private int FB_LOGOUT_SUCCESS = 1;
  private int HIDE_CONTENT = 7;
  private int REFRESH_ACTIVITY = 6;
  protected String TAG = "SHARE_SETTINGS_ACTIVTY";
  private int TIMELINE_ENABLED = 5;
  private int TW_FAILURE = 4;
  private int TW_SUCCESS = 3;
  private ShareSettingsAdapter adapter;
  private AlertDialog.Builder alertbox;
  Context context;
  private ProgressDialog dialog;
  private ErrorMessage error;
  private CheckBox fbCheckBox;
  private FabFBClient fbClient;
  protected int fbPublishAction;
  private RelativeLayout fbRow;
  private FabTextView fbSessionStatus;
  private RelativeLayout fbTimelineActiveHolder;
  private RelativeLayout fbTimelineInactiveHolder;
  private JSONArray fbTimelinePreferences;
  private FabTextView fbTimelineSubHeader;
  private FabButton headerCartButton;
  private View headerView;
  protected boolean isTimelineActive;
  private ListView listView;
  private Handler mHandler;
  protected HashMap<String, String> parameters;
  private ProgressBar progressBar;
  private RelativeLayout settingsHolder;
  private ExecuteRequestAsyncTask task;
  private ArrayList<FBPreferencesBo> theFBPreferences;
  private CheckBox timelineCheckBox;
  private FabTextView timelineConditionText;
  private FabTextView timelineText;
  private FabTextView timer;
  private Handler timerHandler;
  private TitleWidget titleWidget;
  private GoogleAnalyticsTracker tracker;
  private Button tryItNowButton;
  private CheckBox twCheckBox;

  private AccessToken getAccessToken()
  {
    SharedPreferences localSharedPreferences = getSharedPreferences("twitterLogin", 0);
    String str1 = localSharedPreferences.getString("accessTokenToken", "");
    String str2 = localSharedPreferences.getString("accessTokenSecret", "");
    if ((str1 != null) && (str2 != null) && (!"".equals(str2)) && (!"".equals(str1)));
    for (AccessToken localAccessToken = new AccessToken(str1, str2); ; localAccessToken = null)
      return localAccessToken;
  }

  private void loadShareSettings()
  {
    this.task = new ExecuteRequestAsyncTask(this.context, "GET");
    this.task.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      // ERROR //
      public void onTaskComplete(Object paramAnonymousObject)
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore_2
        //   2: aload_1
        //   3: checkcast 30	org/json/JSONObject
        //   6: astore_3
        //   7: aload_3
        //   8: ifnull +422 -> 430
        //   11: aload_0
        //   12: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   15: getfield 34	com/fab/ShareSettingsActivity:TAG	Ljava/lang/String;
        //   18: aload_3
        //   19: invokevirtual 38	org/json/JSONObject:toString	()Ljava/lang/String;
        //   22: invokestatic 44	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
        //   25: iconst_0
        //   26: istore 4
        //   28: aload_3
        //   29: ldc 46
        //   31: invokevirtual 50	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
        //   34: istore 21
        //   36: iload 21
        //   38: istore 4
        //   40: iload 4
        //   42: ifeq +21 -> 63
        //   45: aload_0
        //   46: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   49: getfield 54	com/fab/ShareSettingsActivity:context	Landroid/content/Context;
        //   52: aconst_null
        //   53: invokestatic 58	com/fab/utils/FabUtils:logoutUser	(Landroid/content/Context;Landroid/os/Handler;)V
        //   56: aload_0
        //   57: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   60: invokevirtual 61	com/fab/ShareSettingsActivity:finish	()V
        //   63: aload_3
        //   64: ldc 63
        //   66: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   69: pop
        //   70: aconst_null
        //   71: astore 7
        //   73: aload_3
        //   74: ldc 69
        //   76: invokevirtual 73	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
        //   79: astore 19
        //   81: aload 19
        //   83: astore 7
        //   85: aload 7
        //   87: ifnull +279 -> 366
        //   90: aload 7
        //   92: invokevirtual 77	org/json/JSONObject:length	()I
        //   95: ifeq +271 -> 366
        //   98: aload_0
        //   99: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   102: invokestatic 81	com/fab/ShareSettingsActivity:access$23	(Lcom/fab/ShareSettingsActivity;)Landroid/widget/RelativeLayout;
        //   105: iconst_0
        //   106: invokevirtual 87	android/widget/RelativeLayout:setVisibility	(I)V
        //   109: aload_0
        //   110: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   113: invokestatic 91	com/fab/ShareSettingsActivity:access$24	(Lcom/fab/ShareSettingsActivity;)Lcom/fab/views/FabTextView;
        //   116: aload 7
        //   118: ldc 93
        //   120: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   123: invokevirtual 99	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
        //   126: aload_0
        //   127: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   130: invokestatic 103	com/fab/ShareSettingsActivity:access$4	(Lcom/fab/ShareSettingsActivity;)Landroid/widget/CheckBox;
        //   133: astore 17
        //   135: aload 7
        //   137: ldc 105
        //   139: invokevirtual 109	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   142: iload_2
        //   143: if_icmpne +217 -> 360
        //   146: iload_2
        //   147: istore 18
        //   149: aload 17
        //   151: iload 18
        //   153: invokevirtual 115	android/widget/CheckBox:setChecked	(Z)V
        //   156: aload_3
        //   157: ldc 117
        //   159: invokevirtual 73	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
        //   162: astore 14
        //   164: aload_0
        //   165: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   168: aload 14
        //   170: ldc 119
        //   172: invokevirtual 109	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   175: invokevirtual 122	com/fab/ShareSettingsActivity:updateCartCounter	(I)V
        //   178: aload 14
        //   180: ldc 124
        //   182: invokevirtual 109	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   185: i2l
        //   186: new 126	java/util/GregorianCalendar
        //   189: dup
        //   190: invokespecial 127	java/util/GregorianCalendar:<init>	()V
        //   193: invokestatic 131	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
        //   196: ldc2_w 132
        //   199: lcmp
        //   200: iflt +39 -> 239
        //   203: aload 14
        //   205: ldc 124
        //   207: invokevirtual 109	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   210: i2l
        //   211: invokestatic 139	com/fab/utils/FabHelper:setCartTime	(J)V
        //   214: aload_0
        //   215: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   218: invokevirtual 143	com/fab/ShareSettingsActivity:getApplicationContext	()Landroid/content/Context;
        //   221: aload 14
        //   223: ldc 124
        //   225: invokevirtual 109	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   228: i2l
        //   229: aload_0
        //   230: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   233: invokestatic 147	com/fab/ShareSettingsActivity:access$25	(Lcom/fab/ShareSettingsActivity;)Landroid/os/Handler;
        //   236: invokestatic 151	com/fab/utils/FabHelper:stopAndRestartCartTimer	(Landroid/content/Context;JLandroid/os/Handler;)V
        //   239: aload_0
        //   240: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   243: invokestatic 154	com/fab/ShareSettingsActivity:access$15	(Lcom/fab/ShareSettingsActivity;)Landroid/widget/RelativeLayout;
        //   246: iconst_0
        //   247: invokevirtual 87	android/widget/RelativeLayout:setVisibility	(I)V
        //   250: aload_0
        //   251: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   254: astore 13
        //   256: aload_3
        //   257: ldc 156
        //   259: invokevirtual 109	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   262: iload_2
        //   263: if_icmpne +118 -> 381
        //   266: aload 13
        //   268: iload_2
        //   269: putfield 160	com/fab/ShareSettingsActivity:isTimelineActive	Z
        //   272: aload_0
        //   273: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   276: getfield 160	com/fab/ShareSettingsActivity:isTimelineActive	Z
        //   279: ifeq +107 -> 386
        //   282: aload_0
        //   283: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   286: aload_3
        //   287: ldc 162
        //   289: invokevirtual 166	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
        //   292: invokestatic 170	com/fab/ShareSettingsActivity:access$21	(Lcom/fab/ShareSettingsActivity;Lorg/json/JSONArray;)V
        //   295: aload_0
        //   296: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   299: aload_3
        //   300: ldc 172
        //   302: invokevirtual 109	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   305: putfield 176	com/fab/ShareSettingsActivity:fbPublishAction	I
        //   308: aload_0
        //   309: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   312: invokestatic 179	com/fab/ShareSettingsActivity:access$22	(Lcom/fab/ShareSettingsActivity;)V
        //   315: aload_0
        //   316: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   319: invokestatic 183	com/fab/ShareSettingsActivity:access$14	(Lcom/fab/ShareSettingsActivity;)Landroid/widget/ProgressBar;
        //   322: bipush 8
        //   324: invokevirtual 186	android/widget/ProgressBar:setVisibility	(I)V
        //   327: aload_0
        //   328: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   331: invokestatic 190	com/fab/ShareSettingsActivity:access$29	(Lcom/fab/ShareSettingsActivity;)Lcom/fab/views/ErrorMessage;
        //   334: bipush 8
        //   336: invokevirtual 193	com/fab/views/ErrorMessage:setVisibility	(I)V
        //   339: return
        //   340: astore 6
        //   342: aload 6
        //   344: invokevirtual 196	org/json/JSONException:printStackTrace	()V
        //   347: goto -277 -> 70
        //   350: astore 8
        //   352: aload 8
        //   354: invokevirtual 196	org/json/JSONException:printStackTrace	()V
        //   357: goto -272 -> 85
        //   360: iconst_0
        //   361: istore 18
        //   363: goto -214 -> 149
        //   366: aload_0
        //   367: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   370: invokestatic 81	com/fab/ShareSettingsActivity:access$23	(Lcom/fab/ShareSettingsActivity;)Landroid/widget/RelativeLayout;
        //   373: bipush 8
        //   375: invokevirtual 87	android/widget/RelativeLayout:setVisibility	(I)V
        //   378: goto -222 -> 156
        //   381: iconst_0
        //   382: istore_2
        //   383: goto -117 -> 266
        //   386: aload_0
        //   387: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   390: invokestatic 200	com/fab/ShareSettingsActivity:access$26	(Lcom/fab/ShareSettingsActivity;)Landroid/widget/Button;
        //   393: new 8	com/fab/ShareSettingsActivity$7$1
        //   396: dup
        //   397: aload_0
        //   398: invokespecial 203	com/fab/ShareSettingsActivity$7$1:<init>	(Lcom/fab/ShareSettingsActivity$7;)V
        //   401: invokevirtual 209	android/widget/Button:setOnClickListener	(Landroid/view/View$OnClickListener;)V
        //   404: aload_0
        //   405: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   408: invokestatic 212	com/fab/ShareSettingsActivity:access$27	(Lcom/fab/ShareSettingsActivity;)Landroid/widget/RelativeLayout;
        //   411: bipush 8
        //   413: invokevirtual 87	android/widget/RelativeLayout:setVisibility	(I)V
        //   416: aload_0
        //   417: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   420: invokestatic 215	com/fab/ShareSettingsActivity:access$28	(Lcom/fab/ShareSettingsActivity;)Landroid/widget/RelativeLayout;
        //   423: iconst_0
        //   424: invokevirtual 87	android/widget/RelativeLayout:setVisibility	(I)V
        //   427: goto -112 -> 315
        //   430: aload_0
        //   431: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   434: invokestatic 183	com/fab/ShareSettingsActivity:access$14	(Lcom/fab/ShareSettingsActivity;)Landroid/widget/ProgressBar;
        //   437: bipush 8
        //   439: invokevirtual 186	android/widget/ProgressBar:setVisibility	(I)V
        //   442: aload_0
        //   443: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   446: invokestatic 219	com/fab/ShareSettingsActivity:access$20	(Lcom/fab/ShareSettingsActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
        //   449: invokevirtual 224	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
        //   452: invokestatic 230	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   455: ifne -116 -> 339
        //   458: aload_0
        //   459: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   462: invokestatic 190	com/fab/ShareSettingsActivity:access$29	(Lcom/fab/ShareSettingsActivity;)Lcom/fab/views/ErrorMessage;
        //   465: iconst_0
        //   466: invokevirtual 193	com/fab/views/ErrorMessage:setVisibility	(I)V
        //   469: aload_0
        //   470: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   473: invokestatic 190	com/fab/ShareSettingsActivity:access$29	(Lcom/fab/ShareSettingsActivity;)Lcom/fab/views/ErrorMessage;
        //   476: aload_0
        //   477: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   480: invokestatic 219	com/fab/ShareSettingsActivity:access$20	(Lcom/fab/ShareSettingsActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
        //   483: invokevirtual 224	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
        //   486: invokevirtual 234	com/fab/views/ErrorMessage:setErrorMsg	(Ljava/lang/String;)V
        //   489: aload_0
        //   490: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   493: invokestatic 154	com/fab/ShareSettingsActivity:access$15	(Lcom/fab/ShareSettingsActivity;)Landroid/widget/RelativeLayout;
        //   496: bipush 8
        //   498: invokevirtual 87	android/widget/RelativeLayout:setVisibility	(I)V
        //   501: aload_0
        //   502: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   505: invokestatic 212	com/fab/ShareSettingsActivity:access$27	(Lcom/fab/ShareSettingsActivity;)Landroid/widget/RelativeLayout;
        //   508: bipush 8
        //   510: invokevirtual 87	android/widget/RelativeLayout:setVisibility	(I)V
        //   513: aload_0
        //   514: getfield 20	com/fab/ShareSettingsActivity$7:this$0	Lcom/fab/ShareSettingsActivity;
        //   517: invokestatic 215	com/fab/ShareSettingsActivity:access$28	(Lcom/fab/ShareSettingsActivity;)Landroid/widget/RelativeLayout;
        //   520: bipush 8
        //   522: invokevirtual 87	android/widget/RelativeLayout:setVisibility	(I)V
        //   525: goto -186 -> 339
        //   528: astore 12
        //   530: goto -222 -> 308
        //   533: astore 11
        //   535: goto -240 -> 295
        //   538: astore 10
        //   540: goto -268 -> 272
        //   543: astore 9
        //   545: goto -306 -> 239
        //   548: astore 16
        //   550: goto -394 -> 156
        //   553: astore 15
        //   555: goto -429 -> 126
        //   558: astore 5
        //   560: goto -520 -> 40
        //
        // Exception table:
        //   from	to	target	type
        //   63	70	340	org/json/JSONException
        //   73	81	350	org/json/JSONException
        //   295	308	528	org/json/JSONException
        //   282	295	533	org/json/JSONException
        //   250	272	538	org/json/JSONException
        //   156	239	543	org/json/JSONException
        //   126	156	548	org/json/JSONException
        //   109	126	553	org/json/JSONException
        //   28	36	558	org/json/JSONException
      }
    });
    ExecuteRequestAsyncTask localExecuteRequestAsyncTask = this.task;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/get-share-settings-info/", "");
    localExecuteRequestAsyncTask.execute(arrayOfString);
  }

  private void populateFBTwitterShareSettings()
  {
    this.fbClient = new FabFBClient(this.context);
    if (this.fbClient.isLoggedIn())
    {
      this.fbCheckBox.setChecked(true);
      this.fbCheckBox.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (((CheckBox)paramAnonymousView).isChecked())
            ShareSettingsActivity.this.fbClient = FabHelper.linkToFacebook(ShareSettingsActivity.this.context, ShareSettingsActivity.this.mHandler);
          while (true)
          {
            return;
            if (ShareSettingsActivity.isActivityAlive)
            {
              ShareSettingsActivity.this.dialog.setMessage("Logging out...");
              ShareSettingsActivity.this.dialog.setCancelable(true);
              ShareSettingsActivity.this.dialog.show();
            }
            ShareSettingsActivity.this.fbClient.setmHandler(ShareSettingsActivity.this.mHandler);
            ShareSettingsActivity.this.fbClient.removeFBCredentials();
          }
        }
      });
      if (getAccessToken() != null)
        break label90;
      this.twCheckBox.setChecked(false);
    }
    while (true)
    {
      this.twCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
      {
        public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
        {
          if (paramAnonymousBoolean)
          {
            ShareSettingsActivity.this.finish();
            Intent localIntent = new Intent(ShareSettingsActivity.this.context, FabTwitterClient.class);
            localIntent.putExtra("TWEET_TEXT", "FAB_TW_LOGIN");
            ShareSettingsActivity.this.startActivity(localIntent);
          }
          while (true)
          {
            return;
            if (ShareSettingsActivity.isActivityAlive)
            {
              ShareSettingsActivity.this.dialog.setMessage("Logging out...");
              ShareSettingsActivity.this.dialog.setCancelable(true);
              ShareSettingsActivity.this.dialog.show();
            }
            new Thread(new Runnable()
            {
              public void run()
              {
                FabSharedPrefs.unlinkTwitter();
                if (TextUtils.isEmpty("success"))
                  ShareSettingsActivity.this.mHandler.sendEmptyMessage(ShareSettingsActivity.this.TW_FAILURE);
                while (true)
                {
                  return;
                  ShareSettingsActivity.this.mHandler.sendEmptyMessage(ShareSettingsActivity.this.TW_SUCCESS);
                }
              }
            }).start();
          }
        }
      });
      return;
      this.fbCheckBox.setChecked(false);
      break;
      label90: this.twCheckBox.setChecked(true);
    }
  }

  private void populateTimelinePreferences()
  {
    boolean bool1;
    label39: int i;
    int j;
    if (this.fbPublishAction == 1)
    {
      this.timelineCheckBox.setChecked(true);
      this.fbTimelineSubHeader.setVisibility(0);
      CheckBox localCheckBox = this.timelineCheckBox;
      if (this.fbPublishAction != 1)
        break label142;
      bool1 = true;
      localCheckBox.setChecked(bool1);
      this.timelineCheckBox.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if ((ShareSettingsActivity.isActivityAlive) && (ShareSettingsActivity.this.dialog != null))
          {
            ShareSettingsActivity.this.dialog.show();
            ShareSettingsActivity.this.dialog.setMessage("Updating...");
            ShareSettingsActivity.this.dialog.setCancelable(true);
          }
          final CheckBox localCheckBox = (CheckBox)paramAnonymousView;
          if (localCheckBox.isChecked());
          for (final int i = 1; ; i = 0)
          {
            ShareSettingsActivity.this.task = new ExecuteRequestAsyncTask(ShareSettingsActivity.this.context, "GET");
            ShareSettingsActivity.this.task.setTaskCompleteListener(new TaskCompleteListener()
            {
              public void onTaskComplete()
              {
              }

              public void onTaskComplete(Object paramAnonymous2Object)
              {
                int i = 1;
                JSONObject localJSONObject = (JSONObject)paramAnonymous2Object;
                Object localObject;
                if (localJSONObject != null)
                  localObject = null;
                while (true)
                {
                  try
                  {
                    String str = localJSONObject.getString("err_msg");
                    localObject = str;
                    if (!TextUtils.isEmpty(localObject))
                    {
                      CheckBox localCheckBox = localCheckBox;
                      if (i == i)
                        i = 0;
                      localCheckBox.setChecked(i);
                      if ((ShareSettingsActivity.isActivityAlive) && (ShareSettingsActivity.this.dialog != null) && (ShareSettingsActivity.this.dialog.isShowing()))
                        ShareSettingsActivity.this.dialog.cancel();
                      return;
                    }
                  }
                  catch (JSONException localJSONException1)
                  {
                    localJSONException1.printStackTrace();
                    continue;
                    if (ShareSettingsActivity.this.theFBPreferences != null)
                    {
                      ShareSettingsActivity.this.theFBPreferences.clear();
                      ShareSettingsActivity.this.adapter.notifyDataSetChanged();
                    }
                  }
                  try
                  {
                    ShareSettingsActivity.this.fbTimelinePreferences = localJSONObject.getJSONArray("list_of_options");
                    try
                    {
                      label170: ShareSettingsActivity.this.fbPublishAction = localJSONObject.getInt("fb_publish_action");
                      label186: ShareSettingsActivity.this.populateTimelinePreferences();
                    }
                    catch (JSONException localJSONException3)
                    {
                      break label186;
                    }
                  }
                  catch (JSONException localJSONException2)
                  {
                    break label170;
                  }
                }
              }
            });
            ExecuteRequestAsyncTask localExecuteRequestAsyncTask = ShareSettingsActivity.this.task;
            String[] arrayOfString = new String[1];
            arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/update-user-fb-publish-action-setting/", "?fb_publish_action=" + i);
            localExecuteRequestAsyncTask.execute(arrayOfString);
            return;
          }
        }
      });
      if ((this.theFBPreferences != null) && (this.fbTimelinePreferences != null))
      {
        this.theFBPreferences.clear();
        i = 0;
        j = this.fbTimelinePreferences.length();
      }
    }
    while (true)
    {
      if (i >= j)
      {
        this.fbTimelineActiveHolder.setVisibility(0);
        this.fbTimelineInactiveHolder.setVisibility(8);
        this.adapter.notifyDataSetChanged();
        return;
        this.timelineCheckBox.setChecked(false);
        this.fbTimelineSubHeader.setVisibility(8);
        break;
        label142: bool1 = false;
        break label39;
      }
      Object localObject = new JSONObject();
      FBPreferencesBo localFBPreferencesBo = new FBPreferencesBo();
      try
      {
        JSONObject localJSONObject = this.fbTimelinePreferences.getJSONObject(i);
        localObject = localJSONObject;
        try
        {
          label179: localFBPreferencesBo.setHeading(((JSONObject)localObject).getString("heading"));
          try
          {
            label192: boolean bool3;
            if (((JSONObject)localObject).getInt("is_editable") == 1)
            {
              bool3 = true;
              label207: localFBPreferencesBo.setCBEnabled(bool3);
            }
            try
            {
              label214: boolean bool2;
              if (((JSONObject)localObject).getInt("enable") == 1)
              {
                bool2 = true;
                localFBPreferencesBo.setChecked(bool2);
              }
              try
              {
                label236: localFBPreferencesBo.setUrl(((JSONObject)localObject).getString("url"));
                label249: this.theFBPreferences.add(localFBPreferencesBo);
                i++;
                continue;
                bool3 = false;
                break label207;
                bool2 = false;
              }
              catch (JSONException localJSONException5)
              {
                break label249;
              }
            }
            catch (JSONException localJSONException4)
            {
              break label236;
            }
          }
          catch (JSONException localJSONException3)
          {
            break label214;
          }
        }
        catch (JSONException localJSONException2)
        {
          break label192;
        }
      }
      catch (JSONException localJSONException1)
      {
        break label179;
      }
    }
  }

  protected void hideActivityContent()
  {
    if ((this.theFBPreferences != null) && (this.adapter != null))
    {
      this.theFBPreferences.clear();
      this.adapter.notifyDataSetChanged();
    }
    this.settingsHolder.setVisibility(8);
    this.progressBar.setVisibility(0);
    this.error.setVisibility(8);
    this.settingsHolder.setVisibility(8);
    this.fbTimelineActiveHolder.setVisibility(8);
    this.fbTimelineInactiveHolder.setVisibility(8);
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
    this.context = this;
    setContentView(2130903088);
    isActivityAlive = true;
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("shareSettings"));
    this.dialog = new ProgressDialog(this.context);
    this.titleWidget = ((TitleWidget)findViewById(2131361810));
    this.timer = ((FabTextView)findViewById(2131361795));
    this.timer.setFont("georgiai.ttf");
    this.alertbox = new AlertDialog.Builder(this.context);
    this.timerHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Bundle localBundle = paramAnonymousMessage.getData();
        if (FabUtils.isLogEnabled())
          FabUtils.log(ShareSettingsActivity.this.TAG, localBundle.toString());
        int i = localBundle.getInt("Type");
        String str = localBundle.getString("Value");
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
            ShareSettingsActivity.this.timer.setVisibility(4);
          if (ShareSettingsActivity.isActivityAlive)
          {
            AlertDialog.Builder localBuilder = ShareSettingsActivity.this.alertbox;
            if (localBundle.getInt("Error") == 1);
            for (int j = 2130837504; ; j = 2130837507)
            {
              localBuilder.setIcon(j);
              ShareSettingsActivity.this.alertbox.setTitle(localBundle.getString("Title"));
              ShareSettingsActivity.this.alertbox.setMessage(localBundle.getString("Message"));
              ShareSettingsActivity.this.alertbox.setNeutralButton("OK", null);
              ShareSettingsActivity.this.alertbox.show();
              break;
            }
            ShareSettingsActivity.this.timer.setVisibility(0);
            ShareSettingsActivity.this.timer.setText(str);
          }
        }
      }
    };
    this.mHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        paramAnonymousMessage.getData().getString("error");
        if (paramAnonymousMessage.what == ShareSettingsActivity.this.FB_LOGOUT_SUCCESS)
        {
          if ((ShareSettingsActivity.isActivityAlive) && (ShareSettingsActivity.this.dialog != null) && (ShareSettingsActivity.this.dialog.isShowing()))
            ShareSettingsActivity.this.dialog.cancel();
          ShareSettingsActivity.this.fbCheckBox.setChecked(false);
          if (ShareSettingsActivity.this.theFBPreferences != null)
          {
            ShareSettingsActivity.this.theFBPreferences.clear();
            ShareSettingsActivity.this.adapter.notifyDataSetChanged();
          }
          ShareSettingsActivity.this.onFBConnectComplete(0);
        }
        while (true)
        {
          return;
          if (paramAnonymousMessage.what == ShareSettingsActivity.this.FB_LOGOUT_FAILURE)
          {
            if ((ShareSettingsActivity.isActivityAlive) && (ShareSettingsActivity.this.dialog != null) && (ShareSettingsActivity.this.dialog.isShowing()))
              ShareSettingsActivity.this.dialog.cancel();
            ShareSettingsActivity.this.fbCheckBox.setChecked(true);
          }
          else if (paramAnonymousMessage.what == ShareSettingsActivity.this.TW_SUCCESS)
          {
            if ((ShareSettingsActivity.isActivityAlive) && (ShareSettingsActivity.this.dialog != null) && (ShareSettingsActivity.this.dialog.isShowing()))
              ShareSettingsActivity.this.dialog.cancel();
            ShareSettingsActivity.this.twCheckBox.setChecked(false);
          }
          else if (paramAnonymousMessage.what == ShareSettingsActivity.this.TW_FAILURE)
          {
            if ((ShareSettingsActivity.isActivityAlive) && (ShareSettingsActivity.this.dialog != null) && (ShareSettingsActivity.this.dialog.isShowing()))
              ShareSettingsActivity.this.dialog.cancel();
            ShareSettingsActivity.this.twCheckBox.setChecked(true);
          }
          else if (paramAnonymousMessage.what == ShareSettingsActivity.this.TIMELINE_ENABLED)
          {
            ShareSettingsActivity.this.onFBConnectComplete(1);
          }
          else if (paramAnonymousMessage.what == ShareSettingsActivity.this.REFRESH_ACTIVITY)
          {
            ShareSettingsActivity.this.refreshActivity();
          }
          else if (paramAnonymousMessage.what == ShareSettingsActivity.this.HIDE_CONTENT)
          {
            ShareSettingsActivity.this.hideActivityContent();
          }
          else if (paramAnonymousMessage.what == 10)
          {
            ShareSettingsActivity.this.onFBConnectComplete(1);
          }
          else if (paramAnonymousMessage.what == 11)
          {
            ShareSettingsActivity.this.refreshActivity();
          }
        }
      }
    };
    this.listView = ((ListView)findViewById(2131362034));
    this.theFBPreferences = new ArrayList();
    this.headerView = ((LayoutInflater)getSystemService("layout_inflater")).inflate(2130903089, null);
    this.listView.addHeaderView(this.headerView);
    this.adapter = new ShareSettingsAdapter(this, this.theFBPreferences);
    this.listView.setAdapter(this.adapter);
    ((FabTextView)findViewById(2131362033)).setFont("HelveticaNeu_bold.ttf");
    this.fbSessionStatus = ((FabTextView)findViewById(2131361931));
    this.fbSessionStatus.setFont("HelveticaNeu_normal.ttf");
    this.fbRow = ((RelativeLayout)findViewById(2131362036));
    this.fbCheckBox = ((CheckBox)findViewById(2131362037));
    this.twCheckBox = ((CheckBox)findViewById(2131362040));
    this.progressBar = ((ProgressBar)findViewById(2131361874));
    this.settingsHolder = ((RelativeLayout)findViewById(2131362035));
    this.fbTimelineActiveHolder = ((RelativeLayout)findViewById(2131362042));
    this.fbTimelineSubHeader = ((FabTextView)findViewById(2131362047));
    this.fbTimelineSubHeader.setFont("georgiai.ttf");
    this.fbTimelineInactiveHolder = ((RelativeLayout)findViewById(2131362048));
    this.error = ((ErrorMessage)findViewById(2131361811));
    this.timelineText = ((FabTextView)findViewById(2131361868));
    this.timelineText.setFont("HelveticaNeu_bold.ttf");
    this.timelineConditionText = ((FabTextView)findViewById(2131361869));
    this.timelineConditionText.setFont("georgiai.ttf");
    this.tryItNowButton = ((Button)findViewById(2131361870));
    this.timelineCheckBox = ((CheckBox)findViewById(2131362045));
    populateFBTwitterShareSettings();
    loadShareSettings();
    this.headerCartButton = this.titleWidget.getCartButton();
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
        Intent localIntent = new Intent(ShareSettingsActivity.this.context, FabWebViewActivity.class);
        ShareSettingsActivity.this.startActivity(localIntent);
        return false;
      }
    });
    MenuItem localMenuItem2 = paramMenu.findItem(2131361908);
    localMenuItem2.setVisible(true);
    localMenuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(ShareSettingsActivity.this.context, InviteActivity.class);
        ShareSettingsActivity.this.startActivity(localIntent);
        return false;
      }
    });
    return super.onCreateOptionsMenu(paramMenu);
  }

  protected void onDestroy()
  {
    isActivityAlive = false;
    this.tracker.stopSession();
    super.onDestroy();
  }

  protected void onFBConnectComplete(int paramInt)
  {
    if ((isActivityAlive) && (this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.cancel();
    this.progressBar.setVisibility(0);
    this.error.setVisibility(8);
    this.settingsHolder.setVisibility(8);
    this.fbTimelineActiveHolder.setVisibility(8);
    this.fbTimelineInactiveHolder.setVisibility(8);
    SharedPreferences localSharedPreferences = this.context.getSharedPreferences("facebook-credentials", 0);
    ExecuteRequestAsyncTask localExecuteRequestAsyncTask = new ExecuteRequestAsyncTask(this.context, "POST");
    this.parameters = new HashMap();
    this.parameters.put("d", FabSharedPrefs.getDeviceDensity());
    this.parameters.put("app_version", FabApplication.APP_VERSION);
    this.parameters.put("device_model", Build.MODEL);
    this.parameters.put("device_os", Build.VERSION.RELEASE);
    this.parameters.put("enable", paramInt);
    if (paramInt == 1)
    {
      this.parameters.put("fb_id", localSharedPreferences.getLong("fb_id", 0L));
      this.parameters.put("fb_access_token", localSharedPreferences.getString("access_token", null));
      this.parameters.put("expires", localSharedPreferences.getLong("expires_in", 0L) / 1000L);
      this.parameters.put("fb_name", localSharedPreferences.getString("fb_name", ""));
      this.parameters.put("fb_email", localSharedPreferences.getString("fb_email", ""));
    }
    localExecuteRequestAsyncTask.setParameters(this.parameters);
    localExecuteRequestAsyncTask.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      public void onTaskComplete(Object paramAnonymousObject)
      {
        JSONObject localJSONObject = (JSONObject)paramAnonymousObject;
        ShareSettingsActivity.this.progressBar.setVisibility(8);
        ShareSettingsActivity.this.settingsHolder.setVisibility(0);
        Object localObject;
        if (localJSONObject != null)
          localObject = null;
        try
        {
          String str = localJSONObject.getString("err_msg");
          localObject = str;
          if ((!TextUtils.isEmpty(localObject)) && (ShareSettingsActivity.isActivityAlive))
          {
            ShareSettingsActivity.this.alertbox.setIcon(2130837504);
            ShareSettingsActivity.this.alertbox.setTitle("Fab");
            ShareSettingsActivity.this.alertbox.setMessage(localObject);
            ShareSettingsActivity.this.alertbox.setNeutralButton("OK", null);
            ShareSettingsActivity.this.alertbox.show();
          }
          ShareSettingsActivity.this.refreshActivity();
          if (ShareSettingsActivity.this.parameters != null)
            ShareSettingsActivity.this.parameters.clear();
          return;
        }
        catch (JSONException localJSONException)
        {
          while (true)
            localJSONException.printStackTrace();
        }
      }
    });
    String[] arrayOfString = new String[1];
    arrayOfString[0] = FabUtils.requestPostUrl(true, "/mobile/toggle-fb-connect/");
    localExecuteRequestAsyncTask.execute(arrayOfString);
  }

  protected void onPause()
  {
    isActivityAlive = false;
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.cancel();
    super.onPause();
  }

  protected void onRestart()
  {
    isActivityAlive = true;
    if (this.fbClient.isLoggedIn())
      this.fbCheckBox.setChecked(true);
    while (true)
    {
      FabHelper.stopAndRestartCartTimer(getApplicationContext(), FabHelper.getCartTime(), this.timerHandler);
      updateCartCounter(FabHelper.getCartCount());
      super.onRestart();
      return;
      this.fbCheckBox.setChecked(false);
    }
  }

  protected void onResume()
  {
    isActivityAlive = true;
    if (this.fbClient.isLoggedIn())
      this.fbCheckBox.setChecked(true);
    while (true)
    {
      this.error.setVisibility(8);
      FabHelper.stopAndRestartCartTimer(getApplicationContext(), FabHelper.getCartTime(), this.timerHandler);
      updateCartCounter(FabHelper.getCartCount());
      super.onResume();
      return;
      this.fbCheckBox.setChecked(false);
    }
  }

  protected void refreshActivity()
  {
    this.timer.setVisibility(4);
    FabHelper.stopAndRestartCartTimer(getApplicationContext(), 0L, this.mHandler);
    hideActivityContent();
    populateFBTwitterShareSettings();
    loadShareSettings();
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
 * Qualified Name:     com.fab.ShareSettingsActivity
 * JD-Core Version:    0.6.2
 */