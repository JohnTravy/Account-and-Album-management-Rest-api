package com.travy.SpringRestAPI.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travy.SpringRestAPI.Model.Photo;
import com.travy.SpringRestAPI.Repository.PhotoRepository;

@Service
public class PhotoService {
    
@Autowired    
private PhotoRepository photoRepository;


public Photo save (Photo photo){

   return photoRepository.save(photo);

}

public Optional<Photo> findById(Long id){

   return photoRepository.findById(id);

}

public List <Photo> findByAlbum_id(Long id){

   return photoRepository.findByAlbum_id(id);
   
}

public void delete (Photo photo){

   photoRepository.delete(photo);

}




}
