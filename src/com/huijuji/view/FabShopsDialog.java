package com.huijuji.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.fab.sale.BrowseAdapter;
import com.fab.sale.BrowseAdapter.BrowseItemsHolder;
import com.fab.sale.CategoryAdapter;
import com.fab.sale.CategoryAdapter.CategoryHolder;
import com.fab.sale.ColorsAdapter;
import com.fab.sale.ColorsAdapter.ColorHolder;
import com.fab.sale.ShopDetailsActivity;
import com.fab.sale.ShopSortAdapter;
import com.fab.sale.ShopSortAdapter.ShopSortHolder;
import com.fab.sale.WeeklyShopDetailsActivity;
import com.fab.sale.bo.BrowseBo;
import com.fab.sale.bo.CategoryBo;
import com.fab.sale.bo.ShopSortBo;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabUtils;
import com.fab.utils.TaskCompleteListener;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FabShopsDialog extends Dialog
  implements AdapterView.OnItemClickListener, DialogInterface.OnDismissListener
{
  private BrowseAdapter browseAdapter;
  private Button browseButton;
  private CategoryAdapter categoryAdapter;
  private Drawable checkedImgDrawable;
  private ColorsAdapter colorsAdapter;
  private Context context;
  private GridView dialogGridView;
  private ListView dialogListView;
  private TextView dialogTitle;
  private RelativeLayout dialogprogressBar;
  private FabShopsDialog fabDialog;
  private Button leftButton;
  private Button rightButton;
  private ShopSortAdapter shopSortAdapter;
  private String shopSortUrl;
  private boolean silentDismiss;
  private ArrayList<BrowseBo> theBrowseItems;
  private GoogleAnalyticsTracker tracker;
  private Drawable uncheckedImgDrawable;

  public FabShopsDialog(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
  }

  public FabShopsDialog(Context paramContext, Button paramButton)
  {
    super(paramContext);
    this.context = paramContext;
    this.browseButton = paramButton;
  }

  public FabShopsDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    super(paramContext, paramBoolean, paramOnCancelListener);
  }

  private void getBrowseCategories(final String paramString)
  {
    if (paramString.equalsIgnoreCase("Categories"));
    for (final String str = "shops"; ; str = paramString.toLowerCase())
    {
      ExecuteRequestAsyncTask localExecuteRequestAsyncTask = new ExecuteRequestAsyncTask(this.context, "GET");
      localExecuteRequestAsyncTask.setTaskCompleteListener(new TaskCompleteListener()
      {
        public void onTaskComplete()
        {
        }

        public void onTaskComplete(Object paramAnonymousObject)
        {
          JSONObject localJSONObject1 = (JSONObject)paramAnonymousObject;
          Object localObject1;
          Object localObject2;
          ArrayList localArrayList;
          if (localJSONObject1 != null)
          {
            FabShopsDialog.this.dialogprogressBar.setVisibility(8);
            FabShopsDialog.this.dialogGridView.setVisibility(8);
            FabShopsDialog.this.dialogListView.setVisibility(0);
            ((RelativeLayout.LayoutParams)FabShopsDialog.this.dialogTitle.getLayoutParams()).addRule(13, 1);
            FabShopsDialog.this.leftButton.setVisibility(0);
            FabShopsDialog.this.dialogTitle.setText(paramString);
            localObject1 = new JSONArray();
            localObject2 = new JSONObject();
            localArrayList = new ArrayList();
          }
          try
          {
            JSONArray localJSONArray = localJSONObject1.getJSONObject("fab_shops").getJSONArray(str);
            localObject1 = localJSONArray;
            label132: int i = 0;
            int j = ((JSONArray)localObject1).length();
            while (true)
            {
              label246: CategoryBo localCategoryBo;
              if (i >= j)
              {
                FabShopsDialog.this.dialogprogressBar.setVisibility(8);
                FabShopsDialog.this.dialogListView.setVisibility(0);
                if (paramString.equalsIgnoreCase("colors"))
                {
                  FabShopsDialog.this.colorsAdapter = new ColorsAdapter(FabShopsDialog.this.context, localArrayList);
                  FabShopsDialog.this.dialogListView.setVisibility(8);
                  FabShopsDialog.this.dialogGridView.setVisibility(0);
                  FabShopsDialog.this.dialogGridView.setAdapter(FabShopsDialog.this.colorsAdapter);
                }
              }
              else
              {
                localCategoryBo = new CategoryBo();
              }
              try
              {
                JSONObject localJSONObject2 = ((JSONArray)localObject1).getJSONObject(i);
                localObject2 = localJSONObject2;
              }
              catch (JSONException localJSONException2)
              {
                try
                {
                  localCategoryBo.setShopId(((JSONObject)localObject2).getInt("shop_id"));
                  try
                  {
                    label280: localCategoryBo.setShopName(((JSONObject)localObject2).getString("shop_name"));
                    try
                    {
                      label292: localCategoryBo.setShopType(((JSONObject)localObject2).getString("type"));
                      try
                      {
                        label304: localCategoryBo.setShopTotalProducts(((JSONObject)localObject2).getInt("total_visible_products"));
                        label329: if (paramString.equalsIgnoreCase("colors"));
                        try
                        {
                          localCategoryBo.setImageUrl(((JSONObject)localObject2).getString("image_url"));
                          label353: localArrayList.add(localCategoryBo);
                          i++;
                          continue;
                          FabShopsDialog.this.categoryAdapter = new CategoryAdapter(FabShopsDialog.this.context, localArrayList);
                          FabShopsDialog.this.dialogGridView.setVisibility(8);
                          FabShopsDialog.this.dialogListView.setVisibility(0);
                          FabShopsDialog.this.dialogListView.setAdapter(FabShopsDialog.this.categoryAdapter);
                          break label246;
                          FabShopsDialog.this.fabDialog.dismiss();
                          FabShopsDialog.this.browseButton.setEnabled(true);
                          break label246;
                          localJSONException2 = localJSONException2;
                        }
                        catch (JSONException localJSONException7)
                        {
                          break label353;
                        }
                      }
                      catch (JSONException localJSONException6)
                      {
                        break label329;
                      }
                    }
                    catch (JSONException localJSONException5)
                    {
                      break label304;
                    }
                  }
                  catch (JSONException localJSONException4)
                  {
                    break label292;
                  }
                }
                catch (JSONException localJSONException3)
                {
                  break label280;
                }
              }
            }
          }
          catch (JSONException localJSONException1)
          {
            break label132;
          }
        }
      });
      String[] arrayOfString = new String[1];
      arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/browse-shops/", "?filter=" + str);
      localExecuteRequestAsyncTask.execute(arrayOfString);
      return;
    }
  }

  private int getHeight()
  {
    return 0;
  }

  public String getShopSortUrl()
  {
    return this.shopSortUrl;
  }

  public boolean isSilentDismiss()
  {
    return this.silentDismiss;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903053);
    this.context = getContext();
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this.context);
    this.tracker.trackPageView(FabUtils.gATracUrl("browserShops"));
    this.fabDialog = this;
    this.fabDialog.setOnDismissListener(this);
    this.checkedImgDrawable = this.context.getResources().getDrawable(2130837577);
    this.uncheckedImgDrawable = this.context.getResources().getDrawable(2130837578);
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131361866);
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -2);
    Display localDisplay = ((WindowManager)this.context.getSystemService("window")).getDefaultDisplay();
    int i = (int)(localDisplay.getHeight() - 0.9D * localDisplay.getHeight());
    localLayoutParams.setMargins(0, i, 0, i);
    localLinearLayout.setLayoutParams(localLayoutParams);
    this.dialogListView = ((ListView)findViewById(2131361879));
    this.dialogGridView = ((GridView)findViewById(2131361880));
    this.dialogListView.setOnItemClickListener(this);
    this.dialogGridView.setOnItemClickListener(this);
    this.dialogListView.setVisibility(8);
    this.dialogGridView.setVisibility(8);
    this.theBrowseItems = new ArrayList();
    this.browseAdapter = new BrowseAdapter(this.context, this.theBrowseItems);
    this.dialogListView.setAdapter(this.browseAdapter);
    this.dialogprogressBar = ((RelativeLayout)findViewById(2131361873));
    this.dialogprogressBar.setVisibility(0);
    this.dialogTitle = ((TextView)findViewById(2131361865));
    this.dialogTitle.setText("Browse");
    ((RelativeLayout.LayoutParams)this.dialogTitle.getLayoutParams()).addRule(13, 0);
    this.leftButton = ((Button)findViewById(2131361876));
    this.leftButton.setVisibility(8);
    this.leftButton.setText("Back");
    this.leftButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        FabShopsDialog.this.leftButton.setVisibility(8);
        RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)FabShopsDialog.this.dialogTitle.getLayoutParams();
        localLayoutParams.addRule(13, 0);
        FabShopsDialog.this.dialogTitle.setLayoutParams(localLayoutParams);
        FabShopsDialog.this.dialogTitle.setText("Browse");
        FabShopsDialog.this.browseAdapter = new BrowseAdapter(FabShopsDialog.this.context, FabShopsDialog.this.theBrowseItems);
        FabShopsDialog.this.dialogGridView.setVisibility(8);
        FabShopsDialog.this.dialogListView.setVisibility(0);
        FabShopsDialog.this.dialogListView.setAdapter(FabShopsDialog.this.browseAdapter);
      }
    });
    this.rightButton = ((Button)findViewById(2131361877));
    this.rightButton.setText("Cancel");
    this.rightButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        FabShopsDialog.this.fabDialog.dismiss();
      }
    });
  }

  public void onDismiss(DialogInterface paramDialogInterface)
  {
    this.browseButton.setEnabled(true);
    this.tracker.stopSession();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Class localClass = paramView.getTag().getClass();
    if (localClass.equals(BrowseAdapter.BrowseItemsHolder.class))
    {
      BrowseAdapter.BrowseItemsHolder localBrowseItemsHolder = (BrowseAdapter.BrowseItemsHolder)paramView.getTag();
      this.dialogprogressBar.setVisibility(0);
      this.dialogListView.setVisibility(8);
      this.dialogGridView.setVisibility(8);
      getBrowseCategories(localBrowseItemsHolder.title);
    }
    while (true)
    {
      return;
      if (localClass.equals(CategoryAdapter.CategoryHolder.class))
      {
        CategoryAdapter.CategoryHolder localCategoryHolder = (CategoryAdapter.CategoryHolder)paramView.getTag();
        Intent localIntent2;
        if ("weekly_shop".equalsIgnoreCase(localCategoryHolder.shopType))
        {
          localIntent2 = new Intent(this.context, WeeklyShopDetailsActivity.class);
          localIntent2.putExtra("WEEKLY_SHOP_ID", localCategoryHolder.shopId);
        }
        while (true)
        {
          this.context.startActivity(localIntent2);
          this.fabDialog.dismiss();
          break;
          localIntent2 = new Intent(this.context, ShopDetailsActivity.class);
          localIntent2.putExtra("SHOP_ID", localCategoryHolder.shopId);
        }
      }
      if (localClass.equals(ColorsAdapter.ColorHolder.class))
      {
        ColorsAdapter.ColorHolder localColorHolder = (ColorsAdapter.ColorHolder)paramView.getTag();
        Intent localIntent1 = new Intent(this.context, ShopDetailsActivity.class);
        localIntent1.putExtra("SHOP_ID", localColorHolder.shopId);
        this.context.startActivity(localIntent1);
        this.fabDialog.dismiss();
      }
      else if (localClass.equals(ShopSortAdapter.ShopSortHolder.class))
      {
        ShopSortAdapter.ShopSortHolder localShopSortHolder = (ShopSortAdapter.ShopSortHolder)paramView.getTag();
        int i = ((ShopSortAdapter)paramAdapterView.getAdapter()).getSelectedRowPosition();
        if (i != paramInt)
        {
          this.dialogListView.getChildAt(i).findViewById(2131362055).setBackgroundDrawable(this.uncheckedImgDrawable);
          ((ShopSortAdapter)paramAdapterView.getAdapter()).setSelectedRowPosition(paramInt);
          localShopSortHolder.sortRadioButton.setBackgroundDrawable(this.checkedImgDrawable);
          this.shopSortUrl = localShopSortHolder.shopSortUrl;
        }
        else
        {
          this.shopSortUrl = null;
        }
      }
    }
  }

  public void setBrowseShops()
  {
    this.fabDialog = this;
    this.fabDialog.show();
    this.browseButton.setEnabled(false);
    this.dialogListView.setAdapter(this.browseAdapter);
    this.dialogGridView.setVisibility(8);
    this.dialogListView.setVisibility(0);
    this.dialogTitle.setText("Browse");
    ((RelativeLayout.LayoutParams)this.dialogTitle.getLayoutParams()).addRule(13, 0);
    this.leftButton.setText("Back");
    this.leftButton.setVisibility(8);
    String[] arrayOfString1 = this.context.getResources().getStringArray(2131034113);
    String[] arrayOfString2 = this.context.getResources().getStringArray(2131034114);
    int i = arrayOfString1.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        this.dialogprogressBar.setVisibility(8);
        this.dialogListView.setVisibility(0);
        this.browseAdapter.notifyDataSetChanged();
        return;
      }
      BrowseBo localBrowseBo = new BrowseBo();
      localBrowseBo.setHeader(arrayOfString1[j]);
      localBrowseBo.setValue(arrayOfString2[j]);
      this.theBrowseItems.add(localBrowseBo);
    }
  }

  public void setSortOPtions(ArrayList<ShopSortBo> paramArrayList, int paramInt)
  {
    this.fabDialog = this;
    this.fabDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousInt == 4) && (FabShopsDialog.this.fabDialog.isShowing()))
          FabShopsDialog.this.silentDismiss = true;
        return false;
      }
    });
    this.dialogprogressBar.setVisibility(8);
    this.dialogGridView.setVisibility(8);
    this.dialogListView.setVisibility(0);
    this.dialogTitle.setText("Sort");
    ((RelativeLayout.LayoutParams)this.dialogTitle.getLayoutParams()).addRule(13, 0);
    this.leftButton.setVisibility(8);
    this.rightButton.setText("Done");
    this.shopSortAdapter = new ShopSortAdapter(this.context, paramArrayList);
    this.dialogListView.setAdapter(this.shopSortAdapter);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.FabShopsDialog
 * JD-Core Version:    0.6.2
 */