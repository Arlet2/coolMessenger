import connection.BasicPackageService;
import connection.PackageService;
import connection.data_objects.NetDTO;
import exceptions.SerializerException;
import org.junit.Assert;
import org.junit.Test;

public class BasicPackageServiceTest {
    private final PackageService packageService = new BasicPackageService(1);

    @Test
    public void simplePackingAndUnpackingTest() {
        String expected = "hello";

        NetDTO dto = packageService.packData(expected, NetDTO.DataCode.MESSAGE);
        String result = (String) packageService.unpackData(dto);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void packingAndUnpackingWithEncryptionTest() {
        String expected = "hello";

        NetDTO dto = packageService.packDataWithEncryption(expected, NetDTO.DataCode.MESSAGE);
        String result = (String) packageService.unpackDataWithDecryption(dto);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void encryptionPackingTest() {
        String expected = "hello";

        NetDTO dto = packageService.packDataWithEncryption(expected, NetDTO.DataCode.MESSAGE);
        try {
            String interResult = (String) packageService.unpackData(dto);
            if (interResult.equals(expected))
                Assert.fail();
        } catch (SerializerException ignored) {
            // может и должна возникнуть ошибка
        }

        String result = (String) packageService.unpackDataWithDecryption(dto);

        Assert.assertEquals(expected, result);
    }
}
