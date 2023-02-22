package br.com.jlgregorio.MyBooks.controller;

import br.com.jlgregorio.MyBooks.DTO.CategoryDTO;
import br.com.jlgregorio.MyBooks.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories/v1")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/{id}")
    public CategoryDTO findById(@PathVariable("id") int id){
        var categoryDTO = service.findById(id);
        if(categoryDTO != null){
            buildEntityLink(categoryDTO);
            return categoryDTO;
        } else {
            return null;
        }
    }

    @PostMapping
    public CategoryDTO create(@RequestBody CategoryDTO categoryDTO){
        var category = service.create(categoryDTO);
        buildEntityLink(category);
        return category;
    }

    @PutMapping
    public CategoryDTO update(@RequestBody CategoryDTO categoryDTO){
        var category = service.update(categoryDTO);
        buildEntityLink(category);
        return category;
    }

    @GetMapping
    public ResponseEntity<PagedModel<CategoryDTO>> findAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            PagedResourcesAssembler assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
        Page<CategoryDTO> categoriesDTOPage = service.findAll(pageable);
        categoriesDTOPage.forEach(this::buildEntityLink);
        return new ResponseEntity(assembler.toModel(categoriesDTOPage), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<CategoryDTO>> findByName(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            PagedResourcesAssembler assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
        Page<CategoryDTO> categoriesDTOPage = service.findByName(name, pageable);
        categoriesDTOPage.forEach(this::buildEntityLink);
        return new ResponseEntity(assembler.toModel(categoriesDTOPage), HttpStatus.OK);
    }

    private void buildEntityLink(CategoryDTO category){
        category.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()
                ).findById(category.getId())).withSelfRel()
        );
    }

}
