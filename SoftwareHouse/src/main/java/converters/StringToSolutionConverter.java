
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SolutionRepository;
import domain.Solution;

@Component
@Transactional
public class StringToSolutionConverter implements Converter<String, Solution> {

	@Autowired
	SolutionRepository	solutionRepository;


	@Override
	public Solution convert(final String text) {
		Solution result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.solutionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
