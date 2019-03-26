package com.saaolheart.mumbai.configuration.repositoryconfig;


import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomRepository<T, ID extends Serializable>
extends CrudRepository<T, ID> {
    void refresh(T t);
}