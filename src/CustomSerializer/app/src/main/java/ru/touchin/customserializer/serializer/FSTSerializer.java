package ru.touchin.customserializer.serializer;

import org.nustaq.serialization.FSTConfiguration;

public enum FSTSerializer implements Serializer {
    Instance;

    private FSTConfiguration fstConfiguration;

    FSTSerializer() {
        fstConfiguration = FSTConfiguration.createDefaultConfiguration().setForceSerializable(true);
        fstConfiguration.setShareReferences(false);
    }

    @Override
    public <TObject> byte[] serialize(TObject object) {
        return fstConfiguration.asByteArray(object);
//        try {
//            FSTObjectOutput fstObjectOutput = new FSTObjectOutput(fstConfiguration);
//            fstObjectOutput.writeObject(object);
//            byte[] bytes = fstObjectOutput.getCopyOfWrittenBuffer();
//            fstObjectOutput.close();
//            return bytes;
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <TObject> TObject deserialize(byte[] byteArray) {
        return (TObject) fstConfiguration.asObject(byteArray);
//        try {
//            FSTObjectInput fstObjectInput = new FSTObjectInput(fstConfiguration);
//            fstObjectInput.resetForReuseUseArray(byteArray);
//            TObject object = (TObject) fstObjectInput.readObject();
//            fstObjectInput.close();
//            return object;
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
    }

}
