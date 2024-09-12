package it.sport.siw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.sport.siw.model.President;
import it.sport.siw.repository.PresidentRepository;
import it.sport.siw.service.PresidentService;


@Controller
public class PresidentController {
	
	@Autowired
	private PresidentRepository presidentRepository;
	
	@Autowired
	private PresidentService presidentService;
	
	@GetMapping(value="/admin/indexPresident")
	public String indexPresident() {
		return "admin/indexPresident";
	}
	
	@GetMapping(value="/admin/formNewPresident")
	public String formNewPresident(Model model) {
		model.addAttribute("president", new President());
		return "admin/formNewPresident";
	}
	
	@PostMapping("/admin/formNewPresident")
	public String newPresident(@ModelAttribute("president") President president, Model model) {
		if (!presidentRepository.existsByNameAndSurname(president.getName(), president.getSurname())) {
			this.presidentRepository.save(president); 
			model.addAttribute("president", president);
			return "president";
		} else {
			model.addAttribute("messaggioErrore", "Questo presidente esiste già");
			return "admin/formNewPresident"; 
		}
	}
	
	@GetMapping("/president/{id}")
	public String getPresident(@PathVariable("id") Long id, Model model) {
	    Optional<President> optionalPresident = this.presidentRepository.findById(id);
	    
	    if (optionalPresident.isPresent()) {
	        model.addAttribute("president", optionalPresident.get());
	        return "president";
	    } else {
	        model.addAttribute("errorMessage", "Presidente non trovato");
	        return "error"; // O un'altra vista di errore
	    }
	}
	 // Form per eliminare un presidente - Gestito dall'admin
    @GetMapping("/admin/formDeletePresident")
    public String formDeletePresident(Model model) {
        model.addAttribute("presidents", this.presidentRepository.findAll());
        return "admin/formDeletePresident";  // Mostra la vista con il form per eliminare
    }

    // Eliminazione di un presidente - Gestito dall'admin
    @PostMapping("/admin/president/delete")
    public String deletePresident(@RequestParam("Id") Long Id, Model model) {
        if (this.presidentRepository.existsById(Id)) {
            this.presidentRepository.deleteById(Id);
            model.addAttribute("messaggioSuccesso", "Presidente eliminato con successo");
        } else {
            model.addAttribute("messaggioErrore", "Presidente non trovato");
        }
        model.addAttribute("presidents", this.presidentRepository.findAll());
        return "admin/formDeletePresident";  // Torna alla vista con il form per eliminare
    }
    
    @GetMapping("/admin/formEditPresident/{id}")
    public String formEditPresident(@PathVariable Long id, Model model) {
        President president = presidentService.findById(id);
        if (president == null) {
            return "error"; // La tua pagina di errore
        }
        model.addAttribute("president", president);
        return "admin/formEditPresident"; // Assicurati di avere una vista chiamata "editPresidentForm.html"
    }
    
    @PostMapping("/admin/formEditPresident/{id}")
    public String updatePresident(@PathVariable Long id, @ModelAttribute President president, Model model) {
        try {
            // Logica per aggiornare il presidente
            presidentService.updatePresident(id, president);
            return "redirect:/admin/success"; // O qualsiasi altra pagina di successo
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Errore durante l'aggiornamento del presidente.");
            return "error"; // La tua pagina di errore
        }
    }

}
