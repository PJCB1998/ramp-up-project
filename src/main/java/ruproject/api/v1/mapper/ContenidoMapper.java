package ruproject.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ruproject.api.v1.model.ContenidoDTO;
import ruproject.domain.Contenido;

@Mapper(componentModel = "spring")
public interface ContenidoMapper {
    ContenidoMapper INSTANCE = Mappers.getMapper(ContenidoMapper.class);

    ContenidoDTO contenidoToContendidoDTO(Contenido contenido);

    Contenido contenidoDTOToContenido(ContenidoDTO contenidoDTO);


}
