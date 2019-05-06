package com.diving_fish.login.controller;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class Controller {
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject userinfo(Principal principal) {
        JSONObject response = new JSONObject();
        if (principal == null) {
            response.put("status", 401);
        } else {
            response.put("status", 200);
            response.put("username", principal.getName());
            response.put("auth", true);
        }
        return response;
    }
}
