package br.com.jlgregorio.MyBooks.repository;

import br.com.jlgregorio.MyBooks.model.AuthorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorModel, Integer> {

    Optional<AuthorModel> findById(int id);

    public Page<AuthorModel> findAll(Pageable pageable);

    public Page<AuthorModel> findByNameContainsIgnoreCase(String name, Pageable pageable);

}
