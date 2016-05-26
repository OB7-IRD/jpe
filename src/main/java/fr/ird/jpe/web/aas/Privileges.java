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

import fr.ird.aas.exception.UserAlreadyExistsException;
import fr.ird.aas.orm.AbstractAuthorization;
import fr.ird.aas.orm.Authorization;
import fr.ird.aas.orm.Role;
import fr.ird.aas.realm.AASManager;
import fr.ird.aas.service.PasswordService;
import fr.ird.aas.service.UserService;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Permet de générer des utilisateurs par défaut avec des roles et des
 * autorisations.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @date 04 nov. 2014
 */
public class Privileges {

    /**
     * Créer des utilisateurs par défaut
     *
     * @param manager le manager du système d'authentification
     * @throws UserAlreadyExistsException
     */
    public static void insertDefaultPrivileges(AASManager manager) throws UserAlreadyExistsException {
        UserService userService = manager.getUserService();

        JPEUser adminUser = new JPEUser();
        adminUser.setFirstname("Admin");
        adminUser.setLastname("Observatoire Thonier");
        adminUser.setLogin("admin");
        adminUser.setPassword(PasswordService.encrypt("admin"));
        adminUser.setEmail("support.obs.thonier@listes.ird.fr");
        adminUser.setLastAccess(DateTime.now());

        List<AbstractAuthorization> authorizations = new ArrayList<>();
        Role administrator = new Role("administrator", "System administrator",
                "Can manage users, roles, authorizations.");
        Authorization adminStar = new Authorization("admin:*:*", "superadmin", "Super admin will all privilegies. He can manage users, roles, authorizations");
        if (userService.getDao().exist(adminUser) == null) {
            authorizations.add(adminStar);
            administrator.setAuthorizations(authorizations);
            adminUser.addRole(administrator);
            userService.create(adminUser);
        }
    }
}
