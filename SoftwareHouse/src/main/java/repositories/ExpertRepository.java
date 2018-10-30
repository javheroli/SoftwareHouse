
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Expert;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Integer> {

	@Query("select e from Expert e where e.userAccount.id = ?1")
	Expert findByUserAccountId(int userAccountId);

	@Query("select e from Expert e, Discipline d where d.id = ?1 and d member of e.disciplines")
	Collection<Expert> findAllByDiscipline(int disciplineId);

}
