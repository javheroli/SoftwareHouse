
package repositories;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Apprentice;
import domain.Contest;
import domain.Solution;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Integer> {

	@Query("select c from Contest c where c.isDraft = false")
	Collection<Contest> findAllPublished();

	@Query("select c from Contest c where c.manager.id = ?1")
	Collection<Contest> findAllCreatedByManager(int managerId);

	@Query("select c from Contest c where c.isDraft = true and c.editor.id = ?1")
	Collection<Contest> findAllDraftByEditor(int expertId);

	@Query("select c from Contest c, Expert e, Solution s where c.isDraft = false and c.endMoment < CURRENT_TIMESTAMP and e member of c.judges and e.id = ?1 and s.application member of c.applications and s.mark is null")
	Set<Contest> findAllFinishedPendingToAssessByJudge(int expertId);

	@Query("select x.application.applicant from Solution x where x.mark = (select max(s.mark) from Contest c, Solution s where c.id = ?1 and s.application member of c.applications)")
	Collection<Apprentice> findWinnersByContest(int contestId);

	@Query("select s from Contest c, Solution s where c.id = ?1 and s.application member of c.applications and s.mark is null")
	Collection<Solution> findSolutionsNotAssessedByContest(int contestId);

	@Query("select c from Contest c where c.discipline.id = ?1")
	Collection<Contest> findAllByDiscipline(int disciplineId);

	//Dashborad
	@Query("select avg(c.applications.size) from Contest c")
	Double avgApplicationsPerContest();

	@Query("select max(c.applications.size) from Contest c")
	Double maxApplicationsPerContest();

	@Query("select min(c.applications.size) from Contest c")
	Double minApplicationsPerContest();

	@Query("select sqrt(sum(c.applications.size * c.applications.size) / count(c.applications.size) - (avg(c.applications.size) * avg(c.applications.size))) from Contest c")
	Double stdDeviationApplicationsPerContest();

	@Query("select avg(c.problems.size) from Contest c")
	Double avgProblemsPerContest();

	@Query("select max(c.problems.size) from Contest c")
	Double maxProblemsPerContest();

	@Query("select min(c.problems.size) from Contest c")
	Double minProblemsPerContest();

	@Query("select sqrt(sum(c.problems.size * c.problems.size) / count(c.problems.size) - (avg(c.problems.size) * avg(c.problems.size))) from Contest c")
	Double stdDeviationProblemsPerContest();

	@Query("select c from Contest c where exists (select s from Solution s where s.mark is not null and s.application.contest.id = c.id)")
	Collection<Contest> findEvaluatedContests();

}
