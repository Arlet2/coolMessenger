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
    private final EncryptorService encryptor;
    private String filePath;

    public FileStorage(int encryptionProtocol, String filePath) {
        encryptor = EncryptorsFactory.getEncryptor(encryptionProtocol);
        changeFilePath(filePath);
    }

    public void changeFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveData(Object object) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            ByteString byteString = new ByteString(
                    encryptor.encrypt(Serializer.convertObjectToBytes(object)));

            writer.write(byteString.getBytesString());
            writer.flush();
        } catch (IOException | SerializerException e) {
            throw new DataSavingException("Saving to file is failed", e);
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
                    encryptor.decrypt(new ByteString(byteString.toString()).getBytes())
            );
        } catch (IOException | SerializerException e) {
            throw new DataSavingException("Loading from file is failed", e);
        }
    }

}
