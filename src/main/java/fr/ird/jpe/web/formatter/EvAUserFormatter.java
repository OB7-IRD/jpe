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
package fr.ird.jpe.web.formatter;

import fr.ird.aas.orm.AbstractUser;
import fr.ird.jpe.web.aas.JPEUser;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 25 nov. 2014
 *
 */
public class EvAUserFormatter implements Formatter<AbstractUser> {

    @Override
    public String print(AbstractUser u, Locale locale) {
        return u.getId();
    }

    @Override
    public JPEUser parse(String id, Locale locale) throws ParseException {

        JPEUser u = new JPEUser();

        u.setTopiaid(id);

        return u;
    }
}
