package Dino.Duett.config;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class ConfigBean {
    private final EnvBean envBean;
    @Bean
    public Storage storage() throws IOException {
        ClassPathResource resource = new ClassPathResource(envBean.getKeyName());
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        return StorageOptions.newBuilder()
                .setProjectId(envBean.getProjectId())
                .setCredentials(credentials)
                .build()
                .getService();
    }

    @Bean
    public YouTube youtube() {
        // YouTube Data API에 접근할 수 있는 YouTube 클라이언트 생성
        return new YouTube.Builder(
                new NetHttpTransport(),
                new GsonFactory(),
                request -> {
                })
                .setApplicationName("Duett")
                .build();
    }

}
