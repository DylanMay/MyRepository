package com.fab.backgroundservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import com.fab.FabHome;
import com.fab.FabSignin;
import com.fab.InviteActivity;
import com.fab.MoreActivity;
import com.fab.MoreWebViewActivity;
import com.fab.sale.ProductDetailsActivity;
import com.fab.sale.SaleDetailsActivity;
import com.fab.sale.ShopDetailsActivity;
import com.fab.sale.WeeklyShopDetailsActivity;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;
import com.fab.webviews.FabWebViewActivity;

public class SaleNotificationService extends Service
{
  private static final String TAG = "Sale Notification Service";
  private Bundle extra;
  private String goTo;
  private String goToUrl;
  private int isCheckLogin;
  private int isSSL;
  private String msg;
  private int prodId;
  private int saleId;
  private String saleType;
  private int shopId;
  private int weeklyShopId;

  private void announceSaleStart(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString3, String paramString4, int paramInt5, int paramInt6, int paramInt7)
  {
    Notification localNotification = new Notification(2130837670, paramString1, System.currentTimeMillis());
    Intent localIntent = null;
    int i = 0;
    int j;
    if (paramString2 != null)
    {
      if ((TextUtils.isEmpty(paramString2)) || (paramString2.equalsIgnoreCase("c")))
      {
        paramString2 = "new";
        i = 0;
      }
    }
    else
    {
      if (TextUtils.isEmpty(paramString3))
        break label418;
      if (this.isCheckLogin != 1)
        break label240;
      j = 1;
      label66: if (j == 0)
        break label246;
      if ((!FabUtils.isLoggedIn(this)) || (FabSharedPrefs.isGuestUser()))
        localIntent = new Intent(this, FabSignin.class);
    }
    label418: 
    while (true)
    {
      localIntent.addFlags(335544320);
      localNotification.setLatestEventInfo(this, "Fab.com", paramString1, PendingIntent.getActivity(this, 0, localIntent, 0));
      localNotification.defaults = (0x1 | localNotification.defaults);
      localNotification.defaults = (0x2 | localNotification.defaults);
      localNotification.defaults = (0x4 | localNotification.defaults);
      ((NotificationManager)getSystemService("notification")).notify(2131165199, localNotification);
      stopSelf(paramInt7);
      return;
      if ("e".equalsIgnoreCase(paramString2))
      {
        paramString2 = "ending";
        i = 2;
        break;
      }
      if ("u".equalsIgnoreCase(paramString2))
      {
        paramString2 = "upcoming";
        i = 3;
        break;
      }
      if ("s".equalsIgnoreCase(paramString2))
      {
        paramString2 = "featured";
        i = 1;
        break;
      }
      paramString2 = "new";
      break;
      label240: j = 0;
      break label66;
      label246: if ("invite".equalsIgnoreCase(paramString3))
      {
        localIntent = new Intent(this, InviteActivity.class);
      }
      else if ("more".equalsIgnoreCase(paramString3))
      {
        if (!TextUtils.isEmpty(paramString4))
        {
          if (this.isSSL == 1);
          for (boolean bool = true; ; bool = false)
          {
            localIntent = new Intent(this, MoreWebViewActivity.class);
            localIntent.putExtra("URL", FabUtils.requestUrl(bool, "/mobile/" + paramString4, ""));
            break;
          }
        }
        localIntent = new Intent(this, MoreActivity.class);
      }
      else if (paramString3.contains("cart"))
      {
        localIntent = new Intent(this, FabWebViewActivity.class);
      }
      else
      {
        localIntent = new Intent(this, FabHome.class);
        localIntent.putExtra("TAB_ID", i);
        continue;
        if (paramInt1 > 0)
        {
          if (paramInt2 > 0)
          {
            localIntent = new Intent(this, ProductDetailsActivity.class);
            localIntent.putExtra("SALE_ID", this.saleId);
            localIntent.putExtra("PRODUCT_ID", paramInt2);
          }
          else
          {
            localIntent = new Intent(this, SaleDetailsActivity.class);
            localIntent.putExtra("SALE_ID", this.saleId);
            localIntent.putExtra("SALE_CATEGORY", paramString2);
          }
        }
        else if (paramInt3 > 0)
        {
          localIntent = new Intent(this, WeeklyShopDetailsActivity.class);
          localIntent.putExtra("WEEKLY_SHOP_ID", paramInt3);
        }
        else if (paramInt4 > 0)
        {
          localIntent = new Intent(this, ShopDetailsActivity.class);
          localIntent.putExtra("SHOP_ID", paramInt4);
        }
        else
        {
          localIntent = new Intent(this, FabHome.class);
          localIntent.putExtra("TAB_ID", i);
        }
      }
    }
  }

  public void handleCommand(Intent paramIntent, final int paramInt)
  {
    if (paramIntent != null)
    {
      this.extra = paramIntent.getExtras();
      if (this.extra != null)
      {
        this.saleType = this.extra.getString("SALE_TYPE");
        this.msg = this.extra.getString("MSG");
        this.saleId = this.extra.getInt("SALE_ID");
        this.prodId = this.extra.getInt("PROD_ID");
        this.shopId = this.extra.getInt("SHOP_ID");
        this.weeklyShopId = this.extra.getInt("WEEKLY_SHOP_ID");
        this.goTo = this.extra.getString("GO_TO");
        this.goToUrl = this.extra.getString("GO_TO_URL");
        this.isSSL = this.extra.getInt("IS_SSL");
        this.isCheckLogin = this.extra.getInt("IS_CHECK_LOGIN");
      }
      new Thread(new Runnable()
      {
        public void run()
        {
          SaleNotificationService.this.announceSaleStart(SaleNotificationService.this.msg, SaleNotificationService.this.saleType, SaleNotificationService.this.saleId, SaleNotificationService.this.prodId, SaleNotificationService.this.weeklyShopId, SaleNotificationService.this.shopId, SaleNotificationService.this.goTo, SaleNotificationService.this.goToUrl, SaleNotificationService.this.isSSL, SaleNotificationService.this.isCheckLogin, paramInt);
        }
      }).start();
    }
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    super.onCreate();
    Log.i("Sale Notification Service", "Sale Notification Service Started");
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    handleCommand(paramIntent, paramInt);
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    handleCommand(paramIntent, paramInt2);
    return 2;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.backgroundservice.SaleNotificationService
 * JD-Core Version:    0.6.2
 */