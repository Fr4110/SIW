package it.sport.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sport.siw.model.President;
import it.sport.siw.repository.PresidentRepository;

@Service
public class PresidentService {

    @Autowired
    private PresidentRepository presidentRepository;

    public President findById(Long id) {
        return presidentRepository.findById(id).orElse(null);
    }

    public void updatePresident(Long id, President president) {
        if (presidentRepository.existsById(id)) {
            president.setId(id); // Assicurati di impostare l'ID per la modifica
            presidentRepository.save(president);
        } else {
            throw new RuntimeException("Presidente non trovato");
        }
    }
}