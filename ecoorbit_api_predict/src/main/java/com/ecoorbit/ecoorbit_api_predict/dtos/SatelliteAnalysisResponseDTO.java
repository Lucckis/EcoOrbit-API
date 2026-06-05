package com.ecoorbit.ecoorbit_api_predict.dtos;

import java.time.LocalDate;

public record SatelliteAnalysisResponseDTO(
        String status,
        CoordinatesDTO coordinates,
        LocalDate analysisDate,
        Boolean fireDetected,
        Double confidencePercentage
) {

    public record CoordinatesDTO(Double lat, Double lon) {}
}
