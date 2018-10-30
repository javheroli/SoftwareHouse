
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Discipline;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {

	@Query("select d from Discipline d, Contest c where d.id = c.discipline.id")
	Collection<Discipline> findUsedDisciplines();

}
