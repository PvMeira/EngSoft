package frames;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class Principal {

	private JFrame janela;

	private JTextArea areaTexto;

	private File arquivoEscolhido;


	private String textoArquivo = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.janela.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
	}

	private boolean salvaTextoNoArquivo(File arquivo, String texto) {
		if (arquivo != null) {
			try {
				FileWriter gravador = new FileWriter(arquivo, false);
				gravador.write(texto);
				gravador.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public String pegaTextoArquivo(File arquivo) {
		if (arquivo != null && arquivo.exists()) {
			try {
				FileReader leitorArquivo = new FileReader(arquivo);
				BufferedReader leitorBuferizado = new BufferedReader(leitorArquivo);
				String textoArquivo = "";
				String linhaArquivo = null;
				while ((linhaArquivo = leitorBuferizado.readLine()) != null) {
					textoArquivo += linhaArquivo + "\n";
				}
				leitorBuferizado.close();
				return textoArquivo;
			} catch (Exception e) {
				System.out.println("Arquivo nao pode ser lido!");
				e.printStackTrace();
			}
		}
		return "";
	}

	public void abrirArquivo() {
		JFileChooser janelaArquivos = new JFileChooser();
		if (janelaArquivos.showOpenDialog(janela) == JFileChooser.APPROVE_OPTION) {
			arquivoEscolhido = janelaArquivos.getSelectedFile();
			textoArquivo = pegaTextoArquivo(arquivoEscolhido);
			areaTexto.setText(textoArquivo);
			String nomeArquivo = arquivoEscolhido.getName();
			janela.setTitle("Editor - " + nomeArquivo);
			areaTexto.setCaretPosition(0);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		janela = new JFrame();
		janela.setTitle("Editor");
		janela.setBounds(100, 100, 450, 300);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));

		JScrollPane areaRolagem = new JScrollPane();
		janela.getContentPane().add(areaRolagem, "cell 0 0,grow");

		areaTexto = new JTextArea();
		areaTexto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String textoAtualArquivo = areaTexto.getText();
				/**
				 * Se o texto original do arquivo foi alterado, coloca um
				 * asterisco no final do título da janela.
				 */
				if (!textoAtualArquivo.equals(textoArquivo)) {
					String titulo = janela.getTitle();
					titulo = titulo.replace(" * ", "");
					titulo = titulo + " * ";
					janela.setTitle(titulo);
				} else { // Se o texto está igual ao do original, retira o
							// asterisco
					String titulo = janela.getTitle();
					titulo = titulo.replace(" * ", "");
					janela.setTitle(titulo);
				}
			}
		});
		areaRolagem.setViewportView(areaTexto);

		JMenuBar barraMenus = new JMenuBar();
		janela.setJMenuBar(barraMenus);

		JMenu menuArquivo = new JMenu("Arquivo");
		barraMenus.add(menuArquivo);

		JMenuItem opcaoAbrir = new JMenuItem("Abrir...");
		opcaoAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirArquivo();
			}
		});
		opcaoAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		menuArquivo.add(opcaoAbrir);

		JMenuItem opcaoSalvar = new JMenuItem("Salvar");
		opcaoSalvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		opcaoSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!salvaTextoNoArquivo(arquivoEscolhido, areaTexto.getText())) {
					JOptionPane.showMessageDialog(janela,
							"O arquivo '" + arquivoEscolhido.getName() + "' não pôde ser salvo.", "Erro",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String titulo = janela.getTitle();
					titulo = titulo.replace(" * ", "");
					janela.setTitle(titulo);
				}
			}
		});
		menuArquivo.add(opcaoSalvar);

		JMenuItem mntmFechar = new JMenuItem("Fechar");
		mntmFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janela.setVisible(false);
				janela.dispose();
				System.exit(0); // Mata o processo
			}
		});
		mntmFechar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		menuArquivo.add(mntmFechar);
	}

}