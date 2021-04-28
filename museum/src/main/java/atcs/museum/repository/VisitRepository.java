package atcs.museum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import atcs.museum.domain.Visit;
import atcs.museum.domain.Visitor;

@Repository
public interface VisitRepository extends CrudRepository<Visit,Long> {
	public Visit findByVisitor(Visitor visitor);
}
