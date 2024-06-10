package br.com.portfolio.model;

import java.time.LocalDate;

public record Epoch(LocalDate start, LocalDate end, Boolean current) {
}
