package com.fab.sale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.fab.sale.bo.BrowseBo;
import com.fab.sale.bo.CategoryBo;
import java.util.ArrayList;

public class BrowseAdapter extends BaseAdapter
{
  private Context context;
  ArrayList<BrowseBo> theBrowseItems;

  public BrowseAdapter(Context paramContext, ArrayList<BrowseBo> paramArrayList)
  {
    this.context = paramContext;
    this.theBrowseItems = paramArrayList;
  }

  public int getCount()
  {
    return this.theBrowseItems.size();
  }

  public Object getItem(int paramInt)
  {
    return this.theBrowseItems.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    BrowseItemsHolder localBrowseItemsHolder;
    if (paramView == null)
    {
      paramView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903042, null);
      localBrowseItemsHolder = new BrowseItemsHolder();
      localBrowseItemsHolder.header = ((TextView)paramView.findViewById(2131361805));
      localBrowseItemsHolder.value = ((TextView)paramView.findViewById(2131361806));
      paramView.setTag(localBrowseItemsHolder);
    }
    while (true)
    {
      BrowseBo localBrowseBo = (BrowseBo)this.theBrowseItems.get(paramInt);
      localBrowseItemsHolder.header.setText(localBrowseBo.getHeader());
      localBrowseItemsHolder.value.setText(localBrowseBo.getValue());
      localBrowseItemsHolder.title = localBrowseBo.getHeader();
      paramView.setTag(localBrowseItemsHolder);
      return paramView;
      localBrowseItemsHolder = (BrowseItemsHolder)paramView.getTag();
    }
  }

  public static class BrowseItemsHolder
  {
    public TextView header;
    public ArrayList<CategoryBo> theCategories;
    public String title;
    public TextView value;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.BrowseAdapter
 * JD-Core Version:    0.6.2
 */