
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends JpaRepository<domain.Thread, Integer> {

	@Query("select t from Thread t where t.forum.id = ?1 ORDER BY t.momentLastModification DESC")
	Collection<domain.Thread> findAllThreadsByForumOrderedByMomentLastModification(int forumId);

	@Query("select t from Thread t, Discipline d where d.id = ?1 and d member of t.disciplines")
	Collection<domain.Thread> findAllByDiscipline(int disciplineId);

	//Dashborad
	@Query("select avg(t.numPosts) from Thread t")
	Double avgPostsPerThread();

	@Query("select max(t.numPosts) from Thread t")
	Double maxPostsPerThread();

	@Query("select min(t.numPosts) from Thread t")
	Double minPostsPerThread();

	@Query("select sqrt(sum(t.numPosts * t.numPosts) / t.numPosts - (avg(t.numPosts) * avg(t.numPosts))) from Thread t")
	Double stdDeviationPostsPerThread();
}
