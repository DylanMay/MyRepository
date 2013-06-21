package com.fab.sale;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.fab.sale.bo.CategoryBo;
import com.fab.utils.FabHelper;
import com.fab.utils.FabUtils;
import com.fab.utils.ImageDownloader;
import com.fab.utils.TaskCompleteListener;
import java.util.ArrayList;

public class ColorsAdapter extends BaseAdapter
{
  private Context context;
  ArrayList<CategoryBo> theColors;

  public ColorsAdapter(Context paramContext, ArrayList<CategoryBo> paramArrayList)
  {
    this.context = paramContext;
    this.theColors = paramArrayList;
  }

  public int getCount()
  {
    return this.theColors.size();
  }

  public Object getItem(int paramInt)
  {
    return this.theColors.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    final ColorHolder localColorHolder;
    CategoryBo localCategoryBo;
    int i;
    if (paramView == null)
    {
      paramView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903045, null);
      localColorHolder = new ColorHolder();
      localColorHolder.colorImage = ((ImageView)paramView.findViewById(2131361826));
      paramView.setTag(localColorHolder);
      localCategoryBo = (CategoryBo)this.theColors.get(paramInt);
      localColorHolder.colorName = localCategoryBo.getShopName();
      i = this.context.getResources().getIdentifier(localColorHolder.colorName, "drawable", "com.fab");
      if (i != 0)
        break label182;
      ImageDownloader localImageDownloader = new ImageDownloader(this.context, null, true, null);
      localImageDownloader.setTaskCompleteListener(new TaskCompleteListener()
      {
        public void onTaskComplete()
        {
          localColorHolder.colorImage.setImageBitmap(FabHelper.getFabHelper().decodeFileForBitmap(FabHelper.getFabHelper().doesFileExists(ColorsAdapter.this.context, FabUtils.s3MobileIconsUrl(localColorHolder.colorName))));
        }

        public void onTaskComplete(Object paramAnonymousObject)
        {
        }
      });
      String[] arrayOfString = new String[1];
      arrayOfString[0] = FabUtils.s3MobileIconsUrl(localColorHolder.colorName);
      localImageDownloader.execute(arrayOfString);
    }
    while (true)
    {
      localColorHolder.shopId = localCategoryBo.getShopId();
      return paramView;
      localColorHolder = (ColorHolder)paramView.getTag();
      break;
      label182: localColorHolder.colorImage.setBackgroundResource(i);
    }
  }

  public static class ColorHolder
  {
    public ImageView colorImage;
    public String colorName;
    public int shopId;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.ColorsAdapter
 * JD-Core Version:    0.6.2
 */