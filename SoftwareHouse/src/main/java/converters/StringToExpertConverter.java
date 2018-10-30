
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ExpertRepository;
import domain.Expert;

@Component
@Transactional
public class StringToExpertConverter implements Converter<String, Expert> {

	@Autowired
	ExpertRepository	expertRepository;


	@Override
	public Expert convert(final String text) {
		Expert result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.expertRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
