package com.wj.blogs.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutShowCntroller {

    @GetMapping("/about")
    public String toAbout(){

        return "about";
    }

}
