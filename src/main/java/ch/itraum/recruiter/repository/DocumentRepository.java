package ch.itraum.recruiter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ch.itraum.recruiter.model.Document;

public interface DocumentRepository extends CrudRepository<Document, Integer> {
	List<Document> findByCandidate_Id(int id);
}
