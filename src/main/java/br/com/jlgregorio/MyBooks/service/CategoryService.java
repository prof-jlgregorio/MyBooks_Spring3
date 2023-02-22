package br.com.jlgregorio.MyBooks.service;

import br.com.jlgregorio.MyBooks.DTO.CategoryDTO;
import br.com.jlgregorio.MyBooks.mapper.CustomModelMapper;
import br.com.jlgregorio.MyBooks.model.CategoryModel;
import br.com.jlgregorio.MyBooks.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public CategoryDTO findById(int id){
        var found = repository.findById(id);
        return found.map(categoryModel -> CustomModelMapper.parseObject(categoryModel, CategoryDTO.class)).orElse(null);
    }

    public CategoryDTO create(CategoryDTO categoryDTO){
        var entity = CustomModelMapper.parseObject(categoryDTO, CategoryModel.class);
        return CustomModelMapper.parseObject(repository.save(entity), CategoryDTO.class);
    }

    public CategoryDTO update(CategoryDTO categoryDTO){
        var found = repository.findById(categoryDTO.getId());
        if(found.isPresent()){
            var entity = new CategoryModel();
            entity.setId(categoryDTO.getId());
            entity.setName(categoryDTO.getName());
            entity.setDescription(categoryDTO.getDescription());
            return CustomModelMapper.parseObject(repository.save(entity), CategoryDTO.class);
        } else {
            return null;
        }
    }

    public void delete(int id){
        var found = repository.findById(id);
        repository.delete(found.get());
    }

    public Page<CategoryDTO> findAll(Pageable pageable){
        var categoriesPage = repository.findAll(pageable);
        return categoriesPage.map(category -> CustomModelMapper.parseObject(category, CategoryDTO.class));
    }

    public Page<CategoryDTO> findByName(String name, Pageable pageable){
        var categoriesPage = repository.findByNameContainsIgnoreCase(name, pageable);
        return categoriesPage.map(category -> CustomModelMapper.parseObject(category, CategoryDTO.class));
    }

}
