package gh.com.id_verification.id_verification.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    private FileUtil() {
        // restrict instantiation
    }

    public static final String folderPath =  "incoming-files//";
    public static final Path filePath = Paths.get(folderPath);


}
