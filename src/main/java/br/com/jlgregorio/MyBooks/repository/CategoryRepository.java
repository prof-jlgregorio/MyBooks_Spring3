package br.com.jlgregorio.MyBooks.repository;

import br.com.jlgregorio.MyBooks.model.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Integer> {

    Optional<CategoryModel> findById(int id);

    Page<CategoryModel> findAll(Pageable pageable);

    Page<CategoryModel> findByNameContainsIgnoreCase(String name, Pageable pageable);

}
