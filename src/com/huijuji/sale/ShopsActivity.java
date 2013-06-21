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
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
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
import android.widget.ProgressBar;
import com.fab.FabApplication;
import com.fab.FabHome;
import com.fab.FabIndex;
import com.fab.InviteActivity;
import com.fab.MoreActivity;
import com.fab.sale.bo.PairedShops;
import com.fab.sale.bo.PairedWeeklyShops;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabHelper;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.ErrorMessage;
import com.fab.views.FabButton;
import com.fab.views.FabListViewWithViewPager;
import com.fab.views.FabTextView;
import com.fab.views.TitleWidget;
import com.fab.webviews.FabWebViewActivity;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import java.util.ArrayList;
import java.util.Calendar;

public class ShopsActivity extends Activity
{
  protected static boolean isActivityAlive;
  protected String TAG = "ShopsActivity";
  protected int activityLoading = 0;
  private AlertDialog.Builder alertbox;
  public Context context;
  private ImageView dot1;
  private ImageView dot2;
  private ImageView dot3;
  private ErrorMessage error;
  private Bundle extras;
  private View footerView;
  private FabButton headerCartButton;
  private boolean isTabBuilding;
  private FabListViewWithViewPager listView;
  private boolean loadingMore;
  private Handler mHandler;
  public boolean nextPageExists;
  private int page;
  private ProgressBar progressBar;
  private ShopsAdapter shopsAdapter;
  private ExecuteRequestAsyncTask task;
  public ArrayList<PairedShops> thePairedShops = new ArrayList();
  public ArrayList<PairedWeeklyShops> thePairedWeeklyShops = new ArrayList();
  protected FabTextView timer;
  private GoogleAnalyticsTracker tracker;
  private ViewPager viewPager;
  private FabTextView weeklyHeaderTitle;
  public WeeklyShopsAdapter weeklyShopsAdapter;
  private View weeklyShopsView;

  private void loadShopDetails()
  {
    this.activityLoading = 1;
    if ((this.task == null) || ((this.task != null) && (this.task.getStatus() != AsyncTask.Status.RUNNING)))
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
          //   0: aload_1
          //   1: ifnull +1399 -> 1400
          //   4: aload_1
          //   5: instanceof 26
          //   8: ifeq +1392 -> 1400
          //   11: aload_1
          //   12: checkcast 26	org/json/JSONObject
          //   15: astore_2
          //   16: aload_0
          //   17: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   20: getfield 30	com/fab/sale/ShopsActivity:TAG	Ljava/lang/String;
          //   23: aload_2
          //   24: invokevirtual 34	org/json/JSONObject:toString	()Ljava/lang/String;
          //   27: invokestatic 40	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
          //   30: ldc 42
          //   32: astore_3
          //   33: aload_2
          //   34: ldc 44
          //   36: invokevirtual 48	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   39: astore 70
          //   41: aload 70
          //   43: astore_3
          //   44: getstatic 54	com/fab/FabHome:titleWidget	Lcom/fab/views/TitleWidget;
          //   47: invokevirtual 60	com/fab/views/TitleWidget:getFabLogo	()Landroid/widget/ImageView;
          //   50: iconst_1
          //   51: invokevirtual 66	android/widget/ImageView:setEnabled	(Z)V
          //   54: aload_2
          //   55: ldc 68
          //   57: invokevirtual 72	org/json/JSONObject:getLong	(Ljava/lang/String;)J
          //   60: invokestatic 78	com/fab/utils/FabSharedPrefs:setNextSaleTs	(J)V
          //   63: aload_0
          //   64: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   67: invokestatic 82	com/fab/sale/ShopsActivity:access$7	(Lcom/fab/sale/ShopsActivity;)I
          //   70: iconst_1
          //   71: if_icmpne +53 -> 124
          //   74: aload_0
          //   75: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   78: getfield 86	com/fab/sale/ShopsActivity:thePairedShops	Ljava/util/ArrayList;
          //   81: ifnull +586 -> 667
          //   84: aload_0
          //   85: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   88: getfield 86	com/fab/sale/ShopsActivity:thePairedShops	Ljava/util/ArrayList;
          //   91: invokevirtual 91	java/util/ArrayList:clear	()V
          //   94: aload_0
          //   95: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   98: getfield 94	com/fab/sale/ShopsActivity:thePairedWeeklyShops	Ljava/util/ArrayList;
          //   101: ifnull +583 -> 684
          //   104: aload_0
          //   105: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   108: getfield 94	com/fab/sale/ShopsActivity:thePairedWeeklyShops	Ljava/util/ArrayList;
          //   111: invokevirtual 91	java/util/ArrayList:clear	()V
          //   114: aload_0
          //   115: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   118: getfield 98	com/fab/sale/ShopsActivity:weeklyShopsAdapter	Lcom/fab/sale/WeeklyShopsAdapter;
          //   121: invokevirtual 103	com/fab/sale/WeeklyShopsAdapter:notifyDataSetChanged	()V
          //   124: aload_3
          //   125: invokestatic 109	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   128: ifeq +1251 -> 1379
          //   131: new 26	org/json/JSONObject
          //   134: dup
          //   135: invokespecial 110	org/json/JSONObject:<init>	()V
          //   138: astore 6
          //   140: aload_2
          //   141: ldc 112
          //   143: invokevirtual 116	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   146: astore 69
          //   148: aload 69
          //   150: astore 6
          //   152: aload_0
          //   153: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   156: aload 6
          //   158: ldc 118
          //   160: invokevirtual 122	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   163: invokevirtual 126	com/fab/sale/ShopsActivity:updateCartCounter	(I)V
          //   166: aload 6
          //   168: ldc 128
          //   170: invokevirtual 122	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   173: i2l
          //   174: new 130	java/util/GregorianCalendar
          //   177: dup
          //   178: invokespecial 131	java/util/GregorianCalendar:<init>	()V
          //   181: invokestatic 135	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
          //   184: ldc2_w 136
          //   187: lcmp
          //   188: iflt +39 -> 227
          //   191: aload 6
          //   193: ldc 128
          //   195: invokevirtual 122	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   198: i2l
          //   199: invokestatic 142	com/fab/utils/FabHelper:setCartTime	(J)V
          //   202: aload_0
          //   203: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   206: invokevirtual 146	com/fab/sale/ShopsActivity:getApplicationContext	()Landroid/content/Context;
          //   209: aload 6
          //   211: ldc 128
          //   213: invokevirtual 122	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   216: i2l
          //   217: aload_0
          //   218: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   221: invokestatic 150	com/fab/sale/ShopsActivity:access$8	(Lcom/fab/sale/ShopsActivity;)Landroid/os/Handler;
          //   224: invokestatic 154	com/fab/utils/FabHelper:stopAndRestartCartTimer	(Landroid/content/Context;JLandroid/os/Handler;)V
          //   227: aconst_null
          //   228: astore 9
          //   230: aconst_null
          //   231: astore 10
          //   233: aload_2
          //   234: ldc 156
          //   236: invokevirtual 116	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   239: astore 68
          //   241: aload 68
          //   243: astore 9
          //   245: new 88	java/util/ArrayList
          //   248: dup
          //   249: invokespecial 157	java/util/ArrayList:<init>	()V
          //   252: astore 12
          //   254: new 88	java/util/ArrayList
          //   257: dup
          //   258: invokespecial 157	java/util/ArrayList:<init>	()V
          //   261: astore 13
          //   263: aload 9
          //   265: ifnull +331 -> 596
          //   268: aload 9
          //   270: ldc 159
          //   272: invokevirtual 163	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   275: astore 67
          //   277: aload 67
          //   279: astore 10
          //   281: aload 10
          //   283: ifnull +56 -> 339
          //   286: aload 10
          //   288: invokevirtual 169	org/json/JSONArray:length	()I
          //   291: istore 56
          //   293: iconst_0
          //   294: istore 57
          //   296: iload 57
          //   298: iload 56
          //   300: if_icmplt +401 -> 701
          //   303: aload 13
          //   305: invokevirtual 172	java/util/ArrayList:size	()I
          //   308: iconst_2
          //   309: irem
          //   310: iconst_1
          //   311: if_icmpne +20 -> 331
          //   314: aload 13
          //   316: aload 13
          //   318: invokevirtual 172	java/util/ArrayList:size	()I
          //   321: new 174	com/fab/sale/bo/ShopsBO
          //   324: dup
          //   325: invokespecial 175	com/fab/sale/bo/ShopsBO:<init>	()V
          //   328: invokevirtual 179	java/util/ArrayList:add	(ILjava/lang/Object;)V
          //   331: aload 12
          //   333: aload 13
          //   335: invokevirtual 183	java/util/ArrayList:addAll	(Ljava/util/Collection;)Z
          //   338: pop
          //   339: aload_0
          //   340: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   343: invokestatic 82	com/fab/sale/ShopsActivity:access$7	(Lcom/fab/sale/ShopsActivity;)I
          //   346: iconst_1
          //   347: if_icmpne +122 -> 469
          //   350: aload 9
          //   352: ldc 185
          //   354: invokevirtual 163	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   357: astore 55
          //   359: aload 55
          //   361: astore 10
          //   363: aload 10
          //   365: ifnull +104 -> 469
          //   368: new 88	java/util/ArrayList
          //   371: dup
          //   372: invokespecial 157	java/util/ArrayList:<init>	()V
          //   375: astore 39
          //   377: aload 10
          //   379: invokevirtual 169	org/json/JSONArray:length	()I
          //   382: istore 40
          //   384: iconst_0
          //   385: istore 41
          //   387: iload 41
          //   389: iload 40
          //   391: if_icmplt +452 -> 843
          //   394: aload 39
          //   396: invokevirtual 172	java/util/ArrayList:size	()I
          //   399: iconst_2
          //   400: irem
          //   401: iconst_1
          //   402: if_icmpne +20 -> 422
          //   405: aload 39
          //   407: aload 39
          //   409: invokevirtual 172	java/util/ArrayList:size	()I
          //   412: new 187	com/fab/sale/bo/WeeklyShopsBO
          //   415: dup
          //   416: invokespecial 188	com/fab/sale/bo/WeeklyShopsBO:<init>	()V
          //   419: invokevirtual 179	java/util/ArrayList:add	(ILjava/lang/Object;)V
          //   422: iconst_0
          //   423: istore 50
          //   425: aload 39
          //   427: invokevirtual 172	java/util/ArrayList:size	()I
          //   430: istore 51
          //   432: iload 50
          //   434: iload 51
          //   436: if_icmplt +555 -> 991
          //   439: aload_0
          //   440: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   443: getfield 98	com/fab/sale/ShopsActivity:weeklyShopsAdapter	Lcom/fab/sale/WeeklyShopsAdapter;
          //   446: ifnull +23 -> 469
          //   449: aload_0
          //   450: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   453: getfield 98	com/fab/sale/ShopsActivity:weeklyShopsAdapter	Lcom/fab/sale/WeeklyShopsAdapter;
          //   456: invokevirtual 191	com/fab/sale/WeeklyShopsAdapter:downloadTheImages	()V
          //   459: aload_0
          //   460: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   463: getfield 98	com/fab/sale/ShopsActivity:weeklyShopsAdapter	Lcom/fab/sale/WeeklyShopsAdapter;
          //   466: invokevirtual 103	com/fab/sale/WeeklyShopsAdapter:notifyDataSetChanged	()V
          //   469: aload 9
          //   471: ldc 193
          //   473: invokevirtual 163	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   476: astore 37
          //   478: aload 37
          //   480: astore 10
          //   482: aload 10
          //   484: ifnull +65 -> 549
          //   487: new 88	java/util/ArrayList
          //   490: dup
          //   491: invokespecial 157	java/util/ArrayList:<init>	()V
          //   494: astore 19
          //   496: aload 10
          //   498: invokevirtual 169	org/json/JSONArray:length	()I
          //   501: istore 20
          //   503: iconst_0
          //   504: istore 21
          //   506: iload 21
          //   508: iload 20
          //   510: if_icmplt +570 -> 1080
          //   513: aload 19
          //   515: invokevirtual 172	java/util/ArrayList:size	()I
          //   518: iconst_2
          //   519: irem
          //   520: iconst_1
          //   521: if_icmpne +20 -> 541
          //   524: aload 19
          //   526: aload 19
          //   528: invokevirtual 172	java/util/ArrayList:size	()I
          //   531: new 174	com/fab/sale/bo/ShopsBO
          //   534: dup
          //   535: invokespecial 175	com/fab/sale/bo/ShopsBO:<init>	()V
          //   538: invokevirtual 179	java/util/ArrayList:add	(ILjava/lang/Object;)V
          //   541: aload 12
          //   543: aload 19
          //   545: invokevirtual 183	java/util/ArrayList:addAll	(Ljava/util/Collection;)Z
          //   548: pop
          //   549: iconst_0
          //   550: istore 32
          //   552: aload 12
          //   554: invokevirtual 172	java/util/ArrayList:size	()I
          //   557: istore 33
          //   559: iload 32
          //   561: iload 33
          //   563: if_icmplt +673 -> 1236
          //   566: aload_0
          //   567: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   570: invokestatic 197	com/fab/sale/ShopsActivity:access$9	(Lcom/fab/sale/ShopsActivity;)Lcom/fab/sale/ShopsAdapter;
          //   573: ifnull +23 -> 596
          //   576: aload_0
          //   577: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   580: invokestatic 197	com/fab/sale/ShopsActivity:access$9	(Lcom/fab/sale/ShopsActivity;)Lcom/fab/sale/ShopsAdapter;
          //   583: invokevirtual 200	com/fab/sale/ShopsAdapter:downloadTheImages	()V
          //   586: aload_0
          //   587: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   590: invokestatic 197	com/fab/sale/ShopsActivity:access$9	(Lcom/fab/sale/ShopsActivity;)Lcom/fab/sale/ShopsAdapter;
          //   593: invokevirtual 201	com/fab/sale/ShopsAdapter:notifyDataSetChanged	()V
          //   596: aload_2
          //   597: ldc 203
          //   599: invokevirtual 207	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   602: ifeq +723 -> 1325
          //   605: aload_0
          //   606: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   609: iconst_1
          //   610: putfield 211	com/fab/sale/ShopsActivity:nextPageExists	Z
          //   613: aload_0
          //   614: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   617: astore 16
          //   619: aload 16
          //   621: iconst_1
          //   622: aload 16
          //   624: invokestatic 82	com/fab/sale/ShopsActivity:access$7	(Lcom/fab/sale/ShopsActivity;)I
          //   627: iadd
          //   628: invokestatic 215	com/fab/sale/ShopsActivity:access$10	(Lcom/fab/sale/ShopsActivity;I)V
          //   631: aload_0
          //   632: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   635: invokestatic 219	com/fab/sale/ShopsActivity:access$13	(Lcom/fab/sale/ShopsActivity;)Landroid/widget/ProgressBar;
          //   638: bipush 8
          //   640: invokevirtual 224	android/widget/ProgressBar:setVisibility	(I)V
          //   643: aload_0
          //   644: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   647: invokestatic 228	com/fab/sale/ShopsActivity:access$14	(Lcom/fab/sale/ShopsActivity;)Lcom/fab/views/ErrorMessage;
          //   650: bipush 8
          //   652: invokevirtual 231	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   655: aload_0
          //   656: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   659: invokestatic 235	com/fab/sale/ShopsActivity:access$11	(Lcom/fab/sale/ShopsActivity;)Lcom/fab/views/FabListViewWithViewPager;
          //   662: iconst_0
          //   663: invokevirtual 238	com/fab/views/FabListViewWithViewPager:setVisibility	(I)V
          //   666: return
          //   667: aload_0
          //   668: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   671: new 88	java/util/ArrayList
          //   674: dup
          //   675: invokespecial 157	java/util/ArrayList:<init>	()V
          //   678: putfield 86	com/fab/sale/ShopsActivity:thePairedShops	Ljava/util/ArrayList;
          //   681: goto -587 -> 94
          //   684: aload_0
          //   685: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   688: new 88	java/util/ArrayList
          //   691: dup
          //   692: invokespecial 157	java/util/ArrayList:<init>	()V
          //   695: putfield 94	com/fab/sale/ShopsActivity:thePairedWeeklyShops	Ljava/util/ArrayList;
          //   698: goto -574 -> 124
          //   701: new 174	com/fab/sale/bo/ShopsBO
          //   704: dup
          //   705: invokespecial 175	com/fab/sale/bo/ShopsBO:<init>	()V
          //   708: astore 58
          //   710: aload 10
          //   712: iload 57
          //   714: invokevirtual 241	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   717: astore 60
          //   719: aload 60
          //   721: ifnull +116 -> 837
          //   724: iload 57
          //   726: ifne +27 -> 753
          //   729: aload_0
          //   730: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   733: invokestatic 82	com/fab/sale/ShopsActivity:access$7	(Lcom/fab/sale/ShopsActivity;)I
          //   736: iconst_1
          //   737: if_icmpne +16 -> 753
          //   740: aload 58
          //   742: iconst_1
          //   743: invokevirtual 244	com/fab/sale/bo/ShopsBO:setHasHeader	(Z)V
          //   746: aload 58
          //   748: ldc 246
          //   750: invokevirtual 250	com/fab/sale/bo/ShopsBO:setHeaderName	(Ljava/lang/String;)V
          //   753: aload 58
          //   755: aload 60
          //   757: ldc 252
          //   759: invokevirtual 122	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   762: invokevirtual 255	com/fab/sale/bo/ShopsBO:setId	(I)V
          //   765: aload 58
          //   767: aload 60
          //   769: ldc_w 257
          //   772: invokevirtual 48	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   775: invokevirtual 260	com/fab/sale/bo/ShopsBO:setPrimaryTitle	(Ljava/lang/String;)V
          //   778: aload 58
          //   780: aload 60
          //   782: ldc_w 262
          //   785: invokevirtual 48	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   788: invokevirtual 265	com/fab/sale/bo/ShopsBO:setType	(Ljava/lang/String;)V
          //   791: aload 58
          //   793: new 267	java/lang/StringBuilder
          //   796: dup
          //   797: ldc_w 269
          //   800: invokespecial 271	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   803: aload 60
          //   805: ldc_w 273
          //   808: invokevirtual 48	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   811: ldc_w 275
          //   814: ldc_w 277
          //   817: invokevirtual 283	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   820: invokevirtual 287	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   823: invokevirtual 288	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   826: invokevirtual 291	com/fab/sale/bo/ShopsBO:setImgUrl	(Ljava/lang/String;)V
          //   829: aload 13
          //   831: aload 58
          //   833: invokevirtual 294	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   836: pop
          //   837: iinc 57 1
          //   840: goto -544 -> 296
          //   843: new 187	com/fab/sale/bo/WeeklyShopsBO
          //   846: dup
          //   847: invokespecial 188	com/fab/sale/bo/WeeklyShopsBO:<init>	()V
          //   850: astore 42
          //   852: aload 10
          //   854: iload 41
          //   856: invokevirtual 241	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   859: astore 44
          //   861: aload 44
          //   863: ifnull +721 -> 1584
          //   866: aload 42
          //   868: new 267	java/lang/StringBuilder
          //   871: dup
          //   872: ldc_w 269
          //   875: invokespecial 271	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   878: aload 44
          //   880: ldc_w 273
          //   883: invokevirtual 48	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   886: ldc_w 275
          //   889: ldc_w 296
          //   892: invokevirtual 283	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   895: invokevirtual 287	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   898: invokevirtual 288	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   901: invokevirtual 297	com/fab/sale/bo/WeeklyShopsBO:setImgUrl	(Ljava/lang/String;)V
          //   904: aload 42
          //   906: aload 44
          //   908: ldc 252
          //   910: invokevirtual 122	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   913: invokevirtual 298	com/fab/sale/bo/WeeklyShopsBO:setId	(I)V
          //   916: aload 44
          //   918: ldc 252
          //   920: invokevirtual 122	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   923: ifne +16 -> 939
          //   926: aload 42
          //   928: aload 44
          //   930: ldc_w 300
          //   933: invokevirtual 48	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   936: invokevirtual 303	com/fab/sale/bo/WeeklyShopsBO:setPopMsg	(Ljava/lang/String;)V
          //   939: aload 42
          //   941: aload 44
          //   943: ldc_w 305
          //   946: invokevirtual 48	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   949: invokevirtual 308	com/fab/sale/bo/WeeklyShopsBO:setShopName	(Ljava/lang/String;)V
          //   952: aload 42
          //   954: aload 44
          //   956: ldc_w 310
          //   959: invokevirtual 48	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   962: invokevirtual 313	com/fab/sale/bo/WeeklyShopsBO:setDay	(Ljava/lang/String;)V
          //   965: aload 39
          //   967: aload 42
          //   969: invokevirtual 294	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   972: pop
          //   973: goto +611 -> 1584
          //   976: astore 46
          //   978: aload 46
          //   980: invokevirtual 316	org/json/JSONException:printStackTrace	()V
          //   983: goto -44 -> 939
          //   986: astore 43
          //   988: goto +596 -> 1584
          //   991: new 318	com/fab/sale/bo/PairedWeeklyShops
          //   994: dup
          //   995: invokespecial 319	com/fab/sale/bo/PairedWeeklyShops:<init>	()V
          //   998: astore 52
          //   1000: aload 52
          //   1002: aload 39
          //   1004: iload 50
          //   1006: invokevirtual 323	java/util/ArrayList:get	(I)Ljava/lang/Object;
          //   1009: checkcast 187	com/fab/sale/bo/WeeklyShopsBO
          //   1012: invokevirtual 327	com/fab/sale/bo/PairedWeeklyShops:setFirstShop	(Lcom/fab/sale/bo/WeeklyShopsBO;)V
          //   1015: iload 50
          //   1017: iconst_1
          //   1018: iadd
          //   1019: istore 53
          //   1021: iload 53
          //   1023: iload 51
          //   1025: if_icmpge +40 -> 1065
          //   1028: aload 52
          //   1030: aload 39
          //   1032: iload 53
          //   1034: invokevirtual 323	java/util/ArrayList:get	(I)Ljava/lang/Object;
          //   1037: checkcast 187	com/fab/sale/bo/WeeklyShopsBO
          //   1040: invokevirtual 330	com/fab/sale/bo/PairedWeeklyShops:setSecondShop	(Lcom/fab/sale/bo/WeeklyShopsBO;)V
          //   1043: aload_0
          //   1044: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1047: getfield 94	com/fab/sale/ShopsActivity:thePairedWeeklyShops	Ljava/util/ArrayList;
          //   1050: aload 52
          //   1052: invokevirtual 294	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   1055: pop
          //   1056: iload 53
          //   1058: iconst_1
          //   1059: iadd
          //   1060: istore 50
          //   1062: goto -630 -> 432
          //   1065: aload 52
          //   1067: new 187	com/fab/sale/bo/WeeklyShopsBO
          //   1070: dup
          //   1071: invokespecial 188	com/fab/sale/bo/WeeklyShopsBO:<init>	()V
          //   1074: invokevirtual 330	com/fab/sale/bo/PairedWeeklyShops:setSecondShop	(Lcom/fab/sale/bo/WeeklyShopsBO;)V
          //   1077: goto -34 -> 1043
          //   1080: new 174	com/fab/sale/bo/ShopsBO
          //   1083: dup
          //   1084: invokespecial 175	com/fab/sale/bo/ShopsBO:<init>	()V
          //   1087: astore 22
          //   1089: aload 10
          //   1091: iload 21
          //   1093: invokevirtual 241	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   1096: astore 24
          //   1098: aload 24
          //   1100: ifnull +130 -> 1230
          //   1103: iload 21
          //   1105: ifne +28 -> 1133
          //   1108: aload_0
          //   1109: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1112: invokestatic 82	com/fab/sale/ShopsActivity:access$7	(Lcom/fab/sale/ShopsActivity;)I
          //   1115: iconst_1
          //   1116: if_icmpne +17 -> 1133
          //   1119: aload 22
          //   1121: iconst_1
          //   1122: invokevirtual 244	com/fab/sale/bo/ShopsBO:setHasHeader	(Z)V
          //   1125: aload 22
          //   1127: ldc_w 332
          //   1130: invokevirtual 250	com/fab/sale/bo/ShopsBO:setHeaderName	(Ljava/lang/String;)V
          //   1133: aload 22
          //   1135: aload 24
          //   1137: ldc 252
          //   1139: invokevirtual 122	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   1142: invokevirtual 255	com/fab/sale/bo/ShopsBO:setId	(I)V
          //   1145: aload 22
          //   1147: aload 24
          //   1149: ldc_w 257
          //   1152: invokevirtual 48	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1155: invokevirtual 260	com/fab/sale/bo/ShopsBO:setPrimaryTitle	(Ljava/lang/String;)V
          //   1158: aload 22
          //   1160: aload 24
          //   1162: ldc_w 262
          //   1165: invokevirtual 48	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1168: invokevirtual 265	com/fab/sale/bo/ShopsBO:setType	(Ljava/lang/String;)V
          //   1171: aload 22
          //   1173: aload 24
          //   1175: ldc_w 334
          //   1178: invokevirtual 48	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1181: invokevirtual 337	com/fab/sale/bo/ShopsBO:setRibbonText	(Ljava/lang/String;)V
          //   1184: aload 22
          //   1186: new 267	java/lang/StringBuilder
          //   1189: dup
          //   1190: ldc_w 269
          //   1193: invokespecial 271	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   1196: aload 24
          //   1198: ldc_w 273
          //   1201: invokevirtual 48	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1204: ldc_w 275
          //   1207: ldc_w 277
          //   1210: invokevirtual 283	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   1213: invokevirtual 287	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1216: invokevirtual 288	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   1219: invokevirtual 291	com/fab/sale/bo/ShopsBO:setImgUrl	(Ljava/lang/String;)V
          //   1222: aload 12
          //   1224: aload 22
          //   1226: invokevirtual 294	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   1229: pop
          //   1230: iinc 21 1
          //   1233: goto -727 -> 506
          //   1236: new 339	com/fab/sale/bo/PairedShops
          //   1239: dup
          //   1240: invokespecial 340	com/fab/sale/bo/PairedShops:<init>	()V
          //   1243: astore 34
          //   1245: aload 34
          //   1247: aload 12
          //   1249: iload 32
          //   1251: invokevirtual 323	java/util/ArrayList:get	(I)Ljava/lang/Object;
          //   1254: checkcast 174	com/fab/sale/bo/ShopsBO
          //   1257: invokevirtual 343	com/fab/sale/bo/PairedShops:setFirstShop	(Lcom/fab/sale/bo/ShopsBO;)V
          //   1260: iload 32
          //   1262: iconst_1
          //   1263: iadd
          //   1264: istore 35
          //   1266: iload 35
          //   1268: iload 33
          //   1270: if_icmpge +40 -> 1310
          //   1273: aload 34
          //   1275: aload 12
          //   1277: iload 35
          //   1279: invokevirtual 323	java/util/ArrayList:get	(I)Ljava/lang/Object;
          //   1282: checkcast 174	com/fab/sale/bo/ShopsBO
          //   1285: invokevirtual 345	com/fab/sale/bo/PairedShops:setSecondShop	(Lcom/fab/sale/bo/ShopsBO;)V
          //   1288: aload_0
          //   1289: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1292: getfield 86	com/fab/sale/ShopsActivity:thePairedShops	Ljava/util/ArrayList;
          //   1295: aload 34
          //   1297: invokevirtual 294	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   1300: pop
          //   1301: iload 35
          //   1303: iconst_1
          //   1304: iadd
          //   1305: istore 32
          //   1307: goto -748 -> 559
          //   1310: aload 34
          //   1312: new 174	com/fab/sale/bo/ShopsBO
          //   1315: dup
          //   1316: invokespecial 175	com/fab/sale/bo/ShopsBO:<init>	()V
          //   1319: invokevirtual 345	com/fab/sale/bo/PairedShops:setSecondShop	(Lcom/fab/sale/bo/ShopsBO;)V
          //   1322: goto -34 -> 1288
          //   1325: aload_0
          //   1326: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1329: iconst_0
          //   1330: putfield 211	com/fab/sale/ShopsActivity:nextPageExists	Z
          //   1333: aload_0
          //   1334: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1337: invokestatic 235	com/fab/sale/ShopsActivity:access$11	(Lcom/fab/sale/ShopsActivity;)Lcom/fab/views/FabListViewWithViewPager;
          //   1340: aload_0
          //   1341: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1344: invokestatic 349	com/fab/sale/ShopsActivity:access$12	(Lcom/fab/sale/ShopsActivity;)Landroid/view/View;
          //   1347: invokevirtual 353	com/fab/views/FabListViewWithViewPager:removeFooterView	(Landroid/view/View;)Z
          //   1350: pop
          //   1351: goto -720 -> 631
          //   1354: astore 14
          //   1356: aload_0
          //   1357: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1360: iconst_0
          //   1361: putfield 211	com/fab/sale/ShopsActivity:nextPageExists	Z
          //   1364: aload_0
          //   1365: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1368: invokestatic 349	com/fab/sale/ShopsActivity:access$12	(Lcom/fab/sale/ShopsActivity;)Landroid/view/View;
          //   1371: bipush 8
          //   1373: invokevirtual 356	android/view/View:setVisibility	(I)V
          //   1376: goto -745 -> 631
          //   1379: aload_3
          //   1380: invokestatic 109	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   1383: ifne -752 -> 631
          //   1386: aload_0
          //   1387: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1390: getfield 360	com/fab/sale/ShopsActivity:context	Landroid/content/Context;
          //   1393: aload_3
          //   1394: invokestatic 364	com/fab/utils/FabUtils:showAlert	(Landroid/content/Context;Ljava/lang/String;)V
          //   1397: goto -766 -> 631
          //   1400: aload_0
          //   1401: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1404: invokestatic 219	com/fab/sale/ShopsActivity:access$13	(Lcom/fab/sale/ShopsActivity;)Landroid/widget/ProgressBar;
          //   1407: bipush 8
          //   1409: invokevirtual 224	android/widget/ProgressBar:setVisibility	(I)V
          //   1412: aload_0
          //   1413: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1416: invokestatic 368	com/fab/sale/ShopsActivity:access$15	(Lcom/fab/sale/ShopsActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   1419: invokevirtual 373	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   1422: invokestatic 109	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   1425: ifne -759 -> 666
          //   1428: aload_0
          //   1429: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1432: invokestatic 228	com/fab/sale/ShopsActivity:access$14	(Lcom/fab/sale/ShopsActivity;)Lcom/fab/views/ErrorMessage;
          //   1435: iconst_0
          //   1436: invokevirtual 231	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   1439: aload_0
          //   1440: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1443: invokestatic 228	com/fab/sale/ShopsActivity:access$14	(Lcom/fab/sale/ShopsActivity;)Lcom/fab/views/ErrorMessage;
          //   1446: aload_0
          //   1447: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1450: invokestatic 368	com/fab/sale/ShopsActivity:access$15	(Lcom/fab/sale/ShopsActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   1453: invokevirtual 373	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   1456: invokevirtual 376	com/fab/views/ErrorMessage:setErrorMsg	(Ljava/lang/String;)V
          //   1459: aload_0
          //   1460: getfield 18	com/fab/sale/ShopsActivity$5:this$0	Lcom/fab/sale/ShopsActivity;
          //   1463: invokestatic 235	com/fab/sale/ShopsActivity:access$11	(Lcom/fab/sale/ShopsActivity;)Lcom/fab/views/FabListViewWithViewPager;
          //   1466: bipush 8
          //   1468: invokevirtual 238	com/fab/views/FabListViewWithViewPager:setVisibility	(I)V
          //   1471: goto -805 -> 666
          //   1474: astore 23
          //   1476: goto -246 -> 1230
          //   1479: astore 29
          //   1481: goto -259 -> 1222
          //   1484: astore 28
          //   1486: goto -302 -> 1184
          //   1489: astore 27
          //   1491: goto -320 -> 1171
          //   1494: astore 26
          //   1496: goto -338 -> 1158
          //   1499: astore 25
          //   1501: goto -356 -> 1145
          //   1504: astore 18
          //   1506: goto -1024 -> 482
          //   1509: astore 48
          //   1511: goto -546 -> 965
          //   1514: astore 47
          //   1516: goto -564 -> 952
          //   1519: astore 45
          //   1521: goto -617 -> 904
          //   1524: astore 38
          //   1526: goto -1163 -> 363
          //   1529: astore 59
          //   1531: goto -694 -> 837
          //   1534: astore 64
          //   1536: goto -707 -> 829
          //   1539: astore 63
          //   1541: goto -750 -> 791
          //   1544: astore 62
          //   1546: goto -768 -> 778
          //   1549: astore 61
          //   1551: goto -786 -> 765
          //   1554: astore 17
          //   1556: goto -1275 -> 281
          //   1559: astore 11
          //   1561: goto -1316 -> 245
          //   1564: astore 8
          //   1566: goto -1339 -> 227
          //   1569: astore 7
          //   1571: goto -1419 -> 152
          //   1574: astore 5
          //   1576: goto -1513 -> 63
          //   1579: astore 4
          //   1581: goto -1537 -> 44
          //   1584: iinc 41 1
          //   1587: goto -1200 -> 387
          //
          // Exception table:
          //   from	to	target	type
          //   904	939	976	org/json/JSONException
          //   852	861	986	org/json/JSONException
          //   965	983	986	org/json/JSONException
          //   596	631	1354	org/json/JSONException
          //   1325	1351	1354	org/json/JSONException
          //   1089	1133	1474	org/json/JSONException
          //   1222	1230	1474	org/json/JSONException
          //   1184	1222	1479	org/json/JSONException
          //   1171	1184	1484	org/json/JSONException
          //   1158	1171	1489	org/json/JSONException
          //   1145	1158	1494	org/json/JSONException
          //   1133	1145	1499	org/json/JSONException
          //   469	478	1504	org/json/JSONException
          //   952	965	1509	org/json/JSONException
          //   939	952	1514	org/json/JSONException
          //   866	904	1519	org/json/JSONException
          //   350	359	1524	org/json/JSONException
          //   710	753	1529	org/json/JSONException
          //   829	837	1529	org/json/JSONException
          //   791	829	1534	org/json/JSONException
          //   778	791	1539	org/json/JSONException
          //   765	778	1544	org/json/JSONException
          //   753	765	1549	org/json/JSONException
          //   268	277	1554	org/json/JSONException
          //   233	241	1559	org/json/JSONException
          //   152	227	1564	org/json/JSONException
          //   140	148	1569	org/json/JSONException
          //   54	63	1574	org/json/JSONException
          //   33	41	1579	org/json/JSONException
        }
      });
      ExecuteRequestAsyncTask localExecuteRequestAsyncTask = this.task;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/sale/", "?sale_type=shops&page_no=" + this.page);
      localExecuteRequestAsyncTask.execute(arrayOfString);
    }
  }

  private void refreshActivity()
  {
    this.page = 1;
    FabHelper.smoothScroll(this.listView);
    if ((this.viewPager != null) && (this.viewPager.getChildCount() > 0))
      this.viewPager.setCurrentItem(0, true);
    this.timer.setVisibility(4);
    FabHelper.stopAndRestartCartTimer(getApplicationContext(), 0L, this.mHandler);
    this.listView.setVisibility(8);
    this.error.setVisibility(8);
    this.progressBar.setVisibility(0);
    loadShopDetails();
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903095);
    isActivityAlive = true;
    this.context = this;
    if (!FabUtils.isLoggedIn(this.context))
    {
      finish();
      Intent localIntent = new Intent(this.context, FabIndex.class);
      localIntent.setFlags(67108864);
      startActivity(localIntent);
    }
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("salesLanding/Shops"));
    this.timer = ((FabTextView)findViewById(2131361795));
    this.timer.setFont("georgiai.ttf");
    this.listView = ((FabListViewWithViewPager)findViewById(2131362031));
    this.listView.setVisibility(8);
    this.listView.setDividerHeight(0);
    this.error = ((ErrorMessage)findViewById(2131361811));
    FabSharedPrefs.initializePreferences(this.context);
    this.alertbox = new AlertDialog.Builder(this);
    this.listView.setSelector(17170445);
    this.progressBar = ((ProgressBar)findViewById(2131361874));
    LayoutInflater localLayoutInflater = (LayoutInflater)this.context.getSystemService("layout_inflater");
    this.footerView = localLayoutInflater.inflate(2130903067, null);
    this.listView.addFooterView(this.footerView);
    this.weeklyShopsView = localLayoutInflater.inflate(2130903109, null);
    this.listView.addHeaderView(this.weeklyShopsView);
    this.weeklyHeaderTitle = ((FabTextView)this.weeklyShopsView.findViewById(2131362063));
    this.weeklyHeaderTitle.setFont("HelveticaNeu_bold.ttf");
    this.weeklyHeaderTitle.setText(Html.fromHtml("<font color=\"#FF0000\">Weekly </font> Shops"));
    this.viewPager = ((ViewPager)this.weeklyShopsView.findViewById(2131362094));
    this.weeklyShopsAdapter = new WeeklyShopsAdapter(this.context, this.thePairedWeeklyShops);
    this.viewPager.setAdapter(this.weeklyShopsAdapter);
    this.dot1 = ((ImageView)this.weeklyShopsView.findViewById(2131362098));
    this.dot1.setImageResource(2130837717);
    this.dot2 = ((ImageView)this.weeklyShopsView.findViewById(2131362099));
    this.dot3 = ((ImageView)this.weeklyShopsView.findViewById(2131362100));
    this.viewPager.setCurrentItem(0, true);
    this.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
    {
      public void onPageScrollStateChanged(int paramAnonymousInt)
      {
      }

      public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2)
      {
      }

      public void onPageSelected(int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        default:
        case 0:
        case 1:
        case 2:
        }
        while (true)
        {
          return;
          ShopsActivity.this.dot1.setImageResource(2130837717);
          ShopsActivity.this.dot2.setImageResource(2130837625);
          ShopsActivity.this.dot3.setImageResource(2130837625);
          continue;
          ShopsActivity.this.dot1.setImageResource(2130837625);
          ShopsActivity.this.dot2.setImageResource(2130837717);
          ShopsActivity.this.dot3.setImageResource(2130837625);
          continue;
          ShopsActivity.this.dot1.setImageResource(2130837625);
          ShopsActivity.this.dot2.setImageResource(2130837625);
          ShopsActivity.this.dot3.setImageResource(2130837717);
        }
      }
    });
    this.shopsAdapter = new ShopsAdapter(this.context, this.thePairedShops);
    this.listView.setAdapter(this.shopsAdapter);
    this.listView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        FabUtils.log(ShopsActivity.this.TAG, "firstVisibleItem=" + paramAnonymousInt1 + "::visibleItemCount=" + paramAnonymousInt2 + "::totalItemCount=" + paramAnonymousInt3);
        int i = paramAnonymousInt1 + paramAnonymousInt2;
        if ((i != paramAnonymousInt3) || ((i == paramAnonymousInt3) && (!ShopsActivity.this.loadingMore) && (ShopsActivity.this.nextPageExists)))
        {
          if (!Calendar.getInstance().after(Long.valueOf(FabSharedPrefs.getNextSaleTs())))
            break label107;
          ShopsActivity.this.refreshActivity();
        }
        while (true)
        {
          return;
          label107: ShopsActivity.this.loadShopDetails();
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
    FabHome.titleWidget.getFabLogo().setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (ShopsActivity.this.activityLoading == 2)
          ShopsActivity.this.refreshActivity();
      }
    });
    if (!this.isTabBuilding)
      loadShopDetails();
    this.headerCartButton = FabHome.titleWidget.getCartButton();
    this.mHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Bundle localBundle = paramAnonymousMessage.getData();
        if (FabUtils.isLogEnabled())
          FabUtils.log(ShopsActivity.this.TAG, localBundle.toString());
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
            ShopsActivity.this.timer.setVisibility(4);
          if (ShopsActivity.isActivityAlive)
          {
            AlertDialog.Builder localBuilder = ShopsActivity.this.alertbox;
            if (localBundle.getInt("Error") == 1);
            for (int j = 2130837504; ; j = 2130837507)
            {
              localBuilder.setIcon(j);
              ShopsActivity.this.alertbox.setTitle(localBundle.getString("Title"));
              ShopsActivity.this.alertbox.setMessage(localBundle.getString("Message"));
              ShopsActivity.this.alertbox.setNeutralButton("OK", null);
              if (!ShopsActivity.isActivityAlive)
                break;
              ShopsActivity.this.alertbox.show();
              break;
            }
            ShopsActivity.this.timer.setVisibility(0);
            ShopsActivity.this.timer.setText(str);
          }
        }
      }
    };
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
        Intent localIntent = new Intent(ShopsActivity.this.context, MoreActivity.class);
        ShopsActivity.this.startActivity(localIntent);
        return false;
      }
    });
    MenuItem localMenuItem2 = paramMenu.findItem(2131362109);
    localMenuItem2.setVisible(true);
    localMenuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(ShopsActivity.this.context, FabWebViewActivity.class);
        ShopsActivity.this.startActivity(localIntent);
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
          FabUtils.showGuestAlert(ShopsActivity.this.context, ShopsActivity.this.context.getResources().getString(2131165208), ShopsActivity.this.context.getResources().getString(2131165209), false);
        while (true)
        {
          return false;
          Intent localIntent = new Intent(ShopsActivity.this.context, InviteActivity.class);
          ShopsActivity.this.startActivity(localIntent);
        }
      }
    });
    MenuItem localMenuItem4 = paramMenu.findItem(2131362103);
    localMenuItem4.setVisible(true);
    localMenuItem4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        ShopsActivity.this.refreshActivity();
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

  protected void onPause()
  {
    isActivityAlive = false;
    if (this.shopsAdapter != null)
    {
      this.shopsAdapter.stopImageDownloads();
      this.shopsAdapter.clearImageCache();
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
        if (ShopsActivity.this.activityLoading == 2)
          ShopsActivity.this.refreshActivity();
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
        if (ShopsActivity.this.activityLoading == 2)
          ShopsActivity.this.refreshActivity();
      }
    });
    isActivityAlive = true;
    try
    {
      if (Calendar.getInstance().after(Long.valueOf(FabSharedPrefs.getNextSaleTs())))
        refreshActivity();
      label82: this.error.setVisibility(8);
      if (this.thePairedShops.isEmpty())
      {
        this.thePairedWeeklyShops.clear();
        this.page = 1;
        loadShopDetails();
      }
      if ((this.viewPager != null) && (this.viewPager.getChildCount() > 0))
        this.viewPager.setCurrentItem(0, true);
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
 * Qualified Name:     com.fab.sale.ShopsActivity
 * JD-Core Version:    0.6.2
 */