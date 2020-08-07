package com.wj.blogs.dao;

import com.wj.blogs.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepostory extends JpaRepository<Type,Long> {

        Type findByName(String name);

        @Query("select t from t_type t")
        List<Type> findTop(Pageable pageable);
}
