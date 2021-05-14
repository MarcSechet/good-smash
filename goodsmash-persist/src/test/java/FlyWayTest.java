import java.io.File;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class FlyWayTest {

    @Test
    public void checkVersioning() {

        var listOfVersion = new ArrayList<>();
        var directory = new File("src/main/resources/db/migration");

        Stream.of(directory.list()).forEach(fileName -> {
            var split = fileName.split("__");
            var version = split[0];
            assertFalse("Duplicate flyway version: " + version, listOfVersion.contains(version));
            listOfVersion.add(version);
        });
    }
}
