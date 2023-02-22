package br.com.jlgregorio.MyBooks.controller;

import br.com.jlgregorio.MyBooks.DTO.AuthorDTO;
import br.com.jlgregorio.MyBooks.service.AuthorService;
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
@RequestMapping("/authors/v1")
public class AuthorController {

    @Autowired
    AuthorService service;

    @GetMapping
    public ResponseEntity<PagedModel<AuthorDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<AuthorDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        Page<AuthorDTO> authorsDTOPage = service.findAll(pageable);

//       for (AuthorDTO authorDTO : authorsDTOPage){
//           buildEntityLink(authorDTO);
//       }
        authorsDTOPage.forEach(this::buildEntityLink);

       return new ResponseEntity(assembler.toModel(authorsDTOPage), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<AuthorDTO>> findByName(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<AuthorDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        Page<AuthorDTO> authorsDTOPage = service.findByName(name, pageable);

//        for (AuthorDTO authorDTO : authorsDTOPage){
//            buildEntityLink(authorDTO);
//        }

        authorsDTOPage.forEach(this::buildEntityLink);

        return new ResponseEntity(assembler.toModel(authorsDTOPage), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public AuthorDTO findById(@PathVariable("id") int id){
        var authorDTO = service.findById(id);
        if (authorDTO != null){
            buildEntityLink(authorDTO);
            return authorDTO;
        } else {
            return null;
        }
    }

    @PostMapping
    public AuthorDTO create(@RequestBody AuthorDTO authorDTO){
        var author = service.create(authorDTO);
        buildEntityLink(author);
        return author;
    }

    @PutMapping
    public AuthorDTO update(@RequestBody AuthorDTO authorDTO){
        var author = service.update(authorDTO);
        buildEntityLink(author);
        return author;
    }

    private void buildEntityLink(AuthorDTO author){
        author.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()
                ).findById(author.getId())).withSelfRel()
        );
    }




}
