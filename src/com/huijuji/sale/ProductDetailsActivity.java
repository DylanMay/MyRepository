package com.fab.sale;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import com.fab.FabApplication;
import com.fab.FabIndex;
import com.fab.FabSignin;
import com.fab.InviteActivity;
import com.fab.MoreActivity;
import com.fab.facebook.FabFBClient;
import com.fab.facebook.FabFBShareBO;
import com.fab.twitter.FabTwitterClient;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabHelper;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.ErrorMessage;
import com.fab.views.FabButton;
import com.fab.views.FabFBTimelineDialog;
import com.fab.views.FabRotatedTextView;
import com.fab.views.FabTextView;
import com.fab.views.TitleWidget;
import com.fab.webviews.FabWebViewActivity;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailsActivity extends Activity
{
  private String TAG = "PRODUCT_DETAILS";
  private FabButton addToCart;
  private AlertDialog.Builder alertbox;
  private Animation animation;
  private FabButton buyNow;
  private FabButton buyNowBottom;
  private FabButton cancel;
  private int cartCount;
  private Context context;
  private FabTextView detailsValue;
  private ProgressDialog dialog;
  private FabRotatedTextView discountPct;
  public String emailBody;
  public String emailSubject;
  private ErrorMessage error;
  private Bundle extras;
  private FabTextView fabPrice;
  private FabTextView fabPriceBottom;
  private FabTextView fabText;
  private String favCount;
  private Button favCountIcon;
  private ImageView favIcon;
  public int favImageId;
  protected FabFBClient fbClient;
  public FabFBShareBO fbShareData;
  private MenuItem fbshareItem;
  protected boolean hasFBShareToken;
  private FabButton headerCartButton;
  private FabTextView headerTitle;
  private ImageView headerTitleImage;
  public FabHelper helper = FabHelper.getFabHelper();
  private ProgressBar imagePBar;
  private LayoutInflater inflator;
  private boolean isActivityAlive;
  public boolean isFav;
  private boolean isTimelineEnabled;
  private Handler mHandler;
  private WebView moreDetails;
  private FabTextView moreDetailsLable;
  private FabTextView originalPrice;
  private FabTextView originalPriceBottom;
  public ProgressBar pBar;
  private HashMap<String, String> parameters;
  private ViewGroup parentView;
  private View popUp;
  private RelativeLayout popUpRel;
  private View popUpTimer;
  private int productId;
  private ImageView productImage;
  private ScrollView productScrollView;
  private JSONObject productSizeQuantity;
  private HashMap<String, Integer> productSizes;
  private ArrayList<String> productSizesArray;
  private WebView productStory;
  protected Integer qtyAddToCart;
  private ArrayAdapter<Integer> qtyArray;
  private FabTextView quantity;
  private Spinner quantitySpinner;
  private ImageView redTriangle;
  private MenuItem refreshItem;
  private PopupWindow resultPopUp;
  private FabTextView ribbonText;
  private RelativeLayout ribbonView;
  private View runtimeDataRow;
  private LinearLayout runtimeDetailsLayout;
  private View runtimeHeaderView;
  private int saleId;
  private View showDetailsDataRow;
  private LinearLayout showDetailsLayout;
  private FabTextView size;
  protected String sizeAddToCart = "";
  private ArrayAdapter<Object> sizeArray;
  private RelativeLayout sizeRel;
  private Spinner sizeSpinner;
  public String smsShare;
  private ExecuteRequestAsyncTask task;
  private FabTextView textFabStamp;
  public ArrayList<ProductZoomBo> theProductsGallery;
  private ArrayList<Integer> theQuantity = new ArrayList();
  private JSONObject timelineInfo;
  private FabTextView timer;
  private PopupWindow timerPopUpWindow;
  public TitleWidget titleWidget;
  private GoogleAnalyticsTracker tracker;
  private FabTextView tvTimerWindow;
  private String twShareText;

  private void addProductToCart()
  {
    System.gc();
    this.addToCart.setEnabled(false);
    if ((this.isActivityAlive) && (this.dialog != null))
    {
      this.dialog.setCancelable(true);
      this.dialog.setMessage("Please wait...");
      this.dialog.show();
    }
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
        //   0: aload_0
        //   1: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   4: invokestatic 32	com/fab/sale/ProductDetailsActivity:access$11	(Lcom/fab/sale/ProductDetailsActivity;)Z
        //   7: ifeq +36 -> 43
        //   10: aload_0
        //   11: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   14: invokestatic 36	com/fab/sale/ProductDetailsActivity:access$65	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/app/ProgressDialog;
        //   17: ifnull +26 -> 43
        //   20: aload_0
        //   21: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   24: invokestatic 36	com/fab/sale/ProductDetailsActivity:access$65	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/app/ProgressDialog;
        //   27: invokevirtual 42	android/app/ProgressDialog:isShowing	()Z
        //   30: ifeq +13 -> 43
        //   33: aload_0
        //   34: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   37: invokestatic 36	com/fab/sale/ProductDetailsActivity:access$65	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/app/ProgressDialog;
        //   40: invokevirtual 45	android/app/ProgressDialog:cancel	()V
        //   43: aload_0
        //   44: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   47: invokestatic 49	com/fab/sale/ProductDetailsActivity:access$21	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabButton;
        //   50: iconst_1
        //   51: invokevirtual 55	com/fab/views/FabButton:setEnabled	(Z)V
        //   54: aload_0
        //   55: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   58: invokestatic 58	com/fab/sale/ProductDetailsActivity:access$9	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabButton;
        //   61: iconst_1
        //   62: invokevirtual 55	com/fab/views/FabButton:setEnabled	(Z)V
        //   65: aload_0
        //   66: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   69: invokestatic 58	com/fab/sale/ProductDetailsActivity:access$9	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabButton;
        //   72: aload_0
        //   73: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   76: invokestatic 62	com/fab/sale/ProductDetailsActivity:access$1	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/content/Context;
        //   79: invokevirtual 68	android/content/Context:getResources	()Landroid/content/res/Resources;
        //   82: ldc 69
        //   84: invokevirtual 75	android/content/res/Resources:getColor	(I)I
        //   87: invokevirtual 79	com/fab/views/FabButton:setTextColor	(I)V
        //   90: aload_0
        //   91: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   94: invokestatic 82	com/fab/sale/ProductDetailsActivity:access$8	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabButton;
        //   97: iconst_1
        //   98: invokevirtual 55	com/fab/views/FabButton:setEnabled	(Z)V
        //   101: aload_1
        //   102: ifnull +297 -> 399
        //   105: aload_1
        //   106: instanceof 84
        //   109: ifeq +290 -> 399
        //   112: aload_1
        //   113: checkcast 84	org/json/JSONObject
        //   116: astore_2
        //   117: aload_0
        //   118: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   121: invokestatic 88	com/fab/sale/ProductDetailsActivity:access$10	(Lcom/fab/sale/ProductDetailsActivity;)Ljava/lang/String;
        //   124: aload_2
        //   125: invokevirtual 92	org/json/JSONObject:toString	()Ljava/lang/String;
        //   128: invokestatic 98	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
        //   131: aload_2
        //   132: ldc 100
        //   134: invokevirtual 104	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
        //   137: pop
        //   138: aload_0
        //   139: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   142: aload_2
        //   143: ldc 106
        //   145: invokevirtual 110	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
        //   148: invokevirtual 114	com/fab/sale/ProductDetailsActivity:populateCart	(Lorg/json/JSONObject;)V
        //   151: aload_2
        //   152: ldc 116
        //   154: invokevirtual 120	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   157: invokestatic 126	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   160: ifeq +127 -> 287
        //   163: aload_2
        //   164: ldc 128
        //   166: invokevirtual 110	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
        //   169: astore 12
        //   171: aload 12
        //   173: ldc 130
        //   175: invokevirtual 134	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
        //   178: astore 13
        //   180: aload_0
        //   181: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   184: aload 13
        //   186: invokestatic 138	com/fab/sale/ProductDetailsActivity:access$61	(Lcom/fab/sale/ProductDetailsActivity;Lorg/json/JSONArray;)V
        //   189: aload 12
        //   191: ldc 140
        //   193: invokevirtual 120	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   196: astore 14
        //   198: aload 14
        //   200: invokestatic 126	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   203: ifne +26 -> 229
        //   206: aload_0
        //   207: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   210: invokestatic 144	com/fab/sale/ProductDetailsActivity:access$36	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabTextView;
        //   213: aload 14
        //   215: invokevirtual 150	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
        //   218: aload_0
        //   219: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   222: invokestatic 154	com/fab/sale/ProductDetailsActivity:access$37	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/RelativeLayout;
        //   225: iconst_0
        //   226: invokevirtual 159	android/widget/RelativeLayout:setVisibility	(I)V
        //   229: aload_2
        //   230: ldc 161
        //   232: invokevirtual 165	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   235: istore 11
        //   237: iload 11
        //   239: ifne +140 -> 379
        //   242: aload_0
        //   243: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   246: invokestatic 168	com/fab/sale/ProductDetailsActivity:access$0	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/RelativeLayout;
        //   249: bipush 8
        //   251: invokevirtual 159	android/widget/RelativeLayout:setVisibility	(I)V
        //   254: aload_0
        //   255: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   258: invokestatic 32	com/fab/sale/ProductDetailsActivity:access$11	(Lcom/fab/sale/ProductDetailsActivity;)Z
        //   261: ifeq +13 -> 274
        //   264: aload_0
        //   265: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   268: invokestatic 172	com/fab/sale/ProductDetailsActivity:access$6	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/PopupWindow;
        //   271: invokevirtual 177	android/widget/PopupWindow:dismiss	()V
        //   274: aload_0
        //   275: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   278: invokestatic 181	com/fab/sale/ProductDetailsActivity:access$63	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/ErrorMessage;
        //   281: bipush 8
        //   283: invokevirtual 184	com/fab/views/ErrorMessage:setVisibility	(I)V
        //   286: return
        //   287: aload_0
        //   288: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   291: invokestatic 49	com/fab/sale/ProductDetailsActivity:access$21	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabButton;
        //   294: iconst_1
        //   295: invokevirtual 55	com/fab/views/FabButton:setEnabled	(Z)V
        //   298: aload_0
        //   299: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   302: invokestatic 188	com/fab/sale/ProductDetailsActivity:access$13	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/app/AlertDialog$Builder;
        //   305: ldc 189
        //   307: invokevirtual 195	android/app/AlertDialog$Builder:setIcon	(I)Landroid/app/AlertDialog$Builder;
        //   310: pop
        //   311: aload_0
        //   312: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   315: invokestatic 188	com/fab/sale/ProductDetailsActivity:access$13	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/app/AlertDialog$Builder;
        //   318: ldc 197
        //   320: invokevirtual 201	android/app/AlertDialog$Builder:setTitle	(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;
        //   323: pop
        //   324: aload_0
        //   325: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   328: invokestatic 188	com/fab/sale/ProductDetailsActivity:access$13	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/app/AlertDialog$Builder;
        //   331: aload_2
        //   332: ldc 116
        //   334: invokevirtual 120	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   337: invokevirtual 204	android/app/AlertDialog$Builder:setMessage	(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;
        //   340: pop
        //   341: aload_0
        //   342: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   345: invokestatic 188	com/fab/sale/ProductDetailsActivity:access$13	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/app/AlertDialog$Builder;
        //   348: ldc 206
        //   350: aconst_null
        //   351: invokevirtual 210	android/app/AlertDialog$Builder:setNeutralButton	(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;
        //   354: pop
        //   355: aload_0
        //   356: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   359: invokestatic 32	com/fab/sale/ProductDetailsActivity:access$11	(Lcom/fab/sale/ProductDetailsActivity;)Z
        //   362: ifeq -133 -> 229
        //   365: aload_0
        //   366: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   369: invokestatic 188	com/fab/sale/ProductDetailsActivity:access$13	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/app/AlertDialog$Builder;
        //   372: invokevirtual 214	android/app/AlertDialog$Builder:show	()Landroid/app/AlertDialog;
        //   375: pop
        //   376: goto -147 -> 229
        //   379: iload 11
        //   381: iconst_1
        //   382: if_icmpne -108 -> 274
        //   385: aload_0
        //   386: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   389: invokestatic 168	com/fab/sale/ProductDetailsActivity:access$0	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/RelativeLayout;
        //   392: iconst_0
        //   393: invokevirtual 159	android/widget/RelativeLayout:setVisibility	(I)V
        //   396: goto -122 -> 274
        //   399: aload_0
        //   400: getfield 20	com/fab/sale/ProductDetailsActivity$21:this$0	Lcom/fab/sale/ProductDetailsActivity;
        //   403: invokestatic 62	com/fab/sale/ProductDetailsActivity:access$1	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/content/Context;
        //   406: aload_0
        //   407: getfield 22	com/fab/sale/ProductDetailsActivity$21:val$cartTask	Lcom/fab/utils/ExecuteRequestAsyncTask;
        //   410: invokevirtual 219	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
        //   413: invokestatic 223	com/fab/utils/FabUtils:showAlert	(Landroid/content/Context;Ljava/lang/String;)V
        //   416: goto -130 -> 286
        //   419: astore 4
        //   421: goto -270 -> 151
        //   424: astore_3
        //   425: goto -287 -> 138
        //   428: astore 5
        //   430: goto -156 -> 274
        //
        // Exception table:
        //   from	to	target	type
        //   138	151	419	org/json/JSONException
        //   131	138	424	org/json/JSONException
        //   151	274	428	org/json/JSONException
        //   287	396	428	org/json/JSONException
      }
    });
    String[] arrayOfString = new String[1];
    arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/add-to-cart/", "?sale_id=" + this.saleId + "&product_id=" + this.productId + "&size=" + this.sizeAddToCart + "&quantity=" + this.qtyAddToCart + "&add_or_update_quantity=add");
    localExecuteRequestAsyncTask.execute(arrayOfString);
    this.tracker.trackPageView(FabUtils.gATracUrl("/product-add-to-cart/" + this.productId));
  }

  private void createQuantityList(int paramInt)
  {
    if (this.qtyArray != null)
      this.qtyArray.clear();
    this.theQuantity = new ArrayList();
    for (int i = 1; ; i++)
    {
      if (i > paramInt)
        return;
      if (this.qtyArray != null)
        this.qtyArray.add(Integer.valueOf(i));
      this.theQuantity.add(Integer.valueOf(i));
    }
  }

  private void favProductImage(boolean paramBoolean)
  {
    long l1 = 0L;
    Object localObject1 = null;
    long l2 = 0L;
    Object localObject2 = "";
    Object localObject3 = "";
    this.favIcon.setSelected(true);
    if ((this.isActivityAlive) && (this.dialog != null))
    {
      this.dialog.setCancelable(true);
      this.dialog.setMessage("Please wait...");
      this.dialog.show();
    }
    ExecuteRequestAsyncTask localExecuteRequestAsyncTask = new ExecuteRequestAsyncTask(this.context, "POST");
    localExecuteRequestAsyncTask.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      public void onTaskComplete(Object paramAnonymousObject)
      {
        if ((ProductDetailsActivity.this.isActivityAlive) && (ProductDetailsActivity.this.dialog != null) && (ProductDetailsActivity.this.dialog.isShowing()))
          ProductDetailsActivity.this.dialog.cancel();
        JSONObject localJSONObject = (JSONObject)paramAnonymousObject;
        Object localObject1;
        if (localJSONObject != null)
          localObject1 = null;
        while (true)
        {
          Object localObject2;
          try
          {
            String str3 = localJSONObject.getString("err_msg");
            localObject1 = str3;
            if (!TextUtils.isEmpty(localObject1))
            {
              ProductDetailsActivity.this.favIcon.setSelected(false);
              ProductDetailsActivity.this.alertbox.setIcon(2130837504);
              ProductDetailsActivity.this.alertbox.setMessage(localObject1);
              ProductDetailsActivity.this.alertbox.setTitle("Favorite");
              ProductDetailsActivity.this.alertbox.setNeutralButton("OK", null);
              if (ProductDetailsActivity.this.isActivityAlive)
                ProductDetailsActivity.this.alertbox.show();
              if (ProductDetailsActivity.this.parameters != null)
                ProductDetailsActivity.this.parameters.clear();
              return;
            }
          }
          catch (JSONException localJSONException1)
          {
            localJSONException1.printStackTrace();
            continue;
            localObject2 = null;
          }
          try
          {
            String str2 = localJSONObject.getString("success_msg");
            localObject2 = str2;
            try
            {
              label202: String str1 = localJSONObject.getString("warning_msg");
              localObject2 = str1;
              label214: ProductDetailsActivity.this.isFav = true;
              try
              {
                ProductDetailsActivity.this.favCount = localJSONObject.getString("image_fab_count");
                if ("0".equalsIgnoreCase(ProductDetailsActivity.this.favCount))
                  ProductDetailsActivity.this.favCountIcon.setVisibility(8);
                while (true)
                {
                  label262: if (!TextUtils.isEmpty(localObject2))
                  {
                    ProductDetailsActivity.this.alertbox.setIcon(2130837507);
                    ProductDetailsActivity.this.alertbox.setMessage(localObject2);
                    ProductDetailsActivity.this.alertbox.setTitle("Favorite");
                    ProductDetailsActivity.this.alertbox.setNeutralButton("OK", null);
                    if (ProductDetailsActivity.this.isActivityAlive)
                      ProductDetailsActivity.this.alertbox.show();
                  }
                  ProductDetailsActivity.this.favIcon.setImageDrawable(ProductDetailsActivity.this.getResources().getDrawable(2130837626));
                  ProductDetailsActivity.this.favIcon.setEnabled(false);
                  ProductDetailsActivity.this.favIcon.setOnClickListener(null);
                  break;
                  ProductDetailsActivity.this.favCountIcon.setVisibility(0);
                  if (ProductDetailsActivity.this.favCount.length() > 2)
                    ProductDetailsActivity.this.favCountIcon.setBackgroundDrawable(ProductDetailsActivity.this.getResources().getDrawable(2130837524));
                  ProductDetailsActivity.this.favCountIcon.setText(ProductDetailsActivity.this.favCount);
                }
              }
              catch (JSONException localJSONException4)
              {
                break label262;
              }
            }
            catch (JSONException localJSONException3)
            {
              break label214;
            }
          }
          catch (JSONException localJSONException2)
          {
            break label202;
          }
        }
      }
    });
    JSONObject localJSONObject;
    if (paramBoolean)
    {
      this.fbClient = new FabFBClient(this.context);
      localJSONObject = this.fbClient.getUserTimelineInfo();
      if (localJSONObject == null);
    }
    try
    {
      long l4 = localJSONObject.getLong("userID");
      l1 = l4;
      try
      {
        label142: String str3 = localJSONObject.getString("accessToken");
        localObject1 = str3;
        try
        {
          label156: long l3 = localJSONObject.getLong("expiresIn");
          l2 = l3;
          try
          {
            label170: String str2 = localJSONObject.getString("fb_name");
            localObject2 = str2;
            try
            {
              label184: String str1 = localJSONObject.getString("fb_email");
              localObject3 = str1;
              label198: this.parameters = new HashMap();
              this.parameters.put("d", FabSharedPrefs.getDeviceDensity());
              this.parameters.put("app_version", FabApplication.APP_VERSION);
              this.parameters.put("device_model", Build.MODEL);
              this.parameters.put("device_os", Build.VERSION.RELEASE);
              this.parameters.put("product_id", this.productId);
              this.parameters.put("sale_id", this.saleId);
              this.parameters.put("image_id", this.favImageId);
              if ((paramBoolean) && (l1 > 0L) && (!TextUtils.isEmpty(localObject1)) && (l2 != 0L))
              {
                this.parameters.put("userID", l1);
                this.parameters.put("accessToken", localObject1);
                this.parameters.put("expiresIn", l2 / 1000L);
                this.parameters.put("fb_name", localObject2);
                this.parameters.put("fb_email", localObject3);
              }
              localExecuteRequestAsyncTask.setParameters(this.parameters);
              String[] arrayOfString = new String[1];
              arrayOfString[0] = FabUtils.requestPostUrl(false, "/mobile/create-or-fab/");
              localExecuteRequestAsyncTask.execute(arrayOfString);
              return;
            }
            catch (JSONException localJSONException5)
            {
              break label198;
            }
          }
          catch (JSONException localJSONException4)
          {
            break label184;
          }
        }
        catch (JSONException localJSONException3)
        {
          break label170;
        }
      }
      catch (JSONException localJSONException2)
      {
        break label156;
      }
    }
    catch (JSONException localJSONException1)
    {
      break label142;
    }
  }

  private void initializeUI()
  {
    this.error = ((ErrorMessage)findViewById(2131361811));
    this.inflator = ((LayoutInflater)this.context.getSystemService("layout_inflater"));
    this.parentView = ((ViewGroup)findViewById(2131361965));
    this.pBar = ((ProgressBar)findViewById(2131361995));
    this.imagePBar = ((ProgressBar)findViewById(2131361994));
    this.imagePBar.setVisibility(4);
    this.ribbonText = ((FabTextView)findViewById(2131361972));
    this.ribbonText.setFont("HelveticaNeu_bold.ttf");
    this.ribbonView = ((RelativeLayout)findViewById(2131361970));
    this.fabPrice = ((FabTextView)findViewById(2131361973));
    this.fabPrice.setFont("HelveticaNeu_bold.ttf");
    this.fabText = ((FabTextView)findViewById(2131361975));
    this.fabText.setFont("HelveticaNeu_bold.ttf");
    this.textFabStamp = ((FabTextView)findViewById(2131361988));
    this.textFabStamp.setFont("georgiai.ttf");
    this.textFabStamp.setText(Html.fromHtml(getResources().getString(2131165194) + "<font color=\"#333333\">" + "    " + getResources().getString(2131165195) + "</font>"));
    this.originalPrice = ((FabTextView)findViewById(2131361976));
    this.originalPrice.setFont("HelveticaNeu_normal.ttf");
    this.originalPrice.setPaintFlags(0x10 | this.originalPrice.getPaintFlags());
    this.fabPriceBottom = ((FabTextView)findViewById(2131361989));
    this.fabPriceBottom.setFont("HelveticaNeu_bold.ttf");
    this.originalPriceBottom = ((FabTextView)findViewById(2131361990));
    this.originalPriceBottom.setFont("HelveticaNeu_normal.ttf");
    this.originalPriceBottom.setPaintFlags(0x10 | this.originalPriceBottom.getPaintFlags());
    this.productStory = ((WebView)findViewById(2131361979));
    this.discountPct = ((FabRotatedTextView)findViewById(2131361992));
    this.discountPct.setFont("HelveticaNeu_bold.ttf");
    this.redTriangle = ((ImageView)findViewById(2131361968));
    this.moreDetailsLable = ((FabTextView)findViewById(2131361981));
    this.moreDetailsLable.setFont("HelveticaNeu_normal.ttf");
    this.moreDetails = ((WebView)findViewById(2131361982));
    this.productImage = ((ImageView)findViewById(2131361850));
    this.productImage.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (FabApplication.API_LEVEL > 4)
        {
          Intent localIntent = new Intent(ProductDetailsActivity.this.context, ProductGallery.class);
          localIntent.putExtra("PRODUCTS_IMAGES", ProductDetailsActivity.this.theProductsGallery);
          ProductDetailsActivity.this.startActivity(localIntent);
        }
      }
    });
    this.showDetailsLayout = ((LinearLayout)findViewById(2131361984));
    this.runtimeDetailsLayout = ((LinearLayout)findViewById(2131361985));
    this.productScrollView = ((ScrollView)findViewById(2131361967));
    this.popUp = this.inflator.inflate(2130903041, null);
    this.popUpTimer = this.inflator.inflate(2130903040, null);
    populatePopUpWindow();
    this.resultPopUp = new PopupWindow(this.popUp, -1, -2);
    this.timerPopUpWindow = new PopupWindow(this.popUpTimer, -1, -2);
    this.buyNow = ((FabButton)findViewById(2131361977));
    this.buyNow.setFont("HelveticaNeu_bold.ttf");
    if (FabSharedPrefs.isGuestUser())
    {
      this.buyNow.setText("Sign in to Buy");
      this.buyNow.setBackgroundResource(2130837739);
      this.buyNow.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (FabSharedPrefs.isGuestUser())
          {
            Intent localIntent = new Intent(ProductDetailsActivity.this.context, FabSignin.class);
            localIntent.setFlags(67108864);
            localIntent.putExtra("IS_FROM_GUEST", true);
            localIntent.putExtra("REFRESH_PREVIOUS_ACTIVITY", true);
            ProductDetailsActivity.this.context.startActivity(localIntent);
          }
          while (true)
          {
            return;
            if (ProductDetailsActivity.this.timerPopUpWindow.isShowing())
              ProductDetailsActivity.this.timer.setVisibility(0);
            if (!ProductDetailsActivity.this.resultPopUp.isShowing())
              ProductDetailsActivity.this.resultPopUp.showAtLocation(ProductDetailsActivity.this.parentView, 80, 0, 0);
            ProductDetailsActivity.this.popUpRel.setVisibility(0);
            ProductDetailsActivity.this.popUpRel.clearAnimation();
            ProductDetailsActivity.this.popUpRel.startAnimation(AnimationUtils.loadAnimation(ProductDetailsActivity.this.context, 2130968576));
            paramAnonymousView.setEnabled(false);
            ProductDetailsActivity.this.buyNowBottom.setEnabled(false);
          }
        }
      });
      this.buyNowBottom = ((FabButton)findViewById(2131361991));
      this.buyNowBottom.setFont("HelveticaNeu_bold.ttf");
      if (!FabSharedPrefs.isGuestUser())
        break label788;
      this.buyNowBottom.setText("Sign in to Buy");
      this.buyNowBottom.setBackgroundResource(2130837739);
    }
    while (true)
    {
      this.buyNowBottom.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (FabSharedPrefs.isGuestUser())
          {
            Intent localIntent = new Intent(ProductDetailsActivity.this.context, FabSignin.class);
            localIntent.setFlags(67108864);
            localIntent.putExtra("IS_FROM_GUEST", true);
            localIntent.putExtra("REFRESH_PREVIOUS_ACTIVITY", true);
            ProductDetailsActivity.this.context.startActivity(localIntent);
          }
          while (true)
          {
            return;
            if (ProductDetailsActivity.this.timerPopUpWindow.isShowing())
              ProductDetailsActivity.this.timer.setVisibility(0);
            if (!ProductDetailsActivity.this.resultPopUp.isShowing())
              ProductDetailsActivity.this.resultPopUp.showAtLocation(ProductDetailsActivity.this.parentView, 80, 0, 0);
            ProductDetailsActivity.this.popUpRel.setVisibility(0);
            ProductDetailsActivity.this.popUpRel.clearAnimation();
            ProductDetailsActivity.this.popUpRel.startAnimation(AnimationUtils.loadAnimation(ProductDetailsActivity.this.context, 2130968576));
            paramAnonymousView.setEnabled(false);
            ProductDetailsActivity.this.buyNow.setEnabled(false);
          }
        }
      });
      this.favCountIcon = ((Button)findViewById(2131361993));
      this.favIcon = ((ImageView)findViewById(2131361969));
      this.mHandler = new Handler()
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          Bundle localBundle = paramAnonymousMessage.getData();
          if (FabUtils.isLogEnabled())
            FabUtils.log(ProductDetailsActivity.this.TAG, localBundle.toString());
          int i = localBundle.getInt("Type");
          String str = localBundle.getString("Value");
          switch (i)
          {
          default:
          case 2:
          case 1:
          case 3:
          }
          while (true)
          {
            return;
            if (ProductDetailsActivity.this.isActivityAlive)
            {
              if (localBundle.getBoolean("Expire"))
              {
                ProductDetailsActivity.this.timerPopUpWindow.dismiss();
                ProductDetailsActivity.this.tvTimerWindow.setVisibility(4);
              }
              AlertDialog.Builder localBuilder = ProductDetailsActivity.this.alertbox;
              if (localBundle.getInt("Error") == 1);
              for (int j = 2130837504; ; j = 2130837507)
              {
                localBuilder.setIcon(j);
                ProductDetailsActivity.this.alertbox.setTitle(localBundle.getString("Title"));
                ProductDetailsActivity.this.alertbox.setMessage(localBundle.getString("Message"));
                ProductDetailsActivity.this.alertbox.setNeutralButton("OK", null);
                if (!ProductDetailsActivity.this.isActivityAlive)
                  break;
                ProductDetailsActivity.this.alertbox.show();
                break;
              }
              ProductDetailsActivity.this.timer.setText(str);
              if ((!ProductDetailsActivity.this.timerPopUpWindow.isShowing()) && (ProductDetailsActivity.this.isActivityAlive))
              {
                if (ProductDetailsActivity.this.parentView == null)
                  ProductDetailsActivity.this.parentView = ((ViewGroup)((Activity)ProductDetailsActivity.this.context).findViewById(2131361965));
                if ((ProductDetailsActivity.this.parentView != null) && (ProductDetailsActivity.this.isActivityAlive))
                  ProductDetailsActivity.this.parentView.post(new Runnable()
                  {
                    public void run()
                    {
                      ProductDetailsActivity.this.timerPopUpWindow.showAtLocation(ProductDetailsActivity.this.parentView, 80, 0, 0);
                      ProductDetailsActivity.this.tvTimerWindow.setVisibility(0);
                    }
                  });
              }
              ProductDetailsActivity.this.tvTimerWindow.setText(str);
              continue;
              ProductDetailsActivity.this.favProductImage(localBundle.getBoolean("TimelineEnabledNow"));
            }
          }
        }
      };
      return;
      this.buyNow.setText("Buy Now!");
      this.buyNow.setBackgroundResource(2130837525);
      break;
      label788: this.buyNow.setText("Buy Now!");
      this.buyNowBottom.setBackgroundResource(2130837525);
    }
  }

  private void loadProductDetails()
  {
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
          //   1: ifnull +2549 -> 2550
          //   4: aload_1
          //   5: instanceof 38
          //   8: ifeq +2542 -> 2550
          //   11: aload_1
          //   12: checkcast 38	org/json/JSONObject
          //   15: astore_2
          //   16: aload_0
          //   17: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   20: invokestatic 42	com/fab/sale/ProductDetailsActivity:access$10	(Lcom/fab/sale/ProductDetailsActivity;)Ljava/lang/String;
          //   23: aload_2
          //   24: invokevirtual 46	org/json/JSONObject:toString	()Ljava/lang/String;
          //   27: invokestatic 52	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
          //   30: aload_2
          //   31: ldc 54
          //   33: invokevirtual 58	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   36: pop
          //   37: aload_2
          //   38: ldc 60
          //   40: invokevirtual 64	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   43: astore 107
          //   45: aload_0
          //   46: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   49: aload 107
          //   51: ldc 66
          //   53: invokevirtual 70	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   56: invokevirtual 74	com/fab/sale/ProductDetailsActivity:updateCartCounter	(I)V
          //   59: aload 107
          //   61: ldc 76
          //   63: invokevirtual 70	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   66: i2l
          //   67: new 78	java/util/GregorianCalendar
          //   70: dup
          //   71: invokespecial 79	java/util/GregorianCalendar:<init>	()V
          //   74: invokestatic 83	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
          //   77: ldc2_w 84
          //   80: lcmp
          //   81: iflt +39 -> 120
          //   84: aload 107
          //   86: ldc 76
          //   88: invokevirtual 70	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   91: i2l
          //   92: invokestatic 91	com/fab/utils/FabHelper:setCartTime	(J)V
          //   95: aload_0
          //   96: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   99: invokevirtual 95	com/fab/sale/ProductDetailsActivity:getApplicationContext	()Landroid/content/Context;
          //   102: aload 107
          //   104: ldc 76
          //   106: invokevirtual 70	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   109: i2l
          //   110: aload_0
          //   111: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   114: invokestatic 99	com/fab/sale/ProductDetailsActivity:access$24	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/os/Handler;
          //   117: invokestatic 103	com/fab/utils/FabHelper:stopAndRestartCartTimer	(Landroid/content/Context;JLandroid/os/Handler;)V
          //   120: ldc 105
          //   122: astore 5
          //   124: aload_2
          //   125: ldc 107
          //   127: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   130: astore 106
          //   132: aload 106
          //   134: astore 5
          //   136: aload 5
          //   138: invokestatic 117	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   141: ifne +49 -> 190
          //   144: aload_0
          //   145: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   148: invokestatic 121	com/fab/sale/ProductDetailsActivity:access$1	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/content/Context;
          //   151: aload 5
          //   153: invokestatic 125	com/fab/utils/FabUtils:showAlert	(Landroid/content/Context;Ljava/lang/String;)V
          //   156: aload_0
          //   157: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   160: invokevirtual 129	com/fab/sale/ProductDetailsActivity:getApplication	()Landroid/app/Application;
          //   163: checkcast 131	com/fab/FabApplication
          //   166: iconst_1
          //   167: invokevirtual 135	com/fab/FabApplication:setRefreshActivityOnRestart	(Z)V
          //   170: aload_0
          //   171: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   174: invokevirtual 138	com/fab/sale/ProductDetailsActivity:finish	()V
          //   177: aload_0
          //   178: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   181: invokestatic 142	com/fab/sale/ProductDetailsActivity:access$63	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/ErrorMessage;
          //   184: bipush 8
          //   186: invokevirtual 147	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   189: return
          //   190: new 38	org/json/JSONObject
          //   193: dup
          //   194: invokespecial 148	org/json/JSONObject:<init>	()V
          //   197: pop
          //   198: aload_0
          //   199: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   202: aload_2
          //   203: ldc 150
          //   205: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   208: invokestatic 154	com/fab/sale/ProductDetailsActivity:access$25	(Lcom/fab/sale/ProductDetailsActivity;Ljava/lang/String;)V
          //   211: aload_2
          //   212: ldc 156
          //   214: invokevirtual 64	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   217: astore 105
          //   219: aload 105
          //   221: astore 10
          //   223: aload 10
          //   225: ifnull +133 -> 358
          //   228: aload_0
          //   229: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   232: new 158	com/fab/facebook/FabFBShareBO
          //   235: dup
          //   236: invokespecial 159	com/fab/facebook/FabFBShareBO:<init>	()V
          //   239: putfield 163	com/fab/sale/ProductDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   242: aload_0
          //   243: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   246: getfield 163	com/fab/sale/ProductDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   249: aload 10
          //   251: ldc 165
          //   253: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   256: invokevirtual 169	com/fab/facebook/FabFBShareBO:setLink	(Ljava/lang/String;)V
          //   259: aload_0
          //   260: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   263: getfield 163	com/fab/sale/ProductDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   266: aload 10
          //   268: ldc 171
          //   270: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   273: invokevirtual 174	com/fab/facebook/FabFBShareBO:setCaption	(Ljava/lang/String;)V
          //   276: aload_0
          //   277: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   280: getfield 163	com/fab/sale/ProductDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   283: aload 10
          //   285: ldc 176
          //   287: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   290: invokevirtual 179	com/fab/facebook/FabFBShareBO:setDescription	(Ljava/lang/String;)V
          //   293: aload_0
          //   294: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   297: getfield 163	com/fab/sale/ProductDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   300: aload 10
          //   302: ldc 181
          //   304: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   307: invokevirtual 184	com/fab/facebook/FabFBShareBO:setPicture	(Ljava/lang/String;)V
          //   310: aload_0
          //   311: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   314: getfield 163	com/fab/sale/ProductDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   317: aload 10
          //   319: ldc 186
          //   321: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   324: invokevirtual 189	com/fab/facebook/FabFBShareBO:setName	(Ljava/lang/String;)V
          //   327: aload_0
          //   328: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   331: getfield 163	com/fab/sale/ProductDetailsActivity:fbShareData	Lcom/fab/facebook/FabFBShareBO;
          //   334: aload 10
          //   336: ldc 186
          //   338: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   341: invokevirtual 189	com/fab/facebook/FabFBShareBO:setName	(Ljava/lang/String;)V
          //   344: aload_0
          //   345: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   348: aload 10
          //   350: ldc 191
          //   352: invokevirtual 58	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   355: putfield 195	com/fab/sale/ProductDetailsActivity:hasFBShareToken	Z
          //   358: new 38	org/json/JSONObject
          //   361: dup
          //   362: invokespecial 148	org/json/JSONObject:<init>	()V
          //   365: pop
          //   366: aload_2
          //   367: ldc 197
          //   369: invokevirtual 64	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   372: astore 97
          //   374: aload 97
          //   376: astore 13
          //   378: aload 13
          //   380: ifnull +31 -> 411
          //   383: aload_0
          //   384: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   387: aload 13
          //   389: ldc 199
          //   391: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   394: putfield 203	com/fab/sale/ProductDetailsActivity:emailBody	Ljava/lang/String;
          //   397: aload_0
          //   398: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   401: aload 13
          //   403: ldc 205
          //   405: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   408: putfield 208	com/fab/sale/ProductDetailsActivity:emailSubject	Ljava/lang/String;
          //   411: aload_0
          //   412: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   415: aload_2
          //   416: ldc 210
          //   418: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   421: putfield 213	com/fab/sale/ProductDetailsActivity:smsShare	Ljava/lang/String;
          //   424: new 38	org/json/JSONObject
          //   427: dup
          //   428: invokespecial 148	org/json/JSONObject:<init>	()V
          //   431: astore 15
          //   433: aload_2
          //   434: ldc 215
          //   436: invokevirtual 64	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   439: astore 94
          //   441: aload 94
          //   443: astore 15
          //   445: new 38	org/json/JSONObject
          //   448: dup
          //   449: invokespecial 148	org/json/JSONObject:<init>	()V
          //   452: astore 17
          //   454: aload 15
          //   456: ldc 217
          //   458: invokevirtual 64	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   461: astore 93
          //   463: aload 93
          //   465: astore 17
          //   467: new 38	org/json/JSONObject
          //   470: dup
          //   471: invokespecial 148	org/json/JSONObject:<init>	()V
          //   474: astore 19
          //   476: aload 15
          //   478: ldc 219
          //   480: invokevirtual 64	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   483: astore 92
          //   485: aload 92
          //   487: astore 19
          //   489: new 221	org/json/JSONArray
          //   492: dup
          //   493: invokespecial 222	org/json/JSONArray:<init>	()V
          //   496: astore 21
          //   498: aload 15
          //   500: ldc 224
          //   502: invokevirtual 228	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   505: astore 91
          //   507: aload 91
          //   509: astore 21
          //   511: ldc 105
          //   513: astore 23
          //   515: aload 15
          //   517: ldc 230
          //   519: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   522: astore 90
          //   524: aload 90
          //   526: astore 23
          //   528: new 38	org/json/JSONObject
          //   531: dup
          //   532: invokespecial 148	org/json/JSONObject:<init>	()V
          //   535: astore 25
          //   537: aload 15
          //   539: ldc 232
          //   541: invokevirtual 64	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   544: astore 89
          //   546: aload 89
          //   548: astore 25
          //   550: new 221	org/json/JSONArray
          //   553: dup
          //   554: invokespecial 222	org/json/JSONArray:<init>	()V
          //   557: astore 27
          //   559: aload 15
          //   561: ldc 234
          //   563: invokevirtual 228	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   566: astore 88
          //   568: aload 88
          //   570: astore 27
          //   572: new 221	org/json/JSONArray
          //   575: dup
          //   576: invokespecial 222	org/json/JSONArray:<init>	()V
          //   579: astore 29
          //   581: aload 15
          //   583: ldc 236
          //   585: invokevirtual 228	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   588: astore 87
          //   590: aload 87
          //   592: astore 29
          //   594: new 38	org/json/JSONObject
          //   597: dup
          //   598: invokespecial 148	org/json/JSONObject:<init>	()V
          //   601: astore 31
          //   603: aload_0
          //   604: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   607: new 238	java/util/ArrayList
          //   610: dup
          //   611: invokespecial 239	java/util/ArrayList:<init>	()V
          //   614: putfield 243	com/fab/sale/ProductDetailsActivity:theProductsGallery	Ljava/util/ArrayList;
          //   617: iconst_0
          //   618: istore 32
          //   620: aload 29
          //   622: invokevirtual 247	org/json/JSONArray:length	()I
          //   625: istore 33
          //   627: iload 32
          //   629: iload 33
          //   631: if_icmplt +622 -> 1253
          //   634: new 38	org/json/JSONObject
          //   637: dup
          //   638: invokespecial 148	org/json/JSONObject:<init>	()V
          //   641: pop
          //   642: aload 15
          //   644: ldc 249
          //   646: invokevirtual 64	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   649: astore 58
          //   651: aload_0
          //   652: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   655: aload 58
          //   657: ldc 251
          //   659: invokevirtual 58	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
          //   662: putfield 254	com/fab/sale/ProductDetailsActivity:isFav	Z
          //   665: aload_0
          //   666: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   669: aload 58
          //   671: ldc_w 256
          //   674: invokevirtual 70	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   677: putfield 260	com/fab/sale/ProductDetailsActivity:favImageId	I
          //   680: aload_0
          //   681: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   684: aload 58
          //   686: ldc_w 262
          //   689: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   692: invokestatic 265	com/fab/sale/ProductDetailsActivity:access$30	(Lcom/fab/sale/ProductDetailsActivity;Ljava/lang/String;)V
          //   695: ldc_w 267
          //   698: aload_0
          //   699: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   702: invokestatic 270	com/fab/sale/ProductDetailsActivity:access$31	(Lcom/fab/sale/ProductDetailsActivity;)Ljava/lang/String;
          //   705: invokevirtual 275	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
          //   708: ifeq +1034 -> 1742
          //   711: aload_0
          //   712: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   715: invokestatic 279	com/fab/sale/ProductDetailsActivity:access$32	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/Button;
          //   718: bipush 8
          //   720: invokevirtual 282	android/widget/Button:setVisibility	(I)V
          //   723: aload_0
          //   724: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   727: astore 65
          //   729: aload_2
          //   730: ldc_w 284
          //   733: invokevirtual 70	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   736: iconst_1
          //   737: if_icmpne +2076 -> 2813
          //   740: iconst_1
          //   741: istore 66
          //   743: aload 65
          //   745: iload 66
          //   747: invokestatic 288	com/fab/sale/ProductDetailsActivity:access$33	(Lcom/fab/sale/ProductDetailsActivity;Z)V
          //   750: aload_0
          //   751: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   754: getfield 254	com/fab/sale/ProductDetailsActivity:isFav	Z
          //   757: ifeq +1079 -> 1836
          //   760: aload_0
          //   761: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   764: invokestatic 292	com/fab/sale/ProductDetailsActivity:access$34	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/ImageView;
          //   767: aload_0
          //   768: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   771: invokevirtual 296	com/fab/sale/ProductDetailsActivity:getResources	()Landroid/content/res/Resources;
          //   774: ldc_w 297
          //   777: invokevirtual 303	android/content/res/Resources:getDrawable	(I)Landroid/graphics/drawable/Drawable;
          //   780: invokevirtual 309	android/widget/ImageView:setImageDrawable	(Landroid/graphics/drawable/Drawable;)V
          //   783: aload_0
          //   784: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   787: invokestatic 292	com/fab/sale/ProductDetailsActivity:access$34	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/ImageView;
          //   790: iconst_0
          //   791: invokevirtual 312	android/widget/ImageView:setEnabled	(Z)V
          //   794: aload_0
          //   795: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   798: invokestatic 292	com/fab/sale/ProductDetailsActivity:access$34	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/ImageView;
          //   801: aconst_null
          //   802: invokevirtual 316	android/widget/ImageView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
          //   805: aload 23
          //   807: invokestatic 117	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   810: ifne +26 -> 836
          //   813: aload_0
          //   814: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   817: invokestatic 320	com/fab/sale/ProductDetailsActivity:access$36	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabTextView;
          //   820: aload 23
          //   822: invokevirtual 326	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
          //   825: aload_0
          //   826: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   829: invokestatic 330	com/fab/sale/ProductDetailsActivity:access$37	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/RelativeLayout;
          //   832: iconst_0
          //   833: invokevirtual 333	android/widget/RelativeLayout:setVisibility	(I)V
          //   836: aload_0
          //   837: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   840: invokestatic 336	com/fab/sale/ProductDetailsActivity:access$38	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabTextView;
          //   843: aload 17
          //   845: ldc_w 338
          //   848: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   851: invokevirtual 326	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
          //   854: aload_0
          //   855: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   858: invokestatic 341	com/fab/sale/ProductDetailsActivity:access$39	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabTextView;
          //   861: aload 17
          //   863: ldc_w 338
          //   866: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   869: invokevirtual 326	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
          //   872: aload 17
          //   874: ldc_w 338
          //   877: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   880: aload 17
          //   882: ldc_w 343
          //   885: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   888: invokevirtual 275	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
          //   891: ifne +99 -> 990
          //   894: aload_0
          //   895: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   898: invokestatic 346	com/fab/sale/ProductDetailsActivity:access$40	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabTextView;
          //   901: new 348	java/lang/StringBuilder
          //   904: dup
          //   905: aload 17
          //   907: ldc_w 343
          //   910: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   913: invokestatic 352	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
          //   916: invokespecial 354	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   919: ldc_w 356
          //   922: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   925: invokevirtual 361	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   928: invokevirtual 326	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
          //   931: aload_0
          //   932: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   935: invokestatic 346	com/fab/sale/ProductDetailsActivity:access$40	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabTextView;
          //   938: iconst_0
          //   939: invokevirtual 362	com/fab/views/FabTextView:setVisibility	(I)V
          //   942: aload_0
          //   943: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   946: invokestatic 365	com/fab/sale/ProductDetailsActivity:access$41	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabTextView;
          //   949: new 348	java/lang/StringBuilder
          //   952: dup
          //   953: aload 17
          //   955: ldc_w 343
          //   958: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   961: invokestatic 352	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
          //   964: invokespecial 354	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   967: ldc_w 356
          //   970: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   973: invokevirtual 361	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   976: invokevirtual 326	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
          //   979: aload_0
          //   980: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   983: invokestatic 365	com/fab/sale/ProductDetailsActivity:access$41	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabTextView;
          //   986: iconst_0
          //   987: invokevirtual 362	com/fab/views/FabTextView:setVisibility	(I)V
          //   990: aload_0
          //   991: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   994: invokestatic 369	com/fab/sale/ProductDetailsActivity:access$42	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/webkit/WebView;
          //   997: aload 19
          //   999: ldc_w 371
          //   1002: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1005: ldc_w 373
          //   1008: ldc_w 375
          //   1011: invokestatic 379	com/fab/utils/FabUtils:setWebViewStyle	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
          //   1014: ldc_w 381
          //   1017: ldc_w 383
          //   1020: invokevirtual 389	android/webkit/WebView:loadData	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
          //   1023: aload 17
          //   1025: ldc_w 391
          //   1028: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1031: ldc_w 267
          //   1034: invokevirtual 275	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
          //   1037: ifne +828 -> 1865
          //   1040: aload_0
          //   1041: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1044: invokestatic 395	com/fab/sale/ProductDetailsActivity:access$43	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabRotatedTextView;
          //   1047: new 348	java/lang/StringBuilder
          //   1050: dup
          //   1051: ldc_w 397
          //   1054: invokespecial 354	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   1057: aload 17
          //   1059: ldc_w 391
          //   1062: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1065: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1068: ldc_w 399
          //   1071: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1074: invokevirtual 361	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   1077: invokevirtual 402	com/fab/views/FabRotatedTextView:setText	(Ljava/lang/CharSequence;)V
          //   1080: aload 25
          //   1082: ldc_w 404
          //   1085: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1088: invokestatic 117	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   1091: ifne +47 -> 1138
          //   1094: aload_0
          //   1095: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1098: invokestatic 407	com/fab/sale/ProductDetailsActivity:access$45	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/webkit/WebView;
          //   1101: iconst_0
          //   1102: invokevirtual 408	android/webkit/WebView:setVisibility	(I)V
          //   1105: aload_0
          //   1106: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1109: invokestatic 407	com/fab/sale/ProductDetailsActivity:access$45	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/webkit/WebView;
          //   1112: aload 25
          //   1114: ldc_w 404
          //   1117: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1120: ldc_w 373
          //   1123: ldc_w 375
          //   1126: invokestatic 379	com/fab/utils/FabUtils:setWebViewStyle	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
          //   1129: ldc_w 381
          //   1132: ldc_w 383
          //   1135: invokevirtual 389	android/webkit/WebView:loadData	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
          //   1138: aload 25
          //   1140: ldc_w 410
          //   1143: invokevirtual 228	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   1146: astore 55
          //   1148: aload_0
          //   1149: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1152: invokestatic 414	com/fab/sale/ProductDetailsActivity:access$46	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/LinearLayout;
          //   1155: invokevirtual 419	android/widget/LinearLayout:removeAllViews	()V
          //   1158: iconst_0
          //   1159: istore 56
          //   1161: aload 55
          //   1163: invokevirtual 247	org/json/JSONArray:length	()I
          //   1166: istore 57
          //   1168: iload 56
          //   1170: iload 57
          //   1172: if_icmplt +723 -> 1895
          //   1175: aload_0
          //   1176: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1179: invokestatic 422	com/fab/sale/ProductDetailsActivity:access$52	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/LinearLayout;
          //   1182: invokevirtual 419	android/widget/LinearLayout:removeAllViews	()V
          //   1185: iconst_0
          //   1186: istore 43
          //   1188: aload 27
          //   1190: invokevirtual 247	org/json/JSONArray:length	()I
          //   1193: istore 44
          //   1195: iload 43
          //   1197: iload 44
          //   1199: if_icmplt +841 -> 2040
          //   1202: aload_0
          //   1203: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1206: aload 21
          //   1208: invokestatic 426	com/fab/sale/ProductDetailsActivity:access$61	(Lcom/fab/sale/ProductDetailsActivity;Lorg/json/JSONArray;)V
          //   1211: aload_0
          //   1212: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1215: invokestatic 430	com/fab/sale/ProductDetailsActivity:access$62	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/ScrollView;
          //   1218: iconst_0
          //   1219: invokevirtual 433	android/widget/ScrollView:setVisibility	(I)V
          //   1222: aload_0
          //   1223: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1226: getfield 437	com/fab/sale/ProductDetailsActivity:pBar	Landroid/widget/ProgressBar;
          //   1229: bipush 8
          //   1231: invokevirtual 440	android/widget/ProgressBar:setVisibility	(I)V
          //   1234: goto -1057 -> 177
          //   1237: astore 9
          //   1239: aconst_null
          //   1240: astore 10
          //   1242: goto -1019 -> 223
          //   1245: astore 12
          //   1247: aconst_null
          //   1248: astore 13
          //   1250: goto -872 -> 378
          //   1253: aload 29
          //   1255: iload 32
          //   1257: invokevirtual 443	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   1260: astore 86
          //   1262: aload 86
          //   1264: astore 31
          //   1266: new 445	com/fab/sale/ProductZoomBo
          //   1269: dup
          //   1270: invokespecial 446	com/fab/sale/ProductZoomBo:<init>	()V
          //   1273: astore 68
          //   1275: aload 68
          //   1277: sipush 480
          //   1280: invokevirtual 449	com/fab/sale/ProductZoomBo:setOriginalSize	(I)V
          //   1283: iload 32
          //   1285: ifne +189 -> 1474
          //   1288: aload 68
          //   1290: iconst_1
          //   1291: invokevirtual 452	com/fab/sale/ProductZoomBo:setPrimary	(I)V
          //   1294: aload_0
          //   1295: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1298: invokestatic 456	com/fab/sale/ProductDetailsActivity:access$26	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/ProgressBar;
          //   1301: iconst_0
          //   1302: invokevirtual 440	android/widget/ProgressBar:setVisibility	(I)V
          //   1305: new 348	java/lang/StringBuilder
          //   1308: dup
          //   1309: ldc_w 458
          //   1312: invokespecial 354	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   1315: aload 31
          //   1317: ldc_w 460
          //   1320: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1323: ldc_w 462
          //   1326: ldc_w 464
          //   1329: invokevirtual 468	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   1332: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1335: invokevirtual 361	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   1338: astore 75
          //   1340: aload_0
          //   1341: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1344: new 38	org/json/JSONObject
          //   1347: dup
          //   1348: invokespecial 148	org/json/JSONObject:<init>	()V
          //   1351: invokestatic 472	com/fab/sale/ProductDetailsActivity:access$27	(Lcom/fab/sale/ProductDetailsActivity;Lorg/json/JSONObject;)V
          //   1354: aload_0
          //   1355: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1358: invokestatic 476	com/fab/sale/ProductDetailsActivity:access$28	(Lcom/fab/sale/ProductDetailsActivity;)Lorg/json/JSONObject;
          //   1361: ldc_w 478
          //   1364: aload_2
          //   1365: ldc_w 480
          //   1368: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1371: invokevirtual 484	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
          //   1374: pop
          //   1375: aload_0
          //   1376: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1379: invokestatic 476	com/fab/sale/ProductDetailsActivity:access$28	(Lcom/fab/sale/ProductDetailsActivity;)Lorg/json/JSONObject;
          //   1382: ldc_w 486
          //   1385: aload_2
          //   1386: ldc_w 488
          //   1389: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1392: invokevirtual 484	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
          //   1395: pop
          //   1396: aload_0
          //   1397: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1400: invokestatic 476	com/fab/sale/ProductDetailsActivity:access$28	(Lcom/fab/sale/ProductDetailsActivity;)Lorg/json/JSONObject;
          //   1403: ldc_w 490
          //   1406: aload 75
          //   1408: invokevirtual 484	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
          //   1411: pop
          //   1412: aload_0
          //   1413: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1416: getfield 494	com/fab/sale/ProductDetailsActivity:helper	Lcom/fab/utils/FabHelper;
          //   1419: aload_0
          //   1420: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1423: invokestatic 121	com/fab/sale/ProductDetailsActivity:access$1	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/content/Context;
          //   1426: aload 75
          //   1428: invokevirtual 498	com/fab/utils/FabHelper:doesFileExists	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
          //   1431: astore 79
          //   1433: aload 79
          //   1435: invokestatic 117	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   1438: ifne +223 -> 1661
          //   1441: aload_0
          //   1442: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1445: invokestatic 456	com/fab/sale/ProductDetailsActivity:access$26	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/ProgressBar;
          //   1448: iconst_4
          //   1449: invokevirtual 440	android/widget/ProgressBar:setVisibility	(I)V
          //   1452: aload_0
          //   1453: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1456: invokestatic 501	com/fab/sale/ProductDetailsActivity:access$29	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/ImageView;
          //   1459: aload_0
          //   1460: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1463: getfield 494	com/fab/sale/ProductDetailsActivity:helper	Lcom/fab/utils/FabHelper;
          //   1466: aload 79
          //   1468: invokevirtual 505	com/fab/utils/FabHelper:decodeFileForBitmap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
          //   1471: invokevirtual 509	android/widget/ImageView:setImageBitmap	(Landroid/graphics/Bitmap;)V
          //   1474: aload 68
          //   1476: new 348	java/lang/StringBuilder
          //   1479: dup
          //   1480: ldc_w 458
          //   1483: invokespecial 354	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   1486: aload 31
          //   1488: ldc_w 460
          //   1491: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1494: ldc_w 462
          //   1497: ldc_w 464
          //   1500: invokevirtual 468	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   1503: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1506: invokevirtual 361	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   1509: invokevirtual 512	com/fab/sale/ProductZoomBo:setImageUrl	(Ljava/lang/String;)V
          //   1512: aload 68
          //   1514: new 348	java/lang/StringBuilder
          //   1517: dup
          //   1518: ldc_w 458
          //   1521: invokespecial 354	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   1524: aload 31
          //   1526: ldc_w 460
          //   1529: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1532: ldc_w 462
          //   1535: ldc_w 514
          //   1538: invokevirtual 468	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   1541: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1544: invokevirtual 361	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   1547: invokevirtual 517	com/fab/sale/ProductZoomBo:setGalleryImageUrl	(Ljava/lang/String;)V
          //   1550: aload 31
          //   1552: ldc_w 519
          //   1555: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1558: invokestatic 117	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   1561: ifne +166 -> 1727
          //   1564: aload 68
          //   1566: new 348	java/lang/StringBuilder
          //   1569: dup
          //   1570: ldc_w 458
          //   1573: invokespecial 354	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   1576: aload 31
          //   1578: ldc_w 460
          //   1581: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1584: ldc_w 462
          //   1587: aload 31
          //   1589: ldc_w 519
          //   1592: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1595: invokevirtual 468	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
          //   1598: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1601: invokevirtual 361	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   1604: invokevirtual 522	com/fab/sale/ProductZoomBo:setZoomImageUrl	(Ljava/lang/String;)V
          //   1607: aload 68
          //   1609: aload 31
          //   1611: ldc_w 519
          //   1614: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1617: iconst_1
          //   1618: aload 31
          //   1620: ldc_w 519
          //   1623: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1626: ldc_w 524
          //   1629: invokevirtual 527	java/lang/String:lastIndexOf	(Ljava/lang/String;)I
          //   1632: iadd
          //   1633: invokevirtual 531	java/lang/String:substring	(I)Ljava/lang/String;
          //   1636: invokestatic 536	java/lang/Integer:parseInt	(Ljava/lang/String;)I
          //   1639: invokevirtual 539	com/fab/sale/ProductZoomBo:setZoomSize	(I)V
          //   1642: aload_0
          //   1643: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1646: getfield 243	com/fab/sale/ProductDetailsActivity:theProductsGallery	Ljava/util/ArrayList;
          //   1649: aload 68
          //   1651: invokevirtual 543	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   1654: pop
          //   1655: iinc 32 1
          //   1658: goto -1031 -> 627
          //   1661: new 545	com/fab/utils/ImageDownloader
          //   1664: dup
          //   1665: aload_0
          //   1666: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1669: invokestatic 121	com/fab/sale/ProductDetailsActivity:access$1	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/content/Context;
          //   1672: aconst_null
          //   1673: iconst_1
          //   1674: aconst_null
          //   1675: invokespecial 548	com/fab/utils/ImageDownloader:<init>	(Landroid/content/Context;Lcom/fab/utils/FabBaseAdapter;ZLandroid/widget/ProgressBar;)V
          //   1678: astore 80
          //   1680: new 8	com/fab/sale/ProductDetailsActivity$19$1
          //   1683: dup
          //   1684: aload_0
          //   1685: aload 75
          //   1687: invokespecial 551	com/fab/sale/ProductDetailsActivity$19$1:<init>	(Lcom/fab/sale/ProductDetailsActivity$19;Ljava/lang/String;)V
          //   1690: astore 81
          //   1692: aload 80
          //   1694: aload 81
          //   1696: invokevirtual 555	com/fab/utils/ImageDownloader:setTaskCompleteListener	(Lcom/fab/utils/TaskCompleteListener;)V
          //   1699: iconst_1
          //   1700: anewarray 272	java/lang/String
          //   1703: astore 82
          //   1705: aload 82
          //   1707: iconst_0
          //   1708: aload 75
          //   1710: aastore
          //   1711: aload 80
          //   1713: aload 82
          //   1715: invokevirtual 559	com/fab/utils/ImageDownloader:execute	([Ljava/lang/Object;)Landroid/os/AsyncTask;
          //   1718: pop
          //   1719: goto -245 -> 1474
          //   1722: astore 74
          //   1724: goto -250 -> 1474
          //   1727: aload 68
          //   1729: ldc 105
          //   1731: invokevirtual 522	com/fab/sale/ProductZoomBo:setZoomImageUrl	(Ljava/lang/String;)V
          //   1734: goto -92 -> 1642
          //   1737: astore 73
          //   1739: goto -97 -> 1642
          //   1742: aload_0
          //   1743: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1746: invokestatic 279	com/fab/sale/ProductDetailsActivity:access$32	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/Button;
          //   1749: iconst_0
          //   1750: invokevirtual 282	android/widget/Button:setVisibility	(I)V
          //   1753: new 348	java/lang/StringBuilder
          //   1756: dup
          //   1757: aload_0
          //   1758: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1761: invokestatic 270	com/fab/sale/ProductDetailsActivity:access$31	(Lcom/fab/sale/ProductDetailsActivity;)Ljava/lang/String;
          //   1764: invokestatic 352	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
          //   1767: invokespecial 354	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   1770: invokevirtual 361	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   1773: invokevirtual 560	java/lang/String:length	()I
          //   1776: iconst_2
          //   1777: if_icmple +26 -> 1803
          //   1780: aload_0
          //   1781: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1784: invokestatic 279	com/fab/sale/ProductDetailsActivity:access$32	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/Button;
          //   1787: aload_0
          //   1788: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1791: invokevirtual 296	com/fab/sale/ProductDetailsActivity:getResources	()Landroid/content/res/Resources;
          //   1794: ldc_w 561
          //   1797: invokevirtual 303	android/content/res/Resources:getDrawable	(I)Landroid/graphics/drawable/Drawable;
          //   1800: invokevirtual 564	android/widget/Button:setBackgroundDrawable	(Landroid/graphics/drawable/Drawable;)V
          //   1803: aload_0
          //   1804: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1807: invokestatic 279	com/fab/sale/ProductDetailsActivity:access$32	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/Button;
          //   1810: new 348	java/lang/StringBuilder
          //   1813: dup
          //   1814: invokespecial 565	java/lang/StringBuilder:<init>	()V
          //   1817: aload_0
          //   1818: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1821: invokestatic 270	com/fab/sale/ProductDetailsActivity:access$31	(Lcom/fab/sale/ProductDetailsActivity;)Ljava/lang/String;
          //   1824: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1827: invokevirtual 361	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   1830: invokevirtual 566	android/widget/Button:setText	(Ljava/lang/CharSequence;)V
          //   1833: goto -1110 -> 723
          //   1836: aload_0
          //   1837: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1840: invokestatic 292	com/fab/sale/ProductDetailsActivity:access$34	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/ImageView;
          //   1843: astore 63
          //   1845: new 10	com/fab/sale/ProductDetailsActivity$19$2
          //   1848: dup
          //   1849: aload_0
          //   1850: invokespecial 569	com/fab/sale/ProductDetailsActivity$19$2:<init>	(Lcom/fab/sale/ProductDetailsActivity$19;)V
          //   1853: astore 64
          //   1855: aload 63
          //   1857: aload 64
          //   1859: invokevirtual 316	android/widget/ImageView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
          //   1862: goto -1057 -> 805
          //   1865: aload_0
          //   1866: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1869: invokestatic 395	com/fab/sale/ProductDetailsActivity:access$43	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabRotatedTextView;
          //   1872: iconst_4
          //   1873: invokevirtual 570	com/fab/views/FabRotatedTextView:setVisibility	(I)V
          //   1876: aload_0
          //   1877: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1880: invokestatic 573	com/fab/sale/ProductDetailsActivity:access$44	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/ImageView;
          //   1883: iconst_4
          //   1884: invokevirtual 574	android/widget/ImageView:setVisibility	(I)V
          //   1887: goto -807 -> 1080
          //   1890: astore 39
          //   1892: goto -812 -> 1080
          //   1895: aload_0
          //   1896: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1899: aload_0
          //   1900: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1903: invokestatic 578	com/fab/sale/ProductDetailsActivity:access$47	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/view/LayoutInflater;
          //   1906: ldc_w 579
          //   1909: aconst_null
          //   1910: invokevirtual 585	android/view/LayoutInflater:inflate	(ILandroid/view/ViewGroup;)Landroid/view/View;
          //   1913: invokestatic 589	com/fab/sale/ProductDetailsActivity:access$48	(Lcom/fab/sale/ProductDetailsActivity;Landroid/view/View;)V
          //   1916: aload_0
          //   1917: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1920: aload_0
          //   1921: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1924: invokestatic 593	com/fab/sale/ProductDetailsActivity:access$49	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/view/View;
          //   1927: ldc_w 594
          //   1930: invokevirtual 600	android/view/View:findViewById	(I)Landroid/view/View;
          //   1933: checkcast 322	com/fab/views/FabTextView
          //   1936: invokestatic 604	com/fab/sale/ProductDetailsActivity:access$50	(Lcom/fab/sale/ProductDetailsActivity;Lcom/fab/views/FabTextView;)V
          //   1939: aload_0
          //   1940: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   1943: invokestatic 607	com/fab/sale/ProductDetailsActivity:access$51	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabTextView;
          //   1946: new 348	java/lang/StringBuilder
          //   1949: dup
          //   1950: ldc_w 609
          //   1953: invokespecial 354	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   1956: aload 55
          //   1958: iload 56
          //   1960: invokevirtual 443	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   1963: ldc_w 611
          //   1966: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1969: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1972: ldc_w 613
          //   1975: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1978: ldc_w 615
          //   1981: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   1984: aload 55
          //   1986: iload 56
          //   1988: invokevirtual 443	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   1991: ldc_w 617
          //   1994: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   1997: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   2000: ldc_w 619
          //   2003: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   2006: invokevirtual 361	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   2009: invokestatic 625	android/text/Html:fromHtml	(Ljava/lang/String;)Landroid/text/Spanned;
          //   2012: invokevirtual 326	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
          //   2015: aload_0
          //   2016: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2019: invokestatic 414	com/fab/sale/ProductDetailsActivity:access$46	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/LinearLayout;
          //   2022: aload_0
          //   2023: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2026: invokestatic 593	com/fab/sale/ProductDetailsActivity:access$49	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/view/View;
          //   2029: iload 56
          //   2031: invokevirtual 629	android/widget/LinearLayout:addView	(Landroid/view/View;I)V
          //   2034: iinc 56 1
          //   2037: goto -869 -> 1168
          //   2040: aload_0
          //   2041: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2044: aload_0
          //   2045: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2048: invokestatic 578	com/fab/sale/ProductDetailsActivity:access$47	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/view/LayoutInflater;
          //   2051: ldc_w 630
          //   2054: aconst_null
          //   2055: invokevirtual 585	android/view/LayoutInflater:inflate	(ILandroid/view/ViewGroup;)Landroid/view/View;
          //   2058: invokestatic 633	com/fab/sale/ProductDetailsActivity:access$53	(Lcom/fab/sale/ProductDetailsActivity;Landroid/view/View;)V
          //   2061: aload_0
          //   2062: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2065: aload_0
          //   2066: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2069: invokestatic 636	com/fab/sale/ProductDetailsActivity:access$54	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/view/View;
          //   2072: ldc_w 637
          //   2075: invokevirtual 600	android/view/View:findViewById	(I)Landroid/view/View;
          //   2078: checkcast 322	com/fab/views/FabTextView
          //   2081: invokestatic 640	com/fab/sale/ProductDetailsActivity:access$55	(Lcom/fab/sale/ProductDetailsActivity;Lcom/fab/views/FabTextView;)V
          //   2084: aload_0
          //   2085: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2088: invokestatic 643	com/fab/sale/ProductDetailsActivity:access$56	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabTextView;
          //   2091: ldc_w 645
          //   2094: invokevirtual 648	com/fab/views/FabTextView:setFont	(Ljava/lang/String;)V
          //   2097: aload_0
          //   2098: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2101: aload_0
          //   2102: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2105: invokestatic 636	com/fab/sale/ProductDetailsActivity:access$54	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/view/View;
          //   2108: ldc_w 649
          //   2111: invokevirtual 600	android/view/View:findViewById	(I)Landroid/view/View;
          //   2114: checkcast 305	android/widget/ImageView
          //   2117: invokestatic 653	com/fab/sale/ProductDetailsActivity:access$57	(Lcom/fab/sale/ProductDetailsActivity;Landroid/widget/ImageView;)V
          //   2120: aload 27
          //   2122: iload 43
          //   2124: invokevirtual 443	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   2127: astore 45
          //   2129: aload_0
          //   2130: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2133: invokestatic 643	com/fab/sale/ProductDetailsActivity:access$56	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabTextView;
          //   2136: aload 45
          //   2138: ldc_w 655
          //   2141: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   2144: invokevirtual 326	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
          //   2147: aload 45
          //   2149: ldc_w 657
          //   2152: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   2155: astore 46
          //   2157: ldc_w 659
          //   2160: aload 46
          //   2162: invokevirtual 275	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
          //   2165: ifne +117 -> 2282
          //   2168: new 348	java/lang/StringBuilder
          //   2171: dup
          //   2172: aload 46
          //   2174: invokestatic 352	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
          //   2177: invokespecial 354	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   2180: ldc_w 397
          //   2183: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   2186: invokestatic 664	com/fab/utils/FabSharedPrefs:getDeviceDensity	()Ljava/lang/String;
          //   2189: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   2192: invokevirtual 361	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   2195: astore 50
          //   2197: invokestatic 668	com/fab/utils/FabHelper:getFabHelper	()Lcom/fab/utils/FabHelper;
          //   2200: aload_0
          //   2201: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2204: invokestatic 121	com/fab/sale/ProductDetailsActivity:access$1	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/content/Context;
          //   2207: aload 50
          //   2209: invokestatic 671	com/fab/utils/FabUtils:s3MobileIconsUrl	(Ljava/lang/String;)Ljava/lang/String;
          //   2212: invokevirtual 498	com/fab/utils/FabHelper:doesFileExists	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
          //   2215: invokestatic 117	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   2218: ifeq +104 -> 2322
          //   2221: new 545	com/fab/utils/ImageDownloader
          //   2224: dup
          //   2225: aload_0
          //   2226: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2229: invokestatic 121	com/fab/sale/ProductDetailsActivity:access$1	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/content/Context;
          //   2232: aconst_null
          //   2233: iconst_1
          //   2234: aconst_null
          //   2235: invokespecial 548	com/fab/utils/ImageDownloader:<init>	(Landroid/content/Context;Lcom/fab/utils/FabBaseAdapter;ZLandroid/widget/ProgressBar;)V
          //   2238: astore 51
          //   2240: new 12	com/fab/sale/ProductDetailsActivity$19$3
          //   2243: dup
          //   2244: aload_0
          //   2245: aload 50
          //   2247: invokespecial 672	com/fab/sale/ProductDetailsActivity$19$3:<init>	(Lcom/fab/sale/ProductDetailsActivity$19;Ljava/lang/String;)V
          //   2250: astore 52
          //   2252: aload 51
          //   2254: aload 52
          //   2256: invokevirtual 555	com/fab/utils/ImageDownloader:setTaskCompleteListener	(Lcom/fab/utils/TaskCompleteListener;)V
          //   2259: iconst_1
          //   2260: anewarray 272	java/lang/String
          //   2263: astore 53
          //   2265: aload 53
          //   2267: iconst_0
          //   2268: aload 50
          //   2270: invokestatic 671	com/fab/utils/FabUtils:s3MobileIconsUrl	(Ljava/lang/String;)Ljava/lang/String;
          //   2273: aastore
          //   2274: aload 51
          //   2276: aload 53
          //   2278: invokevirtual 559	com/fab/utils/ImageDownloader:execute	([Ljava/lang/Object;)Landroid/os/AsyncTask;
          //   2281: pop
          //   2282: aload_0
          //   2283: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2286: invokestatic 422	com/fab/sale/ProductDetailsActivity:access$52	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/LinearLayout;
          //   2289: aload_0
          //   2290: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2293: invokestatic 636	com/fab/sale/ProductDetailsActivity:access$54	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/view/View;
          //   2296: invokevirtual 675	android/widget/LinearLayout:addView	(Landroid/view/View;)V
          //   2299: aload 45
          //   2301: ldc_w 677
          //   2304: invokevirtual 228	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
          //   2307: astore 47
          //   2309: iconst_0
          //   2310: istore 48
          //   2312: aload 47
          //   2314: invokevirtual 247	org/json/JSONArray:length	()I
          //   2317: istore 49
          //   2319: goto +500 -> 2819
          //   2322: aload_0
          //   2323: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2326: invokestatic 680	com/fab/sale/ProductDetailsActivity:access$58	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/ImageView;
          //   2329: invokestatic 668	com/fab/utils/FabHelper:getFabHelper	()Lcom/fab/utils/FabHelper;
          //   2332: invokestatic 668	com/fab/utils/FabHelper:getFabHelper	()Lcom/fab/utils/FabHelper;
          //   2335: aload_0
          //   2336: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2339: invokestatic 121	com/fab/sale/ProductDetailsActivity:access$1	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/content/Context;
          //   2342: aload 50
          //   2344: invokestatic 671	com/fab/utils/FabUtils:s3MobileIconsUrl	(Ljava/lang/String;)Ljava/lang/String;
          //   2347: invokevirtual 498	com/fab/utils/FabHelper:doesFileExists	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
          //   2350: invokevirtual 505	com/fab/utils/FabHelper:decodeFileForBitmap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
          //   2353: invokevirtual 509	android/widget/ImageView:setImageBitmap	(Landroid/graphics/Bitmap;)V
          //   2356: goto -74 -> 2282
          //   2359: aload_0
          //   2360: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2363: aload_0
          //   2364: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2367: invokestatic 578	com/fab/sale/ProductDetailsActivity:access$47	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/view/LayoutInflater;
          //   2370: ldc_w 579
          //   2373: aconst_null
          //   2374: invokevirtual 585	android/view/LayoutInflater:inflate	(ILandroid/view/ViewGroup;)Landroid/view/View;
          //   2377: invokestatic 683	com/fab/sale/ProductDetailsActivity:access$59	(Lcom/fab/sale/ProductDetailsActivity;Landroid/view/View;)V
          //   2380: aload_0
          //   2381: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2384: aload_0
          //   2385: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2388: invokestatic 686	com/fab/sale/ProductDetailsActivity:access$60	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/view/View;
          //   2391: ldc_w 594
          //   2394: invokevirtual 600	android/view/View:findViewById	(I)Landroid/view/View;
          //   2397: checkcast 322	com/fab/views/FabTextView
          //   2400: invokestatic 604	com/fab/sale/ProductDetailsActivity:access$50	(Lcom/fab/sale/ProductDetailsActivity;Lcom/fab/views/FabTextView;)V
          //   2403: aload 47
          //   2405: iload 48
          //   2407: invokevirtual 443	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   2410: ldc_w 611
          //   2413: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   2416: invokestatic 117	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   2419: ifeq +52 -> 2471
          //   2422: aload_0
          //   2423: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2426: invokestatic 607	com/fab/sale/ProductDetailsActivity:access$51	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabTextView;
          //   2429: aload 47
          //   2431: iload 48
          //   2433: invokevirtual 443	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   2436: ldc_w 617
          //   2439: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   2442: invokestatic 625	android/text/Html:fromHtml	(Ljava/lang/String;)Landroid/text/Spanned;
          //   2445: invokevirtual 326	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
          //   2448: aload_0
          //   2449: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2452: invokestatic 422	com/fab/sale/ProductDetailsActivity:access$52	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/LinearLayout;
          //   2455: aload_0
          //   2456: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2459: invokestatic 686	com/fab/sale/ProductDetailsActivity:access$60	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/view/View;
          //   2462: invokevirtual 675	android/widget/LinearLayout:addView	(Landroid/view/View;)V
          //   2465: iinc 48 1
          //   2468: goto +351 -> 2819
          //   2471: aload_0
          //   2472: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2475: invokestatic 607	com/fab/sale/ProductDetailsActivity:access$51	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/FabTextView;
          //   2478: new 348	java/lang/StringBuilder
          //   2481: dup
          //   2482: ldc_w 609
          //   2485: invokespecial 354	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
          //   2488: aload 47
          //   2490: iload 48
          //   2492: invokevirtual 443	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   2495: ldc_w 611
          //   2498: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   2501: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   2504: ldc_w 613
          //   2507: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   2510: ldc_w 615
          //   2513: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   2516: aload 47
          //   2518: iload 48
          //   2520: invokevirtual 443	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
          //   2523: ldc_w 617
          //   2526: invokevirtual 111	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
          //   2529: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   2532: ldc_w 619
          //   2535: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   2538: invokevirtual 361	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   2541: invokestatic 625	android/text/Html:fromHtml	(Ljava/lang/String;)Landroid/text/Spanned;
          //   2544: invokevirtual 326	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
          //   2547: goto -99 -> 2448
          //   2550: aload_0
          //   2551: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2554: getfield 437	com/fab/sale/ProductDetailsActivity:pBar	Landroid/widget/ProgressBar;
          //   2557: bipush 8
          //   2559: invokevirtual 440	android/widget/ProgressBar:setVisibility	(I)V
          //   2562: aload_0
          //   2563: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2566: invokestatic 690	com/fab/sale/ProductDetailsActivity:access$64	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   2569: invokevirtual 695	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   2572: invokestatic 117	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
          //   2575: ifne -2386 -> 189
          //   2578: aload_0
          //   2579: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2582: invokestatic 430	com/fab/sale/ProductDetailsActivity:access$62	(Lcom/fab/sale/ProductDetailsActivity;)Landroid/widget/ScrollView;
          //   2585: bipush 8
          //   2587: invokevirtual 433	android/widget/ScrollView:setVisibility	(I)V
          //   2590: aload_0
          //   2591: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2594: invokestatic 142	com/fab/sale/ProductDetailsActivity:access$63	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/ErrorMessage;
          //   2597: iconst_0
          //   2598: invokevirtual 147	com/fab/views/ErrorMessage:setVisibility	(I)V
          //   2601: aload_0
          //   2602: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2605: invokestatic 142	com/fab/sale/ProductDetailsActivity:access$63	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/views/ErrorMessage;
          //   2608: aload_0
          //   2609: getfield 24	com/fab/sale/ProductDetailsActivity$19:this$0	Lcom/fab/sale/ProductDetailsActivity;
          //   2612: invokestatic 690	com/fab/sale/ProductDetailsActivity:access$64	(Lcom/fab/sale/ProductDetailsActivity;)Lcom/fab/utils/ExecuteRequestAsyncTask;
          //   2615: invokevirtual 695	com/fab/utils/ExecuteRequestAsyncTask:getErrMsg	()Ljava/lang/String;
          //   2618: invokevirtual 698	com/fab/views/ErrorMessage:setErrorMsg	(Ljava/lang/String;)V
          //   2621: goto -2432 -> 189
          //   2624: astore 67
          //   2626: goto -1360 -> 1266
          //   2629: astore 41
          //   2631: goto -1456 -> 1175
          //   2634: astore 40
          //   2636: goto -1498 -> 1138
          //   2639: astore 38
          //   2641: goto -1618 -> 1023
          //   2644: astore 37
          //   2646: goto -1656 -> 990
          //   2649: astore 36
          //   2651: goto -1779 -> 872
          //   2654: astore 62
          //   2656: goto -1906 -> 750
          //   2659: astore 61
          //   2661: goto -1966 -> 695
          //   2664: astore 60
          //   2666: goto -1986 -> 680
          //   2669: astore 59
          //   2671: goto -2006 -> 665
          //   2674: astore 71
          //   2676: goto -1034 -> 1642
          //   2679: astore 70
          //   2681: goto -1131 -> 1550
          //   2684: astore 69
          //   2686: goto -1174 -> 1512
          //   2689: astore 77
          //   2691: goto -1295 -> 1396
          //   2694: astore 76
          //   2696: goto -1321 -> 1375
          //   2699: astore 30
          //   2701: goto -2107 -> 594
          //   2704: astore 28
          //   2706: goto -2134 -> 572
          //   2709: astore 26
          //   2711: goto -2161 -> 550
          //   2714: astore 24
          //   2716: goto -2188 -> 528
          //   2719: astore 22
          //   2721: goto -2210 -> 511
          //   2724: astore 20
          //   2726: goto -2237 -> 489
          //   2729: astore 18
          //   2731: goto -2264 -> 467
          //   2734: astore 16
          //   2736: goto -2291 -> 445
          //   2739: astore 14
          //   2741: goto -2317 -> 424
          //   2744: astore 96
          //   2746: goto -2335 -> 411
          //   2749: astore 95
          //   2751: goto -2354 -> 397
          //   2754: astore 104
          //   2756: goto -2398 -> 358
          //   2759: astore 103
          //   2761: goto -2417 -> 344
          //   2764: astore 102
          //   2766: goto -2439 -> 327
          //   2769: astore 101
          //   2771: goto -2461 -> 310
          //   2774: astore 100
          //   2776: goto -2483 -> 293
          //   2779: astore 99
          //   2781: goto -2505 -> 276
          //   2784: astore 98
          //   2786: goto -2527 -> 259
          //   2789: astore 8
          //   2791: goto -2580 -> 211
          //   2794: astore 6
          //   2796: goto -2660 -> 136
          //   2799: astore 4
          //   2801: goto -2681 -> 120
          //   2804: astore_3
          //   2805: goto -2768 -> 37
          //   2808: astore 35
          //   2810: goto -2005 -> 805
          //   2813: iconst_0
          //   2814: istore 66
          //   2816: goto -2073 -> 743
          //   2819: iload 48
          //   2821: iload 49
          //   2823: if_icmplt -464 -> 2359
          //   2826: iinc 43 1
          //   2829: goto -1634 -> 1195
          //   2832: astore 42
          //   2834: goto -1632 -> 1202
          //
          // Exception table:
          //   from	to	target	type
          //   211	219	1237	org/json/JSONException
          //   366	374	1245	org/json/JSONException
          //   1294	1354	1722	org/json/JSONException
          //   1396	1474	1722	org/json/JSONException
          //   1661	1719	1722	org/json/JSONException
          //   1550	1642	1737	java/lang/NumberFormatException
          //   1727	1734	1737	java/lang/NumberFormatException
          //   1023	1080	1890	org/json/JSONException
          //   1865	1887	1890	org/json/JSONException
          //   1253	1262	2624	org/json/JSONException
          //   1138	1168	2629	org/json/JSONException
          //   1895	2034	2629	org/json/JSONException
          //   1080	1138	2634	org/json/JSONException
          //   990	1023	2639	java/lang/Exception
          //   872	990	2644	org/json/JSONException
          //   836	872	2649	org/json/JSONException
          //   723	750	2654	org/json/JSONException
          //   680	695	2659	java/lang/Exception
          //   665	680	2664	org/json/JSONException
          //   651	665	2669	org/json/JSONException
          //   1550	1642	2674	org/json/JSONException
          //   1727	1734	2674	org/json/JSONException
          //   1512	1550	2679	org/json/JSONException
          //   1474	1512	2684	org/json/JSONException
          //   1375	1396	2689	org/json/JSONException
          //   1354	1375	2694	org/json/JSONException
          //   581	590	2699	org/json/JSONException
          //   559	568	2704	org/json/JSONException
          //   537	546	2709	org/json/JSONException
          //   515	524	2714	org/json/JSONException
          //   498	507	2719	org/json/JSONException
          //   476	485	2724	org/json/JSONException
          //   454	463	2729	org/json/JSONException
          //   433	441	2734	org/json/JSONException
          //   411	424	2739	org/json/JSONException
          //   397	411	2744	org/json/JSONException
          //   383	397	2749	org/json/JSONException
          //   344	358	2754	org/json/JSONException
          //   327	344	2759	org/json/JSONException
          //   310	327	2764	org/json/JSONException
          //   293	310	2769	org/json/JSONException
          //   276	293	2774	org/json/JSONException
          //   259	276	2779	org/json/JSONException
          //   242	259	2784	org/json/JSONException
          //   198	211	2789	org/json/JSONException
          //   124	132	2794	org/json/JSONException
          //   37	120	2799	org/json/JSONException
          //   30	37	2804	org/json/JSONException
          //   642	651	2808	org/json/JSONException
          //   680	695	2808	org/json/JSONException
          //   695	723	2808	org/json/JSONException
          //   750	805	2808	org/json/JSONException
          //   1742	1862	2808	org/json/JSONException
          //   1175	1195	2832	org/json/JSONException
          //   2040	2547	2832	org/json/JSONException
        }
      });
      ExecuteRequestAsyncTask localExecuteRequestAsyncTask = this.task;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/product/" + this.productId, "?sale_id=" + this.saleId);
      localExecuteRequestAsyncTask.execute(arrayOfString);
      this.tracker.trackPageView(FabUtils.gATracUrl("/product/" + this.productId));
    }
  }

  private void populateInventryDetails(JSONArray paramJSONArray)
  {
    try
    {
      this.productSizes = new HashMap();
      this.productSizesArray = new ArrayList();
      i = 0;
      int j = paramJSONArray.length();
      if (i >= j)
      {
        this.productSizes.put("-", Integer.valueOf(0));
        this.productSizesArray.add("-");
        if (this.productSizes.size() != 1)
          break label281;
        this.sizeRel.setVisibility(8);
        this.quantitySpinner.setClickable(true);
        this.quantitySpinner.setBackgroundDrawable(getResources().getDrawable(2130837548));
        this.addToCart.setEnabled(true);
        this.addToCart.setClickable(true);
      }
      while (true)
      {
        this.qtyArray = new ArrayAdapter(this.context, 17367048, this.theQuantity);
        this.qtyArray.setDropDownViewResource(17367049);
        this.quantitySpinner.setAdapter(this.qtyArray);
        break;
        String str = paramJSONArray.getJSONObject(i).getString("size");
        if (!TextUtils.isEmpty(str))
        {
          this.productSizesArray.add(str);
          this.productSizes.put(str, Integer.valueOf(paramJSONArray.getJSONObject(i).getInt("size_available_quantity")));
          break label349;
        }
        if (paramJSONArray.getJSONObject(i).getInt("size_available_quantity") == 0)
        {
          this.buyNow.setEnabled(false);
          this.buyNowBottom.setEnabled(false);
        }
        createQuantityList(paramJSONArray.getJSONObject(i).getInt("size_available_quantity"));
        break label349;
        label281: this.sizeArray = new ArrayAdapter(this.context, 17367048, this.productSizesArray.toArray());
        this.sizeArray.setDropDownViewResource(17367049);
        this.sizeSpinner.setAdapter(this.sizeArray);
        this.sizeSpinner.setSelection(this.sizeArray.getPosition("-"));
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        int i;
        return;
        i++;
      }
    }
    catch (JSONException localJSONException)
    {
      label348: label349: break label348;
    }
  }

  private void populatePopUpWindow()
  {
    this.tvTimerWindow = ((FabTextView)this.popUpTimer.findViewById(2131361792));
    this.tvTimerWindow.setFont("georgiai.ttf");
    this.timer = ((FabTextView)this.popUp.findViewById(2131361795));
    this.timer.setFont("georgiai.ttf");
    this.size = ((FabTextView)this.popUp.findViewById(2131361798));
    this.size.setFont("HelveticaNeu_bold.ttf");
    this.quantity = ((FabTextView)this.popUp.findViewById(2131361801));
    this.quantity.setFont("HelveticaNeu_bold.ttf");
    this.popUpRel = ((RelativeLayout)this.popUp.findViewById(2131361794));
    this.sizeRel = ((RelativeLayout)this.popUp.findViewById(2131361797));
    this.sizeSpinner = ((Spinner)this.popUp.findViewById(2131361799));
    this.sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        ProductDetailsActivity.this.sizeAddToCart = paramAnonymousAdapterView.getSelectedItem().toString();
        if (!ProductDetailsActivity.this.sizeAddToCart.equalsIgnoreCase("-"))
        {
          ProductDetailsActivity.this.createQuantityList(((Integer)ProductDetailsActivity.this.productSizes.get(ProductDetailsActivity.this.sizeAddToCart)).intValue());
          ProductDetailsActivity.this.qtyArray.notifyDataSetChanged();
          ProductDetailsActivity.this.quantitySpinner.setEnabled(true);
          ProductDetailsActivity.this.quantity.setTextColor(ProductDetailsActivity.this.getResources().getColor(2131099654));
          ProductDetailsActivity.this.quantitySpinner.setClickable(true);
          ProductDetailsActivity.this.quantitySpinner.setBackgroundDrawable(ProductDetailsActivity.this.getResources().getDrawable(2130837548));
          ProductDetailsActivity.this.addToCart.setEnabled(true);
          ProductDetailsActivity.this.addToCart.setTextColor(ProductDetailsActivity.this.getResources().getColor(2131099649));
          ProductDetailsActivity.this.addToCart.setClickable(true);
        }
        while (true)
        {
          return;
          ProductDetailsActivity.this.quantitySpinner.setEnabled(false);
          ProductDetailsActivity.this.quantity.setTextColor(ProductDetailsActivity.this.getResources().getColor(2131099657));
          ProductDetailsActivity.this.addToCart.setEnabled(false);
        }
      }

      public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView)
      {
      }
    });
    this.quantitySpinner = ((Spinner)this.popUp.findViewById(2131361802));
    this.quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        ProductDetailsActivity.this.qtyAddToCart = ((Integer)paramAnonymousAdapterView.getSelectedItem());
      }

      public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView)
      {
      }
    });
    this.addToCart = ((FabButton)this.popUp.findViewById(2131361803));
    this.addToCart.setFont("HelveticaNeu_bold.ttf");
    this.addToCart.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ProductDetailsActivity.this.addProductToCart();
      }
    });
    this.cancel = ((FabButton)this.popUp.findViewById(2131361804));
    this.cancel.setFont("HelveticaNeu_bold.ttf");
    this.cancel.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ProductDetailsActivity.this.buyNow.setEnabled(true);
        ProductDetailsActivity.this.buyNowBottom.setEnabled(true);
        ProductDetailsActivity.this.popUpRel.clearAnimation();
        ProductDetailsActivity.this.popUpRel.startAnimation(ProductDetailsActivity.this.animation);
      }
    });
  }

  private void refreshActivity()
  {
    this.resultPopUp.dismiss();
    this.timerPopUpWindow.dismiss();
    if (FabSharedPrefs.isGuestUser())
    {
      this.buyNow.setText("Sign in to Buy");
      this.buyNowBottom.setText("Sign in to Buy");
      this.buyNow.setBackgroundResource(2130837739);
      this.buyNowBottom.setBackgroundResource(2130837739);
    }
    while (true)
    {
      FabHelper.stopAndRestartCartTimer(getApplicationContext(), 0L, this.mHandler);
      this.titleWidget.clearCartButton();
      this.productScrollView.setVisibility(8);
      this.popUpRel.setVisibility(8);
      this.error.setVisibility(8);
      this.pBar.setVisibility(0);
      loadProductDetails();
      return;
      this.buyNow.setText("Buy Now!");
      this.buyNowBottom.setText("Buy Now!");
      this.buyNow.setBackgroundResource(2130837525);
      this.buyNowBottom.setBackgroundResource(2130837525);
    }
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
    setContentView(2130903080);
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("productDetails"));
    this.context = this;
    if (TextUtils.isEmpty(FabSharedPrefs.getDeviceDensity()))
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
      FabSharedPrefs.setDeviceDensity(localDisplayMetrics.densityDpi);
    }
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
    this.isActivityAlive = true;
    this.titleWidget = ((TitleWidget)findViewById(2131361810));
    this.extras = getIntent().getExtras();
    if (this.extras != null)
    {
      this.saleId = this.extras.getInt("SALE_ID");
      this.productId = this.extras.getInt("PRODUCT_ID");
      this.extras.getString("PRODUCT_NAME");
    }
    this.alertbox = new AlertDialog.Builder(this);
    this.dialog = new ProgressDialog(this);
    loadProductDetails();
    initializeUI();
    this.animation = AnimationUtils.loadAnimation(this.context, 2130968579);
    this.animation.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        ProductDetailsActivity.this.popUpRel.setVisibility(8);
      }

      public void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnonymousAnimation)
      {
      }
    });
    this.tracker.dispatch();
    this.headerCartButton = this.titleWidget.getCartButton();
  }

  protected void onDestroy()
  {
    this.isActivityAlive = false;
    this.timerPopUpWindow.dismiss();
    this.resultPopUp.dismiss();
    if ((this.task != null) && (this.task.getStatus() != AsyncTask.Status.RUNNING))
    {
      this.task.cancel(true);
      this.task = null;
    }
    System.gc();
    finish();
    this.tracker.stopSession();
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    if ((paramInt == 4) && (this.popUpRel.getVisibility() == 0))
    {
      this.popUpRel.clearAnimation();
      this.popUpRel.startAnimation(this.animation);
      this.buyNow.setEnabled(bool);
      this.buyNowBottom.setEnabled(bool);
    }
    while (true)
    {
      return bool;
      bool = super.onKeyDown(paramInt, paramKeyEvent);
    }
  }

  protected void onPause()
  {
    this.isActivityAlive = false;
    this.timerPopUpWindow.dismiss();
    this.resultPopUp.dismiss();
    System.gc();
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
        Intent localIntent = new Intent(ProductDetailsActivity.this.context, MoreActivity.class);
        ProductDetailsActivity.this.startActivity(localIntent);
        return false;
      }
    });
    MenuItem localMenuItem2 = paramMenu.findItem(2131362109);
    localMenuItem2.setVisible(true);
    localMenuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        Intent localIntent = new Intent(ProductDetailsActivity.this.context, FabWebViewActivity.class);
        ProductDetailsActivity.this.startActivity(localIntent);
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
          FabUtils.showGuestAlert(ProductDetailsActivity.this.context, ProductDetailsActivity.this.context.getResources().getString(2131165208), ProductDetailsActivity.this.context.getResources().getString(2131165209), true);
        while (true)
        {
          return false;
          Intent localIntent = new Intent(ProductDetailsActivity.this.context, InviteActivity.class);
          ProductDetailsActivity.this.startActivity(localIntent);
        }
      }
    });
    this.refreshItem = paramMenu.findItem(2131362103);
    this.refreshItem.setVisible(true);
    this.refreshItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    {
      public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
      {
        ProductDetailsActivity.this.refreshActivity();
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
          FabUtils.showGuestAlert(ProductDetailsActivity.this.context, ProductDetailsActivity.this.context.getResources().getString(2131165208), ProductDetailsActivity.this.context.getResources().getString(2131165210), true);
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
          if (FabSharedPrefs.isGuestUser())
            FabUtils.showGuestAlert(ProductDetailsActivity.this.context, ProductDetailsActivity.this.context.getResources().getString(2131165208), ProductDetailsActivity.this.context.getResources().getString(2131165210), true);
          while (true)
          {
            return false;
            if (ProductDetailsActivity.this.fbShareData != null)
            {
              ProductDetailsActivity.this.fbClient = new FabFBClient(ProductDetailsActivity.this.context);
              ProductDetailsActivity.this.fbClient.setFBShareData(ProductDetailsActivity.this.fbShareData);
              ProductDetailsActivity.this.fbClient.setHasFBShareToken(ProductDetailsActivity.this.hasFBShareToken);
              ProductDetailsActivity.this.fbClient.sendRequest("publish_stream");
            }
          }
        }
      });
      MenuItem localMenuItem5 = paramMenu.findItem(2131362105);
      localMenuItem5.setVisible(true);
      localMenuItem5.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
      {
        public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
        {
          if (FabSharedPrefs.isGuestUser())
            FabUtils.showGuestAlert(ProductDetailsActivity.this.context, ProductDetailsActivity.this.context.getResources().getString(2131165208), ProductDetailsActivity.this.context.getResources().getString(2131165210), true);
          while (true)
          {
            return false;
            if (ProductDetailsActivity.this.twShareText != null)
            {
              Intent localIntent = new Intent(ProductDetailsActivity.this.context, FabTwitterClient.class);
              localIntent.putExtra("TWEET_TEXT", ProductDetailsActivity.this.twShareText);
              ProductDetailsActivity.this.startActivity(localIntent);
            }
          }
        }
      });
      MenuItem localMenuItem6 = paramMenu.findItem(2131362107);
      localMenuItem6.setVisible(true);
      localMenuItem6.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
      {
        public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
        {
          if (ProductDetailsActivity.this.emailBody != null)
          {
            Intent localIntent = new Intent("android.intent.action.SEND");
            localIntent.setType("text/html");
            localIntent.putExtra("android.intent.extra.SUBJECT", ProductDetailsActivity.this.emailSubject);
            localIntent.putExtra("android.intent.extra.TEXT", Html.fromHtml(ProductDetailsActivity.this.emailBody));
            ProductDetailsActivity.this.startActivity(localIntent);
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
          if (ProductDetailsActivity.this.smsShare != null)
          {
            Intent localIntent = new Intent("android.intent.action.VIEW");
            localIntent.setType("vnd.android-dir/mms-sms");
            localIntent.setData(Uri.parse("sms:"));
            localIntent.putExtra("sms_body", ProductDetailsActivity.this.smsShare);
            ProductDetailsActivity.this.startActivity(localIntent);
          }
          return false;
        }
      });
    }
    return true;
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
    if (FabSharedPrefs.isGuestUser())
    {
      this.buyNow.setText("Sign in to Buy");
      this.buyNowBottom.setText("Sign in to Buy");
      this.buyNow.setBackgroundResource(2130837739);
      this.buyNowBottom.setBackgroundResource(2130837739);
    }
    while (true)
    {
      if (((FabApplication)getApplication()).isRefreshActivityOnRestart())
      {
        ((FabApplication)getApplication()).setRefreshActivityOnRestart(false);
        refreshActivity();
      }
      return;
      this.buyNow.setText("Buy Now!");
      this.buyNowBottom.setText("Buy Now!");
      this.buyNow.setBackgroundResource(2130837525);
      this.buyNowBottom.setBackgroundResource(2130837525);
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
    this.isActivityAlive = true;
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.cancel();
    FabHelper.stopAndRestartCartTimer(getApplicationContext(), FabHelper.getCartTime(), this.mHandler);
    updateCartCounter(FabHelper.getCartCount());
    if (FabSharedPrefs.isGuestUser())
    {
      this.buyNow.setText("Sign in to Buy");
      this.buyNowBottom.setText("Sign in to Buy");
      this.buyNow.setBackgroundResource(2130837739);
      this.buyNowBottom.setBackgroundResource(2130837739);
    }
    while (true)
    {
      super.onResume();
      return;
      this.buyNow.setText("Buy Now!");
      this.buyNowBottom.setText("Buy Now!");
      this.buyNow.setBackgroundResource(2130837525);
      this.buyNowBottom.setBackgroundResource(2130837525);
    }
  }

  protected void populateCart(JSONObject paramJSONObject)
    throws JSONException
  {
    updateCartCounter(paramJSONObject.getInt("count"));
    if (FabUtils.getSecondsLeftForTimer(paramJSONObject.getInt("reserved_timestamp"), new GregorianCalendar()) >= 0L)
    {
      FabHelper.setCartTime(paramJSONObject.getInt("reserved_timestamp"));
      FabHelper.stopAndRestartCartTimer(getApplicationContext(), paramJSONObject.getInt("reserved_timestamp"), this.mHandler);
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
 * Qualified Name:     com.fab.sale.ProductDetailsActivity
 * JD-Core Version:    0.6.2
 */