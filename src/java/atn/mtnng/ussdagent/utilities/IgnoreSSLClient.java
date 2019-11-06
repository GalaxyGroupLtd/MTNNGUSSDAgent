/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atn.mtnng.ussdagent.utilities;

import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 *
 * @author manzi
 */
public class IgnoreSSLClient {
  
    
    public static Client ignoreSSLClient() throws Exception {

    SSLContext sslcontext = SSLContext.getInstance("TLS");

    sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) {}
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}
        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
    }}, new java.security.SecureRandom());

    return ClientBuilder.newBuilder()
                        .sslContext(sslcontext)
                        .hostnameVerifier((s1, s2) -> true)
                        .build();
}
    
}
