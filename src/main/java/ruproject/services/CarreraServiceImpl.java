package ruproject.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ruproject.api.v1.mapper.CarreraMapper;
import ruproject.api.v1.model.CarreraDTO;
import ruproject.domain.Carrera;
import ruproject.repositories.CarreraRepository;

import java.util.List;
import java.util.stream.Collectors;
@Transactional
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
    public CarreraDTO saveCarrera(Carrera carrera) {
        return carreraMapper.carreraToCarreaDTO(carreraRepository.save(carrera));
    }

    @Override
    public CarreraDTO updateCarera(Carrera carrera) {
        return carreraMapper.carreraToCarreaDTO(carreraRepository.save(carrera));
    }

    @Override
    public Boolean existsByName(String name) {

        return carreraRepository.existsByName(name);
    }

    @Override
    public CarreraDTO deleteCarrera(String name) {
        return carreraMapper.carreraToCarreaDTO(carreraRepository.deleteByName(name));
    }


}
