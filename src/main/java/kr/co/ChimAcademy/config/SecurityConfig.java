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
public class SecurityConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SecurityUserService securityUserService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	// 주소 허용
        http.authorizeHttpRequests((authorize) -> authorize
        		// resources 허용
        		.antMatchers("/js/**", "/img/**", "/style/**").permitAll()
        		// 로그인 전체 허용
        		.antMatchers("/member/**", "/", "/index", "/login").permitAll()
        		// 교수, 학생, 조교 허용
//        		.antMatchers("/professor/**").hasAnyRole("3", "4")
//        		.antMatchers("/student/**").hasAnyRole("1","2", "4")
//        		.antMatchers("/assistant/lecture/search").permitAll()
//        		.antMatchers("/assistant/**").hasAnyRole("2", "4")
        		// 도서관 전자책 등록 페이지 접근 ( 관리자만 ) - 홍모
        		.antMatchers("/elib/info/register").hasAnyRole("4") 
        		// 그외 주소 로그인 필요
        		.anyRequest().authenticated()
		        );
        // 로그인 설정
        http.formLogin(login -> login
                .loginPage("/member/login")
                .defaultSuccessUrl("/notice")
                .failureUrl("/login?success=100")
                .usernameParameter("uid")
                .passwordParameter("pass")
                );

        // 사이트 위조 방지 설정 (구홍모: 배포 시 주석처리해야함, 그러나 주석 처리 시 ajax 불가능)
        http.csrf(csrf -> csrf.disable());
        
        http.logout(logout -> logout
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?success=200")
                .permitAll()
                );
        // 자동로그인
        http.rememberMe(me -> me
                .userDetailsService(securityUserService)
                .tokenRepository(tokenRepository())
                .tokenValiditySeconds(604800)); // 1주일
        // 권한 접근 불가 페이지
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
