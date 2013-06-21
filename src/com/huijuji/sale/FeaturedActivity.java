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
import com.fab.sale.bo.PairedSales;
import com.fab.sale.bo.SalesBO;
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

public class FeaturedActivity extends Activity
{
  private static final String TAG = "FEATURED_TODAY";
  protected static boolean isActivityAlive;
  public int activityLoading = 0;
  private EndingSoonAdapter adapter;
  private AlertDialog.Builder alertbox;
  private Context context;
  private ErrorMessage error;
  private Bundle extras;
  private View footerView;
  private boolean hasSetHeader;
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
  protected ArrayList<PairedSales> theFeaturedPair;
  private ArrayList<SalesBO> theSales;
  private FabTextView timer;
  private GoogleAnalyticsTracker tracker;

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
          //   1: ifnull +940 -> 941
          //   4: aload_1
          //   5: instanceof 26
          //   8: ifeq +933 -> 941
          //   11: aload_1
          //   12: checkcast 26	org/json/JSONObject
          //   15: astore_2
          //   16: aload_0
          //   17: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   20: iconst_2
          //   21: putfield 30	com/fab/sale/FeaturedActivity:activityLoading	I
          //   24: ldc 32
          //   26: aload_2
          //   27: invokevirtual 36	org/json/JSONObject:toString	()Ljava/lang/String;
          //   30: invokestatic 42	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
          //   33: iconst_0
          //   34: istore_3
          //   35: aload_2
          //   36: ldc 44
          //   38: invokevirtual 48	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   41: istore 41
          //   43: iload 41
          //   45: istore_3
          //   46: getstatic 54	com/fab/FabHome:titleWidget	Lcom/fab/views/TitleWidget;
          //   49: invokevirtual 60	com/fab/views/TitleWidget:getFabLogo	()Landroid/widget/ImageView;
          //   52: iconst_1
          //   53: invokevirtual 66	android/widget/ImageView:setEnabled	(Z)V
          //   56: aload_0
          //   57: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   60: aload_2
          //   61: ldc 68
          //   63: invokevirtual 72	org/json/JSONObject:getLong	(Ljava/lang/String;)J
          //   66: putfield 76	com/fab/sale/FeaturedActivity:nextSaleTs	J
          //   69: aload_0
          //   70: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   73: getfield 76	com/fab/sale/FeaturedActivity:nextSaleTs	J
          //   76: invokestatic 82	com/fab/utils/FabSharedPrefs:setNextSaleTs	(J)V
          //   79: iload_3
          //   80: ifeq +21 -> 101
          //   83: aload_0
          //   84: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   87: invokestatic 86	com/fab/sale/FeaturedActivity:access$4	(Lcom/fab/sale/FeaturedActivity;)Landroid/content/Context;
          //   90: aconst_null
          //   91: invokestatic 90	com/fab/utils/FabUtils:logoutUser	(Landroid/content/Context;Landroid/os/Handler;)V
          //   94: aload_0
          //   95: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   98: invokevirtual 93	com/fab/sale/FeaturedActivity:finish	()V
          //   101: ldc 95
          //   103: astore 6
          //   105: aload_2
          //   106: ldc 97
          //   108: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   111: astore 40
          //   113: aload 40
          //   115: astore 6
          //   117: aload_0
          //   118: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   121: invokestatic 105	com/fab/sale/FeaturedActivity:access$5	(Lcom/fab/sale/FeaturedActivity;)I
          //   124: iconst_1
          //   125: if_icmpne +23 -> 148
          //   128: aload_0
          //   129: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   132: getfield 109	com/fab/sale/FeaturedActivity:theFeaturedPair	Ljava/util/ArrayList;
          //   135: ifnull +360 -> 495
          //   138: aload_0
          //   139: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   142: getfield 109	com/fab/sale/FeaturedActivity:theFeaturedPair	Ljava/util/ArrayList;
          //   145: invokevirtual 114	java/util/ArrayList:clear	()V
          //   148: aload 6
          //   150: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   153: ifeq +693 -> 846
          //   156: new 122	org/json/JSONArray
          //   159: dup
          //   160: invokespecial 123	org/json/JSONArray:<init>	()V
          //   163: astore 8
          //   165: new 26	org/json/JSONObject
          //   168: dup
          //   169: invokespecial 124	org/json/JSONObject:<init>	()V
          //   172: astore 9
          //   174: aload_2
          //   175: ldc 126
          //   177: invokevirtual 130	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   180: astore 39
          //   182: aload 39
          //   184: astore 9
          //   186: aload_0
          //   187: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   190: aload 9
          //   192: ldc 132
          //   194: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   197: invokevirtual 140	com/fab/sale/FeaturedActivity:updateCartCounter	(I)V
          //   200: aload 9
          //   202: ldc 142
          //   204: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   207: i2l
          //   208: new 144	java/util/GregorianCalendar
          //   211: dup
          //   212: invokespecial 145	java/util/GregorianCalendar:<init>	()V
          //   215: invokestatic 149	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
          //   218: ldc2_w 150
          //   221: lcmp
          //   222: iflt +39 -> 261
          //   225: aload 9
          //   227: ldc 142
          //   229: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   232: i2l
          //   233: invokestatic 156	com/fab/utils/FabHelper:setCartTime	(J)V
          //   236: aload_0
          //   237: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   240: invokevirtual 160	com/fab/sale/FeaturedActivity:getApplicationContext	()Landroid/content/Context;
          //   243: aload 9
          //   245: ldc 142
          //   247: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   250: i2l
          //   251: aload_0
          //   252: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   255: invokestatic 164	com/fab/sale/FeaturedActivity:access$6	(Lcom/fab/sale/FeaturedActivity;)Landroid/os/Handler;
          //   258: invokestatic 168	com/fab/utils/FabHelper:stopAndRestartCartTimer	(Landroid/content/Context;JLandroid/os/Handler;)V
          //   261: aload_0
          //   262: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   265: iconst_0
          //   266: invokestatic 172	com/fab/sale/FeaturedActivity:access$7	(Lcom/fab/sale/FeaturedActivity;Z)V
          //   269: aload_2
          //   270: ldc 174
          //   272: invokevirtual 178	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   275: astore 38
          //   277: aload 38
          //   279: astore 8
          //   281: aload_0
          //   282: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   285: invokestatic 182	com/fab/sale/FeaturedActivity:access$8	(Lcom/fab/sale/FeaturedActivity;)Ljava/util/ArrayList;
          //   288: ifnull +224 -> 512
          //   291: aload_0
          //   292: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   295: invokestatic 182	com/fab/sale/FeaturedActivity:access$8	(Lcom/fab/sale/FeaturedActivity;)Ljava/util/ArrayList;
          //   298: invokevirtual 114	java/util/ArrayList:clear	()V
          //   301: iconst_0
          //   302: istore 13
          //   304: aload 8
          //   306: invokevirtual 186	org/json/JSONArray:length	()I
          //   309: istore 14
          //   311: iload 13
          //   313: iload 14
          //   315: if_icmplt +214 -> 529
          //   318: aload_0
          //   319: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   322: invokestatic 182	com/fab/sale/FeaturedActivity:access$8	(Lcom/fab/sale/FeaturedActivity;)Ljava/util/ArrayList;
          //   325: invokevirtual 189	java/util/ArrayList:size	()I
          //   328: iconst_2
          //   329: irem
          //   330: iconst_1
          //   331: if_icmpne +30 -> 361
          //   334: aload_0
          //   335: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   338: invokestatic 182	com/fab/sale/FeaturedActivity:access$8	(Lcom/fab/sale/FeaturedActivity;)Ljava/util/ArrayList;
          //   341: aload_0
          //   342: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   345: invokestatic 182	com/fab/sale/FeaturedActivity:access$8	(Lcom/fab/sale/FeaturedActivity;)Ljava/util/ArrayList;
          //   348: invokevirtual 189	java/util/ArrayList:size	()I
          //   351: new 191	com/fab/sale/bo/SalesBO
          //   354: dup
          //   355: invokespecial 192	com/fab/sale/bo/SalesBO:<init>	()V
          //   358: invokevirtual 196	java/util/ArrayList:add	(ILjava/lang/Object;)V
          //   361: iconst_0
          //   362: istore 29
          //   364: aload_0
          //   365: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   368: invokestatic 182	com/fab/sale/FeaturedActivity:access$8	(Lcom/fab/sale/FeaturedActivity;)Ljava/util/ArrayList;
          //   371: invokevirtual 189	java/util/ArrayList:size	()I
          //   374: istore 30
          //   376: iload 29
          //   378: iload 30
          //   380: if_icmplt +389 -> 769
          //   383: aload_0
          //   384: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   387: invokestatic 200	com/fab/sale/FeaturedActivity:access$11	(Lcom/fab/sale/FeaturedActivity;)Lcom/fab/sale/EndingSoonAdapter;
          //   390: ifnull +23 -> 413
          //   393: aload_0
          //   394: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   397: invokestatic 200	com/fab/sale/FeaturedActivity:access$11	(Lcom/fab/sale/FeaturedActivity;)Lcom/fab/sale/EndingSoonAdapter;
          //   400: invokevirtual 205	com/fab/sale/EndingSoonAdapter:downloadTheImages	()V
          //   403: aload_0
          //   404: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   407: invokestatic 200	com/fab/sale/FeaturedActivity:access$11	(Lcom/fab/sale/FeaturedActivity;)Lcom/fab/sale/EndingSoonAdapter;
          //   410: invokevirtual 208	com/fab/sale/EndingSoonAdapter:notifyDataSetChanged	()V
          //   413: aload_2
          //   414: ldc 210
          //   416: invokevirtual 48	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   419: ifeq +450 -> 869
          //   422: aload_0
          //   423: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   426: iconst_1
          //   427: putfield 214	com/fab/sale/FeaturedActivity:nextPageExists	Z
          //   430: aload_0
          //   431: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   434: astore 37
          //   436: aload 37
          //   438: iconst_1
          //   439: aload 37
          //   441: invokestatic 105	com/fab/sale/FeaturedActivity:access$5	(Lcom/fab/sale/FeaturedActivity;)I
          //   444: iadd
          //   445: invokestatic 218	com/fab/sale/FeaturedActivity:access$12	(Lcom/fab/sale/FeaturedActivity;I)V
          //   448: aload_0
          //   449: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   452: invokestatic 222	com/fab/sale/FeaturedActivity:access$13	(Lcom/fab/sale/FeaturedActivity;)Landroid/view/View;
          //   455: iconst_0
          //   456: invokevirtual 227	android/view/View:setVisibility	(I)V
          //   459: aload_0
          //   460: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   463: invokestatic 231	com/fab/sale/FeaturedActivity:access$14	(Lcom/fab/sale/FeaturedActivity;)Landroid/widget/ListView;
          //   466: iconst_0
          //   467: invokevirtual 234	android/widget/ListView:setVisibility	(I)V
          //   470: aload_0
          //   471: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   474: invokestatic 238	com/fab/sale/FeaturedActivity:access$15	(Lcom/fab/sale/FeaturedActivity;)Landroid/widget/ProgressBar;
          //   477: bipush 8
          //   479: invokevirtual 241	android/widget/ProgressBar:setVisibility	(I)V
          //   482: aload_0
          //   483: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   486: invokestatic 245	com/fab/sale/FeaturedActivity:access$16	(Lcom/fab/sale/FeaturedActivity;)Lcom/fab/views/ErrorMessage;
          //   489: bipush 8
          //   491: invokevirtual 248	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   494: return
          //   495: aload_0
          //   496: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   499: new 111	java/util/ArrayList
          //   502: dup
          //   503: invokespecial 249	java/util/ArrayList:<init>	()V
          //   506: putfield 109	com/fab/sale/FeaturedActivity:theFeaturedPair	Ljava/util/ArrayList;
          //   509: goto -361 -> 148
          //   512: aload_0
          //   513: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   516: new 111	java/util/ArrayList
          //   519: dup
          //   520: invokespecial 249	java/util/ArrayList:<init>	()V
          //   523: invokestatic 253	com/fab/sale/FeaturedActivity:access$9	(Lcom/fab/sale/FeaturedActivity;Ljava/util/ArrayList;)V
          //   526: goto -225 -> 301
          //   529: new 191	com/fab/sale/bo/SalesBO
          //   532: dup
          //   533: invokespecial 192	com/fab/sale/bo/SalesBO:<init>	()V
          //   536: astore 15
          //   538: new 26	org/json/JSONObject
          //   541: dup
          //   542: invokespecial 124	org/json/JSONObject:<init>	()V
          //   545: astore 16
          //   547: aload 8
          //   549: iload 13
          //   551: invokevirtual 256	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   554: astore 28
          //   556: aload 28
          //   558: astore 16
          //   560: aload 15
          //   562: aload 16
          //   564: ldc_w 258
          //   567: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   570: invokevirtual 261	com/fab/sale/bo/SalesBO:setSaleId	(I)V
          //   573: aload_0
          //   574: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   577: invokestatic 265	com/fab/sale/FeaturedActivity:access$10	(Lcom/fab/sale/FeaturedActivity;)Z
          //   580: ifne +180 -> 760
          //   583: aload_0
          //   584: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   587: invokestatic 105	com/fab/sale/FeaturedActivity:access$5	(Lcom/fab/sale/FeaturedActivity;)I
          //   590: iconst_1
          //   591: if_icmpne +169 -> 760
          //   594: aload 15
          //   596: iconst_1
          //   597: invokevirtual 268	com/fab/sale/bo/SalesBO:setHeader	(Z)V
          //   600: aload_0
          //   601: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   604: iconst_1
          //   605: invokestatic 172	com/fab/sale/FeaturedActivity:access$7	(Lcom/fab/sale/FeaturedActivity;Z)V
          //   608: aload 16
          //   610: ldc_w 270
          //   613: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   616: astore 25
          //   618: aload 15
          //   620: aload 25
          //   622: invokevirtual 274	com/fab/sale/bo/SalesBO:setType	(Ljava/lang/String;)V
          //   625: ldc_w 276
          //   628: aload 25
          //   630: invokevirtual 281	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
          //   633: ifne +18 -> 651
          //   636: ldc_w 283
          //   639: aload 25
          //   641: invokevirtual 281	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
          //   644: istore 27
          //   646: iload 27
          //   648: ifeq +16 -> 664
          //   651: aload 15
          //   653: aload 16
          //   655: ldc_w 285
          //   658: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   661: invokevirtual 288	com/fab/sale/bo/SalesBO:setSaleName	(Ljava/lang/String;)V
          //   664: aload 15
          //   666: aload 16
          //   668: ldc_w 290
          //   671: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   674: invokevirtual 293	com/fab/sale/bo/SalesBO:setPrimaryTitle	(Ljava/lang/String;)V
          //   677: aload 15
          //   679: new 295	java/lang/StringBuilder
          //   682: dup
          //   683: ldc_w 297
          //   686: invokespecial 299	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   689: aload 16
          //   691: ldc_w 301
          //   694: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   697: ldc_w 303
          //   700: ldc_w 305
          //   703: invokevirtual 309	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   706: invokevirtual 313	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   709: invokevirtual 314	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   712: invokevirtual 317	com/fab/sale/bo/SalesBO:setSaleImageUrl	(Ljava/lang/String;)V
          //   715: aload 15
          //   717: aload 16
          //   719: ldc_w 319
          //   722: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   725: invokevirtual 322	com/fab/sale/bo/SalesBO:setSaleDuration	(Ljava/lang/String;)V
          //   728: aload 15
          //   730: aload 16
          //   732: ldc_w 324
          //   735: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   738: invokevirtual 327	com/fab/sale/bo/SalesBO:setRibbonText	(Ljava/lang/String;)V
          //   741: aload_0
          //   742: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   745: invokestatic 182	com/fab/sale/FeaturedActivity:access$8	(Lcom/fab/sale/FeaturedActivity;)Ljava/util/ArrayList;
          //   748: aload 15
          //   750: invokevirtual 330	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   753: pop
          //   754: iinc 13 1
          //   757: goto -446 -> 311
          //   760: aload 15
          //   762: iconst_0
          //   763: invokevirtual 268	com/fab/sale/bo/SalesBO:setHeader	(Z)V
          //   766: goto -158 -> 608
          //   769: new 332	com/fab/sale/bo/PairedSales
          //   772: dup
          //   773: invokespecial 333	com/fab/sale/bo/PairedSales:<init>	()V
          //   776: astore 31
          //   778: aload 31
          //   780: aload_0
          //   781: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   784: invokestatic 182	com/fab/sale/FeaturedActivity:access$8	(Lcom/fab/sale/FeaturedActivity;)Ljava/util/ArrayList;
          //   787: iload 29
          //   789: invokevirtual 337	java/util/ArrayList:get	(I)Ljava/lang/Object;
          //   792: checkcast 191	com/fab/sale/bo/SalesBO
          //   795: invokevirtual 341	com/fab/sale/bo/PairedSales:setFirstSale	(Lcom/fab/sale/bo/SalesBO;)V
          //   798: iload 29
          //   800: iconst_1
          //   801: iadd
          //   802: istore 32
          //   804: aload 31
          //   806: aload_0
          //   807: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   810: invokestatic 182	com/fab/sale/FeaturedActivity:access$8	(Lcom/fab/sale/FeaturedActivity;)Ljava/util/ArrayList;
          //   813: iload 32
          //   815: invokevirtual 337	java/util/ArrayList:get	(I)Ljava/lang/Object;
          //   818: checkcast 191	com/fab/sale/bo/SalesBO
          //   821: invokevirtual 344	com/fab/sale/bo/PairedSales:setSecondSale	(Lcom/fab/sale/bo/SalesBO;)V
          //   824: aload_0
          //   825: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   828: getfield 109	com/fab/sale/FeaturedActivity:theFeaturedPair	Ljava/util/ArrayList;
          //   831: aload 31
          //   833: invokevirtual 330	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   836: pop
          //   837: iload 32
          //   839: iconst_1
          //   840: iadd
          //   841: istore 29
          //   843: goto -467 -> 376
          //   846: aload 6
          //   848: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   851: ifne -468 -> 383
          //   854: aload_0
          //   855: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   858: invokestatic 86	com/fab/sale/FeaturedActivity:access$4	(Lcom/fab/sale/FeaturedActivity;)Landroid/content/Context;
          //   861: aload 6
          //   863: invokestatic 348	com/fab/utils/FabUtils:showAlert	(Landroid/content/Context;Ljava/lang/String;)V
          //   866: goto -483 -> 383
          //   869: aload_0
          //   870: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   873: iconst_0
          //   874: putfield 214	com/fab/sale/FeaturedActivity:nextPageExists	Z
          //   877: aload_0
          //   878: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   881: invokestatic 231	com/fab/sale/FeaturedActivity:access$14	(Lcom/fab/sale/FeaturedActivity;)Landroid/widget/ListView;
          //   884: aload_0
          //   885: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   888: invokestatic 222	com/fab/sale/FeaturedActivity:access$13	(Lcom/fab/sale/FeaturedActivity;)Landroid/view/View;
          //   891: invokevirtual 352	android/widget/ListView:removeFooterView	(Landroid/view/View;)Z
          //   894: pop
          //   895: goto -436 -> 459
          //   898: astore 34
          //   900: aload_0
          //   901: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   904: iconst_0
          //   905: putfield 214	com/fab/sale/FeaturedActivity:nextPageExists	Z
          //   908: aload_0
          //   909: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   912: invokestatic 222	com/fab/sale/FeaturedActivity:access$13	(Lcom/fab/sale/FeaturedActivity;)Landroid/view/View;
          //   915: bipush 8
          //   917: invokevirtual 227	android/view/View:setVisibility	(I)V
          //   920: aload_0
          //   921: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   924: invokestatic 231	com/fab/sale/FeaturedActivity:access$14	(Lcom/fab/sale/FeaturedActivity;)Landroid/widget/ListView;
          //   927: aload_0
          //   928: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   931: invokestatic 222	com/fab/sale/FeaturedActivity:access$13	(Lcom/fab/sale/FeaturedActivity;)Landroid/view/View;
          //   934: invokevirtual 352	android/widget/ListView:removeFooterView	(Landroid/view/View;)Z
          //   937: pop
          //   938: goto -479 -> 459
          //   941: aload_0
          //   942: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   945: invokestatic 238	com/fab/sale/FeaturedActivity:access$15	(Lcom/fab/sale/FeaturedActivity;)Landroid/widget/ProgressBar;
          //   948: bipush 8
          //   950: invokevirtual 241	android/widget/ProgressBar:setVisibility	(I)V
          //   953: aload_0
          //   954: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   957: invokestatic 356	com/fab/sale/FeaturedActivity:access$17	(Lcom/fab/sale/FeaturedActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   960: invokevirtual 361	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   963: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   966: ifne -472 -> 494
          //   969: aload_0
          //   970: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   973: invokestatic 245	com/fab/sale/FeaturedActivity:access$16	(Lcom/fab/sale/FeaturedActivity;)Lcom/fab/views/ErrorMessage;
          //   976: iconst_0
          //   977: invokevirtual 248	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   980: aload_0
          //   981: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   984: invokestatic 245	com/fab/sale/FeaturedActivity:access$16	(Lcom/fab/sale/FeaturedActivity;)Lcom/fab/views/ErrorMessage;
          //   987: aload_0
          //   988: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   991: invokestatic 356	com/fab/sale/FeaturedActivity:access$17	(Lcom/fab/sale/FeaturedActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   994: invokevirtual 361	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   997: invokevirtual 364	com/fab/views/ErrorMessage:setErrorMsg	(Ljava/lang/String;)V
          //   1000: aload_0
          //   1001: getfield 18	com/fab/sale/FeaturedActivity$10:this$0	Lcom/fab/sale/FeaturedActivity;
          //   1004: invokestatic 231	com/fab/sale/FeaturedActivity:access$14	(Lcom/fab/sale/FeaturedActivity;)Landroid/widget/ListView;
          //   1007: bipush 8
          //   1009: invokevirtual 234	android/widget/ListView:setVisibility	(I)V
          //   1012: goto -518 -> 494
          //   1015: astore 17
          //   1017: goto -457 -> 560
          //   1020: astore 23
          //   1022: goto -281 -> 741
          //   1025: astore 22
          //   1027: goto -299 -> 728
          //   1030: astore 21
          //   1032: goto -317 -> 715
          //   1035: astore 20
          //   1037: goto -360 -> 677
          //   1040: astore 19
          //   1042: goto -378 -> 664
          //   1045: astore 26
          //   1047: goto -383 -> 664
          //   1050: astore 18
          //   1052: goto -479 -> 573
          //   1055: astore 12
          //   1057: goto -776 -> 281
          //   1060: astore 11
          //   1062: goto -801 -> 261
          //   1065: astore 10
          //   1067: goto -881 -> 186
          //   1070: astore 7
          //   1072: goto -955 -> 117
          //   1075: astore 5
          //   1077: goto -998 -> 79
          //   1080: astore 4
          //   1082: goto -1036 -> 46
          //
          // Exception table:
          //   from	to	target	type
          //   413	459	898	org/json/JSONException
          //   869	895	898	org/json/JSONException
          //   547	556	1015	org/json/JSONException
          //   728	741	1020	org/json/JSONException
          //   715	728	1025	org/json/JSONException
          //   677	715	1030	org/json/JSONException
          //   664	677	1035	org/json/JSONException
          //   608	646	1040	org/json/JSONException
          //   651	664	1045	org/json/JSONException
          //   560	573	1050	org/json/JSONException
          //   269	277	1055	org/json/JSONException
          //   186	261	1060	org/json/JSONException
          //   174	182	1065	org/json/JSONException
          //   105	113	1070	org/json/JSONException
          //   56	79	1075	org/json/JSONException
          //   35	43	1080	org/json/JSONException
        }
      });
      ExecuteRequestAsyncTask localExecuteRequestAsyncTask = this.task;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/featured-today/", "?page_no=" + this.page);
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
    this.theSales = new ArrayList();
    this.theFeaturedPair = new ArrayList();
    this.hasSetHeader = false;
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
        FabUtils.log("FEATURED_TODAY", "firstVisibleItem=" + paramAnonymousInt1 + "::visibleItemCount=" + paramAnonymousInt2 + "::totalItemCount=" + paramAnonymousInt3);
        int i = paramAnonymousInt1 + paramAnonymousInt2;
        if ((i != paramAnonymousInt3) || ((i == paramAnonymousInt3) && (!FeaturedActivity.this.loadingMore) && (FeaturedActivity.this.nextPageExists)))
        {
          if (!Calendar.getInstance().after(Long.valueOf(FabSharedPrefs.getNextSaleTs())))
            break label102;
          FeaturedActivity.this.refreshActivity();
        }
        while (true)
        {
          return;
          label102: FeaturedActivity.this.loadSales();
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
    this.tracker.trackPageView(FabUtils.gATracUrl("salesLanding/current"));
    this.adapter = new EndingSoonAdapter(this.context, this.theFeaturedPair);
    this.listView.setAdapter(this.adapter);
    FabHome.titleWidget.getFabLogo().setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (FeaturedActivity.this.activityLoading == 2)
          FeaturedActivity.this.refreshActivity();
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
          FabUtils.log("FEATURED_TODAY", localBundle.toString());
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
            FeaturedActivity.this.timer.setVisibility(4);
          if (FeaturedActivity.isActivityAlive)
          {
            AlertDialog.Builder localBuilder = FeaturedActivity.this.alertbox;
            if (localBundle.getInt("Error") == 1);
            for (int j = 2130837504; ; j = 2130837507)
            {
              localBuilder.setIcon(j);
              FeaturedActivity.this.alertbox.setTitle(localBundle.getString("Title"));
              FeaturedActivity.this.alertbox.setMessage(localBundle.getString("Message"));
              FeaturedActivity.this.alertbox.setNeutralButton("OK", null);
              if (!FeaturedActivity.isActivityAlive)
                break;
              FeaturedActivity.this.alertbox.show();
              break;
            }
            FeaturedActivity.this.timer.setVisibility(0);
            FeaturedActivity.this.timer.setText(str);
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
        Intent localIntent = new Intent(FeaturedActivity.this.context, MoreActivity.class);
        FeaturedActivity.this.startActivity(localIntent);
        return false;
      }
    });
    MenuItem localMenuItem2 = paramMenu.findItem(2131362109);
    localMenuItem2.setVisible(true);
    localMenuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(FeaturedActivity.this.context, FabWebViewActivity.class);
        FeaturedActivity.this.startActivity(localIntent);
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
          FabUtils.showGuestAlert(FeaturedActivity.this.context, FeaturedActivity.this.context.getResources().getString(2131165208), FeaturedActivity.this.context.getResources().getString(2131165209), false);
        while (true)
        {
          return false;
          Intent localIntent = new Intent(FeaturedActivity.this.context, InviteActivity.class);
          FeaturedActivity.this.startActivity(localIntent);
        }
      }
    });
    MenuItem localMenuItem4 = paramMenu.findItem(2131362103);
    localMenuItem4.setVisible(true);
    localMenuItem4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        FeaturedActivity.this.refreshActivity();
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
    if (this.adapter != null)
    {
      this.adapter.stopImageDownloads();
      this.adapter.clearImageCache();
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
        if (FeaturedActivity.this.activityLoading == 2)
          FeaturedActivity.this.refreshActivity();
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
        if (FeaturedActivity.this.activityLoading == 2)
          FeaturedActivity.this.refreshActivity();
      }
    });
    isActivityAlive = true;
    try
    {
      if (Calendar.getInstance().after(Long.valueOf(FabSharedPrefs.getNextSaleTs())))
        refreshActivity();
      label82: this.error.setVisibility(8);
      if (this.theFeaturedPair.isEmpty())
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
 * Qualified Name:     com.fab.sale.FeaturedActivity
 * JD-Core Version:    0.6.2
 */