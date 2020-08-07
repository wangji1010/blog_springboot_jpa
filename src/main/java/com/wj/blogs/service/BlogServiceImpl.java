package com.wj.blogs.service;

import com.wj.blogs.dao.BlogRepostory;
import com.wj.blogs.handler.NotFoundException;
import com.wj.blogs.po.Blog;
import com.wj.blogs.po.Type;
import com.wj.blogs.util.MarkdownUtils;
import com.wj.blogs.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
  private   BlogRepostory blogRepostory;

    @Override
    @Cacheable(value = "blog",key = "#id")
    public Blog getBlog(Long id) {
        System.out.println("查询博客："+id);
        return blogRepostory.getOne(id);
    }

    @Override

    public Blog getAndConvert(Long id) {
        Blog blog = blogRepostory.getOne(id);
        if (blog==null){
            throw new  NotFoundException("博客不存在");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();

        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        return blogRepostory.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();
                //处理标题模糊查询
                if (!"".equals(blog.getTitle())&&blog.getTitle()!=null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                //处理分类模糊查询
                if (blog.getTypeId()!=null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                //处理是否勾选推荐
                if (blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recomend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {

        return blogRepostory.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepostory.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = new PageRequest(0,size,sort);
        return blogRepostory.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {

        List<String> years = blogRepostory.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year : years){
            map.put(year,blogRepostory.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepostory.count();
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {

        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());

        blog.setViews(0);//初始浏览次数
        return blogRepostory.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepostory.getOne(id);
        if (b!=null){
            throw new NotFoundException("该博客不存在！");
        }else {
            BeanUtils.copyProperties(b,blog);
        }
        return blogRepostory.save(b);
    }

    @Override
    public void deleteBlog(Long id) {

        blogRepostory.deleteById(id);
    }
}
