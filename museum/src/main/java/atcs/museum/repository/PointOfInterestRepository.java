package atcs.museum.repository;

import org.springframework.data.repository.CrudRepository;

import atcs.museum.domain.PointOfInterest;


public interface PointOfInterestRepository extends CrudRepository<PointOfInterest,Long>{

}