package com.ecoorbit.ecoorbit_api_predict.service;

import com.ecoorbit.ecoorbit_api_predict.dtos.FlaskPredictionResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;

@Service
public class AIPredictionClient {
    private final WebClient webClient;

    public AIPredictionClient(WebClient.Builder webClientBuilder,
                              @Value("${api.flask}") String apiFlask) {

        this.webClient = webClientBuilder
                .baseUrl(apiFlask)
                .build();
    }

    public Mono<FlaskPredictionResponseDTO> predictFire(byte[] imageBytes) {

        ByteArrayResource imageResource = new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return "satellite_snapshot.jpg";
            }
        };

        MultiValueMap<String, Object> multipartData = new LinkedMultiValueMap<>();
        multipartData.add("imagem", imageResource);

        return this.webClient.post()
                .uri("/api/v1/predict")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartData))
                .retrieve()
                .bodyToMono(FlaskPredictionResponseDTO.class);
    }
}
