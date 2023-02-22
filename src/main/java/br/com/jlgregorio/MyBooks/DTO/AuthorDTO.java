package br.com.jlgregorio.MyBooks.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO extends RepresentationModel<AuthorDTO> {

    @Getter
    @Setter
    private int id;

    @Getter @Setter
    private String name;
    @Getter @Setter
    private String country;

}
