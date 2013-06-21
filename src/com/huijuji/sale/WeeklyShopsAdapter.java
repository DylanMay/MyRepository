package com.fab.sale;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask.Status;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.fab.sale.bo.PairedWeeklyShops;
import com.fab.sale.bo.WeeklyShopsBO;
import com.fab.utils.FabHelper;
import com.fab.utils.ImageDownloader;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.ShopsView;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class WeeklyShopsAdapter extends PagerAdapter
  implements TaskCompleteListener
{
  Context context;
  private ImageDownloader downloader;
  private FabHelper helper = FabHelper.getFabHelper();
  HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap();
  private boolean isAllDownloading;
  private ArrayList<String> theImageQueues = new ArrayList();
  ArrayList<PairedWeeklyShops> thePairedWeeklyShops;

  public WeeklyShopsAdapter(Context paramContext, ArrayList<PairedWeeklyShops> paramArrayList)
  {
    this.context = paramContext;
    this.thePairedWeeklyShops = paramArrayList;
  }

  // ERROR //
  private void populateUI(ShopsView paramShopsView, WeeklyShopsBO paramWeeklyShopsBO, ProgressBar paramProgressBar, int paramInt, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_1
    //   1: aload_2
    //   2: invokevirtual 64	com/fab/views/ShopsView:setTag	(Ljava/lang/Object;)V
    //   5: aload_2
    //   6: invokevirtual 70	com/fab/sale/bo/WeeklyShopsBO:getDay	()Ljava/lang/String;
    //   9: invokestatic 76	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   12: ifne +668 -> 680
    //   15: aconst_null
    //   16: astore 6
    //   18: aload_3
    //   19: iconst_0
    //   20: invokevirtual 82	android/widget/ProgressBar:setVisibility	(I)V
    //   23: aload_0
    //   24: getfield 48	com/fab/sale/WeeklyShopsAdapter:imageCache	Ljava/util/HashMap;
    //   27: aload_2
    //   28: invokevirtual 85	com/fab/sale/bo/WeeklyShopsBO:getImgUrl	()Ljava/lang/String;
    //   31: invokevirtual 89	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   34: ifnull +443 -> 477
    //   37: aload_0
    //   38: getfield 48	com/fab/sale/WeeklyShopsAdapter:imageCache	Ljava/util/HashMap;
    //   41: aload_2
    //   42: invokevirtual 85	com/fab/sale/bo/WeeklyShopsBO:getImgUrl	()Ljava/lang/String;
    //   45: invokevirtual 89	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   48: checkcast 91	java/lang/ref/SoftReference
    //   51: invokevirtual 94	java/lang/ref/SoftReference:get	()Ljava/lang/Object;
    //   54: checkcast 96	android/graphics/Bitmap
    //   57: astore 13
    //   59: aload 13
    //   61: ifnull +40 -> 101
    //   64: aload 13
    //   66: aload 13
    //   68: invokevirtual 100	android/graphics/Bitmap:getWidth	()I
    //   71: iconst_2
    //   72: idiv
    //   73: aload 13
    //   75: invokevirtual 103	android/graphics/Bitmap:getHeight	()I
    //   78: iconst_2
    //   79: idiv
    //   80: invokevirtual 107	android/graphics/Bitmap:getPixel	(II)I
    //   83: ifne +18 -> 101
    //   86: aconst_null
    //   87: astore 13
    //   89: aload_0
    //   90: getfield 48	com/fab/sale/WeeklyShopsAdapter:imageCache	Ljava/util/HashMap;
    //   93: aload_2
    //   94: invokevirtual 85	com/fab/sale/bo/WeeklyShopsBO:getImgUrl	()Ljava/lang/String;
    //   97: invokevirtual 110	java/util/HashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   100: pop
    //   101: aload 13
    //   103: ifnull +151 -> 254
    //   106: aload 13
    //   108: invokevirtual 103	android/graphics/Bitmap:getHeight	()I
    //   111: bipush 255
    //   113: if_icmpne +13 -> 126
    //   116: aload 13
    //   118: invokevirtual 100	android/graphics/Bitmap:getWidth	()I
    //   121: bipush 255
    //   123: if_icmpeq +131 -> 254
    //   126: aload_3
    //   127: iconst_4
    //   128: invokevirtual 82	android/widget/ProgressBar:setVisibility	(I)V
    //   131: aload_1
    //   132: aload 13
    //   134: invokevirtual 114	com/fab/views/ShopsView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   137: aload_2
    //   138: invokevirtual 117	com/fab/sale/bo/WeeklyShopsBO:getId	()I
    //   141: ifne +532 -> 673
    //   144: aload_1
    //   145: invokevirtual 120	com/fab/views/ShopsView:setOverlay	()V
    //   148: aload_1
    //   149: invokevirtual 124	com/fab/views/ShopsView:getPrimaryTitle	()Lcom/fab/views/FabTextView;
    //   152: aload_2
    //   153: invokevirtual 127	com/fab/sale/bo/WeeklyShopsBO:getShopName	()Ljava/lang/String;
    //   156: invokevirtual 133	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
    //   159: aload_1
    //   160: invokevirtual 135	com/fab/views/ShopsView:getDay	()Lcom/fab/views/FabTextView;
    //   163: aload_2
    //   164: invokevirtual 70	com/fab/sale/bo/WeeklyShopsBO:getDay	()Ljava/lang/String;
    //   167: invokevirtual 133	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
    //   170: aload_1
    //   171: invokevirtual 135	com/fab/views/ShopsView:getDay	()Lcom/fab/views/FabTextView;
    //   174: iconst_0
    //   175: invokevirtual 136	com/fab/views/FabTextView:setVisibility	(I)V
    //   178: iload 4
    //   180: ifne +28 -> 208
    //   183: iload 5
    //   185: ifeq +23 -> 208
    //   188: aload_1
    //   189: invokevirtual 140	com/fab/views/ShopsView:getClock	()Landroid/widget/ImageView;
    //   192: iconst_0
    //   193: invokevirtual 143	android/widget/ImageView:setVisibility	(I)V
    //   196: aload_1
    //   197: invokevirtual 135	com/fab/views/ShopsView:getDay	()Lcom/fab/views/FabTextView;
    //   200: ldc 145
    //   202: invokestatic 151	android/graphics/Color:parseColor	(Ljava/lang/String;)I
    //   205: invokevirtual 154	com/fab/views/FabTextView:setBackgroundColor	(I)V
    //   208: aload_1
    //   209: new 8	com/fab/sale/WeeklyShopsAdapter$1
    //   212: dup
    //   213: aload_0
    //   214: invokespecial 157	com/fab/sale/WeeklyShopsAdapter$1:<init>	(Lcom/fab/sale/WeeklyShopsAdapter;)V
    //   217: invokevirtual 161	com/fab/views/ShopsView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   220: aload_1
    //   221: invokevirtual 165	com/fab/views/ShopsView:getProductContainer	()Landroid/widget/RelativeLayout;
    //   224: iconst_0
    //   225: invokevirtual 168	android/widget/RelativeLayout:setVisibility	(I)V
    //   228: aload_1
    //   229: invokevirtual 171	com/fab/views/ShopsView:getDummyProductHolder	()Landroid/widget/ImageView;
    //   232: bipush 8
    //   234: invokevirtual 143	android/widget/ImageView:setVisibility	(I)V
    //   237: return
    //   238: astore 22
    //   240: aconst_null
    //   241: astore 13
    //   243: goto -142 -> 101
    //   246: astore 21
    //   248: aconst_null
    //   249: astore 13
    //   251: goto -150 -> 101
    //   254: aload_0
    //   255: getfield 38	com/fab/sale/WeeklyShopsAdapter:helper	Lcom/fab/utils/FabHelper;
    //   258: aload_0
    //   259: getfield 50	com/fab/sale/WeeklyShopsAdapter:context	Landroid/content/Context;
    //   262: aload_2
    //   263: invokevirtual 85	com/fab/sale/bo/WeeklyShopsBO:getImgUrl	()Ljava/lang/String;
    //   266: invokevirtual 175	com/fab/utils/FabHelper:doesFileExists	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   269: astore 14
    //   271: aload 14
    //   273: invokestatic 76	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   276: ifne +56 -> 332
    //   279: aload_0
    //   280: getfield 38	com/fab/sale/WeeklyShopsAdapter:helper	Lcom/fab/utils/FabHelper;
    //   283: aload 14
    //   285: invokevirtual 179	com/fab/utils/FabHelper:decodeFileForBitmap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   288: astore 13
    //   290: aload 13
    //   292: ifnull +23 -> 315
    //   295: aload 13
    //   297: invokevirtual 103	android/graphics/Bitmap:getHeight	()I
    //   300: bipush 255
    //   302: if_icmpeq +13 -> 315
    //   305: aload 13
    //   307: invokevirtual 100	android/graphics/Bitmap:getWidth	()I
    //   310: bipush 255
    //   312: if_icmpne +20 -> 332
    //   315: new 181	java/io/File
    //   318: dup
    //   319: aload 14
    //   321: invokespecial 184	java/io/File:<init>	(Ljava/lang/String;)V
    //   324: invokevirtual 188	java/io/File:delete	()Z
    //   327: pop
    //   328: ldc 190
    //   330: astore 14
    //   332: aload 13
    //   334: ifnull +44 -> 378
    //   337: aload 13
    //   339: aload 13
    //   341: invokevirtual 100	android/graphics/Bitmap:getWidth	()I
    //   344: iconst_2
    //   345: idiv
    //   346: aload 13
    //   348: invokevirtual 103	android/graphics/Bitmap:getHeight	()I
    //   351: iconst_2
    //   352: idiv
    //   353: invokevirtual 107	android/graphics/Bitmap:getPixel	(II)I
    //   356: ifne +22 -> 378
    //   359: ldc 190
    //   361: astore 14
    //   363: aconst_null
    //   364: astore 13
    //   366: aload_0
    //   367: getfield 48	com/fab/sale/WeeklyShopsAdapter:imageCache	Ljava/util/HashMap;
    //   370: aload_2
    //   371: invokevirtual 85	com/fab/sale/bo/WeeklyShopsBO:getImgUrl	()Ljava/lang/String;
    //   374: invokevirtual 110	java/util/HashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   377: pop
    //   378: aload 14
    //   380: invokestatic 76	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   383: ifne +28 -> 411
    //   386: aload 13
    //   388: ifnull +23 -> 411
    //   391: aload 13
    //   393: invokevirtual 103	android/graphics/Bitmap:getHeight	()I
    //   396: bipush 255
    //   398: if_icmpeq +13 -> 411
    //   401: aload 13
    //   403: invokevirtual 100	android/graphics/Bitmap:getWidth	()I
    //   406: bipush 255
    //   408: if_icmpne +29 -> 437
    //   411: aload_3
    //   412: iconst_0
    //   413: invokevirtual 82	android/widget/ProgressBar:setVisibility	(I)V
    //   416: aload_0
    //   417: getfield 192	com/fab/sale/WeeklyShopsAdapter:isAllDownloading	Z
    //   420: ifne +291 -> 711
    //   423: aload_0
    //   424: invokevirtual 195	com/fab/sale/WeeklyShopsAdapter:isImageDownloading	()Z
    //   427: ifne +284 -> 711
    //   430: aload_0
    //   431: invokevirtual 198	com/fab/sale/WeeklyShopsAdapter:downloadTheImages	()V
    //   434: goto +277 -> 711
    //   437: aload_3
    //   438: iconst_4
    //   439: invokevirtual 82	android/widget/ProgressBar:setVisibility	(I)V
    //   442: aload_1
    //   443: aload 13
    //   445: invokevirtual 114	com/fab/views/ShopsView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   448: aload_0
    //   449: getfield 48	com/fab/sale/WeeklyShopsAdapter:imageCache	Ljava/util/HashMap;
    //   452: aload_2
    //   453: invokevirtual 85	com/fab/sale/bo/WeeklyShopsBO:getImgUrl	()Ljava/lang/String;
    //   456: new 91	java/lang/ref/SoftReference
    //   459: dup
    //   460: aload 13
    //   462: invokespecial 200	java/lang/ref/SoftReference:<init>	(Ljava/lang/Object;)V
    //   465: invokevirtual 204	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   468: pop
    //   469: goto +242 -> 711
    //   472: astore 15
    //   474: aload 15
    //   476: athrow
    //   477: aload_0
    //   478: getfield 38	com/fab/sale/WeeklyShopsAdapter:helper	Lcom/fab/utils/FabHelper;
    //   481: aload_0
    //   482: getfield 50	com/fab/sale/WeeklyShopsAdapter:context	Landroid/content/Context;
    //   485: aload_2
    //   486: invokevirtual 85	com/fab/sale/bo/WeeklyShopsBO:getImgUrl	()Ljava/lang/String;
    //   489: invokevirtual 175	com/fab/utils/FabHelper:doesFileExists	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   492: astore 7
    //   494: aload 7
    //   496: invokestatic 76	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   499: ifne +36 -> 535
    //   502: aload_0
    //   503: getfield 38	com/fab/sale/WeeklyShopsAdapter:helper	Lcom/fab/utils/FabHelper;
    //   506: aload 7
    //   508: invokevirtual 179	com/fab/utils/FabHelper:decodeFileForBitmap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   511: astore 6
    //   513: aload 6
    //   515: ifnonnull +20 -> 535
    //   518: new 181	java/io/File
    //   521: dup
    //   522: aload 7
    //   524: invokespecial 184	java/io/File:<init>	(Ljava/lang/String;)V
    //   527: invokevirtual 188	java/io/File:delete	()Z
    //   530: pop
    //   531: ldc 190
    //   533: astore 7
    //   535: aload 6
    //   537: ifnull +32 -> 569
    //   540: aload 6
    //   542: aload 6
    //   544: invokevirtual 100	android/graphics/Bitmap:getWidth	()I
    //   547: iconst_2
    //   548: idiv
    //   549: aload 6
    //   551: invokevirtual 103	android/graphics/Bitmap:getHeight	()I
    //   554: iconst_2
    //   555: idiv
    //   556: invokevirtual 107	android/graphics/Bitmap:getPixel	(II)I
    //   559: ifne +10 -> 569
    //   562: ldc 190
    //   564: astore 7
    //   566: aconst_null
    //   567: astore 6
    //   569: aload 7
    //   571: invokestatic 76	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   574: ifne +33 -> 607
    //   577: aload 6
    //   579: ifnull +28 -> 607
    //   582: aload 6
    //   584: ifnull +49 -> 633
    //   587: aload 6
    //   589: invokevirtual 103	android/graphics/Bitmap:getHeight	()I
    //   592: bipush 255
    //   594: if_icmpeq +13 -> 607
    //   597: aload 6
    //   599: invokevirtual 100	android/graphics/Bitmap:getWidth	()I
    //   602: bipush 255
    //   604: if_icmpne +29 -> 633
    //   607: aload_3
    //   608: iconst_0
    //   609: invokevirtual 82	android/widget/ProgressBar:setVisibility	(I)V
    //   612: aload_0
    //   613: getfield 192	com/fab/sale/WeeklyShopsAdapter:isAllDownloading	Z
    //   616: ifne +122 -> 738
    //   619: aload_0
    //   620: invokevirtual 195	com/fab/sale/WeeklyShopsAdapter:isImageDownloading	()Z
    //   623: ifne +115 -> 738
    //   626: aload_0
    //   627: invokevirtual 198	com/fab/sale/WeeklyShopsAdapter:downloadTheImages	()V
    //   630: goto +108 -> 738
    //   633: aload_3
    //   634: iconst_4
    //   635: invokevirtual 82	android/widget/ProgressBar:setVisibility	(I)V
    //   638: aload_1
    //   639: aload 6
    //   641: invokevirtual 114	com/fab/views/ShopsView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   644: aload_0
    //   645: getfield 48	com/fab/sale/WeeklyShopsAdapter:imageCache	Ljava/util/HashMap;
    //   648: aload_2
    //   649: invokevirtual 85	com/fab/sale/bo/WeeklyShopsBO:getImgUrl	()Ljava/lang/String;
    //   652: new 91	java/lang/ref/SoftReference
    //   655: dup
    //   656: aload 6
    //   658: invokespecial 200	java/lang/ref/SoftReference:<init>	(Ljava/lang/Object;)V
    //   661: invokevirtual 204	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   664: pop
    //   665: goto +73 -> 738
    //   668: astore 8
    //   670: aload 8
    //   672: athrow
    //   673: aload_1
    //   674: invokevirtual 207	com/fab/views/ShopsView:removeOverlay	()V
    //   677: goto -529 -> 148
    //   680: aload_1
    //   681: aconst_null
    //   682: invokevirtual 161	com/fab/views/ShopsView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   685: aload_1
    //   686: invokevirtual 165	com/fab/views/ShopsView:getProductContainer	()Landroid/widget/RelativeLayout;
    //   689: bipush 8
    //   691: invokevirtual 168	android/widget/RelativeLayout:setVisibility	(I)V
    //   694: aload_1
    //   695: invokevirtual 171	com/fab/views/ShopsView:getDummyProductHolder	()Landroid/widget/ImageView;
    //   698: iconst_0
    //   699: invokevirtual 143	android/widget/ImageView:setVisibility	(I)V
    //   702: aload_3
    //   703: bipush 8
    //   705: invokevirtual 82	android/widget/ProgressBar:setVisibility	(I)V
    //   708: goto -471 -> 237
    //   711: goto -574 -> 137
    //   714: astore 18
    //   716: ldc 190
    //   718: astore 14
    //   720: aconst_null
    //   721: astore 13
    //   723: goto -345 -> 378
    //   726: astore 17
    //   728: ldc 190
    //   730: astore 14
    //   732: aconst_null
    //   733: astore 13
    //   735: goto -357 -> 378
    //   738: goto -601 -> 137
    //   741: astore 11
    //   743: ldc 190
    //   745: astore 7
    //   747: aconst_null
    //   748: astore 6
    //   750: goto -181 -> 569
    //   753: astore 10
    //   755: ldc 190
    //   757: astore 7
    //   759: aconst_null
    //   760: astore 6
    //   762: goto -193 -> 569
    //
    // Exception table:
    //   from	to	target	type
    //   64	101	238	java/lang/IllegalArgumentException
    //   64	101	246	java/lang/Exception
    //   271	332	472	finally
    //   337	378	472	finally
    //   378	469	472	finally
    //   494	535	668	finally
    //   540	566	668	finally
    //   569	665	668	finally
    //   337	378	714	java/lang/IllegalArgumentException
    //   337	378	726	java/lang/Exception
    //   540	566	741	java/lang/IllegalArgumentException
    //   540	566	753	java/lang/Exception
  }

  private void queueTheImages()
  {
    synchronized (this.theImageQueues)
    {
      this.theImageQueues.clear();
      Iterator localIterator = this.thePairedWeeklyShops.iterator();
      WeeklyShopsBO localWeeklyShopsBO;
      do
      {
        if (!localIterator.hasNext())
          return;
        PairedWeeklyShops localPairedWeeklyShops = (PairedWeeklyShops)localIterator.next();
        localWeeklyShopsBO = localPairedWeeklyShops.getFirstShop();
        if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localWeeklyShopsBO.getImgUrl())))
          this.theImageQueues.add(localWeeklyShopsBO.getImgUrl());
        localPairedWeeklyShops.getSecondShop();
      }
      while (!TextUtils.isEmpty(this.helper.doesFileExists(this.context, localWeeklyShopsBO.getImgUrl())));
      this.theImageQueues.add(localWeeklyShopsBO.getImgUrl());
    }
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

  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    ((ViewPager)paramViewGroup).removeView((LinearLayout)paramObject);
  }

  /** @deprecated */
  public void downloadTheImages()
  {
    while (true)
    {
      ArrayList localArrayList;
      Iterator localIterator;
      try
      {
        if (ShopsActivity.isActivityAlive)
        {
          if ((this.isAllDownloading) && (isImageDownloading()))
            queueTheImages();
        }
        else
          return;
        this.isAllDownloading = true;
        localArrayList = new ArrayList();
        if ((this.theImageQueues == null) || (!this.theImageQueues.isEmpty()))
          break label252;
        localIterator = this.thePairedWeeklyShops.iterator();
        if (!localIterator.hasNext())
        {
          if (localArrayList.size() <= 0)
            break label264;
          String[] arrayOfString = new String[localArrayList.size()];
          if (ShopsActivity.isActivityAlive)
          {
            this.downloader = new ImageDownloader(this.context, this, false, null, 0);
            this.downloader.setTaskCompleteListener(this);
            this.downloader.execute((String[])localArrayList.toArray(arrayOfString));
          }
          this.theImageQueues.clear();
          continue;
        }
      }
      finally
      {
      }
      PairedWeeklyShops localPairedWeeklyShops = (PairedWeeklyShops)localIterator.next();
      WeeklyShopsBO localWeeklyShopsBO1 = localPairedWeeklyShops.getFirstShop();
      if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localWeeklyShopsBO1.getImgUrl())))
        localArrayList.add(localWeeklyShopsBO1.getImgUrl());
      WeeklyShopsBO localWeeklyShopsBO2 = localPairedWeeklyShops.getSecondShop();
      if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localWeeklyShopsBO2.getImgUrl())))
      {
        localArrayList.add(localWeeklyShopsBO2.getImgUrl());
        continue;
        label252: localArrayList.addAll(this.theImageQueues);
        continue;
        label264: this.isAllDownloading = false;
        notifyDataSetChanged();
      }
    }
  }

  public void finishUpdate(ViewGroup paramViewGroup)
  {
  }

  public int getCount()
  {
    return this.thePairedWeeklyShops.size();
  }

  public int getItemPosition(Object paramObject)
  {
    return -2;
  }

  public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
  {
    View localView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903110, null);
    ShopsView localShopsView1 = (ShopsView)localView.findViewById(2131362064);
    ProgressBar localProgressBar1 = (ProgressBar)localView.findViewById(2131361858);
    ShopsView localShopsView2 = (ShopsView)localView.findViewById(2131362065);
    ProgressBar localProgressBar2 = (ProgressBar)localView.findViewById(2131361860);
    PairedWeeklyShops localPairedWeeklyShops = (PairedWeeklyShops)this.thePairedWeeklyShops.get(paramInt);
    populateUI(localShopsView1, localPairedWeeklyShops.getFirstShop(), localProgressBar1, paramInt, true);
    populateUI(localShopsView2, localPairedWeeklyShops.getSecondShop(), localProgressBar2, paramInt, false);
    ((ViewPager)paramViewGroup).addView(localView);
    return localView;
  }

  public boolean isImageDownloading()
  {
    boolean bool = false;
    if ((this.downloader != null) && (this.downloader.getStatus() == AsyncTask.Status.RUNNING))
      bool = true;
    return bool;
  }

  public boolean isViewFromObject(View paramView, Object paramObject)
  {
    if (paramView == paramObject);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public void onTaskComplete()
  {
    this.downloader = null;
    if (!this.theImageQueues.isEmpty())
      downloadTheImages();
    notifyDataSetChanged();
  }

  public void onTaskComplete(Object paramObject)
  {
  }

  public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader)
  {
  }

  public Parcelable saveState()
  {
    return null;
  }

  public void setAllDownloading(boolean paramBoolean)
  {
    this.isAllDownloading = paramBoolean;
  }

  public void startUpdate(View paramView)
  {
  }

  public void stopImageDownloads()
  {
    if (this.downloader != null)
      this.downloader.cancel(true);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.WeeklyShopsAdapter
 * JD-Core Version:    0.6.2
 */