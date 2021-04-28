package atcs.museum.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import atcs.museum.domain.GroupVisit;
import atcs.museum.domain.PointOfInterest;

@Repository
public interface GroupRepository extends CrudRepository<GroupVisit,Long>{
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE GroupVisit g SET g.size = :size WHERE g.id = :group_id")
	public void updateSize(@Param("group_id") Long group_id, @Param("size") int size);
}
