package domain.entity;

import domain.destiny.enums.Destination;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public record Config(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE) // Or another suitable strategy
        Long id,
        Destination destination,
        User user,
        String tableName
) {
}
