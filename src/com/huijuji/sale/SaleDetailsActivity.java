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
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.fab.FabApplication;
import com.fab.FabIndex;
import com.fab.InviteActivity;
import com.fab.MoreActivity;
import com.fab.facebook.FabFBClient;
import com.fab.facebook.FabFBShareBO;
import com.fab.sale.bo.PairedProducts;
import com.fab.sale.bo.ProductDetailsBO;
import com.fab.sale.bo.SaleDetailsBO;
import com.fab.twitter.FabTwitterClient;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabHelper;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;
import com.fab.utils.ImageDownloader;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.ErrorMessage;
import com.fab.views.FabButton;
import com.fab.views.FabTextView;
import com.fab.views.TitleWidget;
import com.fab.webviews.FabWebViewActivity;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class SaleDetailsActivity extends Activity
{
  protected static boolean isActivityAlive;
  private String TAG = "SALE_DETAILS";
  private SalesDetailsAdapter adapter;
  private AlertDialog.Builder alertbox;
  Context context;
  private FabTextView curatorCompanyAndDesignation;
  private RelativeLayout curatorContentHolder;
  private ImageView curatorImage;
  private JSONObject curatorInfo;
  private RelativeLayout curatorInfoHolder;
  private FabTextView curatorMessage;
  private FabTextView curatorMessage2;
  private FabTextView curatorName;
  private FabTextView curatorNameBottom;
  private ProgressBar curatorProgressBar;
  private ImageView downArrow;
  private String emailBody;
  private String emailSubject;
  private ErrorMessage error;
  private Bundle extras;
  protected FabFBClient fbClient;
  protected FabFBShareBO fbShareData;
  private MenuItem fbshareItem;
  private View footerView;
  protected boolean hasFBShareToken;
  private FabButton headerCartButton;
  private LinearLayout headerMesh;
  private View headerView;
  private FabHelper helper = FabHelper.getFabHelper();
  private FabTextView hideText;
  private LinearLayout listViewHeader;
  protected boolean loadingMore;
  private Handler mHandler;
  protected boolean nextPageExists;
  protected int page;
  private FabTextView primaryTitle;
  private RelativeLayout progressBar;
  private MenuItem refreshItem;
  private String saleCategory;
  private WebView saleDescription;
  public View saleDetailsRel;
  private FabTextView saleDuration;
  private int saleId;
  private ListView salesListView;
  private FabTextView secondaryTitle;
  public String smsShare;
  private ExecuteRequestAsyncTask task;
  ArrayList<PairedProducts> thePairedProducts = new ArrayList();
  ArrayList<ProductDetailsBO> theProducts;
  private FabTextView timer;
  public TitleWidget titleWidget;
  private GoogleAnalyticsTracker tracker;
  private String twShareText;
  private ImageView upArraow;
  private UpcomingSalesDetailsAdapter upcomingSalesAdapter;

  private void initilizeUI()
  {
    this.progressBar = ((RelativeLayout)findViewById(2131361874));
    this.saleDuration = ((FabTextView)findViewById(2131361848));
    this.saleDuration.setFont("georgiai.ttf");
    LayoutInflater localLayoutInflater = (LayoutInflater)this.context.getSystemService("layout_inflater");
    this.headerView = localLayoutInflater.inflate(2130903085, null);
    this.footerView = localLayoutInflater.inflate(2130903067, null);
    this.headerMesh = ((LinearLayout)this.headerView.findViewById(2131361855));
    this.primaryTitle = ((FabTextView)this.headerView.findViewById(2131362011));
    this.primaryTitle.setFont("HelveticaNeu_bold.ttf");
    this.secondaryTitle = ((FabTextView)this.headerView.findViewById(2131362012));
    this.secondaryTitle.setFont("HelveticaNeu_normal.ttf");
    this.curatorContentHolder = ((RelativeLayout)this.headerView.findViewById(2131362014));
    this.curatorProgressBar = ((ProgressBar)this.headerView.findViewById(2131362013));
    this.hideText = ((FabTextView)this.headerView.findViewById(2131362026));
    this.hideText.setFont("georgiai.ttf");
    this.downArrow = ((ImageView)this.headerView.findViewById(2131362010));
    this.upArraow = ((ImageView)this.headerView.findViewById(2131362022));
    this.downArrow.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SaleDetailsActivity.this.loadCuratorInfo();
      }
    });
    this.listViewHeader = ((LinearLayout)this.headerView.findViewById(2131362009));
    this.saleDescription = ((WebView)this.headerView.findViewById(2131362015));
    this.curatorInfoHolder = ((RelativeLayout)this.headerView.findViewById(2131362017));
    this.curatorName = ((FabTextView)this.headerView.findViewById(2131362020));
    this.curatorName.setFont("georgiai.ttf");
    this.curatorImage = ((ImageView)this.headerView.findViewById(2131362018));
    this.curatorMessage = ((FabTextView)this.headerView.findViewById(2131362019));
    this.curatorMessage.setFont("georgiai.ttf");
    this.curatorMessage2 = ((FabTextView)this.headerView.findViewById(2131362021));
    this.curatorMessage2.setFont("georgiai.ttf");
    this.curatorNameBottom = ((FabTextView)this.headerView.findViewById(2131362024));
    this.curatorNameBottom.setFont("HelveticaNeu_normal.ttf");
    this.curatorCompanyAndDesignation = ((FabTextView)this.headerView.findViewById(2131362025));
    this.curatorCompanyAndDesignation.setFont("HelveticaNeu_normal.ttf");
    this.salesListView = ((ListView)findViewById(2131362006));
    this.salesListView.setDividerHeight(0);
    this.salesListView.setSelector(17170445);
    this.salesListView.setSmoothScrollbarEnabled(true);
    this.salesListView.addHeaderView(this.headerView);
    this.theProducts = new ArrayList();
    this.thePairedProducts = new ArrayList();
    if (this.saleCategory.equalsIgnoreCase("upcoming"))
    {
      this.upcomingSalesAdapter = new UpcomingSalesDetailsAdapter(this.context, this.thePairedProducts);
      this.salesListView.setAdapter(this.upcomingSalesAdapter);
    }
    while (true)
    {
      return;
      this.salesListView.addFooterView(this.footerView);
      this.adapter = new SalesDetailsAdapter(this.context, this.thePairedProducts);
      this.salesListView.setAdapter(this.adapter);
      this.salesListView.setOnScrollListener(new AbsListView.OnScrollListener()
      {
        public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
          FabUtils.log(SaleDetailsActivity.this.TAG, "firstVisibleItem=" + paramAnonymousInt1 + "::visibleItemCount=" + paramAnonymousInt2 + "::totalItemCount=" + paramAnonymousInt3);
          int i = paramAnonymousInt1 + paramAnonymousInt2;
          if ((i != paramAnonymousInt3) || ((i == paramAnonymousInt3) && (!SaleDetailsActivity.this.loadingMore) && (SaleDetailsActivity.this.nextPageExists)))
            SaleDetailsActivity.this.loadSaleDetails();
        }

        public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
        {
        }
      });
    }
  }

  private void loadCuratorInfo()
  {
    final ExecuteRequestAsyncTask localExecuteRequestAsyncTask = new ExecuteRequestAsyncTask(this.context, "GET");
    this.curatorProgressBar.setVisibility(0);
    this.downArrow.setOnClickListener(null);
    localExecuteRequestAsyncTask.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      public void onTaskComplete(Object paramAnonymousObject)
      {
        JSONObject localJSONObject;
        if ((paramAnonymousObject != null) && ((paramAnonymousObject instanceof JSONObject)))
          localJSONObject = (JSONObject)paramAnonymousObject;
        try
        {
          localJSONObject.getBoolean("login_err");
          try
          {
            label23: SaleDetailsActivity.this.curatorInfo = localJSONObject.getJSONObject("curator_info");
            try
            {
              label36: SaleDetailsActivity.this.curatorName.setText(Html.fromHtml(SaleDetailsActivity.this.curatorInfo.getString("full_name")));
              try
              {
                label61: SaleDetailsActivity.this.curatorMessage.setText("     " + SaleDetailsActivity.this.curatorInfo.getString("message"));
                try
                {
                  label98: SaleDetailsActivity.this.saleDescription.loadData(FabUtils.setWebViewStyle(SaleDetailsActivity.this.curatorInfo.getString("sale_description"), "Helvetica Neue", "12px"), "text/html", "utf-8");
                  while (true)
                  {
                    try
                    {
                      label131: arrayOfString2 = SaleDetailsActivity.this.curatorInfo.getString("byline").split(",");
                      if ((arrayOfString2 != null) && (arrayOfString2.length > 0))
                      {
                        str2 = "";
                        SaleDetailsActivity.this.curatorNameBottom.setText("- " + arrayOfString2[0]);
                        if (arrayOfString2.length <= 1)
                          continue;
                        i = 1;
                        if (i < arrayOfString2.length)
                          continue;
                        SaleDetailsActivity.this.curatorCompanyAndDesignation.setText(str2);
                      }
                    }
                    catch (JSONException localJSONException6)
                    {
                      try
                      {
                        String[] arrayOfString2;
                        String str2;
                        int i;
                        final String str1 = SaleDetailsActivity.this.curatorInfo.getString("img_url");
                        if (TextUtils.isEmpty(SaleDetailsActivity.this.helper.doesFileExists(SaleDetailsActivity.this.context, str1)))
                        {
                          ImageDownloader localImageDownloader = new ImageDownloader(SaleDetailsActivity.this.context, null, true, null);
                          localImageDownloader.setTaskCompleteListener(new TaskCompleteListener()
                          {
                            public void onTaskComplete()
                            {
                              SaleDetailsActivity.this.curatorImage.setImageBitmap(SaleDetailsActivity.this.helper.decodeFileForBitmap(SaleDetailsActivity.this.helper.doesFileExists(SaleDetailsActivity.this.context, str1)));
                              SaleDetailsActivity.this.curatorInfoHolder.setVisibility(0);
                            }

                            public void onTaskComplete(Object paramAnonymous2Object)
                            {
                            }
                          });
                          String[] arrayOfString1 = new String[1];
                          arrayOfString1[0] = str1;
                          localImageDownloader.execute(arrayOfString1);
                          SaleDetailsActivity.this.downArrow.setTag(Integer.valueOf(0));
                          SaleDetailsActivity.this.upArraow.setTag(Integer.valueOf(1));
                          SaleDetailsActivity.this.downArrow.setOnClickListener(null);
                          SaleDetailsActivity.this.downArrow.setOnClickListener(new View.OnClickListener()
                          {
                            public void onClick(View paramAnonymous2View)
                            {
                              int i = ((Integer)paramAnonymous2View.getTag()).intValue();
                              if (i == 0)
                              {
                                SaleDetailsActivity.this.curatorContentHolder.setVisibility(0);
                                SaleDetailsActivity.this.downArrow.setTag(Integer.valueOf(1));
                                SaleDetailsActivity.this.downArrow.setVisibility(8);
                              }
                              while (true)
                              {
                                return;
                                if (i == 1)
                                {
                                  SaleDetailsActivity.this.curatorContentHolder.setVisibility(8);
                                  SaleDetailsActivity.this.downArrow.setTag(Integer.valueOf(0));
                                  SaleDetailsActivity.this.downArrow.setVisibility(0);
                                }
                              }
                            }
                          });
                          SaleDetailsActivity.this.upArraow.setOnClickListener(new View.OnClickListener()
                          {
                            public void onClick(View paramAnonymous2View)
                            {
                              int i = ((Integer)paramAnonymous2View.getTag()).intValue();
                              if (i == 0)
                              {
                                SaleDetailsActivity.this.curatorContentHolder.setVisibility(0);
                                SaleDetailsActivity.this.downArrow.setTag(Integer.valueOf(1));
                                SaleDetailsActivity.this.downArrow.setVisibility(4);
                              }
                              while (true)
                              {
                                return;
                                if (i == 1)
                                {
                                  SaleDetailsActivity.this.curatorContentHolder.setVisibility(8);
                                  SaleDetailsActivity.this.downArrow.setTag(Integer.valueOf(0));
                                  SaleDetailsActivity.this.downArrow.setVisibility(0);
                                }
                              }
                            }
                          });
                          SaleDetailsActivity.this.downArrow.performClick();
                          SaleDetailsActivity.this.curatorProgressBar.setVisibility(8);
                          return;
                          str2 = str2 + arrayOfString2[i];
                          i++;
                          continue;
                          SaleDetailsActivity.this.curatorCompanyAndDesignation.setVisibility(8);
                          continue;
                          localJSONException6 = localJSONException6;
                          continue;
                        }
                        SaleDetailsActivity.this.curatorImage.setImageBitmap(SaleDetailsActivity.this.helper.decodeFileForBitmap(SaleDetailsActivity.this.helper.doesFileExists(SaleDetailsActivity.this.context, str1)));
                        SaleDetailsActivity.this.curatorInfoHolder.setVisibility(0);
                        continue;
                      }
                      catch (JSONException localJSONException7)
                      {
                        continue;
                      }
                    }
                    SaleDetailsActivity.this.curatorProgressBar.setVisibility(8);
                    FabUtils.showAlert(SaleDetailsActivity.this.context, localExecuteRequestAsyncTask.getErrMsg());
                  }
                }
                catch (JSONException localJSONException5)
                {
                  break label131;
                }
              }
              catch (JSONException localJSONException4)
              {
                break label98;
              }
            }
            catch (JSONException localJSONException3)
            {
              break label61;
            }
          }
          catch (JSONException localJSONException2)
          {
            break label36;
          }
        }
        catch (JSONException localJSONException1)
        {
          break label23;
        }
      }
    });
    String[] arrayOfString = new String[1];
    arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/curator-info/", "?sale_id=" + this.saleId + "&from=android");
    localExecuteRequestAsyncTask.execute(arrayOfString);
  }

  private void loadSaleDetails()
  {
    if ((this.task == null) || ((this.task != null) && (this.task.getStatus() != AsyncTask.Status.RUNNING)))
    {
      this.task = new ExecuteRequestAsyncTask(this.context, "GET");
      this.loadingMore = true;
      this.task.setTaskCompleteListener(new TaskCompleteListener()
      {
        public void onTaskComplete()
        {
        }

        // ERROR //
        public void onTaskComplete(Object paramAnonymousObject)
        {
          // Byte code:
          //   0: aload_0
          //   1: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   4: iconst_0
          //   5: putfield 28	com/fab/sale/SaleDetailsActivity:loadingMore	Z
          //   8: aload_1
          //   9: ifnull +1299 -> 1308
          //   12: aload_1
          //   13: instanceof 30
          //   16: ifeq +1292 -> 1308
          //   19: aload_1
          //   20: checkcast 30	org/json/JSONObject
          //   23: astore_2
          //   24: new 32	com/fab/sale/bo/SaleDetailsBO
          //   27: dup
          //   28: invokespecial 33	com/fab/sale/bo/SaleDetailsBO:<init>	()V
          //   31: pop
          //   32: aload_0
          //   33: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   36: invokestatic 37	com/fab/sale/SaleDetailsActivity:access$0	(Lcom/fab/sale/SaleDetailsActivity;)Ljava/lang/String;
          //   39: aload_2
          //   40: invokevirtual 41	org/json/JSONObject:toString	()Ljava/lang/String;
          //   43: invokestatic 47	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
          //   46: aload_2
          //   47: ldc 49
          //   49: invokevirtual 53	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   52: pop
          //   53: ldc 55
          //   55: astore 5
          //   57: aload_2
          //   58: ldc 57
          //   60: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   63: astore 68
          //   65: aload 68
          //   67: astore 5
          //   69: aload 5
          //   71: invokestatic 67	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   74: ifeq +1113 -> 1187
          //   77: new 30	org/json/JSONObject
          //   80: dup
          //   81: invokespecial 68	org/json/JSONObject:<init>	()V
          //   84: astore 7
          //   86: aload_2
          //   87: ldc 70
          //   89: invokevirtual 74	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   92: astore 67
          //   94: aload 67
          //   96: astore 7
          //   98: aload_0
          //   99: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   102: aload 7
          //   104: ldc 76
          //   106: invokevirtual 80	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   109: invokevirtual 84	com/fab/sale/SaleDetailsActivity:updateCartCounter	(I)V
          //   112: aload 7
          //   114: ldc 86
          //   116: invokevirtual 80	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   119: i2l
          //   120: new 88	java/util/GregorianCalendar
          //   123: dup
          //   124: invokespecial 89	java/util/GregorianCalendar:<init>	()V
          //   127: invokestatic 93	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
          //   130: ldc2_w 94
          //   133: lcmp
          //   134: iflt +39 -> 173
          //   137: aload 7
          //   139: ldc 86
          //   141: invokevirtual 80	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   144: i2l
          //   145: invokestatic 101	com/fab/utils/FabHelper:setCartTime	(J)V
          //   148: aload_0
          //   149: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   152: invokevirtual 105	com/fab/sale/SaleDetailsActivity:getApplicationContext	()Landroid/content/Context;
          //   155: aload 7
          //   157: ldc 86
          //   159: invokevirtual 80	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   162: i2l
          //   163: aload_0
          //   164: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   167: invokestatic 109	com/fab/sale/SaleDetailsActivity:access$9	(Lcom/fab/sale/SaleDetailsActivity;)Landroid/os/Handler;
          //   170: invokestatic 113	com/fab/utils/FabHelper:stopAndRestartCartTimer	(Landroid/content/Context;JLandroid/os/Handler;)V
          //   173: new 30	org/json/JSONObject
          //   176: dup
          //   177: invokespecial 68	org/json/JSONObject:<init>	()V
          //   180: astore 10
          //   182: aload_2
          //   183: ldc 115
          //   185: invokevirtual 74	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   188: astore 66
          //   190: aload 66
          //   192: astore 10
          //   194: new 30	org/json/JSONObject
          //   197: dup
          //   198: invokespecial 68	org/json/JSONObject:<init>	()V
          //   201: astore 12
          //   203: new 32	com/fab/sale/bo/SaleDetailsBO
          //   206: dup
          //   207: invokespecial 33	com/fab/sale/bo/SaleDetailsBO:<init>	()V
          //   210: astore 13
          //   212: aload_0
          //   213: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   216: getfield 119	com/fab/sale/SaleDetailsActivity:page	I
          //   219: iconst_1
          //   220: if_icmpne +420 -> 640
          //   223: aload_0
          //   224: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   227: getfield 123	com/fab/sale/SaleDetailsActivity:thePairedProducts	Ljava/util/ArrayList;
          //   230: ifnull +665 -> 895
          //   233: aload_0
          //   234: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   237: getfield 123	com/fab/sale/SaleDetailsActivity:thePairedProducts	Ljava/util/ArrayList;
          //   240: invokevirtual 128	java/util/ArrayList:clear	()V
          //   243: new 30	org/json/JSONObject
          //   246: dup
          //   247: invokespecial 68	org/json/JSONObject:<init>	()V
          //   250: pop
          //   251: aload_0
          //   252: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   255: aload_2
          //   256: ldc 130
          //   258: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   261: invokestatic 134	com/fab/sale/SaleDetailsActivity:access$10	(Lcom/fab/sale/SaleDetailsActivity;Ljava/lang/String;)V
          //   264: aload_2
          //   265: ldc 136
          //   267: invokevirtual 74	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   270: astore 65
          //   272: aload 65
          //   274: astore 41
          //   276: aload 41
          //   278: ifnull +133 -> 411
          //   281: aload_0
          //   282: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   285: new 138	com/fab/facebook/FabFBShareBO
          //   288: dup
          //   289: invokespecial 139	com/fab/facebook/FabFBShareBO:<init>	()V
          //   292: putfield 143	com/fab/sale/SaleDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   295: aload_0
          //   296: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   299: getfield 143	com/fab/sale/SaleDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   302: aload 41
          //   304: ldc 145
          //   306: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   309: invokevirtual 149	com/fab/facebook/FabFBShareBO:setLink	(Ljava/lang/String;)V
          //   312: aload_0
          //   313: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   316: getfield 143	com/fab/sale/SaleDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   319: aload 41
          //   321: ldc 151
          //   323: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   326: invokevirtual 154	com/fab/facebook/FabFBShareBO:setCaption	(Ljava/lang/String;)V
          //   329: aload_0
          //   330: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   333: getfield 143	com/fab/sale/SaleDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   336: aload 41
          //   338: ldc 156
          //   340: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   343: invokevirtual 159	com/fab/facebook/FabFBShareBO:setDescription	(Ljava/lang/String;)V
          //   346: aload_0
          //   347: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   350: getfield 143	com/fab/sale/SaleDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   353: aload 41
          //   355: ldc 161
          //   357: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   360: invokevirtual 164	com/fab/facebook/FabFBShareBO:setPicture	(Ljava/lang/String;)V
          //   363: aload_0
          //   364: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   367: getfield 143	com/fab/sale/SaleDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   370: aload 41
          //   372: ldc 166
          //   374: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   377: invokevirtual 169	com/fab/facebook/FabFBShareBO:setName	(Ljava/lang/String;)V
          //   380: aload_0
          //   381: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   384: getfield 143	com/fab/sale/SaleDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   387: aload 41
          //   389: ldc 166
          //   391: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   394: invokevirtual 169	com/fab/facebook/FabFBShareBO:setName	(Ljava/lang/String;)V
          //   397: aload_0
          //   398: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   401: aload 41
          //   403: ldc 171
          //   405: invokevirtual 53	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   408: putfield 174	com/fab/sale/SaleDetailsActivity:hasFBShareToken	Z
          //   411: new 30	org/json/JSONObject
          //   414: dup
          //   415: invokespecial 68	org/json/JSONObject:<init>	()V
          //   418: pop
          //   419: aload_2
          //   420: ldc 176
          //   422: invokevirtual 74	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   425: astore 57
          //   427: aload 57
          //   429: astore 44
          //   431: aload 44
          //   433: ifnull +31 -> 464
          //   436: aload_0
          //   437: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   440: aload 44
          //   442: ldc 178
          //   444: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   447: invokestatic 181	com/fab/sale/SaleDetailsActivity:access$11	(Lcom/fab/sale/SaleDetailsActivity;Ljava/lang/String;)V
          //   450: aload_0
          //   451: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   454: aload 44
          //   456: ldc 183
          //   458: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   461: invokestatic 186	com/fab/sale/SaleDetailsActivity:access$12	(Lcom/fab/sale/SaleDetailsActivity;Ljava/lang/String;)V
          //   464: aload_0
          //   465: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   468: aload_2
          //   469: ldc 188
          //   471: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   474: putfield 192	com/fab/sale/SaleDetailsActivity:smsShare	Ljava/lang/String;
          //   477: iconst_0
          //   478: istore 46
          //   480: aload 10
          //   482: ldc 194
          //   484: invokevirtual 80	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   487: istore 54
          //   489: iload 54
          //   491: istore 46
          //   493: aload_0
          //   494: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   497: aload 10
          //   499: ldc 196
          //   501: invokevirtual 80	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   504: invokestatic 200	com/fab/sale/SaleDetailsActivity:access$13	(Lcom/fab/sale/SaleDetailsActivity;I)V
          //   507: aload 13
          //   509: aload_0
          //   510: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   513: invokestatic 204	com/fab/sale/SaleDetailsActivity:access$14	(Lcom/fab/sale/SaleDetailsActivity;)I
          //   516: invokevirtual 207	com/fab/sale/bo/SaleDetailsBO:setSaleId	(I)V
          //   519: aload 13
          //   521: aload 10
          //   523: ldc 209
          //   525: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   528: invokevirtual 212	com/fab/sale/bo/SaleDetailsBO:setSalesDuration	(Ljava/lang/String;)V
          //   531: aload 13
          //   533: aload 10
          //   535: ldc 214
          //   537: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   540: invokevirtual 217	com/fab/sale/bo/SaleDetailsBO:setPrimaryTitle	(Ljava/lang/String;)V
          //   543: aload 13
          //   545: aload 10
          //   547: ldc 219
          //   549: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   552: invokevirtual 222	com/fab/sale/bo/SaleDetailsBO:setSaleName	(Ljava/lang/String;)V
          //   555: aload 13
          //   557: aload 10
          //   559: ldc 224
          //   561: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   564: invokevirtual 227	com/fab/sale/bo/SaleDetailsBO:setHtmlDescription	(Ljava/lang/String;)V
          //   567: iload 46
          //   569: ifne +37 -> 606
          //   572: aload_0
          //   573: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   576: invokestatic 231	com/fab/sale/SaleDetailsActivity:access$15	(Lcom/fab/sale/SaleDetailsActivity;)Landroid/widget/ImageView;
          //   579: aconst_null
          //   580: invokevirtual 237	android/widget/ImageView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
          //   583: aload_0
          //   584: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   587: invokestatic 240	com/fab/sale/SaleDetailsActivity:access$16	(Lcom/fab/sale/SaleDetailsActivity;)Landroid/widget/ImageView;
          //   590: aconst_null
          //   591: invokevirtual 237	android/widget/ImageView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
          //   594: aload_0
          //   595: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   598: invokestatic 231	com/fab/sale/SaleDetailsActivity:access$15	(Lcom/fab/sale/SaleDetailsActivity;)Landroid/widget/ImageView;
          //   601: bipush 8
          //   603: invokevirtual 243	android/widget/ImageView:setVisibility	(I)V
          //   606: aload 13
          //   608: new 245	java/lang/StringBuilder
          //   611: dup
          //   612: ldc 247
          //   614: invokespecial 249	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   617: aload 10
          //   619: ldc 251
          //   621: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   624: ldc 253
          //   626: ldc 255
          //   628: invokevirtual 261	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   631: invokevirtual 265	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   634: invokevirtual 266	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   637: invokevirtual 269	com/fab/sale/bo/SaleDetailsBO:setSaleImageUrl	(Ljava/lang/String;)V
          //   640: new 271	org/json/JSONArray
          //   643: dup
          //   644: invokespecial 272	org/json/JSONArray:<init>	()V
          //   647: astore 14
          //   649: aload 10
          //   651: ldc_w 274
          //   654: invokevirtual 278	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   657: astore 37
          //   659: aload 37
          //   661: astore 14
          //   663: aload_0
          //   664: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   667: getfield 281	com/fab/sale/SaleDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   670: ifnull +258 -> 928
          //   673: aload_0
          //   674: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   677: getfield 281	com/fab/sale/SaleDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   680: invokevirtual 128	java/util/ArrayList:clear	()V
          //   683: iconst_0
          //   684: istore 16
          //   686: aload 14
          //   688: invokevirtual 285	org/json/JSONArray:length	()I
          //   691: istore 17
          //   693: iload 16
          //   695: iload 17
          //   697: if_icmplt +248 -> 945
          //   700: aload_0
          //   701: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   704: getfield 281	com/fab/sale/SaleDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   707: invokevirtual 288	java/util/ArrayList:size	()I
          //   710: iconst_2
          //   711: irem
          //   712: iconst_1
          //   713: if_icmpne +30 -> 743
          //   716: aload_0
          //   717: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   720: getfield 281	com/fab/sale/SaleDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   723: aload_0
          //   724: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   727: getfield 281	com/fab/sale/SaleDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   730: invokevirtual 288	java/util/ArrayList:size	()I
          //   733: new 290	com/fab/sale/bo/ProductDetailsBO
          //   736: dup
          //   737: invokespecial 291	com/fab/sale/bo/ProductDetailsBO:<init>	()V
          //   740: invokevirtual 295	java/util/ArrayList:add	(ILjava/lang/Object;)V
          //   743: aload 13
          //   745: aload_0
          //   746: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   749: getfield 281	com/fab/sale/SaleDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   752: invokevirtual 299	com/fab/sale/bo/SaleDetailsBO:setTheProducts	(Ljava/util/ArrayList;)V
          //   755: iconst_0
          //   756: istore 29
          //   758: aload_0
          //   759: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   762: getfield 281	com/fab/sale/SaleDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   765: invokevirtual 288	java/util/ArrayList:size	()I
          //   768: istore 30
          //   770: iload 29
          //   772: iload 30
          //   774: if_icmplt +336 -> 1110
          //   777: aload_0
          //   778: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   781: getfield 119	com/fab/sale/SaleDetailsActivity:page	I
          //   784: iconst_1
          //   785: if_icmpne +12 -> 797
          //   788: aload_0
          //   789: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   792: aload 13
          //   794: invokevirtual 303	com/fab/sale/SaleDetailsActivity:populateUI	(Lcom/fab/sale/bo/SaleDetailsBO;)V
          //   797: aload_0
          //   798: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   801: invokestatic 306	com/fab/sale/SaleDetailsActivity:access$17	(Lcom/fab/sale/SaleDetailsActivity;)Ljava/lang/String;
          //   804: ldc_w 308
          //   807: invokevirtual 311	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
          //   810: ifeq +421 -> 1231
          //   813: aload_0
          //   814: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   817: invokestatic 315	com/fab/sale/SaleDetailsActivity:access$18	(Lcom/fab/sale/SaleDetailsActivity;)Lcom/fab/sale/UpcomingSalesDetailsAdapter;
          //   820: invokevirtual 320	com/fab/sale/UpcomingSalesDetailsAdapter:downloadTheImages	()V
          //   823: aload_2
          //   824: ldc_w 322
          //   827: invokevirtual 53	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   830: ifeq +424 -> 1254
          //   833: aload_0
          //   834: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   837: iconst_1
          //   838: putfield 325	com/fab/sale/SaleDetailsActivity:nextPageExists	Z
          //   841: aload_0
          //   842: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   845: astore 36
          //   847: aload 36
          //   849: iconst_1
          //   850: aload 36
          //   852: getfield 119	com/fab/sale/SaleDetailsActivity:page	I
          //   855: iadd
          //   856: putfield 119	com/fab/sale/SaleDetailsActivity:page	I
          //   859: aload_0
          //   860: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   863: invokestatic 329	com/fab/sale/SaleDetailsActivity:access$22	(Lcom/fab/sale/SaleDetailsActivity;)Landroid/widget/RelativeLayout;
          //   866: bipush 8
          //   868: invokevirtual 332	android/widget/RelativeLayout:setVisibility	(I)V
          //   871: aload_0
          //   872: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   875: invokestatic 336	com/fab/sale/SaleDetailsActivity:access$23	(Lcom/fab/sale/SaleDetailsActivity;)Lcom/fab/views/ErrorMessage;
          //   878: bipush 8
          //   880: invokevirtual 339	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   883: aload_0
          //   884: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   887: getfield 343	com/fab/sale/SaleDetailsActivity:saleDetailsRel	Landroid/view/View;
          //   890: iconst_0
          //   891: invokevirtual 346	android/view/View:setVisibility	(I)V
          //   894: return
          //   895: aload_0
          //   896: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   899: new 125	java/util/ArrayList
          //   902: dup
          //   903: invokespecial 347	java/util/ArrayList:<init>	()V
          //   906: putfield 123	com/fab/sale/SaleDetailsActivity:thePairedProducts	Ljava/util/ArrayList;
          //   909: goto -666 -> 243
          //   912: astore 40
          //   914: aconst_null
          //   915: astore 41
          //   917: goto -641 -> 276
          //   920: astore 43
          //   922: aconst_null
          //   923: astore 44
          //   925: goto -494 -> 431
          //   928: aload_0
          //   929: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   932: new 125	java/util/ArrayList
          //   935: dup
          //   936: invokespecial 347	java/util/ArrayList:<init>	()V
          //   939: putfield 281	com/fab/sale/SaleDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   942: goto -259 -> 683
          //   945: new 290	com/fab/sale/bo/ProductDetailsBO
          //   948: dup
          //   949: invokespecial 291	com/fab/sale/bo/ProductDetailsBO:<init>	()V
          //   952: astore 18
          //   954: aload 14
          //   956: iload 16
          //   958: invokevirtual 350	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   961: astore 28
          //   963: aload 28
          //   965: astore 12
          //   967: aload 18
          //   969: aload_0
          //   970: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   973: invokestatic 204	com/fab/sale/SaleDetailsActivity:access$14	(Lcom/fab/sale/SaleDetailsActivity;)I
          //   976: invokevirtual 351	com/fab/sale/bo/ProductDetailsBO:setSaleId	(I)V
          //   979: aload 18
          //   981: aload 12
          //   983: ldc 196
          //   985: invokevirtual 80	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   988: invokevirtual 354	com/fab/sale/bo/ProductDetailsBO:setId	(I)V
          //   991: aload 18
          //   993: aload 12
          //   995: ldc_w 356
          //   998: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1001: invokevirtual 357	com/fab/sale/bo/ProductDetailsBO:setName	(Ljava/lang/String;)V
          //   1004: aload 18
          //   1006: new 245	java/lang/StringBuilder
          //   1009: dup
          //   1010: ldc 247
          //   1012: invokespecial 249	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   1015: aload 12
          //   1017: ldc 251
          //   1019: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1022: ldc 253
          //   1024: ldc_w 359
          //   1027: invokevirtual 261	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   1030: invokevirtual 265	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1033: invokevirtual 266	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   1036: invokevirtual 362	com/fab/sale/bo/ProductDetailsBO:setImageUrl	(Ljava/lang/String;)V
          //   1039: aload 18
          //   1041: aload 12
          //   1043: ldc_w 364
          //   1046: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1049: invokevirtual 367	com/fab/sale/bo/ProductDetailsBO:setFabPrice	(Ljava/lang/String;)V
          //   1052: aload 18
          //   1054: aload 12
          //   1056: ldc_w 369
          //   1059: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1062: invokevirtual 372	com/fab/sale/bo/ProductDetailsBO:setMsrp	(Ljava/lang/String;)V
          //   1065: aload 18
          //   1067: aload 12
          //   1069: ldc_w 374
          //   1072: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1075: invokevirtual 377	com/fab/sale/bo/ProductDetailsBO:setDiscountPct	(Ljava/lang/String;)V
          //   1078: aload 18
          //   1080: aload 12
          //   1082: ldc_w 379
          //   1085: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1088: invokevirtual 382	com/fab/sale/bo/ProductDetailsBO:setStatus	(Ljava/lang/String;)V
          //   1091: aload_0
          //   1092: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1095: getfield 281	com/fab/sale/SaleDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   1098: aload 18
          //   1100: invokevirtual 385	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   1103: pop
          //   1104: iinc 16 1
          //   1107: goto -414 -> 693
          //   1110: new 387	com/fab/sale/bo/PairedProducts
          //   1113: dup
          //   1114: invokespecial 388	com/fab/sale/bo/PairedProducts:<init>	()V
          //   1117: astore 31
          //   1119: aload 31
          //   1121: aload_0
          //   1122: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1125: getfield 281	com/fab/sale/SaleDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   1128: iload 29
          //   1130: invokevirtual 392	java/util/ArrayList:get	(I)Ljava/lang/Object;
          //   1133: checkcast 290	com/fab/sale/bo/ProductDetailsBO
          //   1136: invokevirtual 396	com/fab/sale/bo/PairedProducts:setFirstProduct	(Lcom/fab/sale/bo/ProductDetailsBO;)V
          //   1139: iload 29
          //   1141: iconst_1
          //   1142: iadd
          //   1143: istore 32
          //   1145: aload 31
          //   1147: aload_0
          //   1148: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1151: getfield 281	com/fab/sale/SaleDetailsActivity:theProducts	Ljava/util/ArrayList;
          //   1154: iload 32
          //   1156: invokevirtual 392	java/util/ArrayList:get	(I)Ljava/lang/Object;
          //   1159: checkcast 290	com/fab/sale/bo/ProductDetailsBO
          //   1162: invokevirtual 399	com/fab/sale/bo/PairedProducts:setSecondProduct	(Lcom/fab/sale/bo/ProductDetailsBO;)V
          //   1165: aload_0
          //   1166: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1169: getfield 123	com/fab/sale/SaleDetailsActivity:thePairedProducts	Ljava/util/ArrayList;
          //   1172: aload 31
          //   1174: invokevirtual 385	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   1177: pop
          //   1178: iload 32
          //   1180: iconst_1
          //   1181: iadd
          //   1182: istore 29
          //   1184: goto -414 -> 770
          //   1187: aload 5
          //   1189: invokestatic 67	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   1192: ifne -395 -> 797
          //   1195: aload_0
          //   1196: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1199: getfield 403	com/fab/sale/SaleDetailsActivity:context	Landroid/content/Context;
          //   1202: aload 5
          //   1204: invokestatic 407	com/fab/utils/FabUtils:showAlert	(Landroid/content/Context;Ljava/lang/String;)V
          //   1207: aload_0
          //   1208: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1211: invokevirtual 411	com/fab/sale/SaleDetailsActivity:getApplication	()Landroid/app/Application;
          //   1214: checkcast 413	com/fab/FabApplication
          //   1217: iconst_1
          //   1218: invokevirtual 417	com/fab/FabApplication:setRefreshActivityOnRestart	(Z)V
          //   1221: aload_0
          //   1222: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1225: invokevirtual 420	com/fab/sale/SaleDetailsActivity:finish	()V
          //   1228: goto -431 -> 797
          //   1231: aload_0
          //   1232: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1235: invokestatic 424	com/fab/sale/SaleDetailsActivity:access$19	(Lcom/fab/sale/SaleDetailsActivity;)Lcom/fab/sale/SalesDetailsAdapter;
          //   1238: invokevirtual 427	com/fab/sale/SalesDetailsAdapter:downloadTheImages	()V
          //   1241: aload_0
          //   1242: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1245: invokestatic 424	com/fab/sale/SaleDetailsActivity:access$19	(Lcom/fab/sale/SaleDetailsActivity;)Lcom/fab/sale/SalesDetailsAdapter;
          //   1248: invokevirtual 430	com/fab/sale/SalesDetailsAdapter:notifyDataSetChanged	()V
          //   1251: goto -428 -> 823
          //   1254: aload_0
          //   1255: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1258: iconst_0
          //   1259: putfield 325	com/fab/sale/SaleDetailsActivity:nextPageExists	Z
          //   1262: aload_0
          //   1263: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1266: invokestatic 434	com/fab/sale/SaleDetailsActivity:access$20	(Lcom/fab/sale/SaleDetailsActivity;)Landroid/widget/ListView;
          //   1269: aload_0
          //   1270: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1273: invokestatic 438	com/fab/sale/SaleDetailsActivity:access$21	(Lcom/fab/sale/SaleDetailsActivity;)Landroid/view/View;
          //   1276: invokevirtual 444	android/widget/ListView:removeFooterView	(Landroid/view/View;)Z
          //   1279: pop
          //   1280: goto -421 -> 859
          //   1283: astore 34
          //   1285: aload_0
          //   1286: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1289: iconst_0
          //   1290: putfield 325	com/fab/sale/SaleDetailsActivity:nextPageExists	Z
          //   1293: aload_0
          //   1294: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1297: invokestatic 438	com/fab/sale/SaleDetailsActivity:access$21	(Lcom/fab/sale/SaleDetailsActivity;)Landroid/view/View;
          //   1300: bipush 8
          //   1302: invokevirtual 346	android/view/View:setVisibility	(I)V
          //   1305: goto -446 -> 859
          //   1308: aload_0
          //   1309: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1312: invokestatic 329	com/fab/sale/SaleDetailsActivity:access$22	(Lcom/fab/sale/SaleDetailsActivity;)Landroid/widget/RelativeLayout;
          //   1315: bipush 8
          //   1317: invokevirtual 332	android/widget/RelativeLayout:setVisibility	(I)V
          //   1320: aload_0
          //   1321: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1324: invokestatic 448	com/fab/sale/SaleDetailsActivity:access$24	(Lcom/fab/sale/SaleDetailsActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   1327: invokevirtual 453	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   1330: invokestatic 67	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   1333: ifne -439 -> 894
          //   1336: aload_0
          //   1337: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1340: invokestatic 336	com/fab/sale/SaleDetailsActivity:access$23	(Lcom/fab/sale/SaleDetailsActivity;)Lcom/fab/views/ErrorMessage;
          //   1343: iconst_0
          //   1344: invokevirtual 339	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   1347: aload_0
          //   1348: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1351: invokestatic 336	com/fab/sale/SaleDetailsActivity:access$23	(Lcom/fab/sale/SaleDetailsActivity;)Lcom/fab/views/ErrorMessage;
          //   1354: aload_0
          //   1355: getfield 18	com/fab/sale/SaleDetailsActivity$13:this$0	Lcom/fab/sale/SaleDetailsActivity;
          //   1358: invokestatic 448	com/fab/sale/SaleDetailsActivity:access$24	(Lcom/fab/sale/SaleDetailsActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   1361: invokevirtual 453	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   1364: invokevirtual 456	com/fab/views/ErrorMessage:setErrorMsg	(Ljava/lang/String;)V
          //   1367: goto -473 -> 894
          //   1370: astore 19
          //   1372: goto -405 -> 967
          //   1375: astore 26
          //   1377: goto -286 -> 1091
          //   1380: astore 25
          //   1382: goto -304 -> 1078
          //   1385: astore 24
          //   1387: goto -322 -> 1065
          //   1390: astore 23
          //   1392: goto -340 -> 1052
          //   1395: astore 22
          //   1397: goto -358 -> 1039
          //   1400: astore 21
          //   1402: goto -398 -> 1004
          //   1405: astore 20
          //   1407: goto -416 -> 991
          //   1410: astore 15
          //   1412: goto -749 -> 663
          //   1415: astore 53
          //   1417: goto -777 -> 640
          //   1420: astore 52
          //   1422: goto -855 -> 567
          //   1425: astore 51
          //   1427: goto -872 -> 555
          //   1430: astore 50
          //   1432: goto -889 -> 543
          //   1435: astore 49
          //   1437: goto -906 -> 531
          //   1440: astore 48
          //   1442: goto -923 -> 519
          //   1445: astore 47
          //   1447: goto -954 -> 493
          //   1450: astore 45
          //   1452: goto -975 -> 477
          //   1455: astore 56
          //   1457: goto -993 -> 464
          //   1460: astore 55
          //   1462: goto -1012 -> 450
          //   1465: astore 64
          //   1467: goto -1056 -> 411
          //   1470: astore 63
          //   1472: goto -1075 -> 397
          //   1475: astore 62
          //   1477: goto -1097 -> 380
          //   1480: astore 61
          //   1482: goto -1119 -> 363
          //   1485: astore 60
          //   1487: goto -1141 -> 346
          //   1490: astore 59
          //   1492: goto -1163 -> 329
          //   1495: astore 58
          //   1497: goto -1185 -> 312
          //   1500: astore 39
          //   1502: goto -1238 -> 264
          //   1505: astore 11
          //   1507: goto -1313 -> 194
          //   1510: astore 9
          //   1512: goto -1339 -> 173
          //   1515: astore 8
          //   1517: goto -1419 -> 98
          //   1520: astore 6
          //   1522: goto -1453 -> 69
          //   1525: astore 4
          //   1527: goto -1474 -> 53
          //
          // Exception table:
          //   from	to	target	type
          //   264	272	912	org/json/JSONException
          //   419	427	920	org/json/JSONException
          //   823	859	1283	org/json/JSONException
          //   1254	1280	1283	org/json/JSONException
          //   954	963	1370	org/json/JSONException
          //   1078	1091	1375	org/json/JSONException
          //   1065	1078	1380	org/json/JSONException
          //   1052	1065	1385	org/json/JSONException
          //   1039	1052	1390	org/json/JSONException
          //   1004	1039	1395	org/json/JSONException
          //   991	1004	1400	org/json/JSONException
          //   979	991	1405	org/json/JSONException
          //   649	659	1410	org/json/JSONException
          //   606	640	1415	org/json/JSONException
          //   555	567	1420	org/json/JSONException
          //   543	555	1425	org/json/JSONException
          //   531	543	1430	org/json/JSONException
          //   519	531	1435	org/json/JSONException
          //   493	519	1440	org/json/JSONException
          //   480	489	1445	org/json/JSONException
          //   464	477	1450	org/json/JSONException
          //   450	464	1455	org/json/JSONException
          //   436	450	1460	org/json/JSONException
          //   397	411	1465	org/json/JSONException
          //   380	397	1470	org/json/JSONException
          //   363	380	1475	org/json/JSONException
          //   346	363	1480	org/json/JSONException
          //   329	346	1485	org/json/JSONException
          //   312	329	1490	org/json/JSONException
          //   295	312	1495	org/json/JSONException
          //   251	264	1500	org/json/JSONException
          //   182	190	1505	org/json/JSONException
          //   98	173	1510	org/json/JSONException
          //   86	94	1515	org/json/JSONException
          //   57	65	1520	org/json/JSONException
          //   46	53	1525	org/json/JSONException
        }
      });
      ExecuteRequestAsyncTask localExecuteRequestAsyncTask = this.task;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/sale/" + this.saleId, "?page_no=" + this.page);
      localExecuteRequestAsyncTask.execute(arrayOfString);
    }
  }

  private void refreshActivity()
  {
    FabHelper.smoothScroll(this.salesListView);
    this.page = 1;
    if (!this.saleCategory.equalsIgnoreCase("upcoming"))
      this.salesListView.addFooterView(this.footerView);
    this.timer.setVisibility(4);
    FabHelper.stopAndRestartCartTimer(getApplicationContext(), 0L, this.mHandler);
    this.titleWidget.clearCartButton();
    this.saleDetailsRel.setVisibility(8);
    this.saleDuration.setVisibility(8);
    this.listViewHeader.setVisibility(8);
    this.headerMesh.setVisibility(8);
    this.error.setVisibility(8);
    this.progressBar.setVisibility(0);
    loadSaleDetails();
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
    setContentView(2130903083);
    this.context = this;
    if (TextUtils.isEmpty(FabSharedPrefs.getDeviceDensity()))
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
      FabSharedPrefs.setDeviceDensity(localDisplayMetrics.densityDpi);
    }
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("saleDetails"));
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
    isActivityAlive = true;
    this.loadingMore = false;
    this.nextPageExists = false;
    this.page = 1;
    this.titleWidget = ((TitleWidget)findViewById(2131361810));
    this.error = ((ErrorMessage)findViewById(2131361811));
    this.extras = getIntent().getExtras();
    if (this.extras != null)
    {
      this.saleId = this.extras.getInt("SALE_ID");
      this.saleCategory = this.extras.getString("SALE_CATEGORY");
    }
    this.timer = ((FabTextView)findViewById(2131361795));
    this.timer.setFont("georgiai.ttf");
    this.alertbox = new AlertDialog.Builder(this);
    this.saleDetailsRel = ((RelativeLayout)findViewById(2131362005));
    this.mHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Bundle localBundle = paramAnonymousMessage.getData();
        if (FabUtils.isLogEnabled())
          FabUtils.log(SaleDetailsActivity.this.TAG, localBundle.toString());
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
            SaleDetailsActivity.this.timer.setVisibility(4);
          if (SaleDetailsActivity.isActivityAlive)
          {
            AlertDialog.Builder localBuilder = SaleDetailsActivity.this.alertbox;
            if (localBundle.getInt("Error") == 1);
            for (int j = 2130837504; ; j = 2130837507)
            {
              localBuilder.setIcon(j);
              SaleDetailsActivity.this.alertbox.setTitle(localBundle.getString("Title"));
              SaleDetailsActivity.this.alertbox.setMessage(localBundle.getString("Message"));
              SaleDetailsActivity.this.alertbox.setNeutralButton("OK", null);
              if (!SaleDetailsActivity.isActivityAlive)
                break;
              SaleDetailsActivity.this.alertbox.show();
              break;
            }
            SaleDetailsActivity.this.timer.setVisibility(0);
            SaleDetailsActivity.this.timer.setText(str);
          }
        }
      }
    };
    loadSaleDetails();
    initilizeUI();
    this.headerCartButton = this.titleWidget.getCartButton();
  }

  protected void onDestroy()
  {
    this.tracker.stopSession();
    isActivityAlive = false;
    finish();
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
    if (this.upcomingSalesAdapter != null)
      this.upcomingSalesAdapter.stopImageDownloads();
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
        Intent localIntent = new Intent(SaleDetailsActivity.this.context, MoreActivity.class);
        SaleDetailsActivity.this.startActivity(localIntent);
        return false;
      }
    });
    MenuItem localMenuItem2 = paramMenu.findItem(2131362109);
    localMenuItem2.setVisible(true);
    localMenuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(SaleDetailsActivity.this.context, FabWebViewActivity.class);
        SaleDetailsActivity.this.startActivity(localIntent);
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
          FabUtils.showGuestAlert(SaleDetailsActivity.this.context, SaleDetailsActivity.this.context.getResources().getString(2131165208), SaleDetailsActivity.this.context.getResources().getString(2131165209), false);
        while (true)
        {
          return false;
          Intent localIntent = new Intent(SaleDetailsActivity.this.context, InviteActivity.class);
          SaleDetailsActivity.this.startActivity(localIntent);
        }
      }
    });
    this.refreshItem = paramMenu.findItem(2131362103);
    this.refreshItem.setVisible(true);
    this.refreshItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        SaleDetailsActivity.this.refreshActivity();
        return false;
      }
    });
    MenuItem localMenuItem4 = paramMenu.findItem(2131362033);
    localMenuItem4.setVisible(true);
    localMenuItem4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        if (FabSharedPrefs.isGuestUser())
          FabUtils.showGuestAlert(SaleDetailsActivity.this.context, SaleDetailsActivity.this.context.getResources().getString(2131165208), SaleDetailsActivity.this.context.getResources().getString(2131165212), false);
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
          if (SaleDetailsActivity.this.fbShareData != null)
          {
            SaleDetailsActivity.this.fbClient = new FabFBClient(SaleDetailsActivity.this.context);
            SaleDetailsActivity.this.fbClient.setFBShareData(SaleDetailsActivity.this.fbShareData);
            SaleDetailsActivity.this.fbClient.setHasFBShareToken(SaleDetailsActivity.this.hasFBShareToken);
            SaleDetailsActivity.this.fbClient.sendRequest("publish_stream");
          }
          return false;
        }
      });
      MenuItem localMenuItem5 = paramMenu.findItem(2131362105);
      localMenuItem5.setVisible(true);
      localMenuItem5.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
      {
        public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
        {
          if (SaleDetailsActivity.this.twShareText != null)
          {
            Intent localIntent = new Intent(SaleDetailsActivity.this.context, FabTwitterClient.class);
            localIntent.putExtra("TWEET_TEXT", SaleDetailsActivity.this.twShareText);
            SaleDetailsActivity.this.startActivity(localIntent);
          }
          return false;
        }
      });
      MenuItem localMenuItem6 = paramMenu.findItem(2131362107);
      localMenuItem6.setVisible(true);
      localMenuItem6.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
      {
        public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
        {
          if (SaleDetailsActivity.this.emailBody != null)
          {
            Intent localIntent = new Intent("android.intent.action.SEND");
            localIntent.setType("text/html");
            localIntent.putExtra("android.intent.extra.SUBJECT", SaleDetailsActivity.this.emailSubject);
            localIntent.putExtra("android.intent.extra.TEXT", Html.fromHtml(SaleDetailsActivity.this.emailBody));
            SaleDetailsActivity.this.startActivity(localIntent);
          }
          return false;
        }
      });
      MenuItem localMenuItem7 = paramMenu.findItem(2131362108);
      localMenuItem7.setVisible(true);
      localMenuItem7.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
      {
        public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
        {
          if (SaleDetailsActivity.this.smsShare != null)
          {
            Intent localIntent = new Intent("android.intent.action.VIEW");
            localIntent.setType("vnd.android-dir/mms-sms");
            localIntent.setData(Uri.parse("sms:"));
            localIntent.putExtra("sms_body", SaleDetailsActivity.this.smsShare);
            SaleDetailsActivity.this.startActivity(localIntent);
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
    this.error.setVisibility(8);
    isActivityAlive = true;
    if (this.adapter != null)
      this.adapter.notifyDataSetChanged();
    while (true)
    {
      super.onResume();
      return;
      if (this.upcomingSalesAdapter != null)
        this.upcomingSalesAdapter.notifyDataSetChanged();
      else
        refreshActivity();
    }
  }

  public void populateUI(SaleDetailsBO paramSaleDetailsBO)
  {
    this.saleDuration.setText(paramSaleDetailsBO.getSalesDuration());
    this.primaryTitle.setText(paramSaleDetailsBO.getPrimaryTitle());
    this.secondaryTitle.setText(paramSaleDetailsBO.getSaleName());
    this.saleDuration.setVisibility(0);
    this.listViewHeader.setVisibility(0);
    this.headerMesh.setVisibility(0);
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
 * Qualified Name:     com.fab.sale.SaleDetailsActivity
 * JD-Core Version:    0.6.2
 */