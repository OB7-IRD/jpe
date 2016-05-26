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
import fr.ird.jpe.web.aas.JPEUser;
import fr.ird.jpe.web.controller.model.LoginForm;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Log in and log out controller. This class handles the user authentication
 * with a login form. When the login processing is valid, the user is redirect
 * to the logbook page.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 3 nov. 2014
 */
@Controller
public class LogController {

    public static final String LOGIN_URI = "/login";
    public static final String LOGOUT_URI = "/logout";

    AbstractUser user = null;
    @Autowired
    private AASManager manager;

    /**
     * This request show a login form.
     *
     * @param model the implicit model that will be exposed to the web view
     * @return the login's view name
     */
    @RequestMapping(
            value = LOGIN_URI,
            method = RequestMethod.GET
    )
    public String showLogin(Map model) {
        LoginForm loginForm = new LoginForm();

        model.put("loginForm", loginForm);

        return "pages/login";
    }

    /**
     * This request process the login operation.
     *
     * @param loginForm the form with the user data
     * @param result
     * @param model the implicit model that will be exposed to the web view
     * @param session the http session
     * @param request
     * @param response
     * @return the login's view name if the autentication fails otherwise a
     * redirection to the logbook url
     */
    @RequestMapping(
            value = LOGIN_URI,
            method = RequestMethod.POST
    )
    public String processLogin(@Valid
            @ModelAttribute("loginForm") LoginForm loginForm, BindingResult result, Map model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response) {
        if (result.hasErrors()) {
            return "pages/login";
        }

        loginForm = (LoginForm) model.get("loginForm");

        String identifier = loginForm.getIdentifier();
        String password = loginForm.getPassword();
        AuthenticationToken token = new UsernamePasswordToken(identifier, password);

        // Retrieving the Shiro user session
        user = (JPEUser) session.getAttribute("user");

        // AbstractUser present and authenticated
        if ((user != null) && (user.getShiroSubject() != null) && user.getShiroSubject().isAuthenticated()) {
            return "redirect:" + LogbookController.TRIP_URI;
        } else {
            // Fully authenticate and reload the user
            Subject currentShiroUser = SecurityUtils.getSubject();

            // Authenticating the current subject
            try {
                currentShiroUser.login(token);
            } catch (UnknownAccountException e) {
                result.addError(new ObjectError("loginForm", "authentication.invalidAccountOrPassword"));

                return "pages/login";
            } catch (IncorrectCredentialsException e) {
                result.addError(new ObjectError("loginForm", "authentication.invalidAccountOrPassword"));

                return "pages/login";
            } catch (LockedAccountException e) {
                result.addError(new ObjectError("loginForm", "authentication.accountLocked"));

                return "pages/login";
            } catch (AuthenticationException e) {
                result.addError(new ObjectError("loginForm", "authentication.invalidAccountOrPassword"));

                return "pages/login";
            }

            // Retrieving user infos
            user = manager.getUserService().getDao().getById((String) currentShiroUser.getPrincipal());

            // Adding the Shiro subject to the user coming from the db
            user.setShiroSubject(currentShiroUser);

            // Putting all that stuffs in the session
            session.setAttribute("user", user);
        }

        return "redirect:" + LogbookController.TRIP_URI;
    }

    /**
     * This request handles a logout operation.
     *
     * @param model the implicit model that will be exposed to the web view
     * @param session the http session
     * @return a redirection string to the exit url
     */
    @RequestMapping(
            value = LOGOUT_URI,
            method = RequestMethod.GET
    )
    public String showLogout(Map model, HttpSession session) {
        JPEUser jpeUser = (JPEUser) session.getAttribute("user");

        if ((jpeUser != null) && (jpeUser.getShiroSubject() != null)) {
            jpeUser.setLastAccess(DateTime.now());
            manager.getUserService().getDao().save(jpeUser);
        }

        return "redirect:/exit";
    }
}
