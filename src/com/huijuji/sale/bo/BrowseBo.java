package com.fab.sale.bo;

import java.util.ArrayList;

public class BrowseBo
{
  private String header;
  private ArrayList<CategoryBo> theCategories;
  private String value;

  public ArrayList<CategoryBo> getCategories()
  {
    return this.theCategories;
  }

  public String getHeader()
  {
    return this.header;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setCategories(ArrayList<CategoryBo> paramArrayList)
  {
    this.theCategories = paramArrayList;
  }

  public void setHeader(String paramString)
  {
    this.header = paramString;
  }

  public void setValue(String paramString)
  {
    this.value = paramString;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.bo.BrowseBo
 * JD-Core Version:    0.6.2
 */