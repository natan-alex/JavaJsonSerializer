import java.util.Collection;

public class CollectionSerializer extends JsonSerializationRequestHandler {
    public CollectionSerializer(JsonSerializationRequestHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public String serialize(Object item) {
        StringBuilder serializedCollection = new StringBuilder("[");

        ((Collection<?>) item).forEach(element -> serializedCollection
            .append(Json.serialize(element))
            .append(","));

        int serializedCollectionLength = serializedCollection.length();

        if (serializedCollectionLength > 1)
            serializedCollection.deleteCharAt(serializedCollectionLength - 1);

        serializedCollection.append("]");

        return serializedCollection.toString();
    }

    @Override
    public String handleSerializationRequest(Object itemToSerialize) {
        if (Collection.class.isAssignableFrom(itemToSerialize.getClass()))
            return serialize(itemToSerialize);

        return super.handleSerializationRequest(itemToSerialize);
    }
}
