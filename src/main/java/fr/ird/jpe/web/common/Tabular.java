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
package fr.ird.jpe.web.common;

//~--- JDK imports ------------------------------------------------------------
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un tableau permettant un traitement plus directe dans le moteur de
 * patrons notamment pour la génération des pages HTML.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 30 oct. 2014
 *
 */
public class Tabular {

    private List<String> headers;
    private List<String> footers;

    private List<Object> data;

    public Tabular() {
        this(new ArrayList(), new ArrayList(), new ArrayList());
    }

    public Tabular(List<String> headers, List<String> footers, List<Object> data) {
        this.headers = headers;
        this.footers = footers;
        this.data = data;
    }

    /**
     *
     * @return la liste des entetes
     */
    public List<String> getHeaders() {
        return headers;
    }

    /**
     *
     * @param headers assigne la liste des entetes
     */
    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    /**
     *
     * @return la liste des pieds de pages
     */
    public List<String> getFooters() {
        return footers;
    }

    /**
     *
     * @param footers assigne la liste des pieds de pages
     */
    public void setFooters(List<String> footers) {
        this.footers = footers;
    }

    /**
     *
     * @return la liste des données du tableau
     */
    public List<Object> getData() {
        return data;
    }

    /**
     *
     * @param data assigne la liste des données du tableau
     */
    public void setData(List<Object> data) {
        this.data = data;
    }
}
