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

	private JFrame window;

	private JTextArea textArea;

	private File chosenFIle;

	private String textFile = "";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Principal() {
		initialize();
	}

	private boolean saveTextOnFile(File file, String text) {
		if (file != null) {
			try {
				FileWriter gravador = new FileWriter(file, false);
				gravador.write(text);
				gravador.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public String takeTextonFile(File file) {
		if (file != null && file.exists()) {
			try {
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferReader = new BufferedReader(fileReader);
				String textFile = "";
				String fileLine = null;
				while ((fileLine = bufferReader.readLine()) != null) {
					textFile += fileLine + "\n";
				}
				bufferReader.close();
				return textFile;
			} catch (Exception e) {
				System.out.println("The File cannot be read !");
				e.printStackTrace();
			}
		}
		return "";
	}

	public void openFile() {
		JFileChooser fileWindows = new JFileChooser();
		if (fileWindows.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
			chosenFIle = fileWindows.getSelectedFile();
			textFile = takeTextonFile(chosenFIle);
			textArea.setText(textFile);
			String fileName = chosenFIle.getName();
			window.setTitle("Text Editor - " + fileName);
			textArea.setCaretPosition(0);
		}
	}

	private void initialize() {
		window = new JFrame();
		window.setTitle("Text");
		window.setBounds(100, 100, 450, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));

		JScrollPane scrollRoll = new JScrollPane();
		window.getContentPane().add(scrollRoll, "cell 0 0,grow");

		textArea = new JTextArea();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String fileActualtext = textArea.getText();

				if (!fileActualtext.equals(textFile)) {
					String text = window.getTitle();
					text = text.replace(" * ", "");
					text = text + " * ";
					window.setTitle(text);
				} else {
					String titulo = window.getTitle();
					titulo = titulo.replace(" * ", "");
					window.setTitle(titulo);
				}
			}
		});
		scrollRoll.setViewportView(textArea);

		JMenuBar menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);

		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);

		JMenuItem openOption = new JMenuItem("Open...");
		openOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		openOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		menuFile.add(openOption);

		JMenuItem saveOptiopns = new JMenuItem("Save");
		saveOptiopns.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		saveOptiopns.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!saveTextOnFile(chosenFIle, textArea.getText())) {
					JOptionPane.showMessageDialog(window,
							"The file chosen '" + chosenFIle.getName() + "' cannot be save .", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String title = window.getTitle();
					title = title.replace(" * ", "");
					window.setTitle(title);
				}
			}
		});
		menuFile.add(saveOptiopns);

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				window.setVisible(false);
				window.dispose();
				System.exit(0);
			}
		});
		mntmClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		menuFile.add(mntmClose);
	}

}