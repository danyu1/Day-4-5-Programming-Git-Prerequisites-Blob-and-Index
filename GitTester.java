import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class GitTester {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Git init = new Git();
        init.add("test.txt");
    }
}