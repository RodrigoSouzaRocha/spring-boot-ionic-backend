package com.systemlog.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.systemlog.domain.enums.Perfil;

public class UserSpringSecurity implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String email;	
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;

	public UserSpringSecurity() {
		
	}
	
	public UserSpringSecurity(Long id, String email, String senha, List<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Por padrão manteremos true que a conta não esta EXPIRADA mais podemos implementar no fururo uma verificação 
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Por padrão manteremos true que a conta não esta BLOQUEADA mais podemos implementar no fururo uma verificação 
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Por padrão manteremos true que as credenciais não estão EXPIRADA mais podemos implementar no fururo uma verificação 
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Se o usuario esta ativo por padrão estará ativo
		return true;
	}
	
	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

}
