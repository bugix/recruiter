package ch.itraum.recruiter.repository;

import org.springframework.data.repository.CrudRepository;

import ch.itraum.recruiter.model.Candidate;

public interface CandidateRepository extends CrudRepository<Candidate, Integer> {

}
