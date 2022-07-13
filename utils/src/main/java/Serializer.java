import java.io.*;

public class Serializer {
    public static Object convertBytesToObject(byte[] bytes) {
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objStream = new ObjectInputStream(byteStream);
            return objStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new String(bytes);
        }
    }

    public static byte[] convertObjectToBytes(Object object) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
            objStream.writeObject(object);
            objStream.flush();
            return byteStream.toByteArray();
        } catch (IOException e) {
            return object.toString().getBytes();
        }
    }
}
