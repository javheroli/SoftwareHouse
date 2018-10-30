
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Research;

@Repository
public interface ResearchRepository extends JpaRepository<Research, Integer> {

	@Query("select r from Research r, Expert e where e member of r.team and e.id=?1")
	Collection<Research> findAllByExpert(int expertId);

	@Query("select r from Research r where r.isCancelled = FALSE and r.endDate is NULL")
	Collection<Research> findAllThatNeedFunding();

	//Dashboard
	@Query("select 1.0*count(r)/(select count(x) from Research x) from Research r where r.budget < r.minCost")
	Double ratioResearchesNotStarted();

	@Query("select avg(r.investments.size) from Research r")
	Double avgInvestmentsPerResearch();

	@Query("select max(r.investments.size) from Research r")
	Double maxInvestmentsPerResearch();

	@Query("select min(r.investments.size) from Research r")
	Double minInvestmentsPerResearch();

	@Query("select sqrt(sum(r.investments.size * r.investments.size) / count(r.investments.size) - (avg(r.investments.size) * avg(r.investments.size))) from Research r")
	Double stdDeviationInvestmentsPerResearch();
}
