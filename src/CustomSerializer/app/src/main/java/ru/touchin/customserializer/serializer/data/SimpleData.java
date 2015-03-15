package ru.touchin.customserializer.serializer.data;

import java.io.Serializable;
import java.util.Random;

public class SimpleData implements Serializable {

    public static final int COUNT = 100;

    private int test1;

    private long test2;

    private short test3;

    private String test4;

    private int[] test5 = new int[COUNT];

    private Class test6 = String.class;

    public void fillRandom() {
        Random random = new Random();
        test1 = random.nextInt();
        test2 = random.nextInt();
        test3 = (short) random.nextInt();
        test4 = String.valueOf(random.nextInt());
        for (int i = 0; i < COUNT; i++) {
            test5[i] = random.nextInt();
        }
    }

}
