package atcs.museum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import atcs.museum.domain.PointOfInterest;
import atcs.museum.domain.Room;

@Repository
public interface PointOfInterestRepository extends CrudRepository<PointOfInterest,Long>{
	public PointOfInterest findByName(String name);
}