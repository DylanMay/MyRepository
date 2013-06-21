package android.support.v4.widget;

import android.content.Context;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

class SearchViewCompatHoneycomb
{
  public static Object newOnQueryTextListener(OnQueryTextListenerCompatBridge paramOnQueryTextListenerCompatBridge)
  {
    return new SearchView.OnQueryTextListener()
    {
      public boolean onQueryTextChange(String paramAnonymousString)
      {
        return SearchViewCompatHoneycomb.this.onQueryTextChange(paramAnonymousString);
      }

      public boolean onQueryTextSubmit(String paramAnonymousString)
      {
        return SearchViewCompatHoneycomb.this.onQueryTextSubmit(paramAnonymousString);
      }
    };
  }

  public static View newSearchView(Context paramContext)
  {
    return new SearchView(paramContext);
  }

  public static void setOnQueryTextListener(Object paramObject1, Object paramObject2)
  {
    ((SearchView)paramObject1).setOnQueryTextListener((SearchView.OnQueryTextListener)paramObject2);
  }

  static abstract interface OnQueryTextListenerCompatBridge
  {
    public abstract boolean onQueryTextChange(String paramString);

    public abstract boolean onQueryTextSubmit(String paramString);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.widget.SearchViewCompatHoneycomb
 * JD-Core Version:    0.6.2
 */