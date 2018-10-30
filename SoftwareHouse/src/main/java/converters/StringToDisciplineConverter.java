
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.DisciplineRepository;
import domain.Discipline;

@Component
@Transactional
public class StringToDisciplineConverter implements Converter<String, Discipline> {

	@Autowired
	DisciplineRepository	disciplineRepository;


	@Override
	public Discipline convert(final String text) {
		Discipline result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.disciplineRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
