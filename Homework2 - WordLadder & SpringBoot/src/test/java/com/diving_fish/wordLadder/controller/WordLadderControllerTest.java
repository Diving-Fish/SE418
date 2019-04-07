package com.diving_fish.wordLadder.controller;

import com.diving_fish.wordLadder.WordLadderApplication;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * WorldLadderController Tester.
 *
 * @author <Authors name>
 * @since <pre>Mar, 7, 2019</pre>
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= WordLadderApplication.class)
public class WordLadderControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;
    @Before
    public void before() throws Exception {
        // mocMVC init
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetWordLadder() throws Exception {
        MultiValueMap m = new LinkedMultiValueMap<String, String>();
        m.add("start", "code");
        m.add("end", "data");
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/generate").params(m)
        ).andReturn();
        JSONArray wordladder = JSONArray.fromObject(result.getResponse().getContentAsString());
        Assert.assertEquals("error occurred when generating wordLadder", 5, wordladder.size());
    }

    @Test
    public void testSearch() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/in_dict")
                        .param("word", "code")
        ).andReturn();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("data should be in dictionary", JSONObject.fromObject(result.getResponse().getContentAsString()).get("has"), true);
    }


}

