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
package fr.ird.jpe.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 17 oct. 2014
 *
 */
@Controller
@RequestMapping("/download.do")
public class DownloadController {

    /**
     * Size of a byte buffer to read/write file
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Method for handling file download request from client
     *
     * @param filePath
     * @param request
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    public void doDownload(@RequestParam(value = "filePath") String filePath, HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        // get absolute path of the application
        ServletContext context = request.getServletContext();

//      String appPath = context.getRealPath("");
//      System.out.println("appPath = " + appPath);
        // construct the complete absolute path of the file
        String fullPath = filePath;
        File downloadFile = new File(fullPath);
        if(!downloadFile.exists()){
            throw new IOException("The file does'n exist.");
        }
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);

        if (mimeType == null) {

            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }

        //System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());

        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();
    }
}
