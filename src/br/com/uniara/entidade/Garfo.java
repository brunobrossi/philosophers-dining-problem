package br.com.uniara.entidade;

import br.com.uniara.ui.componente.JGarfo;

/**
 * Essa classe representa os garfos que s�o os recursos pelos quais as threads
 * ir�o concorrer.
 */
public class Garfo {

	private boolean ocupado = false;
	private JGarfo jGarfo;

	/**
	 * M�todo acessor da propriedade ocupado.
	 * 
	 * @return boolean - "true" caso o garfo em quest�o esteja ocupado.
	 */
	public boolean isOcupado() {
		return ocupado;
	}

	/**
	 * Construtor da classe garfo. Recebe alguns par�metros para configura��o do
	 * recurso.
	 * 
	 * @param garfo
	 *            {@link JGarfo} - Componente com a imagem.
	 */
	public Garfo(JGarfo jGarfo) {
		this.jGarfo = jGarfo;
	}

	/**
	 * Associa um recurso com uma thread, no caso um garfo com um fil�sofo. Esse
	 * m�todo espera at� que o garfo seja desoculpado para peg�-lo,
	 * independentemente do tempo que isso demore. <br/>
	 * O modificador synchronized � usado, pois se tratando de acesso � um
	 * recurso por parte de mais de uma thread, deve-se garantir que apenas uma
	 * thread por vez o acessar�, evitando assim o deadlock.
	 */
	public synchronized void pegaGarfo() {
		/*
		 * O la�o abaixo vai "segurar" a execu��o da thread enquanto o recurso
		 * estiver em uso por outra thread.
		 */
		while (this.isOcupado()) {
			try {
				/*
				 * Abaixo temos a chamada ao m�todo wait, que far� com que a
				 * thread em quest�o aguarde a libera��o do recurso. Essa
				 * chamada pode lan�ar uma exce��o, por isso do bloco TRY/CATCH.
				 */
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ocuparGarfo(true);
	}

	/**
	 * Este m�todo � uma sobrecarga do anterior, pois tem o mesmo nome, mas
	 * assinatura diferente (Par�metros diferentes).<br/>
	 * Associa um recurso com uma thread, no caso um garfo com um fil�sofo. Esse
	 * m�todo tenta pegar o garfo por um determinado espa�o de tempo.<br/>
	 * O modificador synchronized tamb�m foi usado.
	 * 
	 * @param tempoLimite
	 *            long - Tempo que o {@link Filosofo} tentar� pegar o
	 *            {@link Garfo}.
	 * @return boolean - "true" em caso de sucesso e "false" em caso de falha.
	 */
	public synchronized boolean pegaGarfo(long tempoLimite) {
		// Atribui a hora atual em milisegundos � variavel.
		long tempoInicial = System.currentTimeMillis();
		/*
		 * O la�o abaixo vai "segurar" a execu��o da thread enquanto o tempo
		 * limite de execu��o n�o for atingido.
		 */
		while (System.currentTimeMillis() - tempoInicial < tempoLimite) {

			// Caso o garfo n�o esteja ocupado o fil�sofo o ocupa imediatamente.
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
	 * M�todo que modifica o garfo e seu respectivo componente quanto �
	 * disponibilidade. E por fim notifica todas as threads.
	 * 
	 * @param isOculpado
	 *            boolean - Indica a disponibilidade do garfo e seu componente.
	 */
	private void ocuparGarfo(boolean isOculpado) {
		/*
		 * Abaixo a thread define o recurso como ocupado ou liberado e altera a
		 * imagem de exibi��o.
		 */
		if (this.jGarfo != null) {
			this.jGarfo.setOcupado(isOculpado);
			this.jGarfo.repaint();
		}
		this.ocupado = isOculpado;

		/*
		 * Essa chamada faz com que todas as threads, que estiverem em espera,
		 * voltem a concorrer pelo recurso em quest�o. No caso, far� com que as
		 * threads verifiquem novamente que o recurso est� em uso.
		 */
		notifyAll();
	}
}
