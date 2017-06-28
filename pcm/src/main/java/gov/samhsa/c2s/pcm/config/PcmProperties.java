package gov.samhsa.c2s.pcm.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "c2s.pcm")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PcmProperties {

    @NotEmpty
    private List<String> supportedProviderSystems = new ArrayList<>();

    @NotNull
    private Consent consent;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Consent {

        @Valid
        private Pagination pagination = new Pagination();

        @Valid
        private Publish publish;

        @Valid
        private ShareConsentTypeConfigured shareConsentTypeConfigured;

        @Data
        public static class Publish {
            @NotNull
            private boolean enabled;

            @NotEmpty
            private String serverUrl;

            @NotEmpty
            private String clientSocketTimeoutInMs;
        }

        @Data
        public static class ShareConsentTypeConfigured {
            @NotNull
            private boolean enabled;

        }

        @Data
        public static class Pagination{
            @Min(1)
            private int defaultSize = 10;
            private int maxSize = 50;
        }
    }
}
