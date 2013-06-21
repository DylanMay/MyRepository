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
import com.fab.views.ShopProductView;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ShopsDetailAdapter extends FabBaseAdapter
  implements TaskCompleteListener
{
  Context context;
  private ImageDownloader downloader;
  private FabHelper helper = FabHelper.getFabHelper();
  HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap();
  private ArrayList<String> theImageQueues = new ArrayList();
  ArrayList<PairedProducts> thePairedShopProducts;

  public ShopsDetailAdapter(Context paramContext, ArrayList<PairedProducts> paramArrayList)
  {
    this.context = paramContext;
    this.thePairedShopProducts = paramArrayList;
  }

  // ERROR //
  private void populateUI(ShopProductView paramShopProductView, ProductDetailsBO paramProductDetailsBO, ProgressBar paramProgressBar)
  {
    // Byte code:
    //   0: aload_1
    //   1: aload_2
    //   2: invokevirtual 71	com/fab/views/ShopProductView:setTag	(Ljava/lang/Object;)V
    //   5: aload_1
    //   6: aload_1
    //   7: invokevirtual 75	com/fab/views/ShopProductView:getDrawable	()Landroid/graphics/drawable/Drawable;
    //   10: invokevirtual 79	com/fab/views/ShopProductView:setProdImage	(Landroid/graphics/drawable/Drawable;)V
    //   13: aload_2
    //   14: invokevirtual 85	com/fab/sale/bo/ProductDetailsBO:getName	()Ljava/lang/String;
    //   17: ifnull +797 -> 814
    //   20: aload_3
    //   21: iconst_0
    //   22: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   25: aload_0
    //   26: getfield 49	com/fab/sale/ShopsDetailAdapter:imageCache	Ljava/util/HashMap;
    //   29: invokevirtual 95	java/util/HashMap:size	()I
    //   32: bipush 20
    //   34: if_icmple +7 -> 41
    //   37: aload_0
    //   38: invokevirtual 98	com/fab/sale/ShopsDetailAdapter:clearImageCache	()V
    //   41: aconst_null
    //   42: astore 4
    //   44: aload_0
    //   45: getfield 49	com/fab/sale/ShopsDetailAdapter:imageCache	Ljava/util/HashMap;
    //   48: aload_2
    //   49: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   52: invokevirtual 105	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   55: ifnull +499 -> 554
    //   58: aload_0
    //   59: getfield 49	com/fab/sale/ShopsDetailAdapter:imageCache	Ljava/util/HashMap;
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
    //   111: getfield 49	com/fab/sale/ShopsDetailAdapter:imageCache	Ljava/util/HashMap;
    //   114: aload_2
    //   115: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   118: invokevirtual 125	java/util/HashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   121: pop
    //   122: aload 11
    //   124: ifnull +207 -> 331
    //   127: aload 11
    //   129: invokevirtual 118	android/graphics/Bitmap:getHeight	()I
    //   132: bipush 255
    //   134: if_icmpne +13 -> 147
    //   137: aload 11
    //   139: invokevirtual 115	android/graphics/Bitmap:getWidth	()I
    //   142: bipush 255
    //   144: if_icmpeq +187 -> 331
    //   147: aload_3
    //   148: iconst_4
    //   149: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   152: aload_1
    //   153: aload 11
    //   155: invokevirtual 128	com/fab/views/ShopProductView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   158: aload_2
    //   159: invokevirtual 131	com/fab/sale/bo/ProductDetailsBO:getDiscountPct	()Ljava/lang/String;
    //   162: ldc 133
    //   164: invokevirtual 139	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   167: ifeq +583 -> 750
    //   170: aload_1
    //   171: ldc 141
    //   173: invokevirtual 145	com/fab/views/ShopProductView:setDiscount	(Ljava/lang/String;)V
    //   176: aload_1
    //   177: invokevirtual 149	com/fab/views/ShopProductView:getDiscountPctRibbon	()Landroid/widget/ImageView;
    //   180: bipush 8
    //   182: invokevirtual 152	android/widget/ImageView:setVisibility	(I)V
    //   185: aload_2
    //   186: invokevirtual 155	com/fab/sale/bo/ProductDetailsBO:getStatus	()Ljava/lang/String;
    //   189: invokestatic 161	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   192: ifne +599 -> 791
    //   195: aload_1
    //   196: aload_2
    //   197: invokevirtual 155	com/fab/sale/bo/ProductDetailsBO:getStatus	()Ljava/lang/String;
    //   200: invokevirtual 164	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   203: invokevirtual 167	com/fab/views/ShopProductView:setProdStatus	(Ljava/lang/String;)V
    //   206: aload_1
    //   207: invokevirtual 171	com/fab/views/ShopProductView:getProdStatus	()Landroid/widget/TextView;
    //   210: iconst_0
    //   211: invokevirtual 174	android/widget/TextView:setVisibility	(I)V
    //   214: aload_1
    //   215: aload_2
    //   216: invokevirtual 85	com/fab/sale/bo/ProductDetailsBO:getName	()Ljava/lang/String;
    //   219: invokevirtual 177	com/fab/views/ShopProductView:setProdName	(Ljava/lang/String;)V
    //   222: aload_1
    //   223: aload_2
    //   224: invokevirtual 180	com/fab/sale/bo/ProductDetailsBO:getProductBy	()Ljava/lang/String;
    //   227: invokevirtual 183	com/fab/views/ShopProductView:setProdBy	(Ljava/lang/String;)V
    //   230: aload_1
    //   231: aload_2
    //   232: invokevirtual 186	com/fab/sale/bo/ProductDetailsBO:getFabPrice	()Ljava/lang/String;
    //   235: invokevirtual 189	com/fab/views/ShopProductView:setFabPrice	(Ljava/lang/String;)V
    //   238: aload_1
    //   239: invokevirtual 193	com/fab/views/ShopProductView:getOriginalPrice	()Lcom/fab/views/FabTextView;
    //   242: bipush 16
    //   244: aload_1
    //   245: invokevirtual 193	com/fab/views/ShopProductView:getOriginalPrice	()Lcom/fab/views/FabTextView;
    //   248: invokevirtual 198	com/fab/views/FabTextView:getPaintFlags	()I
    //   251: ior
    //   252: invokevirtual 201	com/fab/views/FabTextView:setPaintFlags	(I)V
    //   255: aload_2
    //   256: invokevirtual 186	com/fab/sale/bo/ProductDetailsBO:getFabPrice	()Ljava/lang/String;
    //   259: aload_2
    //   260: invokevirtual 204	com/fab/sale/bo/ProductDetailsBO:getMsrp	()Ljava/lang/String;
    //   263: invokevirtual 139	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   266: ifne +536 -> 802
    //   269: aload_1
    //   270: aload_2
    //   271: invokevirtual 204	com/fab/sale/bo/ProductDetailsBO:getMsrp	()Ljava/lang/String;
    //   274: invokevirtual 207	com/fab/views/ShopProductView:setOriginalPrice	(Ljava/lang/String;)V
    //   277: aload_1
    //   278: invokevirtual 193	com/fab/views/ShopProductView:getOriginalPrice	()Lcom/fab/views/FabTextView;
    //   281: iconst_0
    //   282: invokevirtual 208	com/fab/views/FabTextView:setVisibility	(I)V
    //   285: aload_1
    //   286: new 8	com/fab/sale/ShopsDetailAdapter$1
    //   289: dup
    //   290: aload_0
    //   291: invokespecial 211	com/fab/sale/ShopsDetailAdapter$1:<init>	(Lcom/fab/sale/ShopsDetailAdapter;)V
    //   294: invokevirtual 215	com/fab/views/ShopProductView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   297: aload_1
    //   298: invokevirtual 219	com/fab/views/ShopProductView:getProductContainer	()Landroid/widget/RelativeLayout;
    //   301: iconst_0
    //   302: invokevirtual 222	android/widget/RelativeLayout:setVisibility	(I)V
    //   305: aload_1
    //   306: invokevirtual 225	com/fab/views/ShopProductView:getDummyProductHolder	()Landroid/widget/ImageView;
    //   309: bipush 8
    //   311: invokevirtual 152	android/widget/ImageView:setVisibility	(I)V
    //   314: return
    //   315: astore 20
    //   317: aconst_null
    //   318: astore 11
    //   320: goto -198 -> 122
    //   323: astore 19
    //   325: aconst_null
    //   326: astore 11
    //   328: goto -206 -> 122
    //   331: aload_0
    //   332: getfield 39	com/fab/sale/ShopsDetailAdapter:helper	Lcom/fab/utils/FabHelper;
    //   335: aload_0
    //   336: getfield 51	com/fab/sale/ShopsDetailAdapter:context	Landroid/content/Context;
    //   339: aload_2
    //   340: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   343: invokevirtual 229	com/fab/utils/FabHelper:doesFileExists	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   346: astore 12
    //   348: aload 12
    //   350: invokestatic 161	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   353: ifne +56 -> 409
    //   356: aload_0
    //   357: getfield 39	com/fab/sale/ShopsDetailAdapter:helper	Lcom/fab/utils/FabHelper;
    //   360: aload 12
    //   362: invokevirtual 233	com/fab/utils/FabHelper:decodeFileForBitmap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   365: astore 11
    //   367: aload 11
    //   369: ifnull +23 -> 392
    //   372: aload 11
    //   374: invokevirtual 118	android/graphics/Bitmap:getHeight	()I
    //   377: bipush 255
    //   379: if_icmpeq +13 -> 392
    //   382: aload 11
    //   384: invokevirtual 115	android/graphics/Bitmap:getWidth	()I
    //   387: bipush 255
    //   389: if_icmpne +20 -> 409
    //   392: new 235	java/io/File
    //   395: dup
    //   396: aload 12
    //   398: invokespecial 237	java/io/File:<init>	(Ljava/lang/String;)V
    //   401: invokevirtual 241	java/io/File:delete	()Z
    //   404: pop
    //   405: ldc 141
    //   407: astore 12
    //   409: aload 11
    //   411: ifnull +44 -> 455
    //   414: aload 11
    //   416: aload 11
    //   418: invokevirtual 115	android/graphics/Bitmap:getWidth	()I
    //   421: iconst_2
    //   422: idiv
    //   423: aload 11
    //   425: invokevirtual 118	android/graphics/Bitmap:getHeight	()I
    //   428: iconst_2
    //   429: idiv
    //   430: invokevirtual 122	android/graphics/Bitmap:getPixel	(II)I
    //   433: ifne +22 -> 455
    //   436: ldc 141
    //   438: astore 12
    //   440: aconst_null
    //   441: astore 11
    //   443: aload_0
    //   444: getfield 49	com/fab/sale/ShopsDetailAdapter:imageCache	Ljava/util/HashMap;
    //   447: aload_2
    //   448: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   451: invokevirtual 125	java/util/HashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   454: pop
    //   455: aload 12
    //   457: invokestatic 161	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   460: ifne +28 -> 488
    //   463: aload 11
    //   465: ifnull +23 -> 488
    //   468: aload 11
    //   470: invokevirtual 118	android/graphics/Bitmap:getHeight	()I
    //   473: bipush 255
    //   475: if_icmpeq +13 -> 488
    //   478: aload 11
    //   480: invokevirtual 115	android/graphics/Bitmap:getWidth	()I
    //   483: bipush 255
    //   485: if_icmpne +29 -> 514
    //   488: aload_3
    //   489: iconst_0
    //   490: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   493: aload_0
    //   494: invokevirtual 244	com/fab/sale/ShopsDetailAdapter:isAllDownloading	()Z
    //   497: ifne +348 -> 845
    //   500: aload_0
    //   501: invokevirtual 247	com/fab/sale/ShopsDetailAdapter:isImageDownloading	()Z
    //   504: ifne +341 -> 845
    //   507: aload_0
    //   508: invokevirtual 250	com/fab/sale/ShopsDetailAdapter:downloadTheImages	()V
    //   511: goto +334 -> 845
    //   514: aload_3
    //   515: iconst_4
    //   516: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   519: aload_1
    //   520: aload 11
    //   522: invokevirtual 128	com/fab/views/ShopProductView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   525: aload_0
    //   526: getfield 49	com/fab/sale/ShopsDetailAdapter:imageCache	Ljava/util/HashMap;
    //   529: aload_2
    //   530: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   533: new 107	java/lang/ref/SoftReference
    //   536: dup
    //   537: aload 11
    //   539: invokespecial 252	java/lang/ref/SoftReference:<init>	(Ljava/lang/Object;)V
    //   542: invokevirtual 256	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   545: pop
    //   546: goto +299 -> 845
    //   549: astore 13
    //   551: aload 13
    //   553: athrow
    //   554: aload_0
    //   555: getfield 39	com/fab/sale/ShopsDetailAdapter:helper	Lcom/fab/utils/FabHelper;
    //   558: aload_0
    //   559: getfield 51	com/fab/sale/ShopsDetailAdapter:context	Landroid/content/Context;
    //   562: aload_2
    //   563: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   566: invokevirtual 229	com/fab/utils/FabHelper:doesFileExists	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   569: astore 5
    //   571: aload 5
    //   573: invokestatic 161	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   576: ifne +36 -> 612
    //   579: aload_0
    //   580: getfield 39	com/fab/sale/ShopsDetailAdapter:helper	Lcom/fab/utils/FabHelper;
    //   583: aload 5
    //   585: invokevirtual 233	com/fab/utils/FabHelper:decodeFileForBitmap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   588: astore 4
    //   590: aload 4
    //   592: ifnonnull +20 -> 612
    //   595: new 235	java/io/File
    //   598: dup
    //   599: aload 5
    //   601: invokespecial 237	java/io/File:<init>	(Ljava/lang/String;)V
    //   604: invokevirtual 241	java/io/File:delete	()Z
    //   607: pop
    //   608: ldc 141
    //   610: astore 5
    //   612: aload 4
    //   614: ifnull +32 -> 646
    //   617: aload 4
    //   619: aload 4
    //   621: invokevirtual 115	android/graphics/Bitmap:getWidth	()I
    //   624: iconst_2
    //   625: idiv
    //   626: aload 4
    //   628: invokevirtual 118	android/graphics/Bitmap:getHeight	()I
    //   631: iconst_2
    //   632: idiv
    //   633: invokevirtual 122	android/graphics/Bitmap:getPixel	(II)I
    //   636: ifne +10 -> 646
    //   639: ldc 141
    //   641: astore 5
    //   643: aconst_null
    //   644: astore 4
    //   646: aload 5
    //   648: invokestatic 161	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   651: ifne +33 -> 684
    //   654: aload 4
    //   656: ifnull +28 -> 684
    //   659: aload 4
    //   661: ifnull +49 -> 710
    //   664: aload 4
    //   666: invokevirtual 118	android/graphics/Bitmap:getHeight	()I
    //   669: bipush 255
    //   671: if_icmpeq +13 -> 684
    //   674: aload 4
    //   676: invokevirtual 115	android/graphics/Bitmap:getWidth	()I
    //   679: bipush 255
    //   681: if_icmpne +29 -> 710
    //   684: aload_3
    //   685: iconst_0
    //   686: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   689: aload_0
    //   690: invokevirtual 244	com/fab/sale/ShopsDetailAdapter:isAllDownloading	()Z
    //   693: ifne +179 -> 872
    //   696: aload_0
    //   697: invokevirtual 247	com/fab/sale/ShopsDetailAdapter:isImageDownloading	()Z
    //   700: ifne +172 -> 872
    //   703: aload_0
    //   704: invokevirtual 250	com/fab/sale/ShopsDetailAdapter:downloadTheImages	()V
    //   707: goto +165 -> 872
    //   710: aload_3
    //   711: iconst_4
    //   712: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   715: aload_1
    //   716: aload 4
    //   718: invokevirtual 128	com/fab/views/ShopProductView:setProdImage	(Landroid/graphics/Bitmap;)V
    //   721: aload_0
    //   722: getfield 49	com/fab/sale/ShopsDetailAdapter:imageCache	Ljava/util/HashMap;
    //   725: aload_2
    //   726: invokevirtual 101	com/fab/sale/bo/ProductDetailsBO:getImageUrl	()Ljava/lang/String;
    //   729: new 107	java/lang/ref/SoftReference
    //   732: dup
    //   733: aload 4
    //   735: invokespecial 252	java/lang/ref/SoftReference:<init>	(Ljava/lang/Object;)V
    //   738: invokevirtual 256	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   741: pop
    //   742: goto +130 -> 872
    //   745: astore 6
    //   747: aload 6
    //   749: athrow
    //   750: aload_1
    //   751: new 258	java/lang/StringBuilder
    //   754: dup
    //   755: ldc_w 260
    //   758: invokespecial 261	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   761: aload_2
    //   762: invokevirtual 131	com/fab/sale/bo/ProductDetailsBO:getDiscountPct	()Ljava/lang/String;
    //   765: invokevirtual 265	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   768: ldc_w 267
    //   771: invokevirtual 265	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   774: invokevirtual 270	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   777: invokevirtual 145	com/fab/views/ShopProductView:setDiscount	(Ljava/lang/String;)V
    //   780: aload_1
    //   781: invokevirtual 149	com/fab/views/ShopProductView:getDiscountPctRibbon	()Landroid/widget/ImageView;
    //   784: iconst_0
    //   785: invokevirtual 152	android/widget/ImageView:setVisibility	(I)V
    //   788: goto -603 -> 185
    //   791: aload_1
    //   792: invokevirtual 171	com/fab/views/ShopProductView:getProdStatus	()Landroid/widget/TextView;
    //   795: iconst_4
    //   796: invokevirtual 174	android/widget/TextView:setVisibility	(I)V
    //   799: goto -585 -> 214
    //   802: aload_1
    //   803: invokevirtual 193	com/fab/views/ShopProductView:getOriginalPrice	()Lcom/fab/views/FabTextView;
    //   806: bipush 8
    //   808: invokevirtual 208	com/fab/views/FabTextView:setVisibility	(I)V
    //   811: goto -526 -> 285
    //   814: aload_1
    //   815: aconst_null
    //   816: invokevirtual 215	com/fab/views/ShopProductView:setOnClickListener	(Landroid/view/View$OnClickListener;)V
    //   819: aload_1
    //   820: invokevirtual 219	com/fab/views/ShopProductView:getProductContainer	()Landroid/widget/RelativeLayout;
    //   823: bipush 8
    //   825: invokevirtual 222	android/widget/RelativeLayout:setVisibility	(I)V
    //   828: aload_1
    //   829: invokevirtual 225	com/fab/views/ShopProductView:getDummyProductHolder	()Landroid/widget/ImageView;
    //   832: iconst_0
    //   833: invokevirtual 152	android/widget/ImageView:setVisibility	(I)V
    //   836: aload_3
    //   837: bipush 8
    //   839: invokevirtual 91	android/widget/ProgressBar:setVisibility	(I)V
    //   842: goto -528 -> 314
    //   845: goto -687 -> 158
    //   848: astore 16
    //   850: ldc 141
    //   852: astore 12
    //   854: aconst_null
    //   855: astore 11
    //   857: goto -402 -> 455
    //   860: astore 15
    //   862: ldc 141
    //   864: astore 12
    //   866: aconst_null
    //   867: astore 11
    //   869: goto -414 -> 455
    //   872: goto -714 -> 158
    //   875: astore 9
    //   877: ldc 141
    //   879: astore 5
    //   881: aconst_null
    //   882: astore 4
    //   884: goto -238 -> 646
    //   887: astore 8
    //   889: ldc 141
    //   891: astore 5
    //   893: aconst_null
    //   894: astore 4
    //   896: goto -250 -> 646
    //
    // Exception table:
    //   from	to	target	type
    //   85	122	315	java/lang/IllegalArgumentException
    //   85	122	323	java/lang/Exception
    //   348	409	549	finally
    //   414	455	549	finally
    //   455	546	549	finally
    //   571	612	745	finally
    //   617	643	745	finally
    //   646	742	745	finally
    //   414	455	848	java/lang/IllegalArgumentException
    //   414	455	860	java/lang/Exception
    //   617	643	875	java/lang/IllegalArgumentException
    //   617	643	887	java/lang/Exception
  }

  private void queueTheImages()
  {
    synchronized (this.theImageQueues)
    {
      this.theImageQueues.clear();
      Iterator localIterator = this.thePairedShopProducts.iterator();
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
        if (ShopDetailsActivity.isActivityAlive)
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
        localIterator = this.thePairedShopProducts.iterator();
        if (!localIterator.hasNext())
        {
          if (localArrayList.size() <= 0)
            break label263;
          String[] arrayOfString = new String[localArrayList.size()];
          if (ShopDetailsActivity.isActivityAlive)
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
    return this.thePairedShopProducts.size();
  }

  public PairedProducts getItem(int paramInt)
  {
    return (PairedProducts)this.thePairedShopProducts.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ShopDetailsViewHolder localShopDetailsViewHolder;
    if (paramView == null)
    {
      paramView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903091, null);
      localShopDetailsViewHolder = new ShopDetailsViewHolder();
      localShopDetailsViewHolder.product1 = ((ShopProductView)paramView.findViewById(2131362007));
      localShopDetailsViewHolder.pBar1 = ((ProgressBar)paramView.findViewById(2131361858));
      localShopDetailsViewHolder.product2 = ((ShopProductView)paramView.findViewById(2131362008));
      localShopDetailsViewHolder.pBar2 = ((ProgressBar)paramView.findViewById(2131361860));
      paramView.setTag(localShopDetailsViewHolder);
    }
    while (true)
    {
      PairedProducts localPairedProducts = (PairedProducts)this.thePairedShopProducts.get(paramInt);
      populateUI(localShopDetailsViewHolder.product1, localPairedProducts.getFirstProduct(), localShopDetailsViewHolder.pBar1);
      populateUI(localShopDetailsViewHolder.product2, localPairedProducts.getSecondProduct(), localShopDetailsViewHolder.pBar2);
      return paramView;
      localShopDetailsViewHolder = (ShopDetailsViewHolder)paramView.getTag();
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

  static class ShopDetailsViewHolder
  {
    ProgressBar pBar1;
    ProgressBar pBar2;
    ShopProductView product1;
    ShopProductView product2;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.ShopsDetailAdapter
 * JD-Core Version:    0.6.2
 */