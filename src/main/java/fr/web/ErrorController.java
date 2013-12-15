package fr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ErrorController {

    @RequestMapping("/404")
    public String pageNotFound() {
        return "404";
    }

    @RequestMapping("/500")
    public String internatServerError() {
        return "500";
    }
}
