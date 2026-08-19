package br.com.corpia.service;

import br.com.corpia.model.Curriculo;
import br.com.corpia.repository.CurriculoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurriculoService {

    @Autowired
    private CurriculoRepository curriculoRepository;

    public Curriculo salvar(Curriculo curriculo) {

        return curriculoRepository.save(curriculo);
    }

    public List<Curriculo> listarTodos() {

        return curriculoRepository.findAll();
    }
}