package android.support.v4.view;

import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

class AccessibilityDelegateCompatIcs
{
  public static boolean dispatchPopulateAccessibilityEvent(Object paramObject, View paramView, AccessibilityEvent paramAccessibilityEvent)
  {
    return ((View.AccessibilityDelegate)paramObject).dispatchPopulateAccessibilityEvent(paramView, paramAccessibilityEvent);
  }

  public static Object newAccessibilityDelegateBridge(AccessibilityDelegateBridge paramAccessibilityDelegateBridge)
  {
    return new View.AccessibilityDelegate()
    {
      public boolean dispatchPopulateAccessibilityEvent(View paramAnonymousView, AccessibilityEvent paramAnonymousAccessibilityEvent)
      {
        return AccessibilityDelegateCompatIcs.this.dispatchPopulateAccessibilityEvent(paramAnonymousView, paramAnonymousAccessibilityEvent);
      }

      public void onInitializeAccessibilityEvent(View paramAnonymousView, AccessibilityEvent paramAnonymousAccessibilityEvent)
      {
        AccessibilityDelegateCompatIcs.this.onInitializeAccessibilityEvent(paramAnonymousView, paramAnonymousAccessibilityEvent);
      }

      public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfo paramAnonymousAccessibilityNodeInfo)
      {
        AccessibilityDelegateCompatIcs.this.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfo);
      }

      public void onPopulateAccessibilityEvent(View paramAnonymousView, AccessibilityEvent paramAnonymousAccessibilityEvent)
      {
        AccessibilityDelegateCompatIcs.this.onPopulateAccessibilityEvent(paramAnonymousView, paramAnonymousAccessibilityEvent);
      }

      public boolean onRequestSendAccessibilityEvent(ViewGroup paramAnonymousViewGroup, View paramAnonymousView, AccessibilityEvent paramAnonymousAccessibilityEvent)
      {
        return AccessibilityDelegateCompatIcs.this.onRequestSendAccessibilityEvent(paramAnonymousViewGroup, paramAnonymousView, paramAnonymousAccessibilityEvent);
      }

      public void sendAccessibilityEvent(View paramAnonymousView, int paramAnonymousInt)
      {
        AccessibilityDelegateCompatIcs.this.sendAccessibilityEvent(paramAnonymousView, paramAnonymousInt);
      }

      public void sendAccessibilityEventUnchecked(View paramAnonymousView, AccessibilityEvent paramAnonymousAccessibilityEvent)
      {
        AccessibilityDelegateCompatIcs.this.sendAccessibilityEventUnchecked(paramAnonymousView, paramAnonymousAccessibilityEvent);
      }
    };
  }

  public static Object newAccessibilityDelegateDefaultImpl()
  {
    return new View.AccessibilityDelegate();
  }

  public static void onInitializeAccessibilityEvent(Object paramObject, View paramView, AccessibilityEvent paramAccessibilityEvent)
  {
    ((View.AccessibilityDelegate)paramObject).onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
  }

  public static void onInitializeAccessibilityNodeInfo(Object paramObject1, View paramView, Object paramObject2)
  {
    ((View.AccessibilityDelegate)paramObject1).onInitializeAccessibilityNodeInfo(paramView, (AccessibilityNodeInfo)paramObject2);
  }

  public static void onPopulateAccessibilityEvent(Object paramObject, View paramView, AccessibilityEvent paramAccessibilityEvent)
  {
    ((View.AccessibilityDelegate)paramObject).onPopulateAccessibilityEvent(paramView, paramAccessibilityEvent);
  }

  public static boolean onRequestSendAccessibilityEvent(Object paramObject, ViewGroup paramViewGroup, View paramView, AccessibilityEvent paramAccessibilityEvent)
  {
    return ((View.AccessibilityDelegate)paramObject).onRequestSendAccessibilityEvent(paramViewGroup, paramView, paramAccessibilityEvent);
  }

  public static void sendAccessibilityEvent(Object paramObject, View paramView, int paramInt)
  {
    ((View.AccessibilityDelegate)paramObject).sendAccessibilityEvent(paramView, paramInt);
  }

  public static void sendAccessibilityEventUnchecked(Object paramObject, View paramView, AccessibilityEvent paramAccessibilityEvent)
  {
    ((View.AccessibilityDelegate)paramObject).sendAccessibilityEventUnchecked(paramView, paramAccessibilityEvent);
  }

  public static abstract interface AccessibilityDelegateBridge
  {
    public abstract boolean dispatchPopulateAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent);

    public abstract void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent);

    public abstract void onInitializeAccessibilityNodeInfo(View paramView, Object paramObject);

    public abstract void onPopulateAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent);

    public abstract boolean onRequestSendAccessibilityEvent(ViewGroup paramViewGroup, View paramView, AccessibilityEvent paramAccessibilityEvent);

    public abstract void sendAccessibilityEvent(View paramView, int paramInt);

    public abstract void sendAccessibilityEventUnchecked(View paramView, AccessibilityEvent paramAccessibilityEvent);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.AccessibilityDelegateCompatIcs
 * JD-Core Version:    0.6.2
 */