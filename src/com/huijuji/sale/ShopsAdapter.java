package com.fab.sale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask.Status;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fab.sale.bo.PairedShops;
import com.fab.sale.bo.ShopsBO;
import com.fab.utils.FabBaseAdapter;
import com.fab.utils.FabHelper;
import com.fab.utils.ImageDownloader;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.FabTextView;
import com.fab.views.ShopsView;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ShopsAdapter extends FabBaseAdapter
  implements TaskCompleteListener
{
  Context context;
  private ImageDownloader downloader;
  private FabHelper helper = FabHelper.getFabHelper();
  HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap();
  private ArrayList<String> theImageQueues = new ArrayList();
  ArrayList<PairedShops> thePairedShops;

  public ShopsAdapter(Context paramContext, ArrayList<PairedShops> paramArrayList)
  {
    this.context = paramContext;
    this.thePairedShops = paramArrayList;
  }

  // ERROR //
  private void populateUI(ShopsView paramShopsView, ShopsBO paramShopsBO, ProgressBar paramProgressBar)
  {
    // Byte code:
    //   0: aload_1
    //   1: aload_2
    //   2: invokevirtual 71	com/fab/views/ShopsView:setTag	(Ljava/lang/Object;)V
    //   5: aload_1
    //   6: new 73	android/graphics/drawable/BitmapDrawable
    //   9: dup
    //   10: aload_0
    //   11: getfield 51	com/fab/sale/ShopsAdapter:context	Landroid/content/Context;
    //   14: invokevirtual 79	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   17: invokespecial 82	android/graphics/drawable/BitmapDrawable:<init>	(Landroid/content/res/Resources;)V
    //   20: invokevirtual 86	com/fab/views/ShopsView:setProdImage	(Landroid/graphics/drawable/Drawable;)V
    //   23: aload_2
    //   24: invokevirtual 92	com/fab/sale/bo/ShopsBO:getId	()I
    //   27: ifeq +658 -> 685
    //   30: aload_3
    //   31: iconst_0
    //   32: invokevirtual 98	android/widget/ProgressBar:setVisibility	(I)V
    //   35: aload_0
    //   36: getfield 49	com/fab/sale/ShopsAdapter:imageCache	Ljava/util/HashMap;
    //   39: invokevirtual 101	java/util/HashMap:size	()I
    //   42: bipush 20
    //   44: if_icmple +7 -> 51
    //   47: aload_0
    //   48: invokevirtual 104	com/fab/sale/ShopsAdapter:clearImageCache	()V
    //   51: aconst_null
    //   52: astore 4
    //   54: aload_0
    //   55: getfield 49	com/fab/sale/ShopsAdapter:imageCache	Ljava/util/HashMap;
    //   58: aload_2
    //   59: invokevirtual 108	com/fab/sale/bo/ShopsBO:getImgUrl	()Ljava/lang/String;
    //   62: invokevirtual 112	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   65: ifnull +412 -> 477
    //   68: aload_0
    //   69: getfield 49	com/fab/sale/ShopsAdapter:imageCache	Ljava/util/HashMap;
    //   72: aload_2
    //   73: invokevirtual 108	com/fab/sale/bo/ShopsBO:getImgUrl	()Ljava/lang/String;
    //   76: invokevirtual 112	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   79: checkcast 114	java/lang/ref/SoftReference
    //   82: invokevirtual 117	java/lang/ref/SoftReference:get	()Ljava/lang/Object;
    //   85: checkcast 119	android/graphics/Bitmap
    //   88: astore 11
    //   90: aload 11
    //   92: ifnull +40 -> 132
    //   95: aload 11
    //   97: aload 11
    //   99: invokevirtual 122	android/graphics/Bitmap:getWidth	()I
    //   102: iconst_2
    //   103: idiv
    //   104: aload 11
    //   106: invokevirtual 125	android/graphics/Bitmap:getHeight	()I
    //   109: iconst_2
    //   110: idiv
    //   111: invokevirtual 129	android/graphics/Bitmap:getPixel	(II)I
    //   114: ifne +18 -> 132
    //   117: aconst_null
    //   118: astore 11
    //   120: aload_0
    //   121: getfield 49	com/fab/sale/ShopsAdapter:imageCache	Ljava/util/HashMap;
    //   124: aload_2
    //   125: invokevirtual 108	com/fab/sale/bo/ShopsBO:getImgUrl	()Ljava/lang/String;
    //   128: invokevirtual 132	java/util/HashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   131: pop
    //   132: aload 11
    //   134: ifnull +120 -> 254
    //   137: aload 11
    //   139: invokevirtual 125	android/graphics/Bitmap:getHeight	()I
    //   142: bipush 255
    //   144: if_icmpne +13 -> 157
    //   147: aload 11
    //   149: invokevirtual 122	android/graphics/Bitmap:getWidth	()I
    //   152: bipush 255
    //   154: if_icmpeq +100 -> 254
    //   157: aload_3
    //   158: iconst_4
    //   159: invokevirtual 98	android/widget/ProgressBar:setVisibility	(I)V
    //   162: aload_1
    //   163: aload 11
    //   165: invokevirtual 135	com/fab/views/ShopsView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   168: aload_1
    //   169: invokevirtual 139	com/fab/views/ShopsView:getPrimaryTitle	()Lcom/fab/views/FabTextView;
    //   172: aload_2
    //   173: invokevirtual 141	com/fab/sale/bo/ShopsBO:getPrimaryTitle	()Ljava/lang/String;
    //   176: invokevirtual 147	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
    //   179: aload_1
    //   180: new 8	com/fab/sale/ShopsAdapter$1
    //   183: dup
    //   184: aload_0
    //   185: invokespecial 150	com/fab/sale/ShopsAdapter$1:<init>	(Lcom/fab/sale/ShopsAdapter;)V
    //   188: invokevirtual 154	com/fab/views/ShopsView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   191: aload_2
    //   192: invokevirtual 157	com/fab/sale/bo/ShopsBO:getRibbonText	()Ljava/lang/String;
    //   195: invokestatic 163	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   198: ifne +475 -> 673
    //   201: aload_1
    //   202: invokevirtual 165	com/fab/views/ShopsView:getRibbonText	()Lcom/fab/views/FabTextView;
    //   205: aload_2
    //   206: invokevirtual 157	com/fab/sale/bo/ShopsBO:getRibbonText	()Ljava/lang/String;
    //   209: invokevirtual 147	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
    //   212: aload_1
    //   213: invokevirtual 165	com/fab/views/ShopsView:getRibbonText	()Lcom/fab/views/FabTextView;
    //   216: iconst_0
    //   217: invokevirtual 166	com/fab/views/FabTextView:setVisibility	(I)V
    //   220: aload_1
    //   221: invokevirtual 170	com/fab/views/ShopsView:getProductContainer	()Landroid/widget/RelativeLayout;
    //   224: iconst_0
    //   225: invokevirtual 173	android/widget/RelativeLayout:setVisibility	(I)V
    //   228: aload_1
    //   229: invokevirtual 177	com/fab/views/ShopsView:getDummyProductHolder	()Landroid/widget/ImageView;
    //   232: bipush 8
    //   234: invokevirtual 180	android/widget/ImageView:setVisibility	(I)V
    //   237: return
    //   238: astore 20
    //   240: aconst_null
    //   241: astore 11
    //   243: goto -111 -> 132
    //   246: astore 19
    //   248: aconst_null
    //   249: astore 11
    //   251: goto -119 -> 132
    //   254: aload_0
    //   255: getfield 39	com/fab/sale/ShopsAdapter:helper	Lcom/fab/utils/FabHelper;
    //   258: aload_0
    //   259: getfield 51	com/fab/sale/ShopsAdapter:context	Landroid/content/Context;
    //   262: aload_2
    //   263: invokevirtual 108	com/fab/sale/bo/ShopsBO:getImgUrl	()Ljava/lang/String;
    //   266: invokevirtual 184	com/fab/utils/FabHelper:doesFileExists	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   269: astore 12
    //   271: aload 12
    //   273: invokestatic 163	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   276: ifne +56 -> 332
    //   279: aload_0
    //   280: getfield 39	com/fab/sale/ShopsAdapter:helper	Lcom/fab/utils/FabHelper;
    //   283: aload 12
    //   285: invokevirtual 188	com/fab/utils/FabHelper:decodeFileForBitmap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   288: astore 11
    //   290: aload 11
    //   292: ifnull +23 -> 315
    //   295: aload 11
    //   297: invokevirtual 125	android/graphics/Bitmap:getHeight	()I
    //   300: bipush 255
    //   302: if_icmpeq +13 -> 315
    //   305: aload 11
    //   307: invokevirtual 122	android/graphics/Bitmap:getWidth	()I
    //   310: bipush 255
    //   312: if_icmpne +20 -> 332
    //   315: new 190	java/io/File
    //   318: dup
    //   319: aload 12
    //   321: invokespecial 193	java/io/File:<init>	(Ljava/lang/String;)V
    //   324: invokevirtual 197	java/io/File:delete	()Z
    //   327: pop
    //   328: ldc 199
    //   330: astore 12
    //   332: aload 11
    //   334: ifnull +44 -> 378
    //   337: aload 11
    //   339: aload 11
    //   341: invokevirtual 122	android/graphics/Bitmap:getWidth	()I
    //   344: iconst_2
    //   345: idiv
    //   346: aload 11
    //   348: invokevirtual 125	android/graphics/Bitmap:getHeight	()I
    //   351: iconst_2
    //   352: idiv
    //   353: invokevirtual 129	android/graphics/Bitmap:getPixel	(II)I
    //   356: ifne +22 -> 378
    //   359: ldc 199
    //   361: astore 12
    //   363: aconst_null
    //   364: astore 11
    //   366: aload_0
    //   367: getfield 49	com/fab/sale/ShopsAdapter:imageCache	Ljava/util/HashMap;
    //   370: aload_2
    //   371: invokevirtual 108	com/fab/sale/bo/ShopsBO:getImgUrl	()Ljava/lang/String;
    //   374: invokevirtual 132	java/util/HashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   377: pop
    //   378: aload 12
    //   380: invokestatic 163	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   383: ifne +28 -> 411
    //   386: aload 11
    //   388: ifnull +23 -> 411
    //   391: aload 11
    //   393: invokevirtual 125	android/graphics/Bitmap:getHeight	()I
    //   396: bipush 255
    //   398: if_icmpeq +13 -> 411
    //   401: aload 11
    //   403: invokevirtual 122	android/graphics/Bitmap:getWidth	()I
    //   406: bipush 255
    //   408: if_icmpne +29 -> 437
    //   411: aload_3
    //   412: iconst_0
    //   413: invokevirtual 98	android/widget/ProgressBar:setVisibility	(I)V
    //   416: aload_0
    //   417: invokevirtual 202	com/fab/sale/ShopsAdapter:isAllDownloading	()Z
    //   420: ifne +296 -> 716
    //   423: aload_0
    //   424: invokevirtual 205	com/fab/sale/ShopsAdapter:isImageDownloading	()Z
    //   427: ifne +289 -> 716
    //   430: aload_0
    //   431: invokevirtual 208	com/fab/sale/ShopsAdapter:downloadTheImages	()V
    //   434: goto +282 -> 716
    //   437: aload_3
    //   438: iconst_4
    //   439: invokevirtual 98	android/widget/ProgressBar:setVisibility	(I)V
    //   442: aload_1
    //   443: aload 11
    //   445: invokevirtual 135	com/fab/views/ShopsView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   448: aload_0
    //   449: getfield 49	com/fab/sale/ShopsAdapter:imageCache	Ljava/util/HashMap;
    //   452: aload_2
    //   453: invokevirtual 108	com/fab/sale/bo/ShopsBO:getImgUrl	()Ljava/lang/String;
    //   456: new 114	java/lang/ref/SoftReference
    //   459: dup
    //   460: aload 11
    //   462: invokespecial 210	java/lang/ref/SoftReference:<init>	(Ljava/lang/Object;)V
    //   465: invokevirtual 214	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   468: pop
    //   469: goto +247 -> 716
    //   472: astore 13
    //   474: aload 13
    //   476: athrow
    //   477: aload_0
    //   478: getfield 39	com/fab/sale/ShopsAdapter:helper	Lcom/fab/utils/FabHelper;
    //   481: aload_0
    //   482: getfield 51	com/fab/sale/ShopsAdapter:context	Landroid/content/Context;
    //   485: aload_2
    //   486: invokevirtual 108	com/fab/sale/bo/ShopsBO:getImgUrl	()Ljava/lang/String;
    //   489: invokevirtual 184	com/fab/utils/FabHelper:doesFileExists	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   492: astore 5
    //   494: aload 5
    //   496: invokestatic 163	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   499: ifne +36 -> 535
    //   502: aload_0
    //   503: getfield 39	com/fab/sale/ShopsAdapter:helper	Lcom/fab/utils/FabHelper;
    //   506: aload 5
    //   508: invokevirtual 188	com/fab/utils/FabHelper:decodeFileForBitmap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   511: astore 4
    //   513: aload 4
    //   515: ifnonnull +20 -> 535
    //   518: new 190	java/io/File
    //   521: dup
    //   522: aload 5
    //   524: invokespecial 193	java/io/File:<init>	(Ljava/lang/String;)V
    //   527: invokevirtual 197	java/io/File:delete	()Z
    //   530: pop
    //   531: ldc 199
    //   533: astore 5
    //   535: aload 4
    //   537: ifnull +32 -> 569
    //   540: aload 4
    //   542: aload 4
    //   544: invokevirtual 122	android/graphics/Bitmap:getWidth	()I
    //   547: iconst_2
    //   548: idiv
    //   549: aload 4
    //   551: invokevirtual 125	android/graphics/Bitmap:getHeight	()I
    //   554: iconst_2
    //   555: idiv
    //   556: invokevirtual 129	android/graphics/Bitmap:getPixel	(II)I
    //   559: ifne +10 -> 569
    //   562: ldc 199
    //   564: astore 5
    //   566: aconst_null
    //   567: astore 4
    //   569: aload 5
    //   571: invokestatic 163	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   574: ifne +33 -> 607
    //   577: aload 4
    //   579: ifnull +28 -> 607
    //   582: aload 4
    //   584: ifnull +49 -> 633
    //   587: aload 4
    //   589: invokevirtual 125	android/graphics/Bitmap:getHeight	()I
    //   592: bipush 255
    //   594: if_icmpeq +13 -> 607
    //   597: aload 4
    //   599: invokevirtual 122	android/graphics/Bitmap:getWidth	()I
    //   602: bipush 255
    //   604: if_icmpne +29 -> 633
    //   607: aload_3
    //   608: iconst_0
    //   609: invokevirtual 98	android/widget/ProgressBar:setVisibility	(I)V
    //   612: aload_0
    //   613: invokevirtual 202	com/fab/sale/ShopsAdapter:isAllDownloading	()Z
    //   616: ifne +127 -> 743
    //   619: aload_0
    //   620: invokevirtual 205	com/fab/sale/ShopsAdapter:isImageDownloading	()Z
    //   623: ifne +120 -> 743
    //   626: aload_0
    //   627: invokevirtual 208	com/fab/sale/ShopsAdapter:downloadTheImages	()V
    //   630: goto +113 -> 743
    //   633: aload_3
    //   634: iconst_4
    //   635: invokevirtual 98	android/widget/ProgressBar:setVisibility	(I)V
    //   638: aload_1
    //   639: aload 4
    //   641: invokevirtual 135	com/fab/views/ShopsView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   644: aload_0
    //   645: getfield 49	com/fab/sale/ShopsAdapter:imageCache	Ljava/util/HashMap;
    //   648: aload_2
    //   649: invokevirtual 108	com/fab/sale/bo/ShopsBO:getImgUrl	()Ljava/lang/String;
    //   652: new 114	java/lang/ref/SoftReference
    //   655: dup
    //   656: aload 4
    //   658: invokespecial 210	java/lang/ref/SoftReference:<init>	(Ljava/lang/Object;)V
    //   661: invokevirtual 214	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   664: pop
    //   665: goto +78 -> 743
    //   668: astore 6
    //   670: aload 6
    //   672: athrow
    //   673: aload_1
    //   674: invokevirtual 165	com/fab/views/ShopsView:getRibbonText	()Lcom/fab/views/FabTextView;
    //   677: bipush 8
    //   679: invokevirtual 166	com/fab/views/FabTextView:setVisibility	(I)V
    //   682: goto -462 -> 220
    //   685: aload_1
    //   686: aconst_null
    //   687: invokevirtual 154	com/fab/views/ShopsView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   690: aload_1
    //   691: invokevirtual 170	com/fab/views/ShopsView:getProductContainer	()Landroid/widget/RelativeLayout;
    //   694: bipush 8
    //   696: invokevirtual 173	android/widget/RelativeLayout:setVisibility	(I)V
    //   699: aload_1
    //   700: invokevirtual 177	com/fab/views/ShopsView:getDummyProductHolder	()Landroid/widget/ImageView;
    //   703: iconst_0
    //   704: invokevirtual 180	android/widget/ImageView:setVisibility	(I)V
    //   707: aload_3
    //   708: bipush 8
    //   710: invokevirtual 98	android/widget/ProgressBar:setVisibility	(I)V
    //   713: goto -476 -> 237
    //   716: goto -548 -> 168
    //   719: astore 16
    //   721: ldc 199
    //   723: astore 12
    //   725: aconst_null
    //   726: astore 11
    //   728: goto -350 -> 378
    //   731: astore 15
    //   733: ldc 199
    //   735: astore 12
    //   737: aconst_null
    //   738: astore 11
    //   740: goto -362 -> 378
    //   743: goto -575 -> 168
    //   746: astore 9
    //   748: ldc 199
    //   750: astore 5
    //   752: aconst_null
    //   753: astore 4
    //   755: goto -186 -> 569
    //   758: astore 8
    //   760: ldc 199
    //   762: astore 5
    //   764: aconst_null
    //   765: astore 4
    //   767: goto -198 -> 569
    //
    // Exception table:
    //   from	to	target	type
    //   95	132	238	java/lang/IllegalArgumentException
    //   95	132	246	java/lang/Exception
    //   271	332	472	finally
    //   337	378	472	finally
    //   378	469	472	finally
    //   494	535	668	finally
    //   540	566	668	finally
    //   569	665	668	finally
    //   337	378	719	java/lang/IllegalArgumentException
    //   337	378	731	java/lang/Exception
    //   540	566	746	java/lang/IllegalArgumentException
    //   540	566	758	java/lang/Exception
  }

  private void queueTheImages()
  {
    synchronized (this.theImageQueues)
    {
      this.theImageQueues.clear();
      Iterator localIterator = this.thePairedShops.iterator();
      ShopsBO localShopsBO2;
      do
      {
        if (!localIterator.hasNext())
          return;
        PairedShops localPairedShops = (PairedShops)localIterator.next();
        ShopsBO localShopsBO1 = localPairedShops.getFirstShop();
        if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localShopsBO1.getImgUrl())))
          this.theImageQueues.add(localShopsBO1.getImgUrl());
        localShopsBO2 = localPairedShops.getSecondShop();
      }
      while (!TextUtils.isEmpty(this.helper.doesFileExists(this.context, localShopsBO2.getImgUrl())));
      this.theImageQueues.add(localShopsBO2.getImgUrl());
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
          if ((isAllDownloading()) && (isImageDownloading()))
            queueTheImages();
        }
        else
          return;
        setAllDownloading(true);
        localArrayList = new ArrayList();
        if ((this.theImageQueues == null) || (!this.theImageQueues.isEmpty()))
          break label251;
        localIterator = this.thePairedShops.iterator();
        if (!localIterator.hasNext())
        {
          if (localArrayList.size() <= 0)
            break label263;
          String[] arrayOfString = new String[localArrayList.size()];
          if (ShopsActivity.isActivityAlive)
          {
            this.downloader = new ImageDownloader(this.context, this, false, null);
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
      PairedShops localPairedShops = (PairedShops)localIterator.next();
      ShopsBO localShopsBO1 = localPairedShops.getFirstShop();
      if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localShopsBO1.getImgUrl())))
        localArrayList.add(localShopsBO1.getImgUrl());
      ShopsBO localShopsBO2 = localPairedShops.getSecondShop();
      if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localShopsBO2.getImgUrl())))
      {
        localArrayList.add(localShopsBO2.getImgUrl());
        continue;
        label251: localArrayList.addAll(this.theImageQueues);
        continue;
        label263: setAllDownloading(false);
        notifyDataSetChanged();
      }
    }
  }

  public int getCount()
  {
    return this.thePairedShops.size();
  }

  public Object getItem(int paramInt)
  {
    return this.thePairedShops.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ShopsViewHolder localShopsViewHolder;
    PairedShops localPairedShops;
    if (paramView == null)
    {
      paramView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903096, null);
      localShopsViewHolder = new ShopsViewHolder(null);
      localShopsViewHolder.header = ((RelativeLayout)paramView.findViewById(2131361833));
      localShopsViewHolder.headerTitle = ((FabTextView)paramView.findViewById(2131362063));
      localShopsViewHolder.headerTitle.setFont("HelveticaNeu_bold.ttf");
      localShopsViewHolder.border = ((TextView)paramView.findViewById(2131361851));
      localShopsViewHolder.shop1 = ((ShopsView)paramView.findViewById(2131362064));
      localShopsViewHolder.pBar1 = ((ProgressBar)paramView.findViewById(2131361858));
      localShopsViewHolder.shop2 = ((ShopsView)paramView.findViewById(2131362065));
      localShopsViewHolder.pBar2 = ((ProgressBar)paramView.findViewById(2131361860));
      paramView.setTag(localShopsViewHolder);
      localPairedShops = (PairedShops)this.thePairedShops.get(paramInt);
      if (!localPairedShops.getFirstShop().isHasHeader())
        break label287;
      localShopsViewHolder.header.setVisibility(0);
      localShopsViewHolder.headerTitle.setText(Html.fromHtml(localPairedShops.getFirstShop().getHeaderName()));
      if (paramInt != 0)
        break label274;
      localShopsViewHolder.border.setVisibility(0);
    }
    while (true)
    {
      populateUI(localShopsViewHolder.shop1, localPairedShops.getFirstShop(), localShopsViewHolder.pBar1);
      populateUI(localShopsViewHolder.shop2, localPairedShops.getSecondShop(), localShopsViewHolder.pBar2);
      return paramView;
      localShopsViewHolder = (ShopsViewHolder)paramView.getTag();
      break;
      label274: localShopsViewHolder.border.setVisibility(8);
      continue;
      label287: localShopsViewHolder.header.setVisibility(8);
    }
  }

  public boolean isImageDownloading()
  {
    boolean bool = false;
    if ((this.downloader != null) && (this.downloader.getStatus() == AsyncTask.Status.RUNNING))
      bool = true;
    return bool;
  }

  public void onTaskComplete()
  {
    this.downloader = null;
    if (!this.theImageQueues.isEmpty())
      downloadTheImages();
  }

  public void onTaskComplete(Object paramObject)
  {
  }

  public void stopImageDownloads()
  {
    if (this.downloader != null)
      this.downloader.cancel(true);
  }

  private static class ShopsViewHolder
  {
    TextView border;
    RelativeLayout header;
    FabTextView headerTitle;
    ProgressBar pBar1;
    ProgressBar pBar2;
    ShopsView shop1;
    ShopsView shop2;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.ShopsAdapter
 * JD-Core Version:    0.6.2
 */