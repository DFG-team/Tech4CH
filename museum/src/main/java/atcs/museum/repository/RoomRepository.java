


package atcs.museum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import atcs.museum.domain.PresentationVisitor;
import atcs.museum.domain.Room;
import atcs.museum.domain.Visit;
import atcs.museum.domain.Visitor;

@Repository
public interface RoomRepository extends CrudRepository<Room,Long>{
	public Room findByName(String name);
}