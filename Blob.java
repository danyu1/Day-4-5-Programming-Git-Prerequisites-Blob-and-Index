import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Blob {
    private Path p;
    private String fileName;

    // initialize a blob with a path and its fileName
    public Blob(String fileName) {
        this.fileName = fileName;
        this.p = Paths.get(fileName);
    }

    // write given text to the file and path created
    public void writeToFile(String textToWrite) throws IOException {
        try {
            Files.writeString(p, textToWrite, StandardCharsets.ISO_8859_1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}