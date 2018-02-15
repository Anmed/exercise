package com.adem.exercise.search.web;

import com.adem.exercise.persistence.model.Document;
import com.adem.exercise.service.DocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/document")
@Api(value = "document storage", description = "store documents, find by key or value, documents with same keys are not allowed to store")
public class DocumentController {
    private final DocumentService documentService;


    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @ApiOperation(value = "Add a document, tokens list should be separated by WHITESPACE")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity saveProduct(@RequestBody Document document) {
        documentService.store(document);
        return new ResponseEntity("document saved successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a document")
    @GetMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        documentService.delete(id);
        return new ResponseEntity("Document deleted successfully", HttpStatus.OK);

    }

    @ApiOperation(value = "View a list of available products", response = Iterable.class)
    @GetMapping(value = "/list")
    public Iterable<Document> list() {
        return documentService.listAll();
    }

    @ApiOperation(value = "Find a list of keys from documents that contains specified tokens", response = Iterable.class)
    @GetMapping(value = "/search")
    public Set<String> findDocumentsByToken(@RequestParam(value = "token") List<String> tokens) {
        return documentService.findKeysByTokens(tokens);
    }

    @ApiOperation(value = "Search a document by key ", response = Document.class)
    @GetMapping(value = "/find/{key}")
    public Document findDocument(@PathVariable String key, Model model) {
        return documentService.findByKey(key);
    }

}
