package com.service.quiz.security;

import lombok.SneakyThrows;
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
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.KeyStore;

@Configuration
public class SSLCertRestTemplateConfig {

    Logger logger = LoggerFactory.getLogger(SSLCertRestTemplateConfig.class);

    private static final String KEY_STORE_TYPE = "PKCS12";

    @Value("${server.ssl.key-store}")
    private String serverSSLKeyStore;

    @Value("${server.ssl.key-password}")
    private String serverSSLKeyStorePassword;

    private enum SSLFileLocation { CLASSPATH, LOCAL_FILE_STORAGE, WEB_URL };

    @Bean
    public RestTemplate getRestTemplate() throws Exception {
        try {
            logger.info(" server.ssl.key-store : {} ", serverSSLKeyStore);

            StringBuilder sb=new StringBuilder(serverSSLKeyStorePassword);
            logger.info(" server.ssl.key-password : {} ", sb.reverse().toString() );

            // Getting server.ssl.key-store file with different Means
            File file = getSSLFileByDifferentMeans();

            KeyStore clientStore = KeyStore.getInstance(KEY_STORE_TYPE);
            clientStore.load(new FileInputStream(file), serverSSLKeyStorePassword.toCharArray());

            logger.info(" Key-Store Loading Completed. From Path : {} ", serverSSLKeyStore );

            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            // sslContextBuilder.setProtocol("TLS"); // https://bugs.openjdk.java.net/browse/JDK-8241248
            // https://stackoverflow.com/questions/61318433/tomcat-nioendpoint-error-running-socket-processor
            sslContextBuilder.setProtocol("TLSv1.2"); // Or Use options from [ TLS, TLSv1.2, TLSv1.3, OpenSSL ]

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

    private File getSSLFileByDifferentMeans() {
        File file = null;
        try {
            file = getSSLFile(serverSSLKeyStore, SSLFileLocation.WEB_URL);
            logger.info( "{} : SSL certificate File Exist : {} , FilePath : {} ", SSLFileLocation.WEB_URL, file.exists(), (file.exists() ? file.getAbsolutePath() : "---"));
        } catch (Exception wException) {
            logger.error(" Failed - attempt 1 for [{}] , retrying Loading SSL certificate from [{}] > errorMessage : {}", SSLFileLocation.WEB_URL, SSLFileLocation.CLASSPATH, wException.getMessage());
            try {
                file = getSSLFile(serverSSLKeyStore, SSLFileLocation.CLASSPATH);
            } catch (Exception lsException) {
                logger.error(" Failed - attempt 2 for [{}], retrying Loading SSL certificate from [{}] > errorMessage : {}", SSLFileLocation.CLASSPATH, SSLFileLocation.LOCAL_FILE_STORAGE, lsException.getMessage());
                file =  getSSLFile(serverSSLKeyStore, SSLFileLocation.LOCAL_FILE_STORAGE);
                logger.info("{} : SSL certificate File Exist : {} , FilePath : {} ", SSLFileLocation.LOCAL_FILE_STORAGE, file.exists(), (file.exists() ? file.getAbsolutePath() : "---"));
            }
            if (!file.exists()) {
                logger.error(" Failed - attempt 2* for [{}], retrying Loading SSL certificate from : {}", SSLFileLocation.CLASSPATH, SSLFileLocation.LOCAL_FILE_STORAGE );
                file =  getSSLFile(serverSSLKeyStore, SSLFileLocation.LOCAL_FILE_STORAGE);
                logger.info("{} : SSL certificate File Exist : {} , FilePath : {} ", SSLFileLocation.LOCAL_FILE_STORAGE, file.exists(), (file.exists() ? file.getAbsolutePath() : "---"));
            }
        }
        return file;
    }

    /**
     *
     * @param FILE_URL_OR_PATH
     * @param fileLocation
     * @return
     */
    @SneakyThrows
    private File getSSLFile(String FILE_URL_OR_PATH, SSLFileLocation fileLocation) {
        switch (fileLocation) {
            case CLASSPATH:
                return ResourceUtils.getFile( FILE_URL_OR_PATH );
            case LOCAL_FILE_STORAGE:
                return new File( FILE_URL_OR_PATH );
            case WEB_URL: default:
                InputStream in = new URL(FILE_URL_OR_PATH).openStream();
                String FILE_NAME_WITH_PATH = System.getProperty("java.io.tmpdir")  + File.separator + FILE_URL_OR_PATH.substring(FILE_URL_OR_PATH.lastIndexOf("/") + 1 );
                Files.copy(in, Paths.get(FILE_NAME_WITH_PATH), StandardCopyOption.REPLACE_EXISTING);
                return new File(FILE_NAME_WITH_PATH);
        }
    }

}
