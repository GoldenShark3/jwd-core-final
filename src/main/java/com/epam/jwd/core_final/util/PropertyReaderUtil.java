package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.exception.ApplicationPropertiesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertyReaderUtil {
    private static final Properties PROPERTIES = new Properties();
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyReaderUtil.class);

    private PropertyReaderUtil() {
    }

    public static Properties getProperties() {
        return PROPERTIES;
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public static void loadProperties() {
        LOGGER.info("Loading properties file");
        final String propertiesFileName = "src/main/resources/application.properties";
        try(FileInputStream inputStream = new FileInputStream(propertiesFileName)){
            PROPERTIES.load(inputStream);
            populateApplicationProperties();
        }catch(IOException e) {
            throw new ApplicationPropertiesException("Cannot read application.properties file");
        }
    }

    private static void populateApplicationProperties(){
        ApplicationProperties applicationProperties = new ApplicationProperties(
                PROPERTIES.getProperty("inputRootDir"),
                PROPERTIES.getProperty("outputRootDir"),
                PROPERTIES.getProperty("crewFileName"),
                PROPERTIES.getProperty("missionsFileName"),
                PROPERTIES.getProperty("spaceshipsFileName"),
                Integer.parseInt(PROPERTIES.getProperty("fileRefreshRate")),
                PROPERTIES.getProperty("dateTimeFormat"));
        PROPERTIES.put("applicationProperty", applicationProperties);
    }
}
