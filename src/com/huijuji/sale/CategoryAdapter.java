package com.fab.sale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.fab.sale.bo.CategoryBo;
import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter
{
  private Context context;
  ArrayList<CategoryBo> theCategories;

  public CategoryAdapter(Context paramContext, ArrayList<CategoryBo> paramArrayList)
  {
    this.context = paramContext;
    this.theCategories = paramArrayList;
  }

  public int getCount()
  {
    return this.theCategories.size();
  }

  public Object getItem(int paramInt)
  {
    return this.theCategories.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    CategoryHolder localCategoryHolder;
    if (paramView == null)
    {
      paramView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903043, null);
      localCategoryHolder = new CategoryHolder();
      localCategoryHolder.name = ((TextView)paramView.findViewById(2131361807));
      localCategoryHolder.value = ((TextView)paramView.findViewById(2131361808));
      paramView.setTag(localCategoryHolder);
    }
    while (true)
    {
      CategoryBo localCategoryBo = (CategoryBo)this.theCategories.get(paramInt);
      localCategoryHolder.name.setText(localCategoryBo.getShopName());
      localCategoryHolder.value.setText(localCategoryBo.getShopTotalProducts());
      localCategoryHolder.shopId = localCategoryBo.getShopId();
      localCategoryHolder.shopType = localCategoryBo.getShopType();
      return paramView;
      localCategoryHolder = (CategoryHolder)paramView.getTag();
    }
  }

  public static class CategoryHolder
  {
    public TextView name;
    public int shopId;
    public String shopType;
    public TextView value;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.CategoryAdapter
 * JD-Core Version:    0.6.2
 */