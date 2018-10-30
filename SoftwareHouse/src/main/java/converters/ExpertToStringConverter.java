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

import domain.Expert;

@Component
@Transactional
public class ExpertToStringConverter implements Converter<Expert, String> {

	@Override
	public String convert(final Expert expert) {
		String result;

		if (expert == null)
			result = null;
		else
			result = String.valueOf(expert.getId());

		return result;
	}

}
