package it.sport.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.ModelAttribute;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;

	import it.sport.siw.model.Player;
	import it.sport.siw.repository.PlayerRepository;
	
@Controller
public class PlayerController {

		
		@Autowired 
		private PlayerRepository playerRepository;

		@GetMapping(value="/admin/formNewPlayer")
		public String formNewPlayer(Model model) {
			model.addAttribute("player", new Player());
			return "admin/formNewPlayer";
		}
		
		@GetMapping(value="/admin/indexPlayer")
		public String indexPlayer() {
			return "admin/indexPlayer";
		}
		
		@PostMapping("/admin/player")
		public String newPlayer(@ModelAttribute("player") Player player, Model model) {
			if (!playerRepository.existsByNameAndSurname(player.getName(), player.getSurname())) {
				this.playerRepository.save(player); 
				model.addAttribute("player", player);
				return "player";
			} else {
				model.addAttribute("messaggioErrore", "Questo giocatore esiste già");
				return "admin/formNewPlayer"; 
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
	}
