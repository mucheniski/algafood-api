package com.algaworks.algafood.validations;

public interface Grupos {

	/*
	 * Somente um tipo de marcação para delimitar que algumas constraints façam parte de um grupo.
	 * Onde tiver uma anotação bean validation com este grupo vão ser ignorados os demais e somente
	 * o que estiver configurado aqui vai ser validado.
	 * (groups = Grupos.CadastroRestaurante.class)
	 * Por padrão, toda vez que você faz um validação Bean Validation é usado o group Default.class
	 * caso não seja especificado um grupo
	 * */
	public interface CozinhaId {}
	
	public interface EstadoId {}
	
}
