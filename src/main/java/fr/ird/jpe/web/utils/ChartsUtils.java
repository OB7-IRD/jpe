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

import com.googlecode.wickedcharts.highcharts.jackson.JsonRenderer;
import fr.ird.highcharts.AbstractChart;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 27 oct. 2014
 *
 */
public class ChartsUtils {

    public static String stringify(AbstractChart chart) {
        JsonRenderer jr = new JsonRenderer();

        return jr.toJson(chart).trim().replaceAll("\\s+", " ").replaceAll("(function.*;})",
                "\"$1\"").replaceAll("(Highcharts.*?),", "\"$1\"',");
    }
}
