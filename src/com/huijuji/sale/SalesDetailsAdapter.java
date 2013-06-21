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
import android.widget.ProgressBar;
import com.fab.sale.bo.PairedProducts;
import com.fab.sale.bo.ProductDetailsBO;
import com.fab.utils.FabBaseAdapter;
import com.fab.utils.FabHelper;
import com.fab.utils.ImageDownloader;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.ProductView;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SalesDetailsAdapter extends FabBaseAdapter
  implements TaskCompleteListener
{
  Context context;
  private ImageDownloader downloader;
  private FabHelper helper = FabHelper.getFabHelper();
  HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap();
  private ArrayList<String> theImageQueues = new ArrayList();
  ArrayList<PairedProducts> theProducts;

  public SalesDetailsAdapter(Context paramContext, ArrayList<PairedProducts> paramArrayList)
  {
    this.context = paramContext;
    this.theProducts = paramArrayList;
  }

  // ERROR //
  private void populateUI(ProductView paramProductView, ProductDetailsBO paramProductDetailsBO, ProgressBar paramProgressBar)
  {
    // Byte code:
    //   0: aload_1
    //   1: aload_2
    //   2: invokevirtual 71	com/fab/views/ProductView:setTag	(Ljava/lang/Object;)V
    //   5: aload_1
    //   6: aload_1
    //   7: invokevirtual 75	com/fab/views/ProductView:getDrawable	()Landroid/graphics/drawable/Drawable;
    //   10: invokevirtual 79	com/fab/views/ProductView:setProdImage	(Landroid/graphics/drawable/Drawable;)V
    //   13: aload_2
    //   14: invokevirtual 85	com/fab/sale/bo/ProductDetailsBO:getName	()Ljava/lang/String;
    //   17: ifnull +793 -> 810
    //   20: aload_3
    //   21: iconst_0
    //   22: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   25: aload_0
    //   26: getfield 49	com/fab/sale/SalesDetailsAdapter:imageCache	Ljava/util/HashMap;
    //   29: invokevirtual 95	java/util/HashMap:size	()I
    //   32: bipush 20
    //   34: if_icmple +7 -> 41
    //   37: aload_0
    //   38: invokevirtual 98	com/fab/sale/SalesDetailsAdapter:clearImageCache	()V
    //   41: aconst_null
    //   42: astore 4
    //   44: aload_0
    //   45: getfield 49	com/fab/sale/SalesDetailsAdapter:imageCache	Ljava/util/HashMap;
    //   48: aload_2
    //   49: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   52: invokevirtual 105	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   55: ifnull +496 -> 551
    //   58: aload_0
    //   59: getfield 49	com/fab/sale/SalesDetailsAdapter:imageCache	Ljava/util/HashMap;
    //   62: aload_2
    //   63: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   66: invokevirtual 105	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   69: checkcast 107	java/lang/ref/SoftReference
    //   72: invokevirtual 110	java/lang/ref/SoftReference:get	()Ljava/lang/Object;
    //   75: checkcast 112	android/graphics/Bitmap
    //   78: astore 11
    //   80: aload 11
    //   82: ifnull +40 -> 122
    //   85: aload 11
    //   87: aload 11
    //   89: invokevirtual 115	android/graphics/Bitmap:getWidth	()I
    //   92: iconst_2
    //   93: idiv
    //   94: aload 11
    //   96: invokevirtual 118	android/graphics/Bitmap:getHeight	()I
    //   99: iconst_2
    //   100: idiv
    //   101: invokevirtual 122	android/graphics/Bitmap:getPixel	(II)I
    //   104: ifne +18 -> 122
    //   107: aconst_null
    //   108: astore 11
    //   110: aload_0
    //   111: getfield 49	com/fab/sale/SalesDetailsAdapter:imageCache	Ljava/util/HashMap;
    //   114: aload_2
    //   115: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   118: invokevirtual 125	java/util/HashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   121: pop
    //   122: aload 11
    //   124: ifnull +199 -> 323
    //   127: aload 11
    //   129: invokevirtual 118	android/graphics/Bitmap:getHeight	()I
    //   132: bipush 255
    //   134: if_icmpne +13 -> 147
    //   137: aload 11
    //   139: invokevirtual 115	android/graphics/Bitmap:getWidth	()I
    //   142: bipush 255
    //   144: if_icmpeq +179 -> 323
    //   147: aload_3
    //   148: iconst_4
    //   149: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   152: aload_1
    //   153: aload 11
    //   155: invokevirtual 128	com/fab/views/ProductView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   158: aload_2
    //   159: invokevirtual 131	com/fab/sale/bo/ProductDetailsBO:getDiscountPct	()Ljava/lang/String;
    //   162: ldc 133
    //   164: invokevirtual 139	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   167: ifeq +580 -> 747
    //   170: aload_1
    //   171: ldc 141
    //   173: invokevirtual 145	com/fab/views/ProductView:setDiscount	(Ljava/lang/String;)V
    //   176: aload_1
    //   177: invokevirtual 149	com/fab/views/ProductView:getDiscountPctRibbon	()Landroid/widget/ImageView;
    //   180: bipush 8
    //   182: invokevirtual 152	android/widget/ImageView:setVisibility	(I)V
    //   185: aload_2
    //   186: invokevirtual 155	com/fab/sale/bo/ProductDetailsBO:getStatus	()Ljava/lang/String;
    //   189: invokestatic 161	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   192: ifne +595 -> 787
    //   195: aload_1
    //   196: aload_2
    //   197: invokevirtual 155	com/fab/sale/bo/ProductDetailsBO:getStatus	()Ljava/lang/String;
    //   200: invokevirtual 164	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   203: invokevirtual 167	com/fab/views/ProductView:setProdStatus	(Ljava/lang/String;)V
    //   206: aload_1
    //   207: invokevirtual 171	com/fab/views/ProductView:getProdStatus	()Landroid/widget/TextView;
    //   210: iconst_0
    //   211: invokevirtual 174	android/widget/TextView:setVisibility	(I)V
    //   214: aload_1
    //   215: aload_2
    //   216: invokevirtual 85	com/fab/sale/bo/ProductDetailsBO:getName	()Ljava/lang/String;
    //   219: invokevirtual 177	com/fab/views/ProductView:setProdName	(Ljava/lang/String;)V
    //   222: aload_1
    //   223: aload_2
    //   224: invokevirtual 180	com/fab/sale/bo/ProductDetailsBO:getFabPrice	()Ljava/lang/String;
    //   227: invokevirtual 183	com/fab/views/ProductView:setFabPrice	(Ljava/lang/String;)V
    //   230: aload_1
    //   231: invokevirtual 187	com/fab/views/ProductView:getOriginalPrice	()Lcom/fab/views/FabTextView;
    //   234: bipush 16
    //   236: aload_1
    //   237: invokevirtual 187	com/fab/views/ProductView:getOriginalPrice	()Lcom/fab/views/FabTextView;
    //   240: invokevirtual 192	com/fab/views/FabTextView:getPaintFlags	()I
    //   243: ior
    //   244: invokevirtual 195	com/fab/views/FabTextView:setPaintFlags	(I)V
    //   247: aload_2
    //   248: invokevirtual 180	com/fab/sale/bo/ProductDetailsBO:getFabPrice	()Ljava/lang/String;
    //   251: aload_2
    //   252: invokevirtual 198	com/fab/sale/bo/ProductDetailsBO:getMsrp	()Ljava/lang/String;
    //   255: invokevirtual 139	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   258: ifne +540 -> 798
    //   261: aload_1
    //   262: aload_2
    //   263: invokevirtual 198	com/fab/sale/bo/ProductDetailsBO:getMsrp	()Ljava/lang/String;
    //   266: invokevirtual 201	com/fab/views/ProductView:setOriginalPrice	(Ljava/lang/String;)V
    //   269: aload_1
    //   270: invokevirtual 187	com/fab/views/ProductView:getOriginalPrice	()Lcom/fab/views/FabTextView;
    //   273: iconst_0
    //   274: invokevirtual 202	com/fab/views/FabTextView:setVisibility	(I)V
    //   277: aload_1
    //   278: new 8	com/fab/sale/SalesDetailsAdapter$1
    //   281: dup
    //   282: aload_0
    //   283: invokespecial 205	com/fab/sale/SalesDetailsAdapter$1:<init>	(Lcom/fab/sale/SalesDetailsAdapter;)V
    //   286: invokevirtual 209	com/fab/views/ProductView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   289: aload_1
    //   290: invokevirtual 213	com/fab/views/ProductView:getProductContainer	()Landroid/widget/RelativeLayout;
    //   293: iconst_0
    //   294: invokevirtual 216	android/widget/RelativeLayout:setVisibility	(I)V
    //   297: aload_1
    //   298: invokevirtual 219	com/fab/views/ProductView:getDummyProductHolder	()Landroid/widget/ImageView;
    //   301: bipush 8
    //   303: invokevirtual 152	android/widget/ImageView:setVisibility	(I)V
    //   306: return
    //   307: astore 20
    //   309: aconst_null
    //   310: astore 11
    //   312: goto -190 -> 122
    //   315: astore 19
    //   317: aconst_null
    //   318: astore 11
    //   320: goto -198 -> 122
    //   323: aload_3
    //   324: iconst_0
    //   325: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   328: aload_0
    //   329: getfield 39	com/fab/sale/SalesDetailsAdapter:helper	Lcom/fab/utils/FabHelper;
    //   332: aload_0
    //   333: getfield 51	com/fab/sale/SalesDetailsAdapter:context	Landroid/content/Context;
    //   336: aload_2
    //   337: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   340: invokevirtual 223	com/fab/utils/FabHelper:doesFileExists	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   343: astore 12
    //   345: aload 12
    //   347: invokestatic 161	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   350: ifne +56 -> 406
    //   353: aload_0
    //   354: getfield 39	com/fab/sale/SalesDetailsAdapter:helper	Lcom/fab/utils/FabHelper;
    //   357: aload 12
    //   359: invokevirtual 227	com/fab/utils/FabHelper:decodeFileForBitmap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   362: astore 11
    //   364: aload 11
    //   366: ifnull +23 -> 389
    //   369: aload 11
    //   371: invokevirtual 118	android/graphics/Bitmap:getHeight	()I
    //   374: bipush 255
    //   376: if_icmpeq +13 -> 389
    //   379: aload 11
    //   381: invokevirtual 115	android/graphics/Bitmap:getWidth	()I
    //   384: bipush 255
    //   386: if_icmpne +20 -> 406
    //   389: new 229	java/io/File
    //   392: dup
    //   393: aload 12
    //   395: invokespecial 231	java/io/File:<init>	(Ljava/lang/String;)V
    //   398: invokevirtual 235	java/io/File:delete	()Z
    //   401: pop
    //   402: ldc 141
    //   404: astore 12
    //   406: aload 11
    //   408: ifnull +44 -> 452
    //   411: aload 11
    //   413: aload 11
    //   415: invokevirtual 115	android/graphics/Bitmap:getWidth	()I
    //   418: iconst_2
    //   419: idiv
    //   420: aload 11
    //   422: invokevirtual 118	android/graphics/Bitmap:getHeight	()I
    //   425: iconst_2
    //   426: idiv
    //   427: invokevirtual 122	android/graphics/Bitmap:getPixel	(II)I
    //   430: ifne +22 -> 452
    //   433: ldc 141
    //   435: astore 12
    //   437: aconst_null
    //   438: astore 11
    //   440: aload_0
    //   441: getfield 49	com/fab/sale/SalesDetailsAdapter:imageCache	Ljava/util/HashMap;
    //   444: aload_2
    //   445: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   448: invokevirtual 125	java/util/HashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   451: pop
    //   452: aload 12
    //   454: invokestatic 161	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   457: ifne +28 -> 485
    //   460: aload 11
    //   462: ifnull +23 -> 485
    //   465: aload 11
    //   467: invokevirtual 118	android/graphics/Bitmap:getHeight	()I
    //   470: bipush 255
    //   472: if_icmpeq +13 -> 485
    //   475: aload 11
    //   477: invokevirtual 115	android/graphics/Bitmap:getWidth	()I
    //   480: bipush 255
    //   482: if_icmpne +29 -> 511
    //   485: aload_3
    //   486: iconst_0
    //   487: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   490: aload_0
    //   491: invokevirtual 238	com/fab/sale/SalesDetailsAdapter:isAllDownloading	()Z
    //   494: ifne +347 -> 841
    //   497: aload_0
    //   498: invokevirtual 241	com/fab/sale/SalesDetailsAdapter:isImageDownloading	()Z
    //   501: ifne +340 -> 841
    //   504: aload_0
    //   505: invokevirtual 244	com/fab/sale/SalesDetailsAdapter:downloadTheImages	()V
    //   508: goto +333 -> 841
    //   511: aload_3
    //   512: iconst_4
    //   513: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   516: aload_1
    //   517: aload 11
    //   519: invokevirtual 128	com/fab/views/ProductView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   522: aload_0
    //   523: getfield 49	com/fab/sale/SalesDetailsAdapter:imageCache	Ljava/util/HashMap;
    //   526: aload_2
    //   527: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   530: new 107	java/lang/ref/SoftReference
    //   533: dup
    //   534: aload 11
    //   536: invokespecial 246	java/lang/ref/SoftReference:<init>	(Ljava/lang/Object;)V
    //   539: invokevirtual 250	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   542: pop
    //   543: goto +298 -> 841
    //   546: astore 13
    //   548: aload 13
    //   550: athrow
    //   551: aload_0
    //   552: getfield 39	com/fab/sale/SalesDetailsAdapter:helper	Lcom/fab/utils/FabHelper;
    //   555: aload_0
    //   556: getfield 51	com/fab/sale/SalesDetailsAdapter:context	Landroid/content/Context;
    //   559: aload_2
    //   560: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   563: invokevirtual 223	com/fab/utils/FabHelper:doesFileExists	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   566: astore 5
    //   568: aload 5
    //   570: invokestatic 161	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   573: ifne +36 -> 609
    //   576: aload_0
    //   577: getfield 39	com/fab/sale/SalesDetailsAdapter:helper	Lcom/fab/utils/FabHelper;
    //   580: aload 5
    //   582: invokevirtual 227	com/fab/utils/FabHelper:decodeFileForBitmap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   585: astore 4
    //   587: aload 4
    //   589: ifnonnull +20 -> 609
    //   592: new 229	java/io/File
    //   595: dup
    //   596: aload 5
    //   598: invokespecial 231	java/io/File:<init>	(Ljava/lang/String;)V
    //   601: invokevirtual 235	java/io/File:delete	()Z
    //   604: pop
    //   605: ldc 141
    //   607: astore 5
    //   609: aload 4
    //   611: ifnull +32 -> 643
    //   614: aload 4
    //   616: aload 4
    //   618: invokevirtual 115	android/graphics/Bitmap:getWidth	()I
    //   621: iconst_2
    //   622: idiv
    //   623: aload 4
    //   625: invokevirtual 118	android/graphics/Bitmap:getHeight	()I
    //   628: iconst_2
    //   629: idiv
    //   630: invokevirtual 122	android/graphics/Bitmap:getPixel	(II)I
    //   633: ifne +10 -> 643
    //   636: ldc 141
    //   638: astore 5
    //   640: aconst_null
    //   641: astore 4
    //   643: aload 5
    //   645: invokestatic 161	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   648: ifne +33 -> 681
    //   651: aload 4
    //   653: ifnull +28 -> 681
    //   656: aload 4
    //   658: ifnull +49 -> 707
    //   661: aload 4
    //   663: invokevirtual 118	android/graphics/Bitmap:getHeight	()I
    //   666: bipush 255
    //   668: if_icmpeq +13 -> 681
    //   671: aload 4
    //   673: invokevirtual 115	android/graphics/Bitmap:getWidth	()I
    //   676: bipush 255
    //   678: if_icmpne +29 -> 707
    //   681: aload_3
    //   682: iconst_0
    //   683: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   686: aload_0
    //   687: invokevirtual 238	com/fab/sale/SalesDetailsAdapter:isAllDownloading	()Z
    //   690: ifne +178 -> 868
    //   693: aload_0
    //   694: invokevirtual 241	com/fab/sale/SalesDetailsAdapter:isImageDownloading	()Z
    //   697: ifne +171 -> 868
    //   700: aload_0
    //   701: invokevirtual 244	com/fab/sale/SalesDetailsAdapter:downloadTheImages	()V
    //   704: goto +164 -> 868
    //   707: aload_3
    //   708: iconst_4
    //   709: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   712: aload_1
    //   713: aload 4
    //   715: invokevirtual 128	com/fab/views/ProductView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   718: aload_0
    //   719: getfield 49	com/fab/sale/SalesDetailsAdapter:imageCache	Ljava/util/HashMap;
    //   722: aload_2
    //   723: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   726: new 107	java/lang/ref/SoftReference
    //   729: dup
    //   730: aload 4
    //   732: invokespecial 246	java/lang/ref/SoftReference:<init>	(Ljava/lang/Object;)V
    //   735: invokevirtual 250	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   738: pop
    //   739: goto +129 -> 868
    //   742: astore 6
    //   744: aload 6
    //   746: athrow
    //   747: aload_1
    //   748: new 252	java/lang/StringBuilder
    //   751: dup
    //   752: ldc 254
    //   754: invokespecial 255	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   757: aload_2
    //   758: invokevirtual 131	com/fab/sale/bo/ProductDetailsBO:getDiscountPct	()Ljava/lang/String;
    //   761: invokevirtual 259	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   764: ldc_w 261
    //   767: invokevirtual 259	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   770: invokevirtual 264	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   773: invokevirtual 145	com/fab/views/ProductView:setDiscount	(Ljava/lang/String;)V
    //   776: aload_1
    //   777: invokevirtual 149	com/fab/views/ProductView:getDiscountPctRibbon	()Landroid/widget/ImageView;
    //   780: iconst_0
    //   781: invokevirtual 152	android/widget/ImageView:setVisibility	(I)V
    //   784: goto -599 -> 185
    //   787: aload_1
    //   788: invokevirtual 171	com/fab/views/ProductView:getProdStatus	()Landroid/widget/TextView;
    //   791: iconst_4
    //   792: invokevirtual 174	android/widget/TextView:setVisibility	(I)V
    //   795: goto -581 -> 214
    //   798: aload_1
    //   799: invokevirtual 187	com/fab/views/ProductView:getOriginalPrice	()Lcom/fab/views/FabTextView;
    //   802: bipush 8
    //   804: invokevirtual 202	com/fab/views/FabTextView:setVisibility	(I)V
    //   807: goto -530 -> 277
    //   810: aload_1
    //   811: aconst_null
    //   812: invokevirtual 209	com/fab/views/ProductView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   815: aload_1
    //   816: invokevirtual 213	com/fab/views/ProductView:getProductContainer	()Landroid/widget/RelativeLayout;
    //   819: bipush 8
    //   821: invokevirtual 216	android/widget/RelativeLayout:setVisibility	(I)V
    //   824: aload_1
    //   825: invokevirtual 219	com/fab/views/ProductView:getDummyProductHolder	()Landroid/widget/ImageView;
    //   828: iconst_0
    //   829: invokevirtual 152	android/widget/ImageView:setVisibility	(I)V
    //   832: aload_3
    //   833: bipush 8
    //   835: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   838: goto -532 -> 306
    //   841: goto -683 -> 158
    //   844: astore 16
    //   846: ldc 141
    //   848: astore 12
    //   850: aconst_null
    //   851: astore 11
    //   853: goto -401 -> 452
    //   856: astore 15
    //   858: ldc 141
    //   860: astore 12
    //   862: aconst_null
    //   863: astore 11
    //   865: goto -413 -> 452
    //   868: goto -710 -> 158
    //   871: astore 9
    //   873: ldc 141
    //   875: astore 5
    //   877: aconst_null
    //   878: astore 4
    //   880: goto -237 -> 643
    //   883: astore 8
    //   885: ldc 141
    //   887: astore 5
    //   889: aconst_null
    //   890: astore 4
    //   892: goto -249 -> 643
    //
    // Exception table:
    //   from	to	target	type
    //   85	122	307	java/lang/IllegalArgumentException
    //   85	122	315	java/lang/Exception
    //   345	406	546	finally
    //   411	452	546	finally
    //   452	543	546	finally
    //   568	609	742	finally
    //   614	640	742	finally
    //   643	739	742	finally
    //   411	452	844	java/lang/IllegalArgumentException
    //   411	452	856	java/lang/Exception
    //   614	640	871	java/lang/IllegalArgumentException
    //   614	640	883	java/lang/Exception
  }

  private void queueTheImages()
  {
    synchronized (this.theImageQueues)
    {
      this.theImageQueues.clear();
      Iterator localIterator = this.theProducts.iterator();
      ProductDetailsBO localProductDetailsBO2;
      do
      {
        if (!localIterator.hasNext())
          return;
        PairedProducts localPairedProducts = (PairedProducts)localIterator.next();
        ProductDetailsBO localProductDetailsBO1 = localPairedProducts.getFirstProduct();
        if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localProductDetailsBO1.getImageUrl())))
          this.theImageQueues.add(localProductDetailsBO1.getImageUrl());
        localProductDetailsBO2 = localPairedProducts.getSecondProduct();
      }
      while (!TextUtils.isEmpty(this.helper.doesFileExists(this.context, localProductDetailsBO2.getImageUrl())));
      this.theImageQueues.add(localProductDetailsBO2.getImageUrl());
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
        if (SaleDetailsActivity.isActivityAlive)
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
        localIterator = this.theProducts.iterator();
        if (!localIterator.hasNext())
        {
          if (localArrayList.size() <= 0)
            break label263;
          String[] arrayOfString = new String[localArrayList.size()];
          if (SaleDetailsActivity.isActivityAlive)
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
      PairedProducts localPairedProducts = (PairedProducts)localIterator.next();
      ProductDetailsBO localProductDetailsBO1 = localPairedProducts.getFirstProduct();
      if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localProductDetailsBO1.getImageUrl())))
        localArrayList.add(localProductDetailsBO1.getImageUrl());
      ProductDetailsBO localProductDetailsBO2 = localPairedProducts.getSecondProduct();
      if (TextUtils.isEmpty(this.helper.doesFileExists(this.context, localProductDetailsBO2.getImageUrl())))
      {
        localArrayList.add(localProductDetailsBO2.getImageUrl());
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
    SaleDetailsViewHolder localSaleDetailsViewHolder;
    if (paramView == null)
    {
      paramView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903084, null);
      localSaleDetailsViewHolder = new SaleDetailsViewHolder();
      localSaleDetailsViewHolder.product1 = ((ProductView)paramView.findViewById(2131362007));
      localSaleDetailsViewHolder.pBar1 = ((ProgressBar)paramView.findViewById(2131361858));
      localSaleDetailsViewHolder.product2 = ((ProductView)paramView.findViewById(2131362008));
      localSaleDetailsViewHolder.pBar2 = ((ProgressBar)paramView.findViewById(2131361860));
      paramView.setTag(localSaleDetailsViewHolder);
    }
    while (true)
    {
      PairedProducts localPairedProducts = (PairedProducts)this.theProducts.get(paramInt);
      populateUI(localSaleDetailsViewHolder.product1, localPairedProducts.getFirstProduct(), localSaleDetailsViewHolder.pBar1);
      populateUI(localSaleDetailsViewHolder.product2, localPairedProducts.getSecondProduct(), localSaleDetailsViewHolder.pBar2);
      return paramView;
      localSaleDetailsViewHolder = (SaleDetailsViewHolder)paramView.getTag();
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

  static class SaleDetailsViewHolder
  {
    ProgressBar pBar1;
    ProgressBar pBar2;
    ProductView product1;
    ProductView product2;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.SalesDetailsAdapter
 * JD-Core Version:    0.6.2
 */