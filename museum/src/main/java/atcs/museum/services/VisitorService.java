package atcs.museum.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import atcs.museum.domain.Visitor;
import atcs.museum.repository.VisitorRepository;

@Service
public class VisitorService {

	
	@Autowired
	private VisitorRepository visitorRepository;
	
	
	@Transactional
	public Visitor getById(Long id) {
		return this.visitorRepository.findById(id).get();
		
	}
	@Transactional
	public List<Visitor> getAllVisitor(){
		return 	(List<Visitor>) visitorRepository.findAll();
}
}
