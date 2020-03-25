package br.com.uwrtech.uwrfinance.exceptions;

public class ErroAutenticacao extends RuntimeException{
	
	public ErroAutenticacao(String mensagem) {
		super(mensagem);
	}
}
