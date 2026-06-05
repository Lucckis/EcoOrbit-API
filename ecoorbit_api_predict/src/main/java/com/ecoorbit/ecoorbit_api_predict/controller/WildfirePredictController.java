package com.ecoorbit.ecoorbit_api_predict.controller;

import com.ecoorbit.ecoorbit_api_predict.dtos.PredictDTO;
import com.ecoorbit.ecoorbit_api_predict.dtos.SatelliteAnalysisResponseDTO;
import com.ecoorbit.ecoorbit_api_predict.service.PredictionPipelineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/analyze")
public class WildfirePredictController {
    private final PredictionPipelineService predictionPipelineService;

    @PostMapping("/analyze")
    public Mono<ResponseEntity<SatelliteAnalysisResponseDTO>> analyzeRegion(
            @Valid @RequestBody PredictDTO request) {

        return predictionPipelineService.analyzeRegion(request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }
}
