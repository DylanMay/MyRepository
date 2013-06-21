package com.fab.sale.bo;

import java.util.ArrayList;

public class SaleDetailsBO
{
  private String curatorBy;
  private String curatorCompany;
  private String curatorFullName;
  private String curatorImageUrl;
  private String curatorMessage;
  private String curatorTitle;
  private String htmlDescription;
  private String primaryTitle;
  private int saleId;
  private String saleImageUrl;
  private String saleName;
  private String salesDuration;
  private ArrayList<ProductDetailsBO> theProducts;

  public String getCuratorBy()
  {
    return this.curatorBy;
  }

  public String getCuratorCompany()
  {
    return this.curatorCompany;
  }

  public String getCuratorFullName()
  {
    return this.curatorFullName;
  }

  public String getCuratorImageUrl()
  {
    return this.curatorImageUrl;
  }

  public String getCuratorMessage()
  {
    return this.curatorMessage;
  }

  public String getCuratorTitle()
  {
    return this.curatorTitle;
  }

  public String getHtmlDescription()
  {
    return this.htmlDescription;
  }

  public String getPrimaryTitle()
  {
    return this.primaryTitle;
  }

  public int getSaleId()
  {
    return this.saleId;
  }

  public String getSaleImageUrl()
  {
    return this.saleImageUrl;
  }

  public String getSaleName()
  {
    return this.saleName;
  }

  public String getSalesDuration()
  {
    return this.salesDuration;
  }

  public ArrayList<ProductDetailsBO> getTheProducts()
  {
    return this.theProducts;
  }

  public void setCuratorBy(String paramString)
  {
    this.curatorBy = paramString;
  }

  public void setCuratorCompany(String paramString)
  {
    this.curatorCompany = paramString;
  }

  public void setCuratorFullName(String paramString)
  {
    this.curatorFullName = paramString;
  }

  public void setCuratorImageUrl(String paramString)
  {
    this.curatorImageUrl = paramString;
  }

  public void setCuratorMessage(String paramString)
  {
    this.curatorMessage = paramString;
  }

  public void setCuratorTitle(String paramString)
  {
    this.curatorTitle = paramString;
  }

  public void setHtmlDescription(String paramString)
  {
    this.htmlDescription = paramString;
  }

  public void setPrimaryTitle(String paramString)
  {
    this.primaryTitle = paramString;
  }

  public void setSaleId(int paramInt)
  {
    this.saleId = paramInt;
  }

  public void setSaleImageUrl(String paramString)
  {
    this.saleImageUrl = paramString;
  }

  public void setSaleName(String paramString)
  {
    this.saleName = paramString;
  }

  public void setSalesDuration(String paramString)
  {
    this.salesDuration = paramString;
  }

  public void setTheProducts(ArrayList<ProductDetailsBO> paramArrayList)
  {
    this.theProducts = paramArrayList;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.bo.SaleDetailsBO
 * JD-Core Version:    0.6.2
 */