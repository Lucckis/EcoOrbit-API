package com.ecoorbit.ecoorbit_api_predict.dtos;

public record FlaskPredictionResponseDTO(
        String arquivo_analisado,
        Boolean fogo_detectado,
        Double confianca_percentual
) {
}
