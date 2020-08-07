package com.wj.blogs.web;

import com.wj.blogs.po.Blog;
import com.wj.blogs.service.BlogService;
import com.wj.blogs.service.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        Map<String, List<Blog>> stringListMap = blogService.archiveBlog();
        System.out.println(stringListMap);
        model.addAttribute("archiveMap",stringListMap);
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }

}
