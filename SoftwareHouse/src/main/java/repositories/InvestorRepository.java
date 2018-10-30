
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Investor;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Integer> {

	@Query("select i from Investor i where i.userAccount.id = ?1")
	Investor findByUserAccountId(int userAccountId);

}
