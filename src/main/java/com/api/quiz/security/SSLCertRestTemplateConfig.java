package com.api.quiz.security;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

@Configuration
public class SSLCertRestTemplateConfig {

    Logger logger = LoggerFactory.getLogger(SSLCertRestTemplateConfig.class);

    private static final String KEY_STORE_TYPE = "PKCS12";

    @Value("${server.ssl.key-store}")
    private String serverSSLKeyStore;

    @Value("${server.ssl.key-password}")
    private String serverSSLKeyStorePassword;

    @Bean
    public RestTemplate getRestTemplate() throws Exception {
        try {
            logger.info(" server.ssl.key-store : {} ", serverSSLKeyStore);
           // serverSSLKeyStore = "classpath:" + serverSSLKeyStore;
            StringBuilder sb=new StringBuilder(serverSSLKeyStorePassword);
            logger.info(" server.ssl.key-password : {} ", sb.reverse().toString() );

            File file = ResourceUtils.getFile(serverSSLKeyStore);
            logger.info(" SSL certificate File Exist : {} , FilePath : {} ", file.exists(), (file.exists() ? file.getAbsolutePath() : "---") );

            if ( !file.exists() ) {
                file = new File( serverSSLKeyStore ) ;
                logger.info("Retry-> SSL certificate File Exist : {} , FilePath : {} ", file.exists(), (file.exists() ? file.getAbsolutePath() : "---") );
            }

            KeyStore clientStore = KeyStore.getInstance(KEY_STORE_TYPE);
            clientStore.load(new FileInputStream(file), serverSSLKeyStorePassword.toCharArray());

            logger.info(" Key-Store Loading Completed. From Path : {} ", serverSSLKeyStore );

            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            sslContextBuilder.setProtocol("TLS");
            sslContextBuilder.loadKeyMaterial(clientStore, serverSSLKeyStorePassword.toCharArray());
            sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslConnectionSocketFactory)
                    .build();

            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            requestFactory.setConnectTimeout(10000); // 10 seconds
            requestFactory.setReadTimeout(10000); // 10 seconds

            return new RestTemplate(requestFactory);
        } catch ( Exception exception ) {
            logger.info(" ---NOTICE : Rest Template Loading Failed ");
            logger.error( "ERROR: Loading Rest Template With SSL-Certificates Message [ {} ] , Cause[ {} ]", exception.getMessage(), exception.getCause() );
        }
        return  null;
    }

}
