package com.fab.sale;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.fab.FabApplication;
import com.fab.FabIndex;
import com.fab.InviteActivity;
import com.fab.MoreActivity;
import com.fab.sale.bo.PairedProducts;
import com.fab.sale.bo.ProductDetailsBO;
import com.fab.sale.bo.ShopSortBo;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabHelper;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.ErrorMessage;
import com.fab.views.FabButton;
import com.fab.views.FabShopsDialog;
import com.fab.views.FabTextView;
import com.fab.views.TitleWidget;
import com.fab.webviews.FabWebViewActivity;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class ShopDetailsActivity extends Activity
{
  protected static boolean isActivityAlive;
  protected String TAG = "ShopsDetailsActivity";
  private ShopsDetailAdapter adapter;
  private AlertDialog.Builder alertbox;
  private FabButton btnSort;
  public Context context;
  private ErrorMessage error;
  private Bundle extras;
  private String filter;
  private View footerView;
  private FabButton headerCartButton;
  private ListView listView;
  private boolean loadingMore;
  private Handler mHandler;
  public boolean nextPageExists;
  private int page;
  private HashMap<String, String> parameters;
  private StringBuilder prodIdsStringBulder = new StringBuilder();
  private RelativeLayout progressBar;
  private RelativeLayout shopDetailsRel;
  private int shopId;
  private RelativeLayout sortRel;
  private ExecuteRequestAsyncTask task;
  public ArrayList<PairedProducts> thePairedShopProducts = new ArrayList();
  public ArrayList<String> theProdIDs;
  public ArrayList<ProductDetailsBO> theProducts = new ArrayList();
  public ArrayList<ShopSortBo> theSortOptions;
  protected FabTextView timer;
  private TitleWidget titleWidget;
  private GoogleAnalyticsTracker tracker;
  private FabTextView tvShopName;
  private FabTextView tvSortedBY;

  private String getTheProdIDs()
  {
    Iterator localIterator;
    if ((this.theProdIDs != null) && (!this.theProdIDs.isEmpty()))
    {
      localIterator = this.theProdIDs.iterator();
      if (localIterator.hasNext());
    }
    for (String str1 = this.prodIdsStringBulder.toString(); ; str1 = "")
    {
      return str1;
      String str2 = (String)localIterator.next();
      if (this.prodIdsStringBulder.length() > 0)
        this.prodIdsStringBulder.append(",");
      this.prodIdsStringBulder.append(str2);
      break;
    }
  }

  private void loadShopDetails()
  {
    if (this.btnSort != null)
      this.btnSort.setEnabled(false);
    if ((this.task == null) || ((this.task != null) && (this.task.getStatus() != AsyncTask.Status.RUNNING)))
    {
      this.task = new ExecuteRequestAsyncTask(this.context, "POST");
      this.parameters = new HashMap();
      this.parameters.put("d", FabSharedPrefs.getDeviceDensity());
      this.parameters.put("app_version", FabApplication.APP_VERSION);
      this.parameters.put("device_model", Build.MODEL);
      this.parameters.put("device_os", Build.VERSION.RELEASE);
      this.parameters.put("page_no", this.page);
      if (!TextUtils.isEmpty(getTheProdIDs()))
        this.parameters.put("newIdes", getTheProdIDs());
      if (!TextUtils.isEmpty(this.filter))
        this.parameters.put("filter", this.filter);
      this.task.setParameters(this.parameters);
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
          //   1: ifnull +1398 -> 1399
          //   4: aload_1
          //   5: instanceof 26
          //   8: ifeq +1391 -> 1399
          //   11: aload_1
          //   12: checkcast 26	org/json/JSONObject
          //   15: astore_2
          //   16: ldc 28
          //   18: astore_3
          //   19: aload_2
          //   20: ldc 30
          //   22: invokevirtual 34	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   25: astore 70
          //   27: aload 70
          //   29: astore_3
          //   30: aload_0
          //   31: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   34: getfield 38	com/fab/sale/ShopDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   37: ifnull +548 -> 585
          //   40: aload_0
          //   41: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   44: getfield 38	com/fab/sale/ShopDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   47: invokevirtual 43	java/util/ArrayList:clear	()V
          //   50: aload_0
          //   51: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   54: invokestatic 47	com/fab/sale/ShopDetailsActivity:access$7	(Lcom/fab/sale/ShopDetailsActivity;)I
          //   57: iconst_1
          //   58: if_icmpne +37 -> 95
          //   61: aload_0
          //   62: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   65: getfield 50	com/fab/sale/ShopDetailsActivity:thePairedShopProducts	Ljava/util/ArrayList;
          //   68: ifnull +534 -> 602
          //   71: aload_0
          //   72: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   75: getfield 50	com/fab/sale/ShopDetailsActivity:thePairedShopProducts	Ljava/util/ArrayList;
          //   78: invokevirtual 43	java/util/ArrayList:clear	()V
          //   81: aload_0
          //   82: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   85: new 40	java/util/ArrayList
          //   88: dup
          //   89: invokespecial 51	java/util/ArrayList:<init>	()V
          //   92: putfield 54	com/fab/sale/ShopDetailsActivity:theProdIDs	Ljava/util/ArrayList;
          //   95: aload_3
          //   96: invokestatic 60	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   99: ifeq +1141 -> 1240
          //   102: new 26	org/json/JSONObject
          //   105: dup
          //   106: invokespecial 61	org/json/JSONObject:<init>	()V
          //   109: astore 5
          //   111: aload_2
          //   112: ldc 63
          //   114: invokevirtual 67	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   117: astore 66
          //   119: aload 66
          //   121: astore 5
          //   123: aload_0
          //   124: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   127: aload 5
          //   129: ldc 69
          //   131: invokevirtual 73	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   134: invokevirtual 77	com/fab/sale/ShopDetailsActivity:updateCartCounter	(I)V
          //   137: aload 5
          //   139: ldc 79
          //   141: invokevirtual 73	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   144: i2l
          //   145: new 81	java/util/GregorianCalendar
          //   148: dup
          //   149: invokespecial 82	java/util/GregorianCalendar:<init>	()V
          //   152: invokestatic 88	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
          //   155: ldc2_w 89
          //   158: lcmp
          //   159: iflt +39 -> 198
          //   162: aload 5
          //   164: ldc 79
          //   166: invokevirtual 73	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   169: i2l
          //   170: invokestatic 96	com/fab/utils/FabHelper:setCartTime	(J)V
          //   173: aload_0
          //   174: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   177: invokevirtual 100	com/fab/sale/ShopDetailsActivity:getApplicationContext	()Landroid/content/Context;
          //   180: aload 5
          //   182: ldc 79
          //   184: invokevirtual 73	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   187: i2l
          //   188: aload_0
          //   189: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   192: invokestatic 104	com/fab/sale/ShopDetailsActivity:access$8	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/os/Handler;
          //   195: invokestatic 108	com/fab/utils/FabHelper:stopAndRestartCartTimer	(Landroid/content/Context;JLandroid/os/Handler;)V
          //   198: aload_2
          //   199: ldc 110
          //   201: invokevirtual 34	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   204: astore 65
          //   206: aload_0
          //   207: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   210: invokestatic 114	com/fab/sale/ShopDetailsActivity:access$9	(Lcom/fab/sale/ShopDetailsActivity;)Lcom/fab/views/FabTextView;
          //   213: new 116	java/lang/StringBuilder
          //   216: dup
          //   217: aload 65
          //   219: iconst_0
          //   220: iconst_1
          //   221: invokevirtual 122	java/lang/String:substring	(II)Ljava/lang/String;
          //   224: invokevirtual 126	java/lang/String:toUpperCase	()Ljava/lang/String;
          //   227: invokestatic 130	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
          //   230: invokespecial 133	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   233: aload 65
          //   235: iconst_1
          //   236: aload 65
          //   238: invokevirtual 137	java/lang/String:length	()I
          //   241: invokevirtual 122	java/lang/String:substring	(II)Ljava/lang/String;
          //   244: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   247: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   250: invokevirtual 150	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
          //   253: aconst_null
          //   254: astore 9
          //   256: new 26	org/json/JSONObject
          //   259: dup
          //   260: invokespecial 61	org/json/JSONObject:<init>	()V
          //   263: astore 10
          //   265: aload_2
          //   266: ldc 152
          //   268: invokevirtual 156	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   271: astore 64
          //   273: aload 64
          //   275: astore 9
          //   277: aload 9
          //   279: ifnull +85 -> 364
          //   282: iconst_0
          //   283: istore 42
          //   285: aload 9
          //   287: invokevirtual 159	org/json/JSONArray:length	()I
          //   290: istore 43
          //   292: iload 42
          //   294: iload 43
          //   296: if_icmplt +323 -> 619
          //   299: aload_0
          //   300: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   303: getfield 38	com/fab/sale/ShopDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   306: invokevirtual 162	java/util/ArrayList:size	()I
          //   309: iconst_2
          //   310: irem
          //   311: iconst_1
          //   312: if_icmpne +30 -> 342
          //   315: aload_0
          //   316: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   319: getfield 38	com/fab/sale/ShopDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   322: aload_0
          //   323: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   326: getfield 38	com/fab/sale/ShopDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   329: invokevirtual 162	java/util/ArrayList:size	()I
          //   332: new 164	com/fab/sale/bo/ProductDetailsBO
          //   335: dup
          //   336: invokespecial 165	com/fab/sale/bo/ProductDetailsBO:<init>	()V
          //   339: invokevirtual 169	java/util/ArrayList:add	(ILjava/lang/Object;)V
          //   342: iconst_0
          //   343: istore 59
          //   345: aload_0
          //   346: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   349: getfield 38	com/fab/sale/ShopDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   352: invokevirtual 162	java/util/ArrayList:size	()I
          //   355: istore 60
          //   357: iload 59
          //   359: iload 60
          //   361: if_icmplt +500 -> 861
          //   364: aload_0
          //   365: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   368: invokestatic 173	com/fab/sale/ShopDetailsActivity:access$10	(Lcom/fab/sale/ShopDetailsActivity;)Lcom/fab/sale/ShopsDetailAdapter;
          //   371: ifnull +13 -> 384
          //   374: aload_0
          //   375: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   378: invokestatic 173	com/fab/sale/ShopDetailsActivity:access$10	(Lcom/fab/sale/ShopDetailsActivity;)Lcom/fab/sale/ShopsDetailAdapter;
          //   381: invokevirtual 178	com/fab/sale/ShopsDetailAdapter:notifyDataSetChanged	()V
          //   384: aload_0
          //   385: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   388: invokestatic 47	com/fab/sale/ShopDetailsActivity:access$7	(Lcom/fab/sale/ShopDetailsActivity;)I
          //   391: iconst_1
          //   392: if_icmpne +70 -> 462
          //   395: aconst_null
          //   396: astore 15
          //   398: aconst_null
          //   399: astore 16
          //   401: aconst_null
          //   402: astore 17
          //   404: aconst_null
          //   405: astore 18
          //   407: iconst_0
          //   408: istore 19
          //   410: aload_0
          //   411: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   414: new 40	java/util/ArrayList
          //   417: dup
          //   418: invokespecial 51	java/util/ArrayList:<init>	()V
          //   421: putfield 181	com/fab/sale/ShopDetailsActivity:theSortOptions	Ljava/util/ArrayList;
          //   424: aload_2
          //   425: ldc 183
          //   427: invokevirtual 156	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   430: astore 41
          //   432: aload 41
          //   434: astore 15
          //   436: aload 15
          //   438: ifnull +24 -> 462
          //   441: ldc 28
          //   443: astore 21
          //   445: iconst_0
          //   446: istore 22
          //   448: aload 15
          //   450: invokevirtual 159	org/json/JSONArray:length	()I
          //   453: istore 23
          //   455: iload 22
          //   457: iload 23
          //   459: if_icmplt +479 -> 938
          //   462: aload_2
          //   463: ldc 185
          //   465: invokevirtual 189	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   468: ifeq +718 -> 1186
          //   471: aload_0
          //   472: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   475: iconst_1
          //   476: putfield 193	com/fab/sale/ShopDetailsActivity:nextPageExists	Z
          //   479: aload_0
          //   480: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   483: astore 14
          //   485: aload 14
          //   487: iconst_1
          //   488: aload 14
          //   490: invokestatic 47	com/fab/sale/ShopDetailsActivity:access$7	(Lcom/fab/sale/ShopDetailsActivity;)I
          //   493: iadd
          //   494: invokestatic 197	com/fab/sale/ShopDetailsActivity:access$12	(Lcom/fab/sale/ShopDetailsActivity;I)V
          //   497: aload_0
          //   498: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   501: invokestatic 201	com/fab/sale/ShopDetailsActivity:access$15	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/widget/RelativeLayout;
          //   504: bipush 8
          //   506: invokevirtual 206	android/widget/RelativeLayout:setVisibility	(I)V
          //   509: aload_0
          //   510: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   513: invokestatic 210	com/fab/sale/ShopDetailsActivity:access$16	(Lcom/fab/sale/ShopDetailsActivity;)Lcom/fab/views/ErrorMessage;
          //   516: bipush 8
          //   518: invokevirtual 213	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   521: aload_0
          //   522: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   525: invokestatic 216	com/fab/sale/ShopDetailsActivity:access$17	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/widget/RelativeLayout;
          //   528: iconst_0
          //   529: invokevirtual 206	android/widget/RelativeLayout:setVisibility	(I)V
          //   532: aload_0
          //   533: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   536: invokestatic 219	com/fab/sale/ShopDetailsActivity:access$18	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/widget/RelativeLayout;
          //   539: iconst_0
          //   540: invokevirtual 206	android/widget/RelativeLayout:setVisibility	(I)V
          //   543: aload_0
          //   544: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   547: invokestatic 223	com/fab/sale/ShopDetailsActivity:access$20	(Lcom/fab/sale/ShopDetailsActivity;)Ljava/util/HashMap;
          //   550: ifnull +13 -> 563
          //   553: aload_0
          //   554: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   557: invokestatic 223	com/fab/sale/ShopDetailsActivity:access$20	(Lcom/fab/sale/ShopDetailsActivity;)Ljava/util/HashMap;
          //   560: invokevirtual 226	java/util/HashMap:clear	()V
          //   563: aload_0
          //   564: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   567: invokestatic 230	com/fab/sale/ShopDetailsActivity:access$0	(Lcom/fab/sale/ShopDetailsActivity;)Lcom/fab/views/FabButton;
          //   570: ifnull +14 -> 584
          //   573: aload_0
          //   574: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   577: invokestatic 230	com/fab/sale/ShopDetailsActivity:access$0	(Lcom/fab/sale/ShopDetailsActivity;)Lcom/fab/views/FabButton;
          //   580: iconst_1
          //   581: invokevirtual 236	com/fab/views/FabButton:setEnabled	(Z)V
          //   584: return
          //   585: aload_0
          //   586: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   589: new 40	java/util/ArrayList
          //   592: dup
          //   593: invokespecial 51	java/util/ArrayList:<init>	()V
          //   596: putfield 38	com/fab/sale/ShopDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   599: goto -549 -> 50
          //   602: aload_0
          //   603: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   606: new 40	java/util/ArrayList
          //   609: dup
          //   610: invokespecial 51	java/util/ArrayList:<init>	()V
          //   613: putfield 50	com/fab/sale/ShopDetailsActivity:thePairedShopProducts	Ljava/util/ArrayList;
          //   616: goto -535 -> 81
          //   619: new 164	com/fab/sale/bo/ProductDetailsBO
          //   622: dup
          //   623: invokespecial 165	com/fab/sale/bo/ProductDetailsBO:<init>	()V
          //   626: astore 44
          //   628: aload 9
          //   630: iload 42
          //   632: invokevirtual 239	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   635: astore 58
          //   637: aload 58
          //   639: astore 10
          //   641: aload 44
          //   643: aload 10
          //   645: ldc 241
          //   647: invokevirtual 73	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   650: invokevirtual 244	com/fab/sale/bo/ProductDetailsBO:setSaleId	(I)V
          //   653: aload 44
          //   655: aload 10
          //   657: ldc 246
          //   659: invokevirtual 73	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   662: invokevirtual 249	com/fab/sale/bo/ProductDetailsBO:setId	(I)V
          //   665: aload_0
          //   666: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   669: getfield 54	com/fab/sale/ShopDetailsActivity:theProdIDs	Ljava/util/ArrayList;
          //   672: new 116	java/lang/StringBuilder
          //   675: dup
          //   676: invokespecial 250	java/lang/StringBuilder:<init>	()V
          //   679: aload 10
          //   681: ldc 246
          //   683: invokevirtual 73	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   686: invokevirtual 253	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
          //   689: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   692: invokevirtual 256	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   695: pop
          //   696: aload 44
          //   698: aload 10
          //   700: ldc_w 258
          //   703: invokevirtual 34	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   706: invokevirtual 261	com/fab/sale/bo/ProductDetailsBO:setName	(Ljava/lang/String;)V
          //   709: aload 44
          //   711: new 116	java/lang/StringBuilder
          //   714: dup
          //   715: ldc_w 263
          //   718: invokespecial 133	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   721: aload 10
          //   723: ldc_w 265
          //   726: invokevirtual 34	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   729: ldc_w 267
          //   732: ldc_w 269
          //   735: invokevirtual 273	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   738: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   741: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   744: invokevirtual 276	com/fab/sale/bo/ProductDetailsBO:setImageUrl	(Ljava/lang/String;)V
          //   747: aload 44
          //   749: aload 10
          //   751: ldc_w 278
          //   754: invokevirtual 34	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   757: invokevirtual 281	com/fab/sale/bo/ProductDetailsBO:setFabPrice	(Ljava/lang/String;)V
          //   760: aload 44
          //   762: aload 10
          //   764: ldc_w 283
          //   767: invokevirtual 34	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   770: invokevirtual 286	com/fab/sale/bo/ProductDetailsBO:setMsrp	(Ljava/lang/String;)V
          //   773: aload 44
          //   775: aload 10
          //   777: ldc_w 288
          //   780: invokevirtual 189	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   783: invokevirtual 291	com/fab/sale/bo/ProductDetailsBO:setShowMsrp	(Z)V
          //   786: aload 44
          //   788: aload 10
          //   790: ldc_w 293
          //   793: invokevirtual 34	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   796: invokevirtual 296	com/fab/sale/bo/ProductDetailsBO:setDiscountPct	(Ljava/lang/String;)V
          //   799: aload 44
          //   801: aload 10
          //   803: ldc_w 298
          //   806: invokevirtual 34	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   809: invokevirtual 301	com/fab/sale/bo/ProductDetailsBO:setProductBy	(Ljava/lang/String;)V
          //   812: aload 44
          //   814: aload 10
          //   816: ldc_w 303
          //   819: invokevirtual 34	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   822: invokevirtual 306	com/fab/sale/bo/ProductDetailsBO:setStatus	(Ljava/lang/String;)V
          //   825: aload_0
          //   826: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   829: getfield 38	com/fab/sale/ShopDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   832: aload 44
          //   834: invokevirtual 256	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   837: pop
          //   838: iinc 42 1
          //   841: goto -549 -> 292
          //   844: astore 46
          //   846: aload 44
          //   848: aload_0
          //   849: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   852: invokestatic 309	com/fab/sale/ShopDetailsActivity:access$1	(Lcom/fab/sale/ShopDetailsActivity;)I
          //   855: invokevirtual 244	com/fab/sale/bo/ProductDetailsBO:setSaleId	(I)V
          //   858: goto -205 -> 653
          //   861: new 311	com/fab/sale/bo/PairedProducts
          //   864: dup
          //   865: invokespecial 312	com/fab/sale/bo/PairedProducts:<init>	()V
          //   868: astore 61
          //   870: aload 61
          //   872: aload_0
          //   873: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   876: getfield 38	com/fab/sale/ShopDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   879: iload 59
          //   881: invokevirtual 316	java/util/ArrayList:get	(I)Ljava/lang/Object;
          //   884: checkcast 164	com/fab/sale/bo/ProductDetailsBO
          //   887: invokevirtual 320	com/fab/sale/bo/PairedProducts:setFirstProduct	(Lcom/fab/sale/bo/ProductDetailsBO;)V
          //   890: iload 59
          //   892: iconst_1
          //   893: iadd
          //   894: istore 62
          //   896: aload 61
          //   898: aload_0
          //   899: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   902: getfield 38	com/fab/sale/ShopDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   905: iload 62
          //   907: invokevirtual 316	java/util/ArrayList:get	(I)Ljava/lang/Object;
          //   910: checkcast 164	com/fab/sale/bo/ProductDetailsBO
          //   913: invokevirtual 323	com/fab/sale/bo/PairedProducts:setSecondProduct	(Lcom/fab/sale/bo/ProductDetailsBO;)V
          //   916: aload_0
          //   917: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   920: getfield 50	com/fab/sale/ShopDetailsActivity:thePairedShopProducts	Ljava/util/ArrayList;
          //   923: aload 61
          //   925: invokevirtual 256	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   928: pop
          //   929: iload 62
          //   931: iconst_1
          //   932: iadd
          //   933: istore 59
          //   935: goto -578 -> 357
          //   938: aload 15
          //   940: iload 22
          //   942: invokevirtual 239	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   945: astore 40
          //   947: aload 40
          //   949: astore 17
          //   951: aload 17
          //   953: ifnull +48 -> 1001
          //   956: aload 17
          //   958: ldc_w 325
          //   961: invokevirtual 34	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   964: astore 39
          //   966: aload 39
          //   968: astore 21
          //   970: aload 17
          //   972: ldc_w 327
          //   975: invokevirtual 156	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   978: astore 38
          //   980: aload 38
          //   982: astore 16
          //   984: iconst_0
          //   985: istore 27
          //   987: aload 16
          //   989: invokevirtual 159	org/json/JSONArray:length	()I
          //   992: istore 28
          //   994: iload 27
          //   996: iload 28
          //   998: if_icmplt +19 -> 1017
          //   1001: iinc 22 1
          //   1004: goto -549 -> 455
          //   1007: astore 25
          //   1009: aload 25
          //   1011: invokevirtual 330	org/json/JSONException:printStackTrace	()V
          //   1014: goto -44 -> 970
          //   1017: aload 16
          //   1019: iload 27
          //   1021: invokevirtual 239	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   1024: astore 37
          //   1026: aload 37
          //   1028: astore 18
          //   1030: aload 18
          //   1032: ifnull +76 -> 1108
          //   1035: new 332	com/fab/sale/bo/ShopSortBo
          //   1038: dup
          //   1039: invokespecial 333	com/fab/sale/bo/ShopSortBo:<init>	()V
          //   1042: astore 30
          //   1044: aload 30
          //   1046: aload 18
          //   1048: ldc_w 325
          //   1051: invokevirtual 34	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1054: invokevirtual 336	com/fab/sale/bo/ShopSortBo:setSortValue	(Ljava/lang/String;)V
          //   1057: aload 30
          //   1059: aload 18
          //   1061: ldc_w 338
          //   1064: invokevirtual 34	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1067: invokevirtual 341	com/fab/sale/bo/ShopSortBo:setValueUrl	(Ljava/lang/String;)V
          //   1070: aload 18
          //   1072: ldc_w 343
          //   1075: invokevirtual 73	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   1078: istore 36
          //   1080: iload 36
          //   1082: istore 19
          //   1084: iload 19
          //   1086: ifne +38 -> 1124
          //   1089: aload 30
          //   1091: iconst_0
          //   1092: invokevirtual 346	com/fab/sale/bo/ShopSortBo:setChecked	(Z)V
          //   1095: aload_0
          //   1096: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1099: getfield 181	com/fab/sale/ShopDetailsActivity:theSortOptions	Ljava/util/ArrayList;
          //   1102: aload 30
          //   1104: invokevirtual 256	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   1107: pop
          //   1108: iinc 27 1
          //   1111: goto -117 -> 994
          //   1114: astore 32
          //   1116: aload 32
          //   1118: invokevirtual 330	org/json/JSONException:printStackTrace	()V
          //   1121: goto -51 -> 1070
          //   1124: aload 30
          //   1126: iconst_1
          //   1127: invokevirtual 346	com/fab/sale/bo/ShopSortBo:setChecked	(Z)V
          //   1130: aload_0
          //   1131: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1134: invokestatic 349	com/fab/sale/ShopDetailsActivity:access$11	(Lcom/fab/sale/ShopDetailsActivity;)Lcom/fab/views/FabTextView;
          //   1137: new 116	java/lang/StringBuilder
          //   1140: dup
          //   1141: aload 21
          //   1143: invokestatic 130	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
          //   1146: invokespecial 133	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   1149: ldc_w 351
          //   1152: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1155: aload 18
          //   1157: ldc_w 325
          //   1160: invokevirtual 34	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1163: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1166: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   1169: invokevirtual 150	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
          //   1172: aload_0
          //   1173: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1176: invokestatic 349	com/fab/sale/ShopDetailsActivity:access$11	(Lcom/fab/sale/ShopDetailsActivity;)Lcom/fab/views/FabTextView;
          //   1179: iconst_0
          //   1180: invokevirtual 352	com/fab/views/FabTextView:setVisibility	(I)V
          //   1183: goto -88 -> 1095
          //   1186: aload_0
          //   1187: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1190: iconst_0
          //   1191: putfield 193	com/fab/sale/ShopDetailsActivity:nextPageExists	Z
          //   1194: aload_0
          //   1195: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1198: invokestatic 356	com/fab/sale/ShopDetailsActivity:access$13	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/widget/ListView;
          //   1201: aload_0
          //   1202: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1205: invokestatic 360	com/fab/sale/ShopDetailsActivity:access$14	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/view/View;
          //   1208: invokevirtual 366	android/widget/ListView:removeFooterView	(Landroid/view/View;)Z
          //   1211: pop
          //   1212: goto -715 -> 497
          //   1215: astore 12
          //   1217: aload_0
          //   1218: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1221: iconst_0
          //   1222: putfield 193	com/fab/sale/ShopDetailsActivity:nextPageExists	Z
          //   1225: aload_0
          //   1226: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1229: invokestatic 360	com/fab/sale/ShopDetailsActivity:access$14	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/view/View;
          //   1232: bipush 8
          //   1234: invokevirtual 369	android/view/View:setVisibility	(I)V
          //   1237: goto -740 -> 497
          //   1240: aload_3
          //   1241: invokestatic 60	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   1244: ifne -701 -> 543
          //   1247: aload_0
          //   1248: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1251: invokestatic 201	com/fab/sale/ShopDetailsActivity:access$15	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/widget/RelativeLayout;
          //   1254: bipush 8
          //   1256: invokevirtual 206	android/widget/RelativeLayout:setVisibility	(I)V
          //   1259: aload_0
          //   1260: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1263: invokestatic 210	com/fab/sale/ShopDetailsActivity:access$16	(Lcom/fab/sale/ShopDetailsActivity;)Lcom/fab/views/ErrorMessage;
          //   1266: bipush 8
          //   1268: invokevirtual 213	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   1271: aload_0
          //   1272: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1275: invokestatic 216	com/fab/sale/ShopDetailsActivity:access$17	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/widget/RelativeLayout;
          //   1278: bipush 8
          //   1280: invokevirtual 206	android/widget/RelativeLayout:setVisibility	(I)V
          //   1283: aload_0
          //   1284: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1287: invokestatic 219	com/fab/sale/ShopDetailsActivity:access$18	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/widget/RelativeLayout;
          //   1290: bipush 8
          //   1292: invokevirtual 206	android/widget/RelativeLayout:setVisibility	(I)V
          //   1295: new 371	android/os/Message
          //   1298: dup
          //   1299: invokespecial 372	android/os/Message:<init>	()V
          //   1302: astore 67
          //   1304: new 374	android/os/Bundle
          //   1307: dup
          //   1308: invokespecial 375	android/os/Bundle:<init>	()V
          //   1311: astore 68
          //   1313: aload 68
          //   1315: ldc_w 377
          //   1318: iconst_2
          //   1319: invokevirtual 381	android/os/Bundle:putInt	(Ljava/lang/String;I)V
          //   1322: aload 68
          //   1324: ldc_w 383
          //   1327: ldc_w 385
          //   1330: invokevirtual 389	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
          //   1333: aload 68
          //   1335: ldc_w 391
          //   1338: aload_3
          //   1339: invokevirtual 389	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
          //   1342: aload 68
          //   1344: ldc_w 393
          //   1347: iconst_1
          //   1348: invokevirtual 381	android/os/Bundle:putInt	(Ljava/lang/String;I)V
          //   1351: aload 68
          //   1353: ldc_w 395
          //   1356: ldc_w 397
          //   1359: invokevirtual 389	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
          //   1362: aload 67
          //   1364: aload 68
          //   1366: invokevirtual 401	android/os/Message:setData	(Landroid/os/Bundle;)V
          //   1369: aload_0
          //   1370: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1373: invokestatic 104	com/fab/sale/ShopDetailsActivity:access$8	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/os/Handler;
          //   1376: aload 67
          //   1378: invokevirtual 407	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
          //   1381: pop
          //   1382: aload_0
          //   1383: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1386: invokevirtual 411	com/fab/sale/ShopDetailsActivity:getApplication	()Landroid/app/Application;
          //   1389: checkcast 413	com/fab/FabApplication
          //   1392: iconst_1
          //   1393: invokevirtual 416	com/fab/FabApplication:setRefreshActivityOnRestart	(Z)V
          //   1396: goto -853 -> 543
          //   1399: aload_0
          //   1400: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1403: invokestatic 201	com/fab/sale/ShopDetailsActivity:access$15	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/widget/RelativeLayout;
          //   1406: bipush 8
          //   1408: invokevirtual 206	android/widget/RelativeLayout:setVisibility	(I)V
          //   1411: aload_0
          //   1412: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1415: invokestatic 420	com/fab/sale/ShopDetailsActivity:access$19	(Lcom/fab/sale/ShopDetailsActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   1418: invokevirtual 425	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   1421: invokestatic 60	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   1424: ifne -881 -> 543
          //   1427: aload_0
          //   1428: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1431: invokestatic 210	com/fab/sale/ShopDetailsActivity:access$16	(Lcom/fab/sale/ShopDetailsActivity;)Lcom/fab/views/ErrorMessage;
          //   1434: iconst_0
          //   1435: invokevirtual 213	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   1438: aload_0
          //   1439: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1442: invokestatic 210	com/fab/sale/ShopDetailsActivity:access$16	(Lcom/fab/sale/ShopDetailsActivity;)Lcom/fab/views/ErrorMessage;
          //   1445: aload_0
          //   1446: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1449: invokestatic 420	com/fab/sale/ShopDetailsActivity:access$19	(Lcom/fab/sale/ShopDetailsActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   1452: invokevirtual 425	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   1455: invokevirtual 428	com/fab/views/ErrorMessage:setErrorMsg	(Ljava/lang/String;)V
          //   1458: aload_0
          //   1459: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1462: invokestatic 216	com/fab/sale/ShopDetailsActivity:access$17	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/widget/RelativeLayout;
          //   1465: bipush 8
          //   1467: invokevirtual 206	android/widget/RelativeLayout:setVisibility	(I)V
          //   1470: aload_0
          //   1471: getfield 18	com/fab/sale/ShopDetailsActivity$4:this$0	Lcom/fab/sale/ShopDetailsActivity;
          //   1474: invokestatic 219	com/fab/sale/ShopDetailsActivity:access$18	(Lcom/fab/sale/ShopDetailsActivity;)Landroid/widget/RelativeLayout;
          //   1477: bipush 8
          //   1479: invokevirtual 206	android/widget/RelativeLayout:setVisibility	(I)V
          //   1482: goto -939 -> 543
          //   1485: astore 45
          //   1487: goto -846 -> 641
          //   1490: astore 24
          //   1492: goto -541 -> 951
          //   1495: astore 29
          //   1497: goto -467 -> 1030
          //   1500: astore 34
          //   1502: goto -330 -> 1172
          //   1505: astore 33
          //   1507: goto -423 -> 1084
          //   1510: astore 31
          //   1512: goto -455 -> 1057
          //   1515: astore 26
          //   1517: goto -533 -> 984
          //   1520: astore 20
          //   1522: goto -1086 -> 436
          //   1525: astore 55
          //   1527: goto -702 -> 825
          //   1530: astore 54
          //   1532: goto -720 -> 812
          //   1535: astore 53
          //   1537: goto -738 -> 799
          //   1540: astore 52
          //   1542: goto -756 -> 786
          //   1545: astore 51
          //   1547: goto -774 -> 773
          //   1550: astore 50
          //   1552: goto -792 -> 760
          //   1555: astore 49
          //   1557: goto -810 -> 747
          //   1560: astore 48
          //   1562: goto -853 -> 709
          //   1565: astore 47
          //   1567: goto -871 -> 696
          //   1570: astore 11
          //   1572: goto -1295 -> 277
          //   1575: astore 8
          //   1577: goto -1324 -> 253
          //   1580: astore 7
          //   1582: goto -1384 -> 198
          //   1585: astore 6
          //   1587: goto -1464 -> 123
          //   1590: astore 4
          //   1592: goto -1562 -> 30
          //
          // Exception table:
          //   from	to	target	type
          //   641	653	844	org/json/JSONException
          //   956	966	1007	org/json/JSONException
          //   1057	1070	1114	org/json/JSONException
          //   462	497	1215	org/json/JSONException
          //   1186	1212	1215	org/json/JSONException
          //   628	637	1485	org/json/JSONException
          //   938	947	1490	org/json/JSONException
          //   1017	1026	1495	org/json/JSONException
          //   1130	1172	1500	org/json/JSONException
          //   1070	1080	1505	org/json/JSONException
          //   1044	1057	1510	org/json/JSONException
          //   970	980	1515	org/json/JSONException
          //   424	432	1520	org/json/JSONException
          //   812	825	1525	org/json/JSONException
          //   799	812	1530	org/json/JSONException
          //   786	799	1535	org/json/JSONException
          //   773	786	1540	org/json/JSONException
          //   760	773	1545	org/json/JSONException
          //   747	760	1550	org/json/JSONException
          //   709	747	1555	org/json/JSONException
          //   696	709	1560	org/json/JSONException
          //   653	696	1565	org/json/JSONException
          //   265	273	1570	org/json/JSONException
          //   198	253	1575	org/json/JSONException
          //   123	198	1580	org/json/JSONException
          //   111	119	1585	org/json/JSONException
          //   19	27	1590	org/json/JSONException
        }
      });
      ExecuteRequestAsyncTask localExecuteRequestAsyncTask = this.task;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = FabUtils.requestPostUrl(false, "/mobile/shop/" + this.shopId);
      localExecuteRequestAsyncTask.execute(arrayOfString);
    }
  }

  private void refreshActivity()
  {
    FabHelper.smoothScroll(this.listView);
    this.page = 1;
    this.prodIdsStringBulder = new StringBuilder();
    this.theProdIDs = new ArrayList();
    if (this.listView.getFooterViewsCount() == 0)
      this.listView.addFooterView(this.footerView);
    this.timer.setVisibility(4);
    FabHelper.stopAndRestartCartTimer(getApplicationContext(), 0L, this.mHandler);
    this.titleWidget.clearCartButton();
    this.shopDetailsRel.setVisibility(8);
    this.sortRel.setVisibility(8);
    this.error.setVisibility(8);
    this.progressBar.setVisibility(0);
    loadShopDetails();
  }

  public ArrayList<ShopSortBo> getTheSortOptions()
  {
    return this.theSortOptions;
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903094);
    isActivityAlive = true;
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
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("shopDetails"));
    this.extras = getIntent().getExtras();
    if (this.extras != null)
      this.shopId = this.extras.getInt("SHOP_ID");
    this.filter = "";
    this.sortRel = ((RelativeLayout)findViewById(2131362057));
    this.tvShopName = ((FabTextView)findViewById(2131362059));
    this.tvSortedBY = ((FabTextView)findViewById(2131362060));
    this.btnSort = ((FabButton)findViewById(2131362058));
    this.tvShopName.setFont("HelveticaNeu_bold.ttf");
    this.tvSortedBY.setFont("HelveticaNeu_normal.ttf");
    this.btnSort.setFont("HelveticaNeu_normal.ttf");
    this.btnSort.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (ShopDetailsActivity.this.theSortOptions.size() > 0)
        {
          FabShopsDialog localFabShopsDialog = new FabShopsDialog(ShopDetailsActivity.this.context, ShopDetailsActivity.this.btnSort);
          localFabShopsDialog.show();
          ShopDetailsActivity.this.btnSort.setEnabled(false);
          if (localFabShopsDialog.isShowing())
          {
            localFabShopsDialog.setSortOPtions(ShopDetailsActivity.this.theSortOptions, ShopDetailsActivity.this.shopId);
            localFabShopsDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
            {
              public void onDismiss(DialogInterface paramAnonymous2DialogInterface)
              {
                FabShopsDialog localFabShopsDialog = (FabShopsDialog)paramAnonymous2DialogInterface;
                ShopDetailsActivity.this.btnSort.setEnabled(true);
                if ((!localFabShopsDialog.isSilentDismiss()) && (localFabShopsDialog.getShopSortUrl() != null) && (!TextUtils.isEmpty(localFabShopsDialog.getShopSortUrl())));
                try
                {
                  ShopDetailsActivity.this.filter = localFabShopsDialog.getShopSortUrl();
                  ShopDetailsActivity.this.refreshActivity();
                  label67: return;
                }
                catch (Exception localException)
                {
                  break label67;
                }
              }
            });
          }
        }
      }
    });
    this.timer = ((FabTextView)findViewById(2131361795));
    this.timer.setFont("georgiai.ttf");
    this.titleWidget = ((TitleWidget)findViewById(2131361810));
    this.shopDetailsRel = ((RelativeLayout)findViewById(2131362061));
    this.listView = ((ListView)findViewById(2131362062));
    this.listView.setDividerHeight(0);
    this.error = ((ErrorMessage)findViewById(2131361811));
    FabSharedPrefs.initializePreferences(this.context);
    this.alertbox = new AlertDialog.Builder(this);
    this.listView.setSelector(17170445);
    this.progressBar = ((RelativeLayout)findViewById(2131361874));
    this.footerView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903067, null);
    this.listView.addFooterView(this.footerView);
    this.adapter = new ShopsDetailAdapter(this.context, this.thePairedShopProducts);
    this.listView.setAdapter(this.adapter);
    this.listView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        FabUtils.log(ShopDetailsActivity.this.TAG, "firstVisibleItem=" + paramAnonymousInt1 + "::visibleItemCount=" + paramAnonymousInt2 + "::totalItemCount=" + paramAnonymousInt3);
        int i = paramAnonymousInt1 + paramAnonymousInt2;
        if ((i != paramAnonymousInt3) || ((i == paramAnonymousInt3) && (!ShopDetailsActivity.this.loadingMore) && (ShopDetailsActivity.this.nextPageExists)))
          ShopDetailsActivity.this.loadShopDetails();
      }

      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
      }
    });
    this.loadingMore = false;
    this.nextPageExists = false;
    this.page = 1;
    loadShopDetails();
    this.headerCartButton = this.titleWidget.getCartButton();
    this.mHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Bundle localBundle = paramAnonymousMessage.getData();
        if (FabUtils.isLogEnabled())
          FabUtils.log(ShopDetailsActivity.this.TAG, localBundle.toString());
        int i = localBundle.getInt("Type");
        String str1 = localBundle.getString("Value");
        String str2 = localBundle.getString("OnClick");
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
            ShopDetailsActivity.this.timer.setVisibility(4);
          if (ShopDetailsActivity.isActivityAlive)
          {
            AlertDialog.Builder localBuilder = ShopDetailsActivity.this.alertbox;
            int j;
            if (localBundle.getInt("Error") == 1)
            {
              j = 2130837504;
              label122: localBuilder.setIcon(j);
              ShopDetailsActivity.this.alertbox.setTitle(localBundle.getString("Title"));
              ShopDetailsActivity.this.alertbox.setMessage(localBundle.getString("Message"));
              if (!"finish".equalsIgnoreCase(str2))
                break label241;
              ShopDetailsActivity.this.alertbox.setOnCancelListener(new DialogInterface.OnCancelListener()
              {
                public void onCancel(DialogInterface paramAnonymous2DialogInterface)
                {
                  ShopDetailsActivity.this.finish();
                }
              });
              ShopDetailsActivity.this.alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                {
                  ShopDetailsActivity.this.finish();
                }
              });
            }
            while (true)
            {
              if (!ShopDetailsActivity.isActivityAlive)
                break label256;
              ShopDetailsActivity.this.alertbox.show();
              break;
              j = 2130837507;
              break label122;
              label241: ShopDetailsActivity.this.alertbox.setNeutralButton("OK", null);
            }
            label256: continue;
            ShopDetailsActivity.this.timer.setVisibility(0);
            ShopDetailsActivity.this.timer.setText(str1);
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
        Intent localIntent = new Intent(ShopDetailsActivity.this.context, MoreActivity.class);
        ShopDetailsActivity.this.startActivity(localIntent);
        return false;
      }
    });
    MenuItem localMenuItem2 = paramMenu.findItem(2131362109);
    localMenuItem2.setVisible(true);
    localMenuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(ShopDetailsActivity.this.context, FabWebViewActivity.class);
        ShopDetailsActivity.this.startActivity(localIntent);
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
          FabUtils.showGuestAlert(ShopDetailsActivity.this.context, ShopDetailsActivity.this.context.getResources().getString(2131165208), ShopDetailsActivity.this.context.getResources().getString(2131165209), false);
        while (true)
        {
          return false;
          Intent localIntent = new Intent(ShopDetailsActivity.this.context, InviteActivity.class);
          ShopDetailsActivity.this.startActivity(localIntent);
        }
      }
    });
    MenuItem localMenuItem4 = paramMenu.findItem(2131362103);
    localMenuItem4.setVisible(true);
    localMenuItem4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        ShopDetailsActivity.this.refreshActivity();
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
    try
    {
      if (Calendar.getInstance().after(Long.valueOf(FabSharedPrefs.getNextSaleTs())))
        refreshActivity();
      label65: this.error.setVisibility(8);
      if (this.thePairedShopProducts.isEmpty())
      {
        this.page = 1;
        loadShopDetails();
      }
      FabHelper.stopAndRestartCartTimer(getApplicationContext(), FabHelper.getCartTime(), this.mHandler);
      updateCartCounter(FabHelper.getCartCount());
      super.onResume();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      break label65;
    }
  }

  public void setTheSortOptions(ArrayList<ShopSortBo> paramArrayList)
  {
    this.theSortOptions = paramArrayList;
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
 * Qualified Name:     com.fab.sale.ShopDetailsActivity
 * JD-Core Version:    0.6.2
 */