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
package fr.ird.jpe.web.controller.aas;

//~--- non-JDK imports --------------------------------------------------------
import fr.ird.aas.exception.AASException;
import fr.ird.aas.orm.AbstractAuthorization;
import fr.ird.aas.orm.Role;
import fr.ird.aas.orm.AbstractRole;
import fr.ird.aas.realm.AASManager;
import fr.ird.common.log.LogService;
import static fr.ird.jpe.web.controller.AASController.AAS_ADD_ROLE_URI;
import static fr.ird.jpe.web.controller.AASController.AAS_DELETE_ROLE_URI;
import static fr.ird.jpe.web.controller.AASController.AAS_EDIT_ROLE_URI;
import static fr.ird.jpe.web.controller.AASController.AAS_LIST_ROLE_URI;
import static fr.ird.jpe.web.controller.AASController.AAS_ROLE_URI;
import fr.ird.jpe.web.controller.model.AASRoleForm;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 14 nov. 2014
 */
@Controller
public class AASRoleController {

    @Autowired
    private AASManager manager;

    @ModelAttribute("allAuthorizations")
    public List<AbstractAuthorization> populateAuthorizations() {
        return manager.getAuthorizationService().getDao().list();
    }

    @RequestMapping(
            value = {AAS_ROLE_URI, AAS_LIST_ROLE_URI},
            method = RequestMethod.GET
    )
    public String roles(Model model) {
        model.addAttribute("type", "role");
        LogService.getService(this.getClass()).logApplicationDebug("GET on " + AAS_LIST_ROLE_URI + " - " + manager.getRoleService().getDao().list());
        model.addAttribute("roles", manager.getRoleService().getDao().list());

        return "aas/list/role";
    }

//  @RequestMapping(value = AAS_ADD_ROLE_URI, method = RequestMethod.GET)
//  public String add_role(Model model) {
//      model.addAttribute("type", "role");
//      AbstractRole role = new Role();
//      model.addAttribute("role", role);
//      model.addAttribute("authorizations", manager.getAuthorizationService().getDao().list());
//      return "aas/edit";
//  }
    @RequestMapping(
            value = {AAS_ADD_ROLE_URI, AAS_EDIT_ROLE_URI},
            method = RequestMethod.GET
    )
    public String edit_role(@RequestParam(
            value = "id",
            required = false
    ) String id, Model model) {
        model.addAttribute("type", "role");

        AASRoleForm roleForm;

        if (!model.containsAttribute("role")) {
            if (id != null) {

//              LogService.getService().logApplicationDebug("ID != null > " + id);
                roleForm = new AASRoleForm(manager.getRoleService().getDao().getById(id));
            } else {

//              LogService.getService().logApplicationDebug("ID == null");
                roleForm = new AASRoleForm(new Role());
            }
        } else {
            roleForm = (AASRoleForm) model.asMap().get("role");
        }

        model.addAttribute("role", roleForm);

        return "aas/edit/role";
    }

    @RequestMapping(
            value = AAS_EDIT_ROLE_URI,
            method = RequestMethod.POST
    )
    public String edit_role(@Valid
            @ModelAttribute(value = "role") AASRoleForm roleForm, BindingResult result, RedirectAttributes redirectAttributes,
            Model model) {
        model.addAttribute("type", "role");

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", result);
            redirectAttributes.addFlashAttribute("role", roleForm);

            return "redirect:" + AAS_EDIT_ROLE_URI;
        }

        AbstractRole r = roleForm.getRole();
        List<AbstractAuthorization> newAuth = new ArrayList<>();

        if (roleForm.getAuthorizations() != null) {
            for (AbstractAuthorization a : roleForm.getAuthorizations()) {

//              LogService.getService().logApplicationDebug("A in editRole " + a);
                newAuth.add(manager.getAuthorizationService().getDao().getById(a.getTopiaid()));
            }
        }

        r.setAuthorizations(newAuth);
        manager.getRoleService().save(r);

        return "redirect:" + AAS_ROLE_URI;
    }

    @RequestMapping(
            value = AAS_DELETE_ROLE_URI,
            method = RequestMethod.POST
    )
    public String delete_role(@RequestParam(
            value = "role",
            required = true
    ) AbstractRole role, Model model) {
        model.addAttribute("type", "role");

        try {
            manager.getRoleService().delete(role);
        } catch (AASException ex) {
            LogService.getService(AASRoleController.class).logApplicationError(ex.getMessage());
        }

        return "redirect:" + AAS_ROLE_URI;
    }

    @RequestMapping(
            value = AAS_DELETE_ROLE_URI,
            method = RequestMethod.GET
    )
    public String delete_role(@RequestParam(
            value = "id",
            required = true
    ) String id, Model model) {
        model.addAttribute("type", "role");

        AbstractRole role = manager.getRoleService().getDao().getById(id);

        try {
            manager.getRoleService().delete(role);
        } catch (AASException ex) {
            LogService.getService(AASRoleController.class).logApplicationError(ex.getMessage());
        }

        return "redirect:" + AAS_ROLE_URI;
    }
}
