package com.adem.exercise.service;


import com.adem.exercise.persistence.model.Document;

import java.util.List;
import java.util.Set;

public interface DocumentService extends GenericService<Document> {

    Document findByKey(String key);

    Set<String> findKeysByTokens(List<String> tokens);

    void store(Document document);
}
