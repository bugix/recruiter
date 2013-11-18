package ch.itraum.recruiter.repository;

import org.springframework.data.repository.CrudRepository;

import ch.itraum.recruiter.model.Document;

public interface DocumentRepository extends CrudRepository<Document, Integer> {

}
