package com.wj.blogs.web.admin;

import com.wj.blogs.po.Blog;
import com.wj.blogs.po.User;
import com.wj.blogs.service.BlogService;
import com.wj.blogs.service.TypeService;
import com.wj.blogs.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8
            ,sort = {"updateTime"}
            ,direction = Sort.Direction.DESC) Pageable pageable
            , BlogQuery blog
            , Model model){

        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8
            ,sort = {"updateTime"}
            ,direction = Sort.Direction.DESC) Pageable pageable
            , BlogQuery blog
            , Model model){

        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("blog",new Blog());
        return "admin/blogs_input";
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable("id")Long id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("blog",blogService.getBlog(id));
        return "admin/blogs_input";
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        Blog b = blogService.saveBlog(blog);
        if (b==null){
            //失败
            attributes.addFlashAttribute("message","添加失败！");
        }else {
            //成功
            attributes.addFlashAttribute("message","添加成功！");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete( @PathVariable("id") Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/blogs";
    }
}
