package com.fab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.fab.backgroundservice.SaleNotificationService;

public class SaleNotificationWakeReceiver extends BroadcastReceiver
{
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

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str = paramIntent.getAction();
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
    if ((str.contains("FAB_SALE_NOTIFICATION_ALARM")) && (this.saleType != null) && (this.msg != null))
    {
      Intent localIntent2 = new Intent(paramContext, SaleNotificationService.class);
      localIntent2.putExtra("SALE_TYPE", this.saleType);
      localIntent2.putExtra("MSG", this.msg);
      localIntent2.putExtra("SALE_ID", this.saleId);
      localIntent2.putExtra("PROD_ID", this.prodId);
      localIntent2.putExtra("SHOP_ID", this.shopId);
      localIntent2.putExtra("WEEKLY_SHOP_ID", this.weeklyShopId);
      localIntent2.putExtra("GO_TO", this.goTo);
      localIntent2.putExtra("GO_TO_URL", this.goToUrl);
      localIntent2.putExtra("IS_SSL", this.isSSL);
      localIntent2.putExtra("IS_CHECK_LOGIN", this.isCheckLogin);
      paramContext.startService(localIntent2);
    }
    if ((str.contains("WEEKLY_SHOP_NOTIFICATION")) && (this.msg != null))
    {
      Intent localIntent1 = new Intent(paramContext, SaleNotificationService.class);
      localIntent1.putExtra("MSG", this.msg);
      localIntent1.putExtra("WEEKLY_SHOP_ID", this.weeklyShopId);
      paramContext.startService(localIntent1);
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.SaleNotificationWakeReceiver
 * JD-Core Version:    0.6.2
 */