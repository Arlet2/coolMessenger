import data_saving.DataStorageService;
import data_saving.FileStorage;
import exceptions.DataSavingException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

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
