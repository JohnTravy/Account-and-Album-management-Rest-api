package com.travy.SpringRestAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travy.SpringRestAPI.Model.Photo;
import java.util.List;


@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long>  {

    List<Photo> findByAlbum_id(Long id);
    
}
