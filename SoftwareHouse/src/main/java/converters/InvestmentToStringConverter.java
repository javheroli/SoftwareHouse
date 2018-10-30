
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Investment;

@Component
@Transactional
public class InvestmentToStringConverter implements Converter<Investment, String> {

	@Override
	public String convert(final Investment investment) {
		String result;

		if (investment == null)
			result = null;
		else
			result = String.valueOf(investment.getId());

		return result;
	}

}
