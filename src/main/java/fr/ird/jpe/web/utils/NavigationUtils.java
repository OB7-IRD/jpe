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
package fr.ird.jpe.web.utils;

import fr.ird.jpe.web.navigation.Menu;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Configuration;

/**
 * Bean which provides the navigation utilities.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 8 oct. 2014
 *
 */
@Configuration
public class NavigationUtils {

    private Menu verticalMenu;
    private Menu horizontalMenu;
    private Menu administratorMenu;

    public Menu getVerticalMenu() {
        if (verticalMenu == null) {
            verticalMenu = Menu.factoryVerticalMenu();
        }
        return verticalMenu;
    }

    public Menu getHorizontalMenu() {
        if (horizontalMenu == null) {
            horizontalMenu = Menu.factoryHorizontalMenu();
        }
        return horizontalMenu;
    }

    public Menu getAdminMenu() {
        if (administratorMenu == null) {
            administratorMenu = Menu.administratorMenu();
        }
        return administratorMenu;
    }
    private Map locales;

    public Map getLocales() {
        if (locales == null) {
            locales = new HashMap<>();
            locales.put("fr", "Français");
            locales.put("en", "English");
        }
        return locales;
    }
}
