import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Git {
    public Git() throws IOException {
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

    public void add(String fileName) {

    }
}
