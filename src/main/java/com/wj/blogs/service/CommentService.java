package com.wj.blogs.service;

import com.wj.blogs.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long id);

    Comment saveComment(Comment comment);

}
