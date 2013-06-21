package com.fab.connection;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class FabSSLSocketFactory extends org.apache.http.conn.ssl.SSLSocketFactory
{
  SSLContext sslContext = SSLContext.getInstance("TLS");

  public FabSSLSocketFactory(KeyStore paramKeyStore)
    throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
  {
    super(paramKeyStore);
    X509TrustManager local1 = new X509TrustManager()
    {
      public void checkClientTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
        throws CertificateException
      {
      }

      public void checkServerTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
        throws CertificateException
      {
      }

      public X509Certificate[] getAcceptedIssuers()
      {
        return null;
      }
    };
    SSLContext localSSLContext = this.sslContext;
    TrustManager[] arrayOfTrustManager = new TrustManager[1];
    arrayOfTrustManager[0] = local1;
    localSSLContext.init(null, arrayOfTrustManager, null);
  }

  public Socket createSocket()
    throws IOException
  {
    return this.sslContext.getSocketFactory().createSocket();
  }

  public Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean)
    throws IOException, UnknownHostException
  {
    return this.sslContext.getSocketFactory().createSocket(paramSocket, paramString, paramInt, paramBoolean);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.connection.FabSSLSocketFactory
 * JD-Core Version:    0.6.2
 */