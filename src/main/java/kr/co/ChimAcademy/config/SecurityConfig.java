package kr.co.ChimAcademy.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	@Autowired
	private DataSource dataSource;
	
//	@Autowired
//	private SecurityUserService securityUserService;
	
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    	// 접근권한 설정
        http.authorizeHttpRequests()
        	.antMatchers("/").permitAll();
//        	.antMatchers("/my/**","/admin/**")
//        	.hasAnyRole("2","3","4","5");
//        	.antMatchers("/write").hasAnyRole("3","4","5")
//        	.antMatchers("/view").hasAnyRole("3","4","5");
//        	.antMatchers("/admin/**").hasRole("ADMIN")
//        	.antMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
//        	.antMatchers("/member/**").hasAnyRole("ADMIN", "MANAGER", "MEMBER");
        	
		http.formLogin()
			.loginPage("/member/login")
			.defaultSuccessUrl("/notice")
			.failureUrl("/index?success=100")
			.usernameParameter("uid")
			.passwordParameter("pass");
//        
        // 사이트 위조 방지 설정
        http.csrf().disable();
//        
//        // 로그아웃
        http.logout()
			.invalidateHttpSession(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/index?success=200");
//        
//        // 자동 로그인
//        http.rememberMe()
//        				.userDetailsService(securityUserService)
//        				.tokenRepository(tokenRepository())
//        				.tokenValiditySeconds(600);
        
//        http.exceptionHandling().accessDeniedPage("/accessDenied");
//        	.accessDeniedPage("/accessDenied");
        
        
        
        return http.build();
    }
    
    // AuthenticationManagerBuilder 대신?
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new SecurityUserService();
//	}
	
//     비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    	//return new BCryptPasswordEncoder();
    }
//    
    // JDBC 기반의 tokenRepository 구현체
    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource); // dataSource 주입
        return jdbcTokenRepository;
    }
}
