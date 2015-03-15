package ru.touchin.customserializer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import ru.touchin.customserializer.serializer.CustomSerializer;
import ru.touchin.customserializer.serializer.CustomSerializerFast;
import ru.touchin.customserializer.serializer.FSTSerializer;
import ru.touchin.customserializer.serializer.KryoSerializer;
import ru.touchin.customserializer.serializer.Serializer;
import ru.touchin.customserializer.serializer.data.SimpleData;


public class MainActivity extends ActionBarActivity {

    public static final int COUNT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            doTesting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doTesting() throws Exception {
        testSerializer(new CustomSerializerFast());
        testSerializer(new CustomSerializer());
        testSerializer(KryoSerializer.Instance);
        testSerializer(FSTSerializer.Instance);
    }

    private void testSerializer(Serializer serializer) throws Exception {
        System.gc();
        Log.e("aaa0", serializer.getClass().getSimpleName());
        SimpleData[] data = new SimpleData[COUNT];
        byte[][] serialized = new byte[COUNT][];
        SimpleData[] deserialized = new SimpleData[COUNT];
        for (int i =0; i < COUNT; i++) {
            data[i] = new SimpleData();
            data[i].fillRandom();
        }
        Thread.sleep(500);
        Log.e("aaa1", "begin");
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            serialized[i] = serializer.serialize(data[i]);
        }
        Log.e("aaa1  end", String.valueOf(System.currentTimeMillis() - time));
        Thread.sleep(500);
        Log.e("aaa2", "begin");
        time = System.currentTimeMillis();
        for (int i =0; i < COUNT; i++) {
            deserialized[i] = serializer.deserialize(serialized[i]);
        }
        Log.e("aaa2  end", String.valueOf(System.currentTimeMillis() - time));
        System.gc();
    }

}
