package com.adem.exercise.service.impl;

import com.adem.exercise.persistence.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.adem.exercise.persistence.repository.DocumentRepository;
import com.adem.exercise.service.DocumentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    //split by whitespace
    private static final String REGEXP = "\\s+";
    private final DocumentRepository documentRepository;
    private Map<String, Set<String>> dictionary;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, Map<String, Set<String>> dictionary) {
        this.documentRepository = documentRepository;
        this.dictionary = dictionary;
    }


    @Override
    public List<Document> listAll() {
        List<Document> documents = new ArrayList<>();
        documentRepository.findAll().forEach(documents::add);
        return documents;
    }

    @Override
    public Document getById(Long id) {
        return documentRepository.findOne(id);
    }

    @Override
    public Document saveOrUpdate(Document domainObject) {
        Document document = documentRepository.findByKey(domainObject.getKey());
        if (document != null) {
            domainObject.setDocId(document.getDocId());
        }
        return documentRepository.save(domainObject);
    }

    @Override
    public void delete(Long id) {
        if (documentRepository.findOne(id) != null) {
            documentRepository.delete(id);
        }
    }

    @Override
    public Document findByKey(String key) {
        return documentRepository.findByKey(key);
    }

    /**
     * @param tokens list of tokens
     * @return keys of documents that contains specified tokens
     */
    @Override
    public Set<String> findKeysByTokens(List<String> tokens) {
        if (!tokens.isEmpty()) {
            List<String> matches = new ArrayList<>();
            tokens.forEach(token -> {
                if (dictionary.containsKey(token)) {
                    matches.addAll(dictionary.get(token));
                }
            });
            Map<String, Long> counters = matches.stream()
                    .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
            Map<String, Long> result = counters
                    .entrySet()
                    .stream()
                    .filter(p -> p.getValue() == (tokens.size()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return result.keySet();
        }
        return new HashSet<>();
    }

    @Override
    public void store(Document document) {
        saveOrUpdate(document);
        Arrays.stream(document.getToken().split(REGEXP)).forEach(token -> {
            if (dictionary.containsKey(token)) {
                dictionary.get(token).add(document.getKey());
            } else {
                dictionary.put(token, new HashSet<>());
                dictionary.get(token).add(document.getKey());
            }
        });
    }
}
