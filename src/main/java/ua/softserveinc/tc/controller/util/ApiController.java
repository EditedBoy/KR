package ua.softserveinc.tc.controller.util;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.constants.ApiConstants;
import ua.softserveinc.tc.constants.LocaleConstants;
import ua.softserveinc.tc.dto.ChildDto;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.ApplicationConfigurator;
import ua.softserveinc.tc.util.Log;

import java.util.*;

/**
 * Created by edward on 5/17/16.
 */
@Controller
public class ApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChildService childService;

    @Autowired
    private ApplicationConfigurator configurator;

    @Autowired
    private MessageSource messageSource;

    private static @Log Logger LOG;

    @RequestMapping(value = ApiConstants.USER_REST_URL, method = RequestMethod.GET)
    @ResponseBody
    public String getUser() {
        List<UserDto> result = new ArrayList<>();
        List<User> users = userService.findAll();

        for (User user : users) {
            result.add(new UserDto(user));
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }

    @RequestMapping(value = ApiConstants.USER_REST_BY_ID_URL, method = RequestMethod.GET)
    @ResponseBody
    public String getUserById(@PathVariable long id) {
        User user = userService.findById(id);
        Gson gson = new Gson();

        return gson.toJson(new UserDto(user));
    }

    @RequestMapping(value = ApiConstants.CHILD_REST_URL, method = RequestMethod.GET)
    @ResponseBody
    public String getChild() {
        List<ChildDto> result = new ArrayList<>();
        List<Child> children = childService.findAll();

        for (Child child : children) {
            result.add(new ChildDto(child));
        }

        Gson gson = new Gson();
        LOG.info("Get children");
        return gson.toJson(result);
    }

    @RequestMapping(value = ApiConstants.CHILD_REST_URL, method = RequestMethod.POST)
    @ResponseBody
    public String addChild(@RequestBody Child child) {
        childService.create(child);
        Gson gson = new Gson();
        return gson.toJson(child);
    }

    @RequestMapping(value = ApiConstants.CHILD_BY_ID_REST_URL, method = RequestMethod.GET)
    @ResponseBody
    public String getChildById(@PathVariable long id) {
        Child child = childService.findById(id);
        Gson gson = new Gson();

        return gson.toJson(new ChildDto(child));
    }

    @RequestMapping(value = ApiConstants.GET_CHILD_PARENT_REST_URL, method = RequestMethod.GET)
    @ResponseBody
    public String getParentByChild(@PathVariable long id) {
        Child child = childService.findById(id);
        User user = child.getParentId();
        Gson gson = new Gson();

        return gson.toJson(new UserDto(user));
    }

    @RequestMapping(value = ApiConstants.GET_APP_CONFIGURATION, method = RequestMethod.GET)
    @ResponseBody
    public String getAppConfiguration() {
        Gson gson = new Gson();
        return gson.toJson(configurator);
    }

    @RequestMapping(value = ApiConstants.GET_APP_LOCALIZATION, method = RequestMethod.GET)
    @ResponseBody
    public String getLocale(@RequestParam("locale") String locale) {
        Map<String, String> messages = new HashMap<>();
        Locale localeObj = new Locale(locale);
        for (String message : LocaleConstants.getMessages()) {
            messages.put(message, messageSource.getMessage(message, null, localeObj));
        }
        Gson gson = new Gson();
        return gson.toJson(messages);
    }

}
