package sistemafenicia.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistemafenicia.dao.UsuarioDao;
import sistemafenicia.model.Perfil;
import sistemafenicia.model.Permissao;
import sistemafenicia.model.Usuario;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

	static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	@Qualifier("usuarioDao")
	private UsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario account = new Usuario();
		UserDetails usercheck = null;
		List<Permissao> listaPermissoes = new ArrayList<Permissao>();
		
		boolean enable = false;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		/*
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String usernameAuthentication = authentication.getName();
        String passwordAuthentication = (String) authentication.getCredentials();
        */

		try {

			account = (Usuario) usuarioDao.findByField("login", username);
			
			if(account==null) {
				log.debug("No such user: " + username);
				account = new Usuario();
				account.setSenha("");
			} else if(account.getPerfis().isEmpty()) {
				log.debug("User " + username + " has no authorities");
			}else{
				
				enable = account.isEnabled();
				accountNonExpired = account.isAccountNonExpired();
				credentialsNonExpired = account.isCredentialsNonExpired();
				accountNonLocked = account.isAccountNonLocked();
				
				for(int i=0; i<account.getPerfis().size(); i++) {
					List<Perfil> listaPerfils = new ArrayList<Perfil>(account.getPerfis());
					int max2 = listaPerfils.get(i).getPermissoes().size();
					for(int j=0; j<max2; j++) {
						List<Permissao> listaPermissoes2 = new ArrayList<Permissao>(listaPerfils.get(i).getPermissoes());
						listaPermissoes.add(listaPermissoes2.get(j));
					}
				}
			}
			
			
			
			usercheck = new User(username, account.getSenha(),
					enable, accountNonExpired, credentialsNonExpired,
					accountNonLocked, getAuthorities(listaPermissoes));

		} catch (Exception e) {
			log.debug("ERRO");
		}
		return usercheck;
	}

	public List<String> getRolesAsList(List<Permissao> list) {
		List <String> rolesAsList = new ArrayList<String>();
		for(Permissao role : list) {
			rolesAsList.add(role.getNome());
		}
		return rolesAsList;
	}

	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	public Collection<? extends GrantedAuthority> getAuthorities(List<Permissao> list) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRolesAsList(list));
		return authList;
	}


}

