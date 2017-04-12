package br.com.uniara.ui.componente;

import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import br.com.uniara.constante.Estado;

/**
 * Classe que representa o painel onde as imagens dos fil�sofos ser�o
 * manipuladas e desenhadas.<br/>
 * Extende de {@link JPanel} o que a torna um painel da biblioteca Swing.
 */
public class JFilosofo extends JPanel {

	private static final long serialVersionUID = 1L;
	private String nome;
	private Estado estado;

	/**
	 * M�todo que retorna o caminho da imagem de acordo com o estado do
	 * fil�sofo.
	 * 
	 * @return {@link String} - Caminho absoluto da imagem.
	 */
	private String getCaminhoImagem() {
		String retorno = "";
		switch (this.getEstado()) {
		case COMENDO:
			retorno = "imagens/filosofo-comendo.png";
			break;
		case FAMINTO_COM_GARFO:
			retorno = "imagens/filosofo-faminto-garfo.png";
			break;
		case FAMINTO_SEM_GARFOS:
			retorno = "imagens/filosofo-faminto.png";
			break;
		case PENSANDO:
		default:
			retorno = "imagens/filosofo-pensando.png";
			break;
		}
		return retorno;
	}

	/**
	 * M�todo acessor que recupera o nome do fil�sofo.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * M�todo acessor que recupera o Enum com o estado do fil�sofo.
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * M�todo acessor que modifica o Enum com o estado do fil�sofo.
	 * 
	 * @param estado
	 *            {@link Estado} - Enum que indica o estado atual do fil�sofo.
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	/**
	 * Construtor que recebe dois parametros e os atribui �s propriedades da
	 * classe.
	 * 
	 * @param estado
	 *            {@link Estado} - Par�metro que indica o estado do fil�sofo.
	 * @param nome
	 *            {@link String} - Identificador do fil�sofo.
	 */
	public JFilosofo(Estado estado, String nome) {
		this.estado = estado;
		this.nome = nome;
	}

	/**
	 * Sobreescrita do m�todo que desenha o componente. Nele foi especializada a
	 * maneira de desenhar o componente.
	 */
	@Override
	protected void paintComponent(Graphics grafico) {
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			URL caminhoImagem = classLoader
					.getResource(this.getCaminhoImagem());
			grafico.clearRect(0, 0, 75, 100);
			ImageIcon icon = new ImageIcon(caminhoImagem);
			grafico.drawImage(icon.getImage(), 0, 0, 70, 50, null);
			grafico.drawChars(this.getNome().toCharArray(), 0, this.getNome()
					.length(), 5, 70);
			String estado = this.getEstado().toString();
			grafico.drawChars(estado.toCharArray(), 0, estado.length(),
					estado.equals("FAMINTO") ? 10 : 3, 85);
		} catch (Exception execao) {
			System.err.println(execao);
		}
	}
}
