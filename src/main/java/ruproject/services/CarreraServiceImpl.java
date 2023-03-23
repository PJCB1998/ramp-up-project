package ruproject.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ruproject.api.v1.mapper.CarreraMapper;
import ruproject.api.v1.mapper.CycleAvoidingMappingContext;
import ruproject.api.v1.model.CarreraDTO;
import ruproject.domain.Carrera;
import ruproject.domain.Materia;
import ruproject.exception.CarreraNotFoundException;
import ruproject.repositories.CarreraRepository;
import ruproject.repositories.MateriaRepositroy;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreraServiceImpl implements CarreraService {

    private final CarreraMapper carreraMapper;
    private final CarreraRepository carreraRepository;


    private final MateriaRepositroy materiaRepositroy;

    public CarreraServiceImpl(CarreraMapper carreraMapper, CarreraRepository carreraRepository,  MateriaRepositroy materiaRepositroy) {
        this.carreraMapper = carreraMapper;
        this.carreraRepository = carreraRepository;
        this.materiaRepositroy = materiaRepositroy;
    }

    @Override
    public List<CarreraDTO> getAllCarreras() {
        return carreraRepository
                .findAll()
                .stream()
                .map(carrera -> carreraMapper.carreraToCarreaDTO(carrera,new CycleAvoidingMappingContext()))
                .collect(Collectors.toList());
    }

    @Override
    public CarreraDTO getCarreraByName(String name) {
        return carreraMapper.carreraToCarreaDTO(carreraRepository.findByName(name), new CycleAvoidingMappingContext());
    }

    @Override
    public CarreraDTO saveCarrera(CarreraDTO carreraDTO) {
        Carrera carrera = carreraMapper.carreraDTOtoCarrera(carreraDTO, new CycleAvoidingMappingContext());
        return carreraMapper.carreraToCarreaDTO(carreraRepository.save(carrera),new CycleAvoidingMappingContext());
    }

    @Override
    public CarreraDTO updateCarrera(String name, CarreraDTO carreraDTO) {
        if (existsByName(name)) {
            Carrera savedCarrera = carreraRepository.findByName(name);
            Carrera returnCarrera = carreraMapper.carreraDTOtoCarrera(carreraDTO,new CycleAvoidingMappingContext());
            returnCarrera.setName(savedCarrera.getName());
            returnCarrera.setId(savedCarrera.getId());

            List<Materia> returnCarreraMaterias = returnCarrera.getMaterias();
            List<Materia> savedCarrearMaterias = savedCarrera.getMaterias();
            List<Materia> materias= new ArrayList<>();

            if(!savedCarrearMaterias.isEmpty()){
                materias.addAll(savedCarrearMaterias);
            }

            if (!returnCarrera.getMaterias().isEmpty()) {

              materias.addAll(returnCarreraMaterias
                        .stream()
                        .filter(materia -> materiaRepositroy.existsByName(materia.getName()))
                        .map(materia -> materiaRepositroy.findByName(materia.getName()))
                        .collect(Collectors.toList()));

                returnCarrera.setMaterias(materias);

                }


            returnCarrera = carreraRepository.save(returnCarrera);



            return carreraMapper.carreraToCarreaDTO(returnCarrera, new CycleAvoidingMappingContext());

        }



        throw new CarreraNotFoundException(name);

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
