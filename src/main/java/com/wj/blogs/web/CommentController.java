package com.wj.blogs.web;

import com.wj.blogs.po.Comment;
import com.wj.blogs.service.BlogService;
import com.wj.blogs.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
   private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    //只返回一个片段，局部刷新
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Long blogId, Model model){
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        System.out.println(comments);
        model.addAttribute("comments",comments);
        return "blog :: commentList";
    }

    //提交表单信息
    @PostMapping("/comments")
    public String post(Comment comment){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        comment.setAvatar(avatar);
        commentService.saveComment(comment);

        System.out.println(comment);
        return "redirect:/comments/"+blogId;
    }

}
