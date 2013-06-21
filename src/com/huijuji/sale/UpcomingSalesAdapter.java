package com.fab.sale;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fab.sale.bo.UpcomingSalesBO;
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

public class UpcomingSalesAdapter extends FabBaseAdapter
{
  Context context;
  private AsyncTask<String, Void, Void> downloader;
  private FabHelper helper = FabHelper.getFabHelper();
  HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap();
  LayoutInflater inflator;
  ArrayList<UpcomingSalesBO> theSaleDates;

  public UpcomingSalesAdapter(Context paramContext, ArrayList<UpcomingSalesBO> paramArrayList)
  {
    this.context = paramContext;
    this.theSaleDates = paramArrayList;
    this.inflator = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
  }

  public void addSales(ArrayList<UpcomingSalesBO> paramArrayList)
  {
    this.theSaleDates.clear();
    this.theSaleDates.addAll(paramArrayList);
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
        if (UpcomingSalesActivity.isActivityAlive)
        {
          FabUtils.log("UPCOMINGSALEADAPTER", "downloadTheImages", "I");
          setAllDownloading(true);
          localArrayList = new ArrayList();
          localIterator = this.theSaleDates.iterator();
          if (localIterator.hasNext())
            continue;
          if (localArrayList.size() <= 0)
            break label166;
          String[] arrayOfString = new String[localArrayList.size()];
          if (UpcomingSalesActivity.isActivityAlive)
          {
            this.downloader = new ImageDownloader(this.context, this, false, null);
            this.downloader.execute((String[])localArrayList.toArray(arrayOfString));
          }
        }
        return;
        UpcomingSalesBO localUpcomingSalesBO = (UpcomingSalesBO)localIterator.next();
        if ((localUpcomingSalesBO.isHeader()) || (!TextUtils.isEmpty(this.helper.doesFileExists(this.context, localUpcomingSalesBO.getSaleImageUrl()))))
          continue;
        localArrayList.add(localUpcomingSalesBO.getSaleImageUrl());
        continue;
      }
      finally
      {
      }
      label166: setAllDownloading(false);
      notifyDataSetChanged();
    }
  }

  public int getCount()
  {
    return this.theSaleDates.size();
  }

  public UpcomingSalesBO getItem(int paramInt)
  {
    return (UpcomingSalesBO)this.theSaleDates.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public int getItemViewType(int paramInt)
  {
    return 0;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    int i = 0;
    Bitmap localBitmap1 = null;
    final UpcomingSalesBO localUpcomingSalesBO = (UpcomingSalesBO)this.theSaleDates.get(paramInt);
    UpcomingSaleHolder localUpcomingSaleHolder;
    int j;
    label345: int k;
    if (paramView == null)
    {
      paramView = this.inflator.inflate(2130903105, null);
      localUpcomingSaleHolder = new UpcomingSaleHolder();
      localUpcomingSaleHolder.headerLayout = ((LinearLayout)paramView.findViewById(2131362081));
      localUpcomingSaleHolder.dateSparatorView = ((FabTextView)paramView.findViewById(2131362082));
      localUpcomingSaleHolder.dateSparatorView.setFont("HelveticaNeu_light.ttf");
      localUpcomingSaleHolder.ribbonTextView = ((FabTextView)paramView.findViewById(2131362086));
      localUpcomingSaleHolder.ribbonTextView.setFont("georgiai.ttf");
      localUpcomingSaleHolder.shopName = ((FabTextView)paramView.findViewById(2131362059));
      localUpcomingSaleHolder.shopName.setFont("HelveticaNeu_light.ttf");
      localUpcomingSaleHolder.shopIcon = ((ImageView)paramView.findViewById(2131362084));
      localUpcomingSaleHolder.header = ((LinearLayout)paramView.findViewById(2131361855));
      localUpcomingSaleHolder.footer = ((LinearLayout)paramView.findViewById(2131362088));
      localUpcomingSaleHolder.bottomPad = ((TextView)paramView.findViewById(2131361822));
      localUpcomingSaleHolder.salesLayout = ((LinearLayout)paramView.findViewById(2131362087));
      localUpcomingSaleHolder.saleNameView = ((FabTextView)paramView.findViewById(2131362080));
      localUpcomingSaleHolder.saleNameView.setFont("HelveticaNeu_bold.ttf");
      localUpcomingSaleHolder.pBar = ((ProgressBar)paramView.findViewById(2131361946));
      localUpcomingSaleHolder.saleRowSelection = ((RelativeLayout)paramView.findViewById(2131362079));
      localUpcomingSaleHolder.saleDescriptionView = ((FabTextView)paramView.findViewById(2131362015));
      localUpcomingSaleHolder.saleDescriptionView.setFont("HelveticaNeu_normal.ttf");
      localUpcomingSaleHolder.productImageView = ((ImageView)paramView.findViewById(2131361850));
      localUpcomingSaleHolder.drawable = this.context.getResources().getDrawable(2130837774);
      paramView.setTag(localUpcomingSaleHolder);
      LinearLayout localLinearLayout1 = localUpcomingSaleHolder.footer;
      if (paramInt != -1 + this.theSaleDates.size())
        break label512;
      j = i;
      localLinearLayout1.setVisibility(j);
      TextView localTextView = localUpcomingSaleHolder.bottomPad;
      if (!localUpcomingSalesBO.isLastProduct())
        break label519;
      k = i;
      label371: localTextView.setVisibility(k);
      if (!localUpcomingSalesBO.isHeader())
        break label533;
      localUpcomingSaleHolder.headerLayout.setVisibility(i);
      localUpcomingSaleHolder.salesLayout.setVisibility(8);
      localUpcomingSaleHolder.dateSparatorView.setVisibility(i);
      localUpcomingSaleHolder.dateSparatorView.setText(localUpcomingSalesBO.getDateSeparator());
      localUpcomingSaleHolder.shopName.setText(localUpcomingSalesBO.getSaleTypeHeader());
      if (localUpcomingSalesBO.getSaleIconResId() > 0)
        localUpcomingSaleHolder.shopIcon.setImageResource(localUpcomingSalesBO.getSaleIconResId());
      localUpcomingSaleHolder.ribbonTextView.setText(localUpcomingSalesBO.getRibbonText());
      LinearLayout localLinearLayout3 = localUpcomingSaleHolder.header;
      if (!localUpcomingSalesBO.isHeader())
        break label526;
      label491: localLinearLayout3.setVisibility(i);
    }
    while (true)
    {
      return paramView;
      localUpcomingSaleHolder = (UpcomingSaleHolder)paramView.getTag();
      break;
      label512: j = 8;
      break label345;
      label519: k = 8;
      break label371;
      label526: i = 8;
      break label491;
      label533: if (localUpcomingSalesBO.isSubHeader())
      {
        localUpcomingSaleHolder.headerLayout.setVisibility(i);
        localUpcomingSaleHolder.salesLayout.setVisibility(8);
        localUpcomingSaleHolder.dateSparatorView.setVisibility(8);
        localUpcomingSaleHolder.shopName.setText(localUpcomingSalesBO.getSaleTypeHeader());
        if (localUpcomingSalesBO.getSaleIconResId() > 0)
          localUpcomingSaleHolder.shopIcon.setImageResource(localUpcomingSalesBO.getSaleIconResId());
        localUpcomingSaleHolder.ribbonTextView.setText(localUpcomingSalesBO.getRibbonText());
        LinearLayout localLinearLayout2 = localUpcomingSaleHolder.header;
        if (localUpcomingSalesBO.isHeader());
        while (true)
        {
          localLinearLayout2.setVisibility(i);
          break;
          i = 8;
        }
      }
      localUpcomingSaleHolder.headerLayout.setVisibility(8);
      localUpcomingSaleHolder.salesLayout.setVisibility(i);
      localUpcomingSaleHolder.saleNameView.setText(localUpcomingSalesBO.getPrimaryTitle());
      localUpcomingSaleHolder.saleRowSelection.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Intent localIntent = new Intent(UpcomingSalesAdapter.this.context, SaleDetailsActivity.class).putExtra("SALE_ID", localUpcomingSalesBO.getSaleId()).putExtra("SALE_CATEGORY", "upcoming").putExtra("SALE_NAME", localUpcomingSalesBO.getPrimaryTitle());
          UpcomingSalesAdapter.this.context.startActivity(localIntent);
        }
      });
      localUpcomingSaleHolder.saleRowSelection.bringToFront();
      localUpcomingSaleHolder.saleDescriptionView.setText(localUpcomingSalesBO.getSaleName());
      localUpcomingSaleHolder.productImageView.setImageDrawable(localUpcomingSaleHolder.drawable);
      if (this.imageCache.size() > 20)
        clearImageCache();
      localUpcomingSaleHolder.pBar.setVisibility(i);
      if (this.imageCache.get(localUpcomingSalesBO.getSaleImageUrl()) != null)
      {
        Bitmap localBitmap2 = (Bitmap)((SoftReference)this.imageCache.get(localUpcomingSalesBO.getSaleImageUrl())).get();
        if (localBitmap2 != null);
        try
        {
          if (localBitmap2.getPixel(localBitmap2.getWidth() / 2, localBitmap2.getHeight() / 2) == 0)
          {
            localBitmap2 = null;
            this.imageCache.remove(localUpcomingSalesBO.getSaleImageUrl());
          }
          if ((localBitmap2 != null) && ((localBitmap2.getHeight() != -1) || (localBitmap2.getWidth() != -1)))
          {
            localUpcomingSaleHolder.pBar.setVisibility(4);
            localUpcomingSaleHolder.productImageView.setImageBitmap(localBitmap2);
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException3)
        {
          while (true)
            localBitmap2 = null;
        }
        catch (Exception localException3)
        {
          while (true)
            localBitmap2 = null;
          String str2 = this.helper.doesFileExists(this.context, localUpcomingSalesBO.getSaleImageUrl());
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
              this.imageCache.remove(localUpcomingSalesBO.getSaleImageUrl());
            }
            if ((TextUtils.isEmpty(str2)) || (localBitmap2 == null) || (localBitmap2.getHeight() == -1) || (localBitmap2.getWidth() == -1))
            {
              localUpcomingSaleHolder.pBar.setVisibility(i);
              if ((isAllDownloading()) || (isImageDownloading()))
                continue;
              downloadTheImages();
            }
          }
          catch (IllegalArgumentException localIllegalArgumentException2)
          {
            while (true)
            {
              str2 = "";
              localBitmap2 = null;
            }
          }
          catch (Exception localException2)
          {
            while (true)
            {
              str2 = "";
              localBitmap2 = null;
            }
            localUpcomingSaleHolder.pBar.setVisibility(4);
            localUpcomingSaleHolder.productImageView.setImageBitmap(localBitmap2);
            this.imageCache.put(localUpcomingSalesBO.getSaleImageUrl(), new SoftReference(localBitmap2));
          }
        }
      }
      else
      {
        String str1 = this.helper.doesFileExists(this.context, localUpcomingSalesBO.getSaleImageUrl());
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
            localUpcomingSaleHolder.pBar.setVisibility(i);
            if ((isAllDownloading()) || (isImageDownloading()))
              continue;
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
          localUpcomingSaleHolder.pBar.setVisibility(4);
          localUpcomingSaleHolder.productImageView.setImageBitmap(localBitmap1);
          this.imageCache.put(localUpcomingSalesBO.getSaleImageUrl(), new SoftReference(localBitmap1));
        }
      }
    }
  }

  public int getViewTypeCount()
  {
    return 2;
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
    if (this.downloader != null)
      this.downloader.cancel(true);
  }

  class UpcomingSaleHolder
  {
    TextView bottomPad;
    FabTextView dateSparatorView;
    Drawable drawable;
    LinearLayout footer;
    LinearLayout header;
    LinearLayout headerLayout;
    ProgressBar pBar;
    ImageView productImageView;
    FabTextView ribbonTextView;
    FabTextView saleDescriptionView;
    FabTextView saleNameView;
    RelativeLayout saleRowSelection;
    LinearLayout salesLayout;
    ImageView shopIcon;
    FabTextView shopName;

    UpcomingSaleHolder()
    {
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.UpcomingSalesAdapter
 * JD-Core Version:    0.6.2
 */