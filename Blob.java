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

    // read the content of the passed file and convert it into an array of bytes
    public byte[] convertToByteArray(Path path) throws IOException {
        // FileInputStream allows for the reading of binary data by reading data as a
        // stream of bytes
        String filePath = path.toString();
        FileInputStream fis = new FileInputStream(filePath);
        // output stream used for writing data. Is essentially a byte array. As you
        // write more data, the buffer can grow dynamically to accommodate the new data.
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        // read the byte, then write the byte to the byte array, close the file input
        // stream
        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        fis.close();
        return bos.toByteArray();
    }

    // return the SHA1 hash of a byte array
    public String generateSHA1(byte[] fileData) throws NoSuchAlgorithmException {
        // MessageDigest supports different hash algorithms
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hash = md.digest(fileData);

        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void createBlob(String SHA1, Path p) throws IOException {
        // convert the original file into byte
        byte[] originalFile = convertToByteArray(p);

        String objectsFolderPath = "objects";
        // New file path
        Path objectFilePath = Paths.get(objectsFolderPath, SHA1);

        // Create a new file with the SHA-1 hash as the filename inside 'objects' folder
        Files.write(objectFilePath, originalFile);

        System.out.println("New file created with SHA-1 hash as filename: " + objectFilePath);
    }

    public Path getPath() {
        return this.p;
    }
}