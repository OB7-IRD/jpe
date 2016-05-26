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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 8 oct. 2014
 *
 */
@Controller
public class AASController {

    public final static String AAS_URI = AdminController.ADMIN_URI+"/aas";
    public final static String AAS_ROLE_URI = AAS_URI + "/role";
    public final static String AAS_ADD_ROLE_URI = AAS_ROLE_URI + "/add";
    public final static String AAS_LIST_ROLE_URI = AAS_ROLE_URI + "/list";
    public final static String AAS_EDIT_ROLE_URI = AAS_ROLE_URI + "/edit";
    public final static String AAS_DELETE_ROLE_URI = AAS_ROLE_URI + "/delete";
    public final static String AAS_USER_URI = AAS_URI + "/user";
    public final static String AAS_ADD_USER_URI = AAS_USER_URI + "/add";
    public final static String AAS_LIST_USER_URI = AAS_USER_URI + "/list";
    public final static String AAS_EDIT_USER_URI = AAS_USER_URI + "/edit";
    public final static String AAS_DELETE_USER_URI = AAS_USER_URI + "/delete";
    public final static String AAS_AUTH_URI = AAS_URI + "/authorization";
    public final static String AAS_ADD_AUTH_URI = AAS_AUTH_URI + "/add";
    public final static String AAS_LIST_AUTH_URI = AAS_AUTH_URI + "/list";
    public final static String AAS_EDIT_AUTH_URI = AAS_AUTH_URI + "/edit";
    public final static String AAS_DELETE_AUTH_URI = AAS_AUTH_URI + "/delete";

    @RequestMapping(
            value = AAS_URI,
            method = RequestMethod.GET
    )
    public String index() {
        return "aas/index";
    }
}
