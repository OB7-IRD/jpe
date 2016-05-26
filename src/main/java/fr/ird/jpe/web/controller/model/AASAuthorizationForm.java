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
import fr.ird.aas.orm.Authorization;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 14 nov. 2014
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 *
 */
public class AASAuthorizationForm {

    @Size(
            min = 0,
            max = 50
    )
    private String label;
    @NotEmpty
    @Size(
            min = 1,
            max = 50
    )
    private String name;
    private String id;
    private String description;

    public AASAuthorizationForm() {
    }

    public AASAuthorizationForm(AbstractAuthorization a) {
        id = a.getId();
        label = a.getLabel();
        description = a.getDescription();
        name = a.getName();
    }

    @Override
    public String toString() {
        return "AuthForm{" + "label=" + label + ", name=" + name + ", description=" + description + '}';
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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public AbstractAuthorization getAuthorization() {
        AbstractAuthorization a = new Authorization(name, label, description);

        a.setTopiaid(id);

        return a;
    }
}
