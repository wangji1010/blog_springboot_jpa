package com.wj.blogs.web;

import com.wj.blogs.po.Type;
import com.wj.blogs.service.BlogService;
import com.wj.blogs.service.TypeService;
import com.wj.blogs.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @RequestMapping("/")
    public String toIndex(@PageableDefault(size = 8
            ,sort = {"updateTime"}
            ,direction = Sort.Direction.DESC) Pageable pageable
            , Model model){

        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        System.out.println("--------index--------");
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8
            ,sort = {"updateTime"}
            ,direction = Sort.Direction.DESC) Pageable pageable
            , Model model,@RequestParam String query){

        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @RequestMapping("/blog/{id}")
    public String blogDetail(@PathVariable("id") Long id, Model model){

        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    @RequestMapping("/blog")
    public String toBlog(){

        System.out.println("--------index--------");
        return "blog";
    }

}
