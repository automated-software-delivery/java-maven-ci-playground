package it.unimol.anpr_github_metrics.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Simone Scalabrino.
 */
public class FileBasedConfigurationManager {
    protected Properties configuration;

    protected FileBasedConfigurationManager(String configLocation) throws IOException {
        this.configuration = new Properties();
        this.configuration.load(new FileInputStream(configLocation));
    }
}
