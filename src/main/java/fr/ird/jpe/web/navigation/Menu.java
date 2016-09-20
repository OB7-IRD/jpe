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
package fr.ird.jpe.web.navigation;

import fr.ird.jpe.web.common.IconConstant;
import static fr.ird.jpe.web.controller.AASController.*;
import static fr.ird.jpe.web.controller.LogbookController.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the dashboard's navigation bar. The portal is made up
 * of a vertical and horizontal menu.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 9 oct. 2014
 *
 */
public class Menu {

    private List<MenuItem> items;

    public Menu() {
        this(new ArrayList<MenuItem>());
    }

    public Menu(List<MenuItem> items) {
        this.items = items;
    }

    /**
     * Generate the horizontal menu.
     *
     * @return the horizontal menu
     */
    public static Menu factoryHorizontalMenu() {
        Menu menu = new Menu();
        MenuItem tripItem = new MenuItem(TRIP_URI, "service.menu.label.trip", IconConstant.JPE_ERS_ICONS);
        menu.addMenuItem(tripItem);

//      menu.addMenuItem(new MenuItem("", "service.menu.label.filemanager"));
//      menu.addMenuItem(new MenuItem("", "service.menu.label.admin"));
        return menu;
    }

    /**
     * Generate the admin menu.
     *
     * @return the admin menu
     */
    public static Menu administratorMenu() {
        Menu menu = new Menu();

        MenuItem aasItem = new MenuItem(AAS_URI, "service.menu.label.aas", IconConstant.AAS_ICONS);
        MenuItem aasSubItem = new MenuItem(AAS_USER_URI, "service.menu.label.aas.user", IconConstant.AAS_USER_ICONS);
        aasItem.addChildren(aasSubItem);
        aasSubItem = new MenuItem(AAS_ROLE_URI, "service.menu.label.aas.role", IconConstant.AAS_ROLE_ICONS);
        aasItem.addChildren(aasSubItem);
        aasSubItem = new MenuItem(AAS_AUTH_URI, "service.menu.label.aas.authorization", IconConstant.AAS_AUTH_ICONS);
        aasItem.addChildren(aasSubItem);
        menu.addMenuItem(aasItem);

        return menu;
    }

    /**
     * Generate the vertical menu.
     *
     * @return the vertical menu
     */
    public static Menu factoryVerticalMenu() {
        Menu menu = new Menu();

//        menu.addMenuItem(new MenuItem(DASHBOARD_URI, "service.menu.label.dashboard", "fa-dashboard"));
        MenuItem tripItem = new MenuItem(TRIP_URI, "service.menu.label.trip", "fa-ship");

//        tripItem.addChildren(new MenuItem(TRIP_ACTIVE_URI, "service.menu.label.trip.active"));
        tripItem.addChildren(new MenuItem(TRIP_LIST_URI, "service.menu.label.trip.list"));
        menu.addMenuItem(tripItem);
        return menu;
    }

    /**
     * Add a menu item to the menu
     *
     * @param item a menu item added
     */
    public void addMenuItem(MenuItem item) {
        getItems().add(item);
    }

    /**
     *
     * @param items list of menu items
     */
    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    /**
     *
     * @return list of menu items
     */
    public List<MenuItem> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }
}
