package com.diving_fish.wordladder.controller;
import com.diving_fish.wordladder.wordladder.WordLadder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;


@RestController
@EnableConfigurationProperties
public class WordLadderController {

    @Value("${login.ip}")
    String path;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JSONObject login(HttpServletRequest request) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + ", " + password);
        PostMethod postMethod = new PostMethod(path + "/");
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        NameValuePair[] data = {
                new NameValuePair("username", username),
                new NameValuePair("password", password)
        };
        postMethod.setRequestBody(data);
        new HttpClient().executeMethod(postMethod);
        JSONObject response = JSONObject.fromObject(postMethod.getResponseBodyAsString());
        request.getSession().setAttribute("userinfo", response);
        return response;
    }

    @RequestMapping(value = "/logged", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject logged(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        response.put("status", 200);
        JSONObject jsonObject = (JSONObject) request.getSession().getAttribute("userinfo");
        if (jsonObject == null || jsonObject.getInt("status") != 200) {
            response.put("logged", false);
        }
        else {
            response.put("logged", true);
            response.put("username", jsonObject.getString("username"));
        }
        return response;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject logout(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        if (logged(request).getBoolean("logged")) {
            response.put("status", 200);
            response.put("message", "logout successfully");
            request.getSession().removeAttribute("userinfo");
        } else {
            response.put("status", 404);
        }
        return response;
    }

    @RequestMapping(value = "/generate", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject generate(@RequestParam("start") String start, @RequestParam("end") String end, HttpServletRequest request) throws IOException
    {
        JSONObject response = new JSONObject();
        if (!logged(request).getBoolean("logged")) {
            response.put("status", 401);
            response.put("message", "You need login to do this");
            return response;
        }
        ClassPathResource dict = new ClassPathResource("static/dictionary.txt");
        WordLadder wl = new WordLadder(dict.getFile().getAbsolutePath());
        ArrayList<String> list = wl.generate(start, end);
        System.out.println(list);
        JSONArray result = JSONArray.fromObject(list);
        response.put("status", 200);
        response.put("wordladder", result);
        return response;
    }

    @RequestMapping(value = "/in_dict", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject in_dict(@RequestParam("word") String word, HttpServletRequest request) throws IOException
    {
        JSONObject response = new JSONObject();
        if (!logged(request).getBoolean("logged")) {
            response.put("status", 401);
            response.put("message", "You need login to do this");
            return response;
        }
        ClassPathResource dict = new ClassPathResource("static/dictionary.txt");
        WordLadder wl = new WordLadder(dict.getFile().getAbsolutePath());
        response.put("has", wl.in_dict(word));
        return response;
    }
}
