package ru.touchin.customserializer.serializer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CustomSerializerFast implements Serializer {

    @Override
    public <TObject> byte[] serialize(TObject object) throws Exception {
        MyBOUS byteArrayOutputStream = new MyBOUS();
        ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(object);
        oos.flush();
        oos.close();
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public <TObject> TObject deserialize(byte[] byteArray) throws Exception {
        MyBIUS byteArrayInputStream = new MyBIUS(byteArray);
        ObjectInputStream oin = new ObjectInputStream(byteArrayInputStream);
        TObject ts = (TObject) oin.readObject();
        return ts;
    }

}
