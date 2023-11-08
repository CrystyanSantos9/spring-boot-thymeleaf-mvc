package dev.cryss.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.cryss.springboot.model.Usuario;
import dev.cryss.springboot.repository.UsuarioRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findUserByLogin(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return new User(usuario.getLogin(), usuario.getPassword(), usuario.isEnabled(), usuario.isAccountNonExpired(),
				usuario.isCredentialsNonExpired(), usuario.isAccountNonLocked(), usuario.getAuthorities());
	}

}
