package br.com.portfolio.model;

import java.util.List;

public record Experience(String nameCompany, String role, List<String> responsability, Address address, Epoch epoch) {
}
