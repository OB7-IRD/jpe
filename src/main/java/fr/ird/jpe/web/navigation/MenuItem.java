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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 9 oct. 2014
 *
 */
public class MenuItem {

    private List<MenuItem> children = new ArrayList<>();
    private String uri;
    private String label;
    private String icon;
    private String htmlId;

    public MenuItem(String uri, String label) {
        this(uri, label, "", "", new ArrayList<MenuItem>());
    }

    public MenuItem(String uri, String label, String icon) {
        this(uri, label, icon, "", new ArrayList<MenuItem>());
    }

    public MenuItem(String uri, String label, String icon, String htmlId) {
        this(uri, label, icon, htmlId, new ArrayList<MenuItem>());
    }

    public MenuItem(String uri, String label, String icon, String htmlId, List<MenuItem> children) {
        this.uri = uri;
        this.label = label;
        this.icon = icon;
        this.children = children;
        this.htmlId = htmlId;
    }

    public List<MenuItem> getChildren() {
        return children;
    }

    public String getHtmlId() {
        return htmlId;
    }

    public void setHtmlId(String htmlId) {
        this.htmlId = htmlId;
    }

    public void addChildren(MenuItem item) {
        getChildren().add(item);
    }

    public String getIcon() {
        return icon;
    }

    public String getUri() {
        return uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }
}
