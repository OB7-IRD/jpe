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

import fr.ird.aas.exception.AuthorizationUsedException;
import fr.ird.aas.orm.AbstractAuthorization;
import fr.ird.aas.orm.Authorization;
import fr.ird.aas.realm.AASManager;
import fr.ird.common.log.LogService;
import static fr.ird.jpe.web.controller.AASController.*;
import fr.ird.jpe.web.controller.model.AASAuthorizationForm;

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
public class AASAuthorizationController {

    @Autowired
    private AASManager manager;

    @ModelAttribute("allAuthorizations")
    public List<AbstractAuthorization> populateAuthorizations() {
        return manager.getAuthorizationService().getDao().list();
    }

    @RequestMapping(
            value = {AAS_AUTH_URI, AAS_LIST_AUTH_URI},
            method = RequestMethod.GET
    )
    public String authorizations(Model model) {
        model.addAttribute("type", "authorization");
        model.addAttribute("authorizations", manager.getAuthorizationService().getDao().list());
        LogService.getService(this.getClass()).logApplicationDebug("GET on " + AAS_LIST_AUTH_URI + " - " + manager.getAuthorizationService().getDao().list());
        return "aas/list/authorization";
    }

//  @RequestMapping(value = AAS_ADD_AUTH_URI, method = RequestMethod.GET)
//  public String add_authorization(Model model) {
//      model.addAttribute("type", "authorization");
//      AbstractAuthorization authorization = new Authorization();
//      model.addAttribute("authorization", new AASAuthorizationForm(authorization));
//      return "aas/edit";
//  }
    @RequestMapping(
            value = {AAS_ADD_AUTH_URI, AAS_EDIT_AUTH_URI},
            method = RequestMethod.GET
    )
    public String edit_authorization(@RequestParam(value = "id", required = false) String id,
            Model model) {
        model.addAttribute("type", "authorization");

        AASAuthorizationForm authorizationForm;

        if (!model.containsAttribute("authorization")) {
            if (id != null) {
                authorizationForm = new AASAuthorizationForm(manager.getAuthorizationService().getDao().getById(id));
            } else {
                authorizationForm = new AASAuthorizationForm(new Authorization());
            }
        } else {
            authorizationForm = (AASAuthorizationForm) model.asMap().get("authorization");

//          id = authorizationForm.getId();
        }

        model.addAttribute("authorization", authorizationForm);

        return "aas/edit/authorization";
    }

    @RequestMapping(
            value = AAS_EDIT_AUTH_URI,
            method = RequestMethod.POST
    )
    public String edit_authorization(@Valid
            @ModelAttribute("authorization") AASAuthorizationForm form, BindingResult result,
            RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            LogService.getService().logApplicationDebug("ERROR " + result.toString());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.authorization", result);
            redirectAttributes.addFlashAttribute("authorization", form);

            return "redirect:" + AAS_EDIT_AUTH_URI;
        }

        manager.getAuthorizationService().save(form.getAuthorization());

        return "redirect:" + AAS_AUTH_URI;
    }

    @RequestMapping(
            value = AAS_DELETE_AUTH_URI,
            method = RequestMethod.POST
    )
    public String delete_authorization(@RequestParam(
            value = "auth",
            required = true
    ) AbstractAuthorization authorization, Model model) {
        model.addAttribute("type", "authorization");

        try {
            manager.getAuthorizationService().delete(authorization);
        } catch (AuthorizationUsedException ex) {
            LogService.getService(AASAuthorizationController.class).logApplicationError(ex.getMessage());
        }

        return "redirect:" + AAS_AUTH_URI;
    }

    @RequestMapping(
            value = AAS_DELETE_AUTH_URI,
            method = RequestMethod.GET
    )
    public String delete_authorization(@RequestParam(
            value = "id",
            required = true
    ) String id, Model model) {
        model.addAttribute("type", "authorization");

        AbstractAuthorization authorization = manager.getAuthorizationService().getDao().getById(id);

        try {
            manager.getAuthorizationService().delete(authorization);
        } catch (AuthorizationUsedException ex) {
            LogService.getService(AASAuthorizationController.class).logApplicationError(ex.getMessage());
        }

        return "redirect:" + AAS_AUTH_URI;
    }
}
