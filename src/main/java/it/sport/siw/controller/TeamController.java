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
import jakarta.validation.Valid;

@Controller
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamValidator teamValidator;

    @GetMapping("/index")
    public String index() {
        return "index";
    }
    @GetMapping("/teams")
    public String listTeams(Model model) {
        model.addAttribute("teams", this.teamRepository.findAll());
        return "teams"; // Riferisce al template teams.html
    }

    // Visualizza i dettagli di una squadra con i giocatori associati
    @GetMapping("/team/{id}")
    public String getTeam(@PathVariable("id") Long id, Model model) {
        Team team = this.teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid team Id:" + id));
        model.addAttribute("team", team);
        model.addAttribute("players", team.getPlayers()); // Passa la lista dei giocatori al modello
        return "team";  // La vista per visualizzare i dettagli della squadra e i giocatori
    }
 // Visualizza tutte le squadre per admin
    @GetMapping("/admin/indexTeams")
    public String showTeamsForAdmin(Model model) {
        model.addAttribute("teams", this.teamRepository.findAll());
        return "admin/indexTeams";  // Visualizzazione per l'admin
    }
    @GetMapping("/team")
    public String showTeams(Model model) {
        model.addAttribute("teams", this.teamRepository.findAll());
        return "teams";
    }
 // Mappatura per la pagina riservata agli admin per visualizzare i dettagli di una squadra
    @GetMapping("/admin/indexTeam/{id}")
    public String getIndexTeam(@PathVariable("id") Long id, Model model) {
        Team team = this.teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid team Id:" + id));
        model.addAttribute("team", team);
        return "admin/indexTeam";  // Vista per admin
    }
    @PostMapping("/team")
    public String newTeam(@Valid @ModelAttribute("team") Team team, BindingResult bindingResult, Model model) {
        // Valida il team
        this.teamValidator.validate(team, bindingResult);
        
        // Se ci sono errori, mostra nuovamente il form con i messaggi di errore
        if (bindingResult.hasErrors()) {
            return "admin/formNewTeam";  // Nome del template per il form di creazione del team
        }
        
        // Se non ci sono errori, salva il team e reindirizza alla pagina di dettaglio
        this.teamRepository.save(team);
        return "redirect:/team/" + team.getId();  // Reindirizza alla pagina di dettaglio del team
    }


    @GetMapping("/admin/formNewTeam")
    public String formNewTeam(Model model) {
        model.addAttribute("team", new Team());
        return "admin/formNewTeam";
    }

    @GetMapping("/formSearchTeam")
    public String formSearchTeams(Model model) {
        model.addAttribute("teamSearch", new Team());
        return "formSearchTeam";
    }

    @PostMapping("/formSearchTeam")
    public String searchTeams(Model model, @RequestParam("year") int year) {
        model.addAttribute("teams", this.teamRepository.findByYear(year));
        return "foundTeam";
    }

    @GetMapping("/admin/selectTeamToEdit")
    public String selectTeamToEdit(Model model) {
        model.addAttribute("teams", this.teamRepository.findAll());
        return "admin/selectTeamToEdit";
    }

    @GetMapping("/admin/formEditTeam/{id}")
    public String formEditTeam(@PathVariable("id") Long id, Model model) {
        Team team = this.teamRepository.findById(id).orElse(null);
        if (team != null) {
            model.addAttribute("team", team);
            return "admin/formEditTeam";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/admin/formEditTeam/{id}")
    public String updateTeam(@PathVariable("id") Long id, @Valid @ModelAttribute("team") Team team, BindingResult bindingResult, Model model) {
        this.teamValidator.validate(team, bindingResult);
        if (!bindingResult.hasErrors()) {
            Team existingTeam = this.teamRepository.findById(id).orElse(null);
            if (existingTeam != null) {
                existingTeam.setName(team.getName());
                existingTeam.setYear(team.getYear());
                existingTeam.setAddress(team.getAddress());

                this.teamRepository.save(existingTeam);

                model.addAttribute("team", existingTeam);

                return "redirect:/admin/indexTeam/" + id;
            } else {
                return "redirect:/error";
            }
        } else {
            model.addAttribute("team", team);
            return "admin/formEditTeam";
        }
    }
    @GetMapping("/admin/selectTeamToDelete")
    public String selectTeamToDelete(Model model) {
        model.addAttribute("teams", this.teamRepository.findAll());
        return "admin/selectTeamToDelete";
    }
    
    // Rotta per mostrare la pagina delle operazioni sulle squadre (inserimento/aggiornamento)
    @GetMapping("/admin/teamOperation")
    public String teamOperationsPage(Model model) {
        return "admin/teamOperation"; 
    }

    // POST Mapping per eliminare una squadra
    @GetMapping("/admin/selectTeamToDelete/{id}")
    public String deleteTeam(@PathVariable("id") Long id) {
        teamRepository.deleteById(id);
        return "redirect:/admin/selectTeamToDelete";
    }
}