package com.ecoorbit.ecoorbit_api_predict.service;

import com.ecoorbit.ecoorbit_api_predict.dtos.FlaskPredictionResponseDTO;
import com.ecoorbit.ecoorbit_api_predict.dtos.PredictDTO;
import com.ecoorbit.ecoorbit_api_predict.dtos.SatelliteAnalysisResponseDTO;
import com.ecoorbit.ecoorbit_api_predict.entity.BoundingBox;
import com.ecoorbit.ecoorbit_api_predict.transformer.BoundingBoxTransformer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class PredictionPipelineService {

    private final BoundingBoxTransformer transformer;
    private final NASAClientService nasaClient;
    private final AIPredictionClient aiClient;

    public PredictionPipelineService(BoundingBoxTransformer transformer,
                                        NASAClientService nasaClient,
                                        AIPredictionClient aiClient) {
        this.transformer = transformer;
        this.nasaClient = nasaClient;
        this.aiClient = aiClient;
    }

    public Mono<SatelliteAnalysisResponseDTO> analyzeRegion(PredictDTO request) {

        return nasaClient.fetchSatelliteImage(request)
                .flatMap(imageBytes -> aiClient.predictFire(imageBytes))
                .map(flaskResponse -> new SatelliteAnalysisResponseDTO(
                        "SUCCESS",
                        new SatelliteAnalysisResponseDTO.CoordinatesDTO(request.lat(), request.lon()),
                        request.data(),
                        flaskResponse.fogo_detectado(),
                        flaskResponse.confianca_percentual()
                ));
    }
}