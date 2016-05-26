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

//~--- non-JDK imports --------------------------------------------------------
import fr.ird.aas.orm.AbstractUser;
import fr.ird.jpe.web.aas.JPEUser;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 24 nov. 2014
 *
 */
public class PreferencesUserForm {

    @NotEmpty
    @Size(
            min = 1,
            max = 50
    )
    private String firstname;
    @NotEmpty
    @Size(
            min = 1,
            max = 50
    )
    private String lastname;
    @NotNull
    @Email
    private String email;

//
//  @NotNull
//  @Size(min = 2, max = 30)
    private String password;
    private String topiaid;
    private String login;
    private Integer type;
    private String uri;
    private String phone;
    private String location;
    private String service;
    private String organism;

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public PreferencesUserForm() {
    }

    public PreferencesUserForm(JPEUser u) {
        topiaid = u.getId();
        email = u.getEmail();
        firstname = u.getFirstname();
        lastname = u.getLastname();
        login = u.getLogin();
        password = u.getPassword();
        type = u.getType();
        uri = u.getUri();
        phone = u.getPhone();
        location = u.getLocation();
        service = u.getService();
        organism = u.getOrganism();
    }

    public Integer getType() {
        return type;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setTopiaid(String topiaid) {
        this.topiaid = topiaid;
    }

    public String getTopiaid() {
        return topiaid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AbstractUser getUser() {
        JPEUser u = new JPEUser();
        u.setTopiaid(topiaid);

        u.setFirstname(firstname);
        u.setLastname(lastname);
        u.setPassword(password);
        u.setLogin(login);
        u.setEmail(email);
        u.setUri(uri);
        u.setPhone(phone);
        u.setOrganism(organism);
        u.setService(service);
        u.setLocation(location);
        return u;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 23 * hash + Objects.hashCode(this.topiaid);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final PreferencesUserForm other = (PreferencesUserForm) obj;

        if (!Objects.equals(this.firstname, other.firstname)) {
            return false;
        }

        if (!Objects.equals(this.lastname, other.lastname)) {
            return false;
        }

        if (!Objects.equals(this.email, other.email)) {
            return false;
        }

        if (!Objects.equals(this.topiaid, other.topiaid)) {
            return false;
        }

        return true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
