
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Solution;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Integer> {

	@Query("select s from Solution s, Contest c where c.id = ?1 and s.application member of c.applications")
	Collection<Solution> findAllByContest(int contestId);

	@Query("select s from Solution s where s.application.applicant.id = ?1")
	Collection<Solution> findAllByApprentice(int apprenticeId);

	@Query("select s from Solution s where s.application.applicant.id = ?1 and s.application.contest.id = ?2")
	Solution findByApprenticeAndContest(int apprenticeId, int contestId);

	@Query("select avg(s.mark) from Solution s where s.mark is not null and s.application.contest.id = ?1")
	Double getAvgMarkByContest(int contestId);

	@Query("select min(s.mark) from Solution s where s.mark is not null and s.application.contest.id = ?1")
	Double getMinMarkByContest(int contestId);

	@Query("select max(s.mark) from Solution s where s.mark is not null and s.application.contest.id = ?1")
	Double getMaxMarkByContest(int contestId);
}
