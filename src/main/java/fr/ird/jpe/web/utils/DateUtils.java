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

import java.util.Date;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean which provides access to features for processing dates.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 6 oct. 2014
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 *
 */
@Configuration
public class DateUtils {

    private static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String format(DateTime dt) {
        return dt.toString(FULL_DATE_FORMAT);
    }

    @Bean
    public String getCurrentDate() {
        return new DateTime(new Date()).toString(FULL_DATE_FORMAT);
    }

    @Bean
    public String getCurrentYear() {
        return "" + new DateTime().getYear();
    }
}
