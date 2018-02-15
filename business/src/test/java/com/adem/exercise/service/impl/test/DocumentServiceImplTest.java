package com.adem.exercise.service.impl.test;

import com.adem.exercise.persistence.repository.DocumentRepository;
import com.adem.exercise.service.impl.DocumentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DocumentServiceImplTest {
    private static String TOKEN_1 = "t1";
    private static String TOKEN_2 = "t2";
    private static String TOKEN_3 = "t3";
    private static String KEY_1 = "k1";
    private static String KEY_2 = "k2";
    private static String KEY_3 = "k3";

    @Spy
    private Map<String, Set<String>> dictionary;

    @InjectMocks
    private DocumentServiceImpl documentService;

    @Mock
    private DocumentRepository documentRepository ;

    private List<String> tokens;



    @Before
    public void setUp(){

        tokens = new ArrayList<>();
        dictionary = new HashMap<>();

        dictionary.put(TOKEN_1, new HashSet<>());
        dictionary.put(TOKEN_2, new HashSet<>());
        dictionary.put(TOKEN_3, new HashSet<>());
        dictionary.get(TOKEN_1).add(KEY_1);
        dictionary.get(TOKEN_1).add(KEY_2);
        dictionary.get(TOKEN_2).add(KEY_2);
        dictionary.get(TOKEN_1).add(KEY_3);
        dictionary.get(TOKEN_2).add(KEY_3);
        dictionary.get(TOKEN_3).add(KEY_3);
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testFindKeysByTokensAll(){

        tokens.add(TOKEN_1);
        tokens.add(TOKEN_2);
        tokens.add(TOKEN_3);

        Set<String> result = documentService.findKeysByTokens(tokens);
        Assert.assertTrue(result.contains(KEY_3));
        Assert.assertFalse(result.contains(KEY_2));
        Assert.assertFalse(result.contains(KEY_1));

    }

    @Test
    public void testFindKeysByTokensOnlyTwo(){

        tokens.add(TOKEN_1);
        tokens.add(TOKEN_2);

        Set<String> result = documentService.findKeysByTokens(tokens);
        Assert.assertTrue(result.contains(KEY_2));
        Assert.assertTrue(result.contains(KEY_3));
        Assert.assertFalse(result.contains(KEY_1));

    }

    @Test
    public void testFindKeysByTokensOnlyOne(){

        tokens.add(TOKEN_1);

        Set<String> result = documentService.findKeysByTokens(tokens);
        Assert.assertTrue(result.contains(KEY_1));
        Assert.assertTrue(result.contains(KEY_2));
        Assert.assertTrue(result.contains(KEY_3));

    }

    @Test
    public void testFindKeysByTokensEmpry(){
        documentService.findKeysByTokens(tokens);

    }
}
