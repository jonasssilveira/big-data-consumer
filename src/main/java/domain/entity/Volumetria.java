package domain.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public record Volumetria(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE) // Or another suitable strategy
        Long id,
        Integer quant,
        Date data,
        Config config) {
}
