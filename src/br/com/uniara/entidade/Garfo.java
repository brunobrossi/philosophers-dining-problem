package br.com.uniara.entidade;

import br.com.uniara.ui.componente.JGarfo;

/**
 * Essa classe representa os garfos que são os recursos pelos quais as threads
 * irão concorrer.
 */
public class Garfo {

	private boolean ocupado = false;
	private JGarfo jGarfo;

	/**
	 * Método acessor da propriedade ocupado.
	 * 
	 * @return boolean - "true" caso o garfo em questão esteja ocupado.
	 */
	public boolean isOcupado() {
		return ocupado;
	}

	/**
	 * Construtor da classe garfo. Recebe alguns parâmetros para configuração do
	 * recurso.
	 * 
	 * @param garfo
	 *            {@link JGarfo} - Componente com a imagem.
	 */
	public Garfo(JGarfo jGarfo) {
		this.jGarfo = jGarfo;
	}

	/**
	 * Associa um recurso com uma thread, no caso um garfo com um filósofo. Esse
	 * método espera até que o garfo seja desoculpado para pegá-lo,
	 * independentemente do tempo que isso demore. <br/>
	 * O modificador synchronized é usado, pois se tratando de acesso à um
	 * recurso por parte de mais de uma thread, deve-se garantir que apenas uma
	 * thread por vez o acessará, evitando assim o deadlock.
	 */
	public synchronized void pegaGarfo() {
		/*
		 * O laço abaixo vai "segurar" a execução da thread enquanto o recurso
		 * estiver em uso por outra thread.
		 */
		while (this.isOcupado()) {
			try {
				/*
				 * Abaixo temos a chamada ao método wait, que fará com que a
				 * thread em questão aguarde a liberação do recurso. Essa
				 * chamada pode lançar uma exceção, por isso do bloco TRY/CATCH.
				 */
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ocuparGarfo(true);
	}

	/**
	 * Este método é uma sobrecarga do anterior, pois tem o mesmo nome, mas
	 * assinatura diferente (Parâmetros diferentes).<br/>
	 * Associa um recurso com uma thread, no caso um garfo com um filósofo. Esse
	 * método tenta pegar o garfo por um determinado espaço de tempo.<br/>
	 * O modificador synchronized também foi usado.
	 * 
	 * @param tempoLimite
	 *            long - Tempo que o {@link Filosofo} tentará pegar o
	 *            {@link Garfo}.
	 * @return boolean - "true" em caso de sucesso e "false" em caso de falha.
	 */
	public synchronized boolean pegaGarfo(long tempoLimite) {
		// Atribui a hora atual em milisegundos à variavel.
		long tempoInicial = System.currentTimeMillis();
		/*
		 * O laço abaixo vai "segurar" a execução da thread enquanto o tempo
		 * limite de execução não for atingido.
		 */
		while (System.currentTimeMillis() - tempoInicial < tempoLimite) {

			// Caso o garfo não esteja ocupado o filósofo o ocupa imediatamente.
			if (!this.isOcupado()) {
				ocuparGarfo(true);
				return true;
			}
		}
		return false;
	}

	/**
	 * Libera o {@link Garfo}, notificando todas as threads.
	 */
	public synchronized void soltaGarfo() {
		ocuparGarfo(false);
	}

	/**
	 * Método que modifica o garfo e seu respectivo componente quanto à
	 * disponibilidade. E por fim notifica todas as threads.
	 * 
	 * @param isOculpado
	 *            boolean - Indica a disponibilidade do garfo e seu componente.
	 */
	private void ocuparGarfo(boolean isOculpado) {
		/*
		 * Abaixo a thread define o recurso como ocupado ou liberado e altera a
		 * imagem de exibição.
		 */
		if (this.jGarfo != null) {
			this.jGarfo.setOcupado(isOculpado);
			this.jGarfo.repaint();
		}
		this.ocupado = isOculpado;

		/*
		 * Essa chamada faz com que todas as threads, que estiverem em espera,
		 * voltem a concorrer pelo recurso em questão. No caso, fará com que as
		 * threads verifiquem novamente que o recurso está em uso.
		 */
		notifyAll();
	}
}
