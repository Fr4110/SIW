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
		return "formNewTeam";
	}

	@GetMapping("/formSearchTeam")
	public String formSearchTeam() {
		return "formSearchTeam";
	}

	@PostMapping("/formSearchTeam")
	public String searchTeam(Model model, @RequestParam int year) {
		model.addAttribute("teams", this.teamRepository.findByYear(year));
		return "formSearchTeam.html";
	}
}
