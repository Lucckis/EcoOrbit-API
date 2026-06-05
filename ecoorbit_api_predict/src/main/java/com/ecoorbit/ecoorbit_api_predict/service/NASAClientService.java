package com.ecoorbit.ecoorbit_api_predict.service;

import com.ecoorbit.ecoorbit_api_predict.dtos.PredictDTO;
import com.ecoorbit.ecoorbit_api_predict.entity.BoundingBox;
import com.ecoorbit.ecoorbit_api_predict.transformer.BoundingBoxTransformer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NASAClientService {
    private final WebClient webClient;

    private static final String NASA_URL = "https://wvs.earthdata.nasa.gov/api/v1/snapshot";
    private static final String NASA_LAYER = "VIIRS_SNPP_CorrectedReflectance_TrueColor";

    public NASAClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(NASA_URL)
                .defaultHeader("User-Agent", "EcoOrbit-API/1.0 (Integration/Testing)")
                .build();
    }

    public Mono<byte[]> fetchSatelliteImage(PredictDTO dto) {
        BoundingBoxTransformer boundingBoxTransformer = new BoundingBoxTransformer();
        BoundingBox boundingBox = boundingBoxTransformer.toBoundingBox(dto.lat(), dto.lon());

        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("REQUEST", "GetSnapshot")
                        .queryParam("LAYERS", NASA_LAYER)
                        .queryParam("CRS", "EPSG:4326")
                        .queryParam("TIME", dto.data().toString())
                        .queryParam("WRAP", "DAY")
                        .queryParam("BBOX",boundingBox.toNasaFormat())
                        .queryParam("FORMAT", "image/jpeg")
                        .queryParam("WIDTH", "224")
                        .queryParam("HEIGHT", "224")
                        .queryParam("AUTOSCALE", "TRUE")
                        .build()
                )
                .retrieve()
                .bodyToMono(byte[].class);
    }


}
