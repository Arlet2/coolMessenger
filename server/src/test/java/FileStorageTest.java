import connection.data_saving.DataStorageService;
import connection.data_saving.FileStorage;
import encryptors.EncryptorService;
import encryptors.EncryptorsFactory;
import exceptions.DataSavingException;
import exceptions.SerializerException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import utils.ByteString;
import utils.Serializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class FileStorageTest {
    private static final String testFileName = "test.txt";

    @AfterClass
    public static void clearingAfterTests() {
        new File(testFileName).delete();
    }

    @Test
    public void readingFileTest() throws DataSavingException {
        String input = "HELLO MY DEAR ALEXANDR PAVLOV!!!!";
        DataStorageService storageService = new FileStorage(1, testFileName);

        storageService.saveData(input);
        String result = (String) storageService.loadData();

        Assert.assertEquals(input, result);
    }

    @Test
    public void bigStructureSavingTest() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("Mummy");
        DataStorageService storageService = new FileStorage( 1, testFileName);

        storageService.saveData(list);

        ArrayList<String> result = (ArrayList<String>) storageService.loadData();

        Assert.assertEquals(list, result);
    }

}
