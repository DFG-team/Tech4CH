package atcs.museum.repository;

import org.springframework.data.repository.CrudRepository;

import atcs.museum.domain.GroupVisit;
import atcs.museum.domain.PointOfInterest;

public interface GroupRepository extends CrudRepository<GroupVisit,Long>{

}
