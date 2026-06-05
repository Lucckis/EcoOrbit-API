package com.ecoorbit.ecoorbit_api_predict.entity;

import lombok.AllArgsConstructor;

import java.util.Locale;

@AllArgsConstructor
public class BoundingBox {
    private Double lat_top_right;
    private Double lon_top_right;
    private Double lat_bottom_left;
    private Double lon_bottom_left;

    public String toNasaFormat() {
        return String.format(Locale.US, "%.6f,%.6f,%.6f,%.6f",
                lat_bottom_left,
                lon_bottom_left,
                lat_top_right,
                lon_top_right);
    }
}
