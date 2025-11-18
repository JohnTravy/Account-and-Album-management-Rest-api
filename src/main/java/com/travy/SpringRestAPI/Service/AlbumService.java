package com.travy.SpringRestAPI.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travy.SpringRestAPI.Model.Album;
import com.travy.SpringRestAPI.Repository.AlbumRepository;

@Service
public class AlbumService {
    
    @Autowired
    private AlbumRepository albumRepository;

    public Album save(Album album){

        return albumRepository.save(album);
        
    }

    public List <Album> findByAccount_Id(Long id){
      
        return albumRepository.findByAccount_id(id);


    }

    public Optional<Album> findById(Long id){

        return albumRepository.findById(id);
        
    }

    public void deleteAlbum(Album album){

         albumRepository.delete(album);

    }


}
