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
import fr.ird.aas.orm.AbstractRole;
import fr.ird.aas.orm.AbstractUser;
import fr.ird.aas.orm.Role;
import fr.ird.aas.realm.AASManager;
import fr.ird.common.log.LogService;
import fr.ird.jpe.web.aas.JPEUser;
import static fr.ird.jpe.web.controller.AASController.AAS_ADD_USER_URI;
import static fr.ird.jpe.web.controller.AASController.AAS_DELETE_USER_URI;
import static fr.ird.jpe.web.controller.AASController.AAS_EDIT_USER_URI;
import static fr.ird.jpe.web.controller.AASController.AAS_LIST_USER_URI;
import static fr.ird.jpe.web.controller.AASController.AAS_USER_URI;
import fr.ird.jpe.web.controller.model.AASUserForm;
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
 *
 */
@Controller
public class AASUserController {

    @Autowired
    private AASManager manager;

    @ModelAttribute("allRoles")
    public List<AbstractRole> populateRoles() {
        return manager.getRoleService().getDao().list();
    }

    @RequestMapping(
            value = {AAS_USER_URI, AAS_LIST_USER_URI},
            method = RequestMethod.GET
    )
    public String users(Model model) {
        model.addAttribute("type", "user");

        model.addAttribute("users", manager.getUserService().getDao().list());

        return "aas/list/user";
    }

//  @RequestMapping(value = AAS_ADD_USER_URI, method = RequestMethod.GET)
//  public String add_user(Model model) {
//      model.addAttribute("type", "user");
//      AbstractUser user = new DashBoardUser();
//      model.addAttribute("user", user);
//      return "aas/edit";
//  }
    @RequestMapping(
            value = {AAS_ADD_USER_URI, AAS_EDIT_USER_URI},
            method = RequestMethod.GET
    )
    public String edit_user(@RequestParam(
            value = "id",
            required = false
    ) String id, Model model) {
        model.addAttribute("type", "user");

        AASUserForm userForm;

        if (!model.containsAttribute("user")) {
            if (id != null) {

//              LogService.getService().logApplicationDebug("ID != null > " + id);
                userForm = new AASUserForm((JPEUser) manager.getUserService().getDao().getById(id));
            } else {

//              LogService.getService().logApplicationDebug("ID == null");
                userForm = new AASUserForm(new JPEUser());

//              LogService.getService().logApplicationDebug("new ID " + userForm.getTopiaid());
            }
        } else {
            userForm = (AASUserForm) model.asMap().get("user");
        }

        model.addAttribute("user", userForm);

        return "aas/edit/user";
    }

    @RequestMapping(
            value = AAS_EDIT_USER_URI,
            method = RequestMethod.POST
    )
    public String edit_user(@Valid
            @ModelAttribute(value = "user") AASUserForm form, BindingResult result, RedirectAttributes redirectAttributes,
            Model model) {
        model.addAttribute("type", "user");

        if (result.hasErrors()) {

//          LogService.getService().logApplicationDebug("ERROR " + result.toString());
//          LogService.getService().logApplicationDebug("FORM ROLES " + form.getRoles() == null);
//          for (AbstractRole r : form.getRoles()) {
//              LogService.getService().logApplicationDebug("AbstractRole " + r);
//          }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", form);

            return "redirect:" + AAS_EDIT_USER_URI;
        }

        JPEUser u = (JPEUser) form.getUser();
        List<AbstractRole> newRoles = new ArrayList<>();
        if (u.getLogin() == null || "".equals(u.getLogin())) {
            u.setLogin(u.createLoginForLocalAccount());
            Role r = u.generateUserRole();
            manager.getRoleService().save(r);
            newRoles.add(r);
        }

        if (form.getRoles() != null) {
            for (AbstractRole r : form.getRoles()) {
                newRoles.add(manager.getRoleService().getDao().getById(r.getTopiaid()));
            }
        }

        u.setRoles(newRoles);
        manager.getUserService().save(u);

        return "redirect:" + AAS_USER_URI;
    }

    @RequestMapping(
            value = AAS_DELETE_USER_URI,
            method = RequestMethod.POST
    )
    public String delete_user(@RequestParam(
            value = "user",
            required = true
    ) AbstractUser user, Model model) {
        model.addAttribute("type", "user");

        try {
            manager.getUserService().delete(user);
        } catch (AASException ex) {
            LogService.getService(AASUserController.class).logApplicationError(ex.getMessage());
        }

        return "redirect:" + AAS_USER_URI;
    }

    @RequestMapping(
            value = AAS_DELETE_USER_URI,
            method = RequestMethod.GET
    )
    public String delete_user(@RequestParam(
            value = "id",
            required = true
    ) String id, Model model) throws AASException {
        model.addAttribute("type", "user");

        AbstractUser user = manager.getUserService().getDao().getById(id);

        manager.getUserService().delete(user);

        return "redirect:" + AAS_USER_URI;
    }
}
