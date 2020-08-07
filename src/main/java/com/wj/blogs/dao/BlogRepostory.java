package com.wj.blogs.dao;

import com.wj.blogs.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepostory extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from t_blog b where b.recomend=true ")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from t_blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Query("select function('date_format',b.updateTime,'%Y')as year from t_blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select b from t_blog b where function('date_format',b.updateTime,'%Y')=?1")
    List<Blog> findByYear(String year);
}
