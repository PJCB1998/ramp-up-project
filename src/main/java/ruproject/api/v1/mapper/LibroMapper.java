package ruproject.api.v1.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ruproject.api.v1.model.LibroDTO;
import ruproject.domain.Libro;


@Mapper(componentModel = "spring")
public interface LibroMapper {

    LibroMapper INSTANCE = Mappers.getMapper(LibroMapper.class);

    LibroDTO libroToLibroDTO(Libro libro, @Context CycleAvoidingMappingContext context);

    Libro libroDTOTOLibro(LibroDTO libroDTO, @Context CycleAvoidingMappingContext context);



}
