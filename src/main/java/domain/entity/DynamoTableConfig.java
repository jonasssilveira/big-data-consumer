package domain.entity;

import java.util.List;

public record DynamoTableConfig(
        String tableName,
        List<DynamodbAttribute> attributes) {
}
