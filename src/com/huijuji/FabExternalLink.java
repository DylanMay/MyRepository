package com.fab;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.fab.sale.ProductDetailsActivity;
import com.fab.sale.SaleDetailsActivity;
import com.fab.sale.ShopDetailsActivity;
import com.fab.sale.WeeklyShopDetailsActivity;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;

public class FabExternalLink extends Activity
{
  private void callHomeActivity(int paramInt)
  {
    Intent localIntent = new Intent(this, FabHome.class);
    localIntent.putExtra("TAB_ID", paramInt);
    localIntent.setFlags(603979776);
    startActivity(localIntent);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent1 = getIntent();
    String str1;
    String str2;
    String str3;
    String str4;
    String str5;
    String str6;
    String str7;
    String str8;
    boolean bool;
    int i;
    if (FabUtils.isLoggedIn(this))
      if ("android.intent.action.VIEW".equals(localIntent1.getAction()))
      {
        Uri localUri = localIntent1.getData();
        str1 = localUri.getQueryParameter("sale_id");
        str2 = localUri.getQueryParameter("filter_type");
        str3 = localUri.getQueryParameter("product_id");
        str4 = localUri.getQueryParameter("shop_id");
        str5 = localUri.getQueryParameter("weekly_shop_id");
        str6 = localUri.getQueryParameter("goto");
        str7 = localUri.getQueryParameter("go_to_page");
        str8 = localUri.getQueryParameter("check_signin");
        String str9 = localUri.getQueryParameter("is_secure");
        bool = true;
        if ((str9 != null) && (str9.equalsIgnoreCase("1")))
          bool = true;
        i = 0;
        if (str2 != null)
        {
          if (!"ending".equalsIgnoreCase(str2))
            break label207;
          i = 2;
        }
        if (str3 == null)
          break label275;
        if (str1 == null)
          break label266;
      }
    while (true)
    {
      try
      {
        Intent localIntent6 = new Intent(this, ProductDetailsActivity.class);
        localIntent6.putExtra("SALE_ID", Integer.parseInt(str1));
        localIntent6.putExtra("PRODUCT_ID", Integer.parseInt(str3));
        startActivity(localIntent6);
        finish();
        return;
        label207: if ("upcoming".equalsIgnoreCase(str2))
        {
          i = 3;
          break;
        }
        if ("shops".equalsIgnoreCase(str2))
        {
          i = 1;
          break;
        }
        if (!"current".equalsIgnoreCase(str2))
          break;
        i = 0;
      }
      catch (NumberFormatException localNumberFormatException5)
      {
        callHomeActivity(i);
        continue;
      }
      label266: callHomeActivity(i);
      continue;
      label275: if (str1 != null)
      {
        try
        {
          Intent localIntent5 = new Intent(this, SaleDetailsActivity.class);
          startActivity(localIntent5.putExtra("SALE_ID", Integer.parseInt(str1)).putExtra("SALE_CATEGORY", str2));
        }
        catch (NumberFormatException localNumberFormatException4)
        {
          callHomeActivity(i);
        }
      }
      else if (str4 != null)
      {
        try
        {
          Intent localIntent4 = new Intent(this, ShopDetailsActivity.class);
          localIntent4.putExtra("SHOP_ID", Integer.parseInt(str4));
          startActivity(localIntent4);
        }
        catch (NumberFormatException localNumberFormatException3)
        {
          callHomeActivity(i);
        }
      }
      else if (str5 != null)
      {
        try
        {
          Intent localIntent3 = new Intent(this, WeeklyShopDetailsActivity.class);
          localIntent3.putExtra("WEEKLY_SHOP_ID", Integer.parseInt(str5));
          startActivity(localIntent3);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          callHomeActivity(i);
        }
      }
      else if ((str6 != null) && (!TextUtils.isEmpty(str6)))
      {
        if ("more".equalsIgnoreCase(str6))
        {
          try
          {
            if (TextUtils.isEmpty(str7))
              break label556;
            if (Integer.parseInt(str8) != 1)
              continue;
            if (FabSharedPrefs.isGuestUser())
              break label539;
            Intent localIntent2 = new Intent(this, MoreWebViewActivity.class);
            localIntent2.putExtra("URL", FabUtils.requestUrl(bool, "/mobile/" + str7, ""));
            startActivity(localIntent2);
          }
          catch (NumberFormatException localNumberFormatException1)
          {
            callHomeActivity(i);
          }
          continue;
          label539: startActivity(new Intent(this, FabIndex.class));
          continue;
          label556: callHomeActivity(i);
        }
        else if ("invite".equalsIgnoreCase(str6))
        {
          if (FabSharedPrefs.isGuestUser())
            startActivity(new Intent(this, FabIndex.class));
          else
            startActivity(new Intent(this, InviteActivity.class));
        }
      }
      else
      {
        callHomeActivity(i);
        continue;
        startActivity(new Intent(this, FabInit.class));
      }
    }
  }

  protected void onDestroy()
  {
    super.onDestroy();
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.FabExternalLink
 * JD-Core Version:    0.6.2
 */