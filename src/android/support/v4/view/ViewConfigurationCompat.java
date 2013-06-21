package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.ViewConfiguration;

public class ViewConfigurationCompat
{
  static final ViewConfigurationVersionImpl IMPL;

  static
  {
    if (Build.VERSION.SDK_INT >= 11);
    for (IMPL = new FroyoViewConfigurationVersionImpl(); ; IMPL = new BaseViewConfigurationVersionImpl())
      return;
  }

  public static int getScaledPagingTouchSlop(ViewConfiguration paramViewConfiguration)
  {
    return IMPL.getScaledPagingTouchSlop(paramViewConfiguration);
  }

  static class FroyoViewConfigurationVersionImpl
    implements ViewConfigurationCompat.ViewConfigurationVersionImpl
  {
    public int getScaledPagingTouchSlop(ViewConfiguration paramViewConfiguration)
    {
      return ViewConfigurationCompatFroyo.getScaledPagingTouchSlop(paramViewConfiguration);
    }
  }

  static class BaseViewConfigurationVersionImpl
    implements ViewConfigurationCompat.ViewConfigurationVersionImpl
  {
    public int getScaledPagingTouchSlop(ViewConfiguration paramViewConfiguration)
    {
      return paramViewConfiguration.getScaledTouchSlop();
    }
  }

  static abstract interface ViewConfigurationVersionImpl
  {
    public abstract int getScaledPagingTouchSlop(ViewConfiguration paramViewConfiguration);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.ViewConfigurationCompat
 * JD-Core Version:    0.6.2
 */