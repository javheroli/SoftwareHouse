
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Discipline;

@Component
@Transactional
public class DisciplineToStringConverter implements Converter<Discipline, String> {

	@Override
	public String convert(final Discipline discipline) {
		String result;

		if (discipline == null)
			result = null;
		else
			result = String.valueOf(discipline.getId());

		return result;
	}

}
