package ru.touchin.customserializer.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CustomSerializer implements Serializer {

    @Override
    public <TObject> byte[] serialize(TObject object) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(object);
        oos.flush();
        oos.close();
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public <TObject> TObject deserialize(byte[] byteArray) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
        ObjectInputStream oin = new ObjectInputStream(byteArrayInputStream);
        TObject ts = (TObject) oin.readObject();
        return ts;
    }

}
