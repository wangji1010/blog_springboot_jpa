package com.wj.blogs.service;

import com.wj.blogs.dao.CommentRepostory;
import com.wj.blogs.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepostory commentRepostory;

    @Override
    public List<Comment> listCommentByBlogId(Long id) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");//排序
        return commentRepostory.findByBlogId(id,sort);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId!=-1){
            comment.setParentComment(commentRepostory.getOne(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepostory.save(comment);
    }
}
