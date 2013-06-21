package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.MenuItem;

public class MenuCompat
{
  static final MenuVersionImpl IMPL;

  static
  {
    if (Build.VERSION.SDK_INT >= 11);
    for (IMPL = new HoneycombMenuVersionImpl(); ; IMPL = new BaseMenuVersionImpl())
      return;
  }

  public static boolean setShowAsAction(MenuItem paramMenuItem, int paramInt)
  {
    return IMPL.setShowAsAction(paramMenuItem, paramInt);
  }

  static class HoneycombMenuVersionImpl
    implements MenuCompat.MenuVersionImpl
  {
    public boolean setShowAsAction(MenuItem paramMenuItem, int paramInt)
    {
      MenuItemCompatHoneycomb.setShowAsAction(paramMenuItem, paramInt);
      return true;
    }
  }

  static class BaseMenuVersionImpl
    implements MenuCompat.MenuVersionImpl
  {
    public boolean setShowAsAction(MenuItem paramMenuItem, int paramInt)
    {
      return false;
    }
  }

  static abstract interface MenuVersionImpl
  {
    public abstract boolean setShowAsAction(MenuItem paramMenuItem, int paramInt);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.MenuCompat
 * JD-Core Version:    0.6.2
 */