package ruproject.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ruproject.api.v1.mapper.CarreraMapper;
import ruproject.api.v1.mapper.MateriaMapper;
import ruproject.api.v1.model.CarreraDTO;
import ruproject.domain.Carrera;
import ruproject.domain.Materia;
import ruproject.repositories.CarreraRepository;
import ruproject.repositories.MateriaRepositroy;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreraServiceImpl implements CarreraService {

    private final CarreraMapper carreraMapper;
    private final CarreraRepository carreraRepository;

    private final MateriaMapper materiaMapper;

    private final MateriaRepositroy materiaRepositroy;

    public CarreraServiceImpl(CarreraMapper carreraMapper, CarreraRepository carreraRepository, MateriaMapper materiaMapper, MateriaRepositroy materiaRepositroy) {
        this.carreraMapper = carreraMapper;
        this.carreraRepository = carreraRepository;
        this.materiaMapper = materiaMapper;
        this.materiaRepositroy = materiaRepositroy;
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
        if ((existsByName(name))) {
            Carrera savedCarrera = carreraRepository.findByName(name);
            Carrera returnCarrera = carreraMapper.carreraDTOtoCarrera(carreraDTO);
            returnCarrera.setName(savedCarrera.getName());
            returnCarrera.setId(savedCarrera.getId());

            List<Materia> returnCarreraMaterias = returnCarrera.getMaterias();
            List<Materia> savedCarrearMaterias = savedCarrera.getMaterias();
            List<Materia> materias= new ArrayList<>();

            if(savedCarrearMaterias.size()>0){
                materias.addAll(savedCarrearMaterias);
            }

            if (returnCarrera.getMaterias() != null) {

              materias.addAll(returnCarreraMaterias
                        .stream()
                        .filter(materia -> materiaRepositroy.existsByName(materia.getName()))
                        .map(materia -> materiaRepositroy.findByName(materia.getName()))
                        .collect(Collectors.toList()));

                returnCarrera.setMaterias(materias);

                }


            returnCarrera = carreraRepository.save(returnCarrera);



            return carreraMapper.carreraToCarreaDTO(returnCarrera);

        }



        throw new IllegalArgumentException("Carrera with name:" + name + " not found");

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
