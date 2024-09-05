package it.sport.siw.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import static it.sport.siw.model.Credentials.ADMIN_ROLE;
import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//public  class WebSecurityConfig {
	public class AuthConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(withDefaults()) // Abilita CSRF con le impostazioni predefinite
            .cors(cors -> cors.disable()) // Disabilita CORS
            .authorizeHttpRequests(requests -> requests
                // Chiunque può accedere alle pagine index, login, register, ai CSS e alle immagini
                .requestMatchers(HttpMethod.GET, "/","/login", "/index", "/register", "/css/**").permitAll()
                // Chiunque può mandare richieste POST per login e register
                .requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()
                // Solo gli utenti con ruolo ADMIN possono accedere agli endpoint "/admin/**"
                .requestMatchers(HttpMethod.GET, "/admin/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/admin/**").hasAuthority("ROLE_ADMIN")
                // Solo gli utenti autenticati possono accedere alla ricerca
                .requestMatchers(HttpMethod.GET, "/formSearchTeam").authenticated() // Modifica questo percorso secondo necessità
                // Tutti gli utenti autenticati possono accedere alle pagine rimanenti
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/formLogin") // Specifica la pagina di login
                .permitAll()
                .defaultSuccessUrl("/success", true) // Reindirizza alla pagina di successo dopo il login
                .failureUrl("/formLogin?error=true") // Reindirizza alla pagina di login con errore se il login fallisce
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // URL per il logout
                .logoutSuccessUrl("/") // Reindirizza alla home dopo il logout
                .invalidateHttpSession(true) // Invalida la sessione HTTP
                .deleteCookies("JSESSIONID") // Cancella i cookie di sessione
                .clearAuthentication(true) // Pulisce l'autenticazione
                .permitAll()
            );
        return httpSecurity.build();
    }
}