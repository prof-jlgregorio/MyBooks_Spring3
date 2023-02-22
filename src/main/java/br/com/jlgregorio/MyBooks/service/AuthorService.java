package br.com.jlgregorio.MyBooks.service;

import br.com.jlgregorio.MyBooks.DTO.AuthorDTO;
import br.com.jlgregorio.MyBooks.mapper.CustomModelMapper;
import br.com.jlgregorio.MyBooks.model.AuthorModel;
import br.com.jlgregorio.MyBooks.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository repository;

    public AuthorDTO findById(int id){
       var entity = repository.findById(id);
        return entity.map(authorModel -> CustomModelMapper.parseObject(authorModel, AuthorDTO.class)).orElse(null);
    }

    public AuthorDTO create(AuthorDTO authorDTO){
        var entity = CustomModelMapper.parseObject(authorDTO, AuthorModel.class);
        var model = repository.save(entity);
        return CustomModelMapper.parseObject(model, AuthorDTO.class);
    }

    public AuthorDTO update(AuthorDTO authorDTO){
        var found = repository.findById(authorDTO.getId());
        if(found.isPresent()){
            AuthorModel entity = new AuthorModel();
            entity.setId(authorDTO.getId());
            entity.setName(authorDTO.getName());
            entity.setCountry(authorDTO.getCountry());
            entity.setGender(authorDTO.getGender());
            return CustomModelMapper.parseObject(repository.save(entity), AuthorDTO.class);
        } else {
            return null;
        }
    }

    public void delete(int id){
        var found = repository.findById(id);
        repository.delete(found.get());
    }

    public Page<AuthorDTO> findAll(Pageable pageable){
        var authorsPage = repository.findAll(pageable);
        return authorsPage.map(author -> CustomModelMapper.parseObject(author, AuthorDTO.class));
    }

    public Page<AuthorDTO> findByName(String name, Pageable pageable){
        var authorsPage = repository.findByNameContainsIgnoreCase(name, pageable);
        return authorsPage.map(author -> CustomModelMapper.parseObject(author, AuthorDTO.class));
    }

}
