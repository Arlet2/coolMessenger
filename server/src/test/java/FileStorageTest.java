import connection.data_saving.DataStorageService;
import connection.data_saving.FileStorage;
import exceptions.DataSavingException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class FileStorageTest {
    private static final String testFileName = "test.txt";

    @AfterClass
    public static void clearingAfterTests() {
        new File(testFileName).delete();
    }

    @Test
    public void readingFileTest() throws DataSavingException {
        String input = "HELLO MY DEAR ALEXANDR PAVLOV!!!!";
        DataStorageService storageService = new FileStorage(testFileName);

        storageService.saveData(input);
        String result = (String) storageService.loadData();

        Assert.assertEquals(input, result);
    }

}
