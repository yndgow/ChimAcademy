package kr.co.ChimAcademy.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
//@RequiredArgsConstructor
//@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SecurityUserService securityUserService;


//    @Bean
//    WebSecurityCustomizer webSecurityCustomizer() {
//		return web -> web.ignoring().antMatchers("/img/**", "/js/**", "/style/**");
//		return web -> web.ignoring().antMatchers("/img/**", "/js/**", "/style/**");
//	}


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
        		// resources 허용
        		.antMatchers("/js/**", "/img/**", "/style/**").permitAll()
        		// 로그인 전체 허용
        		.antMatchers("/member/**").permitAll()
        		.antMatchers("/", "/index").permitAll()
        		.antMatchers("/login").permitAll()
        		// 교수 허용
        		.antMatchers("/professor/**").hasRole("3")
        		.anyRequest().authenticated()
		        );
                
        http.formLogin(login -> login
                .loginPage("/member/login")
                .defaultSuccessUrl("/notice")
                .failureUrl("/login?success=100")
                .usernameParameter("uid")
                .passwordParameter("pass")
                );

        // 사이트 위조 방지 설정
        http.csrf(csrf -> csrf.disable());
        http.logout(logout -> logout
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index?success=200")
                .permitAll()
                );
        http.rememberMe(me -> me
                .userDetailsService(securityUserService)
                .tokenRepository(tokenRepository())
                .tokenValiditySeconds(604800)); // 1주일
        
        http.exceptionHandling(handling -> handling.accessDeniedPage("/accessDenied"));
        	
        
        
        
        return http.build();
    }

//     비밀번호 암호화
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

//    
    // JDBC 기반의 tokenRepository 구현체
    @Bean
    PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource); // dataSource 주입
        return jdbcTokenRepository;
    }
}
