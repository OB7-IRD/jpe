/*
 * Copyright (C) 2014 IRD
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
package fr.ird.jpe.web.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 0.0
 * @date 20 oct. 2014
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 *
 */
public class WebUtils {

    public static final String TEMP_FILE_DIR = System.getProperty("catalina.home") + File.separator + "dashboard_temp";

    public static boolean uploadFile(String name, MultipartFile file) {
        if (file == null) {
            return false;
        }

        try {
            byte[] bytes = file.getBytes();

            // Creating the directory to store file
            File dir = new File(TEMP_FILE_DIR);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            System.out.println(" You failed to upload " + name + " => " + e.getMessage());

            return false;
        }

        return true;
    }
}
