import java.lang.reflect.Array;

public class ArraySerializer extends JsonSerializationRequestHandler {
    public ArraySerializer(JsonSerializationRequestHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public String serialize(Object item) {
        StringBuilder serializedArray = new StringBuilder("[");
        int arraySize = Array.getLength(item);

        for (int i = 0; i < arraySize; i++) {
            serializedArray
                .append(Json.serialize(Array.get(item, i)))
                .append(",");
        }

        int serializedArrayLength = serializedArray.length();

        if (serializedArrayLength > 1)
            serializedArray.deleteCharAt(serializedArrayLength - 1);

        serializedArray.append("]");

        return serializedArray.toString();
    }

    @Override
    public String handleSerializationRequest(Object itemToSerialize) {
        if (itemToSerialize.getClass().isArray())
            return serialize(itemToSerialize);

        return super.handleSerializationRequest(itemToSerialize);
    }
}
