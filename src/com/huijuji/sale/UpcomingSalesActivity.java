package com.fab.sale;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.fab.FabApplication;
import com.fab.FabHome;
import com.fab.FabIndex;
import com.fab.InviteActivity;
import com.fab.MoreActivity;
import com.fab.sale.bo.UpcomingSalesBO;
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
import java.util.Calendar;

public class UpcomingSalesActivity extends Activity
{
  private static final String TAG = "UPCOMING_SALES_ACTIVTY";
  protected static boolean isActivityAlive;
  public int activityLoading = 0;
  private AlertDialog.Builder alertbox;
  private Context context;
  private ErrorMessage error;
  private Bundle extras;
  private View footerView;
  private FabButton headerCartButton;
  private boolean isTabBuilding;
  private ListView listView;
  protected boolean loadingMore;
  private Handler mHandler;
  public boolean nextPageExists;
  public long nextSaleTs;
  private int page;
  private ProgressBar progressBar;
  private ExecuteRequestAsyncTask task;
  private ArrayList<UpcomingSalesBO> theSaleDates;
  private FabTextView timer;
  private GoogleAnalyticsTracker tracker;
  private UpcomingSalesAdapter upcomingAdapter;

  private void loadSales()
  {
    FabHome.titleWidget.getFabLogo().setEnabled(false);
    if (this.page < 1)
      this.page = 1;
    if ((this.task == null) || ((this.task != null) && (this.task.getStatus() != AsyncTask.Status.RUNNING)))
    {
      this.task = new ExecuteRequestAsyncTask(this.context, "GET");
      this.activityLoading = 1;
      this.task.setTaskCompleteListener(new TaskCompleteListener()
      {
        public void onTaskComplete()
        {
        }

        // ERROR //
        public void onTaskComplete(Object paramAnonymousObject)
        {
          // Byte code:
          //   0: aload_1
          //   1: ifnull +958 -> 959
          //   4: aload_1
          //   5: instanceof 26
          //   8: ifeq +951 -> 959
          //   11: aload_1
          //   12: checkcast 26	org/json/JSONObject
          //   15: astore_2
          //   16: aload_0
          //   17: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   20: iconst_2
          //   21: putfield 30	com/fab/sale/UpcomingSalesActivity:activityLoading	I
          //   24: aconst_null
          //   25: astore_3
          //   26: ldc 32
          //   28: aload_2
          //   29: invokevirtual 36	org/json/JSONObject:toString	()Ljava/lang/String;
          //   32: invokestatic 42	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
          //   35: iconst_0
          //   36: istore 4
          //   38: aload_2
          //   39: ldc 44
          //   41: invokevirtual 48	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   44: istore 54
          //   46: iload 54
          //   48: istore 4
          //   50: getstatic 54	com/fab/FabHome:titleWidget	Lcom/fab/views/TitleWidget;
          //   53: invokevirtual 60	com/fab/views/TitleWidget:getFabLogo	()Landroid/widget/ImageView;
          //   56: iconst_1
          //   57: invokevirtual 66	android/widget/ImageView:setEnabled	(Z)V
          //   60: aload_0
          //   61: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   64: aload_2
          //   65: ldc 68
          //   67: invokevirtual 72	org/json/JSONObject:getLong	(Ljava/lang/String;)J
          //   70: putfield 76	com/fab/sale/UpcomingSalesActivity:nextSaleTs	J
          //   73: aload_0
          //   74: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   77: getfield 76	com/fab/sale/UpcomingSalesActivity:nextSaleTs	J
          //   80: invokestatic 82	com/fab/utils/FabSharedPrefs:setNextSaleTs	(J)V
          //   83: iload 4
          //   85: ifeq +21 -> 106
          //   88: aload_0
          //   89: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   92: invokestatic 86	com/fab/sale/UpcomingSalesActivity:access$4	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/content/Context;
          //   95: aconst_null
          //   96: invokestatic 90	com/fab/utils/FabUtils:logoutUser	(Landroid/content/Context;Landroid/os/Handler;)V
          //   99: aload_0
          //   100: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   103: invokevirtual 93	com/fab/sale/UpcomingSalesActivity:finish	()V
          //   106: ldc 95
          //   108: astore 7
          //   110: aload_2
          //   111: ldc 97
          //   113: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   116: astore 53
          //   118: aload 53
          //   120: astore 7
          //   122: aload_0
          //   123: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   126: invokestatic 105	com/fab/sale/UpcomingSalesActivity:access$5	(Lcom/fab/sale/UpcomingSalesActivity;)I
          //   129: iconst_1
          //   130: if_icmpne +23 -> 153
          //   133: aload_0
          //   134: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   137: invokestatic 109	com/fab/sale/UpcomingSalesActivity:access$6	(Lcom/fab/sale/UpcomingSalesActivity;)Ljava/util/ArrayList;
          //   140: ifnull +310 -> 450
          //   143: aload_0
          //   144: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   147: invokestatic 109	com/fab/sale/UpcomingSalesActivity:access$6	(Lcom/fab/sale/UpcomingSalesActivity;)Ljava/util/ArrayList;
          //   150: invokevirtual 114	java/util/ArrayList:clear	()V
          //   153: aload 7
          //   155: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   158: ifeq +693 -> 851
          //   161: new 122	org/json/JSONArray
          //   164: dup
          //   165: invokespecial 123	org/json/JSONArray:<init>	()V
          //   168: astore 9
          //   170: new 26	org/json/JSONObject
          //   173: dup
          //   174: invokespecial 124	org/json/JSONObject:<init>	()V
          //   177: astore 10
          //   179: new 122	org/json/JSONArray
          //   182: dup
          //   183: invokespecial 123	org/json/JSONArray:<init>	()V
          //   186: astore 11
          //   188: new 122	org/json/JSONArray
          //   191: dup
          //   192: invokespecial 123	org/json/JSONArray:<init>	()V
          //   195: astore 12
          //   197: new 26	org/json/JSONObject
          //   200: dup
          //   201: invokespecial 124	org/json/JSONObject:<init>	()V
          //   204: astore 13
          //   206: aload_2
          //   207: ldc 126
          //   209: invokevirtual 130	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   212: astore 52
          //   214: aload 52
          //   216: astore 13
          //   218: aload_0
          //   219: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   222: aload 13
          //   224: ldc 132
          //   226: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   229: invokevirtual 140	com/fab/sale/UpcomingSalesActivity:updateCartCounter	(I)V
          //   232: aload 13
          //   234: ldc 142
          //   236: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   239: i2l
          //   240: new 144	java/util/GregorianCalendar
          //   243: dup
          //   244: invokespecial 145	java/util/GregorianCalendar:<init>	()V
          //   247: invokestatic 149	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
          //   250: ldc2_w 150
          //   253: lcmp
          //   254: iflt +39 -> 293
          //   257: aload 13
          //   259: ldc 142
          //   261: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   264: i2l
          //   265: invokestatic 156	com/fab/utils/FabHelper:setCartTime	(J)V
          //   268: aload_0
          //   269: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   272: invokevirtual 160	com/fab/sale/UpcomingSalesActivity:getApplicationContext	()Landroid/content/Context;
          //   275: aload 13
          //   277: ldc 142
          //   279: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   282: i2l
          //   283: aload_0
          //   284: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   287: invokestatic 164	com/fab/sale/UpcomingSalesActivity:access$8	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/os/Handler;
          //   290: invokestatic 168	com/fab/utils/FabHelper:stopAndRestartCartTimer	(Landroid/content/Context;JLandroid/os/Handler;)V
          //   293: aload_2
          //   294: ldc 170
          //   296: invokevirtual 130	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   299: astore 51
          //   301: aload 51
          //   303: astore 10
          //   305: aload_2
          //   306: ldc 172
          //   308: invokevirtual 176	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   311: astore 50
          //   313: aload 50
          //   315: astore 11
          //   317: ldc 95
          //   319: astore 18
          //   321: iconst_0
          //   322: istore 19
          //   324: aload 11
          //   326: invokevirtual 180	org/json/JSONArray:length	()I
          //   329: istore 20
          //   331: iload 19
          //   333: iload 20
          //   335: if_icmplt +132 -> 467
          //   338: aload_0
          //   339: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   342: invokestatic 184	com/fab/sale/UpcomingSalesActivity:access$9	(Lcom/fab/sale/UpcomingSalesActivity;)Lcom/fab/sale/UpcomingSalesAdapter;
          //   345: ifnull +23 -> 368
          //   348: aload_0
          //   349: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   352: invokestatic 184	com/fab/sale/UpcomingSalesActivity:access$9	(Lcom/fab/sale/UpcomingSalesActivity;)Lcom/fab/sale/UpcomingSalesAdapter;
          //   355: invokevirtual 189	com/fab/sale/UpcomingSalesAdapter:downloadTheImages	()V
          //   358: aload_0
          //   359: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   362: invokestatic 184	com/fab/sale/UpcomingSalesActivity:access$9	(Lcom/fab/sale/UpcomingSalesActivity;)Lcom/fab/sale/UpcomingSalesAdapter;
          //   365: invokevirtual 192	com/fab/sale/UpcomingSalesAdapter:notifyDataSetChanged	()V
          //   368: aload_2
          //   369: ldc 194
          //   371: invokevirtual 48	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   374: ifeq +500 -> 874
          //   377: aload_0
          //   378: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   381: iconst_1
          //   382: putfield 198	com/fab/sale/UpcomingSalesActivity:nextPageExists	Z
          //   385: aload_0
          //   386: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   389: astore 49
          //   391: aload 49
          //   393: iconst_1
          //   394: aload 49
          //   396: invokestatic 105	com/fab/sale/UpcomingSalesActivity:access$5	(Lcom/fab/sale/UpcomingSalesActivity;)I
          //   399: iadd
          //   400: invokestatic 202	com/fab/sale/UpcomingSalesActivity:access$10	(Lcom/fab/sale/UpcomingSalesActivity;I)V
          //   403: aload_0
          //   404: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   407: invokestatic 206	com/fab/sale/UpcomingSalesActivity:access$11	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/view/View;
          //   410: iconst_0
          //   411: invokevirtual 211	android/view/View:setVisibility	(I)V
          //   414: aload_0
          //   415: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   418: invokestatic 215	com/fab/sale/UpcomingSalesActivity:access$12	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/widget/ListView;
          //   421: iconst_0
          //   422: invokevirtual 218	android/widget/ListView:setVisibility	(I)V
          //   425: aload_0
          //   426: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   429: invokestatic 222	com/fab/sale/UpcomingSalesActivity:access$13	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/widget/ProgressBar;
          //   432: bipush 8
          //   434: invokevirtual 225	android/widget/ProgressBar:setVisibility	(I)V
          //   437: aload_0
          //   438: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   441: invokestatic 229	com/fab/sale/UpcomingSalesActivity:access$14	(Lcom/fab/sale/UpcomingSalesActivity;)Lcom/fab/views/ErrorMessage;
          //   444: bipush 8
          //   446: invokevirtual 232	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   449: return
          //   450: aload_0
          //   451: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   454: new 111	java/util/ArrayList
          //   457: dup
          //   458: invokespecial 233	java/util/ArrayList:<init>	()V
          //   461: invokestatic 237	com/fab/sale/UpcomingSalesActivity:access$7	(Lcom/fab/sale/UpcomingSalesActivity;Ljava/util/ArrayList;)V
          //   464: goto -311 -> 153
          //   467: aload 11
          //   469: iload 19
          //   471: invokevirtual 240	org/json/JSONArray:getString	(I)Ljava/lang/String;
          //   474: astore 45
          //   476: aload 45
          //   478: astore 18
          //   480: aload 10
          //   482: aload 18
          //   484: invokevirtual 176	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   487: astore 44
          //   489: aload 44
          //   491: astore 12
          //   493: iconst_0
          //   494: istore 23
          //   496: aload 12
          //   498: invokevirtual 180	org/json/JSONArray:length	()I
          //   501: istore 24
          //   503: iload 23
          //   505: iload 24
          //   507: if_icmplt +9 -> 516
          //   510: iinc 19 1
          //   513: goto -182 -> 331
          //   516: new 242	com/fab/sale/bo/UpcomingSalesBO
          //   519: dup
          //   520: invokespecial 243	com/fab/sale/bo/UpcomingSalesBO:<init>	()V
          //   523: astore 25
          //   525: iload 23
          //   527: ifne +170 -> 697
          //   530: aload 25
          //   532: iconst_1
          //   533: invokevirtual 246	com/fab/sale/bo/UpcomingSalesBO:setHeader	(Z)V
          //   536: aload 25
          //   538: iconst_0
          //   539: invokevirtual 249	com/fab/sale/bo/UpcomingSalesBO:setSubHeader	(Z)V
          //   542: aload 25
          //   544: aload 18
          //   546: invokevirtual 253	com/fab/sale/bo/UpcomingSalesBO:setDateSeparator	(Ljava/lang/String;)V
          //   549: aload 25
          //   551: aload 12
          //   553: iload 23
          //   555: invokevirtual 256	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   558: ldc_w 258
          //   561: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   564: invokevirtual 261	com/fab/sale/bo/UpcomingSalesBO:setSaleTypeHeader	(Ljava/lang/String;)V
          //   567: aload 12
          //   569: iload 23
          //   571: invokevirtual 256	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   574: ldc_w 263
          //   577: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   580: astore 42
          //   582: aload 42
          //   584: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   587: ifne +38 -> 625
          //   590: aload_0
          //   591: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   594: invokestatic 86	com/fab/sale/UpcomingSalesActivity:access$4	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/content/Context;
          //   597: invokevirtual 269	android/content/Context:getResources	()Landroid/content/res/Resources;
          //   600: aload 42
          //   602: ldc_w 271
          //   605: ldc_w 273
          //   608: invokevirtual 279	android/content/res/Resources:getIdentifier	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
          //   611: istore 43
          //   613: iload 43
          //   615: ifle +10 -> 625
          //   618: aload 25
          //   620: iload 43
          //   622: invokevirtual 282	com/fab/sale/bo/UpcomingSalesBO:setSaleIconResId	(I)V
          //   625: aload 25
          //   627: aload 12
          //   629: iload 23
          //   631: invokevirtual 256	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   634: ldc_w 284
          //   637: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   640: invokevirtual 287	com/fab/sale/bo/UpcomingSalesBO:setRibbonText	(Ljava/lang/String;)V
          //   643: aload 12
          //   645: iload 23
          //   647: invokevirtual 256	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   650: ldc 170
          //   652: invokevirtual 176	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   655: astore 41
          //   657: aload 41
          //   659: astore 9
          //   661: aload_0
          //   662: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   665: invokestatic 109	com/fab/sale/UpcomingSalesActivity:access$6	(Lcom/fab/sale/UpcomingSalesActivity;)Ljava/util/ArrayList;
          //   668: aload 25
          //   670: invokevirtual 291	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   673: pop
          //   674: iconst_0
          //   675: istore 31
          //   677: aload 9
          //   679: invokevirtual 180	org/json/JSONArray:length	()I
          //   682: istore 32
          //   684: iload 31
          //   686: iload 32
          //   688: if_icmplt +24 -> 712
          //   691: iinc 23 1
          //   694: goto -191 -> 503
          //   697: aload 25
          //   699: iconst_0
          //   700: invokevirtual 246	com/fab/sale/bo/UpcomingSalesBO:setHeader	(Z)V
          //   703: aload 25
          //   705: iconst_1
          //   706: invokevirtual 249	com/fab/sale/bo/UpcomingSalesBO:setSubHeader	(Z)V
          //   709: goto -160 -> 549
          //   712: new 242	com/fab/sale/bo/UpcomingSalesBO
          //   715: dup
          //   716: invokespecial 243	com/fab/sale/bo/UpcomingSalesBO:<init>	()V
          //   719: astore 33
          //   721: iload 23
          //   723: iload 24
          //   725: bipush 255
          //   727: iadd
          //   728: if_icmpne +19 -> 747
          //   731: iload 31
          //   733: iload 32
          //   735: bipush 255
          //   737: iadd
          //   738: if_icmpne +9 -> 747
          //   741: aload 33
          //   743: iconst_1
          //   744: invokevirtual 294	com/fab/sale/bo/UpcomingSalesBO:setLastProduct	(Z)V
          //   747: aload 9
          //   749: iload 31
          //   751: invokevirtual 256	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   754: astore 40
          //   756: aload 40
          //   758: astore_3
          //   759: aload 33
          //   761: aload_3
          //   762: ldc_w 296
          //   765: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   768: invokevirtual 299	com/fab/sale/bo/UpcomingSalesBO:setSaleId	(I)V
          //   771: aload 33
          //   773: aload_3
          //   774: ldc_w 301
          //   777: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   780: invokevirtual 304	com/fab/sale/bo/UpcomingSalesBO:setSaleName	(Ljava/lang/String;)V
          //   783: aload 33
          //   785: aload_3
          //   786: ldc_w 306
          //   789: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   792: invokevirtual 309	com/fab/sale/bo/UpcomingSalesBO:setPrimaryTitle	(Ljava/lang/String;)V
          //   795: aload 33
          //   797: new 311	java/lang/StringBuilder
          //   800: dup
          //   801: ldc_w 313
          //   804: invokespecial 315	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   807: aload_3
          //   808: ldc_w 317
          //   811: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   814: ldc_w 319
          //   817: ldc_w 321
          //   820: invokevirtual 327	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   823: invokevirtual 331	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   826: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   829: invokevirtual 335	com/fab/sale/bo/UpcomingSalesBO:setSaleImageUrl	(Ljava/lang/String;)V
          //   832: aload_0
          //   833: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   836: invokestatic 109	com/fab/sale/UpcomingSalesActivity:access$6	(Lcom/fab/sale/UpcomingSalesActivity;)Ljava/util/ArrayList;
          //   839: aload 33
          //   841: invokevirtual 291	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   844: pop
          //   845: iinc 31 1
          //   848: goto -164 -> 684
          //   851: aload 7
          //   853: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   856: ifne -518 -> 338
          //   859: aload_0
          //   860: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   863: invokestatic 86	com/fab/sale/UpcomingSalesActivity:access$4	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/content/Context;
          //   866: aload 7
          //   868: invokestatic 339	com/fab/utils/FabUtils:showAlert	(Landroid/content/Context;Ljava/lang/String;)V
          //   871: goto -533 -> 338
          //   874: aload_0
          //   875: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   878: iconst_0
          //   879: putfield 198	com/fab/sale/UpcomingSalesActivity:nextPageExists	Z
          //   882: aload_0
          //   883: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   886: invokestatic 215	com/fab/sale/UpcomingSalesActivity:access$12	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/widget/ListView;
          //   889: aload_0
          //   890: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   893: invokestatic 206	com/fab/sale/UpcomingSalesActivity:access$11	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/view/View;
          //   896: invokevirtual 343	android/widget/ListView:removeFooterView	(Landroid/view/View;)Z
          //   899: pop
          //   900: goto -486 -> 414
          //   903: astore 46
          //   905: aload_0
          //   906: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   909: getfield 198	com/fab/sale/UpcomingSalesActivity:nextPageExists	Z
          //   912: ifne +24 -> 936
          //   915: aload_0
          //   916: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   919: invokestatic 215	com/fab/sale/UpcomingSalesActivity:access$12	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/widget/ListView;
          //   922: aload_0
          //   923: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   926: invokestatic 206	com/fab/sale/UpcomingSalesActivity:access$11	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/view/View;
          //   929: invokevirtual 343	android/widget/ListView:removeFooterView	(Landroid/view/View;)Z
          //   932: pop
          //   933: goto -519 -> 414
          //   936: aload_0
          //   937: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   940: iconst_0
          //   941: putfield 198	com/fab/sale/UpcomingSalesActivity:nextPageExists	Z
          //   944: aload_0
          //   945: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   948: invokestatic 206	com/fab/sale/UpcomingSalesActivity:access$11	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/view/View;
          //   951: bipush 8
          //   953: invokevirtual 211	android/view/View:setVisibility	(I)V
          //   956: goto -542 -> 414
          //   959: aload_0
          //   960: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   963: invokestatic 222	com/fab/sale/UpcomingSalesActivity:access$13	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/widget/ProgressBar;
          //   966: bipush 8
          //   968: invokevirtual 225	android/widget/ProgressBar:setVisibility	(I)V
          //   971: aload_0
          //   972: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   975: invokestatic 347	com/fab/sale/UpcomingSalesActivity:access$15	(Lcom/fab/sale/UpcomingSalesActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   978: invokevirtual 352	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   981: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   984: ifne -535 -> 449
          //   987: aload_0
          //   988: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   991: invokestatic 229	com/fab/sale/UpcomingSalesActivity:access$14	(Lcom/fab/sale/UpcomingSalesActivity;)Lcom/fab/views/ErrorMessage;
          //   994: iconst_0
          //   995: invokevirtual 232	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   998: aload_0
          //   999: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   1002: invokestatic 229	com/fab/sale/UpcomingSalesActivity:access$14	(Lcom/fab/sale/UpcomingSalesActivity;)Lcom/fab/views/ErrorMessage;
          //   1005: aload_0
          //   1006: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   1009: invokestatic 347	com/fab/sale/UpcomingSalesActivity:access$15	(Lcom/fab/sale/UpcomingSalesActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   1012: invokevirtual 352	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   1015: invokevirtual 355	com/fab/views/ErrorMessage:setErrorMsg	(Ljava/lang/String;)V
          //   1018: aload_0
          //   1019: getfield 18	com/fab/sale/UpcomingSalesActivity$10:this$0	Lcom/fab/sale/UpcomingSalesActivity;
          //   1022: invokestatic 215	com/fab/sale/UpcomingSalesActivity:access$12	(Lcom/fab/sale/UpcomingSalesActivity;)Landroid/widget/ListView;
          //   1025: bipush 8
          //   1027: invokevirtual 218	android/widget/ListView:setVisibility	(I)V
          //   1030: goto -581 -> 449
          //   1033: astore 21
          //   1035: goto -555 -> 480
          //   1038: astore 22
          //   1040: goto -547 -> 493
          //   1043: astore 34
          //   1045: goto -286 -> 759
          //   1048: astore 38
          //   1050: goto -218 -> 832
          //   1053: astore 37
          //   1055: goto -260 -> 795
          //   1058: astore 36
          //   1060: goto -277 -> 783
          //   1063: astore 35
          //   1065: goto -294 -> 771
          //   1068: astore 29
          //   1070: goto -409 -> 661
          //   1073: astore 28
          //   1075: goto -432 -> 643
          //   1078: astore 27
          //   1080: goto -455 -> 625
          //   1083: astore 26
          //   1085: goto -518 -> 567
          //   1088: astore 17
          //   1090: goto -773 -> 317
          //   1093: astore 16
          //   1095: goto -790 -> 305
          //   1098: astore 15
          //   1100: goto -807 -> 293
          //   1103: astore 14
          //   1105: goto -887 -> 218
          //   1108: astore 8
          //   1110: goto -988 -> 122
          //   1113: astore 6
          //   1115: goto -1032 -> 83
          //   1118: astore 5
          //   1120: goto -1070 -> 50
          //
          // Exception table:
          //   from	to	target	type
          //   368	414	903	org/json/JSONException
          //   874	900	903	org/json/JSONException
          //   467	476	1033	org/json/JSONException
          //   480	489	1038	org/json/JSONException
          //   747	756	1043	org/json/JSONException
          //   795	832	1048	org/json/JSONException
          //   783	795	1053	org/json/JSONException
          //   771	783	1058	org/json/JSONException
          //   759	771	1063	org/json/JSONException
          //   643	657	1068	org/json/JSONException
          //   625	643	1073	org/json/JSONException
          //   567	625	1078	org/json/JSONException
          //   549	567	1083	org/json/JSONException
          //   305	313	1088	org/json/JSONException
          //   293	301	1093	org/json/JSONException
          //   218	293	1098	org/json/JSONException
          //   206	214	1103	org/json/JSONException
          //   110	118	1108	org/json/JSONException
          //   60	83	1113	org/json/JSONException
          //   38	46	1118	org/json/JSONException
        }
      });
      ExecuteRequestAsyncTask localExecuteRequestAsyncTask = this.task;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/upcoming-sales/", "?page_no=" + this.page);
      localExecuteRequestAsyncTask.execute(arrayOfString);
    }
  }

  private void refreshActivity()
  {
    this.page = 1;
    FabHelper.smoothScroll(this.listView);
    this.timer.setVisibility(4);
    FabHelper.stopAndRestartCartTimer(getApplicationContext(), 0L, this.mHandler);
    this.listView.setVisibility(8);
    this.error.setVisibility(8);
    this.progressBar.setVisibility(0);
    loadSales();
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903087);
    isActivityAlive = true;
    this.context = this;
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
    this.theSaleDates = new ArrayList();
    this.error = ((ErrorMessage)findViewById(2131361811));
    FabSharedPrefs.initializePreferences(this.context);
    this.alertbox = new AlertDialog.Builder(this);
    this.listView.setSelector(17170445);
    this.progressBar = ((ProgressBar)findViewById(2131361874));
    this.footerView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903067, null);
    this.footerView.setVisibility(8);
    this.listView.addFooterView(this.footerView);
    this.listView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        int i = paramAnonymousInt1 + paramAnonymousInt2;
        if ((i != paramAnonymousInt3) || ((i == paramAnonymousInt3) && (!UpcomingSalesActivity.this.loadingMore) && (UpcomingSalesActivity.this.nextPageExists)))
        {
          if (!Calendar.getInstance().after(Long.valueOf(FabSharedPrefs.getNextSaleTs())))
            break label62;
          UpcomingSalesActivity.this.refreshActivity();
        }
        while (true)
        {
          return;
          label62: UpcomingSalesActivity.this.loadSales();
        }
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
      this.isTabBuilding = this.extras.getBoolean("TAB_BUILDING");
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("salesLanding/upcoming"));
    this.upcomingAdapter = new UpcomingSalesAdapter(this.context, this.theSaleDates);
    this.listView.setAdapter(this.upcomingAdapter);
    FabHome.titleWidget.getFabLogo().setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (UpcomingSalesActivity.this.activityLoading == 2)
          UpcomingSalesActivity.this.refreshActivity();
      }
    });
    if (!this.isTabBuilding)
      loadSales();
    this.mHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Bundle localBundle = paramAnonymousMessage.getData();
        if (FabUtils.isLogEnabled())
          FabUtils.log("UPCOMING_SALES_ACTIVTY", localBundle.toString());
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
            UpcomingSalesActivity.this.timer.setVisibility(4);
          if (UpcomingSalesActivity.isActivityAlive)
          {
            AlertDialog.Builder localBuilder = UpcomingSalesActivity.this.alertbox;
            if (localBundle.getInt("Error") == 1);
            for (int j = 2130837504; ; j = 2130837507)
            {
              localBuilder.setIcon(j);
              UpcomingSalesActivity.this.alertbox.setTitle(localBundle.getString("Title"));
              UpcomingSalesActivity.this.alertbox.setMessage(localBundle.getString("Message"));
              UpcomingSalesActivity.this.alertbox.setNeutralButton("OK", null);
              if (!UpcomingSalesActivity.isActivityAlive)
                break;
              UpcomingSalesActivity.this.alertbox.show();
              break;
            }
            UpcomingSalesActivity.this.timer.setVisibility(0);
            UpcomingSalesActivity.this.timer.setText(str);
          }
        }
      }
    };
    this.headerCartButton = FabHome.titleWidget.getCartButton();
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131296256, paramMenu);
    MenuItem localMenuItem1 = paramMenu.findItem(2131361948);
    localMenuItem1.setVisible(true);
    localMenuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(UpcomingSalesActivity.this.context, MoreActivity.class);
        UpcomingSalesActivity.this.startActivity(localIntent);
        return false;
      }
    });
    MenuItem localMenuItem2 = paramMenu.findItem(2131362109);
    localMenuItem2.setVisible(true);
    localMenuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(UpcomingSalesActivity.this.context, FabWebViewActivity.class);
        UpcomingSalesActivity.this.startActivity(localIntent);
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
          FabUtils.showGuestAlert(UpcomingSalesActivity.this.context, UpcomingSalesActivity.this.context.getResources().getString(2131165208), UpcomingSalesActivity.this.context.getResources().getString(2131165209), false);
        while (true)
        {
          return false;
          Intent localIntent = new Intent(UpcomingSalesActivity.this.context, InviteActivity.class);
          UpcomingSalesActivity.this.startActivity(localIntent);
        }
      }
    });
    MenuItem localMenuItem4 = paramMenu.findItem(2131362103);
    localMenuItem4.setVisible(true);
    localMenuItem4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        UpcomingSalesActivity.this.refreshActivity();
        return false;
      }
    });
    return true;
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
    if (this.upcomingAdapter != null)
    {
      this.upcomingAdapter.stopImageDownloads();
      this.upcomingAdapter.clearImageCache();
    }
    super.onLowMemory();
  }

  protected void onPause()
  {
    isActivityAlive = false;
    if (this.upcomingAdapter != null)
    {
      this.upcomingAdapter.stopImageDownloads();
      this.upcomingAdapter.clearImageCache();
    }
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
    FabHome.titleWidget.getFabLogo().setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (UpcomingSalesActivity.this.activityLoading == 2)
          UpcomingSalesActivity.this.refreshActivity();
      }
    });
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
    FabHome.titleWidget.getFabLogo().setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (UpcomingSalesActivity.this.activityLoading == 2)
          UpcomingSalesActivity.this.refreshActivity();
      }
    });
    isActivityAlive = true;
    try
    {
      if (Calendar.getInstance().after(Long.valueOf(FabSharedPrefs.getNextSaleTs())))
        refreshActivity();
      label82: this.error.setVisibility(8);
      if (this.theSaleDates.isEmpty())
      {
        this.page = 1;
        loadSales();
      }
      FabHelper.stopAndRestartCartTimer(getApplicationContext(), FabHelper.getCartTime(), this.mHandler);
      updateCartCounter(FabHelper.getCartCount());
      super.onResume();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      break label82;
    }
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
 * Qualified Name:     com.fab.sale.UpcomingSalesActivity
 * JD-Core Version:    0.6.2
 */