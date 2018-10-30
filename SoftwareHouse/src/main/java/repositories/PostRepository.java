
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	@Query("select p from Post p where p.topic.id = ?1 ORDER BY p.publicationMoment ASC")
	Collection<Post> findAllPostsByThreadOrderedByMomentPublication(int threadId);

	@Query("select p from Post p where p.publicationMoment = (select max(p.publicationMoment) from Post p where p.topic.forum.id=?1)")
	Post findLastPostByForum(int forumId);

	//Dashboard
	@Query("select 1.0*count(p)/(select count(x) from Post x) from Post p where p.isDeleted = true")
	Double ratioDeletedPosts();
}
