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

    // initialize private instance variables
    // New file path to the objects folder
    // Create the 'objects' folder if it doesn't exist
    // Create index file
    public Git() throws IOException {
        this.totalBlobs = 0;
        keyValuePairs = new ArrayList<String>();
        String objectsFolderName = "objects";
        Path objectFolderPath = Paths.get(objectsFolderName);
        if (!Files.exists(objectFolderPath))
            Files.createDirectories(objectFolderPath);

        File file = new File("index");
        Path indexFolderPath = Paths.get("index");
        // Create the 'index' file if it doesn't exist
        if (!Files.exists(indexFolderPath))
            file.createNewFile();
    }

    // creates a sha1 hash of the passed file and updates the index file to record
    // appropriate key:value pair
    public void add(String fileName) throws NoSuchAlgorithmException, IOException {
        Path p = Paths.get("index");
        Blob b = new Blob(fileName);
        String SHA1 = b.generateSHA1(b.convertToByteArray(b.getPath()));
        if (!keyValuePairs.contains(fileName + " : " + SHA1)) {
            StringBuilder sb = new StringBuilder("");
            b.createBlob(SHA1, b.getPath());
            keyValuePairs.add(fileName + " : " + SHA1);
            totalBlobs++;
            for (int i = 0; i < keyValuePairs.size(); i++) {
                if (totalBlobs == 1) {
                    sb.append(keyValuePairs.get(i));
                } else {
                    // makes sure that \n is printed appropriately
                    if (i == 0) {
                        sb.append(keyValuePairs.get(i));
                    } else {
                        sb.append("\n" + keyValuePairs.get(i));
                    }
                }
            }
            Files.writeString(p, sb.toString(), StandardCharsets.ISO_8859_1);
        }
    }

    // create a path to the index file
    // remove the keyValuePair from the arrayList
    // create a path to access the sha1 file
    // delete the sha1 hash file
    public void remove(String fileName) throws NoSuchAlgorithmException, IOException {
        int i = 0;
        Path p = Paths.get("index");
        StringBuilder sb = new StringBuilder("");
        boolean removed = false;
        Blob b = new Blob(fileName);
        String currentLine = "";
        String SHA1 = b.generateSHA1(b.convertToByteArray(b.getPath()));
        String keyValuePair = fileName + " : " + SHA1;
        keyValuePairs.remove(keyValuePair);
        // Path sha1Path = Paths.get("objects", SHA1);
        // Files.delete(sha1Path);
        BufferedReader br = new BufferedReader(new FileReader("index"));
        while (br.ready()) {
            currentLine = br.readLine();
            // if the currentline that is read not equal to the key value pair you want to
            // remove then append it to the stringbuilder
            if (!currentLine.equals(keyValuePair)) {
                if (totalBlobs == 1) {
                    sb.append(currentLine);
                } else {
                    if (i == 0) {
                        sb.append(currentLine);
                        i++;
                    } else {
                        sb.append("\n" + currentLine);
                        i++;
                    }
                }
            } else {
                removed = true;
                totalBlobs--;
            }
        }
        if (removed) {
            Files.writeString(p, sb.toString(), StandardCharsets.ISO_8859_1);
            System.out.println("Succesfully removed blob.");
        } else {
            System.out.println("Blob not found.");
        }
    }
}
