package atcs.museum.repository;

import org.springframework.data.repository.CrudRepository;

import atcs.museum.domain.Presentation;
import atcs.museum.domain.PresentationVisitor;

public interface PresentationRepository extends CrudRepository<Presentation,Long>{


}
