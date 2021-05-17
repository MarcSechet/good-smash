import java.io.File;
import java.util.ArrayList;
import java.util.stream.Stream;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class FlyWayTest {

    @Test
    public void checkVersioning() {

        var listOfVersion = new ArrayList<>();
        var directory = new File("src/main/resources/db/migration");

        Stream.of(directory.list()).forEach(fileName -> {
            var split = fileName.split("__");
            var version = split[0];
            Assertions.assertFalse(listOfVersion.contains(version), "Duplicate flyway version: " + version);
            listOfVersion.add(version);
        });
    }
}
