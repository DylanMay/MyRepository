package com.fab;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.io.Closeable;
import java.io.IOException;

class FabEula
{
  private static final String ASSET_EULA = "EULA";
  private static final String PREFERENCES_EULA = "eula";
  private static final String PREFERENCE_EULA_ACCEPTED = "eula.accepted";

  private static void accept(SharedPreferences paramSharedPreferences)
  {
    paramSharedPreferences.edit().putBoolean("eula.accepted", true).commit();
  }

  private static void closeStream(Closeable paramCloseable)
  {
    if (paramCloseable != null);
    try
    {
      paramCloseable.close();
      label10: return;
    }
    catch (IOException localIOException)
    {
      break label10;
    }
  }

  // ERROR //
  private static java.lang.CharSequence readEula(Activity paramActivity)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 67	java/io/BufferedReader
    //   5: dup
    //   6: new 69	java/io/InputStreamReader
    //   9: dup
    //   10: aload_0
    //   11: invokevirtual 75	android/app/Activity:getAssets	()Landroid/content/res/AssetManager;
    //   14: ldc 18
    //   16: invokevirtual 81	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   19: invokespecial 84	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   22: invokespecial 87	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   25: astore_2
    //   26: new 89	java/lang/StringBuilder
    //   29: dup
    //   30: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   33: astore_3
    //   34: aload_2
    //   35: invokevirtual 94	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   38: astore 6
    //   40: aload 6
    //   42: ifnonnull +9 -> 51
    //   45: aload_2
    //   46: invokestatic 96	com/fab/FabEula:closeStream	(Ljava/io/Closeable;)V
    //   49: aload_3
    //   50: areturn
    //   51: aload_3
    //   52: aload 6
    //   54: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   57: bipush 10
    //   59: invokevirtual 103	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   62: pop
    //   63: goto -29 -> 34
    //   66: astore 5
    //   68: aload_2
    //   69: astore_1
    //   70: aload_1
    //   71: invokestatic 96	com/fab/FabEula:closeStream	(Ljava/io/Closeable;)V
    //   74: ldc 105
    //   76: astore_3
    //   77: goto -28 -> 49
    //   80: astore 4
    //   82: aload_1
    //   83: invokestatic 96	com/fab/FabEula:closeStream	(Ljava/io/Closeable;)V
    //   86: aload 4
    //   88: athrow
    //   89: astore 4
    //   91: aload_2
    //   92: astore_1
    //   93: goto -11 -> 82
    //   96: astore 8
    //   98: goto -28 -> 70
    //
    // Exception table:
    //   from	to	target	type
    //   26	40	66	java/io/IOException
    //   51	63	66	java/io/IOException
    //   2	26	80	finally
    //   26	40	89	finally
    //   51	63	89	finally
    //   2	26	96	java/io/IOException
  }

  private static void refuse(Activity paramActivity)
  {
    paramActivity.finish();
  }

  static boolean show(final Activity paramActivity)
  {
    int i = 0;
    SharedPreferences localSharedPreferences = paramActivity.getSharedPreferences("eula", i);
    if (!localSharedPreferences.getBoolean("eula.accepted", i))
    {
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramActivity);
      localBuilder.setTitle("License");
      localBuilder.setCancelable(true);
      localBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          FabEula.accept(FabEula.this);
          if ((paramActivity instanceof FabEula.OnEulaAgreedTo))
            ((FabEula.OnEulaAgreedTo)paramActivity).onEulaAgreedTo();
        }
      });
      localBuilder.setNegativeButton("Refuse", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          FabEula.refuse(FabEula.this);
        }
      });
      localBuilder.setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          FabEula.refuse(FabEula.this);
        }
      });
      localBuilder.setMessage(readEula(paramActivity));
      localBuilder.create().show();
    }
    while (true)
    {
      return i;
      int j = 1;
    }
  }

  static abstract interface OnEulaAgreedTo
  {
    public abstract void onEulaAgreedTo();
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.FabEula
 * JD-Core Version:    0.6.2
 */