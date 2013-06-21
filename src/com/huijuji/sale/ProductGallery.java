package com.fab.sale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;
import com.fab.utils.FabHelper;
import com.fab.utils.FabUtils;
import com.fab.utils.ImageDownloader;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.FabZoomableImageView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import java.util.ArrayList;
import java.util.Iterator;

public class ProductGallery extends Activity
  implements TaskCompleteListener
{
  private static final int LEFT_SWIPE = 1;
  private static final int RIGHT_SWIPE = 2;
  private static final String TAG = "ProductGallery";
  private static Gallery gallery;
  private Context context;
  private Bundle extras;
  private ProductGalleryImageAdapter galleryAdapter;
  private RelativeLayout galleryRel;
  private FabHelper helper = FabHelper.getFabHelper();
  private ViewFlipper iSwitcher;
  private ImageDownloader imageDownloader;
  private LayoutInflater inflator;
  private boolean isGalleryVisible = true;
  private ViewGroup.LayoutParams params;
  private RelativeLayout progressBar;
  private ArrayList<Integer> theImageQueues = new ArrayList();
  private ArrayList<ProductZoomBo> theZoomProd;
  private GoogleAnalyticsTracker tracker;
  private ImageDownloader zoomTask;

  private void populateImageSwitcher()
  {
    Bitmap localBitmap = null;
    int i = this.theZoomProd.size();
    Iterator localIterator = this.theZoomProd.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      ProductZoomBo localProductZoomBo = (ProductZoomBo)localIterator.next();
      if (localProductZoomBo.isPrimary() == 1)
        continue;
      try
      {
        View localView = this.inflator.inflate(2130903062, null);
        FabZoomableImageView localFabZoomableImageView = (FabZoomableImageView)localView.findViewById(2131361861);
        ((ProgressBar)localView.findViewById(2131361874));
        if (this.theZoomProd.indexOf(localProductZoomBo) == i + -1)
          localFabZoomableImageView.setLastElement(true);
        localFabZoomableImageView.setZoomImageUrl(localProductZoomBo.getZoomImageUrl());
        localFabZoomableImageView.setProductGallery(this);
        String str = this.helper.doesFileExists(this.context, localProductZoomBo.getImageUrl());
        if (!TextUtils.isEmpty(str))
          localBitmap = this.helper.decodeFileForBitmap(str);
        if ((TextUtils.isEmpty(str)) || (localBitmap == null) || (localBitmap.getHeight() == -1) || (localBitmap.getWidth() == -1))
        {
          if ((this.imageDownloader != null) && (this.imageDownloader.getStatus() != AsyncTask.Status.RUNNING))
          {
            this.imageDownloader = new ImageDownloader(this.context, null, true, null);
            ImageDownloader localImageDownloader = this.imageDownloader;
            String[] arrayOfString = new String[1];
            arrayOfString[0] = localProductZoomBo.getImageUrl();
            localImageDownloader.execute(arrayOfString);
          }
          localFabZoomableImageView.setIndex(this.theZoomProd.indexOf(localProductZoomBo));
          localFabZoomableImageView.setTag(localProductZoomBo);
          localView.setTag(localFabZoomableImageView);
          this.iSwitcher.addView(localView, this.params);
          localBitmap = null;
          continue;
        }
        localFabZoomableImageView.setBitmap(localBitmap);
      }
      finally
      {
      }
    }
  }

  private void queueTheImages(int paramInt)
  {
    synchronized (this.theImageQueues)
    {
      this.theImageQueues.add(Integer.valueOf(paramInt));
      return;
    }
  }

  public static void setGallerySelection()
  {
    gallery.setSelection(0);
  }

  private void showGallery()
  {
    this.isGalleryVisible = true;
    gallery.setVisibility(0);
  }

  public void downloadGalleryImages()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.theZoomProd.iterator();
    if (!localIterator.hasNext())
    {
      if (localArrayList.size() <= 0)
        break label138;
      String[] arrayOfString = new String[localArrayList.size()];
      this.imageDownloader = new ImageDownloader(this.context, this.galleryAdapter, false, null);
      this.imageDownloader.setTaskCompleteListener(new TaskCompleteListener()
      {
        public void onTaskComplete()
        {
          ProductGallery.this.progressBar.setVisibility(8);
          ProductGallery.this.galleryRel.setVisibility(0);
          ProductGallery.gallery.setAdapter(ProductGallery.this.galleryAdapter);
          ProductGallery.gallery.setSelection(0);
        }

        public void onTaskComplete(Object paramAnonymousObject)
        {
        }
      });
      this.imageDownloader.execute((String[])localArrayList.toArray(arrayOfString));
    }
    while (true)
    {
      return;
      ProductZoomBo localProductZoomBo = (ProductZoomBo)localIterator.next();
      if (!TextUtils.isEmpty(this.helper.doesFileExists(this.context, localProductZoomBo.getGalleryImageUrl())))
        break;
      localArrayList.add(localProductZoomBo.getGalleryImageUrl());
      break;
      label138: this.progressBar.setVisibility(8);
      this.galleryRel.setVisibility(0);
      gallery.setAdapter(this.galleryAdapter);
      gallery.setSelection(0);
    }
  }

  public void downloadTheImages()
  {
    while ((this.imageDownloader != null) && (this.imageDownloader.getStatus() != AsyncTask.Status.RUNNING));
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.theZoomProd.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        if (localArrayList.size() > 0)
        {
          String[] arrayOfString = new String[localArrayList.size()];
          this.imageDownloader = new ImageDownloader(this.context, null, false, null);
          this.imageDownloader.setTaskCompleteListener(this);
          this.imageDownloader.execute((String[])localArrayList.toArray(arrayOfString));
        }
        populateImageSwitcher();
        return;
      }
      ProductZoomBo localProductZoomBo = (ProductZoomBo)localIterator.next();
      if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localProductZoomBo.getImageUrl())))
        localArrayList.add(localProductZoomBo.getImageUrl());
    }
  }

  public void downloadZoomImage(String paramString, final int paramInt)
  {
    if (isImageDownloading())
      queueTheImages(paramInt);
    while (true)
    {
      return;
      this.zoomTask = new ImageDownloader(this.context, null, true, null);
      this.zoomTask.setTaskCompleteListener(new TaskCompleteListener()
      {
        public void onTaskComplete()
        {
          ProductGallery.this.zoomTask = null;
          FabZoomableImageView localFabZoomableImageView;
          ProductZoomBo localProductZoomBo;
          if (ProductGallery.this.iSwitcher != null)
            if ((paramInt == ProductGallery.this.iSwitcher.getDisplayedChild()) && (ProductGallery.this.iSwitcher.getChildAt(paramInt) != null))
            {
              localFabZoomableImageView = (FabZoomableImageView)ProductGallery.this.iSwitcher.getChildAt(paramInt).getTag();
              if (localFabZoomableImageView != null)
              {
                localFabZoomableImageView.setMinScale(0.4F);
                localProductZoomBo = (ProductZoomBo)localFabZoomableImageView.getTag();
                if (!localFabZoomableImageView.isZoomedImage)
                  break label297;
                localFabZoomableImageView.setBitmapNull();
                localFabZoomableImageView.setDefaultScale(0);
                Bitmap localBitmap = ProductGallery.this.helper.decodeFileForBitmap(ProductGallery.this.helper.doesFileExists(ProductGallery.this.context, localProductZoomBo.getZoomImageUrl()));
                if ((localBitmap == null) || ((localBitmap.getHeight() == -1) && (localBitmap.getWidth() == -1)))
                  break label254;
                localFabZoomableImageView.setBitmap(localBitmap);
              }
            }
          while (true)
          {
            if (!ProductGallery.this.theImageQueues.isEmpty())
            {
              ProductGallery.this.downloadZoomImage(((ProductZoomBo)ProductGallery.this.theZoomProd.get(((Integer)ProductGallery.this.theImageQueues.get(0)).intValue())).getZoomImageUrl(), ((Integer)ProductGallery.this.theImageQueues.get(0)).intValue());
              ProductGallery.this.theImageQueues.remove(0);
            }
            return;
            label254: localFabZoomableImageView.setDefaultScale(1);
            localFabZoomableImageView.setBitmap(ProductGallery.this.helper.decodeFileForBitmap(ProductGallery.this.helper.doesFileExists(ProductGallery.this.context, localProductZoomBo.getImageUrl())));
            continue;
            label297: localFabZoomableImageView.setBitmapNull();
            localFabZoomableImageView.setDefaultScale(1);
            localFabZoomableImageView.setBitmap(ProductGallery.this.helper.decodeFileForBitmap(ProductGallery.this.helper.doesFileExists(ProductGallery.this.context, localProductZoomBo.getImageUrl())));
          }
        }

        public void onTaskComplete(Object paramAnonymousObject)
        {
        }
      });
      ImageDownloader localImageDownloader = this.zoomTask;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramString;
      localImageDownloader.execute(arrayOfString);
    }
  }

  public void hideGallery()
  {
    this.isGalleryVisible = false;
    gallery.setVisibility(8);
  }

  public boolean isImageDownloading()
  {
    boolean bool = false;
    if ((this.zoomTask != null) && (this.zoomTask.getStatus() == AsyncTask.Status.RUNNING))
      bool = true;
    return bool;
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903081);
    this.context = this;
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this);
    this.tracker.trackPageView(FabUtils.gATracUrl("productGallery"));
    this.extras = getIntent().getExtras();
    if (this.extras != null)
      this.theZoomProd = this.extras.getParcelableArrayList("PRODUCTS_IMAGES");
    if (this.theZoomProd == null)
      this.theZoomProd = new ArrayList();
    this.params = new ViewGroup.LayoutParams(-1, -1);
    this.progressBar = ((RelativeLayout)findViewById(2131361874));
    this.galleryRel = ((RelativeLayout)findViewById(2131361996));
    this.iSwitcher = ((ViewFlipper)findViewById(2131361997));
    this.iSwitcher.setInAnimation(AnimationUtils.makeInAnimation(this, false));
    this.inflator = ((LayoutInflater)this.context.getSystemService("layout_inflater"));
    gallery = (Gallery)findViewById(2131361998);
    gallery.setSpacing(10);
    gallery.setSelection(0);
    gallery.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        FabZoomableImageView localFabZoomableImageView2;
        ProgressBar localProgressBar;
        ProductZoomBo localProductZoomBo;
        if ((ProductGallery.this.iSwitcher != null) && (ProductGallery.this.iSwitcher.getDisplayedChild() != paramAnonymousInt) && (ProductGallery.this.iSwitcher.getCurrentView() != null))
        {
          FabZoomableImageView localFabZoomableImageView1 = (FabZoomableImageView)ProductGallery.this.iSwitcher.getCurrentView().getTag();
          if (localFabZoomableImageView1 != null)
            localFabZoomableImageView1.setBitmapNull();
          ProductGallery.this.iSwitcher.setDisplayedChild(paramAnonymousInt);
          localFabZoomableImageView2 = (FabZoomableImageView)ProductGallery.this.iSwitcher.getCurrentView().getTag();
          localProgressBar = (ProgressBar)ProductGallery.this.iSwitcher.getCurrentView().findViewById(2131361874);
          if (localFabZoomableImageView2.isBitmapNull())
            localProgressBar.setVisibility(0);
          localProductZoomBo = (ProductZoomBo)localFabZoomableImageView2.getTag();
          if (!localFabZoomableImageView2.isZoomedImage)
            break label346;
          if (TextUtils.isEmpty(ProductGallery.this.helper.doesFileExists(ProductGallery.this.context, localProductZoomBo.getZoomImageUrl())))
            break label300;
          Bitmap localBitmap = ProductGallery.this.helper.decodeFileForBitmap(ProductGallery.this.helper.doesFileExists(ProductGallery.this.context, localProductZoomBo.getZoomImageUrl()));
          if ((localBitmap == null) || ((localBitmap.getHeight() == -1) && (localBitmap.getWidth() == -1)))
            break label254;
          localFabZoomableImageView2.setDefaultScale(0);
          localFabZoomableImageView2.setBitmap(localBitmap);
          localProgressBar.setVisibility(8);
        }
        while (true)
        {
          return;
          label254: localFabZoomableImageView2.setDefaultScale(1);
          localFabZoomableImageView2.setBitmap(ProductGallery.this.helper.decodeFileForBitmap(ProductGallery.this.helper.doesFileExists(ProductGallery.this.context, localProductZoomBo.getImageUrl())));
          break;
          label300: localFabZoomableImageView2.setDefaultScale(1);
          localFabZoomableImageView2.setBitmap(ProductGallery.this.helper.decodeFileForBitmap(ProductGallery.this.helper.doesFileExists(ProductGallery.this.context, localProductZoomBo.getImageUrl())));
          break;
          label346: if (!TextUtils.isEmpty(ProductGallery.this.helper.doesFileExists(ProductGallery.this.context, localProductZoomBo.getImageUrl())))
          {
            localFabZoomableImageView2.setDefaultScale(1);
            localFabZoomableImageView2.setBitmap(ProductGallery.this.helper.decodeFileForBitmap(ProductGallery.this.helper.doesFileExists(ProductGallery.this.context, localProductZoomBo.getImageUrl())));
            localProgressBar.setVisibility(8);
          }
          else
          {
            localProgressBar.setVisibility(0);
          }
        }
      }
    });
    this.galleryAdapter = new ProductGalleryImageAdapter(this, this.theZoomProd);
    downloadGalleryImages();
    Bitmap localBitmap = null;
    Iterator localIterator = this.theZoomProd.iterator();
    if (!localIterator.hasNext());
    while (true)
    {
      downloadTheImages();
      return;
      final ProductZoomBo localProductZoomBo = (ProductZoomBo)localIterator.next();
      if (localProductZoomBo.isPrimary() != 1)
        break;
      try
      {
        View localView = this.inflator.inflate(2130903062, null);
        final FabZoomableImageView localFabZoomableImageView = (FabZoomableImageView)localView.findViewById(2131361861);
        final ProgressBar localProgressBar = (ProgressBar)localView.findViewById(2131361874);
        localFabZoomableImageView.setFirstElement(true);
        localFabZoomableImageView.setIndex(0);
        if (this.theZoomProd.size() == 1)
          localFabZoomableImageView.setLastElement(true);
        localFabZoomableImageView.setZoomImageUrl(localProductZoomBo.getZoomImageUrl());
        localFabZoomableImageView.setProductGallery(this);
        String str = this.helper.doesFileExists(this.context, localProductZoomBo.getImageUrl());
        if (!TextUtils.isEmpty(str))
          localBitmap = this.helper.decodeFileForBitmap(str);
        if ((TextUtils.isEmpty(str)) || (localBitmap == null) || (localBitmap.getHeight() <= 0) || (localBitmap.getWidth() <= 0))
        {
          this.imageDownloader = new ImageDownloader(this.context, null, true, null);
          localProgressBar.setVisibility(0);
          this.imageDownloader.setTaskCompleteListener(new TaskCompleteListener()
          {
            public void onTaskComplete()
            {
              localFabZoomableImageView.setBitmap(ProductGallery.this.helper.decodeFileForBitmap(ProductGallery.this.helper.doesFileExists(ProductGallery.this.context, localProductZoomBo.getImageUrl())));
              localProgressBar.setVisibility(8);
            }

            public void onTaskComplete(Object paramAnonymousObject)
            {
            }
          });
          ImageDownloader localImageDownloader = this.imageDownloader;
          String[] arrayOfString = new String[1];
          arrayOfString[0] = localProductZoomBo.getImageUrl();
          localImageDownloader.execute(arrayOfString);
          localFabZoomableImageView.setTag(localProductZoomBo);
          localView.setTag(localFabZoomableImageView);
          this.iSwitcher.addView(localView, this.params);
          continue;
        }
        localFabZoomableImageView.setBitmap(localBitmap);
        localProgressBar.setVisibility(8);
      }
      finally
      {
      }
    }
  }

  protected void onDestroy()
  {
    this.tracker.stopSession();
    if (this.imageDownloader != null)
    {
      this.imageDownloader.cancel(true);
      this.imageDownloader = null;
    }
    if (this.zoomTask != null)
    {
      this.zoomTask.cancel(true);
      this.zoomTask = null;
    }
    this.iSwitcher.removeAllViews();
    System.gc();
    finish();
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      if (this.isGalleryVisible)
        break label20;
      showGallery();
    }
    label20: for (boolean bool = true; ; bool = super.onKeyDown(paramInt, paramKeyEvent))
      return bool;
  }

  public void onLowMemory()
  {
    this.iSwitcher.removeAllViews();
    finish();
    super.onLowMemory();
  }

  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    finish();
  }

  public void onTaskComplete()
  {
    this.imageDownloader = null;
  }

  public void onTaskComplete(Object paramObject)
  {
  }

  public void showNextElement(int paramInt)
  {
    System.gc();
    FabZoomableImageView localFabZoomableImageView2;
    ProgressBar localProgressBar2;
    ProductZoomBo localProductZoomBo2;
    if (paramInt == 1)
    {
      this.iSwitcher.setInAnimation(AnimationUtils.makeInAnimation(this, false));
      hideGallery();
      int j = this.iSwitcher.getDisplayedChild();
      if (j != -1 + this.iSwitcher.getChildCount())
      {
        this.iSwitcher.showNext();
        j = this.iSwitcher.getDisplayedChild();
        localFabZoomableImageView2 = (FabZoomableImageView)this.iSwitcher.getCurrentView().getTag();
        localProgressBar2 = (ProgressBar)this.iSwitcher.getCurrentView().findViewById(2131361874);
        localProductZoomBo2 = (ProductZoomBo)localFabZoomableImageView2.getTag();
        if (!localFabZoomableImageView2.isZoomedImage)
          break label297;
        if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localProductZoomBo2.getZoomImageUrl())))
          break label260;
        Bitmap localBitmap4 = this.helper.decodeFileForBitmap(this.helper.doesFileExists(this.context, localProductZoomBo2.getZoomImageUrl()));
        if ((localBitmap4 != null) && ((localBitmap4.getHeight() != -1) || (localBitmap4.getWidth() != -1)))
        {
          localFabZoomableImageView2.setDefaultScale(0);
          localFabZoomableImageView2.setBitmap(localBitmap4);
          localProgressBar2.setVisibility(8);
        }
      }
      else
      {
        label206: if (this.isGalleryVisible)
          gallery.setSelection(j, true);
      }
    }
    label260: label297: label570: label595: label756: 
    while (true)
    {
      return;
      localFabZoomableImageView2.setDefaultScale(1);
      localFabZoomableImageView2.setBitmap(this.helper.decodeFileForBitmap(this.helper.doesFileExists(this.context, localProductZoomBo2.getImageUrl())));
      break;
      localFabZoomableImageView2.setDefaultScale(1);
      localFabZoomableImageView2.setBitmap(this.helper.decodeFileForBitmap(this.helper.doesFileExists(this.context, localProductZoomBo2.getImageUrl())));
      break;
      if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localProductZoomBo2.getImageUrl())))
        break label206;
      Bitmap localBitmap3 = this.helper.decodeFileForBitmap(this.helper.doesFileExists(this.context, localProductZoomBo2.getImageUrl()));
      if ((localBitmap3 == null) || ((localBitmap3.getHeight() == -1) && (localBitmap3.getWidth() == -1)))
        break label206;
      localFabZoomableImageView2.setDefaultScale(1);
      localFabZoomableImageView2.setBitmap(localBitmap3);
      localProgressBar2.setVisibility(8);
      break label206;
      if (paramInt == 2)
      {
        this.iSwitcher.setInAnimation(AnimationUtils.makeInAnimation(this, true));
        hideGallery();
        int i = this.iSwitcher.getDisplayedChild();
        FabZoomableImageView localFabZoomableImageView1;
        ProgressBar localProgressBar1;
        ProductZoomBo localProductZoomBo1;
        if (i != 0)
        {
          this.iSwitcher.showPrevious();
          i = this.iSwitcher.getDisplayedChild();
          localFabZoomableImageView1 = (FabZoomableImageView)this.iSwitcher.getCurrentView().getTag();
          localProgressBar1 = (ProgressBar)this.iSwitcher.getCurrentView().findViewById(2131361874);
          localProductZoomBo1 = (ProductZoomBo)localFabZoomableImageView1.getTag();
          if (!localFabZoomableImageView1.isZoomedImage)
            break label665;
          if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localProductZoomBo1.getZoomImageUrl())))
            break label630;
          Bitmap localBitmap2 = this.helper.decodeFileForBitmap(this.helper.doesFileExists(this.context, localProductZoomBo1.getZoomImageUrl()));
          if ((localBitmap2 == null) || ((localBitmap2.getHeight() == -1) && (localBitmap2.getWidth() == -1)))
            break label595;
          localFabZoomableImageView1.setDefaultScale(0);
          localFabZoomableImageView1.setBitmap(localBitmap2);
          localProgressBar1.setVisibility(8);
        }
        while (true)
        {
          if (!this.isGalleryVisible)
            break label756;
          gallery.setSelection(i, true);
          break;
          localFabZoomableImageView1.setDefaultScale(1);
          localFabZoomableImageView1.setBitmap(this.helper.decodeFileForBitmap(this.helper.doesFileExists(this.context, localProductZoomBo1.getImageUrl())));
          break label570;
          label630: localFabZoomableImageView1.setDefaultScale(1);
          localFabZoomableImageView1.setBitmap(this.helper.decodeFileForBitmap(this.helper.doesFileExists(this.context, localProductZoomBo1.getImageUrl())));
          break label570;
          if (!TextUtils.isEmpty(this.helper.doesFileExists(this.context, localProductZoomBo1.getImageUrl())))
          {
            Bitmap localBitmap1 = this.helper.decodeFileForBitmap(this.helper.doesFileExists(this.context, localProductZoomBo1.getImageUrl()));
            if ((localBitmap1 != null) && ((localBitmap1.getHeight() != -1) || (localBitmap1.getWidth() != -1)))
            {
              localFabZoomableImageView1.setDefaultScale(1);
              localFabZoomableImageView1.setBitmap(localBitmap1);
              localProgressBar1.setVisibility(8);
            }
          }
        }
      }
    }
  }

  public void showOrHideGallery()
  {
    if (gallery.isShown())
    {
      this.isGalleryVisible = false;
      gallery.setVisibility(8);
    }
    while (true)
    {
      return;
      this.isGalleryVisible = true;
      gallery.setVisibility(0);
    }
  }

  public void stopImageDownloads()
  {
    if (this.zoomTask != null)
      this.zoomTask.cancel(true);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.ProductGallery
 * JD-Core Version:    0.6.2
 */