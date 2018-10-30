
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Apprentice;

@Repository
public interface ApprenticeRepository extends JpaRepository<Apprentice, Integer> {

	@Query("select a from Apprentice a where a.userAccount.id = ?1")
	Apprentice findByUserAccountId(int userAccountId);
}
