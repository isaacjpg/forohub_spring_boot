package com.forohub.infra.errores;

public record ExceptionResponse(String message, int status, long timestamp) {
}
