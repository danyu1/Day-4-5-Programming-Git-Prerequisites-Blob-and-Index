import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class BlobTester {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Blob blob1 = new Blob("test.txt");
        // blob1.writeToFile("This is some cool content.");
        blob1.createBlob(blob1.generateSHA1(blob1.convertToByteArray(blob1.getPath())), blob1.getPath());
    }
}
