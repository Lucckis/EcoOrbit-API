package com.ecoorbit.ecoorbit_api_predict.transformer;

import com.ecoorbit.ecoorbit_api_predict.entity.BoundingBox;
import org.springframework.stereotype.Component;

@Component
public class BoundingBoxTransformer {
    private static final double DELTA_EM_GRAUS = 0.2522;

    public BoundingBox toBoundingBox(Double lat, Double lon){
        Double lat_top_right = Math.max(90.0, lat - DELTA_EM_GRAUS);
        Double lon_top_right = Math.max(180, lon - DELTA_EM_GRAUS);

        Double lat_bottom_left = Math.max(-90.0, lat - DELTA_EM_GRAUS);
        Double lon_bottom_left = Math.max(-180.0, lon - DELTA_EM_GRAUS);


        return new BoundingBox(lat_top_right,lon_top_right, lat_bottom_left, lon_bottom_left);
    }
}
