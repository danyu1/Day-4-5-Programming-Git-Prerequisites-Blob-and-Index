import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Git {
    private int totalBlobs;
    private ArrayList<String> keyValuePairs;

    public Git() throws IOException {
        this.totalBlobs = 0;
        keyValuePairs = new ArrayList<String>();
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

    // creates a sha1 hash of the passed file and updates the index file to record
    // appropriate key:value pair
    public void add(String fileName) throws NoSuchAlgorithmException, IOException {
        StringBuilder sb = new StringBuilder("");
        Path p = Paths.get("index");
        Blob b = new Blob(fileName);
        String SHA1 = b.generateSHA1(b.convertToByteArray(b.getPath()));
        b.createBlob(SHA1, b.getPath());
        keyValuePairs.add(fileName + " : " + SHA1);
        totalBlobs++;
        for (int i = 0; i < keyValuePairs.size(); i++) {
            if (totalBlobs == 1) {
                sb.append(keyValuePairs.get(i));
            } else {
                if (i == 0) {
                    sb.append(keyValuePairs.get(i));
                } else {
                    sb.append("\n" + keyValuePairs.get(i));
                }
            }
        }
        Files.writeString(p, sb.toString(), StandardCharsets.ISO_8859_1);
    }

    public void remove(String fileName) throws NoSuchAlgorithmException, IOException {
        boolean removed = false;
        Blob b = new Blob(fileName);
        String currentLine = "";
        String SHA1 = b.generateSHA1(b.convertToByteArray(b.getPath()));
        String keyValuePair = fileName + " : " + SHA1;
        Files.delete(b.getPath());
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        PrintWriter pw = new PrintWriter("index");
        while (br.ready()) {
            currentLine = br.readLine();
            if (!currentLine.equals(keyValuePair)) {
                pw.print(currentLine);
                pw.flush();
            } else {
                removed = true;
            }
        }
        if (removed) {
            System.out.println("Succesfully removed blob.");
        } else {
            System.out.println("Blob not found.");
        }
    }
}
