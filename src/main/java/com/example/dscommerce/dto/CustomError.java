package com.example.dscommerce.dto;

import java.time.Instant;

public record CustomError(Instant timestamp, Integer status, String error, String path) {
}
