/*
 * Copyright (C) 2015 Observatoire Thonier - IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.jpe.web.config;

import fr.ird.common.configuration.IRDProperties;
import fr.ird.common.log.LogService;
import java.io.File;
import java.util.Properties;

/**
 * The JPEProperties class represents a persistent set of properties. This
 * properties are stored in the file "eva-config.xml". J'utilise cette classe
 * dans un projet Spring par cohérence avec les autres projets de l'OT. Une
 * solution en utilisant les fichiers configurations Spring aurait été possible.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 17 mars 2015
 */
public final class JPEProperties extends IRDProperties {

    private static final JPEProperties SERVICE = new JPEProperties();

    public static JPEProperties getService() {
        return SERVICE;
    }

    public static final String STANDARD_RELATIVE_CONFIG_PATH = "db";
    public static final String RESOURCES_RELATIVE_CONFIG_PATH = "resources";
//
    public static final String KEY_STANDARD_DIRECTORY = "standard_directory";
    public static String STANDARD_DIRECTORY;
    public static String WEB_SERVICE_JSON_FILE;
    public static String AKADO_ERS_WEB_SERVICE_URI;

    public static final String KEY_JDBC_USERNAME = "jdbc_username";
    public static final String KEY_JDBC_PASSWORD = "jdbc_password";
    public static final String KEY_JDBC_URL = "jdbc_url";
    public static final String KEY_JDBC_DRIVER_CLASS = "jdbc_driver";

    public static String JDBC_USERNAME;
    public static String JDBC_PASSWORD;
    public static String JDBC_URL;
    public static String JDBC_DRIVER_CLASS;

    private JPEProperties() {
        PROJECT_NAME = "jpe-web";
        PROJECT_CONFIG_FILENAME = "jpe-config.xml";
        PROJECT_CONFIG_COMMENT = "JPE configuration's properties";

        init();
    }

    /**
     * Initialize the loading of properties.
     */
    public void init() {
        try {
            if (!configFileExist()) {
                LogService.getService(JPEProperties.class).logApplicationInfo("!configFileExist(): so create it!");
                createConfigFile();
            }
            Properties p = loadProperties();
            LogService.getService(JPEProperties.class).logApplicationDebug("Properties :");
            for (String k : p.stringPropertyNames()) {
                LogService.getService(JPEProperties.class).logApplicationDebug("Property : (" + k + "," + p.getProperty(k) + ")");
            }
            JPEProperties.STANDARD_DIRECTORY = p.getProperty(KEY_STANDARD_DIRECTORY);
            JPEProperties.JDBC_USERNAME = p.getProperty(KEY_JDBC_USERNAME);
            JPEProperties.JDBC_PASSWORD = p.getProperty(KEY_JDBC_PASSWORD);
            JPEProperties.JDBC_URL = p.getProperty(KEY_JDBC_URL);
            JPEProperties.JDBC_DRIVER_CLASS = p.getProperty(KEY_JDBC_DRIVER_CLASS);

        } catch (Exception e) {
            LogService.getService(JPEProperties.class).logApplicationError(e.getMessage());
        }
    }

    @Override
    public Properties createDefaultProperties() {
        LogService.getService(JPEProperties.class).logApplicationInfo("createDefaultProperties");
        LogService.getService(JPEProperties.class).logApplicationInfo("Initialisation de properties ");
        Properties properties = new Properties();
        properties.setProperty(KEY_STANDARD_DIRECTORY, PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + STANDARD_RELATIVE_CONFIG_PATH);

        properties.setProperty(KEY_JDBC_URL, "");
        properties.setProperty(KEY_JDBC_DRIVER_CLASS, "");
        properties.setProperty(KEY_JDBC_USERNAME, "");
        properties.setProperty(KEY_JDBC_PASSWORD, "");

        return properties;
    }

    @Override
    public void createDefaultDirectory() {
        super.createDefaultDirectory();
        String path = PROJECT_CONFIG_ABSOLUTE_PATH;
        path += File.separator + STANDARD_RELATIVE_CONFIG_PATH;
        boolean success = (new File(path)).mkdirs();
        if (success) {
            LogService.getService(JPEProperties.class).logApplicationInfo("Directory: " + path + " created");
        }
        path = PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + RESOURCES_RELATIVE_CONFIG_PATH;
        success = (new File(path)).mkdirs();
        if (success) {
            LogService.getService(JPEProperties.class).logApplicationInfo("Directory: " + path + " created");
        }
    }

    @Override
    public void copyDefaultFile() {
    }
}
