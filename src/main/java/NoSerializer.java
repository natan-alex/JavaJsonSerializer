import java.util.Map;

public class NoSerializer extends JsonSerializationRequestHandler {
    private static final Map<Class<?>, Class<?>> wrappersAndPrimitives = Map.of(
        Boolean.class, boolean.class,
        Byte.class, byte.class,
        Short.class, short.class,
        Integer.class, int.class,
        Long.class, long.class,
        Float.class, float.class,
        Double.class, double.class
    );

    public NoSerializer(JsonSerializationRequestHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public String serialize(Object item) {
        return item.toString();
    }

    @Override
    public String handleSerializationRequest(Object itemToSerialize) {
        Class<?> itemClass = itemToSerialize.getClass();

        if (itemClass.isPrimitive() || wrappersAndPrimitives.get(itemClass) != null) {
            return serialize(itemToSerialize);
        }

        return super.handleSerializationRequest(itemToSerialize);
    }
}
