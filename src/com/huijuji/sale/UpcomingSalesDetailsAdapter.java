package com.fab.sale;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask.Status;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import com.fab.sale.bo.PairedProducts;
import com.fab.sale.bo.ProductDetailsBO;
import com.fab.utils.FabBaseAdapter;
import com.fab.utils.FabHelper;
import com.fab.utils.ImageDownloader;
import com.fab.views.UpcomingProductView;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class UpcomingSalesDetailsAdapter extends FabBaseAdapter
{
  Context context;
  private ImageDownloader downloader;
  private FabHelper helper = FabHelper.getFabHelper();
  ArrayList<PairedProducts> theProducts;

  public UpcomingSalesDetailsAdapter(Context paramContext, ArrayList<PairedProducts> paramArrayList)
  {
    this.context = paramContext;
    this.theProducts = paramArrayList;
  }

  private void populateUI(UpcomingProductView paramUpcomingProductView, ProductDetailsBO paramProductDetailsBO, ProgressBar paramProgressBar)
  {
    paramUpcomingProductView.setTag(paramProductDetailsBO);
    paramUpcomingProductView.setProdImage(this.context.getResources().getDrawable(2130837695));
    Bitmap localBitmap;
    if (paramProductDetailsBO.getName() != null)
    {
      paramProgressBar.setVisibility(0);
      String str = this.helper.doesFileExists(this.context, paramProductDetailsBO.getImageUrl());
      localBitmap = null;
      if (!TextUtils.isEmpty(str))
      {
        localBitmap = this.helper.decodeFileForBitmap(str);
        if (localBitmap == null)
        {
          new File(str).delete();
          str = "";
        }
      }
      if ((TextUtils.isEmpty(str)) || (localBitmap == null) || (localBitmap.getHeight() == -1) || (localBitmap.getWidth() == -1))
      {
        paramProgressBar.setVisibility(0);
        if ((!isAllDownloading()) && (!isImageDownloading()))
          downloadTheImages();
      }
    }
    while (true)
    {
      return;
      paramProgressBar.setVisibility(4);
      paramUpcomingProductView.setProdImage(localBitmap);
      paramUpcomingProductView.getProdImage().setScaleType(ImageView.ScaleType.FIT_CENTER);
      continue;
      paramUpcomingProductView.setProdImage(this.context.getResources().getDrawable(2130837598));
      paramUpcomingProductView.getProdImage().setScaleType(ImageView.ScaleType.CENTER);
      paramProgressBar.setVisibility(8);
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
        if (SaleDetailsActivity.isActivityAlive)
        {
          setAllDownloading(true);
          localArrayList = new ArrayList();
          localIterator = this.theProducts.iterator();
          if (localIterator.hasNext())
            continue;
          if (localArrayList.size() <= 0)
            break label195;
          String[] arrayOfString = new String[localArrayList.size()];
          if (SaleDetailsActivity.isActivityAlive)
          {
            this.downloader = new ImageDownloader(this.context, this, false, null);
            this.downloader.execute((String[])localArrayList.toArray(arrayOfString));
          }
        }
        return;
        PairedProducts localPairedProducts = (PairedProducts)localIterator.next();
        ProductDetailsBO localProductDetailsBO1 = localPairedProducts.getFirstProduct();
        if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localProductDetailsBO1.getImageUrl())))
          localArrayList.add(localProductDetailsBO1.getImageUrl());
        ProductDetailsBO localProductDetailsBO2 = localPairedProducts.getSecondProduct();
        if (!TextUtils.isEmpty(this.helper.doesFileExists(this.context, localProductDetailsBO2.getImageUrl())))
          continue;
        localArrayList.add(localProductDetailsBO2.getImageUrl());
        continue;
      }
      finally
      {
      }
      label195: setAllDownloading(false);
      notifyDataSetChanged();
    }
  }

  public int getCount()
  {
    return this.theProducts.size();
  }

  public PairedProducts getItem(int paramInt)
  {
    return (PairedProducts)this.theProducts.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
      paramView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903103, null);
    UpcomingProductView localUpcomingProductView1 = (UpcomingProductView)paramView.findViewById(2131362007);
    ProgressBar localProgressBar1 = (ProgressBar)paramView.findViewById(2131361858);
    UpcomingProductView localUpcomingProductView2 = (UpcomingProductView)paramView.findViewById(2131362008);
    ProgressBar localProgressBar2 = (ProgressBar)paramView.findViewById(2131361860);
    PairedProducts localPairedProducts = (PairedProducts)this.theProducts.get(paramInt);
    paramView.setTag(localPairedProducts);
    populateUI(localUpcomingProductView1, localPairedProducts.getFirstProduct(), localProgressBar1);
    populateUI(localUpcomingProductView2, localPairedProducts.getSecondProduct(), localProgressBar2);
    return paramView;
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
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.UpcomingSalesDetailsAdapter
 * JD-Core Version:    0.6.2
 */