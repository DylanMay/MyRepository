package com.fab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.fab.backgroundservice.SalePollNotificationService;

public class SaleWakeReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ("FAB_PULL_NOTIFICATION_ALARM".equalsIgnoreCase(paramIntent.getAction()))
      paramContext.startService(new Intent(paramContext, SalePollNotificationService.class));
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.SaleWakeReceiver
 * JD-Core Version:    0.6.2
 */