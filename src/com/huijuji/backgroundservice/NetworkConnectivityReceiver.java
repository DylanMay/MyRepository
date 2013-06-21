package com.fab.backgroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.fab.utils.FabSharedPrefs;
import java.io.PrintStream;

public class NetworkConnectivityReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (!paramIntent.getBooleanExtra("noConnectivity", false))
    {
      System.out.println("NETWORK IS CONNECTED !!!!!!!!!!!!!!!!!!!!!!!!!!");
      if (paramContext != null)
        FabSharedPrefs.initializePreferences(paramContext);
      if (FabSharedPrefs.isPollingRequired())
      {
        System.out.println("STARTING    SERVICE !!!!!!!!!!!!!!!!!!!!!!!!!!");
        paramContext.startService(new Intent(paramContext, SalePollNotificationService.class));
      }
      if (FabSharedPrefs.getSaleNotification())
      {
        System.out.println("STARTING    SERVICE !!!!!!!!!!!!!!!!!!!!!!!!!!");
        Intent localIntent = new Intent(paramContext, SaleNotificationService.class);
        localIntent.putExtra("NAME", FabSharedPrefs.getSaleNotificationName());
        localIntent.putExtra("URL", FabSharedPrefs.getSaleNotificationUrl());
        paramContext.startService(localIntent);
      }
    }
    while (true)
    {
      return;
      System.out.println("NETWORK IS NOT CONNECTED !!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.backgroundservice.NetworkConnectivityReceiver
 * JD-Core Version:    0.6.2
 */