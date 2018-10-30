
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.InvestmentRepository;
import domain.Investment;

@Component
@Transactional
public class StringToInvestmentConverter implements Converter<String, Investment> {

	@Autowired
	InvestmentRepository	investmentRepository;


	@Override
	public Investment convert(final String text) {
		Investment result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.investmentRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
