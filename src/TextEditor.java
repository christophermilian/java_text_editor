import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import java.io.File;

import java.util.Scanner;
import java.util.Formatter;
import javax.swing.ScrollPaneConstants;

public class TextEditor {

	private final String TITLE = "Text Editor (Java)";

	private JFrame frame;
	private JTextArea textArea;
	private File openFile;
	private int input;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {

					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					TextEditor window = new TextEditor();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TextEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setTitle("Untitled - " + TITLE);
		frame.setBounds(100, 100, 943, 671);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 13));
		textArea.setBackground(Color.WHITE);
		frame.getContentPane().add(textArea, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnNewMenu = new JMenu("File");
		mnNewMenu.setBackground(Color.WHITE);
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Open");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				open();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save As");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		});

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Save");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Close File");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFile();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_3);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Exit");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_4);

	}

	private void open() {
		try {

			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Select a text file to open");
			chooser.showOpenDialog(null);

			openFile = chooser.getSelectedFile();
			if (openFile != null && !openFile.exists()) {
				JOptionPane.showMessageDialog(null, "Failed to open file. File does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
				openFile = null;
				return;
			}

			Scanner reader = new Scanner(openFile);
			String contents = "";
			while (reader.hasNextLine()) {
				contents += reader.nextLine() + "\n";
			}
			reader.close();
			textArea.setText(contents);

			frame.setTitle(openFile.getName() + " - " + TITLE);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveAs() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Save as");
			chooser.showSaveDialog(null);

			openFile = chooser.getSelectedFile();

			save();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void save() {
		try {

			if (openFile == null) {
				JOptionPane.showMessageDialog(null, "Failed to save file. No file is selected.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String contents = textArea.getText();
			Formatter form = new Formatter(openFile);
			form.format("%s", contents);
			form.close();

			frame.setTitle(openFile.getName() + " - " + TITLE);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeFile() {
		if (openFile == null) {
			JOptionPane.showMessageDialog(null, "Failed to close file. No file is selected.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {

			input = JOptionPane.showConfirmDialog(null, "Do you want to save changes?", "Wait!", JOptionPane.YES_NO_OPTION);

			if (input == JOptionPane.YES_OPTION) {
				save();
			}

			textArea.setText("");
			openFile = null;
			frame.setTitle("Untitled - " + TITLE);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void exit() {

		if (openFile != null) {
			input = JOptionPane.showConfirmDialog(null, "Do you want to save changes?", "Wait!", JOptionPane.YES_NO_OPTION);
				
			if (input == JOptionPane.YES_OPTION) {
				save();
				}
			}else {
				System.exit(0);
			}
		} 
	}

