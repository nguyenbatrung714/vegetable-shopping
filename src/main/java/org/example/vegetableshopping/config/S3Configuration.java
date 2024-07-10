package org.example.vegetableshopping.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Configuration {

    @Value("${cloud.aws.s3.access.key}")
    private String accessKey;

    @Value("${cloud.aws.s3.secret.key}")
    private String secretKey;

    // Trả về đối tượng s3 để tương tác với dịch vụ s3
    @Bean
    public AmazonS3 s3Client() {

        // Tạo đối tượng chứa thông tin xác thức
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);


        // Thiết lập đối tượng amazon s3
        return AmazonS3ClientBuilder
                .standard() // Tạo object mới
                .withCredentials(new AWSStaticCredentialsProvider(credentials)) // Cung cấp thông tin xác thực
                .withRegion(Regions.AP_SOUTHEAST_2) // Vị trí
                .build(); // build đối tượng
    }
}
