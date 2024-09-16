package it.sport.siw.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.sport.siw.model.Contract;
import it.sport.siw.model.Credentials;
import it.sport.siw.model.Player;
import it.sport.siw.model.Team;
import it.sport.siw.repository.ContractRepository;
import it.sport.siw.repository.PlayerRepository;
import it.sport.siw.service.CredentialsService;

@Controller
public class ContrattoController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ContractRepository contrattoRepository;

    @Autowired
    private CredentialsService credentialsService;

 // Visualizza un elenco di contratti
    @GetMapping("/admin/contracts")
    public String listContracts(Model model) {
        model.addAttribute("contracts", contrattoRepository.findAll());
        return "admin/contracts"; // Vista che mostra l'elenco dei contratti
    }

    // Visualizza un contratto specifico
    @GetMapping("/admin/contract/{id}")
    public String viewContract(@PathVariable("id") Long id, Model model) {
        Contract contratto = contrattoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID contratto non valido: " + id));
        model.addAttribute("contract", contratto);
        return "admin/viewContract"; // Vista per i dettagli del contratto
    }
  }