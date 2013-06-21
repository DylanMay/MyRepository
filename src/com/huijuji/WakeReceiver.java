package com.fab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.fab.backgroundservice.ClearSDCardService;
import com.fab.utils.FabUtils;

public class WakeReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str;
    if (paramIntent != null)
    {
      str = paramIntent.getAction();
      if (!"android.intent.action.BOOT_COMPLETED".equalsIgnoreCase(str))
        break label23;
      FabUtils.setAlarm(paramContext);
    }
    while (true)
    {
      return;
      label23: if ("android.intent.action.DEVICE_STORAGE_LOW".equalsIgnoreCase(str))
      {
        if (paramContext != null)
        {
          Intent localIntent = new Intent(paramContext, ClearSDCardService.class);
          localIntent.putExtra("FORCE_CLEAR", true);
          paramContext.startService(localIntent);
        }
      }
      else if (("FAB_ALARM".equalsIgnoreCase(str)) && (paramContext != null))
        paramContext.startService(new Intent(paramContext, ClearSDCardService.class));
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.WakeReceiver
 * JD-Core Version:    0.6.2
 */