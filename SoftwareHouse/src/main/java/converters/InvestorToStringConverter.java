
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Investor;

@Component
@Transactional
public class InvestorToStringConverter implements Converter<Investor, String> {

	@Override
	public String convert(final Investor investor) {
		String result;

		if (investor == null)
			result = null;
		else
			result = String.valueOf(investor.getId());

		return result;
	}

}
