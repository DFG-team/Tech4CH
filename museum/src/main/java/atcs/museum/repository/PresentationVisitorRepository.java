package atcs.museum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import atcs.museum.domain.PresentationVisitor;
import atcs.museum.domain.Visitor;

@Repository
public interface PresentationVisitorRepository extends CrudRepository<PresentationVisitor,Long>{
	
}
