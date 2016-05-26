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

import fr.ird.aas.orm.Role;
import fr.ird.aas.orm.AbstractRole;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 24 nov. 2014
 *
 */
public class RoleFormatter implements Formatter<AbstractRole> {

    @Override
    public String print(AbstractRole r, Locale locale) {
        return r.getId();
    }

    @Override
    public AbstractRole parse(String id, Locale locale) throws ParseException {
        AbstractRole r = new Role();

        r.setTopiaid(id);

        return r;
    }
}
