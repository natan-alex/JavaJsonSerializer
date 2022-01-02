import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjectSerializer extends JsonSerializationRequestHandler {
    public ObjectSerializer(JsonSerializationRequestHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public String serialize(Object item) {
        StringBuilder serializedObj = new StringBuilder("{");
        Field[] classFields = item.getClass().getDeclaredFields();

        for (Field field : classFields) {
            if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
            }

            serializedObj.append(serializeAClassField(field, item));
        }

        serializedObj.deleteCharAt(serializedObj.length() - 1);
        serializedObj.append("}");

        return serializedObj.toString();
    }

    private String serializeAClassField(Field field, Object fieldOwner) {
        Object fieldValue = tryGetFieldValue(field, fieldOwner);

        return "\"" +
            field.getName() +
            "\":" +
            Json.serialize(fieldValue) +
            ",";
    }

    private Object tryGetFieldValue(Field field, Object fieldOwner) {
        try {
            return field.get(fieldOwner);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public String handleSerializationRequest(Object itemToSerialize) {
        return serialize(itemToSerialize);
    }
}
