package br.com.uniara.ui.componente;

import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Classe que representa o painel onde as imagens dos garfos serão manipuladas e
 * desenhadas.<br/>
 * Extende de {@link JPanel} o que a torna um painel da biblioteca Swing.
 */
public class JGarfo extends JPanel {

	private static final long serialVersionUID = 2L;
	private boolean ocupado = false;

	/**
	 * Método que retorna o caminho da imagem de acordo com a disponibilidade do
	 * garfo, disponibilidade essa determinada pela propriedade ocupado.
	 * 
	 * @return caminho absoluto da imagem.
	 */
	private String getCaminhoImagem() {
		return this.ocupado ? "imagens/prato.png" : "imagens/prato-garfo.png";
	}

	/**
	 * Método acessor que recupera o valor da propriedade ocupado.
	 */
	public boolean isOcupado() {
		return ocupado;
	}

	/**
	 * Método acessor que modifica o valor da propriedade ocupado.
	 * 
	 * @param ocupado
	 *            indica a disponibilidade do garfo.
	 */
	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}

	/**
	 * Sobreescrita do método que desenha o componente. Nele foi especializada a
	 * maneira de desenhar o componente.
	 */
	@Override
	protected void paintComponent(Graphics grafico) {
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			URL res = classLoader.getResource(this.getCaminhoImagem());
			grafico.clearRect(0, 0, 70, 60);
			ImageIcon icon = new ImageIcon(res);
			grafico.drawImage(icon.getImage(), 15, 20, 50, 31, null);
		} catch (Exception execao) {
			System.err.println(execao);
		}
	}

}
