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

import domain.Research;

@Component
@Transactional
public class ResearchToStringConverter implements Converter<Research, String> {

	@Override
	public String convert(final Research research) {
		String result;

		if (research == null)
			result = null;
		else
			result = String.valueOf(research.getId());

		return result;
	}

}
