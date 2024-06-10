package br.com.portfolio.exception;

public record MessageException(int status, String error, String message) {
}
