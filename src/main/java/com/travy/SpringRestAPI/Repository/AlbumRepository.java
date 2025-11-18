package com.travy.SpringRestAPI.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travy.SpringRestAPI.Model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List <Album> findByAccount_id(Long id);
    
}
