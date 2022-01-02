public class CharsSerializer extends JsonSerializationRequestHandler {
    public CharsSerializer(JsonSerializationRequestHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public String serialize(Object item) {
        return "\"" + item + "\"";
    }

    @Override
    public String handleSerializationRequest(Object itemToSerialize) {
        Class<?> itemClass = itemToSerialize.getClass();

        if (itemClass == String.class || itemClass == Character.class)
            return serialize(itemToSerialize);

        return super.handleSerializationRequest(itemToSerialize);
    }
}
