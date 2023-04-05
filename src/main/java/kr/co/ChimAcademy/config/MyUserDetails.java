package kr.co.ChimAcademy.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.co.ChimAcademy.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class MyUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;

	private MemberEntity user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 계정이 갖는 권한 목록 리턴
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getLevel()));
		return authorities;
	}

	@Override
	public String getPassword() {
		// 계정이 갖는 비밀번호
		return user.getPass();
	}

	@Override
	public String getUsername() {
		// 게정이 갖는 아이디
		return user.getUid();
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정 만료 여부(true:만료안됨, false:만료)
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정 잠금 여부(true:안됨, false:)
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 계정비밀번호 만료 (true:안됨)
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 계정 활성화 여부
		return true;
	}

}
