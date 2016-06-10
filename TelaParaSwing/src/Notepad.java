
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Notepad extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private TextArea textArea = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	private MenuBar menuBar = new MenuBar();
	private Menu file = new Menu();

	private MenuItem openFile = new MenuItem();
	private MenuItem saveFile = new MenuItem();
	private MenuItem close = new MenuItem();

	public Notepad() {
		this.setSize(500, 300);
		this.setTitle("GlobalEditor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.textArea.setFont(new Font("Century Gothic", Font.BOLD, 12));

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(textArea);

		this.setMenuBar(this.menuBar);
		this.menuBar.add(this.file);

		this.file.setLabel("File");

		this.openFile.setLabel("Open");
		this.openFile.addActionListener(this);
		this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
		this.file.add(this.openFile);

		this.saveFile.setLabel("Save");
		this.saveFile.addActionListener(this);
		this.saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
		this.file.add(this.saveFile);

		this.close.setLabel("Close");

		this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4, false));
		this.close.addActionListener(this);
		this.file.add(this.close);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.close)
			this.dispose();

		else if (e.getSource() == this.openFile) {
			JFileChooser open = new JFileChooser();
			int option = open.showOpenDialog(this);

			if (option == JFileChooser.APPROVE_OPTION) {
				this.textArea.setText("");
				try {

					@SuppressWarnings("resource")
					Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
					while (scan.hasNext())
						this.textArea.append(scan.nextLine() + "\n");
				} catch (Exception ex) {

					System.out.println(ex.getMessage());
				}
			}
		}

		else if (e.getSource() == this.saveFile) {
			JFileChooser save = new JFileChooser();
			int option = save.showSaveDialog(this);

			if (option == JFileChooser.APPROVE_OPTION) {
				try {

					BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
					out.write(this.textArea.getText());
					out.close();
				} catch (Exception ex) {

					System.out.println(ex.getMessage());
				}
			}
		}
	}

	public static void main(String args[]) {
		Notepad app = new Notepad();
		app.setVisible(true);

	}

}
