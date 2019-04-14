package com.diving_fish.wordLadder.controller;
import com.diving_fish.wordLadder.WordLadderApplication;
import com.diving_fish.wordLadder.controller.WordLadderController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= WordLadderApplication.class)
@ContextConfiguration(classes = WordLadderController.class)
public class ActuatorTest {

    protected MockMvc mockMvc;
    private MockHttpSession session;
    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    protected WebApplicationContext wac;
    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).addFilters(this.springSecurityFilterChain).build();
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/login").content("username=admin&password=admin")
                        .header("Content-Type", "application/x-www-form-urlencoded")
        ).andExpect(status().isFound()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        session = (MockHttpSession)result.getRequest().getSession();
    }

    @After
    public void after() throws Exception {

    }

    @Test
    public void testHealth() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/actuator/health").session(session)
        ).andExpect(status().isOk()).andReturn();
        JSONObject response = JSONObject.fromObject(result.getResponse().getContentAsString());
        String status = (String)response.get("status");
        Assert.assertEquals("status: up => health", status, "UP");
    }


    @Test
    public void testInfo() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/actuator/info").session(session)
        ).andExpect(status().isOk()).andReturn();
        JSONObject response = JSONObject.fromObject(result.getResponse().getContentAsString());
        JSONObject app = (JSONObject) response.get("app");
        String name = (String)app.get("name");
        String encoding = (String)app.get("encoding");
        Assert.assertEquals("check application name", name, "WordLadder");
        Assert.assertEquals("check application encoding", encoding, "UTF-8");
    }


    @Test
    public void testEnvironment() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/actuator/env").session(session)
        ).andExpect(status().isOk()).andReturn();
        JSONObject response = JSONObject.fromObject(result.getResponse().getContentAsString());
        JSONArray environment = (JSONArray) response.get("propertySources");
        JSONObject info = (JSONObject)environment.get(3);
        JSONObject properties = (JSONObject) info.get("properties");
        JSONObject name = (JSONObject)properties.get("info.app.name");
        String value = name.getString("value");
        Assert.assertEquals("check application information", value, "WordLadder");
    }
}
