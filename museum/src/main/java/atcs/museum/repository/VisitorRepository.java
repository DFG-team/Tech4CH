package atcs.museum.repository;

import java.util.List;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import atcs.museum.domain.*;

@Repository
public interface VisitorRepository extends CrudRepository<Visitor,Long> {
	

}
