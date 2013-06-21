package com.fab.sale;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask.Status;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import com.fab.sale.bo.SalesBO;
import com.fab.utils.FabBaseAdapter;
import com.fab.utils.FabHelper;
import com.fab.utils.FabUtils;
import com.fab.utils.ImageDownloader;
import com.fab.views.FabTextView;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SalesAdapter extends FabBaseAdapter
{
  Context context;
  private ImageDownloader downloader;
  FabHelper helper = FabHelper.getFabHelper();
  private HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap();
  ArrayList<SalesBO> theSales;

  public SalesAdapter(Context paramContext, ArrayList<SalesBO> paramArrayList)
  {
    this.context = paramContext;
    this.theSales = paramArrayList;
  }

  /** @deprecated */
  public void clearImageCache()
  {
    try
    {
      this.imageCache.clear();
      System.gc();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  /** @deprecated */
  public void downloadTheImages()
  {
    while (true)
    {
      try
      {
        ArrayList localArrayList;
        Iterator localIterator;
        if ((!isImageDownloading()) && (!isAllDownloading()) && ((SalesActivity.isActivityAlive) || (WeeklyShopDetailsActivity.isActivityAlive)))
        {
          FabUtils.log("SALEADAPTER", "downloadTheImages", "I");
          setAllDownloading(true);
          localArrayList = new ArrayList();
          localIterator = this.theSales.iterator();
          if (localIterator.hasNext())
            continue;
          if (localArrayList.size() <= 0)
            break label193;
          String[] arrayOfString = new String[localArrayList.size()];
          if ((SalesActivity.isActivityAlive) || (WeeklyShopDetailsActivity.isActivityAlive))
          {
            this.downloader = new ImageDownloader(this.context, this, false, null);
            this.downloader.execute((String[])localArrayList.toArray(arrayOfString));
            FabUtils.log("SalesActivity", "Thread Started", "I");
          }
        }
        return;
        SalesBO localSalesBO = (SalesBO)localIterator.next();
        if (!TextUtils.isEmpty(this.helper.doesFileExists(this.context, localSalesBO.getSaleImageUrl())))
          continue;
        localArrayList.add(localSalesBO.getSaleImageUrl());
        continue;
      }
      finally
      {
      }
      label193: setAllDownloading(false);
      notifyDataSetChanged();
    }
  }

  public int getCount()
  {
    return this.theSales.size();
  }

  public SalesBO getItem(int paramInt)
  {
    return (SalesBO)this.theSales.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    final SalesBO localSalesBO = (SalesBO)this.theSales.get(paramInt);
    final SaleViewHolder localSaleViewHolder;
    int i;
    Bitmap localBitmap1;
    Bitmap localBitmap2;
    if (paramView == null)
    {
      paramView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903086, null);
      localSaleViewHolder = new SaleViewHolder();
      localSaleViewHolder.drawable = this.context.getResources().getDrawable(2130837726);
      localSaleViewHolder.header = ((LinearLayout)paramView.findViewById(2131361855));
      localSaleViewHolder.saleImage = ((ImageView)paramView.findViewById(2131362027));
      localSaleViewHolder.rel = ((RelativeLayout)paramView.findViewById(2131361978));
      localSaleViewHolder.pBar = ((ProgressBar)paramView.findViewById(2131361946));
      localSaleViewHolder.drawer = ((SlidingDrawer)paramView.findViewById(2131361847));
      localSaleViewHolder.drawerHandle = ((Button)localSaleViewHolder.drawer.getHandle());
      localSaleViewHolder.tvDuration = ((FabTextView)localSaleViewHolder.drawer.getContent());
      localSaleViewHolder.tvDuration.setFont("georgiai.ttf");
      localSaleViewHolder.tvDuration.setSingleLine(true);
      localSaleViewHolder.primaryTitle = ((FabTextView)paramView.findViewById(2131361852));
      localSaleViewHolder.primaryTitle.setFont("HelveticaNeu_bold.ttf");
      localSaleViewHolder.secTitle = ((FabTextView)paramView.findViewById(2131362029));
      localSaleViewHolder.secTitle.setFont("HelveticaNeu_normal.ttf");
      localSaleViewHolder.ribbonView = ((RelativeLayout)paramView.findViewById(2131361970));
      localSaleViewHolder.type = ((TextView)paramView.findViewById(2131362030));
      localSaleViewHolder.rel3 = ((RelativeLayout)paramView.findViewById(2131361846));
      localSaleViewHolder.drawerHandle.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          localSaleViewHolder.drawer.animateOpen();
        }
      });
      localSaleViewHolder.tvDuration.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          localSaleViewHolder.drawer.animateClose();
        }
      });
      paramView.setTag(localSaleViewHolder);
      localSaleViewHolder.rel.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if ("shops".equalsIgnoreCase(localSalesBO.getType()))
          {
            Intent localIntent1 = new Intent(SalesAdapter.this.context, ShopDetailsActivity.class);
            localIntent1.putExtra("SHOP_ID", localSalesBO.getSaleId());
            SalesAdapter.this.context.startActivity(localIntent1);
          }
          while (true)
          {
            return;
            if ("weekly_shop".equalsIgnoreCase(localSalesBO.getType()))
            {
              Intent localIntent2 = new Intent(SalesAdapter.this.context, WeeklyShopDetailsActivity.class);
              localIntent2.putExtra("WEEKLY_SHOP_ID", localSalesBO.getSaleId());
              SalesAdapter.this.context.startActivity(localIntent2);
            }
            else
            {
              Intent localIntent3 = new Intent(SalesAdapter.this.context, SaleDetailsActivity.class).putExtra("SALE_ID", localSalesBO.getSaleId()).putExtra("SALE_CATEGORY", "new").putExtra("SALE_NAME", localSalesBO.getPrimaryTitle());
              SalesAdapter.this.context.startActivity(localIntent3);
            }
          }
        }
      });
      localSaleViewHolder.saleImage.setImageDrawable(localSaleViewHolder.drawable);
      LinearLayout localLinearLayout = localSaleViewHolder.header;
      if (!localSalesBO.isHeader().booleanValue())
        break label686;
      i = 0;
      localLinearLayout.setVisibility(i);
      localBitmap1 = null;
      if (this.imageCache.size() > 10)
        clearImageCache();
      localSaleViewHolder.pBar.setVisibility(0);
      if (this.imageCache.get(localSalesBO.getSaleImageUrl()) == null)
        break label981;
      localBitmap2 = (Bitmap)((SoftReference)this.imageCache.get(localSalesBO.getSaleImageUrl())).get();
      if (localBitmap2 == null);
    }
    while (true)
    {
      try
      {
        if (localBitmap2.getPixel(localBitmap2.getWidth() / 2, localBitmap2.getHeight() / 2) == 0)
        {
          localBitmap2 = null;
          this.imageCache.remove(localSalesBO.getSaleImageUrl());
        }
        if ((localBitmap2 != null) && ((localBitmap2.getHeight() != -1) || (localBitmap2.getWidth() != -1)))
        {
          localSaleViewHolder.saleImage.setImageBitmap(localBitmap2);
          localSaleViewHolder.pBar.setVisibility(4);
          if (!"shops".equalsIgnoreCase(localSalesBO.getType()))
            break label1225;
          localSaleViewHolder.rel3.setVisibility(4);
          localSaleViewHolder.drawer.close();
          localSaleViewHolder.tvDuration.setText(localSalesBO.getSaleDuration());
          localSaleViewHolder.primaryTitle.setText(localSalesBO.getPrimaryTitle());
          if (TextUtils.isEmpty(localSalesBO.getSaleName()))
            break label1237;
          localSaleViewHolder.secTitle.setText(localSalesBO.getSaleName());
          localSaleViewHolder.secTitle.setVisibility(0);
          if ((TextUtils.isEmpty(localSalesBO.getRibbonText())) && (!"popup_shop".equalsIgnoreCase(localSalesBO.getType())))
            break label1250;
          localSaleViewHolder.type.setText(localSalesBO.getRibbonText());
          localSaleViewHolder.ribbonView.setVisibility(0);
          return paramView;
          localSaleViewHolder = (SaleViewHolder)paramView.getTag();
          break;
          label686: i = 8;
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException3)
      {
        localBitmap2 = null;
        continue;
      }
      catch (Exception localException3)
      {
        localBitmap2 = null;
        continue;
        String str2 = this.helper.doesFileExists(this.context, localSalesBO.getSaleImageUrl());
        if (!TextUtils.isEmpty(str2))
        {
          localBitmap2 = this.helper.decodeFileForBitmap(str2);
          if ((localBitmap2 == null) || (localBitmap2.getHeight() == -1) || (localBitmap2.getWidth() == -1))
          {
            new File(str2).delete();
            str2 = "";
          }
        }
        if (localBitmap2 != null);
        try
        {
          if (localBitmap2.getPixel(localBitmap2.getWidth() / 2, localBitmap2.getHeight() / 2) == 0)
          {
            str2 = "";
            localBitmap2 = null;
            this.imageCache.remove(localSalesBO.getSaleImageUrl());
          }
          if ((TextUtils.isEmpty(str2)) || (localBitmap2 == null) || (localBitmap2.getHeight() == -1) || (localBitmap2.getWidth() == -1))
          {
            localSaleViewHolder.pBar.setVisibility(0);
            if ((isAllDownloading()) || (isImageDownloading()))
              continue;
            FabUtils.log("SalesAdapter", "Calling image download", "I");
            downloadTheImages();
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException2)
        {
          str2 = "";
          localBitmap2 = null;
          continue;
        }
        catch (Exception localException2)
        {
          str2 = "";
          localBitmap2 = null;
          continue;
          localSaleViewHolder.pBar.setVisibility(4);
          localSaleViewHolder.saleImage.setImageBitmap(localBitmap2);
          this.imageCache.put(localSalesBO.getSaleImageUrl(), new SoftReference(localBitmap2));
        }
        continue;
      }
      label981: String str1 = this.helper.doesFileExists(this.context, localSalesBO.getSaleImageUrl());
      if (!TextUtils.isEmpty(str1))
      {
        localBitmap1 = this.helper.decodeFileForBitmap(str1);
        if (localBitmap1 == null)
        {
          new File(str1).delete();
          str1 = "";
        }
      }
      if (localBitmap1 != null);
      try
      {
        if (localBitmap1.getPixel(localBitmap1.getWidth() / 2, localBitmap1.getHeight() / 2) == 0)
        {
          str1 = "";
          localBitmap1 = null;
        }
        if ((TextUtils.isEmpty(str1)) || (localBitmap1 == null) || ((localBitmap1 != null) && ((localBitmap1.getHeight() == -1) || (localBitmap1.getWidth() == -1))))
        {
          localSaleViewHolder.pBar.setVisibility(0);
          if ((isAllDownloading()) || (isImageDownloading()))
            continue;
          FabUtils.log("SalesAdapter", "Calling image download", "I");
          downloadTheImages();
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException1)
      {
        while (true)
        {
          str1 = "";
          localBitmap1 = null;
        }
      }
      catch (Exception localException1)
      {
        while (true)
        {
          str1 = "";
          localBitmap1 = null;
        }
        localSaleViewHolder.pBar.setVisibility(4);
        localSaleViewHolder.saleImage.setImageBitmap(localBitmap1);
        this.imageCache.put(localSalesBO.getSaleImageUrl(), new SoftReference(localBitmap1));
      }
      continue;
      label1225: localSaleViewHolder.rel3.setVisibility(0);
      continue;
      label1237: localSaleViewHolder.secTitle.setVisibility(8);
      continue;
      label1250: localSaleViewHolder.ribbonView.setVisibility(8);
    }
  }

  public boolean isImageDownloading()
  {
    boolean bool = false;
    if ((this.downloader != null) && (this.downloader.getStatus() == AsyncTask.Status.RUNNING))
      bool = true;
    return bool;
  }

  public void stopImageDownloads()
  {
    if ((this.downloader != null) && (this.downloader.cancel(true)))
    {
      FabUtils.log("SalesActivity", "Thread Canceled", "I");
      this.downloader = null;
    }
  }

  static class SaleViewHolder
  {
    Drawable drawable;
    SlidingDrawer drawer;
    Button drawerHandle;
    LinearLayout header;
    ProgressBar pBar;
    FabTextView primaryTitle;
    RelativeLayout rel;
    RelativeLayout rel3;
    RelativeLayout ribbonView;
    ImageView saleImage;
    FabTextView secTitle;
    FabTextView tvDuration;
    TextView type;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.SalesAdapter
 * JD-Core Version:    0.6.2
 */