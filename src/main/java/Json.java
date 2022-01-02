public class Json {
    private static final JsonSerializationRequestHandler serializersChain =
        new NoSerializer(
            new CharsSerializer(
                new ArraySerializer(
                    new MapSerializer(
                        new CollectionSerializer(
                            new ObjectSerializer(null))))));

    public static String serialize(Object item) {
        if (item == null)
            return "null";

        return serializersChain.handleSerializationRequest(item);
    }
}
