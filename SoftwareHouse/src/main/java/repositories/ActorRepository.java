
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccountId(int userAccountId);

	//Dashboard
	@Query("select a from Actor a where a.posts.size >= 1.1 * (select avg(x.posts.size) from Actor x)")
	Collection<Actor> actorsWith10PercentMorePostsThanAverage();

}
