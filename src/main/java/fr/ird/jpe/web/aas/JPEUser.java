/*
 * Copyright (C) 2014 Observatoire thonier, IRD
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
package fr.ird.jpe.web.aas;

import fr.ird.aas.orm.User;
import fr.ird.aas.orm.AbstractUser;
import fr.ird.aas.orm.Account;
import fr.ird.aas.orm.Role;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.joda.time.DateTime;

/**
 * The {@link User} of the JPE's application. This class is a part of AAS
 * library.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 */
@Entity
@DiscriminatorValue("jpe")
public class JPEUser extends User {

    @Column(
            name = "email",
            nullable = true,
            length = 255
    )
    private String email;

    @Column(
            name = "lastaccess",
            nullable = true
    )
    private DateTime lastAccess;

    private Boolean disable = false;

    private String uri;
    private String phone;
    private String organism;
    private String service;
    private String location;

    public JPEUser() {
        super();
        lastAccess = DateTime.now();
    }

    public Role generateUserRole() {
        return new Role(this.login, this.login + "_role", "Main collection of Permissions for the " + this.firstname + " " + this.lastname + " user.");
    }

    /**
     *
     * @param email email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return the user email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return la date du dernière accès au portail
     */
    public DateTime getLastAccess() {
        return lastAccess;
    }

    /**
     *
     * @param lastAccess date du dernière accès au portail
     */
    public void setLastAccess(DateTime lastAccess) {
        this.lastAccess = lastAccess;
    }

    @Override
    public String toString() {
        return "JPEUser{[" + super.toString() + "] email=" + email + ", lastAccess=" + lastAccess + '}';
    }

    /**
     * Créer l'idendifiant de l'utilisateur. Dans le cas de DAP c'est l'adresse
     * mail qui est utilisée.
     *
     * @return l'identifiant associé à l'Utilisateur du DAP
     */
    @Override
    public String createLoginForLocalAccount() {
        return email;
    }

    /**
     * Met à jour l'utilisateur courant.
     *
     * @param user utilisateur pour la mise à jour
     */
    @Override
    public void update(AbstractUser user) {
        super.update(user);
        JPEUser dbu = (JPEUser) user;
        setEmail(dbu.getEmail());
        setLastAccess(dbu.getLastAccess());
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

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public boolean isLocalAccount() {
        return type.equals(Account.TYPE_LOCAL);
    }
}
