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
package fr.ird.jpe.web.controller.model;

//import fr.ird.common.message.Flux;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 17 oct. 2014
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 *
 */
public class EvaJob {

    private MultipartFile msAccessFile;
    private String tripNumber;
//    private Flux flux;

    public MultipartFile getMsAccessFile() {
        return msAccessFile;
    }

    public void setMsAccessFile(MultipartFile msAccessFile) {
        this.msAccessFile = msAccessFile;
    }

    public String getTripNumber() {
        return tripNumber;
    }

    public void setTripNumber(String tripNumber) {
        this.tripNumber = tripNumber;
    }

//    public void setFlux(Flux flux) {
//        this.flux = flux;
//    }
//
//    public Flux getFlux() {
//        return flux;
//    }

    @Override
    public String toString() {
        return "EvaJob{" + "msAccessFile=" + msAccessFile + ", tripNumber=" + tripNumber + '}';
    }
}
