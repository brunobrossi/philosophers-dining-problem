package br.com.uniara.constante;

import javax.swing.JTextArea;

import br.com.uniara.entidade.Filosofo;

/**
 * Esse enum foi criado para representar os estados poss�veis de uma thread (
 * {@link Filosofo} ).
 */
public enum Estado {

	PENSANDO("pensando"), COMENDO("comendo"), FAMINTO_SEM_GARFOS(
			"faminto sem garfos"), FAMINTO_COM_GARFO("faminto com um garfo");

	private String estado;

	/**
	 * Construtor do enum {@link Estado}.
	 * 
	 * @param estado
	 *            {@link Estado} - Valor que ser� associado � propriedade
	 *            estado.
	 */
	private Estado(String estado) {
		this.estado = estado;
	}

	/**
	 * Registra uma mensagem utilizando os dois par�metros.
	 * 
	 * @param nomeFilosofo
	 *            {@link String} - Nome da thread que representa um Filosofo,
	 *            esse par�metro ser� usado na mensagem.
	 * @param txtLog
	 *            {@link JTextArea} - Componente em que a mensagem ser� escrita.
	 */
	public void exibeEstado(String nomeFilosofo, JTextArea txtLog) {
		txtLog.append(String.format("   � %s est� %s. \n", nomeFilosofo,
				this.estado));
	}

	/**
	 * O m�todo toString foi sobreescrito para que os estados
	 * "FAMINTO_COM_GARFO" e "FAMINTO_SEM_GARFOS" retornem o valor "FAMINTO".
	 */
	@Override
	public String toString() {
		String retorno = super.toString();
		if (this.equals(FAMINTO_COM_GARFO) || this.equals(FAMINTO_SEM_GARFOS)) {
			retorno = "FAMINTO";
		}
		return retorno;
	}
}
