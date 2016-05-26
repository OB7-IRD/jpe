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

import fr.ird.aas.orm.AbstractRole;
import fr.ird.aas.orm.AbstractUser;
import fr.ird.aas.service.PasswordService;
import fr.ird.jpe.web.aas.JPEUser;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 24 nov. 2014
 *
 */
public class AASUserForm {
    
    private List<AbstractRole> roles;
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
    @NotEmpty
    @Email
    private String email;
    private String password;
    private String topiaid;
    private String login;
    private boolean disable;
    private boolean local;
    
    public AASUserForm() {
    }
    
    public boolean isLocal() {
        return local;
    }
    
    public void setLocal(boolean local) {
        this.local = local;
    }
    
    public AASUserForm(JPEUser u) {
        topiaid = u.getId();
        this.email = u.getEmail();
        this.firstname = u.getFirstname();
        this.lastname = u.getLastname();
        this.password = u.getPassword();
        this.login = u.getLogin();
        this.local = u.isLocalAccount();
        this.roles = u.getRoles();
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
    
    public List<AbstractRole> getRoles() {
        return roles;
    }
    
    public void setRoles(List<AbstractRole> roles) {
        this.roles = roles;
    }
    
    public AbstractUser getUser() {
        JPEUser u = new JPEUser();
        u.setFirstname(firstname);
        u.setLastname(lastname);
        u.setPassword(password);
        u.setLogin(login);
        u.setEmail(email);
        u.setTopiaid(topiaid);
        
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
        
        final AASUserForm other = (AASUserForm) obj;
        
        if (!Objects.equals(this.roles, other.roles)) {
            return false;
        }
        
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
        this.password = PasswordService.encrypt(password);
    }
    
    public boolean isDisable() {
        return disable;
    }
    
    public void setDisable(boolean disable) {
        this.disable = disable;
    }
    
}
