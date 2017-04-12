package br.com.uniara.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import br.com.uniara.constante.Estado;
import br.com.uniara.entidade.Filosofo;
import br.com.uniara.entidade.Garfo;
import br.com.uniara.ui.componente.JFilosofo;
import br.com.uniara.ui.componente.JGarfo;

/**
 * Esta classe representa a janela do sistema. � nela que s�o criados e
 * configurados os componentes.
 */
public class JPrincipal extends JFrame {

	private static final long serialVersionUID = 3L;
	private static final String TEXTO_INICIAL_BOTAO = "Jantar!";

	private JFilosofo jFilosofo1;
	private JFilosofo jFilosofo2;
	private JFilosofo jFilosofo3;
	private JFilosofo jFilosofo4;
	private JFilosofo jFilosofo5;

	private JGarfo jGarfoA;
	private JGarfo jGarfoB;
	private JGarfo jGarfoC;
	private JGarfo jGarfoD;
	private JGarfo jGarfoE;

	private JTextArea txtLog;

	private List<Filosofo> filosofos;

	private boolean jantarIniciado = false;

	/**
	 * O construtor da classe, faz uma configura��o inicial da tela e chama os
	 * m�todos de configura��o dos componentes da tela.
	 */
	public JPrincipal() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setTitle("Sistemas de Tempo Real - Jantar dos Fil�sofos");

		this.montaGarfos();
		this.montaFilosofos();
		this.criaTextArea();
	}

	/**
	 * M�todo acessor que retorna uma lista de Filosofos.
	 * 
	 * @return {@link List}<{@link Filosofo}> - Lista com os Fil�sofos criados
	 *         no m�todo criaThreads().
	 */
	public List<Filosofo> getFilosofos() {
		if (filosofos == null) {
			this.criaThreads();
		}
		return filosofos;
	}

	/**
	 * M�todo acessor da propriedade de controle booleana jantarIniciado.
	 * 
	 * @return boolean - Caso o bot�o "Jantar!" j� tiver sido clicado retorna
	 *         true.
	 */
	public boolean isJantarIniciado() {
		return jantarIniciado;
	}

	/**
	 * Cria os componentes com as imagens que representam os fil�sofos.
	 */
	private void criaJFilosofos() {
		this.jFilosofo1 = new JFilosofo(Estado.PENSANDO, "  S�crates ");
		this.jFilosofo2 = new JFilosofo(Estado.PENSANDO, "   Plat�o  ");
		this.jFilosofo3 = new JFilosofo(Estado.PENSANDO, "Arist�teles");
		this.jFilosofo4 = new JFilosofo(Estado.PENSANDO, "  Ptolomeu ");
		this.jFilosofo5 = new JFilosofo(Estado.PENSANDO, " Pit�goras ");
	}

	/**
	 * Cria os componentes com as imagens que representam os garfos.
	 */
	private void criaJGarfos() {
		this.jGarfoA = new JGarfo();
		this.jGarfoB = new JGarfo();
		this.jGarfoC = new JGarfo();
		this.jGarfoD = new JGarfo();
		this.jGarfoE = new JGarfo();
	}

	/**
	 * Cria o componente de texto que ser� utilizado para exibir o log.
	 */
	private void criaTextArea() {
		this.txtLog = new JTextArea();
		this.txtLog.setBounds(455, 15, 252, 420);
		this.txtLog.setEditable(false);
		JScrollPane rolagem = new JScrollPane(this.txtLog);
		DefaultCaret caretRolagem = (DefaultCaret) this.txtLog.getCaret();
		caretRolagem.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		rolagem.setBounds(455, 15, 252, 420);
		rolagem.setAutoscrolls(true);
		this.add(rolagem);
	}

	/**
	 * Este m�todo cria os recursos (garfos) e os associa com suas respectivas
	 * threads (fil�sofos). Posteriormente, os fil�sofos s�o adicionados em uma
	 * lista para facilitar a inicializa��o das threads.
	 */
	private void criaThreads() {
		filosofos = new ArrayList<Filosofo>(5);

		// Cria��o dos recursos:
		Garfo garfoA = new Garfo(this.jGarfoA);
		Garfo garfoB = new Garfo(this.jGarfoB);
		Garfo garfoC = new Garfo(this.jGarfoC);
		Garfo garfoD = new Garfo(this.jGarfoD);
		Garfo garfoE = new Garfo(this.jGarfoE);

		// Cria��o das threads:
		Filosofo filosofo1 = new Filosofo(garfoE, garfoA, this.jFilosofo1,
				this.txtLog);
		Filosofo filosofo2 = new Filosofo(garfoA, garfoB, this.jFilosofo2,
				this.txtLog);
		Filosofo filosofo3 = new Filosofo(garfoB, garfoC, this.jFilosofo3,
				this.txtLog);
		Filosofo filosofo4 = new Filosofo(garfoC, garfoD, this.jFilosofo4,
				this.txtLog);
		Filosofo filosofo5 = new Filosofo(garfoD, garfoE, this.jFilosofo5,
				this.txtLog);

		// Adic�o dos Filosofos na lista:
		filosofos.add(filosofo1);
		filosofos.add(filosofo2);
		filosofos.add(filosofo3);
		filosofos.add(filosofo4);
		filosofos.add(filosofo5);
	}

	/**
	 * Este m�todo configura a disposi��o dos Fil�sofos e adiciona-os na tela.
	 */
	public void montaFilosofos() {
		this.criaJFilosofos();

		int larguraFilosofo = 75;
		int alturaFilosofo = 100;

		int espacoX = 20;
		int espacoY = 10;

		this.jFilosofo1.setBounds(espacoX + 175, espacoY + 0, larguraFilosofo,
				alturaFilosofo);
		this.add(jFilosofo1);

		this.jFilosofo2.setBounds(espacoX + 350, espacoY + 120,
				larguraFilosofo, alturaFilosofo);
		this.add(jFilosofo2);

		this.jFilosofo3.setBounds(espacoX + 283, espacoY + 316,
				larguraFilosofo, alturaFilosofo);
		this.add(jFilosofo3);

		this.jFilosofo4.setBounds(espacoX + 66, espacoY + 316, larguraFilosofo,
				alturaFilosofo);
		this.add(jFilosofo4);

		this.jFilosofo5.setBounds(espacoX + 0, espacoY + 120, larguraFilosofo,
				alturaFilosofo);
		this.add(jFilosofo5);
	}

	/**
	 * Este m�todo configura a disposi��o dos Garfos e adiciona-os na tela.
	 */
	public void montaGarfos() {
		this.criaJGarfos();

		int larguraGarfo = 70;
		int alturaGarfo = 60;

		int espacoX = 20;
		int espacoY = 10;

		this.jGarfoA.setBounds(espacoX + 283, espacoY + 33, larguraGarfo,
				alturaGarfo);
		this.add(jGarfoA);

		this.jGarfoB.setBounds(espacoX + 350, espacoY + 229, larguraGarfo,
				alturaGarfo);
		this.add(jGarfoB);

		this.jGarfoC.setBounds(espacoX + 175, espacoY + 350, larguraGarfo,
				alturaGarfo);
		this.add(jGarfoC);

		this.jGarfoD.setBounds(espacoX + 0, espacoY + 229, larguraGarfo,
				alturaGarfo);
		this.add(jGarfoD);

		this.jGarfoE.setBounds(espacoX + 66, espacoY + 33, larguraGarfo,
				alturaGarfo);
		this.add(jGarfoE);
	}

	/**
	 * Este m�todo percorre a lista que cont�m as threads e as inicia.
	 */
	public void iniciaThreads() {
		for (Filosofo filosofo : getFilosofos()) {
			filosofo.start();
		}
		this.jantarIniciado = true;
	}

	/**
	 * Este m�todo percorre a lista que cont�m as threads e as reinicia.<br/>
	 * � um m�odo depreciado, ou seja, pode deixar de existir na proxima vers�o
	 * do programa.
	 */
	@Deprecated
	public void reiniciaThreads() {
		for (Filosofo filosofo : getFilosofos()) {
			filosofo.resume();
		}
	}

	/**
	 * Este m�todo percorre a lista que cont�m as threads e as pausa.<br/>
	 * � um m�odo depreciado, ou seja, pode deixar de existir na proxima vers�o
	 * do programa.
	 */
	@Deprecated
	public void pausaThreads() {
		for (Filosofo filosofo : getFilosofos()) {
			filosofo.suspend();
		}
	}

	/**
	 * M�todo principal da classe, � ele que � chamado quando roda o sistema.
	 * Neste m�todo � onde a tela � criada.
	 */
	public static void main(String[] args) {
		final JPrincipal pnlPrincipal = new JPrincipal();
		pnlPrincipal.setSize(725, 480);
		pnlPrincipal.setVisible(true);
		pnlPrincipal.setLocationRelativeTo(null);

		final JButton btnJantarPausar = new JButton(TEXTO_INICIAL_BOTAO);
		btnJantarPausar.setBounds(180, 205, 100, 35);

		btnJantarPausar.addActionListener(new ActionListener() {
			/**
			 * Esse evento ser� executado toda a vez que o usu�rio clicar no
			 * bot�o da tela principal.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isJantar = btnJantarPausar.getText().equals(
						TEXTO_INICIAL_BOTAO);
				// A l�gica abaixo contrala o inicio, pausa e reinicio das
				// threads.
				if (isJantar) {
					if (pnlPrincipal.isJantarIniciado()) {
						pnlPrincipal.reiniciaThreads();
					} else {
						pnlPrincipal.iniciaThreads();
					}
				} else {
					pnlPrincipal.pausaThreads();
				}
				btnJantarPausar.setText(isJantar ? "Pausar"
						: TEXTO_INICIAL_BOTAO);
			}
		});

		pnlPrincipal.add(btnJantarPausar);
	}

}
