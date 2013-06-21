package com.fab;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import com.fab.facebook.FabFBClient;
import com.fab.facebook.FabFBShareBO;
import com.fab.twitter.FabTwitterClient;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabHelper;
import com.fab.utils.FabUtils;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.ErrorMessage;
import com.fab.views.FabButton;
import com.fab.views.FabTextView;
import com.fab.views.TitleWidget;
import com.fab.webviews.FabWebViewActivity;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class InviteActivity extends Activity
  implements View.OnClickListener
{
  private static final int EMAIL = 3;
  private static final int FACEBOOK = 1;
  private static final int SMS = 4;
  private static final int TWITTER = 2;
  protected String TAG = "InviteActivity";
  protected AlertDialog.Builder alertbox;
  private Animation animation;
  private Animation animation1;
  private Context context;
  private RelativeLayout creditsRel;
  private FabButton email;
  public String emailBody;
  public String emailSubject;
  private ErrorMessage error;
  private FabButton facebook;
  private FabFBClient fbClient;
  public FabFBShareBO fbShareData;
  protected boolean hasFBShareToken;
  private FabButton headerCartButton;
  private FabTextView hideInfo;
  private LayoutInflater inflator;
  private ImageView infoBtn;
  private ImageView infoBtnBottom;
  private String inviteCode;
  private FabTextView inviteLable;
  private LinearLayout inviteLayout;
  public String inviteSMS;
  protected boolean isActivityAlive;
  protected boolean isInfoClicked;
  private Handler mHandler;
  private RelativeLayout membershipRel;
  private LinearLayout membershipsInfo;
  private RelativeLayout pbar;
  private ImageView progress;
  private View runtimeRow;
  private ScrollView scrollView;
  private FabButton sms;
  private FabTextView timer;
  private TitleWidget titleWidget;
  private GoogleAnalyticsTracker tracker;
  private FabTextView tvCredits1;
  private FabTextView tvCredits2;
  private FabTextView tvCredits3;
  public FabTextView tvDesc;
  private FabTextView tvFounding;
  public FabTextView tvHeader;
  private FabTextView tvInviteBlack;
  private FabTextView tvMember;
  private FabTextView tvPrime;
  private FabTextView tvVip;
  private String twShareText;
  private FabButton twitter;

  private void initilizeUI()
  {
    this.error = ((ErrorMessage)findViewById(2131361811));
    this.alertbox = new AlertDialog.Builder(this.context);
    this.inflator = ((LayoutInflater)this.context.getSystemService("layout_inflater"));
    this.titleWidget = ((TitleWidget)findViewById(2131361810));
    this.timer = ((FabTextView)findViewById(2131361795));
    this.timer.setFont("georgiai.ttf");
    this.scrollView = ((ScrollView)findViewById(2131361909));
    this.pbar = ((RelativeLayout)findViewById(2131361935));
    this.creditsRel = ((RelativeLayout)findViewById(2131361911));
    this.creditsRel.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent(InviteActivity.this.context, MoreWebViewActivity.class);
        localIntent.putExtra("URL", FabUtils.requestUrl(false, "/mobile/my-credits", ""));
        InviteActivity.this.context.startActivity(localIntent);
      }
    });
    this.tvCredits1 = ((FabTextView)findViewById(2131361913));
    this.tvCredits1.setFont("georgiai.ttf");
    this.tvCredits2 = ((FabTextView)findViewById(2131361914));
    this.tvCredits2.setFont("HelveticaNeu_bold.ttf");
    this.tvCredits3 = ((FabTextView)findViewById(2131361915));
    this.tvCredits3.setFont("georgiai.ttf");
    this.tvInviteBlack = ((FabTextView)findViewById(2131361917));
    this.tvInviteBlack.setFont("HelveticaNeu_normal.ttf");
    this.tvInviteBlack.setText(Html.fromHtml("Invite Your Friends and <font color=\"#DD0017\"> Earn Cash! </font>"));
    this.tvMember = ((FabTextView)findViewById(2131361918));
    this.tvMember.setFont("georgiai.ttf");
    this.progress = ((ImageView)findViewById(2131361919));
    this.membershipRel = ((RelativeLayout)findViewById(2131361924));
    this.membershipsInfo = ((LinearLayout)findViewById(2131361925));
    this.infoBtnBottom = ((ImageView)findViewById(2131361927));
    this.infoBtnBottom.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        InviteActivity.this.isInfoClicked = false;
        InviteActivity.this.infoBtn.setImageDrawable(InviteActivity.this.getResources().getDrawable(2130837634));
        InviteActivity.this.membershipRel.setVisibility(8);
      }
    });
    this.infoBtn = ((ImageView)findViewById(2131361920));
    this.infoBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (!InviteActivity.this.isInfoClicked)
        {
          InviteActivity.this.infoBtn.setImageDrawable(InviteActivity.this.getResources().getDrawable(2130837635));
          InviteActivity.this.membershipRel.setVisibility(0);
          InviteActivity.this.isInfoClicked = true;
        }
      }
    });
    this.hideInfo = ((FabTextView)findViewById(2131361928));
    this.hideInfo.setFont("georgiai.ttf");
    this.tvFounding = ((FabTextView)findViewById(2131361921));
    this.tvFounding.setFont("HelveticaNeu_normal.ttf");
    this.tvVip = ((FabTextView)findViewById(2131361922));
    this.tvVip.setFont("HelveticaNeu_normal.ttf");
    this.tvPrime = ((FabTextView)findViewById(2131361923));
    this.tvPrime.setFont("HelveticaNeu_normal.ttf");
    this.inviteLayout = ((LinearLayout)findViewById(2131361929));
    this.inviteLable = ((FabTextView)findViewById(2131361930));
    this.inviteLable.setFont("georgiai.ttf");
    this.facebook = ((FabButton)findViewById(2131361931));
    this.facebook.setFont("HelveticaNeu_bold.ttf");
    this.facebook.setId(1);
    this.facebook.setOnClickListener(this);
    this.twitter = ((FabButton)findViewById(2131361932));
    this.twitter.setFont("HelveticaNeu_bold.ttf");
    this.twitter.setId(2);
    this.twitter.setOnClickListener(this);
    this.email = ((FabButton)findViewById(2131361933));
    this.email.setFont("HelveticaNeu_bold.ttf");
    this.email.setId(3);
    this.email.setOnClickListener(this);
    this.sms = ((FabButton)findViewById(2131361934));
    this.sms.setFont("HelveticaNeu_bold.ttf");
    this.sms.setId(4);
    this.sms.setOnClickListener(this);
    this.mHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Bundle localBundle = paramAnonymousMessage.getData();
        FabUtils.log(InviteActivity.this.TAG, localBundle.toString());
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
            InviteActivity.this.timer.setVisibility(4);
          if (InviteActivity.this.isActivityAlive)
          {
            AlertDialog.Builder localBuilder = InviteActivity.this.alertbox;
            if (localBundle.getInt("Error") == 1);
            for (int j = 2130837504; ; j = 2130837507)
            {
              localBuilder.setIcon(j);
              InviteActivity.this.alertbox.setTitle(localBundle.getString("Title"));
              InviteActivity.this.alertbox.setMessage(localBundle.getString("Message"));
              InviteActivity.this.alertbox.setNeutralButton("OK", null);
              InviteActivity.this.alertbox.show();
              break;
            }
            InviteActivity.this.timer.setVisibility(0);
            InviteActivity.this.timer.setText(str);
          }
        }
      }
    };
  }

  private void loadInviteData()
  {
    final ExecuteRequestAsyncTask localExecuteRequestAsyncTask = new ExecuteRequestAsyncTask(this.context, "GET");
    localExecuteRequestAsyncTask.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      // ERROR //
      public void onTaskComplete(Object paramAnonymousObject)
      {
        // Byte code:
        //   0: aload_1
        //   1: ifnull +882 -> 883
        //   4: aload_1
        //   5: instanceof 30
        //   8: ifeq +875 -> 883
        //   11: aload_1
        //   12: checkcast 30	org/json/JSONObject
        //   15: astore_2
        //   16: aload_2
        //   17: ldc 32
        //   19: invokevirtual 36	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
        //   22: pop
        //   23: aload_2
        //   24: ldc 38
        //   26: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   29: invokestatic 48	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   32: istore 5
        //   34: iload 5
        //   36: ifeq +811 -> 847
        //   39: aload_2
        //   40: ldc 50
        //   42: invokevirtual 54	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
        //   45: astore 31
        //   47: aload_0
        //   48: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   51: aload 31
        //   53: ldc 56
        //   55: invokevirtual 60	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   58: invokevirtual 64	com/fab/InviteActivity:updateCartCounter	(I)V
        //   61: aload 31
        //   63: ldc 66
        //   65: invokevirtual 60	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   68: i2l
        //   69: new 68	java/util/GregorianCalendar
        //   72: dup
        //   73: invokespecial 69	java/util/GregorianCalendar:<init>	()V
        //   76: invokestatic 75	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
        //   79: ldc2_w 76
        //   82: lcmp
        //   83: iflt +39 -> 122
        //   86: aload 31
        //   88: ldc 66
        //   90: invokevirtual 60	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   93: i2l
        //   94: invokestatic 83	com/fab/utils/FabHelper:setCartTime	(J)V
        //   97: aload_0
        //   98: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   101: invokevirtual 87	com/fab/InviteActivity:getApplicationContext	()Landroid/content/Context;
        //   104: aload 31
        //   106: ldc 66
        //   108: invokevirtual 60	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   111: i2l
        //   112: aload_0
        //   113: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   116: invokestatic 91	com/fab/InviteActivity:access$1	(Lcom/fab/InviteActivity;)Landroid/os/Handler;
        //   119: invokestatic 95	com/fab/utils/FabHelper:stopAndRestartCartTimer	(Landroid/content/Context;JLandroid/os/Handler;)V
        //   122: aload_2
        //   123: ldc 97
        //   125: invokevirtual 54	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
        //   128: astore 20
        //   130: aload_0
        //   131: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   134: aload_2
        //   135: ldc 99
        //   137: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   140: invokestatic 103	com/fab/InviteActivity:access$2	(Lcom/fab/InviteActivity;Ljava/lang/String;)V
        //   143: aload 20
        //   145: ifnull +177 -> 322
        //   148: aload_0
        //   149: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   152: new 105	com/fab/facebook/FabFBShareBO
        //   155: dup
        //   156: invokespecial 106	com/fab/facebook/FabFBShareBO:<init>	()V
        //   159: putfield 110	com/fab/InviteActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
        //   162: aload_0
        //   163: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   166: getfield 110	com/fab/InviteActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
        //   169: aload 20
        //   171: ldc 112
        //   173: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   176: invokevirtual 116	com/fab/facebook/FabFBShareBO:setLink	(Ljava/lang/String;)V
        //   179: aload_0
        //   180: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   183: getfield 110	com/fab/InviteActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
        //   186: aload 20
        //   188: ldc 118
        //   190: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   193: invokevirtual 121	com/fab/facebook/FabFBShareBO:setCaption	(Ljava/lang/String;)V
        //   196: aload_0
        //   197: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   200: getfield 110	com/fab/InviteActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
        //   203: aload 20
        //   205: ldc 123
        //   207: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   210: invokevirtual 126	com/fab/facebook/FabFBShareBO:setDescription	(Ljava/lang/String;)V
        //   213: aload_0
        //   214: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   217: getfield 110	com/fab/InviteActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
        //   220: aload 20
        //   222: ldc 128
        //   224: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   227: invokevirtual 131	com/fab/facebook/FabFBShareBO:setPicture	(Ljava/lang/String;)V
        //   230: aload_0
        //   231: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   234: getfield 110	com/fab/InviteActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
        //   237: aload 20
        //   239: ldc 133
        //   241: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   244: invokevirtual 136	com/fab/facebook/FabFBShareBO:setName	(Ljava/lang/String;)V
        //   247: aload_0
        //   248: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   251: getfield 110	com/fab/InviteActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
        //   254: aload 20
        //   256: ldc 133
        //   258: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   261: invokevirtual 136	com/fab/facebook/FabFBShareBO:setName	(Ljava/lang/String;)V
        //   264: aload_0
        //   265: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   268: getfield 110	com/fab/InviteActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
        //   271: aload 20
        //   273: ldc 138
        //   275: invokevirtual 54	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
        //   278: ldc 140
        //   280: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   283: invokevirtual 143	com/fab/facebook/FabFBShareBO:setHeader	(Ljava/lang/String;)V
        //   286: aload_0
        //   287: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   290: getfield 110	com/fab/InviteActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
        //   293: aload 20
        //   295: ldc 138
        //   297: invokevirtual 54	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
        //   300: ldc 145
        //   302: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   305: invokevirtual 148	com/fab/facebook/FabFBShareBO:setText	(Ljava/lang/String;)V
        //   308: aload_0
        //   309: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   312: aload 20
        //   314: ldc 150
        //   316: invokevirtual 36	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
        //   319: putfield 154	com/fab/InviteActivity:hasFBShareToken	Z
        //   322: aload_0
        //   323: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   326: invokestatic 158	com/fab/InviteActivity:access$3	(Lcom/fab/InviteActivity;)Lcom/fab/views/FabTextView;
        //   329: aload_2
        //   330: ldc 160
        //   332: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   335: invokevirtual 165	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
        //   338: aload_0
        //   339: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   342: aload_2
        //   343: ldc 167
        //   345: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   348: invokestatic 170	com/fab/InviteActivity:access$4	(Lcom/fab/InviteActivity;Ljava/lang/String;)V
        //   351: aload_2
        //   352: ldc 172
        //   354: invokevirtual 60	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   357: istore 19
        //   359: iload 19
        //   361: ifne +148 -> 509
        //   364: aload_0
        //   365: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   368: invokestatic 176	com/fab/InviteActivity:access$5	(Lcom/fab/InviteActivity;)Landroid/widget/ImageView;
        //   371: aload_0
        //   372: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   375: invokestatic 180	com/fab/InviteActivity:access$6	(Lcom/fab/InviteActivity;)Landroid/content/Context;
        //   378: invokevirtual 186	android/content/Context:getResources	()Landroid/content/res/Resources;
        //   381: ldc 187
        //   383: invokevirtual 193	android/content/res/Resources:getDrawable	(I)Landroid/graphics/drawable/Drawable;
        //   386: invokevirtual 199	android/widget/ImageView:setImageDrawable	(Landroid/graphics/drawable/Drawable;)V
        //   389: aload_0
        //   390: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   393: invokestatic 202	com/fab/InviteActivity:access$7	(Lcom/fab/InviteActivity;)Lcom/fab/views/FabTextView;
        //   396: bipush 8
        //   398: invokevirtual 205	com/fab/views/FabTextView:setVisibility	(I)V
        //   401: aload_0
        //   402: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   405: invokestatic 209	com/fab/InviteActivity:access$8	(Lcom/fab/InviteActivity;)Landroid/widget/LinearLayout;
        //   408: invokevirtual 214	android/widget/LinearLayout:removeAllViews	()V
        //   411: aload_2
        //   412: ldc 216
        //   414: invokevirtual 220	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
        //   417: astore 15
        //   419: iconst_0
        //   420: istore 16
        //   422: aload 15
        //   424: invokevirtual 226	org/json/JSONArray:length	()I
        //   427: istore 17
        //   429: iload 16
        //   431: iload 17
        //   433: if_icmplt +253 -> 686
        //   436: aload_0
        //   437: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   440: aload_2
        //   441: ldc 228
        //   443: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   446: putfield 232	com/fab/InviteActivity:inviteSMS	Ljava/lang/String;
        //   449: aload_2
        //   450: ldc 234
        //   452: invokevirtual 54	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
        //   455: astore 14
        //   457: aload_0
        //   458: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   461: aload 14
        //   463: ldc 236
        //   465: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   468: putfield 239	com/fab/InviteActivity:emailBody	Ljava/lang/String;
        //   471: aload_0
        //   472: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   475: aload 14
        //   477: ldc 241
        //   479: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   482: putfield 244	com/fab/InviteActivity:emailSubject	Ljava/lang/String;
        //   485: aload_0
        //   486: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   489: invokestatic 248	com/fab/InviteActivity:access$12	(Lcom/fab/InviteActivity;)Landroid/widget/RelativeLayout;
        //   492: bipush 8
        //   494: invokevirtual 251	android/widget/RelativeLayout:setVisibility	(I)V
        //   497: aload_0
        //   498: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   501: invokestatic 255	com/fab/InviteActivity:access$13	(Lcom/fab/InviteActivity;)Landroid/widget/ScrollView;
        //   504: iconst_0
        //   505: invokevirtual 258	android/widget/ScrollView:setVisibility	(I)V
        //   508: return
        //   509: iload 19
        //   511: iconst_1
        //   512: if_icmpne +56 -> 568
        //   515: aload_0
        //   516: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   519: invokestatic 176	com/fab/InviteActivity:access$5	(Lcom/fab/InviteActivity;)Landroid/widget/ImageView;
        //   522: aload_0
        //   523: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   526: invokestatic 180	com/fab/InviteActivity:access$6	(Lcom/fab/InviteActivity;)Landroid/content/Context;
        //   529: invokevirtual 186	android/content/Context:getResources	()Landroid/content/res/Resources;
        //   532: ldc_w 259
        //   535: invokevirtual 193	android/content/res/Resources:getDrawable	(I)Landroid/graphics/drawable/Drawable;
        //   538: invokevirtual 199	android/widget/ImageView:setImageDrawable	(Landroid/graphics/drawable/Drawable;)V
        //   541: aload_0
        //   542: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   545: invokestatic 202	com/fab/InviteActivity:access$7	(Lcom/fab/InviteActivity;)Lcom/fab/views/FabTextView;
        //   548: ldc_w 261
        //   551: invokevirtual 165	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
        //   554: aload_0
        //   555: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   558: invokestatic 202	com/fab/InviteActivity:access$7	(Lcom/fab/InviteActivity;)Lcom/fab/views/FabTextView;
        //   561: iconst_0
        //   562: invokevirtual 205	com/fab/views/FabTextView:setVisibility	(I)V
        //   565: goto -164 -> 401
        //   568: iload 19
        //   570: iconst_2
        //   571: if_icmpne +56 -> 627
        //   574: aload_0
        //   575: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   578: invokestatic 176	com/fab/InviteActivity:access$5	(Lcom/fab/InviteActivity;)Landroid/widget/ImageView;
        //   581: aload_0
        //   582: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   585: invokestatic 180	com/fab/InviteActivity:access$6	(Lcom/fab/InviteActivity;)Landroid/content/Context;
        //   588: invokevirtual 186	android/content/Context:getResources	()Landroid/content/res/Resources;
        //   591: ldc_w 262
        //   594: invokevirtual 193	android/content/res/Resources:getDrawable	(I)Landroid/graphics/drawable/Drawable;
        //   597: invokevirtual 199	android/widget/ImageView:setImageDrawable	(Landroid/graphics/drawable/Drawable;)V
        //   600: aload_0
        //   601: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   604: invokestatic 202	com/fab/InviteActivity:access$7	(Lcom/fab/InviteActivity;)Lcom/fab/views/FabTextView;
        //   607: ldc_w 264
        //   610: invokevirtual 165	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
        //   613: aload_0
        //   614: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   617: invokestatic 202	com/fab/InviteActivity:access$7	(Lcom/fab/InviteActivity;)Lcom/fab/views/FabTextView;
        //   620: iconst_0
        //   621: invokevirtual 205	com/fab/views/FabTextView:setVisibility	(I)V
        //   624: goto -223 -> 401
        //   627: iload 19
        //   629: iconst_3
        //   630: if_icmpne -229 -> 401
        //   633: aload_0
        //   634: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   637: invokestatic 176	com/fab/InviteActivity:access$5	(Lcom/fab/InviteActivity;)Landroid/widget/ImageView;
        //   640: aload_0
        //   641: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   644: invokestatic 180	com/fab/InviteActivity:access$6	(Lcom/fab/InviteActivity;)Landroid/content/Context;
        //   647: invokevirtual 186	android/content/Context:getResources	()Landroid/content/res/Resources;
        //   650: ldc_w 265
        //   653: invokevirtual 193	android/content/res/Resources:getDrawable	(I)Landroid/graphics/drawable/Drawable;
        //   656: invokevirtual 199	android/widget/ImageView:setImageDrawable	(Landroid/graphics/drawable/Drawable;)V
        //   659: aload_0
        //   660: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   663: invokestatic 202	com/fab/InviteActivity:access$7	(Lcom/fab/InviteActivity;)Lcom/fab/views/FabTextView;
        //   666: ldc_w 267
        //   669: invokevirtual 165	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
        //   672: aload_0
        //   673: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   676: invokestatic 202	com/fab/InviteActivity:access$7	(Lcom/fab/InviteActivity;)Lcom/fab/views/FabTextView;
        //   679: iconst_0
        //   680: invokevirtual 205	com/fab/views/FabTextView:setVisibility	(I)V
        //   683: goto -282 -> 401
        //   686: aload 15
        //   688: iload 16
        //   690: invokevirtual 270	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
        //   693: astore 18
        //   695: aload_0
        //   696: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   699: aload_0
        //   700: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   703: invokestatic 274	com/fab/InviteActivity:access$9	(Lcom/fab/InviteActivity;)Landroid/view/LayoutInflater;
        //   706: ldc_w 275
        //   709: aconst_null
        //   710: invokevirtual 281	android/view/LayoutInflater:inflate	(ILandroid/view/ViewGroup;)Landroid/view/View;
        //   713: invokestatic 285	com/fab/InviteActivity:access$10	(Lcom/fab/InviteActivity;Landroid/view/View;)V
        //   716: aload_0
        //   717: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   720: aload_0
        //   721: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   724: invokestatic 289	com/fab/InviteActivity:access$11	(Lcom/fab/InviteActivity;)Landroid/view/View;
        //   727: ldc_w 290
        //   730: invokevirtual 296	android/view/View:findViewById	(I)Landroid/view/View;
        //   733: checkcast 162	com/fab/views/FabTextView
        //   736: putfield 300	com/fab/InviteActivity:tvHeader	Lcom/fab/views/FabTextView;
        //   739: aload_0
        //   740: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   743: getfield 300	com/fab/InviteActivity:tvHeader	Lcom/fab/views/FabTextView;
        //   746: ldc_w 302
        //   749: invokevirtual 305	com/fab/views/FabTextView:setFont	(Ljava/lang/String;)V
        //   752: aload_0
        //   753: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   756: aload_0
        //   757: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   760: invokestatic 289	com/fab/InviteActivity:access$11	(Lcom/fab/InviteActivity;)Landroid/view/View;
        //   763: ldc_w 306
        //   766: invokevirtual 296	android/view/View:findViewById	(I)Landroid/view/View;
        //   769: checkcast 162	com/fab/views/FabTextView
        //   772: putfield 309	com/fab/InviteActivity:tvDesc	Lcom/fab/views/FabTextView;
        //   775: aload_0
        //   776: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   779: getfield 309	com/fab/InviteActivity:tvDesc	Lcom/fab/views/FabTextView;
        //   782: ldc_w 311
        //   785: invokevirtual 305	com/fab/views/FabTextView:setFont	(Ljava/lang/String;)V
        //   788: aload_0
        //   789: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   792: getfield 300	com/fab/InviteActivity:tvHeader	Lcom/fab/views/FabTextView;
        //   795: aload 18
        //   797: ldc_w 313
        //   800: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   803: invokevirtual 165	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
        //   806: aload_0
        //   807: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   810: getfield 309	com/fab/InviteActivity:tvDesc	Lcom/fab/views/FabTextView;
        //   813: aload 18
        //   815: ldc_w 315
        //   818: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   821: invokevirtual 165	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
        //   824: aload_0
        //   825: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   828: invokestatic 209	com/fab/InviteActivity:access$8	(Lcom/fab/InviteActivity;)Landroid/widget/LinearLayout;
        //   831: aload_0
        //   832: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   835: invokestatic 289	com/fab/InviteActivity:access$11	(Lcom/fab/InviteActivity;)Landroid/view/View;
        //   838: invokevirtual 319	android/widget/LinearLayout:addView	(Landroid/view/View;)V
        //   841: iinc 16 1
        //   844: goto -415 -> 429
        //   847: aload_0
        //   848: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   851: invokestatic 248	com/fab/InviteActivity:access$12	(Lcom/fab/InviteActivity;)Landroid/widget/RelativeLayout;
        //   854: bipush 8
        //   856: invokevirtual 251	android/widget/RelativeLayout:setVisibility	(I)V
        //   859: aload_0
        //   860: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   863: invokestatic 180	com/fab/InviteActivity:access$6	(Lcom/fab/InviteActivity;)Landroid/content/Context;
        //   866: aload_2
        //   867: ldc 38
        //   869: invokevirtual 42	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   872: invokestatic 323	com/fab/utils/FabUtils:showAlert	(Landroid/content/Context;Ljava/lang/String;)V
        //   875: goto -367 -> 508
        //   878: astore 4
        //   880: goto -372 -> 508
        //   883: aload_0
        //   884: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   887: invokestatic 248	com/fab/InviteActivity:access$12	(Lcom/fab/InviteActivity;)Landroid/widget/RelativeLayout;
        //   890: bipush 8
        //   892: invokevirtual 251	android/widget/RelativeLayout:setVisibility	(I)V
        //   895: aload_0
        //   896: getfield 22	com/fab/InviteActivity$3:val$task	Lcom/fab/utils/ExecuteRequestAsyncTask;
        //   899: invokevirtual 329	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
        //   902: invokestatic 48	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   905: ifne -397 -> 508
        //   908: aload_0
        //   909: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   912: invokestatic 333	com/fab/InviteActivity:access$14	(Lcom/fab/InviteActivity;)Lcom/fab/views/ErrorMessage;
        //   915: iconst_0
        //   916: invokevirtual 336	com/fab/views/ErrorMessage:setVisibility	(I)V
        //   919: aload_0
        //   920: getfield 20	com/fab/InviteActivity$3:this$0	Lcom/fab/InviteActivity;
        //   923: invokestatic 333	com/fab/InviteActivity:access$14	(Lcom/fab/InviteActivity;)Lcom/fab/views/ErrorMessage;
        //   926: aload_0
        //   927: getfield 22	com/fab/InviteActivity$3:val$task	Lcom/fab/utils/ExecuteRequestAsyncTask;
        //   930: invokevirtual 329	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
        //   933: invokevirtual 339	com/fab/views/ErrorMessage:setErrorMsg	(Ljava/lang/String;)V
        //   936: goto -428 -> 508
        //   939: astore 13
        //   941: goto -456 -> 485
        //   944: astore 12
        //   946: goto -497 -> 449
        //   949: astore 11
        //   951: goto -515 -> 436
        //   954: astore 9
        //   956: goto -605 -> 351
        //   959: astore 8
        //   961: goto -623 -> 338
        //   964: astore 7
        //   966: goto -644 -> 322
        //   969: astore 30
        //   971: goto -649 -> 322
        //   974: astore 29
        //   976: goto -668 -> 308
        //   979: astore 28
        //   981: goto -695 -> 286
        //   984: astore 27
        //   986: goto -722 -> 264
        //   989: astore 26
        //   991: goto -744 -> 247
        //   994: astore 25
        //   996: goto -766 -> 230
        //   999: astore 24
        //   1001: goto -788 -> 213
        //   1004: astore 23
        //   1006: goto -810 -> 196
        //   1009: astore 22
        //   1011: goto -832 -> 179
        //   1014: astore 21
        //   1016: goto -873 -> 143
        //   1019: astore 6
        //   1021: goto -899 -> 122
        //   1024: astore_3
        //   1025: goto -1002 -> 23
        //   1028: astore 10
        //   1030: goto -629 -> 401
        //
        // Exception table:
        //   from	to	target	type
        //   23	34	878	org/json/JSONException
        //   485	508	878	org/json/JSONException
        //   847	875	878	org/json/JSONException
        //   449	485	939	org/json/JSONException
        //   436	449	944	org/json/JSONException
        //   401	429	949	org/json/JSONException
        //   686	841	949	org/json/JSONException
        //   338	351	954	org/json/JSONException
        //   322	338	959	org/json/JSONException
        //   122	130	964	org/json/JSONException
        //   148	162	964	org/json/JSONException
        //   308	322	969	org/json/JSONException
        //   286	308	974	org/json/JSONException
        //   264	286	979	org/json/JSONException
        //   247	264	984	org/json/JSONException
        //   230	247	989	org/json/JSONException
        //   213	230	994	org/json/JSONException
        //   196	213	999	org/json/JSONException
        //   179	196	1004	org/json/JSONException
        //   162	179	1009	org/json/JSONException
        //   130	143	1014	org/json/JSONException
        //   39	122	1019	org/json/JSONException
        //   16	23	1024	org/json/JSONException
        //   351	401	1028	org/json/JSONException
        //   515	683	1028	org/json/JSONException
      }
    });
    String[] arrayOfString = new String[1];
    arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/invites", "");
    localExecuteRequestAsyncTask.execute(arrayOfString);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (this.fbClient != null)
      this.fbClient.authorizeCallback(paramInt1, paramInt2, paramIntent);
  }

  public void onClick(View paramView)
  {
    if ((paramView instanceof FabButton))
      switch (((FabButton)paramView).getId())
      {
      default:
      case 1:
      case 2:
      case 3:
      case 4:
      }
    while (true)
    {
      return;
      if (this.fbShareData != null)
      {
        this.fbClient = new FabFBClient(this.context);
        this.fbClient.setFBShareData(this.fbShareData);
        this.fbClient.setHasFBShareToken(this.hasFBShareToken);
        this.fbClient.sendRequest("publish_stream");
        continue;
        if (this.twShareText != null)
        {
          Intent localIntent3 = new Intent(this.context, FabTwitterClient.class);
          localIntent3.putExtra("TWEET_TEXT", this.twShareText);
          startActivity(localIntent3);
          continue;
          Intent localIntent2 = new Intent("android.intent.action.SEND");
          localIntent2.setType("text/html");
          localIntent2.putExtra("android.intent.extra.SUBJECT", this.emailSubject);
          localIntent2.putExtra("android.intent.extra.TEXT", Html.fromHtml(this.emailBody));
          startActivity(localIntent2);
          continue;
          Intent localIntent1 = new Intent("android.intent.action.VIEW");
          localIntent1.setType("vnd.android-dir/mms-sms");
          localIntent1.setData(Uri.parse("sms:"));
          localIntent1.putExtra("sms_body", this.inviteSMS);
          startActivity(localIntent1);
        }
      }
    }
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903064);
    this.context = this;
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("invitePage"));
    if (!FabUtils.isLoggedIn(this.context))
    {
      finish();
      Intent localIntent = new Intent(this.context, FabIndex.class);
      localIntent.setFlags(67108864);
      startActivity(localIntent);
    }
    this.isActivityAlive = true;
    initilizeUI();
    loadInviteData();
    this.animation = AnimationUtils.loadAnimation(this.context, 2130968577);
    this.animation.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
      }

      public void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnonymousAnimation)
      {
        InviteActivity.this.membershipRel.setVisibility(8);
      }
    });
    this.animation1 = AnimationUtils.loadAnimation(this.context, 2130968578);
    this.animation1.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        InviteActivity.this.membershipRel.setVisibility(0);
      }

      public void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnonymousAnimation)
      {
      }
    });
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
        Intent localIntent = new Intent(InviteActivity.this.context, FabWebViewActivity.class);
        InviteActivity.this.startActivity(localIntent);
        return false;
      }
    });
    MenuItem localMenuItem2 = paramMenu.findItem(2131361948);
    localMenuItem2.setVisible(true);
    localMenuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(InviteActivity.this.context, MoreActivity.class);
        InviteActivity.this.startActivity(localIntent);
        return false;
      }
    });
    MenuItem localMenuItem3 = paramMenu.findItem(2131362103);
    localMenuItem3.setVisible(true);
    localMenuItem3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        InviteActivity.this.scrollView.setVisibility(8);
        InviteActivity.this.pbar.setVisibility(0);
        InviteActivity.this.loadInviteData();
        return false;
      }
    });
    return super.onCreateOptionsMenu(paramMenu);
  }

  protected void onDestroy()
  {
    this.tracker.stopSession();
    this.isActivityAlive = false;
    super.onDestroy();
  }

  protected void onPause()
  {
    this.isActivityAlive = false;
    super.onPause();
  }

  protected void onRestart()
  {
    this.isActivityAlive = true;
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
    this.isActivityAlive = true;
    super.onResume();
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
 * Qualified Name:     com.fab.InviteActivity
 * JD-Core Version:    0.6.2
 */