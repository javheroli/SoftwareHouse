
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select c.applications from Contest c where c.manager.id = ?1")
	Collection<Application> findAllByManager(int managerId);

	@Query("select a from Contest c join c.applications a where a.status = 'REJECTED' and c.manager.id = ?1")
	Collection<Application> findRejectedByManager(int managerId);

	@Query("select a from Contest c join c.applications a where a.status = 'ACCEPTED' and c.manager.id = ?1")
	Collection<Application> findAcceptedByManager(int managerId);

	@Query("select a from Contest c join c.applications a where a.status = 'PENDING' and c.manager.id = ?1")
	Collection<Application> findPendingByManager(int managerId);

	@Query("select a from Contest c join c.applications a where a.status = 'DUE' and c.manager.id = ?1")
	Collection<Application> findDueByManager(int managerId);

	@Query("select a from Application a where a.contest.id = ?2 and a.applicant.id = ?1")
	Collection<Application> findByApprenticeAndContest(int apprenticeId, int contestId);

	@Query("select a from Application a where a.contest.id = ?1 and (a.status = 'PENDING' or a.status = 'DUE')")
	Collection<Application> findByContestWithStatusPendingOrDue(int contestId);

	@Query("select a from  Application a where a.contest.manager.id = ?1 and a.contest.startMoment <= CURRENT_TIMESTAMP and (a.status = 'PENDING' or a.status = 'DUE')")
	Collection<Application> findFinishedWithStatusPendingOrDueByManager(int managerId);

	@Query("select a.applications from Apprentice a where a.id = ?1")
	Collection<Application> findAllByApprentice(int apprenticeId);

	@Query("select x from Application x where x.status = 'PENDING' and x.applicant.id = ?1")
	Collection<Application> findPendingByApprentice(int apprenticeId);

	@Query("select x from Application x where x.status = 'REJECTED' and x.applicant.id = ?1")
	Collection<Application> findRejectedByApprentice(int apprenticeId);

	@Query("select x from Application x where x.status = 'DUE' and x.applicant.id = ?1")
	Collection<Application> findDueByApprentice(int apprenticeId);

	@Query("select x from  Application x where x.status = 'ACCEPTED' and x.applicant.id = ?1")
	Collection<Application> findAcceptedByApprentice(int apprenticeId);

	@Query("select a from Application a where a.applicant.id = ?1 and a.contest.startMoment <= CURRENT_TIMESTAMP and (a.status = 'PENDING' or a.status = 'DUE')")
	Collection<Application> findFinishedWithStatusPendingOrDueByApprentice(int apprenticeId);

	@Query("select a from Application a where a.applicant.id = ?1 and a.contest.id = ?2")
	Application findByApplicantAndByContest(int apprenticeId, int contestId);

	//Dashboard
	@Query("select 1.0*count(a)/(select count(n) from Application n) from Application a where a.status='PENDING'")
	Double ratioPendingApplications();

	@Query("select 1.0*count(a)/(select count(n) from Application n) from Application a where a.status='DUE'")
	Double ratioDueApplications();

	@Query("select 1.0*count(a)/(select count(n) from Application n) from Application a where a.status='ACCEPTED'")
	Double ratioAcceptedApplications();

	@Query("select 1.0*count(a)/(select count(n) from Application n) from Application a where a.status='REJECTED'")
	Double ratioRejectedApplications();
}
