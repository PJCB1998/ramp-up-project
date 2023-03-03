package ruproject.api.v1.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;
import ruproject.api.v1.model.MateriaDTO;
import ruproject.domain.Materia;

@Mapper(componentModel = "spring")
public interface MateriaMapper {

    MateriaMapper INSTANCE = Mappers.getMapper(MateriaMapper.class);

    MateriaDTO materiaToMateriaDTO(Materia materia);

}
