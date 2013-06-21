package android.support.v4.app;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class FragmentStatePagerAdapter extends PagerAdapter
{
  private static final boolean DEBUG = false;
  private static final String TAG = "FragmentStatePagerAdapter";
  private FragmentTransaction mCurTransaction = null;
  private Fragment mCurrentPrimaryItem = null;
  private final FragmentManager mFragmentManager;
  private ArrayList<Fragment> mFragments = new ArrayList();
  private ArrayList<Fragment.SavedState> mSavedState = new ArrayList();

  public FragmentStatePagerAdapter(FragmentManager paramFragmentManager)
  {
    this.mFragmentManager = paramFragmentManager;
  }

  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    Fragment localFragment = (Fragment)paramObject;
    if (this.mCurTransaction == null)
      this.mCurTransaction = this.mFragmentManager.beginTransaction();
    while (this.mSavedState.size() <= paramInt)
      this.mSavedState.add(null);
    this.mSavedState.set(paramInt, this.mFragmentManager.saveFragmentInstanceState(localFragment));
    this.mFragments.set(paramInt, null);
    this.mCurTransaction.remove(localFragment);
  }

  public void finishUpdate(ViewGroup paramViewGroup)
  {
    if (this.mCurTransaction != null)
    {
      this.mCurTransaction.commitAllowingStateLoss();
      this.mCurTransaction = null;
      this.mFragmentManager.executePendingTransactions();
    }
  }

  public abstract Fragment getItem(int paramInt);

  public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
  {
    Object localObject;
    if (this.mFragments.size() > paramInt)
    {
      localObject = (Fragment)this.mFragments.get(paramInt);
      if (localObject == null);
    }
    while (true)
    {
      return localObject;
      if (this.mCurTransaction == null)
        this.mCurTransaction = this.mFragmentManager.beginTransaction();
      Fragment localFragment = getItem(paramInt);
      if (this.mSavedState.size() > paramInt)
      {
        Fragment.SavedState localSavedState = (Fragment.SavedState)this.mSavedState.get(paramInt);
        if (localSavedState != null)
          localFragment.setInitialSavedState(localSavedState);
      }
      while (this.mFragments.size() <= paramInt)
        this.mFragments.add(null);
      localFragment.setMenuVisibility(false);
      this.mFragments.set(paramInt, localFragment);
      this.mCurTransaction.add(paramViewGroup.getId(), localFragment);
      localObject = localFragment;
    }
  }

  public boolean isViewFromObject(View paramView, Object paramObject)
  {
    if (((Fragment)paramObject).getView() == paramView);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader)
  {
    if (paramParcelable != null)
    {
      Bundle localBundle = (Bundle)paramParcelable;
      localBundle.setClassLoader(paramClassLoader);
      Parcelable[] arrayOfParcelable = localBundle.getParcelableArray("states");
      this.mSavedState.clear();
      this.mFragments.clear();
      if (arrayOfParcelable != null)
        for (int j = 0; j < arrayOfParcelable.length; j++)
          this.mSavedState.add((Fragment.SavedState)arrayOfParcelable[j]);
      Iterator localIterator = localBundle.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (str.startsWith("f"))
        {
          int i = Integer.parseInt(str.substring(1));
          Fragment localFragment = this.mFragmentManager.getFragment(localBundle, str);
          if (localFragment != null)
          {
            while (this.mFragments.size() <= i)
              this.mFragments.add(null);
            localFragment.setMenuVisibility(false);
            this.mFragments.set(i, localFragment);
          }
          else
          {
            Log.w("FragmentStatePagerAdapter", "Bad fragment at key " + str);
          }
        }
      }
    }
  }

  public Parcelable saveState()
  {
    Bundle localBundle = null;
    if (this.mSavedState.size() > 0)
    {
      localBundle = new Bundle();
      Fragment.SavedState[] arrayOfSavedState = new Fragment.SavedState[this.mSavedState.size()];
      this.mSavedState.toArray(arrayOfSavedState);
      localBundle.putParcelableArray("states", arrayOfSavedState);
    }
    for (int i = 0; i < this.mFragments.size(); i++)
    {
      Fragment localFragment = (Fragment)this.mFragments.get(i);
      if (localFragment != null)
      {
        if (localBundle == null)
          localBundle = new Bundle();
        String str = "f" + i;
        this.mFragmentManager.putFragment(localBundle, str, localFragment);
      }
    }
    return localBundle;
  }

  public void setPrimaryItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    Fragment localFragment = (Fragment)paramObject;
    if (localFragment != this.mCurrentPrimaryItem)
    {
      if (this.mCurrentPrimaryItem != null)
        this.mCurrentPrimaryItem.setMenuVisibility(false);
      if (localFragment != null)
        localFragment.setMenuVisibility(true);
      this.mCurrentPrimaryItem = localFragment;
    }
  }

  public void startUpdate(ViewGroup paramViewGroup)
  {
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.app.FragmentStatePagerAdapter
 * JD-Core Version:    0.6.2
 */