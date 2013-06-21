package com.huijuji.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.fab.sale.ProductZoomBo;
import java.io.File;

public class FabImageView extends ImageView
{
  static final int DRAG = 1;
  static final int NONE = 0;
  protected static final int SUCCESS = 0;
  private static final String TAG = "FabImageView";
  static final int ZOOM = 2;
  private Bitmap bitmap;
  Context context;
  private int counter = 0;
  protected File file;
  private FabImageView iView = this;
  public boolean isDownloading;
  private String isZoomed;
  Matrix matrix = new Matrix();
  PointF mid = new PointF();
  int mode = 0;
  private float newHt;
  private float newWt;
  float oldDist = 1.0F;
  private float originalHt;
  private float originalWt;
  private ProductZoomBo productZoom;
  public boolean running;
  Matrix savedMatrix = new Matrix();
  private float scale;
  PointF start = new PointF();
  private String zoomImageUrl;

  public FabImageView(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    this.productZoom = ((ProductZoomBo)getTag());
  }

  public FabImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    this.productZoom = ((ProductZoomBo)getTag());
  }

  public FabImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.context = paramContext;
    this.productZoom = ((ProductZoomBo)getTag());
  }

  public float getNewHt()
  {
    return this.newHt;
  }

  public float getNewWt()
  {
    return this.newWt;
  }

  public float getOriginalHt()
  {
    return this.originalHt;
  }

  public float getOriginalWt()
  {
    return this.originalWt;
  }

  public String getZoomImageUrl()
  {
    return this.zoomImageUrl;
  }

  public void setNewHt(float paramFloat)
  {
    this.newHt = paramFloat;
  }

  public void setNewWt(float paramFloat)
  {
    this.newWt = paramFloat;
  }

  public void setOriginalHt(float paramFloat)
  {
    this.originalHt = paramFloat;
  }

  public void setOriginalWt(float paramFloat)
  {
    this.originalWt = paramFloat;
  }

  public void setZoomImageUrl(String paramString)
  {
    this.zoomImageUrl = paramString;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.FabImageView
 * JD-Core Version:    0.6.2
 */