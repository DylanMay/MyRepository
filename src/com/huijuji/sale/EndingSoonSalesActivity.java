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

public class EndingSoonSalesActivity extends Activity
{
  private static final String TAG = "SALES_LIST";
  protected static boolean isActivityAlive;
  public int activityLoading = 0;
  private AlertDialog.Builder alertbox;
  private Context context;
  private EndingSoonAdapter endingSoonAdapter;
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
  private ArrayList<PairedSales> theEndingSales;
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
      this.activityLoading = 1;
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
          //   0: aload_1
          //   1: ifnull +907 -> 908
          //   4: aload_1
          //   5: instanceof 26
          //   8: ifeq +900 -> 908
          //   11: aload_1
          //   12: checkcast 26	org/json/JSONObject
          //   15: astore_2
          //   16: aload_0
          //   17: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   20: iconst_2
          //   21: putfield 30	com/fab/sale/EndingSoonSalesActivity:activityLoading	I
          //   24: ldc 32
          //   26: aload_2
          //   27: invokevirtual 36	org/json/JSONObject:toString	()Ljava/lang/String;
          //   30: invokestatic 42	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
          //   33: iconst_0
          //   34: istore_3
          //   35: aload_2
          //   36: ldc 44
          //   38: invokevirtual 48	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   41: istore 38
          //   43: iload 38
          //   45: istore_3
          //   46: getstatic 54	com/fab/FabHome:titleWidget	Lcom/fab/views/TitleWidget;
          //   49: invokevirtual 60	com/fab/views/TitleWidget:getFabLogo	()Landroid/widget/ImageView;
          //   52: iconst_1
          //   53: invokevirtual 66	android/widget/ImageView:setEnabled	(Z)V
          //   56: aload_0
          //   57: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   60: aload_2
          //   61: ldc 68
          //   63: invokevirtual 72	org/json/JSONObject:getLong	(Ljava/lang/String;)J
          //   66: putfield 76	com/fab/sale/EndingSoonSalesActivity:nextSaleTs	J
          //   69: aload_0
          //   70: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   73: getfield 76	com/fab/sale/EndingSoonSalesActivity:nextSaleTs	J
          //   76: invokestatic 82	com/fab/utils/FabSharedPrefs:setNextSaleTs	(J)V
          //   79: iload_3
          //   80: ifeq +21 -> 101
          //   83: aload_0
          //   84: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   87: invokestatic 86	com/fab/sale/EndingSoonSalesActivity:access$4	(Lcom/fab/sale/EndingSoonSalesActivity;)Landroid/content/Context;
          //   90: aconst_null
          //   91: invokestatic 90	com/fab/utils/FabUtils:logoutUser	(Landroid/content/Context;Landroid/os/Handler;)V
          //   94: aload_0
          //   95: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   98: invokevirtual 93	com/fab/sale/EndingSoonSalesActivity:finish	()V
          //   101: ldc 95
          //   103: astore 6
          //   105: aload_2
          //   106: ldc 97
          //   108: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   111: astore 37
          //   113: aload 37
          //   115: astore 6
          //   117: aload_0
          //   118: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   121: invokestatic 105	com/fab/sale/EndingSoonSalesActivity:access$5	(Lcom/fab/sale/EndingSoonSalesActivity;)I
          //   124: iconst_1
          //   125: if_icmpne +23 -> 148
          //   128: aload_0
          //   129: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   132: invokestatic 109	com/fab/sale/EndingSoonSalesActivity:access$6	(Lcom/fab/sale/EndingSoonSalesActivity;)Ljava/util/ArrayList;
          //   135: ifnull +360 -> 495
          //   138: aload_0
          //   139: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   142: invokestatic 109	com/fab/sale/EndingSoonSalesActivity:access$6	(Lcom/fab/sale/EndingSoonSalesActivity;)Ljava/util/ArrayList;
          //   145: invokevirtual 114	java/util/ArrayList:clear	()V
          //   148: aload 6
          //   150: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   153: ifeq +678 -> 831
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
          //   180: astore 36
          //   182: aload 36
          //   184: astore 9
          //   186: aload_0
          //   187: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   190: aload 9
          //   192: ldc 132
          //   194: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   197: invokevirtual 140	com/fab/sale/EndingSoonSalesActivity:updateCartCounter	(I)V
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
          //   237: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   240: invokevirtual 160	com/fab/sale/EndingSoonSalesActivity:getApplicationContext	()Landroid/content/Context;
          //   243: aload 9
          //   245: ldc 142
          //   247: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   250: i2l
          //   251: aload_0
          //   252: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   255: invokestatic 164	com/fab/sale/EndingSoonSalesActivity:access$8	(Lcom/fab/sale/EndingSoonSalesActivity;)Landroid/os/Handler;
          //   258: invokestatic 168	com/fab/utils/FabHelper:stopAndRestartCartTimer	(Landroid/content/Context;JLandroid/os/Handler;)V
          //   261: aload_0
          //   262: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   265: iconst_0
          //   266: invokestatic 172	com/fab/sale/EndingSoonSalesActivity:access$9	(Lcom/fab/sale/EndingSoonSalesActivity;Z)V
          //   269: aload_2
          //   270: ldc 174
          //   272: invokevirtual 178	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   275: astore 35
          //   277: aload 35
          //   279: astore 8
          //   281: aload_0
          //   282: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   285: invokestatic 181	com/fab/sale/EndingSoonSalesActivity:access$10	(Lcom/fab/sale/EndingSoonSalesActivity;)Ljava/util/ArrayList;
          //   288: ifnull +224 -> 512
          //   291: aload_0
          //   292: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   295: invokestatic 181	com/fab/sale/EndingSoonSalesActivity:access$10	(Lcom/fab/sale/EndingSoonSalesActivity;)Ljava/util/ArrayList;
          //   298: invokevirtual 114	java/util/ArrayList:clear	()V
          //   301: iconst_0
          //   302: istore 13
          //   304: aload 8
          //   306: invokevirtual 185	org/json/JSONArray:length	()I
          //   309: istore 14
          //   311: iload 13
          //   313: iload 14
          //   315: if_icmplt +214 -> 529
          //   318: aload_0
          //   319: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   322: invokestatic 181	com/fab/sale/EndingSoonSalesActivity:access$10	(Lcom/fab/sale/EndingSoonSalesActivity;)Ljava/util/ArrayList;
          //   325: invokevirtual 188	java/util/ArrayList:size	()I
          //   328: iconst_2
          //   329: irem
          //   330: iconst_1
          //   331: if_icmpne +30 -> 361
          //   334: aload_0
          //   335: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   338: invokestatic 181	com/fab/sale/EndingSoonSalesActivity:access$10	(Lcom/fab/sale/EndingSoonSalesActivity;)Ljava/util/ArrayList;
          //   341: aload_0
          //   342: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   345: invokestatic 181	com/fab/sale/EndingSoonSalesActivity:access$10	(Lcom/fab/sale/EndingSoonSalesActivity;)Ljava/util/ArrayList;
          //   348: invokevirtual 188	java/util/ArrayList:size	()I
          //   351: new 190	com/fab/sale/bo/SalesBO
          //   354: dup
          //   355: invokespecial 191	com/fab/sale/bo/SalesBO:<init>	()V
          //   358: invokevirtual 195	java/util/ArrayList:add	(ILjava/lang/Object;)V
          //   361: iconst_0
          //   362: istore 27
          //   364: aload_0
          //   365: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   368: invokestatic 181	com/fab/sale/EndingSoonSalesActivity:access$10	(Lcom/fab/sale/EndingSoonSalesActivity;)Ljava/util/ArrayList;
          //   371: invokevirtual 188	java/util/ArrayList:size	()I
          //   374: istore 28
          //   376: iload 27
          //   378: iload 28
          //   380: if_icmplt +374 -> 754
          //   383: aload_0
          //   384: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   387: invokestatic 199	com/fab/sale/EndingSoonSalesActivity:access$13	(Lcom/fab/sale/EndingSoonSalesActivity;)Lcom/fab/sale/EndingSoonAdapter;
          //   390: ifnull +23 -> 413
          //   393: aload_0
          //   394: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   397: invokestatic 199	com/fab/sale/EndingSoonSalesActivity:access$13	(Lcom/fab/sale/EndingSoonSalesActivity;)Lcom/fab/sale/EndingSoonAdapter;
          //   400: invokevirtual 204	com/fab/sale/EndingSoonAdapter:downloadTheImages	()V
          //   403: aload_0
          //   404: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   407: invokestatic 199	com/fab/sale/EndingSoonSalesActivity:access$13	(Lcom/fab/sale/EndingSoonSalesActivity;)Lcom/fab/sale/EndingSoonAdapter;
          //   410: invokevirtual 207	com/fab/sale/EndingSoonAdapter:notifyDataSetChanged	()V
          //   413: aload_2
          //   414: ldc 209
          //   416: invokevirtual 48	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   419: ifeq +435 -> 854
          //   422: aload_0
          //   423: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   426: iconst_1
          //   427: putfield 213	com/fab/sale/EndingSoonSalesActivity:nextPageExists	Z
          //   430: aload_0
          //   431: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   434: astore 34
          //   436: aload 34
          //   438: iconst_1
          //   439: aload 34
          //   441: invokestatic 105	com/fab/sale/EndingSoonSalesActivity:access$5	(Lcom/fab/sale/EndingSoonSalesActivity;)I
          //   444: iadd
          //   445: invokestatic 217	com/fab/sale/EndingSoonSalesActivity:access$14	(Lcom/fab/sale/EndingSoonSalesActivity;I)V
          //   448: aload_0
          //   449: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   452: invokestatic 221	com/fab/sale/EndingSoonSalesActivity:access$15	(Lcom/fab/sale/EndingSoonSalesActivity;)Landroid/view/View;
          //   455: iconst_0
          //   456: invokevirtual 226	android/view/View:setVisibility	(I)V
          //   459: aload_0
          //   460: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   463: invokestatic 230	com/fab/sale/EndingSoonSalesActivity:access$16	(Lcom/fab/sale/EndingSoonSalesActivity;)Landroid/widget/ListView;
          //   466: iconst_0
          //   467: invokevirtual 233	android/widget/ListView:setVisibility	(I)V
          //   470: aload_0
          //   471: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   474: invokestatic 237	com/fab/sale/EndingSoonSalesActivity:access$17	(Lcom/fab/sale/EndingSoonSalesActivity;)Landroid/widget/ProgressBar;
          //   477: bipush 8
          //   479: invokevirtual 240	android/widget/ProgressBar:setVisibility	(I)V
          //   482: aload_0
          //   483: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   486: invokestatic 244	com/fab/sale/EndingSoonSalesActivity:access$18	(Lcom/fab/sale/EndingSoonSalesActivity;)Lcom/fab/views/ErrorMessage;
          //   489: bipush 8
          //   491: invokevirtual 247	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   494: return
          //   495: aload_0
          //   496: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   499: new 111	java/util/ArrayList
          //   502: dup
          //   503: invokespecial 248	java/util/ArrayList:<init>	()V
          //   506: invokestatic 252	com/fab/sale/EndingSoonSalesActivity:access$7	(Lcom/fab/sale/EndingSoonSalesActivity;Ljava/util/ArrayList;)V
          //   509: goto -361 -> 148
          //   512: aload_0
          //   513: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   516: new 111	java/util/ArrayList
          //   519: dup
          //   520: invokespecial 248	java/util/ArrayList:<init>	()V
          //   523: invokestatic 255	com/fab/sale/EndingSoonSalesActivity:access$11	(Lcom/fab/sale/EndingSoonSalesActivity;Ljava/util/ArrayList;)V
          //   526: goto -225 -> 301
          //   529: new 190	com/fab/sale/bo/SalesBO
          //   532: dup
          //   533: invokespecial 191	com/fab/sale/bo/SalesBO:<init>	()V
          //   536: astore 15
          //   538: new 26	org/json/JSONObject
          //   541: dup
          //   542: invokespecial 124	org/json/JSONObject:<init>	()V
          //   545: astore 16
          //   547: aload 8
          //   549: iload 13
          //   551: invokevirtual 258	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   554: astore 26
          //   556: aload 26
          //   558: astore 16
          //   560: aload 15
          //   562: aload 16
          //   564: ldc_w 260
          //   567: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   570: invokevirtual 263	com/fab/sale/bo/SalesBO:setSaleId	(I)V
          //   573: aload_0
          //   574: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   577: invokestatic 267	com/fab/sale/EndingSoonSalesActivity:access$12	(Lcom/fab/sale/EndingSoonSalesActivity;)Z
          //   580: ifne +165 -> 745
          //   583: aload_0
          //   584: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   587: invokestatic 105	com/fab/sale/EndingSoonSalesActivity:access$5	(Lcom/fab/sale/EndingSoonSalesActivity;)I
          //   590: iconst_1
          //   591: if_icmpne +154 -> 745
          //   594: aload 15
          //   596: iconst_1
          //   597: invokevirtual 270	com/fab/sale/bo/SalesBO:setHeader	(Z)V
          //   600: aload_0
          //   601: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   604: iconst_1
          //   605: invokestatic 172	com/fab/sale/EndingSoonSalesActivity:access$9	(Lcom/fab/sale/EndingSoonSalesActivity;Z)V
          //   608: aload 16
          //   610: ldc_w 272
          //   613: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   616: astore 25
          //   618: aload 15
          //   620: aload 25
          //   622: invokevirtual 276	com/fab/sale/bo/SalesBO:setType	(Ljava/lang/String;)V
          //   625: ldc_w 278
          //   628: aload 25
          //   630: invokevirtual 283	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
          //   633: ifeq +16 -> 649
          //   636: aload 15
          //   638: aload 16
          //   640: ldc_w 285
          //   643: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   646: invokevirtual 288	com/fab/sale/bo/SalesBO:setRibbonText	(Ljava/lang/String;)V
          //   649: aload 15
          //   651: aload 16
          //   653: ldc_w 290
          //   656: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   659: invokevirtual 293	com/fab/sale/bo/SalesBO:setSaleName	(Ljava/lang/String;)V
          //   662: aload 15
          //   664: aload 16
          //   666: ldc_w 295
          //   669: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   672: invokevirtual 298	com/fab/sale/bo/SalesBO:setPrimaryTitle	(Ljava/lang/String;)V
          //   675: aload 15
          //   677: new 300	java/lang/StringBuilder
          //   680: dup
          //   681: ldc_w 302
          //   684: invokespecial 304	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   687: aload 16
          //   689: ldc_w 306
          //   692: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   695: ldc_w 308
          //   698: ldc_w 310
          //   701: invokevirtual 314	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   704: invokevirtual 318	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   707: invokevirtual 319	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   710: invokevirtual 322	com/fab/sale/bo/SalesBO:setSaleImageUrl	(Ljava/lang/String;)V
          //   713: aload 15
          //   715: aload 16
          //   717: ldc_w 324
          //   720: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   723: invokevirtual 327	com/fab/sale/bo/SalesBO:setSaleDuration	(Ljava/lang/String;)V
          //   726: aload_0
          //   727: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   730: invokestatic 181	com/fab/sale/EndingSoonSalesActivity:access$10	(Lcom/fab/sale/EndingSoonSalesActivity;)Ljava/util/ArrayList;
          //   733: aload 15
          //   735: invokevirtual 330	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   738: pop
          //   739: iinc 13 1
          //   742: goto -431 -> 311
          //   745: aload 15
          //   747: iconst_0
          //   748: invokevirtual 270	com/fab/sale/bo/SalesBO:setHeader	(Z)V
          //   751: goto -143 -> 608
          //   754: new 332	com/fab/sale/bo/PairedSales
          //   757: dup
          //   758: invokespecial 333	com/fab/sale/bo/PairedSales:<init>	()V
          //   761: astore 29
          //   763: aload 29
          //   765: aload_0
          //   766: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   769: invokestatic 181	com/fab/sale/EndingSoonSalesActivity:access$10	(Lcom/fab/sale/EndingSoonSalesActivity;)Ljava/util/ArrayList;
          //   772: iload 27
          //   774: invokevirtual 337	java/util/ArrayList:get	(I)Ljava/lang/Object;
          //   777: checkcast 190	com/fab/sale/bo/SalesBO
          //   780: invokevirtual 341	com/fab/sale/bo/PairedSales:setFirstSale	(Lcom/fab/sale/bo/SalesBO;)V
          //   783: iload 27
          //   785: iconst_1
          //   786: iadd
          //   787: istore 30
          //   789: aload 29
          //   791: aload_0
          //   792: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   795: invokestatic 181	com/fab/sale/EndingSoonSalesActivity:access$10	(Lcom/fab/sale/EndingSoonSalesActivity;)Ljava/util/ArrayList;
          //   798: iload 30
          //   800: invokevirtual 337	java/util/ArrayList:get	(I)Ljava/lang/Object;
          //   803: checkcast 190	com/fab/sale/bo/SalesBO
          //   806: invokevirtual 344	com/fab/sale/bo/PairedSales:setSecondSale	(Lcom/fab/sale/bo/SalesBO;)V
          //   809: aload_0
          //   810: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   813: invokestatic 109	com/fab/sale/EndingSoonSalesActivity:access$6	(Lcom/fab/sale/EndingSoonSalesActivity;)Ljava/util/ArrayList;
          //   816: aload 29
          //   818: invokevirtual 330	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   821: pop
          //   822: iload 30
          //   824: iconst_1
          //   825: iadd
          //   826: istore 27
          //   828: goto -452 -> 376
          //   831: aload 6
          //   833: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   836: ifne -453 -> 383
          //   839: aload_0
          //   840: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   843: invokestatic 86	com/fab/sale/EndingSoonSalesActivity:access$4	(Lcom/fab/sale/EndingSoonSalesActivity;)Landroid/content/Context;
          //   846: aload 6
          //   848: invokestatic 348	com/fab/utils/FabUtils:showAlert	(Landroid/content/Context;Ljava/lang/String;)V
          //   851: goto -468 -> 383
          //   854: aload_0
          //   855: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   858: iconst_0
          //   859: putfield 213	com/fab/sale/EndingSoonSalesActivity:nextPageExists	Z
          //   862: aload_0
          //   863: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   866: invokestatic 230	com/fab/sale/EndingSoonSalesActivity:access$16	(Lcom/fab/sale/EndingSoonSalesActivity;)Landroid/widget/ListView;
          //   869: aload_0
          //   870: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   873: invokestatic 221	com/fab/sale/EndingSoonSalesActivity:access$15	(Lcom/fab/sale/EndingSoonSalesActivity;)Landroid/view/View;
          //   876: invokevirtual 352	android/widget/ListView:removeFooterView	(Landroid/view/View;)Z
          //   879: pop
          //   880: goto -421 -> 459
          //   883: astore 32
          //   885: aload_0
          //   886: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   889: iconst_0
          //   890: putfield 213	com/fab/sale/EndingSoonSalesActivity:nextPageExists	Z
          //   893: aload_0
          //   894: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   897: invokestatic 221	com/fab/sale/EndingSoonSalesActivity:access$15	(Lcom/fab/sale/EndingSoonSalesActivity;)Landroid/view/View;
          //   900: bipush 8
          //   902: invokevirtual 226	android/view/View:setVisibility	(I)V
          //   905: goto -446 -> 459
          //   908: aload_0
          //   909: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   912: invokestatic 237	com/fab/sale/EndingSoonSalesActivity:access$17	(Lcom/fab/sale/EndingSoonSalesActivity;)Landroid/widget/ProgressBar;
          //   915: bipush 8
          //   917: invokevirtual 240	android/widget/ProgressBar:setVisibility	(I)V
          //   920: aload_0
          //   921: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   924: invokestatic 356	com/fab/sale/EndingSoonSalesActivity:access$19	(Lcom/fab/sale/EndingSoonSalesActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   927: invokevirtual 361	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   930: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   933: ifne -439 -> 494
          //   936: aload_0
          //   937: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   940: invokestatic 244	com/fab/sale/EndingSoonSalesActivity:access$18	(Lcom/fab/sale/EndingSoonSalesActivity;)Lcom/fab/views/ErrorMessage;
          //   943: iconst_0
          //   944: invokevirtual 247	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   947: aload_0
          //   948: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   951: invokestatic 244	com/fab/sale/EndingSoonSalesActivity:access$18	(Lcom/fab/sale/EndingSoonSalesActivity;)Lcom/fab/views/ErrorMessage;
          //   954: aload_0
          //   955: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   958: invokestatic 356	com/fab/sale/EndingSoonSalesActivity:access$19	(Lcom/fab/sale/EndingSoonSalesActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   961: invokevirtual 361	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   964: invokevirtual 364	com/fab/views/ErrorMessage:setErrorMsg	(Ljava/lang/String;)V
          //   967: aload_0
          //   968: getfield 18	com/fab/sale/EndingSoonSalesActivity$10:this$0	Lcom/fab/sale/EndingSoonSalesActivity;
          //   971: invokestatic 230	com/fab/sale/EndingSoonSalesActivity:access$16	(Lcom/fab/sale/EndingSoonSalesActivity;)Landroid/widget/ListView;
          //   974: bipush 8
          //   976: invokevirtual 233	android/widget/ListView:setVisibility	(I)V
          //   979: goto -485 -> 494
          //   982: astore 17
          //   984: goto -424 -> 560
          //   987: astore 23
          //   989: goto -263 -> 726
          //   992: astore 22
          //   994: goto -281 -> 713
          //   997: astore 21
          //   999: goto -324 -> 675
          //   1002: astore 20
          //   1004: goto -342 -> 662
          //   1007: astore 19
          //   1009: goto -360 -> 649
          //   1012: astore 18
          //   1014: goto -441 -> 573
          //   1017: astore 12
          //   1019: goto -738 -> 281
          //   1022: astore 11
          //   1024: goto -763 -> 261
          //   1027: astore 10
          //   1029: goto -843 -> 186
          //   1032: astore 7
          //   1034: goto -917 -> 117
          //   1037: astore 5
          //   1039: goto -960 -> 79
          //   1042: astore 4
          //   1044: goto -998 -> 46
          //
          // Exception table:
          //   from	to	target	type
          //   413	459	883	org/json/JSONException
          //   854	880	883	org/json/JSONException
          //   547	556	982	org/json/JSONException
          //   713	726	987	org/json/JSONException
          //   675	713	992	org/json/JSONException
          //   662	675	997	org/json/JSONException
          //   649	662	1002	org/json/JSONException
          //   608	649	1007	org/json/JSONException
          //   560	573	1012	org/json/JSONException
          //   269	277	1017	org/json/JSONException
          //   186	261	1022	org/json/JSONException
          //   174	182	1027	org/json/JSONException
          //   105	113	1032	org/json/JSONException
          //   56	79	1037	org/json/JSONException
          //   35	43	1042	org/json/JSONException
        }
      });
      ExecuteRequestAsyncTask localExecuteRequestAsyncTask = this.task;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/sale/", "?sale_type=ending&page_no=" + this.page);
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
    this.theEndingSales = new ArrayList();
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
        FabUtils.log("SALES_LIST", "firstVisibleItem=" + paramAnonymousInt1 + "::visibleItemCount=" + paramAnonymousInt2 + "::totalItemCount=" + paramAnonymousInt3);
        int i = paramAnonymousInt1 + paramAnonymousInt2;
        if ((i != paramAnonymousInt3) || ((i == paramAnonymousInt3) && (!EndingSoonSalesActivity.this.loadingMore) && (EndingSoonSalesActivity.this.nextPageExists)))
        {
          if (!Calendar.getInstance().after(Long.valueOf(FabSharedPrefs.getNextSaleTs())))
            break label102;
          EndingSoonSalesActivity.this.refreshActivity();
        }
        while (true)
        {
          return;
          label102: EndingSoonSalesActivity.this.loadSales();
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
    this.tracker.trackPageView(FabUtils.gATracUrl("salesLanding/ending"));
    this.endingSoonAdapter = new EndingSoonAdapter(this.context, this.theEndingSales);
    this.listView.setAdapter(this.endingSoonAdapter);
    FabHome.titleWidget.getFabLogo().setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (EndingSoonSalesActivity.this.activityLoading == 2)
          EndingSoonSalesActivity.this.refreshActivity();
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
          FabUtils.log("SALES_LIST", localBundle.toString());
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
            EndingSoonSalesActivity.this.timer.setVisibility(4);
          if (EndingSoonSalesActivity.isActivityAlive)
          {
            AlertDialog.Builder localBuilder = EndingSoonSalesActivity.this.alertbox;
            if (localBundle.getInt("Error") == 1);
            for (int j = 2130837504; ; j = 2130837507)
            {
              localBuilder.setIcon(j);
              EndingSoonSalesActivity.this.alertbox.setTitle(localBundle.getString("Title"));
              EndingSoonSalesActivity.this.alertbox.setMessage(localBundle.getString("Message"));
              EndingSoonSalesActivity.this.alertbox.setNeutralButton("OK", null);
              if (!EndingSoonSalesActivity.isActivityAlive)
                break;
              EndingSoonSalesActivity.this.alertbox.show();
              break;
            }
            EndingSoonSalesActivity.this.timer.setVisibility(0);
            EndingSoonSalesActivity.this.timer.setText(str);
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
        Intent localIntent = new Intent(EndingSoonSalesActivity.this.context, MoreActivity.class);
        EndingSoonSalesActivity.this.startActivity(localIntent);
        return false;
      }
    });
    MenuItem localMenuItem2 = paramMenu.findItem(2131362109);
    localMenuItem2.setVisible(true);
    localMenuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(EndingSoonSalesActivity.this.context, FabWebViewActivity.class);
        EndingSoonSalesActivity.this.startActivity(localIntent);
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
          FabUtils.showGuestAlert(EndingSoonSalesActivity.this.context, EndingSoonSalesActivity.this.context.getResources().getString(2131165208), EndingSoonSalesActivity.this.context.getResources().getString(2131165209), false);
        while (true)
        {
          return false;
          Intent localIntent = new Intent(EndingSoonSalesActivity.this.context, InviteActivity.class);
          EndingSoonSalesActivity.this.startActivity(localIntent);
        }
      }
    });
    MenuItem localMenuItem4 = paramMenu.findItem(2131362103);
    localMenuItem4.setVisible(true);
    localMenuItem4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        EndingSoonSalesActivity.this.refreshActivity();
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
    if (this.endingSoonAdapter != null)
    {
      this.endingSoonAdapter.downloadTheImages();
      this.endingSoonAdapter.notifyDataSetChanged();
    }
    super.onLowMemory();
  }

  protected void onPause()
  {
    isActivityAlive = false;
    if (this.endingSoonAdapter != null)
    {
      this.endingSoonAdapter.stopImageDownloads();
      this.endingSoonAdapter.clearImageCache();
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
        if (EndingSoonSalesActivity.this.activityLoading == 2)
          EndingSoonSalesActivity.this.refreshActivity();
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
        if (EndingSoonSalesActivity.this.activityLoading == 2)
          EndingSoonSalesActivity.this.refreshActivity();
      }
    });
    isActivityAlive = true;
    try
    {
      if (Calendar.getInstance().after(Long.valueOf(FabSharedPrefs.getNextSaleTs())))
        refreshActivity();
      label82: this.error.setVisibility(8);
      if (this.theEndingSales.isEmpty())
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
 * Qualified Name:     com.fab.sale.EndingSoonSalesActivity
 * JD-Core Version:    0.6.2
 */