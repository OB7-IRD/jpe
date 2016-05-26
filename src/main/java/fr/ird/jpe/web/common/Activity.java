/*
 * Copyright (C) 2015 IRD
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
package fr.ird.jpe.web.common;

/**
 * Représente une action réalisable dans le projet. Dans le thème actuelle
 * Metro-UI, c'est symbolisé par une tuile.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 3 févr. 2015
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 *
 */
public class Activity {

    public static final String STATISTIC = "stats";
    public static final String LISTING = "list";
    public static final String EXECUTE = "exec";
    public static final String OBSERVE = "observe";
    public static final String ERS = "ers";
    public static final String REFERENTIEL = "database";
    public static final String REPORT = "report";

    public Activity(String label, String type, String uri) {
        this(label, type, uri, null);
    }

    public Activity(String label, String type, String uri, String description) {
        this.uri = uri;
        this.type = type;
        this.label = label;
        this.description = description;
    }

    private String uri;
    private String type;
    private String label;
    private String description;

    /**
     *
     * @return l'uri de l'activité
     */
    public String getUri() {
        return uri;
    }

    /**
     *
     * @param uri assigne l'uri de l'activité
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     *
     * @return le type de l'activité
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type assigne le type de l'activité
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return le label de l'activité (une entrée du fichier de propriétés i18n)
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @param label assigne le label de l'activité (une entrée du fichier de
     * propriétés i18n)
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     *
     * @return la description de l'activité (une entrée du fichier de propriétés
     * i18n)
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description assigne la description de l'activité (une entrée du
     * fichier de propriétés i18n)
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
