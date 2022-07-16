package connection.data_saving;

import encryptors.EncryptorService;
import encryptors.EncryptorsFactory;
import exceptions.DataSavingException;
import exceptions.SerializerException;
import utils.ByteString;
import utils.Serializer;

import java.io.*;
import java.util.stream.Collectors;

public class FileStorage implements DataStorageService {
    private final EncryptorService encryptor = EncryptorsFactory.getEncryptor(1);
    private String filePath;

    public FileStorage(String filePath) {
        changeFilePath(filePath);
    }

    public void changeFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveData(Object object) {
        try (FileWriter writer = new FileWriter(filePath)) {

            ByteString byteString = new ByteString(
                    encryptor.encrypt(Serializer.convertObjectToBytes(object)));

            writer.write(byteString.getBytesString());
        } catch (IOException | SerializerException e) {
            throw new DataSavingException("Saving to file is failed");
        }
    }

    @Override
    public Object loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder byteString = new StringBuilder();
            for (String line : reader.lines().collect(Collectors.toList()))
                byteString.append(line).append('\n');

            byteString.deleteCharAt(byteString.length() - 1);

            return Serializer.convertBytesToObject(
                    encryptor.decrypt(ByteString.convertByteStringToBytes(byteString.toString())));
        } catch (IOException | SerializerException e) {
            throw new DataSavingException("Loading from file is failed");
        }
    }

}
