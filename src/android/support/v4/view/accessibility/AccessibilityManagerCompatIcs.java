package android.support.v4.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener;
import java.util.List;

class AccessibilityManagerCompatIcs
{
  public static boolean addAccessibilityStateChangeListener(AccessibilityManager paramAccessibilityManager, Object paramObject)
  {
    return paramAccessibilityManager.addAccessibilityStateChangeListener((AccessibilityManager.AccessibilityStateChangeListener)paramObject);
  }

  public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager paramAccessibilityManager, int paramInt)
  {
    return paramAccessibilityManager.getEnabledAccessibilityServiceList(paramInt);
  }

  public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager paramAccessibilityManager)
  {
    return paramAccessibilityManager.getInstalledAccessibilityServiceList();
  }

  public static boolean isTouchExplorationEnabled(AccessibilityManager paramAccessibilityManager)
  {
    return paramAccessibilityManager.isTouchExplorationEnabled();
  }

  public static Object newAccessibilityStateChangeListener(AccessibilityStateChangeListenerBridge paramAccessibilityStateChangeListenerBridge)
  {
    return new AccessibilityManager.AccessibilityStateChangeListener()
    {
      public void onAccessibilityStateChanged(boolean paramAnonymousBoolean)
      {
        AccessibilityManagerCompatIcs.this.onAccessibilityStateChanged(paramAnonymousBoolean);
      }
    };
  }

  public static boolean removeAccessibilityStateChangeListener(AccessibilityManager paramAccessibilityManager, Object paramObject)
  {
    return paramAccessibilityManager.removeAccessibilityStateChangeListener((AccessibilityManager.AccessibilityStateChangeListener)paramObject);
  }

  static abstract interface AccessibilityStateChangeListenerBridge
  {
    public abstract void onAccessibilityStateChanged(boolean paramBoolean);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.accessibility.AccessibilityManagerCompatIcs
 * JD-Core Version:    0.6.2
 */