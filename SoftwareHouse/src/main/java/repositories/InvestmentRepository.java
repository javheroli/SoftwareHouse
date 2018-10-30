
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Investment;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Integer> {

	@Query("select i from Investment i where i.investor.id=?1")
	Collection<Investment> findAllByInvestor(int investorId);

	@Query("select sum(i.amount) from Investment i where i.investor.id=?1")
	Double getTotalAmountInvestedByInvestor(int investorId);
}
