
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ThreadRepository;

@Component
@Transactional
public class StringToThreadConverter implements Converter<String, domain.Thread> {

	@Autowired
	ThreadRepository	threadRepository;


	@Override
	public domain.Thread convert(final String text) {
		domain.Thread result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.threadRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
