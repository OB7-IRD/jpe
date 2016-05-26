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
package fr.ird.jpe.web.controller.model;

import java.util.Locale;
import org.springframework.context.MessageSource;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 0.0
 * @date 5 févr. 2015
 *
 * $LastChangedDate$
 *
 * $LastChangedRevision$
 *
 */
public abstract class AbstractShowJob {

    private final Locale locale;
    private final MessageSource messageSource;

    public AbstractShowJob(MessageSource source, Locale locale) {
        this.locale = locale;
        this.messageSource = source;
    }

    protected String getMessage(String i18nCode) {
        return messageSource.getMessage(i18nCode, null, locale);
    }

    protected String getMessage(String i18nCode, Object[] args) {
        return messageSource.getMessage(i18nCode, args, locale);
    }

}
