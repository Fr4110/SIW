package it.sport.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sport.siw.model.Team;
import it.sport.siw.repository.TeamRepository;

@Service
public class TeamService {
	
	@Autowired
	private TeamRepository teamRepository;

	public Team findById(Long id) {
		return teamRepository.findById(id).orElse(null);
	}

	public List<Team> findAll() {
		return (List<Team>) teamRepository.findAll();
	}
	
	public List<Team> findByYear(Integer year) {
		return teamRepository.findByYear(year);
	}

	public Team save(Team team) {
		return teamRepository.save(team);
		}

}
