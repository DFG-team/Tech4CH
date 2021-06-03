package atcs.museum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import atcs.museum.domain.Presentation;
import atcs.museum.domain.PresentationVisitor;

@Repository
public interface PresentationRepository extends CrudRepository<Presentation,Long>{
	public Presentation findByName(String name);

}
