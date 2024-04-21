package com.consultorio.citas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
  @Autowired
  private final UserDetailsService userDetailsService;

  public SecurityConfiguration(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  /* Metodo que administra los permisos y los accesos. */
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    String[] admin = { "/citas/**" , "/editarUsuario", "/usuariosEditar/**","/nuevoUsuario","/usuarios","/medico/**"};// Se defina las paginas que accede el rol admin
    String [] noAutenticado ={ "/registrarse", "/ingresar","/guardarUsuario" ,"/guardarRegistro"};// Se defina las paginas que accede sin autenticación
    String[] user = {"/admin","/citas/**"}; // Se define las paginas que accede el rol user
    return http
            .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
            .authorizeRequests(auth -> auth
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers(noAutenticado).permitAll()
                    .antMatchers(admin).hasAuthority("ADMIN")
                    .antMatchers(user).hasAnyAuthority("ADMIN", "USER")
                    .anyRequest().authenticated())
            .userDetailsService(userDetailsService)
             .headers(headers -> headers.frameOptions().sameOrigin())
            .formLogin(login -> login
                    .loginPage("/")//Carga pagina de login
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                    .successForwardUrl("/admin") // Si el login es exitosos carga la pagina admin
                    .failureUrl("/errors")// si da error carga la pagina de error
                    .and())
            .httpBasic(withDefaults())
            .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {//cifrado de contraseña
    return new BCryptPasswordEncoder();
  }

  /*
   * @Override
   * protected void configure(HttpSecurity http) throws Exception {
   * String[] admin = { "/editarUsuario", "/nuevoUsuario","/admin"
   * ,"/usuarios"};// Se defina las paginas que accede el role
   * // admin
   * String[] user = { "/citas" }; // Se define las paginas que accede el rol user
   * 
   * String [] noAutenticado ={"/", "/registrarse", "/ingresar" }; // enlaces que
   * no requieren autenticación
   * http
   * .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
   * .userDetailsService(userDetailsService)
   * .authorizeRequests()
   * .antMatchers(noAutenticado).permitAll()
   * .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
   * .antMatchers(admin).hasRole("ADMIN")
   * .antMatchers(user).hasAnyRole("ADMIN", "USER")
   * .anyRequest().authenticated() // (3)
   * .and()
   * .formLogin()
   * .loginPage("/")
   * .failureUrl("/error")
   * .permitAll()
   * .and()
   * .httpBasic();
   * http.headers().frameOptions().disable();
   * }
   * 
   * @Bean
   * public AuthenticationManager authenticationManager(HttpSecurity http) throws
   * Exception {
   * return http.getSharedObject(AuthenticationManagerBuilder.class)
   * .build();
   * }
   */
}
