package ruproject.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ruproject.api.v1.model.CarreraDTO;
import ruproject.domain.Carrera;

@Mapper(componentModel = "spring")
public interface CarreraMapper {
    CarreraMapper INSTANCE = Mappers.getMapper(CarreraMapper.class);

    CarreraDTO carreraToCarreaDTO(Carrera carrear);

    Carrera carreraDTOtoCarrera(CarreraDTO carreraDTO);

}
