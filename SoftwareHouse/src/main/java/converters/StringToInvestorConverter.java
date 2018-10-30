
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.InvestorRepository;
import domain.Investor;

@Component
@Transactional
public class StringToInvestorConverter implements Converter<String, Investor> {

	@Autowired
	InvestorRepository	investorRepository;


	@Override
	public Investor convert(final String text) {
		Investor result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.investorRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
