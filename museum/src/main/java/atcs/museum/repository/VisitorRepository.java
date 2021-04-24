package atcs.museum.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import atcs.museum.domain.*;
public interface VisitorRepository extends CrudRepository<Visitor,Long> {
	public List<Visitor> findByName(String name);
	

}
