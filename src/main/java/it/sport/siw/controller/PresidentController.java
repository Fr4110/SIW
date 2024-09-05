package it.sport.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.sport.siw.model.President;
import it.sport.siw.repository.PresidentRepository;


@Controller
public class PresidentController {
	
	@Autowired
	private PresidentRepository presidentRepository;
	
	@GetMapping(value="/admin/formNewPresident")
	public String formNewPresident(Model model) {
		model.addAttribute("president", new President());
		return "admin/formNewPresident";
	}
	
	@GetMapping(value="/admin/indexPresident")
	public String indexPresident() {
		return "admin/indexPresident";
	}
	
	@PostMapping("/admin/president")
	public String newPresident(@ModelAttribute("presidentt") President president, Model model) {
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
		model.addAttribute("president", this.presidentRepository.findById(id).get());
		return "president";
	}

	@GetMapping("/president")
	public String getPresidents(Model model) {
		model.addAttribute("presidents", this.presidentRepository.findAll());
		return "presidents";
	}
}
