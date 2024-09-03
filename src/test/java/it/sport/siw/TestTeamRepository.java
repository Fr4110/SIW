package it.sport.siw;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.sport.siw.model.Team;
import it.sport.siw.repository.TeamRepository;

@SpringBootTest
public class TestTeamRepository {

	@Autowired
	 private TeamRepository teamRepository;

    @Test
    public void testFindByYear() {
        List<Team> teams = teamRepository.findByYear(2023);
        assertNotNull(teams);
        assertFalse(teams.isEmpty());
    }
}
