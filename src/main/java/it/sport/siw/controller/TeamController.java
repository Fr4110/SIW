package it.sport.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.sport.siw.model.Team;
import it.sport.siw.repository.TeamRepository;
//import it.sport.siw.service.TeamService;


@Controller
public class TeamController {
	//@Autowired 
	//private TeamService teamService;
	
	@Autowired
	private TeamRepository teamRepository;
		
	  @GetMapping("/index")
	  public String index() {
	  return "index.html";
	  }
	
	  @GetMapping("/team/{id}")
	  public String getTeam(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("team", this.teamRepository.findById(id).get());
	    return "team.html";
	  }

	  @GetMapping("/team")
	  public String getTeams(Model model) {
	    model.addAttribute("teams", this.teamRepository.findAll());
	    return "teams.html";
	  }
	  
	  @GetMapping("/formNewTeam")
	    public String formNewTeam(Model model) {
	    model.addAttribute("team", new Team());
	    return "formNewTeam.html";
	  }
	  
	 @GetMapping("/formSearchTeam")
	  public String formSearchTeam() {
	    return "formSearchTeam.html";
	  }
	  
	  @PostMapping("/team")
	  public String newTeam(@ModelAttribute("team") Team team, Model model) {
		this.teamRepository.save(team);
	    model.addAttribute("team", team);
	    return "team.html";
	    /*"redirect:/team/"+team.getId();*/
	  }
	  
	  /*@PostMapping("/teams")
		public String newTeam(@ModelAttribute("movie") Team team, Model model) {
			
			if (!TeamRepository.existsByNameAndYear(Team.getName(), Team.getYear()))) {
				this.TeamRepository.save(team); 
				model.addAttribute("team", team);
				return "team.html";
			} else {
				return "formNewTeam.html"; 
			}
		}
	  */
	  @PostMapping("/formSearchTeam")
	  public String searchTeam(Model model, @RequestParam int year) {
	      model.addAttribute("teams", this.teamRepository.findByYear(year));
	      return "foundTeam.html";
	  }
}
