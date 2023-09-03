import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class Git {
    private int totalBlobs;

    public Git() throws IOException {
        this.totalBlobs = 0;
        String objectsFolderName = "objects";
        // New file path
        Path objectFolderPath = Paths.get(objectsFolderName);
        // Create the 'objects' folder if it doesn't exist
        if (!Files.exists(objectFolderPath))
            Files.createDirectories(objectFolderPath);

        // Create index file
        File file = new File("index");
        Path indexFolderPath = Paths.get("index");
        // Create the 'index' file if it doesn't exist
        if (!Files.exists(indexFolderPath))
            file.createNewFile();
    }

    public void add(String fileName) throws NoSuchAlgorithmException, IOException {
        Blob b = new Blob(fileName);
        String SHA1 = b.generateSHA1(b.convertToByteArray(b.getPath()));
        b.createBlob(SHA1, b.getPath());
        if (totalBlobs == 0) {
            PrintWriter pw = new PrintWriter("index");
            pw.print(fileName + " : ");
            pw.print(SHA1);
            totalBlobs++;
            pw.close();
        } else {
            PrintWriter pw = new PrintWriter("index");
            pw.print("\n" + fileName + " : ");
            pw.print(SHA1);
            totalBlobs++;
            pw.close();
        }
    }
}
