package ruproject.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ruproject.api.v1.model.CarreraDTO;
import ruproject.domain.Carrera;

@Mapper
public interface CarreraMapper {

    CarreraMapper INSTANCE = Mappers.getMapper(CarreraMapper.class);
     @Mapping(source = "id",target = "id")
     CarreraDTO carreraToCarreaDTO(Carrera carrear);

}
