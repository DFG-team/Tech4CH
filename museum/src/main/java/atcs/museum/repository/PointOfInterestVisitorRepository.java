package atcs.museum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import atcs.museum.domain.PointOfInterestVisitor;
import atcs.museum.domain.PresentationVisitor;

@Repository
public interface PointOfInterestVisitorRepository extends CrudRepository<PointOfInterestVisitor,Long>{
	
	
	

}
