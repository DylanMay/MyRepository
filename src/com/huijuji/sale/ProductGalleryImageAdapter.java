package com.fab.sale;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask.Status;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.fab.utils.FabBaseAdapter;
import com.fab.utils.FabHelper;
import com.fab.utils.ImageDownloader;
import java.util.ArrayList;
import java.util.Iterator;

public class ProductGalleryImageAdapter extends FabBaseAdapter
{
  private Context context;
  private ImageDownloader downloader;
  private FabHelper helper = FabHelper.getFabHelper();
  int mGalleryItemBackground;
  private ArrayList<ProductZoomBo> theZoomProd;

  public ProductGalleryImageAdapter(Context paramContext, ArrayList<ProductZoomBo> paramArrayList)
  {
    this.context = paramContext;
    this.theZoomProd = paramArrayList;
  }

  public void downloadTheImages()
  {
    if (this.downloader != null)
      this.downloader.cancel(true);
    setAllDownloading(true);
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.theZoomProd.iterator();
    if (!localIterator.hasNext())
    {
      if (localArrayList.size() <= 0)
        break label141;
      String[] arrayOfString = new String[localArrayList.size()];
      this.downloader = new ImageDownloader(this.context, this, false, null);
      this.downloader.execute((String[])localArrayList.toArray(arrayOfString));
    }
    while (true)
    {
      return;
      ProductZoomBo localProductZoomBo = (ProductZoomBo)localIterator.next();
      if (!TextUtils.isEmpty(this.helper.doesFileExists(this.context, localProductZoomBo.getGalleryImageUrl())))
        break;
      localArrayList.add(localProductZoomBo.getGalleryImageUrl());
      break;
      label141: notifyDataSetChanged();
    }
  }

  public int getCount()
  {
    return this.theZoomProd.size();
  }

  public Object getItem(int paramInt)
  {
    return this.theZoomProd.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    Bitmap localBitmap = null;
    ProductZoomBo localProductZoomBo = (ProductZoomBo)this.theZoomProd.get(paramInt);
    ImageView localImageView = new ImageView(this.context);
    localImageView.setBackgroundResource(2130837612);
    localImageView.setLayoutParams(new Gallery.LayoutParams(46, 46));
    localImageView.setPadding(3, 3, 3, 3);
    localImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    localImageView.setTag(localProductZoomBo);
    String str = this.helper.doesFileExists(this.context, localProductZoomBo.getGalleryImageUrl());
    if (!TextUtils.isEmpty(str))
    {
      localBitmap = this.helper.decodeFileForBitmap(str);
      if (localBitmap == null)
      {
        ImageDownloader localImageDownloader = new ImageDownloader(this.context, this, true, null);
        String[] arrayOfString = new String[1];
        arrayOfString[0] = localProductZoomBo.getGalleryImageUrl();
        localImageDownloader.execute(arrayOfString);
      }
    }
    if ((TextUtils.isEmpty(str)) || (localBitmap == null))
      if ((!isAllDownloading()) && (!isImageDownloading()))
        downloadTheImages();
    while (true)
    {
      return localImageView;
      localImageView.setImageBitmap(localBitmap);
    }
  }

  public boolean isImageDownloading()
  {
    boolean bool = false;
    if ((this.downloader != null) && (this.downloader.getStatus() == AsyncTask.Status.RUNNING))
      bool = true;
    return bool;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.ProductGalleryImageAdapter
 * JD-Core Version:    0.6.2
 */