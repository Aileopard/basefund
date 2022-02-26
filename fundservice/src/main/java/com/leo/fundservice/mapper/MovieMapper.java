package com.leo.fundservice.mapper;


import com.leo.fundservice.model.Movie;

public interface MovieMapper {
    int insert(Movie record);

    int insertSelective(Movie record);
}