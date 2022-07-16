package utils;

import exceptions.SerializerException;

import java.io.*;

public class Serializer {
    public static Object convertBytesToObject(byte[] bytes) throws SerializerException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        return convertByteStreamToObject(byteStream);
    }

    public static Object convertByteStreamToObject(InputStream stream) throws SerializerException {
        try {
            ObjectInputStream objStream = new ObjectInputStream(stream);
            return objStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SerializerException("Cannot convert bytes to object");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SerializerException("Class was not found to convert from bytes");
        }
    }

    public static byte[] convertObjectToBytes(Object object) throws SerializerException {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
            objStream.writeObject(object);
            objStream.flush();
            return byteStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SerializerException("Cannot convert " + object.getClass() + " to bytes");
        }
    }
}
