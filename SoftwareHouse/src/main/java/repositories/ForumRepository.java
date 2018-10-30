
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Forum;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Integer> {

	//Dashborad
	@Query("select avg(f.threads.size) from Forum f")
	Double avgThreadsPerForum();

	@Query("select max(f.threads.size) from Forum f")
	Double maxThreadsPerForum();

	@Query("select min(f.threads.size) from Forum f")
	Double minThreadsPerForum();

	@Query("select sqrt(sum(f.threads.size * f.threads.size) / count(f.threads.size) - (avg(f.threads.size) * avg(f.threads.size))) from Forum f")
	Double stdDeviationThreadsPerForum();

}
