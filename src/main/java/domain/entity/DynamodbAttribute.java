package domain.entity;

import domain.destiny.enums.AttributeType;

public record DynamodbAttribute(String key,
                                AttributeType type,
                                Boolean primaryKey) {
}
