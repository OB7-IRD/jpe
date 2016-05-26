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

import fr.ird.aas.orm.AbstractAuthorization;
import fr.ird.aas.orm.Role;
import fr.ird.aas.orm.AbstractRole;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 24 nov. 2014
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 *
 */
public class AASRoleForm {

    @Size(
            min = 1,
            max = 50
    )
    private String label;
    @NotEmpty
    @Size(
            min = 1,
            max = 50
    )
    private String name;
    private String topiaid;
    private String description;
    private List<AbstractAuthorization> authorizations;

    public AASRoleForm() {
    }

    public AASRoleForm(AbstractRole r) {
        topiaid = r.getId();
        label = r.getLabel();
        description = r.getDescription();
        name = r.getName();
        authorizations = r.getAuthorizations();
    }

    public List<AbstractAuthorization> getAuthorizations() {
        return authorizations;
    }

    public void setAuthorizations(Collection<AbstractAuthorization> authorizations) {

//      if (authorizations == null) {
//          this.authorizations = new ArrayList<>();
//      } else {
        this.authorizations = (List<AbstractAuthorization>) authorizations;

//      }
//
//      for (AbstractAuthorization a : this.authorizations) {
//          System.out.println(">>>> " + a);
//      }
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTopiaid(String topiaid) {
        this.topiaid = topiaid;
    }

    public String getTopiaid() {
        return topiaid;
    }

    public AbstractRole getRole() {
        AbstractRole r = new Role(name, label, description);

        r.setTopiaid(topiaid);
        r.setAuthorizations(authorizations);

        return r;
    }

    @Override
    public String toString() {
        return "RoleForm{" + "label=" + label + ", name=" + name + ", id=" + topiaid + ", description=" + description
                + ", authorizations=" + authorizations + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;

        hash = 53 * hash + Objects.hashCode(this.label);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.topiaid);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.authorizations);

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

        final AASRoleForm other = (AASRoleForm) obj;

        if (!Objects.equals(this.label, other.label)) {
            return false;
        }

        if (!Objects.equals(this.name, other.name)) {
            return false;
        }

        if (!Objects.equals(this.topiaid, other.topiaid)) {
            return false;
        }

        if (!Objects.equals(this.description, other.description)) {
            return false;
        }

        if (!Objects.equals(this.authorizations, other.authorizations)) {
            return false;
        }

        return true;
    }
}
