package ruproject.api.v1.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ruproject.api.v1.model.ContenidoDTO;
import ruproject.domain.Contenido;

@Mapper(componentModel = "spring")
public interface ContenidoMapper {
    ContenidoMapper INSTANCE = Mappers.getMapper(ContenidoMapper.class);

    ContenidoDTO contenidoToContendidoDTO(Contenido contenido, @Context CycleAvoidingMappingContext context);

    Contenido contenidoDTOToContenido(ContenidoDTO contenidoDTO, @Context CycleAvoidingMappingContext context);


}
