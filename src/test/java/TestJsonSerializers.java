import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class TestJsonSerializers {
    @Test
    public void TestStringSerialization() {
        assertEquals(Json.serialize("ola"), "\"ola\"");
        assertEquals(Json.serialize("hello world!"), "\"hello world!\"");
        assertEquals(Json.serialize("o"), "\"o\"");
        assertEquals(Json.serialize(""), "\"\"");
    }

    @Test
    public void TestCharSerialization() {
        assertEquals(Json.serialize('c'), "\"c\"");
    }

    @Test
    public void TestNumberSerialization() {
        assertEquals(Json.serialize((byte) 1), Byte.toString((byte) 1));
        assertEquals(Json.serialize((short) 1), Short.toString((short) 1));
        assertEquals(Json.serialize(1), Integer.toString(1));
        assertEquals(Json.serialize(1L), Long.toString(1L));
        assertEquals(Json.serialize(1F), Float.toString(1F));
        assertEquals(Json.serialize(1.0), Double.toString(1.0));
    }

    @Test
    public void TestBooleanSerialization() {
        assertEquals(Json.serialize(true), Boolean.toString(true));
        assertEquals(Json.serialize(false), Boolean.toString(false));
    }

    @Test
    public void TestNullSerialization() {
        assertEquals(Json.serialize(null), "null");
    }

    @Test
    public void TestMapSerialization() {
        Map<?, ?> empty = new HashMap<>();

        Map<String, Integer> peopleAndTheirAges = new LinkedHashMap<>();
        peopleAndTheirAges.put("john", 10);
        peopleAndTheirAges.put("doe", 20);
        peopleAndTheirAges.put("foo", 2);
        peopleAndTheirAges.put("bar", 50);

        Map<Integer, Map<String, Integer>> mapWithInnerMap = new LinkedHashMap<>();
        mapWithInnerMap.put(0, Map.of("hello", 10));
        mapWithInnerMap.put(1, Map.of("world", 15));

        Map<Float, String[]> sizeAndObjNames = new LinkedHashMap<>();
        sizeAndObjNames.put(3.4F, new String[] {"pen", "cellphone"});
        sizeAndObjNames.put(5.0F, new String[] {"mouse", "keyboard"});

        assertEquals(Json.serialize(empty), "{}");
        assertEquals(Json.serialize(peopleAndTheirAges), "{\"john\":10,\"doe\":20,\"foo\":2,\"bar\":50}");
        assertEquals(Json.serialize(mapWithInnerMap), "{\"0\":{\"hello\":10},\"1\":{\"world\":15}}");
        assertEquals(Json.serialize(sizeAndObjNames), "{\"3.4\":[\"pen\",\"cellphone\"],\"5.0\":[\"mouse\",\"keyboard\"]}");
    }

    @Test
    public void TestArraySerialization() {
        float[] empty = new float[0];
        int[] fibonacci = new int[] {1,1,2,3,5,8,13};
        String[] names = new String[] {"john doe", "foo bar", "hello world"};

        assertEquals(Json.serialize(empty), "[]");
        assertEquals(Json.serialize(fibonacci), "[1,1,2,3,5,8,13]");
        assertEquals(Json.serialize(names), "[\"john doe\",\"foo bar\",\"hello world\"]");
    }

    @Test
    public void TestCollectionSerialization() {
        List<Integer> empty = new ArrayList<>();

        List<String> names = new ArrayList<>();
        names.add("john doe");
        names.add("foo bar");
        names.add("hello world");

        Queue<Integer> numbers = new ArrayDeque<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);

        List<int[]> matrix = new ArrayList<>();
        matrix.add(0, new int[] {1,2});
        matrix.add(1, new int[] {3,4});

        List<Person> people = new ArrayList<>();
        people.add(new Person("hey", 10));

        assertEquals(Json.serialize(empty), "[]");
        assertEquals(Json.serialize(names), "[\"john doe\",\"foo bar\",\"hello world\"]");
        assertEquals(Json.serialize(numbers), "[1,2,3,4]");
        assertEquals(Json.serialize(matrix), "[[1,2],[3,4]]");
        assertEquals(Json.serialize(people), "[{\"name\":\"hey\",\"age\":10,\"nicknames\":null,\"cellphoneNumbers\":null}]");
    }

    @Test
    public void TestClassInstanceSerialization() {
        Person person = new Person("john doe", 30);
        person.setNicknames(new String[] {"little john", "nhoj"});
        List<String> cellphoneNumbers = new ArrayList<>();
        cellphoneNumbers.add("0101");
        cellphoneNumbers.add("0000");
        person.setCellphoneNumbers(cellphoneNumbers);

        Map<Person, Integer> studentsAndChairNumbers = new LinkedHashMap<>();
        studentsAndChairNumbers.put(new Person("john doe", 11), 1);
        studentsAndChairNumbers.put(new Person("foo bar", 10), 2);
        Classroom classroom = new Classroom(studentsAndChairNumbers);

        assertEquals(Json.serialize(person),
            "{\"name\":\"john doe\",\"age\":30," +
                "\"nicknames\":[\"little john\",\"nhoj\"]," +
                "\"cellphoneNumbers\":[\"0101\",\"0000\"]}");
        assertEquals(Json.serialize(classroom),
        "{\"studentsAndChairNumbers\":{" +
            "{\"name\":\"john doe\",\"age\":11," +
            "\"nicknames\":null," +
            "\"cellphoneNumbers\":null}:1," +
            "{\"name\":\"foo bar\",\"age\":10," +
            "\"nicknames\":null," +
            "\"cellphoneNumbers\":null}:2}," +
            "\"students\":[\"john doe\",\"foo bar\"]," +
            "\"numberOfChairs\":2}");
    }

    private static class Person {
        private final String name;
        private final int age;
        private String[] nicknames;
        private List<String> cellphoneNumbers;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void setNicknames(String[] nicknames) {
            this.nicknames = nicknames;
        }

        public void setCellphoneNumbers(List<String> cellphoneNumbers) {
            this.cellphoneNumbers = cellphoneNumbers;
        }

        public String getName() {
            return name;
        }
    }

    private static class Classroom {
        private final Map<Person, Integer> studentsAndChairNumbers;
        private final List<String> students;
        private final int numberOfChairs;

        public Classroom(Map<Person, Integer> studentsAndChairNumbers) {
            this.studentsAndChairNumbers = studentsAndChairNumbers;
            numberOfChairs = studentsAndChairNumbers.size();
            students = new ArrayList<>();
            studentsAndChairNumbers.keySet().forEach(p -> students.add(p.getName()));
        }
    }
}
