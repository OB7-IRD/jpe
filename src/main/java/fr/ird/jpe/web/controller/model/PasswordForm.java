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

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 10 déc. 2014
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 *
 */
public class PasswordForm {

    @NotEmpty
    @Size(
            min = 4,
            max = 50
    )
    String password;

//  @NotEmpty
//  @Size(min = 4, max = 50)
//  String passwordRepeat;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//  public String getPasswordRepeat() {
//      return passwordRepeat;
//  }
//
//  public void setPasswordRepeat(String passwordRepeat) {
//      this.passwordRepeat = passwordRepeat;
//  }
}
