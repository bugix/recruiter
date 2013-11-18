package ch.itraum.recruiter.repository;

import org.springframework.data.repository.CrudRepository;

import ch.itraum.recruiter.model.Job;

public interface JobRepository extends CrudRepository<Job, Integer> {

}
