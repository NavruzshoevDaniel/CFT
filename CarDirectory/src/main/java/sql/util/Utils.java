package sql.util;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static List<String> getAttributes(Class clazz) {
        List<String> fieldNames = new ArrayList<>();
        List<Field> fieldList = getFields(clazz, new ArrayList<>());

        for (Field field : fieldList) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

    public static List<Field> getFields(Class clazz, List<Field> fields) {
        if (clazz == null)
            return fields;
        fields = getFields(clazz.getSuperclass(), fields);
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return fields;
    }
}
