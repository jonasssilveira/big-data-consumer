package domain.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public record User(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE) // Or another suitable strategy
        Long id,
        String username,
        String password,
        String nome) {
}
