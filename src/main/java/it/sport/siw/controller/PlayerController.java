package it.sport.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.ModelAttribute;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.sport.siw.model.Player;
	import it.sport.siw.repository.PlayerRepository;
	
@Controller
public class PlayerController {

		
		@Autowired 
		private PlayerRepository playerRepository;

		@GetMapping(value="/president/formNewPlayer")
		public String formNewPlayer(Model model) {
			model.addAttribute("player", new Player());
			return "president/formNewPlayer";
		}
		
		@GetMapping(value="/president/indexPlayer")
		public String indexPlayer() {
			return "president/indexPlayer";
		}
		
		@PostMapping("/president/player")
		public String newPlayer(@ModelAttribute("player") Player player, Model model) {
			if (!playerRepository.existsByNameAndSurname(player.getName(), player.getSurname())) {
				this.playerRepository.save(player); 
				model.addAttribute("player", player);
				return "player";
			} else {
				model.addAttribute("messaggioErrore", "Questo giocatore esiste già");
				return "president/formNewPlayer"; 
			}
		}

		@GetMapping("/player/{id}")
		public String getPlayer(@PathVariable("id") Long id, Model model) {
			model.addAttribute("player", this.playerRepository.findById(id).get());
			return "player";
		}

		@GetMapping("/player")
		public String getPlayers(Model model) {
			model.addAttribute("players", this.playerRepository.findAll());
			return "players";
		}
		
		 // Form per eliminare un giocatore - Gestito dal presidente
	    @GetMapping("/president/formDeletePlayer")
	    public String formDeletePlayer(Model model) {
	        model.addAttribute("players", this.playerRepository.findAll());
	        return "president/formDeletePlayer";  // Mostra la vista con il form per eliminare
	    }

	    // Eliminazione di un giocatore - Gestito dal presidente
	    @PostMapping("/president/player/delete")
	    public String deletePlayer(@RequestParam("Id") Long Id, Model model) {
	        if (this.playerRepository.existsById(Id)) {
	            this.playerRepository.deleteById(Id);
	            model.addAttribute("messaggioSuccesso", "Giocatore eliminato con successo");
	        } else {
	            model.addAttribute("messaggioErrore", "Giocatore non trovato");
	        }
	        model.addAttribute("players", this.playerRepository.findAll());
	        return "president/formDeletePlayer";  // Torna alla vista con il form per eliminare
	    }
	}
