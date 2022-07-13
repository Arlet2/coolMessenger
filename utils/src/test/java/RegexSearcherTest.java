import exceptions.NotFoundByRegexException;
import org.junit.Assert;
import org.junit.Test;

public class RegexSearcherTest {
    private final String portRegex = "(?<=port:\\s{1,5})[^\\s]*";
    private final String hostNameRegex = "(?<=address:\\s{1,5})[^\\s]*";
    private final String versionRegex = "(?<=version:\\s{1,5})[^\\s]*";

    @Test
    public void findPortInLinesTest() {
        String input = "port: 123\n" +
                "address: 127.0.0.1";

        String result = RegexSearcher.searchFirst(portRegex, input);

        Assert.assertEquals("123", result);
    }

    @Test(expected = NotFoundByRegexException.class)
    public void noFindPortTest() {
        String input = "address: 127.0.0.1";

        RegexSearcher.searchFirst(portRegex, input);
    }

    @Test
    public void countRegexTest() {
        String input = "port: 123 \n" +
                "port: 12345 \n" +
                "port: 456\n";

        int count = RegexSearcher.searchFindCount(portRegex, input);

        Assert.assertEquals(3, count);
    }

    @Test
    public void checkExtraSpaces() {
        String input = "port: 123 \n";

        int result = Integer.parseInt(RegexSearcher.searchFirst(portRegex, input));

        Assert.assertEquals(123, result);
    }

    @Test
    public void findHostNameInLines() {
        String input = "port: 123 \n" +
                "address: bao.cf \n" +
                "version: 123";

        String result = RegexSearcher.searchFirst(hostNameRegex, input);

        Assert.assertEquals("bao.cf", result);
    }

    @Test
    public void findVersionInLines() {
        String input = "port: 123 \n" +
                "address: bao.cf \n" +
                "version: 123 ";

        String result = RegexSearcher.searchFirst(versionRegex, input);

        Assert.assertEquals(123, Integer.parseInt(result));
    }

    @Test
    public void nothingWasFoundTest() {
        String input = "port: 123";

        int count = RegexSearcher.searchFindCount(versionRegex, input);

        Assert.assertEquals(0, count);
    }
}
