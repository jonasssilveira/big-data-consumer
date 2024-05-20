package domain.destiny;

import domain.entity.DynamoTableConfig;
import domain.entity.DynamodbAttribute;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Dynamodb {

    private final String dynamoEndpoint;
    private final String region;
    private final Long readCapacityUnits;
    private final Long writeCapacityUnits;

    public Dynamodb(
            @Value("aws.dynamodb.endpoint") String dynamoEndpoint,
            @Value("aws.region") String region,
            @Value("aws.dynamodb.units.capacity.read") Long readCapacityUnits,
            @Value("aws.dynamodb.units.capacity.write") Long writeCapacityUnits) {
        this.dynamoEndpoint = dynamoEndpoint;
        this.readCapacityUnits = readCapacityUnits;
        this.writeCapacityUnits = writeCapacityUnits;
        this.region = region;
    }

    public void create(DynamoTableConfig dynamoTableConfig) {
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.of(this.region))
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .endpointOverride(URI.create(this.dynamoEndpoint))
                .build();
        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();

        dynamoTableConfig.attributes().forEach(
                (attribute) -> attributeDefinitions.add(AttributeDefinition.builder()
                        .attributeName(attribute.key())
                        .attributeType(attribute.type().getAttributeType())
                        .build()
                )
        );
        AtomicReference<KeySchemaElement> keySchemaElement = new AtomicReference<>();
        dynamoTableConfig.attributes().stream().filter(DynamodbAttribute::primaryKey).forEach((attribute) -> {
            keySchemaElement.set(KeySchemaElement.builder()
                    .attributeName(attribute.key())
                    .keyType(KeyType.HASH)
                    .build());
        });
        ProvisionedThroughput provisionedThroughput = ProvisionedThroughput.builder()
                .readCapacityUnits(this.readCapacityUnits)
                .writeCapacityUnits(this.writeCapacityUnits)
                .build();

        // Crie uma solicitação de criação de tabela
        CreateTableRequest request = CreateTableRequest.builder()
                .tableName(dynamoTableConfig.tableName())
                .keySchema(keySchemaElement.get())
                .attributeDefinitions(attributeDefinitions)
                .provisionedThroughput(provisionedThroughput)
                .build();

        // Envie a solicitação para criar a tabela
        CreateTableResponse response = ddb.createTable(request);

        // Imprima o ARN da tabela recém-criada
        System.out.println("Table ARN: " + response.tableDescription().tableArn());

        // Feche o cliente DynamoDB
        ddb.close();
    }

}
