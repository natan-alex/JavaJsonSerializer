import java.util.Map;

public class MapSerializer extends JsonSerializationRequestHandler {
    public MapSerializer(JsonSerializationRequestHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public String serialize(Object item) {
        StringBuilder serializedMap = new StringBuilder("{");

        ((Map<?, ?>) item).forEach((key, value) -> {
            String serializedKey = Json.serialize(key);
            char firstChar = serializedKey.charAt(0);

            if (firstChar != '[' && firstChar != '{' && firstChar != '"') {
                serializedKey = "\"" + serializedKey + "\"";
            }

            serializedMap
                .append(serializedKey)
                .append(":")
                .append(Json.serialize(value))
                .append(",");
        });

        int serializedMapLength = serializedMap.length();

        if (serializedMapLength > 1)
            serializedMap.deleteCharAt(serializedMapLength - 1);

        serializedMap.append("}");

        return serializedMap.toString();
    }

    @Override
    public String handleSerializationRequest(Object itemToSerialize) {
        Class<?> itemClass = itemToSerialize.getClass();

        if (Map.class.isAssignableFrom(itemClass))
            return serialize(itemToSerialize);

        return super.handleSerializationRequest(itemToSerialize);
    }
}
