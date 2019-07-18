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
import fr.ird.driver.eva.business.Position;
import java.io.File;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * The JPEProperties class represents a persistent set of properties. This
 * properties are stored in the file "eva-config.xml". J'utilise cette classe
 * dans un projet Spring par cohérence avec les autres projets de l'OT. Une
 * solution en utilisant les fichiers configurations Spring aurait été
 * possible.
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

    public static final String KEY_JDBC_USERNAME_AAS = "jdbc_username_aas";
    public static final String KEY_JDBC_PASSWORD_AAS = "jdbc_password_aas";
    public static final String KEY_JDBC_URL_AAS = "jdbc_url_aas";
    public static final String KEY_JDBC_DRIVER_CLASS_AAS = "jdbc_driver_aas";

    public static final String KEY_JDBC_USERNAME_EVA = "jdbc_username_eva";
    public static final String KEY_JDBC_PASSWORD_EVA = "jdbc_password_eva";
    public static final String KEY_JDBC_URL_EVA = "jdbc_url_eva";
    public static final String KEY_JDBC_DRIVER_CLASS_EVA = "jdbc_driver_eva";

    public static String JDBC_USERNAME_AAS;
    public static String JDBC_PASSWORD_AAS;
    public static String JDBC_URL_AAS;
    public static String JDBC_DRIVER_CLASS_AAS;

    public static String JDBC_USERNAME_EVA;
    public static String JDBC_PASSWORD_EVA;
    public static String JDBC_URL_EVA;
    public static String JDBC_DRIVER_CLASS_EVA;

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
            JPEProperties.JDBC_USERNAME_AAS = p.getProperty(KEY_JDBC_USERNAME_AAS);
            JPEProperties.JDBC_PASSWORD_AAS = p.getProperty(KEY_JDBC_PASSWORD_AAS);
            JPEProperties.JDBC_URL_AAS = p.getProperty(KEY_JDBC_URL_AAS);
            JPEProperties.JDBC_DRIVER_CLASS_AAS = p.getProperty(KEY_JDBC_DRIVER_CLASS_AAS);

            JPEProperties.JDBC_USERNAME_EVA = p.getProperty(KEY_JDBC_USERNAME_EVA);
            JPEProperties.JDBC_PASSWORD_EVA = p.getProperty(KEY_JDBC_PASSWORD_EVA);
            JPEProperties.JDBC_URL_EVA = p.getProperty(KEY_JDBC_URL_EVA);
            JPEProperties.JDBC_DRIVER_CLASS_EVA = p.getProperty(KEY_JDBC_DRIVER_CLASS_EVA);
        } catch (InvalidPropertiesFormatException e) {
            LogService.getService(JPEProperties.class).logApplicationError("The properties files is ivalid. Please check the file's encoding (UTF-8).");
            LogService.getService(JPEProperties.class).logApplicationError(e.getMessage());
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

        properties.setProperty(KEY_JDBC_URL_AAS, "");
        properties.setProperty(KEY_JDBC_DRIVER_CLASS_AAS, "");
        properties.setProperty(KEY_JDBC_USERNAME_AAS, "");
        properties.setProperty(KEY_JDBC_PASSWORD_AAS, "");
        properties.setProperty(KEY_JDBC_URL_EVA, "");
        properties.setProperty(KEY_JDBC_DRIVER_CLASS_EVA, "");
        properties.setProperty(KEY_JDBC_USERNAME_EVA, "");
        properties.setProperty(KEY_JDBC_PASSWORD_EVA, "");

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

    public static Position getHarbourPosition(String locode) {
        switch (locode) {
            case "SNDKR":
                return new Position(14.764, -17.366);
            case "CIABJ":
                return new Position(5.319, -4.034);
            case "GHTEM":
                return new Position(5.666, 0d);
            case "SCVIC":
                return new Position(-4.37, 55.27);
            case "MUPLU":
                return new Position(-20.167, 57.517);
            case "FRCOC":
                return new Position(47.867, -3.917);
            case "REREU":
                return new Position(-20.860, 55.450);
            default:
                return new Position(0d, 0d);
        }
    }
}
