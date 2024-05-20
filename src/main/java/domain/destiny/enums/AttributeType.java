package domain.destiny.enums;

import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

public enum AttributeType {
    STRING(ScalarAttributeType.S),
    NUMBER(ScalarAttributeType.N),
    BINARY(ScalarAttributeType.B);

    private final ScalarAttributeType attributeType;

    AttributeType(ScalarAttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public ScalarAttributeType getAttributeType() {
        return attributeType;
    }
}
