package com.huijuji.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import com.fab.FabApplication;
import com.fab.sale.ProductGallery;
import com.fab.utils.FabHelper;
import com.fab.utils.FabUtils;
import com.fab.utils.TaskCompleteListener;

public class FabZoomableImageView extends View
  implements TaskCompleteListener
{
  public static final int DEFAULT_SCALE_FIT_INSIDE = 0;
  public static final int DEFAULT_SCALE_ORIGINAL = 1;
  static final int DRAG = 1;
  private static final int LEFT_SWIPE = 1;
  static final int NONE = 0;
  private static final int RIGHT_SWIPE = 2;
  private static final int SWIPE_MIN_DIST = 120;
  private static final int SWIPE_THRES_SPEED = 200;
  private static final String TAG = "ZoomableImageView";
  static final int ZOOM = 2;
  Paint background;
  private int containerHeight;
  private int containerWidth;
  Context context;
  float curX;
  float curY;
  float currentScale;
  private int defaultScale;
  float easing = 0.2F;
  private GestureDetector gestureDetector;
  private Bitmap imgBitmap = null;
  private int index;
  boolean isAnimating = false;
  private boolean isFirstElement;
  private boolean isLastElement;
  public boolean isZoomedImage;
  private Handler mHandler = new Handler();
  private Runnable mUpdateImagePositionTask = new Runnable()
  {
    public void run()
    {
      if ((Math.abs(FabZoomableImageView.this.targetX - FabZoomableImageView.this.curX) < 5.0F) && (Math.abs(FabZoomableImageView.this.targetY - FabZoomableImageView.this.curY) < 5.0F))
      {
        FabZoomableImageView.this.isAnimating = false;
        FabZoomableImageView.this.mHandler.removeCallbacks(FabZoomableImageView.this.mUpdateImagePositionTask);
        float[] arrayOfFloat2 = new float[9];
        FabZoomableImageView.this.matrix.getValues(arrayOfFloat2);
        FabZoomableImageView.this.currentScale = arrayOfFloat2[0];
        FabZoomableImageView.this.curX = arrayOfFloat2[2];
        FabZoomableImageView.this.curY = arrayOfFloat2[5];
        float f3 = FabZoomableImageView.this.targetX - FabZoomableImageView.this.curX;
        float f4 = FabZoomableImageView.this.targetY - FabZoomableImageView.this.curY;
        FabZoomableImageView.this.matrix.postTranslate(f3, f4);
      }
      while (true)
      {
        FabZoomableImageView.this.invalidate();
        return;
        FabZoomableImageView.this.isAnimating = true;
        float[] arrayOfFloat1 = new float[9];
        FabZoomableImageView.this.matrix.getValues(arrayOfFloat1);
        FabZoomableImageView.this.currentScale = arrayOfFloat1[0];
        FabZoomableImageView.this.curX = arrayOfFloat1[2];
        FabZoomableImageView.this.curY = arrayOfFloat1[5];
        float f1 = 0.3F * (FabZoomableImageView.this.targetX - FabZoomableImageView.this.curX);
        float f2 = 0.3F * (FabZoomableImageView.this.targetY - FabZoomableImageView.this.curY);
        FabZoomableImageView.this.matrix.postTranslate(f1, f2);
        FabZoomableImageView.this.mHandler.postDelayed(this, 25L);
      }
    }
  };
  private Runnable mUpdateImageScale = new Runnable()
  {
    public void run()
    {
      float f1 = FabZoomableImageView.this.targetScale / FabZoomableImageView.this.currentScale;
      if (Math.abs(f1 - 1.0F) > 0.05D)
      {
        FabZoomableImageView.this.isAnimating = true;
        if (FabZoomableImageView.this.targetScale > FabZoomableImageView.this.currentScale)
        {
          float f3 = f1 - 1.0F;
          FabZoomableImageView.this.scaleChange = (1.0F + 0.2F * f3);
          FabZoomableImageView localFabZoomableImageView3 = FabZoomableImageView.this;
          localFabZoomableImageView3.currentScale *= FabZoomableImageView.this.scaleChange;
          if (FabZoomableImageView.this.currentScale > FabZoomableImageView.this.targetScale)
          {
            FabZoomableImageView localFabZoomableImageView4 = FabZoomableImageView.this;
            localFabZoomableImageView4.currentScale /= FabZoomableImageView.this.scaleChange;
            FabZoomableImageView.this.scaleChange = 1.0F;
          }
          if (FabZoomableImageView.this.scaleChange == 1.0F)
            break label331;
          FabZoomableImageView.this.matrix.postScale(FabZoomableImageView.this.scaleChange, FabZoomableImageView.this.scaleChange, FabZoomableImageView.this.targetScaleX, FabZoomableImageView.this.targetScaleY);
          FabZoomableImageView.this.mHandler.postDelayed(FabZoomableImageView.this.mUpdateImageScale, 15L);
          FabZoomableImageView.this.invalidate();
        }
      }
      while (true)
      {
        return;
        float f2 = 1.0F - f1;
        FabZoomableImageView.this.scaleChange = (1.0F - 0.5F * f2);
        FabZoomableImageView localFabZoomableImageView1 = FabZoomableImageView.this;
        localFabZoomableImageView1.currentScale *= FabZoomableImageView.this.scaleChange;
        if (FabZoomableImageView.this.currentScale >= FabZoomableImageView.this.targetScale)
          break;
        FabZoomableImageView localFabZoomableImageView2 = FabZoomableImageView.this;
        localFabZoomableImageView2.currentScale /= FabZoomableImageView.this.scaleChange;
        FabZoomableImageView.this.scaleChange = 1.0F;
        break;
        label331: FabZoomableImageView.this.isAnimating = false;
        FabZoomableImageView.this.scaleChange = 1.0F;
        FabZoomableImageView.this.matrix.postScale(FabZoomableImageView.this.targetScale / FabZoomableImageView.this.currentScale, FabZoomableImageView.this.targetScale / FabZoomableImageView.this.currentScale, FabZoomableImageView.this.targetScaleX, FabZoomableImageView.this.targetScaleY);
        FabZoomableImageView.this.currentScale = FabZoomableImageView.this.targetScale;
        FabZoomableImageView.this.mHandler.removeCallbacks(FabZoomableImageView.this.mUpdateImageScale);
        FabZoomableImageView.this.invalidate();
        FabZoomableImageView.this.checkImageConstraints();
        continue;
        FabZoomableImageView.this.isAnimating = false;
        FabZoomableImageView.this.scaleChange = 1.0F;
        FabZoomableImageView.this.matrix.postScale(FabZoomableImageView.this.targetScale / FabZoomableImageView.this.currentScale, FabZoomableImageView.this.targetScale / FabZoomableImageView.this.currentScale, FabZoomableImageView.this.targetScaleX, FabZoomableImageView.this.targetScaleY);
        FabZoomableImageView.this.currentScale = FabZoomableImageView.this.targetScale;
        FabZoomableImageView.this.mHandler.removeCallbacks(FabZoomableImageView.this.mUpdateImageScale);
        FabZoomableImageView.this.invalidate();
        FabZoomableImageView.this.checkImageConstraints();
      }
    }
  };
  Matrix matrix = new Matrix();
  float maxScale = 2.0F;
  PointF mid = new PointF();
  float minScale;
  int mode = 0;
  float oldDist = 1.0F;
  private ProductGallery productGallery;
  Matrix savedMatrix = new Matrix();
  float scaleChange;
  float scaleDampingFactor = 0.5F;
  float screenDensity;
  PointF start = new PointF();
  float targetRatio;
  float targetScale;
  float targetScaleX;
  float targetScaleY;
  float targetX;
  float targetY;
  float transitionalRatio;
  private String zoomImageUrl;

  public FabZoomableImageView(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    setFocusable(true);
    setFocusableInTouchMode(true);
    this.screenDensity = paramContext.getResources().getDisplayMetrics().density;
    initPaints();
    this.gestureDetector = new GestureDetector(new MyGestureDetector());
  }

  public FabZoomableImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    this.screenDensity = paramContext.getResources().getDisplayMetrics().density;
    initPaints();
    this.gestureDetector = new GestureDetector(new MyGestureDetector());
    this.defaultScale = 1;
    this.isZoomedImage = false;
  }

  private void checkImageConstraints()
  {
    if (this.imgBitmap == null);
    label187: label316: label338: label350: 
    while (true)
    {
      return;
      float[] arrayOfFloat = new float[9];
      this.matrix.getValues(arrayOfFloat);
      this.currentScale = arrayOfFloat[0];
      if (this.currentScale < this.minScale)
      {
        float f1 = this.minScale / this.currentScale;
        float f2 = this.containerWidth / 2;
        float f3 = this.containerHeight / 2;
        this.matrix.postScale(f1, f1, f2, f3);
        invalidate();
      }
      this.matrix.getValues(arrayOfFloat);
      this.currentScale = arrayOfFloat[0];
      this.curX = arrayOfFloat[2];
      this.curY = arrayOfFloat[5];
      int i = this.containerWidth - (int)(this.imgBitmap.getWidth() * this.currentScale);
      int j = this.containerHeight - (int)(this.imgBitmap.getHeight() * this.currentScale);
      int k = 0;
      int m = 0;
      if (i < 0)
        if (this.curX > 0.0F)
        {
          this.targetX = 0.0F;
          k = 1;
          if (j >= 0)
            break label338;
          if (this.curY <= 0.0F)
            break label316;
          this.targetY = 0.0F;
          m = 1;
        }
      while (true)
      {
        if ((k == 0) && (m == 0))
          break label350;
        if (m == 0)
          this.targetY = this.curY;
        if (k == 0)
          this.targetX = this.curX;
        this.isAnimating = true;
        this.mHandler.removeCallbacks(this.mUpdateImagePositionTask);
        this.mHandler.postDelayed(this.mUpdateImagePositionTask, 100L);
        break;
        if (this.curX >= i)
          break label187;
        this.targetX = i;
        k = 1;
        break label187;
        this.targetX = (i / 2);
        k = 1;
        break label187;
        if (this.curY < j)
        {
          this.targetY = j;
          m = 1;
          continue;
          this.targetY = (j / 2);
          m = 1;
        }
      }
    }
  }

  private void initPaints()
  {
    this.background = new Paint();
  }

  private void midPoint(PointF paramPointF, MotionEvent paramMotionEvent)
  {
    int i = 0;
    if (FabApplication.API_LEVEL >= 5)
      if (paramMotionEvent.getPointerCount() >= 2)
        i = 1;
    while (true)
    {
      if (i != 0);
      try
      {
        float f1 = paramMotionEvent.getX() + paramMotionEvent.getX();
        float f2 = paramMotionEvent.getY() + paramMotionEvent.getY();
        paramPointF.set(f1 / 2.0F, f2 / 2.0F);
        return;
        i = 0;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        while (true)
          FabUtils.log("ZoomableImageView", "Pointer count is " + paramMotionEvent.getPointerCount());
      }
    }
  }

  private float spacing(MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getX() - paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY() - paramMotionEvent.getY();
    return FloatMath.sqrt(f1 * f1 + f2 * f2);
  }

  public int getDefaultScale()
  {
    return this.defaultScale;
  }

  public int getIndex()
  {
    return this.index;
  }

  public Bitmap getPhotoBitmap()
  {
    return this.imgBitmap;
  }

  public ProductGallery getProductGallery()
  {
    return this.productGallery;
  }

  public String getZoomImageUrl()
  {
    return this.zoomImageUrl;
  }

  public boolean isBitmapNull()
  {
    if (this.imgBitmap == null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public boolean isFirstElement()
  {
    return this.isFirstElement;
  }

  public boolean isLastElement()
  {
    return this.isLastElement;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if ((this.imgBitmap != null) && (paramCanvas != null))
      paramCanvas.drawBitmap(this.imgBitmap, this.matrix, this.background);
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.containerWidth = paramInt1;
    this.containerHeight = paramInt2;
    int i;
    int j;
    int k;
    int m;
    float f1;
    if (this.imgBitmap != null)
    {
      i = this.imgBitmap.getHeight();
      j = this.imgBitmap.getWidth();
      k = 0;
      m = 0;
      if (this.defaultScale != 0)
        break label210;
      if (j <= this.containerWidth)
        break label152;
      f1 = this.containerWidth / j;
      float f3 = f1 * i;
      m = (this.containerHeight - (int)f3) / 2;
      this.matrix.setScale(f1, f1);
      this.matrix.postTranslate(0.0F, m);
    }
    while (true)
    {
      this.curX = k;
      this.curY = m;
      this.currentScale = f1;
      this.minScale = f1;
      invalidate();
      return;
      label152: f1 = this.containerHeight / i;
      float f2 = f1 * j;
      k = (this.containerWidth - (int)f2) / 2;
      this.matrix.setScale(f1, f1);
      this.matrix.postTranslate(k, 0.0F);
    }
    label210: int n;
    if (j > this.containerWidth)
    {
      n = (this.containerHeight - i) / 2;
      this.matrix.postTranslate(0.0F, n);
    }
    while (true)
    {
      this.curX = k;
      this.curY = n;
      this.currentScale = 1.0F;
      this.minScale = 1.0F;
      break;
      k = (this.containerWidth - j) / 2;
      n = (this.containerHeight - i) / 2;
      this.matrix.postTranslate(k, n);
    }
  }

  public void onTaskComplete()
  {
    this.minScale = 0.4F;
    String str = FabHelper.getFabHelper().doesFileExists(this.context, this.zoomImageUrl);
    if (!TextUtils.isEmpty(str))
    {
      setBitmapNull();
      this.imgBitmap = FabHelper.getFabHelper().decodeFileForBitmap(str);
      setBitmap(this.imgBitmap);
    }
  }

  public void onTaskComplete(Object paramObject)
  {
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.gestureDetector.onTouchEvent(paramMotionEvent));
    for (boolean bool = true; ; bool = true)
    {
      return bool;
      if (!this.isAnimating)
        break;
    }
    float[] arrayOfFloat = new float[9];
    switch (0xFF & paramMotionEvent.getAction())
    {
    case 3:
    case 4:
    default:
    case 5:
    case 0:
    case 1:
    case 6:
    case 2:
    }
    label477: float f1;
    do
    {
      do
        while (true)
        {
          invalidate();
          bool = true;
          break;
          if (!this.isAnimating)
          {
            this.savedMatrix.set(this.matrix);
            this.start.set(paramMotionEvent.getX(), paramMotionEvent.getY());
            this.mode = 1;
            continue;
            this.oldDist = spacing(paramMotionEvent);
            if ((this.oldDist > 10.0F) && (!TextUtils.isEmpty(this.zoomImageUrl)))
            {
              this.savedMatrix.set(this.matrix);
              midPoint(this.mid, paramMotionEvent);
              if (!this.isZoomedImage)
              {
                this.isZoomedImage = true;
                this.defaultScale = 0;
                this.minScale = 1.0F;
                FabHelper localFabHelper = FabHelper.getFabHelper();
                String str = localFabHelper.doesFileExists(this.context, this.zoomImageUrl);
                if (!TextUtils.isEmpty(str))
                {
                  setBitmapNull();
                  this.imgBitmap = localFabHelper.decodeFileForBitmap(str);
                  setBitmap(this.imgBitmap);
                }
                if ((TextUtils.isEmpty(str)) || (this.imgBitmap == null))
                  this.productGallery.downloadZoomImage(this.zoomImageUrl, this.index);
                this.productGallery.hideGallery();
              }
              this.mode = 2;
              continue;
              this.mode = 0;
              this.matrix.getValues(arrayOfFloat);
              this.curX = arrayOfFloat[2];
              this.curY = arrayOfFloat[5];
              this.currentScale = arrayOfFloat[0];
              if (!this.isAnimating)
              {
                checkImageConstraints();
                continue;
                if ((this.mode != 1) || (this.isAnimating))
                  break label477;
                this.matrix.set(this.savedMatrix);
                float f3 = paramMotionEvent.getX() - this.start.x;
                float f4 = paramMotionEvent.getY() - this.start.y;
                if ((f4 == 0.0F) || ((this.isZoomedImage) && (this.currentScale != this.minScale)))
                {
                  this.matrix.postTranslate(f3, f4);
                  this.matrix.getValues(arrayOfFloat);
                  this.curX = arrayOfFloat[2];
                  this.curY = arrayOfFloat[5];
                  this.currentScale = arrayOfFloat[0];
                }
              }
            }
          }
        }
      while ((this.mode != 2) || (this.isAnimating));
      f1 = spacing(paramMotionEvent);
    }
    while (f1 <= 10.0F);
    this.matrix.set(this.savedMatrix);
    float f2 = f1 / this.oldDist;
    this.matrix.getValues(arrayOfFloat);
    this.currentScale = arrayOfFloat[0];
    if (f2 * this.currentScale <= this.minScale)
      this.matrix.postScale(this.minScale / this.currentScale, this.minScale / this.currentScale, this.mid.x, this.mid.y);
    while (true)
    {
      this.matrix.getValues(arrayOfFloat);
      this.curX = arrayOfFloat[2];
      this.curY = arrayOfFloat[5];
      this.currentScale = arrayOfFloat[0];
      break;
      if (f2 * this.currentScale >= this.maxScale)
        this.matrix.postScale(this.maxScale / this.currentScale, this.maxScale / this.currentScale, this.mid.x, this.mid.y);
      else
        this.matrix.postScale(f2, f2, this.mid.x, this.mid.y);
    }
  }

  public void setBitmap(Bitmap paramBitmap)
  {
    int i;
    int j;
    int k;
    float f1;
    if (paramBitmap != null)
    {
      this.imgBitmap = paramBitmap;
      this.containerWidth = getWidth();
      this.containerHeight = getHeight();
      i = this.imgBitmap.getHeight();
      j = this.imgBitmap.getWidth();
      k = 0;
      int m = 0;
      this.matrix.reset();
      if (this.defaultScale == 0)
        if (j > this.containerWidth)
        {
          f1 = this.containerWidth / j;
          float f3 = f1 * i;
          m = (this.containerHeight - (int)f3) / 2;
          this.matrix.setScale(f1, f1);
          this.matrix.postTranslate(0.0F, m);
          this.curX = k;
          this.curY = m;
          this.currentScale = f1;
          this.minScale = f1;
        }
    }
    while (true)
    {
      invalidate();
      return;
      f1 = this.containerHeight / i;
      float f2 = f1 * j;
      k = (this.containerWidth - (int)f2) / 2;
      this.matrix.setScale(f1, f1);
      this.matrix.postTranslate(k, 0.0F);
      break;
      if (j > this.containerWidth)
      {
        n = 0;
        if (i > this.containerHeight);
        for (i1 = 0; ; i1 = (this.containerHeight - i) / 2)
        {
          this.matrix.postTranslate(0.0F, i1);
          this.curX = n;
          this.curY = i1;
          this.currentScale = 1.0F;
          this.minScale = 1.0F;
          break;
        }
      }
      int n = (this.containerWidth - j) / 2;
      if (i > this.containerHeight);
      for (int i1 = 0; ; i1 = (this.containerHeight - i) / 2)
      {
        this.matrix.postTranslate(n, i1);
        break;
      }
      Log.d("ZoomableImageView", "bitmap is null");
    }
  }

  public void setBitmapNull()
  {
    if (this.imgBitmap != null)
    {
      this.imgBitmap.recycle();
      this.imgBitmap = null;
      System.gc();
    }
  }

  public void setDefaultScale(int paramInt)
  {
    this.defaultScale = paramInt;
  }

  public void setFirstElement(boolean paramBoolean)
  {
    this.isFirstElement = paramBoolean;
  }

  public void setIndex(int paramInt)
  {
    this.index = paramInt;
  }

  public void setLastElement(boolean paramBoolean)
  {
    this.isLastElement = paramBoolean;
  }

  public void setMinScale(float paramFloat)
  {
    this.minScale = paramFloat;
  }

  public void setProductGallery(ProductGallery paramProductGallery)
  {
    this.productGallery = paramProductGallery;
  }

  public void setZoomImageUrl(String paramString)
  {
    this.zoomImageUrl = paramString;
  }

  class MyGestureDetector extends GestureDetector.SimpleOnGestureListener
  {
    MyGestureDetector()
    {
    }

    public boolean onDoubleTap(MotionEvent paramMotionEvent)
    {
      if (FabZoomableImageView.this.isAnimating);
      while (TextUtils.isEmpty(FabZoomableImageView.this.zoomImageUrl))
        return true;
      FabZoomableImageView.this.savedMatrix.set(FabZoomableImageView.this.matrix);
      FabZoomableImageView.this.midPoint(FabZoomableImageView.this.mid, paramMotionEvent);
      if (!FabZoomableImageView.this.isZoomedImage)
      {
        FabZoomableImageView.this.productGallery.hideGallery();
        FabZoomableImageView.this.isZoomedImage = true;
        FabZoomableImageView.this.defaultScale = 0;
        FabZoomableImageView.this.minScale = 1.0F;
        FabHelper localFabHelper = FabHelper.getFabHelper();
        String str = localFabHelper.doesFileExists(FabZoomableImageView.this.context, FabZoomableImageView.this.zoomImageUrl);
        if (!TextUtils.isEmpty(str))
        {
          FabZoomableImageView.this.setBitmapNull();
          FabZoomableImageView.this.imgBitmap = localFabHelper.decodeFileForBitmap(str);
          FabZoomableImageView.this.setBitmap(FabZoomableImageView.this.imgBitmap);
        }
        if ((TextUtils.isEmpty(str)) || (FabZoomableImageView.this.imgBitmap == null))
          FabZoomableImageView.this.productGallery.downloadZoomImage(FabZoomableImageView.this.zoomImageUrl, FabZoomableImageView.this.index);
      }
      FabZoomableImageView.this.scaleChange = 1.0F;
      FabZoomableImageView.this.isAnimating = true;
      FabZoomableImageView.this.targetScaleX = paramMotionEvent.getX();
      FabZoomableImageView.this.targetScaleY = paramMotionEvent.getY();
      if (Math.abs(FabZoomableImageView.this.currentScale - FabZoomableImageView.this.maxScale) > 0.1D);
      for (FabZoomableImageView.this.targetScale = FabZoomableImageView.this.maxScale; ; FabZoomableImageView.this.targetScale = FabZoomableImageView.this.minScale)
      {
        FabZoomableImageView.this.targetRatio = (FabZoomableImageView.this.targetScale / FabZoomableImageView.this.currentScale);
        FabZoomableImageView.this.mHandler.removeCallbacks(FabZoomableImageView.this.mUpdateImageScale);
        FabZoomableImageView.this.mHandler.post(FabZoomableImageView.this.mUpdateImageScale);
        break;
      }
    }

    public boolean onDown(MotionEvent paramMotionEvent)
    {
      return false;
    }

    public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
    {
      int i;
      if (!FabZoomableImageView.this.isAnimating)
        i = 0;
      boolean bool;
      try
      {
        if (Math.abs(paramMotionEvent1.getY() - paramMotionEvent2.getY()) > 100.0F)
        {
          bool = false;
        }
        else
        {
          if (paramMotionEvent1.getX() - paramMotionEvent2.getX() > 120.0F)
          {
            float f2 = Math.abs(paramFloat1);
            if (f2 > 200.0F)
              i = 1;
          }
          while (true)
          {
            FabZoomableImageView.this.scaleChange = 1.0F;
            FabZoomableImageView.this.isAnimating = true;
            FabZoomableImageView.this.targetScale = FabZoomableImageView.this.minScale;
            FabZoomableImageView.this.targetRatio = (FabZoomableImageView.this.targetScale / FabZoomableImageView.this.currentScale);
            FabZoomableImageView.this.mHandler.removeCallbacks(FabZoomableImageView.this.mUpdateImageScale);
            FabZoomableImageView.this.mHandler.post(FabZoomableImageView.this.mUpdateImageScale);
            if (i != 1)
              break;
            if (!FabZoomableImageView.this.isLastElement)
              FabZoomableImageView.this.setBitmapNull();
            FabZoomableImageView.this.productGallery.showNextElement(i);
            bool = super.onFling(paramMotionEvent1, paramMotionEvent2, paramFloat1, paramFloat2);
            break label282;
            if (paramMotionEvent2.getX() - paramMotionEvent1.getX() > 120.0F)
            {
              float f1 = Math.abs(paramFloat1);
              if (f1 > 200.0F)
                i = 2;
            }
          }
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          FabUtils.log("ZoomableImageView", localException.getLocalizedMessage());
          continue;
          if ((i == 2) && (!FabZoomableImageView.this.isFirstElement))
            FabZoomableImageView.this.setBitmapNull();
        }
      }
      label282: return bool;
    }

    public boolean onSingleTapConfirmed(MotionEvent paramMotionEvent)
    {
      FabZoomableImageView.this.productGallery.showOrHideGallery();
      return super.onSingleTapConfirmed(paramMotionEvent);
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.FabZoomableImageView
 * JD-Core Version:    0.6.2
 */