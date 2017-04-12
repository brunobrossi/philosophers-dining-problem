package br.com.uniara.constante;

import javax.swing.JTextArea;

import br.com.uniara.entidade.Filosofo;

/**
 * Esse enum foi criado para representar os estados possíveis de uma thread (
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
	 *            {@link Estado} - Valor que será associado à propriedade
	 *            estado.
	 */
	private Estado(String estado) {
		this.estado = estado;
	}

	/**
	 * Registra uma mensagem utilizando os dois parâmetros.
	 * 
	 * @param nomeFilosofo
	 *            {@link String} - Nome da thread que representa um Filosofo,
	 *            esse parâmetro será usado na mensagem.
	 * @param txtLog
	 *            {@link JTextArea} - Componente em que a mensagem será escrita.
	 */
	public void exibeEstado(String nomeFilosofo, JTextArea txtLog) {
		txtLog.append(String.format("   • %s está %s. \n", nomeFilosofo,
				this.estado));
	}

	/**
	 * O método toString foi sobreescrito para que os estados
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
