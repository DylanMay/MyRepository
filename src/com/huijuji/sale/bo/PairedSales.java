package com.fab.sale.bo;

public class PairedSales
{
  private SalesBO firstSale;
  private SalesBO secondSale;

  public SalesBO getFirstSale()
  {
    return this.firstSale;
  }

  public SalesBO getSecondSale()
  {
    return this.secondSale;
  }

  public void setFirstSale(SalesBO paramSalesBO)
  {
    this.firstSale = paramSalesBO;
  }

  public void setSecondSale(SalesBO paramSalesBO)
  {
    this.secondSale = paramSalesBO;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.bo.PairedSales
 * JD-Core Version:    0.6.2
 */