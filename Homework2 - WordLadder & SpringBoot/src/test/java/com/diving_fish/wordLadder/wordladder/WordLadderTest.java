package com.diving_fish.wordLadder.wordladder;
import com.diving_fish.wordLadder.wordladder.WordLadder;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.core.io.ClassPathResource;

public class WordLadderTest {

    private WordLadder wl;
    @Before
    public void before() throws Exception {
        ClassPathResource dict = new ClassPathResource("dictionary.txt");
        wl = new WordLadder(dict.getFile().getAbsolutePath());
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: find(String word)
     *
     */
    @Test
    public void test_in_dict() throws Exception {
        Boolean answer_false = wl.in_dict("cacaca");
        Boolean answer_true = wl.in_dict("data");
        Assert.assertEquals("The word cacaca should not be founded.",answer_false, false);
        Assert.assertEquals("The word data should be founded.",answer_true, true);
//TODO: Test goes here...
    }

    /**
     *
     * Method: BFS(String start_word, String end_word)
     *
     */
    @Test
    public void test_generate() throws Exception {
        Assert.assertEquals("data -> code should return array(5).",wl.generate("code", "data").size(), 5);
        Assert.assertEquals("data -> dom should return array(0).",wl.generate("data", "dom").size(), 0);
    }
}
