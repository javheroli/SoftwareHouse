
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Solution;

@Component
@Transactional
public class SolutionToStringConverter implements Converter<Solution, String> {

	@Override
	public String convert(final Solution solution) {
		String result;

		if (solution == null)
			result = null;
		else
			result = String.valueOf(solution.getId());

		return result;
	}

}
