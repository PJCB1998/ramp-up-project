package ruproject.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ruproject.api.v1.model.ContenidoDTO;
import ruproject.domain.Contenido;

@Mapper
public interface ContenidoMapper {
    ContenidoMapper INSTANCE = Mappers.getMapper(ContenidoMapper.class);

    @Mapping(source = "id", target = "id")
    ContenidoDTO contenidoToContendidoDTO(Contenido contenido);
}
