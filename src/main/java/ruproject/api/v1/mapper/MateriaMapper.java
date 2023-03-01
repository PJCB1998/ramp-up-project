package ruproject.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ruproject.api.v1.model.MateriaDTO;
import ruproject.domain.Materia;

@Mapper
public interface MateriaMapper {

    MateriaMapper INSTANCE = Mappers.getMapper(MateriaMapper.class);

    @Mapping(source = "id",target = "id")
    MateriaDTO materiaToMateriaDTO(Materia materia);

}
