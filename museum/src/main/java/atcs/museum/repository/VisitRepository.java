package atcs.museum.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import atcs.museum.domain.Visit;
import atcs.museum.domain.Visitor;

public interface VisitRepository extends CrudRepository<Visit,Long> {
	public List<Visitor> findByVisitor(String name);
}
