package ruproject.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ruproject.api.v1.model.LibroDTO;
import ruproject.domain.Libro;


@Mapper(componentModel = "spring")
public interface LibroMapper {

    LibroMapper INSTANCE = Mappers.getMapper(LibroMapper.class);

    LibroDTO libroToLibroDTO(Libro libro);

    Libro libroDTOTOLibro(LibroDTO libroDTO);



}
