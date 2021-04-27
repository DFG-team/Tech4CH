package atcs.museum.repository;

import org.springframework.data.repository.CrudRepository;

import atcs.museum.domain.PointOfInterestVisitor;
import atcs.museum.domain.PresentationVisitor;

public interface PointOfInterestVisitorRepository extends CrudRepository<PointOfInterestVisitor,Long>{

}
