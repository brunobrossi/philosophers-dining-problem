package br.com.uniara.entidade;

import javax.swing.JTextArea;

import br.com.uniara.constante.Estado;
import br.com.uniara.ui.componente.JFilosofo;

/**
 * Essa classe representa um fil�sofo. Ela herda da classe Thread, portanto se
 * comportar� como a mesma.<br>
 * <br>
 * 1 - A configura��o das constantes abaixo ir� gerar um deadlock expl�cito
 * visualmente, j� que o algoritmo criado n�o permite que a execu��o seja
 * interrompida: <br>
 * TEMPO_PEGAR_GARFO_SECUNDARIO = 1000 <br>
 * TEMPO_COMENDO = 0 <br>
 * TEMPO_PENSANDO = 0 <br>
 * <br>
 * 2 - O modificador synchronized foi utilizado nos m�todos que acessam recursos
 * compartilhados na classe {@link Garfo} (No nosso caso o recurso compartilhado
 * � o pr�prio {@link Garfo}) , isso evita que duas threads ({@link Filosofo})
 * os acesse ao mesmo tempo.<br>
 * <br>
 * 3 - A configura��o das constantes abaixo deixa ainda mais expl�cita a solu��o
 * do desafio de fazer um fil�sofo comer a cada 5 segundos e a l�gica
 * anti-deadlock, esses problemas foram solucionados com o aux�lio do m�todo
 * sleep: <br>
 * TEMPO_PEGAR_GARFO_SECUNDARIO = 100 <br>
 * TEMPO_COMENDO = 50 <br>
 * TEMPO_PENSANDO = 150
 */
public class Filosofo extends Thread {

	/**
	 * Constante com o tempo que ser� utilizado pelo fil�sofo na tentativa de
	 * pegar o segundo garfo.
	 */
	private static final int TEMPO_PEGAR_GARFO_SECUNDARIO = 500;
	/**
	 * Constante com o tempo que o fil�sofo gastar� comendo.
	 */
	private static final int TEMPO_COMENDO = 250;
	/**
	 * Constante com o tempo que o fil�sofo gastar� pensando.
	 */
	private static final int TEMPO_PENSANDO = 750;

	private Garfo garfoEsquerdo;
	private Garfo garfoDireito;
	private JFilosofo filosofo;
	private JTextArea txtLog;

	/**
	 * Construtor da classe Filosofo. Recebe alguns par�metros para configura��o
	 * da thread.
	 * 
	 * @param garfoEsquerdo
	 *            {@link Garfo} - Recurso alocado na "m�o" esquerda da thread.
	 * @param garfoDireito
	 *            {@link Garfo} - Recurso alocado na "m�o" direita da thread.
	 * @param filosofo
	 *            {@link JFilosofo} - Componente com a imagem.
	 * @param txtLog
	 *            {@link JTextArea} - Componente de texto que exibe as a��es do
	 *            sistema, no caso a a��o da thread.
	 */
	public Filosofo(Garfo garfoDireito, Garfo garfoEsquerdo,
			JFilosofo filosofo, JTextArea txtLog) {
		this.garfoEsquerdo = garfoEsquerdo;
		this.garfoDireito = garfoDireito;
		this.filosofo = filosofo;
		this.txtLog = txtLog;
	}

	/**
	 * M�todo acessor da propriedade garfoEsquerdo.
	 * 
	 * @return {@link Garfo} - "M�o" esquerda.
	 */
	public Garfo getGarfoEsquerdo() {
		return garfoEsquerdo;
	}

	/**
	 * M�todo acessor da propriedade garfoDireito.
	 * 
	 * @return {@link Garfo} - "M�o" direita.
	 */
	public Garfo getGarfoDireito() {
		return garfoDireito;
	}

	/**
	 * Este m�todo define o estado da thread com o par�metro e invoca a
	 * altera��o da imagem e a troca do texto indicando o estado em quest�o.
	 * 
	 * @param estado
	 *            {@link Estado} - Indentifica o Estado que ser� utilizado.
	 */
	public void alteraEstado(Estado estado) {
		if (this.filosofo != null) {
			this.filosofo.setEstado(estado);
			this.filosofo.repaint();
		}
		estado.exibeEstado(this.filosofo.getNome().trim(), this.txtLog);
	}

	/**
	 * Quando a thread � inicializada, este m�todo � chamado. � ele que vai
	 * "gerenciar" a atividade da thread determinando qual a��o ela dever�
	 * tentar executar e qual o intervalo que ela ficar� em cado estado.
	 */
	@Override
	public void run() {
		try {
			// Executa de forma infinita o conte�do do la�o.
			while (true) {
				/*
				 * Exibe o nome da thread e indica que uma nova execu��o foi
				 * disparada no componente de log.
				 */
				this.txtLog.append(String.format(
						" %s - Nova itera��o disparada! \n", this.filosofo
								.getNome().trim()));
				/*
				 * Define o estado como pensando e aciona o m�todo sleep, que
				 * far� o fil�sofo pausar pela quantidade de segundos
				 * determinada na constante TEMPO_PENSANDO.
				 */
				alteraEstado(Estado.PENSANDO);
				sleep(TEMPO_PENSANDO);
				/*
				 * Seta o estado para faminto sem garfo, depois entra em um la�o
				 * infinito, que s� ser� interrompido quando o fil�sofo pegar os
				 * dois garfos, para assim conseguir comer.
				 */
				alteraEstado(Estado.FAMINTO_SEM_GARFOS);
				while (true) {
					/*
					 * Chama o m�todo pegaGarfo que n�o cont�m parametro, ele
					 * tentar� pegar o garfo at� conseguir, independente do
					 * tempo que isso leve.
					 */
					this.getGarfoDireito().pegaGarfo();
					// Define o estado como faminto com garfo
					alteraEstado(Estado.FAMINTO_COM_GARFO);
					/*
					 * Chama o m�todo pegaGarfo que cont�m parametro, ele
					 * tentar� pegar o garfo pelo tempo da constante
					 * TEMPO_PEGAR_GARFO_SECUNDARIO, caso consiga retorna
					 * verdadeiro, o que interromper� o la�o de repeti��o com o
					 * comando break.
					 */
					if (this.getGarfoEsquerdo().pegaGarfo(
							TEMPO_PEGAR_GARFO_SECUNDARIO)) {
						break;
					} else {
						/*
						 * Caso contr�rio, o fil�sofo soltar� o garfo que est�
						 * segurando na m�o direita usando o m�todo soltaGarfo,
						 * que notifica os outros fil�sofos sobre a
						 * disponibilidade do garfo.
						 */
						this.getGarfoDireito().soltaGarfo();
						// Define o estado como faminto sem garfos com garfo
						alteraEstado(Estado.FAMINTO_SEM_GARFOS);
						/*
						 * Adiciona uma notifica��o no componente de log sobre a
						 * n�o ocorr�ncia do deadlock.
						 */
						this.txtLog
								.append(String
										.format(" %s evitou um deadlock soltando seu garfo! \n",
												this.filosofo.getNome().trim()));
					}
				}
				/*
				 * Define o estado como comendo e aciona o m�todo sleep, que
				 * far� o fil�sofo pausar pela quantidade de segundos
				 * determinada na constante TEMPO_COMENDO.
				 */
				alteraEstado(Estado.COMENDO);
				sleep(TEMPO_COMENDO);
				/*
				 * Libera os dois garfos usando o m�todo soltaGarfo, ele
				 * notifica os outros fil�sofos sobre a disponibilidade dos
				 * garfos.
				 */
				this.getGarfoDireito().soltaGarfo();
				this.getGarfoEsquerdo().soltaGarfo();
			}
			/*
			 * Um bloco TRY/CATCH teve de ser feito porque o m�todo sleep pode
			 * lan�ar uma exce��o do tipo InterruptedException.
			 */
		} catch (InterruptedException excecao) {
			excecao.printStackTrace();
		}
	}
}
