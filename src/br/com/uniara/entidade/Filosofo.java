package br.com.uniara.entidade;

import javax.swing.JTextArea;

import br.com.uniara.constante.Estado;
import br.com.uniara.ui.componente.JFilosofo;

/**
 * Essa classe representa um filósofo. Ela herda da classe Thread, portanto se
 * comportará como a mesma.<br>
 * <br>
 * 1 - A configuração das constantes abaixo irá gerar um deadlock explícito
 * visualmente, já que o algoritmo criado não permite que a execução seja
 * interrompida: <br>
 * TEMPO_PEGAR_GARFO_SECUNDARIO = 1000 <br>
 * TEMPO_COMENDO = 0 <br>
 * TEMPO_PENSANDO = 0 <br>
 * <br>
 * 2 - O modificador synchronized foi utilizado nos métodos que acessam recursos
 * compartilhados na classe {@link Garfo} (No nosso caso o recurso compartilhado
 * é o próprio {@link Garfo}) , isso evita que duas threads ({@link Filosofo})
 * os acesse ao mesmo tempo.<br>
 * <br>
 * 3 - A configuração das constantes abaixo deixa ainda mais explícita a solução
 * do desafio de fazer um filósofo comer a cada 5 segundos e a lógica
 * anti-deadlock, esses problemas foram solucionados com o auxílio do método
 * sleep: <br>
 * TEMPO_PEGAR_GARFO_SECUNDARIO = 100 <br>
 * TEMPO_COMENDO = 50 <br>
 * TEMPO_PENSANDO = 150
 */
public class Filosofo extends Thread {

	/**
	 * Constante com o tempo que será utilizado pelo filósofo na tentativa de
	 * pegar o segundo garfo.
	 */
	private static final int TEMPO_PEGAR_GARFO_SECUNDARIO = 500;
	/**
	 * Constante com o tempo que o filósofo gastará comendo.
	 */
	private static final int TEMPO_COMENDO = 250;
	/**
	 * Constante com o tempo que o filósofo gastará pensando.
	 */
	private static final int TEMPO_PENSANDO = 750;

	private Garfo garfoEsquerdo;
	private Garfo garfoDireito;
	private JFilosofo filosofo;
	private JTextArea txtLog;

	/**
	 * Construtor da classe Filosofo. Recebe alguns parâmetros para configuração
	 * da thread.
	 * 
	 * @param garfoEsquerdo
	 *            {@link Garfo} - Recurso alocado na "mão" esquerda da thread.
	 * @param garfoDireito
	 *            {@link Garfo} - Recurso alocado na "mão" direita da thread.
	 * @param filosofo
	 *            {@link JFilosofo} - Componente com a imagem.
	 * @param txtLog
	 *            {@link JTextArea} - Componente de texto que exibe as ações do
	 *            sistema, no caso a ação da thread.
	 */
	public Filosofo(Garfo garfoDireito, Garfo garfoEsquerdo,
			JFilosofo filosofo, JTextArea txtLog) {
		this.garfoEsquerdo = garfoEsquerdo;
		this.garfoDireito = garfoDireito;
		this.filosofo = filosofo;
		this.txtLog = txtLog;
	}

	/**
	 * Método acessor da propriedade garfoEsquerdo.
	 * 
	 * @return {@link Garfo} - "Mão" esquerda.
	 */
	public Garfo getGarfoEsquerdo() {
		return garfoEsquerdo;
	}

	/**
	 * Método acessor da propriedade garfoDireito.
	 * 
	 * @return {@link Garfo} - "Mão" direita.
	 */
	public Garfo getGarfoDireito() {
		return garfoDireito;
	}

	/**
	 * Este método define o estado da thread com o parâmetro e invoca a
	 * alteração da imagem e a troca do texto indicando o estado em questão.
	 * 
	 * @param estado
	 *            {@link Estado} - Indentifica o Estado que será utilizado.
	 */
	public void alteraEstado(Estado estado) {
		if (this.filosofo != null) {
			this.filosofo.setEstado(estado);
			this.filosofo.repaint();
		}
		estado.exibeEstado(this.filosofo.getNome().trim(), this.txtLog);
	}

	/**
	 * Quando a thread é inicializada, este método é chamado. É ele que vai
	 * "gerenciar" a atividade da thread determinando qual ação ela deverá
	 * tentar executar e qual o intervalo que ela ficará em cado estado.
	 */
	@Override
	public void run() {
		try {
			// Executa de forma infinita o conteúdo do laço.
			while (true) {
				/*
				 * Exibe o nome da thread e indica que uma nova execução foi
				 * disparada no componente de log.
				 */
				this.txtLog.append(String.format(
						" %s - Nova iteração disparada! \n", this.filosofo
								.getNome().trim()));
				/*
				 * Define o estado como pensando e aciona o método sleep, que
				 * fará o filósofo pausar pela quantidade de segundos
				 * determinada na constante TEMPO_PENSANDO.
				 */
				alteraEstado(Estado.PENSANDO);
				sleep(TEMPO_PENSANDO);
				/*
				 * Seta o estado para faminto sem garfo, depois entra em um laço
				 * infinito, que só será interrompido quando o filósofo pegar os
				 * dois garfos, para assim conseguir comer.
				 */
				alteraEstado(Estado.FAMINTO_SEM_GARFOS);
				while (true) {
					/*
					 * Chama o método pegaGarfo que não contém parametro, ele
					 * tentará pegar o garfo até conseguir, independente do
					 * tempo que isso leve.
					 */
					this.getGarfoDireito().pegaGarfo();
					// Define o estado como faminto com garfo
					alteraEstado(Estado.FAMINTO_COM_GARFO);
					/*
					 * Chama o método pegaGarfo que contém parametro, ele
					 * tentará pegar o garfo pelo tempo da constante
					 * TEMPO_PEGAR_GARFO_SECUNDARIO, caso consiga retorna
					 * verdadeiro, o que interromperá o laço de repetição com o
					 * comando break.
					 */
					if (this.getGarfoEsquerdo().pegaGarfo(
							TEMPO_PEGAR_GARFO_SECUNDARIO)) {
						break;
					} else {
						/*
						 * Caso contrário, o filósofo soltará o garfo que está
						 * segurando na mão direita usando o método soltaGarfo,
						 * que notifica os outros filósofos sobre a
						 * disponibilidade do garfo.
						 */
						this.getGarfoDireito().soltaGarfo();
						// Define o estado como faminto sem garfos com garfo
						alteraEstado(Estado.FAMINTO_SEM_GARFOS);
						/*
						 * Adiciona uma notificação no componente de log sobre a
						 * não ocorrência do deadlock.
						 */
						this.txtLog
								.append(String
										.format(" %s evitou um deadlock soltando seu garfo! \n",
												this.filosofo.getNome().trim()));
					}
				}
				/*
				 * Define o estado como comendo e aciona o método sleep, que
				 * fará o filósofo pausar pela quantidade de segundos
				 * determinada na constante TEMPO_COMENDO.
				 */
				alteraEstado(Estado.COMENDO);
				sleep(TEMPO_COMENDO);
				/*
				 * Libera os dois garfos usando o método soltaGarfo, ele
				 * notifica os outros filósofos sobre a disponibilidade dos
				 * garfos.
				 */
				this.getGarfoDireito().soltaGarfo();
				this.getGarfoEsquerdo().soltaGarfo();
			}
			/*
			 * Um bloco TRY/CATCH teve de ser feito porque o método sleep pode
			 * lançar uma exceção do tipo InterruptedException.
			 */
		} catch (InterruptedException excecao) {
			excecao.printStackTrace();
		}
	}
}
