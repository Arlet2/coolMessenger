package utils;

public class ByteString {
    private final String byteString;

    public ByteString(byte[] bytes) {
        byteString = convertBytesToByteString(bytes);
    }

    public ByteString(String byteString) {
        this.byteString = byteString;
    }

    public String getBytesString() {
        return byteString;
    }

    public byte[] getBytes() {
        return convertByteStringToBytes(byteString);
    }

    public static String convertBytesToByteString(byte[] bytes) {
        char[] chars = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++)
            chars[i] = (char) bytes[i];
        return new String(chars);
    }

    public static byte[] convertByteStringToBytes(String byteString) {
        char[] chars = byteString.toCharArray();
        byte[] bytes = new byte[chars.length];
        for (int i = 0; i < chars.length; i++)
            bytes[i] = (byte) chars[i];
        return bytes;
    }

}
