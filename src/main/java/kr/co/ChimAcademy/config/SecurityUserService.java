package kr.co.ChimAcademy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.ChimAcademy.entity.MemberEntity;
import kr.co.ChimAcademy.repository.MemberRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SecurityUserService implements UserDetailsService{

	@Autowired
	private MemberRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberEntity user = null;
		try {
			user = repo.findById(username).get();
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		UserDetails userDts = MyUserDetails.builder()
										   .user(user)
										   .build();
		
		return userDts;
	}

}
