package com.fab.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FabBaseAdapter extends BaseAdapter
{
  private boolean isAllDownloading;

  public int getCount()
  {
    return 0;
  }

  public Object getItem(int paramInt)
  {
    return null;
  }

  public long getItemId(int paramInt)
  {
    return 0L;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    return null;
  }

  public boolean isAllDownloading()
  {
    return this.isAllDownloading;
  }

  public void setAllDownloading(boolean paramBoolean)
  {
    this.isAllDownloading = paramBoolean;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.utils.FabBaseAdapter
 * JD-Core Version:    0.6.2
 */