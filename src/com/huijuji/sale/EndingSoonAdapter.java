package com.fab.sale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask.Status;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SlidingDrawer;
import com.fab.sale.bo.PairedSales;
import com.fab.sale.bo.SalesBO;
import com.fab.utils.FabBaseAdapter;
import com.fab.utils.FabHelper;
import com.fab.utils.ImageDownloader;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.EndingSoonView;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class EndingSoonAdapter extends FabBaseAdapter
  implements TaskCompleteListener
{
  Context context;
  private ImageDownloader downloader;
  private FabHelper helper = FabHelper.getFabHelper();
  HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap();
  private ArrayList<Integer> theDrawerOpenItems = new ArrayList();
  private ArrayList<PairedSales> theEndingSales;
  private ArrayList<String> theImageQueues = new ArrayList();

  public EndingSoonAdapter(Context paramContext, ArrayList<PairedSales> paramArrayList)
  {
    this.context = paramContext;
    this.theEndingSales = paramArrayList;
  }

  // ERROR //
  private void populateUI(final EndingSoonView paramEndingSoonView, final SalesBO paramSalesBO, ProgressBar paramProgressBar, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_1
    //   1: new 77	android/graphics/drawable/BitmapDrawable
    //   4: dup
    //   5: aload_0
    //   6: getfield 59	com/fab/sale/EndingSoonAdapter:context	Landroid/content/Context;
    //   9: invokevirtual 83	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   12: invokespecial 86	android/graphics/drawable/BitmapDrawable:<init>	(Landroid/content/res/Resources;)V
    //   15: invokevirtual 92	com/fab/views/EndingSoonView:setProdImage	(Landroid/graphics/drawable/Drawable;)V
    //   18: aload_1
    //   19: invokevirtual 96	com/fab/views/EndingSoonView:getDrawer	()Landroid/widget/SlidingDrawer;
    //   22: invokevirtual 101	android/widget/SlidingDrawer:close	()V
    //   25: aload_0
    //   26: getfield 52	com/fab/sale/EndingSoonAdapter:theDrawerOpenItems	Ljava/util/ArrayList;
    //   29: aload_2
    //   30: invokevirtual 107	com/fab/sale/bo/SalesBO:getSaleId	()I
    //   33: invokestatic 113	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   36: invokevirtual 117	java/util/ArrayList:contains	(Ljava/lang/Object;)Z
    //   39: ifeq +10 -> 49
    //   42: aload_1
    //   43: invokevirtual 96	com/fab/views/EndingSoonView:getDrawer	()Landroid/widget/SlidingDrawer;
    //   46: invokevirtual 120	android/widget/SlidingDrawer:open	()V
    //   49: aload_1
    //   50: invokevirtual 124	com/fab/views/EndingSoonView:getDrawerHandle	()Landroid/widget/Button;
    //   53: new 8	com/fab/sale/EndingSoonAdapter$1
    //   56: dup
    //   57: aload_0
    //   58: aload_1
    //   59: aload_2
    //   60: invokespecial 127	com/fab/sale/EndingSoonAdapter$1:<init>	(Lcom/fab/sale/EndingSoonAdapter;Lcom/fab/views/EndingSoonView;Lcom/fab/sale/bo/SalesBO;)V
    //   63: invokevirtual 133	android/widget/Button:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   66: ldc 135
    //   68: aload_2
    //   69: invokevirtual 139	com/fab/sale/bo/SalesBO:getType	()Ljava/lang/String;
    //   72: invokevirtual 145	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   75: ifeq +267 -> 342
    //   78: aload_1
    //   79: iconst_4
    //   80: invokevirtual 149	com/fab/views/EndingSoonView:setSlidingDrawerVisibility	(I)V
    //   83: ldc 151
    //   85: aload_2
    //   86: invokevirtual 139	com/fab/sale/bo/SalesBO:getType	()Ljava/lang/String;
    //   89: invokevirtual 145	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   92: ifeq +258 -> 350
    //   95: aload_2
    //   96: invokevirtual 154	com/fab/sale/bo/SalesBO:getRibbonText	()Ljava/lang/String;
    //   99: invokestatic 160	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   102: ifne +22 -> 124
    //   105: aload_1
    //   106: invokevirtual 164	com/fab/views/EndingSoonView:getRibbonTextView	()Lcom/fab/views/FabTextView;
    //   109: aload_2
    //   110: invokevirtual 154	com/fab/sale/bo/SalesBO:getRibbonText	()Ljava/lang/String;
    //   113: invokevirtual 170	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
    //   116: aload_1
    //   117: invokevirtual 164	com/fab/views/EndingSoonView:getRibbonTextView	()Lcom/fab/views/FabTextView;
    //   120: iconst_0
    //   121: invokevirtual 173	com/fab/views/FabTextView:setVisibility	(I)V
    //   124: aload_1
    //   125: invokevirtual 176	com/fab/views/EndingSoonView:getTvDuration	()Lcom/fab/views/FabTextView;
    //   128: new 10	com/fab/sale/EndingSoonAdapter$2
    //   131: dup
    //   132: aload_0
    //   133: aload_2
    //   134: aload_1
    //   135: invokespecial 179	com/fab/sale/EndingSoonAdapter$2:<init>	(Lcom/fab/sale/EndingSoonAdapter;Lcom/fab/sale/bo/SalesBO;Lcom/fab/views/EndingSoonView;)V
    //   138: invokevirtual 180	com/fab/views/FabTextView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   141: aload_2
    //   142: invokevirtual 107	com/fab/sale/bo/SalesBO:getSaleId	()I
    //   145: ifle +655 -> 800
    //   148: aload_3
    //   149: iconst_0
    //   150: invokevirtual 183	android/widget/ProgressBar:setVisibility	(I)V
    //   153: aload_0
    //   154: getfield 57	com/fab/sale/EndingSoonAdapter:imageCache	Ljava/util/HashMap;
    //   157: invokevirtual 186	java/util/HashMap:size	()I
    //   160: bipush 20
    //   162: if_icmple +7 -> 169
    //   165: aload_0
    //   166: invokevirtual 189	com/fab/sale/EndingSoonAdapter:clearImageCache	()V
    //   169: aconst_null
    //   170: astore 5
    //   172: aload_0
    //   173: getfield 57	com/fab/sale/EndingSoonAdapter:imageCache	Ljava/util/HashMap;
    //   176: aload_2
    //   177: invokevirtual 192	com/fab/sale/bo/SalesBO:getSaleImageUrl	()Ljava/lang/String;
    //   180: invokevirtual 196	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   183: ifnull +419 -> 602
    //   186: aload_0
    //   187: getfield 57	com/fab/sale/EndingSoonAdapter:imageCache	Ljava/util/HashMap;
    //   190: aload_2
    //   191: invokevirtual 192	com/fab/sale/bo/SalesBO:getSaleImageUrl	()Ljava/lang/String;
    //   194: invokevirtual 196	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   197: checkcast 198	java/lang/ref/SoftReference
    //   200: invokevirtual 201	java/lang/ref/SoftReference:get	()Ljava/lang/Object;
    //   203: checkcast 203	android/graphics/Bitmap
    //   206: astore 12
    //   208: aload 12
    //   210: ifnull +40 -> 250
    //   213: aload 12
    //   215: aload 12
    //   217: invokevirtual 206	android/graphics/Bitmap:getWidth	()I
    //   220: iconst_2
    //   221: idiv
    //   222: aload 12
    //   224: invokevirtual 209	android/graphics/Bitmap:getHeight	()I
    //   227: iconst_2
    //   228: idiv
    //   229: invokevirtual 213	android/graphics/Bitmap:getPixel	(II)I
    //   232: ifne +18 -> 250
    //   235: aconst_null
    //   236: astore 12
    //   238: aload_0
    //   239: getfield 57	com/fab/sale/EndingSoonAdapter:imageCache	Ljava/util/HashMap;
    //   242: aload_2
    //   243: invokevirtual 192	com/fab/sale/bo/SalesBO:getSaleImageUrl	()Ljava/lang/String;
    //   246: invokevirtual 216	java/util/HashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   249: pop
    //   250: aload 12
    //   252: ifnull +125 -> 377
    //   255: aload 12
    //   257: invokevirtual 209	android/graphics/Bitmap:getHeight	()I
    //   260: bipush 255
    //   262: if_icmpne +13 -> 275
    //   265: aload 12
    //   267: invokevirtual 206	android/graphics/Bitmap:getWidth	()I
    //   270: bipush 255
    //   272: if_icmpeq +105 -> 377
    //   275: aload_3
    //   276: iconst_4
    //   277: invokevirtual 183	android/widget/ProgressBar:setVisibility	(I)V
    //   280: aload_1
    //   281: aload 12
    //   283: invokevirtual 219	com/fab/views/EndingSoonView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   286: aload_1
    //   287: invokevirtual 222	com/fab/views/EndingSoonView:getPrimaryTitle	()Lcom/fab/views/FabTextView;
    //   290: aload_2
    //   291: invokevirtual 224	com/fab/sale/bo/SalesBO:getPrimaryTitle	()Ljava/lang/String;
    //   294: invokevirtual 170	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
    //   297: aload_1
    //   298: invokevirtual 176	com/fab/views/EndingSoonView:getTvDuration	()Lcom/fab/views/FabTextView;
    //   301: aload_2
    //   302: invokevirtual 227	com/fab/sale/bo/SalesBO:getSaleDuration	()Ljava/lang/String;
    //   305: invokevirtual 170	com/fab/views/FabTextView:setText	(Ljava/lang/CharSequence;)V
    //   308: aload_1
    //   309: invokevirtual 231	com/fab/views/EndingSoonView:getProductContainer	()Landroid/widget/RelativeLayout;
    //   312: new 12	com/fab/sale/EndingSoonAdapter$3
    //   315: dup
    //   316: aload_0
    //   317: aload_2
    //   318: invokespecial 234	com/fab/sale/EndingSoonAdapter$3:<init>	(Lcom/fab/sale/EndingSoonAdapter;Lcom/fab/sale/bo/SalesBO;)V
    //   321: invokevirtual 237	android/widget/RelativeLayout:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   324: aload_1
    //   325: invokevirtual 231	com/fab/views/EndingSoonView:getProductContainer	()Landroid/widget/RelativeLayout;
    //   328: iconst_0
    //   329: invokevirtual 238	android/widget/RelativeLayout:setVisibility	(I)V
    //   332: aload_1
    //   333: invokevirtual 242	com/fab/views/EndingSoonView:getDummyProductHolder	()Landroid/widget/ImageView;
    //   336: bipush 8
    //   338: invokevirtual 245	android/widget/ImageView:setVisibility	(I)V
    //   341: return
    //   342: aload_1
    //   343: iconst_0
    //   344: invokevirtual 149	com/fab/views/EndingSoonView:setSlidingDrawerVisibility	(I)V
    //   347: goto -264 -> 83
    //   350: aload_1
    //   351: invokevirtual 164	com/fab/views/EndingSoonView:getRibbonTextView	()Lcom/fab/views/FabTextView;
    //   354: iconst_4
    //   355: invokevirtual 173	com/fab/views/FabTextView:setVisibility	(I)V
    //   358: goto -234 -> 124
    //   361: astore 21
    //   363: aconst_null
    //   364: astore 12
    //   366: goto -116 -> 250
    //   369: astore 20
    //   371: aconst_null
    //   372: astore 12
    //   374: goto -124 -> 250
    //   377: aload_0
    //   378: getfield 45	com/fab/sale/EndingSoonAdapter:helper	Lcom/fab/utils/FabHelper;
    //   381: aload_0
    //   382: getfield 59	com/fab/sale/EndingSoonAdapter:context	Landroid/content/Context;
    //   385: aload_2
    //   386: invokevirtual 192	com/fab/sale/bo/SalesBO:getSaleImageUrl	()Ljava/lang/String;
    //   389: invokevirtual 249	com/fab/utils/FabHelper:doesFileExists	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   392: astore 13
    //   394: aload 13
    //   396: invokestatic 160	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   399: ifne +57 -> 456
    //   402: aload_0
    //   403: getfield 45	com/fab/sale/EndingSoonAdapter:helper	Lcom/fab/utils/FabHelper;
    //   406: aload 13
    //   408: invokevirtual 253	com/fab/utils/FabHelper:decodeFileForBitmap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   411: astore 12
    //   413: aload 12
    //   415: ifnull +23 -> 438
    //   418: aload 12
    //   420: invokevirtual 209	android/graphics/Bitmap:getHeight	()I
    //   423: bipush 255
    //   425: if_icmpeq +13 -> 438
    //   428: aload 12
    //   430: invokevirtual 206	android/graphics/Bitmap:getWidth	()I
    //   433: bipush 255
    //   435: if_icmpne +21 -> 456
    //   438: new 255	java/io/File
    //   441: dup
    //   442: aload 13
    //   444: invokespecial 258	java/io/File:<init>	(Ljava/lang/String;)V
    //   447: invokevirtual 262	java/io/File:delete	()Z
    //   450: pop
    //   451: ldc_w 264
    //   454: astore 13
    //   456: aload 12
    //   458: ifnull +45 -> 503
    //   461: aload 12
    //   463: aload 12
    //   465: invokevirtual 206	android/graphics/Bitmap:getWidth	()I
    //   468: iconst_2
    //   469: idiv
    //   470: aload 12
    //   472: invokevirtual 209	android/graphics/Bitmap:getHeight	()I
    //   475: iconst_2
    //   476: idiv
    //   477: invokevirtual 213	android/graphics/Bitmap:getPixel	(II)I
    //   480: ifne +23 -> 503
    //   483: ldc_w 264
    //   486: astore 13
    //   488: aconst_null
    //   489: astore 12
    //   491: aload_0
    //   492: getfield 57	com/fab/sale/EndingSoonAdapter:imageCache	Ljava/util/HashMap;
    //   495: aload_2
    //   496: invokevirtual 192	com/fab/sale/bo/SalesBO:getSaleImageUrl	()Ljava/lang/String;
    //   499: invokevirtual 216	java/util/HashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   502: pop
    //   503: aload 13
    //   505: invokestatic 160	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   508: ifne +28 -> 536
    //   511: aload 12
    //   513: ifnull +23 -> 536
    //   516: aload 12
    //   518: invokevirtual 209	android/graphics/Bitmap:getHeight	()I
    //   521: bipush 255
    //   523: if_icmpeq +13 -> 536
    //   526: aload 12
    //   528: invokevirtual 206	android/graphics/Bitmap:getWidth	()I
    //   531: bipush 255
    //   533: if_icmpne +29 -> 562
    //   536: aload_3
    //   537: iconst_0
    //   538: invokevirtual 183	android/widget/ProgressBar:setVisibility	(I)V
    //   541: aload_0
    //   542: invokevirtual 267	com/fab/sale/EndingSoonAdapter:isAllDownloading	()Z
    //   545: ifne +286 -> 831
    //   548: aload_0
    //   549: invokevirtual 270	com/fab/sale/EndingSoonAdapter:isImageDownloading	()Z
    //   552: ifne +279 -> 831
    //   555: aload_0
    //   556: invokevirtual 273	com/fab/sale/EndingSoonAdapter:downloadTheImages	()V
    //   559: goto +272 -> 831
    //   562: aload_3
    //   563: iconst_4
    //   564: invokevirtual 183	android/widget/ProgressBar:setVisibility	(I)V
    //   567: aload_1
    //   568: aload 12
    //   570: invokevirtual 219	com/fab/views/EndingSoonView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   573: aload_0
    //   574: getfield 57	com/fab/sale/EndingSoonAdapter:imageCache	Ljava/util/HashMap;
    //   577: aload_2
    //   578: invokevirtual 192	com/fab/sale/bo/SalesBO:getSaleImageUrl	()Ljava/lang/String;
    //   581: new 198	java/lang/ref/SoftReference
    //   584: dup
    //   585: aload 12
    //   587: invokespecial 276	java/lang/ref/SoftReference:<init>	(Ljava/lang/Object;)V
    //   590: invokevirtual 280	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   593: pop
    //   594: goto +237 -> 831
    //   597: astore 14
    //   599: aload 14
    //   601: athrow
    //   602: aload_0
    //   603: getfield 45	com/fab/sale/EndingSoonAdapter:helper	Lcom/fab/utils/FabHelper;
    //   606: aload_0
    //   607: getfield 59	com/fab/sale/EndingSoonAdapter:context	Landroid/content/Context;
    //   610: aload_2
    //   611: invokevirtual 192	com/fab/sale/bo/SalesBO:getSaleImageUrl	()Ljava/lang/String;
    //   614: invokevirtual 249	com/fab/utils/FabHelper:doesFileExists	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   617: astore 6
    //   619: aload 6
    //   621: invokestatic 160	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   624: ifne +37 -> 661
    //   627: aload_0
    //   628: getfield 45	com/fab/sale/EndingSoonAdapter:helper	Lcom/fab/utils/FabHelper;
    //   631: aload 6
    //   633: invokevirtual 253	com/fab/utils/FabHelper:decodeFileForBitmap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   636: astore 5
    //   638: aload 5
    //   640: ifnonnull +21 -> 661
    //   643: new 255	java/io/File
    //   646: dup
    //   647: aload 6
    //   649: invokespecial 258	java/io/File:<init>	(Ljava/lang/String;)V
    //   652: invokevirtual 262	java/io/File:delete	()Z
    //   655: pop
    //   656: ldc_w 264
    //   659: astore 6
    //   661: aload 5
    //   663: ifnull +33 -> 696
    //   666: aload 5
    //   668: aload 5
    //   670: invokevirtual 206	android/graphics/Bitmap:getWidth	()I
    //   673: iconst_2
    //   674: idiv
    //   675: aload 5
    //   677: invokevirtual 209	android/graphics/Bitmap:getHeight	()I
    //   680: iconst_2
    //   681: idiv
    //   682: invokevirtual 213	android/graphics/Bitmap:getPixel	(II)I
    //   685: ifne +11 -> 696
    //   688: ldc_w 264
    //   691: astore 6
    //   693: aconst_null
    //   694: astore 5
    //   696: aload 6
    //   698: invokestatic 160	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   701: ifne +33 -> 734
    //   704: aload 5
    //   706: ifnull +28 -> 734
    //   709: aload 5
    //   711: ifnull +49 -> 760
    //   714: aload 5
    //   716: invokevirtual 209	android/graphics/Bitmap:getHeight	()I
    //   719: bipush 255
    //   721: if_icmpeq +13 -> 734
    //   724: aload 5
    //   726: invokevirtual 206	android/graphics/Bitmap:getWidth	()I
    //   729: bipush 255
    //   731: if_icmpne +29 -> 760
    //   734: aload_3
    //   735: iconst_0
    //   736: invokevirtual 183	android/widget/ProgressBar:setVisibility	(I)V
    //   739: aload_0
    //   740: invokevirtual 267	com/fab/sale/EndingSoonAdapter:isAllDownloading	()Z
    //   743: ifne +117 -> 860
    //   746: aload_0
    //   747: invokevirtual 270	com/fab/sale/EndingSoonAdapter:isImageDownloading	()Z
    //   750: ifne +110 -> 860
    //   753: aload_0
    //   754: invokevirtual 273	com/fab/sale/EndingSoonAdapter:downloadTheImages	()V
    //   757: goto +103 -> 860
    //   760: aload_3
    //   761: iconst_4
    //   762: invokevirtual 183	android/widget/ProgressBar:setVisibility	(I)V
    //   765: aload_1
    //   766: aload 5
    //   768: invokevirtual 219	com/fab/views/EndingSoonView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   771: aload_0
    //   772: getfield 57	com/fab/sale/EndingSoonAdapter:imageCache	Ljava/util/HashMap;
    //   775: aload_2
    //   776: invokevirtual 192	com/fab/sale/bo/SalesBO:getSaleImageUrl	()Ljava/lang/String;
    //   779: new 198	java/lang/ref/SoftReference
    //   782: dup
    //   783: aload 5
    //   785: invokespecial 276	java/lang/ref/SoftReference:<init>	(Ljava/lang/Object;)V
    //   788: invokevirtual 280	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   791: pop
    //   792: goto +68 -> 860
    //   795: astore 7
    //   797: aload 7
    //   799: athrow
    //   800: aload_1
    //   801: aconst_null
    //   802: invokevirtual 281	com/fab/views/EndingSoonView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   805: aload_1
    //   806: invokevirtual 231	com/fab/views/EndingSoonView:getProductContainer	()Landroid/widget/RelativeLayout;
    //   809: bipush 8
    //   811: invokevirtual 238	android/widget/RelativeLayout:setVisibility	(I)V
    //   814: aload_1
    //   815: invokevirtual 242	com/fab/views/EndingSoonView:getDummyProductHolder	()Landroid/widget/ImageView;
    //   818: iconst_0
    //   819: invokevirtual 245	android/widget/ImageView:setVisibility	(I)V
    //   822: aload_3
    //   823: bipush 8
    //   825: invokevirtual 183	android/widget/ProgressBar:setVisibility	(I)V
    //   828: goto -487 -> 341
    //   831: goto -545 -> 286
    //   834: astore 17
    //   836: ldc_w 264
    //   839: astore 13
    //   841: aconst_null
    //   842: astore 12
    //   844: goto -341 -> 503
    //   847: astore 16
    //   849: ldc_w 264
    //   852: astore 13
    //   854: aconst_null
    //   855: astore 12
    //   857: goto -354 -> 503
    //   860: goto -574 -> 286
    //   863: astore 10
    //   865: ldc_w 264
    //   868: astore 6
    //   870: aconst_null
    //   871: astore 5
    //   873: goto -177 -> 696
    //   876: astore 9
    //   878: ldc_w 264
    //   881: astore 6
    //   883: aconst_null
    //   884: astore 5
    //   886: goto -190 -> 696
    //
    // Exception table:
    //   from	to	target	type
    //   213	250	361	java/lang/IllegalArgumentException
    //   213	250	369	java/lang/Exception
    //   394	456	597	finally
    //   461	503	597	finally
    //   503	594	597	finally
    //   619	661	795	finally
    //   666	693	795	finally
    //   696	792	795	finally
    //   461	503	834	java/lang/IllegalArgumentException
    //   461	503	847	java/lang/Exception
    //   666	693	863	java/lang/IllegalArgumentException
    //   666	693	876	java/lang/Exception
  }

  private void queueTheImages()
  {
    synchronized (this.theImageQueues)
    {
      this.theImageQueues.clear();
      Iterator localIterator = this.theEndingSales.iterator();
      SalesBO localSalesBO2;
      do
      {
        if (!localIterator.hasNext())
          return;
        PairedSales localPairedSales = (PairedSales)localIterator.next();
        SalesBO localSalesBO1 = localPairedSales.getFirstSale();
        if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localSalesBO1.getSaleImageUrl())))
          this.theImageQueues.add(localSalesBO1.getSaleImageUrl());
        localSalesBO2 = localPairedSales.getSecondSale();
      }
      while (!TextUtils.isEmpty(this.helper.doesFileExists(this.context, localSalesBO2.getSaleImageUrl())));
      this.theImageQueues.add(localSalesBO2.getSaleImageUrl());
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
        if ((EndingSoonSalesActivity.isActivityAlive) || (FeaturedActivity.isActivityAlive))
        {
          if ((isAllDownloading()) && (isImageDownloading()))
            queueTheImages();
        }
        else
          return;
        setAllDownloading(true);
        localArrayList = new ArrayList();
        if ((this.theImageQueues == null) || (!this.theImageQueues.isEmpty()))
          break label263;
        localIterator = this.theEndingSales.iterator();
        if (!localIterator.hasNext())
        {
          if (localArrayList.size() <= 0)
            break label275;
          String[] arrayOfString = new String[localArrayList.size()];
          if ((EndingSoonSalesActivity.isActivityAlive) || (FeaturedActivity.isActivityAlive))
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
      PairedSales localPairedSales = (PairedSales)localIterator.next();
      SalesBO localSalesBO1 = localPairedSales.getFirstSale();
      if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localSalesBO1.getSaleImageUrl())))
        localArrayList.add(localSalesBO1.getSaleImageUrl());
      SalesBO localSalesBO2 = localPairedSales.getSecondSale();
      if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localSalesBO2.getSaleImageUrl())))
      {
        localArrayList.add(localSalesBO2.getSaleImageUrl());
        continue;
        label263: localArrayList.addAll(this.theImageQueues);
        continue;
        label275: setAllDownloading(false);
        notifyDataSetChanged();
      }
    }
  }

  public int getCount()
  {
    return this.theEndingSales.size();
  }

  public PairedSales getItem(int paramInt)
  {
    return (PairedSales)this.theEndingSales.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    EndingSoonViewHolder localEndingSoonViewHolder;
    if (paramView == null)
    {
      paramView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903050, null);
      localEndingSoonViewHolder = new EndingSoonViewHolder();
      localEndingSoonViewHolder.header = ((LinearLayout)paramView.findViewById(2131361855));
      localEndingSoonViewHolder.sale1 = ((EndingSoonView)paramView.findViewById(2131361857));
      localEndingSoonViewHolder.pBar1 = ((ProgressBar)paramView.findViewById(2131361858));
      localEndingSoonViewHolder.sale2 = ((EndingSoonView)paramView.findViewById(2131361859));
      localEndingSoonViewHolder.pBar2 = ((ProgressBar)paramView.findViewById(2131361860));
      paramView.setTag(localEndingSoonViewHolder);
      if (paramInt != 0)
        break label209;
      localEndingSoonViewHolder.header.setVisibility(0);
    }
    while (true)
    {
      PairedSales localPairedSales = (PairedSales)this.theEndingSales.get(paramInt);
      populateUI(localEndingSoonViewHolder.sale1, localPairedSales.getFirstSale(), localEndingSoonViewHolder.pBar1, localPairedSales.getFirstSale().isSliderOpen());
      populateUI(localEndingSoonViewHolder.sale2, localPairedSales.getSecondSale(), localEndingSoonViewHolder.pBar2, localPairedSales.getSecondSale().isSliderOpen());
      return paramView;
      localEndingSoonViewHolder = (EndingSoonViewHolder)paramView.getTag();
      break;
      label209: localEndingSoonViewHolder.header.setVisibility(8);
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

  static class EndingSoonViewHolder
  {
    LinearLayout header;
    ProgressBar pBar1;
    ProgressBar pBar2;
    EndingSoonView sale1;
    EndingSoonView sale2;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.EndingSoonAdapter
 * JD-Core Version:    0.6.2
 */