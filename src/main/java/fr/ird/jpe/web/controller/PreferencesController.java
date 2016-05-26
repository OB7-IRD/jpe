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

import fr.ird.aas.orm.AbstractUser;
import fr.ird.aas.realm.AASManager;
import fr.ird.aas.service.PasswordService;
import fr.ird.jpe.web.aas.JPEUser;
import fr.ird.jpe.web.controller.model.PasswordForm;
import fr.ird.jpe.web.controller.model.PreferencesUserForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 4 nov. 2014
 *
 */
@Controller
public class PreferencesController {

    public final static String PREFERENCE_URI = "/preferences";
    public final static String PREFERENCE_SAVE_URI = PREFERENCE_URI + "/save";
    public final static String PREFERENCE_PASSWORD_URI = PREFERENCE_URI + "/password";
    @Autowired
    private AASManager manager;

    @RequestMapping(
            value = PREFERENCE_URI,
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    public String index(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        PreferencesUserForm userForm;

        if (!model.containsAttribute("current_user")) {
            userForm = new PreferencesUserForm((JPEUser) (session.getAttribute("user")));
        } else {
            userForm = (PreferencesUserForm) model.asMap().get("current_user");
        }

        model.addAttribute("current_user", userForm);

        return "preferences/index";
    }

//  @RequestMapping(value = {PREFERENCE_URI}, method = RequestMethod.GET)
//  public String edit_user(@RequestParam(value = "id", required = false) String id, Model model) {
//      model.addAttribute("type", "user");
////      model.addAttribute("user", manager.getUserService().getDao().getById(id));
//
//      PreferencesUserForm userForm;
//      if (!model.containsAttribute("user")) {
//          if (id != null) {
//              System.out.println("ID != null > " + id);
//              userForm = new PreferencesUserForm((JPEUser) manager.getUserService().getDao().getById(id));
//          } else {
//              System.out.println("ID == null");
//              userForm = new PreferencesUserForm(new JPEUser());
//              System.out.println("new ID " + userForm.getTopiaid());
//          }
//      } else {
//          userForm = (PreferencesUserForm) model.asMap().get("user");
//      }
//      model.addAttribute("user", userForm);
//      return "preferences/index";
//  }
    @RequestMapping(
            value = PREFERENCE_SAVE_URI,
            method = RequestMethod.POST
    )
    public String edit_user(@Valid
            @ModelAttribute(value = "currrent_user") PreferencesUserForm form, BindingResult result,
            RedirectAttributes redirectAttributes, Model model) {

//      model.addAttribute("type", "user");
        if (result.hasErrors()) {
            System.out.println("ERROR " + result.toString());

//          System.out.println("FORM ROLES " + form.getRoles() == null);
//          for (Role r : form.getRoles()) {
//              System.out.println("Role " + r);
//          }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.currrent_user", result);
            redirectAttributes.addFlashAttribute("currrent_user", form);

            return "redirect:" + PREFERENCE_URI;
        }

        AbstractUser u = form.getUser();

        System.out.println("USER " + u);

//      List<Role> newRoles = new ArrayList<>();
//      if (form.getRoles() != null) {
//          for (Role r : form.getRoles()) {
//              System.out.println("Role in editRole " + r);
//              newRoles.add(manager.getRoleService().getDao().getById(r.getTopiaid()));
//          }
//      }
//      u.setRoles(newRoles);
        manager.getUserService().save(u);

        return "redirect:" + PREFERENCE_URI;
    }

    @RequestMapping(
            value = PREFERENCE_PASSWORD_URI,
            method = {RequestMethod.GET}
    )
    public String show_password(Model model) {
        PasswordForm passwordForm;

        if (!model.containsAttribute("passwordForm")) {
            passwordForm = new PasswordForm();
        } else {
            passwordForm = (PasswordForm) model.asMap().get("passwordForm");
        }

        model.addAttribute("passwordForm", passwordForm);

        return "preferences/password";
    }

    @RequestMapping(
            value = PREFERENCE_PASSWORD_URI,
            method = {RequestMethod.POST}
    )
    public String edit_password(@Valid
            @ModelAttribute(value = "passwordForm") PasswordForm form, BindingResult result,
            RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        if (result.hasErrors()) {
            System.out.println("ERROR " + result.toString());

//          System.out.println("FORM ROLES " + form.getRoles() == null);
//          for (Role r : form.getRoles()) {
//              System.out.println("Role " + r);
//          }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.passwordForm", result);
            redirectAttributes.addFlashAttribute("passwordForm", form);

            return "redirect:" + PREFERENCE_PASSWORD_URI;
        }

        AbstractUser user = (AbstractUser) session.getAttribute("user");

        user.setPassword(PasswordService.encrypt(form.getPassword()));
        System.out.println(">>>> " + user);
        manager.getUserService().save(user);

        return "redirect:" + PREFERENCE_URI;
    }
}
