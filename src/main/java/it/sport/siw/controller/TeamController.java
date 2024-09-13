package it.sport.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.sport.siw.controller.validator.TeamValidator;
import it.sport.siw.model.Team;
import it.sport.siw.repository.TeamRepository;
//import it.sport.siw.service.TeamService;
import jakarta.validation.Valid;

@Controller
public class TeamController {
	// @Autowired
	// private TeamService teamService;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private TeamValidator teamValidator;

	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@GetMapping("/team/{id}")
	public String getTeam(@PathVariable("id") Long id, Model model) {
		model.addAttribute("team", this.teamRepository.findById(id).get());
		return "team";
	}
	/*
	 * @GetMapping("/team/{id}") public String getTeam(@PathVariable("id") Long id,
	 * Model model) { return this.teamRepository.findById(id) .map(team -> {
	 * model.addAttribute("team", team); return "team.html"; })
	 * .orElse("redirect:/team"); // O indirizza a una pagina di errore
	 * personalizzata }
	 */

	@GetMapping("/team")
	public String showTeams(Model model) {
		model.addAttribute("teams", this.teamRepository.findAll());
		return "teams";
	}

	/*
	 * @PostMapping("/teams") public String newTeam(@ModelAttribute("team") Team
	 * team, Model model) { this.teamRepository.save(team);
	 * model.addAttribute("team", team); return "team";
	 * //"redirect:/team/"+team.getId(); }
	 * 
	 * /*@PostMapping("/year") public String showMovieYear(Model model, Integer
	 * year) { model.addAttribute("teams", this.teamRepository.findByYear(year));
	 * return "teams"; }
	 */

	@PostMapping("/team")
	public String newMovie(@Valid @ModelAttribute("team") Team team, BindingResult bindingResult, Model model) {

		this.teamValidator.validate(team, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.teamRepository.save(team);
			model.addAttribute("team", team);
			return "team.html";
		} else {
			return "admin/formNewTeam";
		}
	}

	@GetMapping("/admin/formNewTeam")
	public String formNewTeam(Model model) {
		model.addAttribute("team", new Team());
		return "admin/formNewTeam";
	}

	@GetMapping("/formSearchTeam")
	public String formSearchTeam(Model model) {
	    model.addAttribute("teamSearch", new Team()); // Associa un nuovo oggetto Team
		return "formSearchTeam";
	}

	@PostMapping("/formSearchTeam")
	public String searchTeam(Model model, @RequestParam("year") int year) {
		model.addAttribute("teams", this.teamRepository.findByYear(year));
		return "foundTeam";
	}
	
	// Metodo per visualizzare il form di modifica per una squadra esistente
	   
    @GetMapping("/admin/formEditTeam/{id}")
    public String formEditTeam(@PathVariable("id") Long id, Model model) {
        Team team = this.teamRepository.findById(id).get();
        if (team != null) {
            model.addAttribute("team", team);
            return "admin/formEditTeam";
        } else {
            // Gestisci il caso in cui il team non è trovato
            return "redirect:/error";
        }
    }


    // Metodo per aggiornare una squadra esistente
    @PostMapping("/admin/formEditTeam/{id}")
    public String updateTeam(@PathVariable("id") Long id, @Valid @ModelAttribute("team") Team team, BindingResult bindingResult, Model model) {
        // Validiamo i dati della squadra
        this.teamValidator.validate(team, bindingResult);
        if (!bindingResult.hasErrors()) {
            // Recuperiamo la squadra esistente dal database
            Team existingTeam = this.teamRepository.findById(id).get();
            
            // Aggiorniamo i campi della squadra
            existingTeam.setName(team.getName());
            existingTeam.setYear(team.getYear());
            existingTeam.setAddress(team.getAddress());
            
            // Salviamo le modifiche
            this.teamRepository.save(existingTeam);

            // Aggiungiamo la squadra aggiornata al modello
            model.addAttribute("team", existingTeam);

            // Redirigiamo alla pagina della squadra
            return "redirect:/team/" + id;
        } else {
            // In caso di errori di validazione, torniamo al form di modifica con gli errori
            model.addAttribute("team", team);
            return "admin/formEditTeam";
        }
    }
    
 // Rotta per mostrare la pagina delle operazioni sulle squadre (inserimento/aggiornamento)
    @GetMapping("/admin/teamOperation")
    public String teamOperationsPage(Model model) {
        return "admin/teamOperation"; 
    }
    
    @GetMapping("/admin/selectTeamToEdit")
    public String selectTeamToEdit(Model model) {
        model.addAttribute("teams", this.teamRepository.findAll());
        return "admin/selectTeamToEdit";
    }

}