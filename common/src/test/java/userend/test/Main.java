package userend.test;

import fr.atlasworld.common.logging.Level;
import fr.atlasworld.common.logging.LogUtils;
import org.slf4j.Logger;

public class Main {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void main(String[] args) {
        LogUtils.setGlobalLevel(Level.TRACE);

        System.out.println("Test!");

        LogUtils.setLevel(LOGGER, Level.WARNING);
    }
}
