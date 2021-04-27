package atcs.museum.repository;

import org.springframework.data.repository.CrudRepository;

import atcs.museum.domain.PresentationVisitor;
import atcs.museum.domain.Visitor;

public interface PresentationVisitorRepository extends CrudRepository<PresentationVisitor,Long>{
	
}
