package com.fab.sale;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask.Status;
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
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.fab.FabApplication;
import com.fab.FabIndex;
import com.fab.InviteActivity;
import com.fab.MoreActivity;
import com.fab.facebook.FabFBClient;
import com.fab.facebook.FabFBShareBO;
import com.fab.sale.bo.SalesBO;
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

public class WeeklyShopDetailsActivity extends Activity
{
  private static final String TAG = "WEEKLY_SHOPS_ACTIVTY";
  protected static boolean isActivityAlive;
  public static TitleWidget titleWidget;
  private SalesAdapter adapter;
  private AlertDialog.Builder alertbox;
  private Context context;
  private String emailBody;
  private String emailSubject;
  private ErrorMessage error;
  private Bundle extras;
  protected FabFBClient fbClient;
  protected FabFBShareBO fbShareData;
  private MenuItem fbshareItem;
  private View footerView;
  protected boolean hasFBShareToken;
  private boolean hasSetHeader;
  private FabButton headerCartButton;
  private View headerView;
  private FabTextView hideText;
  private ImageView infoIconBottom;
  private ImageView infoIconTop;
  private int isNotificationEnabled;
  private ListView listView;
  protected boolean loadingMore;
  private Handler mHandler;
  public boolean nextPageExists;
  public String notificationKey;
  private String notificationURL;
  private int page;
  private ProgressBar progressBar;
  private WebView shopDescription;
  private RelativeLayout shopDescriptionHolder;
  private ImageView shopIcon;
  private FabTextView shopName;
  private FabTextView shopText;
  public String smsShare;
  private ExecuteRequestAsyncTask task;
  private ArrayList<SalesBO> theSales;
  private FabTextView timer;
  private GoogleAnalyticsTracker tracker;
  private String twShareText;
  private int weeklyShopId;

  private void loadSales()
  {
    if (this.page < 1)
      this.page = 1;
    if ((this.task == null) || ((this.task != null) && (this.task.getStatus() != AsyncTask.Status.RUNNING)))
    {
      this.task = new ExecuteRequestAsyncTask(this.context, "GET");
      this.task.setTaskCompleteListener(new TaskCompleteListener()
      {
        private String shopIconName;

        public void onTaskComplete()
        {
        }

        // ERROR //
        public void onTaskComplete(Object paramAnonymousObject)
        {
          // Byte code:
          //   0: aload_1
          //   1: ifnull +1199 -> 1200
          //   4: aload_1
          //   5: instanceof 36
          //   8: ifeq +1192 -> 1200
          //   11: aload_1
          //   12: checkcast 36	org/json/JSONObject
          //   15: astore_2
          //   16: ldc 38
          //   18: aload_2
          //   19: invokevirtual 42	org/json/JSONObject:toString	()Ljava/lang/String;
          //   22: invokestatic 48	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
          //   25: iconst_0
          //   26: istore_3
          //   27: aload_2
          //   28: ldc 50
          //   30: invokevirtual 54	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   33: istore 61
          //   35: iload 61
          //   37: istore_3
          //   38: iload_3
          //   39: ifeq +21 -> 60
          //   42: aload_0
          //   43: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   46: invokestatic 58	com/fab/sale/WeeklyShopDetailsActivity:access$5	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/content/Context;
          //   49: aconst_null
          //   50: invokestatic 62	com/fab/utils/FabUtils:logoutUser	(Landroid/content/Context;Landroid/os/Handler;)V
          //   53: aload_0
          //   54: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   57: invokevirtual 65	com/fab/sale/WeeklyShopDetailsActivity:finish	()V
          //   60: ldc 67
          //   62: astore 5
          //   64: aload_2
          //   65: ldc 69
          //   67: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   70: astore 60
          //   72: aload 60
          //   74: astore 5
          //   76: aload_0
          //   77: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   80: invokestatic 77	com/fab/sale/WeeklyShopDetailsActivity:access$10	(Lcom/fab/sale/WeeklyShopDetailsActivity;)I
          //   83: iconst_1
          //   84: if_icmpne +23 -> 107
          //   87: aload_0
          //   88: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   91: invokestatic 81	com/fab/sale/WeeklyShopDetailsActivity:access$11	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Ljava/util/ArrayList;
          //   94: ifnull +745 -> 839
          //   97: aload_0
          //   98: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   101: invokestatic 81	com/fab/sale/WeeklyShopDetailsActivity:access$11	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Ljava/util/ArrayList;
          //   104: invokevirtual 86	java/util/ArrayList:clear	()V
          //   107: aload 5
          //   109: invokestatic 92	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   112: ifeq +959 -> 1071
          //   115: new 94	org/json/JSONArray
          //   118: dup
          //   119: invokespecial 95	org/json/JSONArray:<init>	()V
          //   122: astore 7
          //   124: new 36	org/json/JSONObject
          //   127: dup
          //   128: invokespecial 96	org/json/JSONObject:<init>	()V
          //   131: astore 8
          //   133: new 36	org/json/JSONObject
          //   136: dup
          //   137: invokespecial 96	org/json/JSONObject:<init>	()V
          //   140: astore 9
          //   142: aload_2
          //   143: ldc 98
          //   145: invokevirtual 102	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   148: astore 59
          //   150: aload 59
          //   152: astore 9
          //   154: aload_0
          //   155: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   158: aload 9
          //   160: ldc 104
          //   162: invokevirtual 108	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   165: invokevirtual 112	com/fab/sale/WeeklyShopDetailsActivity:updateCartCounter	(I)V
          //   168: aload 9
          //   170: ldc 114
          //   172: invokevirtual 108	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   175: i2l
          //   176: new 116	java/util/GregorianCalendar
          //   179: dup
          //   180: invokespecial 117	java/util/GregorianCalendar:<init>	()V
          //   183: invokestatic 121	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
          //   186: ldc2_w 122
          //   189: lcmp
          //   190: iflt +39 -> 229
          //   193: aload 9
          //   195: ldc 114
          //   197: invokevirtual 108	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   200: i2l
          //   201: invokestatic 129	com/fab/utils/FabHelper:setCartTime	(J)V
          //   204: aload_0
          //   205: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   208: invokevirtual 133	com/fab/sale/WeeklyShopDetailsActivity:getApplicationContext	()Landroid/content/Context;
          //   211: aload 9
          //   213: ldc 114
          //   215: invokevirtual 108	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   218: i2l
          //   219: aload_0
          //   220: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   223: invokestatic 137	com/fab/sale/WeeklyShopDetailsActivity:access$13	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/os/Handler;
          //   226: invokestatic 141	com/fab/utils/FabHelper:stopAndRestartCartTimer	(Landroid/content/Context;JLandroid/os/Handler;)V
          //   229: aload_0
          //   230: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   233: invokestatic 77	com/fab/sale/WeeklyShopDetailsActivity:access$10	(Lcom/fab/sale/WeeklyShopDetailsActivity;)I
          //   236: iconst_1
          //   237: if_icmpne +451 -> 688
          //   240: aload_2
          //   241: ldc 143
          //   243: invokevirtual 102	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   246: astore 58
          //   248: aload 58
          //   250: astore 8
          //   252: aload_0
          //   253: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   256: invokestatic 147	com/fab/sale/WeeklyShopDetailsActivity:access$14	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Lcom/fab/views/FabTextView;
          //   259: aload 8
          //   261: ldc 149
          //   263: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   266: invokevirtual 155	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
          //   269: aload_0
          //   270: aload 8
          //   272: ldc 157
          //   274: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   277: putfield 28	com/fab/sale/WeeklyShopDetailsActivity$14:shopIconName	Ljava/lang/String;
          //   280: aload_0
          //   281: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   284: invokestatic 161	com/fab/sale/WeeklyShopDetailsActivity:access$15	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/widget/ImageView;
          //   287: ifnull +102 -> 389
          //   290: aload_0
          //   291: getfield 28	com/fab/sale/WeeklyShopDetailsActivity$14:shopIconName	Ljava/lang/String;
          //   294: invokestatic 92	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   297: ifne +92 -> 389
          //   300: aload_0
          //   301: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   304: invokestatic 58	com/fab/sale/WeeklyShopDetailsActivity:access$5	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/content/Context;
          //   307: invokevirtual 167	android/content/Context:getResources	()Landroid/content/res/Resources;
          //   310: aload_0
          //   311: getfield 28	com/fab/sale/WeeklyShopDetailsActivity$14:shopIconName	Ljava/lang/String;
          //   314: ldc 169
          //   316: ldc 171
          //   318: invokevirtual 177	android/content/res/Resources:getIdentifier	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
          //   321: istore 53
          //   323: iload 53
          //   325: ifne +531 -> 856
          //   328: new 179	com/fab/utils/ImageDownloader
          //   331: dup
          //   332: aload_0
          //   333: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   336: invokestatic 58	com/fab/sale/WeeklyShopDetailsActivity:access$5	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/content/Context;
          //   339: aconst_null
          //   340: iconst_1
          //   341: aconst_null
          //   342: invokespecial 182	com/fab/utils/ImageDownloader:<init>	(Landroid/content/Context;Lcom/fab/utils/FabBaseAdapter;ZLandroid/widget/ProgressBar;)V
          //   345: astore 54
          //   347: new 8	com/fab/sale/WeeklyShopDetailsActivity$14$1
          //   350: dup
          //   351: aload_0
          //   352: invokespecial 185	com/fab/sale/WeeklyShopDetailsActivity$14$1:<init>	(Lcom/fab/sale/WeeklyShopDetailsActivity$14;)V
          //   355: astore 55
          //   357: aload 54
          //   359: aload 55
          //   361: invokevirtual 189	com/fab/utils/ImageDownloader:setTaskCompleteListener	(Lcom/fab/utils/TaskCompleteListener;)V
          //   364: iconst_1
          //   365: anewarray 191	java/lang/String
          //   368: astore 56
          //   370: aload 56
          //   372: iconst_0
          //   373: aload_0
          //   374: getfield 28	com/fab/sale/WeeklyShopDetailsActivity$14:shopIconName	Ljava/lang/String;
          //   377: invokestatic 194	com/fab/utils/FabUtils:s3MobileIconsUrl	(Ljava/lang/String;)Ljava/lang/String;
          //   380: aastore
          //   381: aload 54
          //   383: aload 56
          //   385: invokevirtual 198	com/fab/utils/ImageDownloader:execute	([Ljava/lang/Object;)Landroid/os/AsyncTask;
          //   388: pop
          //   389: aload_0
          //   390: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   393: invokestatic 202	com/fab/sale/WeeklyShopDetailsActivity:access$16	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/webkit/WebView;
          //   396: new 204	java/lang/StringBuilder
          //   399: dup
          //   400: ldc 206
          //   402: invokespecial 209	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   405: aload 8
          //   407: ldc 211
          //   409: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   412: ldc 213
          //   414: ldc 215
          //   416: invokestatic 219	com/fab/utils/FabUtils:setWebViewStyle	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
          //   419: invokevirtual 223	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   422: invokevirtual 224	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   425: ldc 226
          //   427: ldc 228
          //   429: invokevirtual 234	android/webkit/WebView:loadData	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
          //   432: aload_0
          //   433: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   436: invokestatic 238	com/fab/sale/WeeklyShopDetailsActivity:access$17	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/view/View;
          //   439: iconst_0
          //   440: invokevirtual 243	android/view/View:setVisibility	(I)V
          //   443: new 36	org/json/JSONObject
          //   446: dup
          //   447: invokespecial 96	org/json/JSONObject:<init>	()V
          //   450: pop
          //   451: aload_0
          //   452: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   455: aload_2
          //   456: ldc 245
          //   458: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   461: invokestatic 249	com/fab/sale/WeeklyShopDetailsActivity:access$18	(Lcom/fab/sale/WeeklyShopDetailsActivity;Ljava/lang/String;)V
          //   464: aload_2
          //   465: ldc 251
          //   467: invokevirtual 102	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   470: astore 52
          //   472: aload 52
          //   474: astore 37
          //   476: aload 37
          //   478: ifnull +140 -> 618
          //   481: aload_0
          //   482: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   485: new 253	com/fab/facebook/FabFBShareBO
          //   488: dup
          //   489: invokespecial 254	com/fab/facebook/FabFBShareBO:<init>	()V
          //   492: putfield 258	com/fab/sale/WeeklyShopDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   495: aload_0
          //   496: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   499: getfield 258	com/fab/sale/WeeklyShopDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   502: aload 37
          //   504: ldc_w 260
          //   507: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   510: invokevirtual 263	com/fab/facebook/FabFBShareBO:setLink	(Ljava/lang/String;)V
          //   513: aload_0
          //   514: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   517: getfield 258	com/fab/sale/WeeklyShopDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   520: aload 37
          //   522: ldc_w 265
          //   525: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   528: invokevirtual 268	com/fab/facebook/FabFBShareBO:setCaption	(Ljava/lang/String;)V
          //   531: aload_0
          //   532: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   535: getfield 258	com/fab/sale/WeeklyShopDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   538: aload 37
          //   540: ldc_w 270
          //   543: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   546: invokevirtual 273	com/fab/facebook/FabFBShareBO:setDescription	(Ljava/lang/String;)V
          //   549: aload_0
          //   550: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   553: getfield 258	com/fab/sale/WeeklyShopDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   556: aload 37
          //   558: ldc_w 275
          //   561: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   564: invokevirtual 278	com/fab/facebook/FabFBShareBO:setPicture	(Ljava/lang/String;)V
          //   567: aload_0
          //   568: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   571: getfield 258	com/fab/sale/WeeklyShopDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   574: aload 37
          //   576: ldc_w 280
          //   579: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   582: invokevirtual 283	com/fab/facebook/FabFBShareBO:setName	(Ljava/lang/String;)V
          //   585: aload_0
          //   586: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   589: getfield 258	com/fab/sale/WeeklyShopDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   592: aload 37
          //   594: ldc_w 280
          //   597: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   600: invokevirtual 283	com/fab/facebook/FabFBShareBO:setName	(Ljava/lang/String;)V
          //   603: aload_0
          //   604: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   607: aload 37
          //   609: ldc_w 285
          //   612: invokevirtual 54	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   615: putfield 289	com/fab/sale/WeeklyShopDetailsActivity:hasFBShareToken	Z
          //   618: new 36	org/json/JSONObject
          //   621: dup
          //   622: invokespecial 96	org/json/JSONObject:<init>	()V
          //   625: pop
          //   626: aload_2
          //   627: ldc_w 291
          //   630: invokevirtual 102	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   633: astore 44
          //   635: aload 44
          //   637: astore 40
          //   639: aload 40
          //   641: ifnull +33 -> 674
          //   644: aload_0
          //   645: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   648: aload 40
          //   650: ldc_w 293
          //   653: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   656: invokestatic 296	com/fab/sale/WeeklyShopDetailsActivity:access$19	(Lcom/fab/sale/WeeklyShopDetailsActivity;Ljava/lang/String;)V
          //   659: aload_0
          //   660: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   663: aload 40
          //   665: ldc_w 298
          //   668: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   671: invokestatic 301	com/fab/sale/WeeklyShopDetailsActivity:access$20	(Lcom/fab/sale/WeeklyShopDetailsActivity;Ljava/lang/String;)V
          //   674: aload_0
          //   675: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   678: aload_2
          //   679: ldc_w 303
          //   682: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   685: putfield 306	com/fab/sale/WeeklyShopDetailsActivity:smsShare	Ljava/lang/String;
          //   688: aload_0
          //   689: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   692: iconst_0
          //   693: invokestatic 310	com/fab/sale/WeeklyShopDetailsActivity:access$21	(Lcom/fab/sale/WeeklyShopDetailsActivity;Z)V
          //   696: aload_2
          //   697: ldc_w 312
          //   700: invokevirtual 316	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   703: astore 29
          //   705: aload 29
          //   707: astore 7
          //   709: iconst_0
          //   710: istore 13
          //   712: aload 7
          //   714: invokevirtual 320	org/json/JSONArray:length	()I
          //   717: istore 14
          //   719: iload 13
          //   721: iload 14
          //   723: if_icmplt +164 -> 887
          //   726: aload_0
          //   727: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   730: invokestatic 324	com/fab/sale/WeeklyShopDetailsActivity:access$23	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Lcom/fab/sale/SalesAdapter;
          //   733: ifnull +23 -> 756
          //   736: aload_0
          //   737: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   740: invokestatic 324	com/fab/sale/WeeklyShopDetailsActivity:access$23	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Lcom/fab/sale/SalesAdapter;
          //   743: invokevirtual 329	com/fab/sale/SalesAdapter:downloadTheImages	()V
          //   746: aload_0
          //   747: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   750: invokestatic 324	com/fab/sale/WeeklyShopDetailsActivity:access$23	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Lcom/fab/sale/SalesAdapter;
          //   753: invokevirtual 332	com/fab/sale/SalesAdapter:notifyDataSetChanged	()V
          //   756: aload_2
          //   757: ldc_w 334
          //   760: invokevirtual 54	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   763: ifeq +352 -> 1115
          //   766: aload_0
          //   767: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   770: iconst_1
          //   771: putfield 337	com/fab/sale/WeeklyShopDetailsActivity:nextPageExists	Z
          //   774: aload_0
          //   775: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   778: astore 28
          //   780: aload 28
          //   782: iconst_1
          //   783: aload 28
          //   785: invokestatic 77	com/fab/sale/WeeklyShopDetailsActivity:access$10	(Lcom/fab/sale/WeeklyShopDetailsActivity;)I
          //   788: iadd
          //   789: invokestatic 341	com/fab/sale/WeeklyShopDetailsActivity:access$24	(Lcom/fab/sale/WeeklyShopDetailsActivity;I)V
          //   792: aload_0
          //   793: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   796: invokestatic 344	com/fab/sale/WeeklyShopDetailsActivity:access$25	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/view/View;
          //   799: iconst_0
          //   800: invokevirtual 243	android/view/View:setVisibility	(I)V
          //   803: aload_0
          //   804: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   807: invokestatic 348	com/fab/sale/WeeklyShopDetailsActivity:access$26	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/widget/ListView;
          //   810: iconst_0
          //   811: invokevirtual 351	android/widget/ListView:setVisibility	(I)V
          //   814: aload_0
          //   815: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   818: invokestatic 355	com/fab/sale/WeeklyShopDetailsActivity:access$27	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/widget/ProgressBar;
          //   821: bipush 8
          //   823: invokevirtual 358	android/widget/ProgressBar:setVisibility	(I)V
          //   826: aload_0
          //   827: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   830: invokestatic 362	com/fab/sale/WeeklyShopDetailsActivity:access$28	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Lcom/fab/views/ErrorMessage;
          //   833: bipush 8
          //   835: invokevirtual 365	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   838: return
          //   839: aload_0
          //   840: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   843: new 83	java/util/ArrayList
          //   846: dup
          //   847: invokespecial 366	java/util/ArrayList:<init>	()V
          //   850: invokestatic 370	com/fab/sale/WeeklyShopDetailsActivity:access$12	(Lcom/fab/sale/WeeklyShopDetailsActivity;Ljava/util/ArrayList;)V
          //   853: goto -746 -> 107
          //   856: aload_0
          //   857: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   860: invokestatic 161	com/fab/sale/WeeklyShopDetailsActivity:access$15	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/widget/ImageView;
          //   863: iload 53
          //   865: invokevirtual 375	android/widget/ImageView:setImageResource	(I)V
          //   868: goto -479 -> 389
          //   871: astore 36
          //   873: aconst_null
          //   874: astore 37
          //   876: goto -400 -> 476
          //   879: astore 39
          //   881: aconst_null
          //   882: astore 40
          //   884: goto -245 -> 639
          //   887: new 377	com/fab/sale/bo/SalesBO
          //   890: dup
          //   891: invokespecial 378	com/fab/sale/bo/SalesBO:<init>	()V
          //   894: astore 15
          //   896: new 36	org/json/JSONObject
          //   899: dup
          //   900: invokespecial 96	org/json/JSONObject:<init>	()V
          //   903: astore 16
          //   905: aload 7
          //   907: iload 13
          //   909: invokevirtual 381	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   912: astore 24
          //   914: aload 24
          //   916: astore 16
          //   918: aload 15
          //   920: aload 16
          //   922: ldc_w 383
          //   925: invokevirtual 108	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   928: invokevirtual 386	com/fab/sale/bo/SalesBO:setSaleId	(I)V
          //   931: aload_0
          //   932: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   935: invokestatic 390	com/fab/sale/WeeklyShopDetailsActivity:access$22	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Z
          //   938: ifne +124 -> 1062
          //   941: aload_0
          //   942: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   945: invokestatic 77	com/fab/sale/WeeklyShopDetailsActivity:access$10	(Lcom/fab/sale/WeeklyShopDetailsActivity;)I
          //   948: iconst_1
          //   949: if_icmpne +113 -> 1062
          //   952: aload 15
          //   954: iconst_1
          //   955: invokevirtual 394	com/fab/sale/bo/SalesBO:setHeader	(Z)V
          //   958: aload_0
          //   959: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   962: iconst_1
          //   963: invokestatic 310	com/fab/sale/WeeklyShopDetailsActivity:access$21	(Lcom/fab/sale/WeeklyShopDetailsActivity;Z)V
          //   966: aload 15
          //   968: aload 16
          //   970: ldc_w 396
          //   973: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   976: invokevirtual 399	com/fab/sale/bo/SalesBO:setSaleName	(Ljava/lang/String;)V
          //   979: aload 15
          //   981: aload 16
          //   983: ldc_w 401
          //   986: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   989: invokevirtual 404	com/fab/sale/bo/SalesBO:setPrimaryTitle	(Ljava/lang/String;)V
          //   992: aload 15
          //   994: new 204	java/lang/StringBuilder
          //   997: dup
          //   998: ldc_w 406
          //   1001: invokespecial 209	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   1004: aload 16
          //   1006: ldc_w 408
          //   1009: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1012: ldc_w 410
          //   1015: ldc_w 412
          //   1018: invokevirtual 416	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   1021: invokevirtual 223	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1024: invokevirtual 224	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   1027: invokevirtual 419	com/fab/sale/bo/SalesBO:setSaleImageUrl	(Ljava/lang/String;)V
          //   1030: aload 15
          //   1032: aload 16
          //   1034: ldc_w 421
          //   1037: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1040: invokevirtual 424	com/fab/sale/bo/SalesBO:setSaleDuration	(Ljava/lang/String;)V
          //   1043: aload_0
          //   1044: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1047: invokestatic 81	com/fab/sale/WeeklyShopDetailsActivity:access$11	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Ljava/util/ArrayList;
          //   1050: aload 15
          //   1052: invokevirtual 428	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   1055: pop
          //   1056: iinc 13 1
          //   1059: goto -340 -> 719
          //   1062: aload 15
          //   1064: iconst_0
          //   1065: invokevirtual 394	com/fab/sale/bo/SalesBO:setHeader	(Z)V
          //   1068: goto -102 -> 966
          //   1071: aload 5
          //   1073: invokestatic 92	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   1076: ifne -350 -> 726
          //   1079: aload_0
          //   1080: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1083: invokestatic 58	com/fab/sale/WeeklyShopDetailsActivity:access$5	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/content/Context;
          //   1086: aload 5
          //   1088: invokestatic 432	com/fab/utils/FabUtils:showAlert	(Landroid/content/Context;Ljava/lang/String;)V
          //   1091: aload_0
          //   1092: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1095: invokevirtual 436	com/fab/sale/WeeklyShopDetailsActivity:getApplication	()Landroid/app/Application;
          //   1098: checkcast 438	com/fab/FabApplication
          //   1101: iconst_1
          //   1102: invokevirtual 441	com/fab/FabApplication:setRefreshActivityOnRestart	(Z)V
          //   1105: aload_0
          //   1106: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1109: invokevirtual 65	com/fab/sale/WeeklyShopDetailsActivity:finish	()V
          //   1112: goto -386 -> 726
          //   1115: aload_0
          //   1116: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1119: iconst_0
          //   1120: putfield 337	com/fab/sale/WeeklyShopDetailsActivity:nextPageExists	Z
          //   1123: aload_0
          //   1124: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1127: invokestatic 348	com/fab/sale/WeeklyShopDetailsActivity:access$26	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/widget/ListView;
          //   1130: aload_0
          //   1131: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1134: invokestatic 344	com/fab/sale/WeeklyShopDetailsActivity:access$25	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/view/View;
          //   1137: invokevirtual 445	android/widget/ListView:removeFooterView	(Landroid/view/View;)Z
          //   1140: pop
          //   1141: goto -338 -> 803
          //   1144: astore 25
          //   1146: aload_0
          //   1147: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1150: getfield 337	com/fab/sale/WeeklyShopDetailsActivity:nextPageExists	Z
          //   1153: ifne +24 -> 1177
          //   1156: aload_0
          //   1157: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1160: invokestatic 348	com/fab/sale/WeeklyShopDetailsActivity:access$26	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/widget/ListView;
          //   1163: aload_0
          //   1164: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1167: invokestatic 344	com/fab/sale/WeeklyShopDetailsActivity:access$25	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/view/View;
          //   1170: invokevirtual 445	android/widget/ListView:removeFooterView	(Landroid/view/View;)Z
          //   1173: pop
          //   1174: goto -371 -> 803
          //   1177: aload_0
          //   1178: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1181: iconst_0
          //   1182: putfield 337	com/fab/sale/WeeklyShopDetailsActivity:nextPageExists	Z
          //   1185: aload_0
          //   1186: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1189: invokestatic 344	com/fab/sale/WeeklyShopDetailsActivity:access$25	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/view/View;
          //   1192: bipush 8
          //   1194: invokevirtual 243	android/view/View:setVisibility	(I)V
          //   1197: goto -394 -> 803
          //   1200: aload_0
          //   1201: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1204: invokestatic 355	com/fab/sale/WeeklyShopDetailsActivity:access$27	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/widget/ProgressBar;
          //   1207: bipush 8
          //   1209: invokevirtual 358	android/widget/ProgressBar:setVisibility	(I)V
          //   1212: aload_0
          //   1213: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1216: invokestatic 449	com/fab/sale/WeeklyShopDetailsActivity:access$29	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   1219: invokevirtual 454	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   1222: invokestatic 92	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   1225: ifne -387 -> 838
          //   1228: aload_0
          //   1229: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1232: invokestatic 362	com/fab/sale/WeeklyShopDetailsActivity:access$28	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Lcom/fab/views/ErrorMessage;
          //   1235: iconst_0
          //   1236: invokevirtual 365	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   1239: aload_0
          //   1240: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1243: invokestatic 362	com/fab/sale/WeeklyShopDetailsActivity:access$28	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Lcom/fab/views/ErrorMessage;
          //   1246: aload_0
          //   1247: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1250: invokestatic 449	com/fab/sale/WeeklyShopDetailsActivity:access$29	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   1253: invokevirtual 454	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   1256: invokevirtual 457	com/fab/views/ErrorMessage:setErrorMsg	(Ljava/lang/String;)V
          //   1259: aload_0
          //   1260: getfield 22	com/fab/sale/WeeklyShopDetailsActivity$14:this$0	Lcom/fab/sale/WeeklyShopDetailsActivity;
          //   1263: invokestatic 348	com/fab/sale/WeeklyShopDetailsActivity:access$26	(Lcom/fab/sale/WeeklyShopDetailsActivity;)Landroid/widget/ListView;
          //   1266: bipush 8
          //   1268: invokevirtual 351	android/widget/ListView:setVisibility	(I)V
          //   1271: goto -433 -> 838
          //   1274: astore 17
          //   1276: goto -358 -> 918
          //   1279: astore 22
          //   1281: goto -238 -> 1043
          //   1284: astore 21
          //   1286: goto -256 -> 1030
          //   1289: astore 20
          //   1291: goto -299 -> 992
          //   1294: astore 19
          //   1296: goto -317 -> 979
          //   1299: astore 18
          //   1301: goto -370 -> 931
          //   1304: astore 12
          //   1306: goto -597 -> 709
          //   1309: astore 41
          //   1311: goto -623 -> 688
          //   1314: astore 43
          //   1316: goto -642 -> 674
          //   1319: astore 42
          //   1321: goto -662 -> 659
          //   1324: astore 51
          //   1326: goto -708 -> 618
          //   1329: astore 50
          //   1331: goto -728 -> 603
          //   1334: astore 49
          //   1336: goto -751 -> 585
          //   1339: astore 48
          //   1341: goto -774 -> 567
          //   1344: astore 47
          //   1346: goto -797 -> 549
          //   1349: astore 46
          //   1351: goto -820 -> 531
          //   1354: astore 45
          //   1356: goto -843 -> 513
          //   1359: astore 35
          //   1361: goto -897 -> 464
          //   1364: astore 33
          //   1366: goto -934 -> 432
          //   1369: astore 32
          //   1371: goto -1091 -> 280
          //   1374: astore 31
          //   1376: goto -1107 -> 269
          //   1379: astore 30
          //   1381: goto -1129 -> 252
          //   1384: astore 11
          //   1386: goto -1157 -> 229
          //   1389: astore 10
          //   1391: goto -1237 -> 154
          //   1394: astore 6
          //   1396: goto -1320 -> 76
          //   1399: astore 4
          //   1401: goto -1363 -> 38
          //
          // Exception table:
          //   from	to	target	type
          //   464	472	871	org/json/JSONException
          //   626	635	879	org/json/JSONException
          //   756	803	1144	org/json/JSONException
          //   1115	1141	1144	org/json/JSONException
          //   905	914	1274	org/json/JSONException
          //   1030	1043	1279	org/json/JSONException
          //   992	1030	1284	org/json/JSONException
          //   979	992	1289	org/json/JSONException
          //   966	979	1294	org/json/JSONException
          //   918	931	1299	org/json/JSONException
          //   696	705	1304	org/json/JSONException
          //   674	688	1309	org/json/JSONException
          //   659	674	1314	org/json/JSONException
          //   644	659	1319	org/json/JSONException
          //   603	618	1324	org/json/JSONException
          //   585	603	1329	org/json/JSONException
          //   567	585	1334	org/json/JSONException
          //   549	567	1339	org/json/JSONException
          //   531	549	1344	org/json/JSONException
          //   513	531	1349	org/json/JSONException
          //   495	513	1354	org/json/JSONException
          //   451	464	1359	org/json/JSONException
          //   389	432	1364	org/json/JSONException
          //   269	280	1369	org/json/JSONException
          //   252	269	1374	org/json/JSONException
          //   240	248	1379	org/json/JSONException
          //   154	229	1384	org/json/JSONException
          //   142	150	1389	org/json/JSONException
          //   64	72	1394	org/json/JSONException
          //   27	35	1399	org/json/JSONException
        }
      });
      ExecuteRequestAsyncTask localExecuteRequestAsyncTask = this.task;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/weekly-shop/" + this.weeklyShopId, "?page_no=" + this.page);
      localExecuteRequestAsyncTask.execute(arrayOfString);
    }
  }

  private void refreshActivity()
  {
    FabHelper.smoothScroll(this.listView);
    this.timer.setVisibility(4);
    FabHelper.stopAndRestartCartTimer(getApplicationContext(), 0L, this.mHandler);
    this.listView.setVisibility(8);
    this.error.setVisibility(8);
    this.progressBar.setVisibility(0);
    loadSales();
  }

  private void showAlert(String paramString)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.context);
    localBuilder.setIcon(2130837504);
    localBuilder.setTitle("Fab");
    localBuilder.setMessage(paramString);
    localBuilder.setPositiveButton("Ok", null);
    localBuilder.show();
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
    setContentView(2130903107);
    isActivityAlive = true;
    this.context = this;
    NotificationManager localNotificationManager = (NotificationManager)getSystemService("notification");
    localNotificationManager.cancel(2131165198);
    localNotificationManager.cancel(2131165199);
    if (!FabUtils.isLoggedIn(this.context))
    {
      finish();
      Intent localIntent = new Intent(this.context, FabIndex.class);
      localIntent.setFlags(67108864);
      startActivity(localIntent);
    }
    this.timer = ((FabTextView)findViewById(2131361795));
    this.timer.setFont("georgiai.ttf");
    this.listView = ((ListView)findViewById(2131362031));
    this.listView.setDividerHeight(0);
    this.theSales = new ArrayList();
    this.hasSetHeader = false;
    this.error = ((ErrorMessage)findViewById(2131361811));
    FabSharedPrefs.initializePreferences(this.context);
    this.alertbox = new AlertDialog.Builder(this);
    this.listView.setSelector(17170445);
    this.progressBar = ((ProgressBar)findViewById(2131361874));
    this.headerView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903108, null);
    this.headerView.setVisibility(8);
    this.shopIcon = ((ImageView)this.headerView.findViewById(2131362084));
    this.shopName = ((FabTextView)this.headerView.findViewById(2131362059));
    this.shopName.setFont("HelveticaNeu_bold.ttf");
    this.shopText = ((FabTextView)this.headerView.findViewById(2131362090));
    this.shopText.setFont("HelveticaNeu_bold.ttf");
    this.hideText = ((FabTextView)this.headerView.findViewById(2131362026));
    this.hideText.setFont("georgiai.ttf");
    this.shopDescription = ((WebView)this.headerView.findViewById(2131362092));
    this.shopDescriptionHolder = ((RelativeLayout)this.headerView.findViewById(2131362091));
    this.infoIconTop = ((ImageView)this.headerView.findViewById(2131362089));
    this.infoIconTop.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WeeklyShopDetailsActivity.this.shopDescriptionHolder.setVisibility(0);
        WeeklyShopDetailsActivity.this.infoIconTop.setVisibility(4);
      }
    });
    this.infoIconBottom = ((ImageView)this.headerView.findViewById(2131362093));
    this.infoIconBottom.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WeeklyShopDetailsActivity.this.shopDescriptionHolder.setVisibility(8);
        WeeklyShopDetailsActivity.this.infoIconTop.setVisibility(0);
      }
    });
    this.listView.addHeaderView(this.headerView);
    this.footerView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903067, null);
    this.footerView.setVisibility(8);
    this.listView.addFooterView(this.footerView);
    this.listView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        FabUtils.log("WEEKLY_SHOPS_ACTIVTY", "firstVisibleItem=" + paramAnonymousInt1 + "::visibleItemCount=" + paramAnonymousInt2 + "::totalItemCount=" + paramAnonymousInt3);
        int i = paramAnonymousInt1 + paramAnonymousInt2;
        if ((i != paramAnonymousInt3) || ((i == paramAnonymousInt3) && (!WeeklyShopDetailsActivity.this.loadingMore) && (WeeklyShopDetailsActivity.this.nextPageExists)))
          WeeklyShopDetailsActivity.this.loadSales();
      }

      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
      }
    });
    this.loadingMore = false;
    this.nextPageExists = false;
    this.page = 1;
    this.extras = getIntent().getExtras();
    if (this.extras != null)
      this.weeklyShopId = this.extras.getInt("WEEKLY_SHOP_ID");
    titleWidget = (TitleWidget)findViewById(2131361810);
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("weeklyShopDetails/" + this.weeklyShopId));
    this.adapter = new SalesAdapter(this.context, this.theSales);
    this.listView.setAdapter(this.adapter);
    loadSales();
    this.mHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Bundle localBundle = paramAnonymousMessage.getData();
        if (FabUtils.isLogEnabled())
          FabUtils.log("WEEKLY_SHOPS_ACTIVTY", localBundle.toString());
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
            WeeklyShopDetailsActivity.this.timer.setVisibility(4);
          if (WeeklyShopDetailsActivity.isActivityAlive)
          {
            AlertDialog.Builder localBuilder = WeeklyShopDetailsActivity.this.alertbox;
            if (localBundle.getInt("Error") == 1);
            for (int j = 2130837504; ; j = 2130837507)
            {
              localBuilder.setIcon(j);
              WeeklyShopDetailsActivity.this.alertbox.setTitle(localBundle.getString("Title"));
              WeeklyShopDetailsActivity.this.alertbox.setMessage(localBundle.getString("Message"));
              WeeklyShopDetailsActivity.this.alertbox.setNeutralButton("OK", null);
              if (!WeeklyShopDetailsActivity.isActivityAlive)
                break;
              WeeklyShopDetailsActivity.this.alertbox.show();
              break;
            }
            WeeklyShopDetailsActivity.this.timer.setVisibility(0);
            WeeklyShopDetailsActivity.this.timer.setText(str);
          }
        }
      }
    };
    this.headerCartButton = titleWidget.getCartButton();
  }

  protected void onDestroy()
  {
    this.tracker.stopSession();
    isActivityAlive = false;
    super.onDestroy();
  }

  public void onLowMemory()
  {
    System.gc();
    if (this.adapter != null)
    {
      this.adapter.downloadTheImages();
      this.adapter.notifyDataSetChanged();
    }
    super.onLowMemory();
  }

  protected void onPause()
  {
    isActivityAlive = false;
    if (this.adapter != null)
    {
      this.adapter.stopImageDownloads();
      this.adapter.clearImageCache();
    }
    super.onPause();
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131296256, paramMenu);
    MenuItem localMenuItem1 = paramMenu.findItem(2131361948);
    localMenuItem1.setVisible(true);
    localMenuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(WeeklyShopDetailsActivity.this.context, MoreActivity.class);
        WeeklyShopDetailsActivity.this.startActivity(localIntent);
        return false;
      }
    });
    MenuItem localMenuItem2 = paramMenu.findItem(2131362109);
    localMenuItem2.setVisible(true);
    localMenuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(WeeklyShopDetailsActivity.this.context, FabWebViewActivity.class);
        WeeklyShopDetailsActivity.this.startActivity(localIntent);
        return false;
      }
    });
    MenuItem localMenuItem3 = paramMenu.findItem(2131361908);
    localMenuItem3.setVisible(true);
    localMenuItem3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        if (FabSharedPrefs.isGuestUser())
          FabUtils.showGuestAlert(WeeklyShopDetailsActivity.this.context, WeeklyShopDetailsActivity.this.context.getResources().getString(2131165208), WeeklyShopDetailsActivity.this.context.getResources().getString(2131165209), false);
        while (true)
        {
          return false;
          Intent localIntent = new Intent(WeeklyShopDetailsActivity.this.context, InviteActivity.class);
          WeeklyShopDetailsActivity.this.startActivity(localIntent);
        }
      }
    });
    MenuItem localMenuItem4 = paramMenu.findItem(2131362103);
    localMenuItem4.setVisible(true);
    localMenuItem4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        WeeklyShopDetailsActivity.this.refreshActivity();
        return false;
      }
    });
    MenuItem localMenuItem5 = paramMenu.findItem(2131362033);
    localMenuItem5.setVisible(true);
    localMenuItem5.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        if (FabSharedPrefs.isGuestUser())
          FabUtils.showGuestAlert(WeeklyShopDetailsActivity.this.context, WeeklyShopDetailsActivity.this.context.getResources().getString(2131165208), WeeklyShopDetailsActivity.this.context.getResources().getString(2131165213), false);
        return false;
      }
    });
    if (!FabSharedPrefs.isGuestUser())
    {
      this.fbshareItem = paramMenu.findItem(2131362104);
      this.fbshareItem.setVisible(true);
      this.fbshareItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
      {
        public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
        {
          if (WeeklyShopDetailsActivity.this.fbShareData != null)
          {
            WeeklyShopDetailsActivity.this.fbClient = new FabFBClient(WeeklyShopDetailsActivity.this.context);
            WeeklyShopDetailsActivity.this.fbClient.setFBShareData(WeeklyShopDetailsActivity.this.fbShareData);
            WeeklyShopDetailsActivity.this.fbClient.setHasFBShareToken(WeeklyShopDetailsActivity.this.hasFBShareToken);
            WeeklyShopDetailsActivity.this.fbClient.sendRequest("publish_stream");
          }
          return false;
        }
      });
      MenuItem localMenuItem6 = paramMenu.findItem(2131362105);
      localMenuItem6.setVisible(true);
      localMenuItem6.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
      {
        public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
        {
          if (WeeklyShopDetailsActivity.this.twShareText != null)
          {
            Intent localIntent = new Intent(WeeklyShopDetailsActivity.this.context, FabTwitterClient.class);
            localIntent.putExtra("TWEET_TEXT", WeeklyShopDetailsActivity.this.twShareText);
            WeeklyShopDetailsActivity.this.startActivity(localIntent);
          }
          return false;
        }
      });
      MenuItem localMenuItem7 = paramMenu.findItem(2131362107);
      localMenuItem7.setVisible(true);
      localMenuItem7.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
      {
        public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
        {
          if (WeeklyShopDetailsActivity.this.emailBody != null)
          {
            Intent localIntent = new Intent("android.intent.action.SEND");
            localIntent.setType("text/html");
            localIntent.putExtra("android.intent.extra.SUBJECT", WeeklyShopDetailsActivity.this.emailSubject);
            localIntent.putExtra("android.intent.extra.TEXT", Html.fromHtml(WeeklyShopDetailsActivity.this.emailBody));
            WeeklyShopDetailsActivity.this.startActivity(localIntent);
          }
          return false;
        }
      });
      MenuItem localMenuItem8 = paramMenu.findItem(2131362108);
      localMenuItem8.setVisible(true);
      localMenuItem8.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
      {
        public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
        {
          if (WeeklyShopDetailsActivity.this.smsShare != null)
          {
            Intent localIntent = new Intent("android.intent.action.VIEW");
            localIntent.setType("vnd.android-dir/mms-sms");
            localIntent.setData(Uri.parse("sms:"));
            localIntent.putExtra("sms_body", WeeklyShopDetailsActivity.this.smsShare);
            WeeklyShopDetailsActivity.this.startActivity(localIntent);
          }
          return false;
        }
      });
    }
    return true;
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
    if (((FabApplication)getApplication()).isRefreshActivityOnRestart())
    {
      ((FabApplication)getApplication()).setRefreshActivityOnRestart(false);
      refreshActivity();
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
    isActivityAlive = true;
    this.error.setVisibility(8);
    FabHelper.stopAndRestartCartTimer(getApplicationContext(), FabHelper.getCartTime(), this.mHandler);
    updateCartCounter(FabHelper.getCartCount());
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
 * Qualified Name:     com.fab.sale.WeeklyShopDetailsActivity
 * JD-Core Version:    0.6.2
 */