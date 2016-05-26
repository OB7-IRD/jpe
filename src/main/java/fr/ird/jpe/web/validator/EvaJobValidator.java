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
package fr.ird.jpe.web.validator;

import fr.ird.jpe.web.controller.model.EvaJob;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 20 oct. 2014
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 *
 */
public class EvaJobValidator implements Validator {

    public static final String MS_ACCESS_MIME_TYPE = "application/vnd.ms-access";

    @Override
    public void validate(Object evaJob, Errors errors) {
        EvaJob ej = (EvaJob) evaJob;

        if (ej != null) {
            if (ej.getMsAccessFile() == null) {
                System.out.println("Hi " + ej.getMsAccessFile());
            }

            if ((ej.getMsAccessFile() != null) && (ej.getMsAccessFile().getSize() != 0)) {
                System.out.println("MS Access File: " + (ej.getMsAccessFile().getOriginalFilename()));

                if (!ej.getMsAccessFile().getContentType().equals(MS_ACCESS_MIME_TYPE)) {
                    errors.rejectValue("msAccessFile", "upload.file.msacces", "Please select a MS Access file!");
                }
            }
        }

//      if (ej.getFile().getSize() == 0) {
//          errors.rejectValue("file", "uploadForm.salectFile", "Please select a file!");
//      }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return EvaJob.class.equals(clazz);
    }
}
