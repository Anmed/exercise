package com.adem.exercise.persistence.repository;


import com.adem.exercise.persistence.model.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {

    Document findByKey(String key);

}
