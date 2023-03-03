package ruproject.services;

import org.springframework.stereotype.Service;
import ruproject.api.v1.mapper.CarreraMapper;
import ruproject.api.v1.model.CarreraDTO;
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
}
