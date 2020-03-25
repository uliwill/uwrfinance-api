package br.com.uwrtech.uwrfinance.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.uwrtech.uwrfinance.exceptions.ErroAutenticacao;
import br.com.uwrtech.uwrfinance.exceptions.RegraNegocioException;
import br.com.uwrtech.uwrfinance.model.entity.Usuario;
import br.com.uwrtech.uwrfinance.model.repository.UsuarioRepository;
import br.com.uwrtech.uwrfinance.service.impl.UsuarioServiceImpl;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@SpyBean
	UsuarioServiceImpl service;

	@MockBean
	UsuarioRepository repository;
	
	@Test(expected = Test.None.class)
	public void deveSalvarUmUsuario() {
		// cenário
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder().id(1l).nome("nome").email("email@gmail.com").senha("senha").build();
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		// ação
		Usuario usuarioSalvo = service.salvarusuario(new Usuario());
		
		// verificação
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@gmail.com");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
	}
	
	// não deve salvar um usuário com e-mail já cadastrado
	@Test(expected = RegraNegocioException.class)
	public void naoSalvaUsuario() {
		// cenário
		Usuario usuario = Usuario.builder().email("email@email.com").build();
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail("email@email.com");
		
		// ação
		service.salvarusuario(usuario);
		
		// verificação
		Mockito.verify(repository, Mockito.never()).save(usuario);
	}

	// deve autenticar um usuário com sucesso
	@Test(expected = Test.None.class)
	public void autenticarUsuario() {
		// cenário
		String email = "email@email.com";
		String senha = "senha";

		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

		// ação
		Usuario result = service.autenticar(email, senha);

		// verificação
		Assertions.assertThat(result).isNotNull();

	}

	// deve lançar erro quando não encontrar usuário cadastrado com o e-mail
	// informado
	@Test
	public void usuarioNaoCadastrado() {
		// cenário
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

		// ação
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "senha"));

		// verificação
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Usuário não encontrado.");
	}

	// deve lançar erro quando a senha não bater
	@Test
	public void senhaErrada() {
		// cenário
		String senha = "senha";
		Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

		// ação
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "123"));

		// verificação
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida.");
	}

	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		// cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		// ação
		service.validarEmail("email@email.com");
	}

	// deve lançar erro quando existir e-mail cadastrado
	@Test(expected = RegraNegocioException.class)
	public void emailJaExiste() {
		// cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		// ação
		service.validarEmail("email@email.com");
	}
}
