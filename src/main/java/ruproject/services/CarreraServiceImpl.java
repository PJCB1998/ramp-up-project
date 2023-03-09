package ruproject.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ruproject.api.v1.mapper.CarreraMapper;
import ruproject.api.v1.model.CarreraDTO;
import ruproject.domain.Carrera;
import ruproject.repositories.CarreraRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreraServiceImpl implements CarreraService {

    private final CarreraMapper carreraMapper;
    private final CarreraRepository carreraRepository;

    public CarreraServiceImpl(CarreraMapper carreraMapper, CarreraRepository carreraRepository) {
        this.carreraMapper = carreraMapper;
        this.carreraRepository = carreraRepository;
    }

    @Override
    public List<CarreraDTO> getAllCarreras() {
        return carreraRepository
                .findAll()
                .stream()
                .map(carreraMapper::carreraToCarreaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CarreraDTO getCarreraByName(String name) {
        return carreraMapper.carreraToCarreaDTO(carreraRepository.findByName(name));
    }

    @Override
    public CarreraDTO saveCarrera(CarreraDTO carreraDTO) {
        Carrera carrera = carreraMapper.carreraDTOtoCarrera(carreraDTO);
        return carreraMapper.carreraToCarreaDTO(carreraRepository.save(carrera));
    }

    @Override
    public CarreraDTO updateCarrera(String name, CarreraDTO carreraDTO) {
        if((existsByName(name))){
        Carrera carrera = carreraMapper.carreraDTOtoCarrera(carreraDTO);
        carrera.setName(name);
        return carreraMapper.carreraToCarreaDTO(carreraRepository.save(carrera));
        }
        throw new IllegalArgumentException("Carrera with name:"+ name + " not found");
    }

    @Override
    public Boolean existsByName(String name) {
        return carreraRepository.existsByName(name);
    }

    @Override
    @Transactional
    public void deleteCarrera(String name) {
        carreraRepository.deleteByName(name);
    }


}
