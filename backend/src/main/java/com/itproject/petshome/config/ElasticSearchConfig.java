package com.itproject.petshome.config;

import lombok.AllArgsConstructor;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
@AllArgsConstructor
@Configuration
public class ElasticSearchConfig {
    ElasticSearchProperties elasticSearchProperties;

    private static java.security.cert.Certificate loadCertificate(String filePath) throws CertificateException, IOException {
        try (FileInputStream fileInputStream = new FileInputStream(new File(filePath))) {
            java.security.cert.CertificateFactory certFactory = java.security.cert.CertificateFactory.getInstance("X.509");
            return certFactory.generateCertificate(fileInputStream);
        }
    }
    @Bean
    public RestClient elasticsearchClient() throws NoSuchAlgorithmException, KeyManagementException, CertificateException, KeyStoreException, IOException {
            String cacrtPath = "backend/http_ca.pem";

            // Load the CA certificate
            KeyStore truststore = KeyStore.getInstance("PKCS12");
            truststore.load(null, null);
            truststore.setCertificateEntry("ca", loadCertificate(cacrtPath));
            // Load the SSL certificate
            SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(truststore, null)
                .build();
            // Configure credentials provider
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
                    elasticSearchProperties.getUsername(),
                    elasticSearchProperties.getPassword()));

            HttpHost httpHost = new HttpHost("127.0.0.1", 9200, "https");


        RestClient restClient = RestClient
                .builder(httpHost)
                .setHttpClientConfigCallback(hc -> hc
                        .setSSLContext(sslContext)
                        .setDefaultCredentialsProvider(credentialsProvider)
                )
                .build();

        return restClient;

    }
}