package com.fab.sale;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fab.sale.bo.ShopSortBo;
import com.fab.utils.FabHelper;
import com.fab.utils.FabUtils;
import com.fab.utils.ImageDownloader;
import com.fab.utils.TaskCompleteListener;
import java.util.ArrayList;

public class ShopSortAdapter extends BaseAdapter
{
  private Context context;
  private int selectedRowPosition;
  ArrayList<ShopSortBo> theShopSortCategories;

  public ShopSortAdapter(Context paramContext, ArrayList<ShopSortBo> paramArrayList)
  {
    this.context = paramContext;
    this.theShopSortCategories = paramArrayList;
  }

  public int getCount()
  {
    return this.theShopSortCategories.size();
  }

  public Object getItem(int paramInt)
  {
    return this.theShopSortCategories.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public int getSelectedRowPosition()
  {
    return this.selectedRowPosition;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    Drawable localDrawable1 = this.context.getResources().getDrawable(2130837577);
    Drawable localDrawable2 = this.context.getResources().getDrawable(2130837578);
    final ShopSortHolder localShopSortHolder;
    final ShopSortBo localShopSortBo;
    label171: int i;
    if (paramView == null)
    {
      paramView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903092, null);
      localShopSortHolder = new ShopSortHolder();
      localShopSortHolder.sortOptionContainer = ((RelativeLayout)paramView.findViewById(2131362052));
      localShopSortHolder.sortByValue = ((TextView)paramView.findViewById(2131362054));
      localShopSortHolder.sortRadioButton = ((ImageView)paramView.findViewById(2131362055));
      localShopSortHolder.sortIcon = ((ImageView)paramView.findViewById(2131362053));
      paramView.setTag(localShopSortHolder);
      localShopSortBo = (ShopSortBo)this.theShopSortCategories.get(paramInt);
      localShopSortHolder.sortByValue.setText(localShopSortBo.getSortValue());
      if (!localShopSortBo.isChecked())
        break label299;
      setSelectedRowPosition(paramInt);
      localShopSortHolder.sortRadioButton.setBackgroundDrawable(localDrawable1);
      if ((localShopSortHolder.sortIcon != null) && (!TextUtils.isEmpty(localShopSortBo.getValueUrl())))
      {
        i = this.context.getResources().getIdentifier(localShopSortBo.getValueUrl(), "drawable", "com.fab");
        if (i != 0)
          break label312;
        ImageDownloader localImageDownloader = new ImageDownloader(this.context, null, true, null);
        localImageDownloader.setTaskCompleteListener(new TaskCompleteListener()
        {
          public void onTaskComplete()
          {
            localShopSortHolder.sortIcon.setImageBitmap(FabHelper.getFabHelper().decodeFileForBitmap(FabHelper.getFabHelper().doesFileExists(ShopSortAdapter.this.context, FabUtils.s3MobileIconsUrl(localShopSortBo.getValueUrl()))));
          }

          public void onTaskComplete(Object paramAnonymousObject)
          {
          }
        });
        String[] arrayOfString = new String[1];
        arrayOfString[0] = FabUtils.s3MobileIconsUrl(localShopSortBo.getValueUrl());
        localImageDownloader.execute(arrayOfString);
      }
    }
    while (true)
    {
      localShopSortHolder.shopSortUrl = localShopSortBo.getValueUrl();
      return paramView;
      localShopSortHolder = (ShopSortHolder)paramView.getTag();
      break;
      label299: localShopSortHolder.sortRadioButton.setBackgroundDrawable(localDrawable2);
      break label171;
      label312: localShopSortHolder.sortIcon.setImageResource(i);
    }
  }

  public void setSelectedRowPosition(int paramInt)
  {
    this.selectedRowPosition = paramInt;
  }

  public static class ShopSortHolder
  {
    public boolean isHeader;
    public String shopSortUrl;
    public TextView sortByValue;
    public ImageView sortIcon;
    public RelativeLayout sortOptionContainer;
    public ImageView sortRadioButton;
    public TextView sortSubHeader;
    public String sortValue;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.ShopSortAdapter
 * JD-Core Version:    0.6.2
 */