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

@Component
@Transactional
public class ThreadToStringConverter implements Converter<domain.Thread, String> {

	@Override
	public String convert(final domain.Thread thread) {
		String result;

		if (thread == null)
			result = null;
		else
			result = String.valueOf(thread.getId());

		return result;
	}

}
