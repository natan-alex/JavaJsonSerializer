public abstract class JsonSerializationRequestHandler {
    private final JsonSerializationRequestHandler nextHandler;
    public abstract String serialize(Object item);

    public JsonSerializationRequestHandler(JsonSerializationRequestHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public String handleSerializationRequest(Object itemToSerialize) {
        return nextHandler.handleSerializationRequest(itemToSerialize);
    }
}
