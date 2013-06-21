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

public class SalesActivity extends Activity
{
  private static final String TAG = "SALES_LIST";
  protected static boolean isActivityAlive;
  public int activityLoading = 0;
  private SalesAdapter adapter;
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
          //   1: ifnull +727 -> 728
          //   4: aload_1
          //   5: instanceof 26
          //   8: ifeq +720 -> 728
          //   11: aload_1
          //   12: checkcast 26	org/json/JSONObject
          //   15: astore_2
          //   16: aload_0
          //   17: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   20: iconst_2
          //   21: putfield 30	com/fab/sale/SalesActivity:activityLoading	I
          //   24: ldc 32
          //   26: aload_2
          //   27: invokevirtual 36	org/json/JSONObject:toString	()Ljava/lang/String;
          //   30: invokestatic 42	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
          //   33: iconst_0
          //   34: istore_3
          //   35: aload_2
          //   36: ldc 44
          //   38: invokevirtual 48	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   41: istore 33
          //   43: iload 33
          //   45: istore_3
          //   46: getstatic 54	com/fab/FabHome:titleWidget	Lcom/fab/views/TitleWidget;
          //   49: invokevirtual 60	com/fab/views/TitleWidget:getFabLogo	()Landroid/widget/ImageView;
          //   52: iconst_1
          //   53: invokevirtual 66	android/widget/ImageView:setEnabled	(Z)V
          //   56: aload_0
          //   57: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   60: aload_2
          //   61: ldc 68
          //   63: invokevirtual 72	org/json/JSONObject:getLong	(Ljava/lang/String;)J
          //   66: putfield 76	com/fab/sale/SalesActivity:nextSaleTs	J
          //   69: aload_0
          //   70: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   73: getfield 76	com/fab/sale/SalesActivity:nextSaleTs	J
          //   76: invokestatic 82	com/fab/utils/FabSharedPrefs:setNextSaleTs	(J)V
          //   79: iload_3
          //   80: ifeq +21 -> 101
          //   83: aload_0
          //   84: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   87: invokestatic 86	com/fab/sale/SalesActivity:access$4	(Lcom/fab/sale/SalesActivity;)Landroid/content/Context;
          //   90: aconst_null
          //   91: invokestatic 90	com/fab/utils/FabUtils:logoutUser	(Landroid/content/Context;Landroid/os/Handler;)V
          //   94: aload_0
          //   95: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   98: invokevirtual 93	com/fab/sale/SalesActivity:finish	()V
          //   101: ldc 95
          //   103: astore 6
          //   105: aload_2
          //   106: ldc 97
          //   108: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   111: astore 32
          //   113: aload 32
          //   115: astore 6
          //   117: aload_0
          //   118: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   121: invokestatic 105	com/fab/sale/SalesActivity:access$5	(Lcom/fab/sale/SalesActivity;)I
          //   124: iconst_1
          //   125: if_icmpne +23 -> 148
          //   128: aload_0
          //   129: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   132: invokestatic 109	com/fab/sale/SalesActivity:access$6	(Lcom/fab/sale/SalesActivity;)Ljava/util/ArrayList;
          //   135: ifnull +275 -> 410
          //   138: aload_0
          //   139: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   142: invokestatic 109	com/fab/sale/SalesActivity:access$6	(Lcom/fab/sale/SalesActivity;)Ljava/util/ArrayList;
          //   145: invokevirtual 114	java/util/ArrayList:clear	()V
          //   148: aload 6
          //   150: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   153: ifeq +498 -> 651
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
          //   180: astore 31
          //   182: aload 31
          //   184: astore 9
          //   186: aload_0
          //   187: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   190: aload 9
          //   192: ldc 132
          //   194: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   197: invokevirtual 140	com/fab/sale/SalesActivity:updateCartCounter	(I)V
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
          //   237: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   240: invokevirtual 160	com/fab/sale/SalesActivity:getApplicationContext	()Landroid/content/Context;
          //   243: aload 9
          //   245: ldc 142
          //   247: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   250: i2l
          //   251: aload_0
          //   252: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   255: invokestatic 164	com/fab/sale/SalesActivity:access$8	(Lcom/fab/sale/SalesActivity;)Landroid/os/Handler;
          //   258: invokestatic 168	com/fab/utils/FabHelper:stopAndRestartCartTimer	(Landroid/content/Context;JLandroid/os/Handler;)V
          //   261: aload_0
          //   262: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   265: iconst_0
          //   266: invokestatic 172	com/fab/sale/SalesActivity:access$9	(Lcom/fab/sale/SalesActivity;Z)V
          //   269: aload_2
          //   270: ldc 174
          //   272: invokevirtual 178	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   275: astore 30
          //   277: aload 30
          //   279: astore 8
          //   281: iconst_0
          //   282: istore 13
          //   284: aload 8
          //   286: invokevirtual 182	org/json/JSONArray:length	()I
          //   289: istore 14
          //   291: iload 13
          //   293: iload 14
          //   295: if_icmplt +132 -> 427
          //   298: aload_0
          //   299: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   302: invokestatic 186	com/fab/sale/SalesActivity:access$11	(Lcom/fab/sale/SalesActivity;)Lcom/fab/sale/SalesAdapter;
          //   305: ifnull +23 -> 328
          //   308: aload_0
          //   309: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   312: invokestatic 186	com/fab/sale/SalesActivity:access$11	(Lcom/fab/sale/SalesActivity;)Lcom/fab/sale/SalesAdapter;
          //   315: invokevirtual 191	com/fab/sale/SalesAdapter:downloadTheImages	()V
          //   318: aload_0
          //   319: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   322: invokestatic 186	com/fab/sale/SalesActivity:access$11	(Lcom/fab/sale/SalesActivity;)Lcom/fab/sale/SalesAdapter;
          //   325: invokevirtual 194	com/fab/sale/SalesAdapter:notifyDataSetChanged	()V
          //   328: aload_2
          //   329: ldc 196
          //   331: invokevirtual 48	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   334: ifeq +340 -> 674
          //   337: aload_0
          //   338: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   341: iconst_1
          //   342: putfield 200	com/fab/sale/SalesActivity:nextPageExists	Z
          //   345: aload_0
          //   346: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   349: astore 29
          //   351: aload 29
          //   353: iconst_1
          //   354: aload 29
          //   356: invokestatic 105	com/fab/sale/SalesActivity:access$5	(Lcom/fab/sale/SalesActivity;)I
          //   359: iadd
          //   360: invokestatic 204	com/fab/sale/SalesActivity:access$12	(Lcom/fab/sale/SalesActivity;I)V
          //   363: aload_0
          //   364: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   367: invokestatic 208	com/fab/sale/SalesActivity:access$13	(Lcom/fab/sale/SalesActivity;)Landroid/view/View;
          //   370: iconst_0
          //   371: invokevirtual 213	android/view/View:setVisibility	(I)V
          //   374: aload_0
          //   375: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   378: invokestatic 217	com/fab/sale/SalesActivity:access$14	(Lcom/fab/sale/SalesActivity;)Landroid/widget/ListView;
          //   381: iconst_0
          //   382: invokevirtual 220	android/widget/ListView:setVisibility	(I)V
          //   385: aload_0
          //   386: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   389: invokestatic 224	com/fab/sale/SalesActivity:access$15	(Lcom/fab/sale/SalesActivity;)Landroid/widget/ProgressBar;
          //   392: bipush 8
          //   394: invokevirtual 227	android/widget/ProgressBar:setVisibility	(I)V
          //   397: aload_0
          //   398: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   401: invokestatic 231	com/fab/sale/SalesActivity:access$16	(Lcom/fab/sale/SalesActivity;)Lcom/fab/views/ErrorMessage;
          //   404: bipush 8
          //   406: invokevirtual 234	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   409: return
          //   410: aload_0
          //   411: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   414: new 111	java/util/ArrayList
          //   417: dup
          //   418: invokespecial 235	java/util/ArrayList:<init>	()V
          //   421: invokestatic 239	com/fab/sale/SalesActivity:access$7	(Lcom/fab/sale/SalesActivity;Ljava/util/ArrayList;)V
          //   424: goto -276 -> 148
          //   427: new 241	com/fab/sale/bo/SalesBO
          //   430: dup
          //   431: invokespecial 242	com/fab/sale/bo/SalesBO:<init>	()V
          //   434: astore 15
          //   436: new 26	org/json/JSONObject
          //   439: dup
          //   440: invokespecial 124	org/json/JSONObject:<init>	()V
          //   443: astore 16
          //   445: aload 8
          //   447: iload 13
          //   449: invokevirtual 245	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   452: astore 26
          //   454: aload 26
          //   456: astore 16
          //   458: aload 15
          //   460: aload 16
          //   462: ldc 247
          //   464: invokevirtual 136	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   467: invokevirtual 250	com/fab/sale/bo/SalesBO:setSaleId	(I)V
          //   470: aload_0
          //   471: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   474: invokestatic 254	com/fab/sale/SalesActivity:access$10	(Lcom/fab/sale/SalesActivity;)Z
          //   477: ifne +165 -> 642
          //   480: aload_0
          //   481: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   484: invokestatic 105	com/fab/sale/SalesActivity:access$5	(Lcom/fab/sale/SalesActivity;)I
          //   487: iconst_1
          //   488: if_icmpne +154 -> 642
          //   491: aload 15
          //   493: iconst_1
          //   494: invokevirtual 257	com/fab/sale/bo/SalesBO:setHeader	(Z)V
          //   497: aload_0
          //   498: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   501: iconst_1
          //   502: invokestatic 172	com/fab/sale/SalesActivity:access$9	(Lcom/fab/sale/SalesActivity;Z)V
          //   505: aload 15
          //   507: aload 16
          //   509: ldc_w 259
          //   512: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   515: invokevirtual 263	com/fab/sale/bo/SalesBO:setSaleName	(Ljava/lang/String;)V
          //   518: aload 16
          //   520: ldc_w 265
          //   523: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   526: astore 25
          //   528: aload 15
          //   530: aload 25
          //   532: invokevirtual 268	com/fab/sale/bo/SalesBO:setType	(Ljava/lang/String;)V
          //   535: ldc_w 270
          //   538: aload 25
          //   540: invokevirtual 275	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
          //   543: ifeq +16 -> 559
          //   546: aload 15
          //   548: aload 16
          //   550: ldc_w 277
          //   553: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   556: invokevirtual 280	com/fab/sale/bo/SalesBO:setRibbonText	(Ljava/lang/String;)V
          //   559: aload 15
          //   561: aload 16
          //   563: ldc_w 282
          //   566: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   569: invokevirtual 285	com/fab/sale/bo/SalesBO:setPrimaryTitle	(Ljava/lang/String;)V
          //   572: aload 15
          //   574: new 287	java/lang/StringBuilder
          //   577: dup
          //   578: ldc_w 289
          //   581: invokespecial 291	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   584: aload 16
          //   586: ldc_w 293
          //   589: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   592: ldc_w 295
          //   595: ldc_w 297
          //   598: invokevirtual 301	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   601: invokevirtual 305	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   604: invokevirtual 306	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   607: invokevirtual 309	com/fab/sale/bo/SalesBO:setSaleImageUrl	(Ljava/lang/String;)V
          //   610: aload 15
          //   612: aload 16
          //   614: ldc_w 311
          //   617: invokevirtual 101	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   620: invokevirtual 314	com/fab/sale/bo/SalesBO:setSaleDuration	(Ljava/lang/String;)V
          //   623: aload_0
          //   624: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   627: invokestatic 109	com/fab/sale/SalesActivity:access$6	(Lcom/fab/sale/SalesActivity;)Ljava/util/ArrayList;
          //   630: aload 15
          //   632: invokevirtual 318	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   635: pop
          //   636: iinc 13 1
          //   639: goto -348 -> 291
          //   642: aload 15
          //   644: iconst_0
          //   645: invokevirtual 257	com/fab/sale/bo/SalesBO:setHeader	(Z)V
          //   648: goto -143 -> 505
          //   651: aload 6
          //   653: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   656: ifne -358 -> 298
          //   659: aload_0
          //   660: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   663: invokestatic 86	com/fab/sale/SalesActivity:access$4	(Lcom/fab/sale/SalesActivity;)Landroid/content/Context;
          //   666: aload 6
          //   668: invokestatic 322	com/fab/utils/FabUtils:showAlert	(Landroid/content/Context;Ljava/lang/String;)V
          //   671: goto -373 -> 298
          //   674: aload_0
          //   675: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   678: iconst_0
          //   679: putfield 200	com/fab/sale/SalesActivity:nextPageExists	Z
          //   682: aload_0
          //   683: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   686: invokestatic 217	com/fab/sale/SalesActivity:access$14	(Lcom/fab/sale/SalesActivity;)Landroid/widget/ListView;
          //   689: aload_0
          //   690: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   693: invokestatic 208	com/fab/sale/SalesActivity:access$13	(Lcom/fab/sale/SalesActivity;)Landroid/view/View;
          //   696: invokevirtual 326	android/widget/ListView:removeFooterView	(Landroid/view/View;)Z
          //   699: pop
          //   700: goto -326 -> 374
          //   703: astore 27
          //   705: aload_0
          //   706: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   709: iconst_0
          //   710: putfield 200	com/fab/sale/SalesActivity:nextPageExists	Z
          //   713: aload_0
          //   714: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   717: invokestatic 208	com/fab/sale/SalesActivity:access$13	(Lcom/fab/sale/SalesActivity;)Landroid/view/View;
          //   720: bipush 8
          //   722: invokevirtual 213	android/view/View:setVisibility	(I)V
          //   725: goto -351 -> 374
          //   728: aload_0
          //   729: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   732: invokestatic 224	com/fab/sale/SalesActivity:access$15	(Lcom/fab/sale/SalesActivity;)Landroid/widget/ProgressBar;
          //   735: bipush 8
          //   737: invokevirtual 227	android/widget/ProgressBar:setVisibility	(I)V
          //   740: aload_0
          //   741: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   744: invokestatic 330	com/fab/sale/SalesActivity:access$17	(Lcom/fab/sale/SalesActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   747: invokevirtual 335	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   750: invokestatic 120	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   753: ifne -344 -> 409
          //   756: aload_0
          //   757: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   760: invokestatic 231	com/fab/sale/SalesActivity:access$16	(Lcom/fab/sale/SalesActivity;)Lcom/fab/views/ErrorMessage;
          //   763: iconst_0
          //   764: invokevirtual 234	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   767: aload_0
          //   768: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   771: invokestatic 231	com/fab/sale/SalesActivity:access$16	(Lcom/fab/sale/SalesActivity;)Lcom/fab/views/ErrorMessage;
          //   774: aload_0
          //   775: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   778: invokestatic 330	com/fab/sale/SalesActivity:access$17	(Lcom/fab/sale/SalesActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   781: invokevirtual 335	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   784: invokevirtual 338	com/fab/views/ErrorMessage:setErrorMsg	(Ljava/lang/String;)V
          //   787: aload_0
          //   788: getfield 18	com/fab/sale/SalesActivity$10:this$0	Lcom/fab/sale/SalesActivity;
          //   791: invokestatic 217	com/fab/sale/SalesActivity:access$14	(Lcom/fab/sale/SalesActivity;)Landroid/widget/ListView;
          //   794: bipush 8
          //   796: invokevirtual 220	android/widget/ListView:setVisibility	(I)V
          //   799: goto -390 -> 409
          //   802: astore 17
          //   804: goto -346 -> 458
          //   807: astore 23
          //   809: goto -186 -> 623
          //   812: astore 22
          //   814: goto -204 -> 610
          //   817: astore 21
          //   819: goto -247 -> 572
          //   822: astore 20
          //   824: goto -265 -> 559
          //   827: astore 19
          //   829: goto -311 -> 518
          //   832: astore 18
          //   834: goto -364 -> 470
          //   837: astore 12
          //   839: goto -558 -> 281
          //   842: astore 11
          //   844: goto -583 -> 261
          //   847: astore 10
          //   849: goto -663 -> 186
          //   852: astore 7
          //   854: goto -737 -> 117
          //   857: astore 5
          //   859: goto -780 -> 79
          //   862: astore 4
          //   864: goto -818 -> 46
          //
          // Exception table:
          //   from	to	target	type
          //   328	374	703	org/json/JSONException
          //   674	700	703	org/json/JSONException
          //   445	454	802	org/json/JSONException
          //   610	623	807	org/json/JSONException
          //   572	610	812	org/json/JSONException
          //   559	572	817	org/json/JSONException
          //   518	559	822	org/json/JSONException
          //   505	518	827	org/json/JSONException
          //   458	470	832	org/json/JSONException
          //   269	277	837	org/json/JSONException
          //   186	261	842	org/json/JSONException
          //   174	182	847	org/json/JSONException
          //   105	113	852	org/json/JSONException
          //   56	79	857	org/json/JSONException
          //   35	43	862	org/json/JSONException
        }
      });
      ExecuteRequestAsyncTask localExecuteRequestAsyncTask = this.task;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/sale/", "?sale_type=current&page_no=" + this.page);
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
        if ((i != paramAnonymousInt3) || ((i == paramAnonymousInt3) && (!SalesActivity.this.loadingMore) && (SalesActivity.this.nextPageExists)))
        {
          if (!Calendar.getInstance().after(Long.valueOf(FabSharedPrefs.getNextSaleTs())))
            break label102;
          SalesActivity.this.refreshActivity();
        }
        while (true)
        {
          return;
          label102: SalesActivity.this.loadSales();
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
    this.adapter = new SalesAdapter(this.context, this.theSales);
    this.listView.setAdapter(this.adapter);
    FabHome.titleWidget.getFabLogo().setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (SalesActivity.this.activityLoading == 2)
          SalesActivity.this.refreshActivity();
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
            SalesActivity.this.timer.setVisibility(4);
          if (SalesActivity.isActivityAlive)
          {
            AlertDialog.Builder localBuilder = SalesActivity.this.alertbox;
            if (localBundle.getInt("Error") == 1);
            for (int j = 2130837504; ; j = 2130837507)
            {
              localBuilder.setIcon(j);
              SalesActivity.this.alertbox.setTitle(localBundle.getString("Title"));
              SalesActivity.this.alertbox.setMessage(localBundle.getString("Message"));
              SalesActivity.this.alertbox.setNeutralButton("OK", null);
              if (!SalesActivity.isActivityAlive)
                break;
              SalesActivity.this.alertbox.show();
              break;
            }
            SalesActivity.this.timer.setVisibility(0);
            SalesActivity.this.timer.setText(str);
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
        Intent localIntent = new Intent(SalesActivity.this.context, MoreActivity.class);
        SalesActivity.this.startActivity(localIntent);
        return false;
      }
    });
    MenuItem localMenuItem2 = paramMenu.findItem(2131362109);
    localMenuItem2.setVisible(true);
    localMenuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(SalesActivity.this.context, FabWebViewActivity.class);
        SalesActivity.this.startActivity(localIntent);
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
          FabUtils.showGuestAlert(SalesActivity.this.context, SalesActivity.this.context.getResources().getString(2131165208), SalesActivity.this.context.getResources().getString(2131165209), false);
        while (true)
        {
          return false;
          Intent localIntent = new Intent(SalesActivity.this.context, InviteActivity.class);
          SalesActivity.this.startActivity(localIntent);
        }
      }
    });
    MenuItem localMenuItem4 = paramMenu.findItem(2131362103);
    localMenuItem4.setVisible(true);
    localMenuItem4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        SalesActivity.this.refreshActivity();
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
        if (SalesActivity.this.activityLoading == 2)
          SalesActivity.this.refreshActivity();
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
        if (SalesActivity.this.activityLoading == 2)
          SalesActivity.this.refreshActivity();
      }
    });
    isActivityAlive = true;
    try
    {
      if (Calendar.getInstance().after(Long.valueOf(FabSharedPrefs.getNextSaleTs())))
        refreshActivity();
      label82: this.error.setVisibility(8);
      if (this.theSales.isEmpty())
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
 * Qualified Name:     com.fab.sale.SalesActivity
 * JD-Core Version:    0.6.2
 */