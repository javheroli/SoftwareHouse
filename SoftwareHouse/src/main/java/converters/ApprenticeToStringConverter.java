/*
 * AnnouncementToStringConverter.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Apprentice;

@Component
@Transactional
public class ApprenticeToStringConverter implements Converter<Apprentice, String> {

	@Override
	public String convert(final Apprentice apprentice) {
		String result;

		if (apprentice == null)
			result = null;
		else
			result = String.valueOf(apprentice.getId());

		return result;
	}

}
