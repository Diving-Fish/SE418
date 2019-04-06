package com.diving_fish.wordLadder.controller;
import com.diving_fish.wordLadder.wordladder.WordLadder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


@RestController
public class WordLadderController {

    @RequestMapping(value = "/generate", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONArray generate(@RequestParam("start") String start, @RequestParam("end") String end) throws IOException
    {
        ClassPathResource dict = new ClassPathResource("dictionary.txt");
        WordLadder wl = new WordLadder(dict.getFile().getAbsolutePath());
        ArrayList<String> list = wl.generate(start, end);
        System.out.println(list);
        JSONArray result = JSONArray.fromObject(list);
        return result;
    }

    @RequestMapping(value = "/in_dict", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject in_dict(@RequestParam("word") String word) throws IOException
    {
        ClassPathResource dict = new ClassPathResource("dictionary.txt");
        WordLadder wl = new WordLadder(dict.getFile().getAbsolutePath());
        JSONObject result = new JSONObject();
        result.put("has", wl.in_dict(word));
        return result;
    }
}
