package ru.touchin.customserializer.serializer;

public interface Serializer {

    public <TObject> byte[] serialize(TObject object) throws Exception;

    public <TObject> TObject deserialize(byte[] byteArray) throws Exception;

}
