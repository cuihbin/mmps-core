package com.zzvc.mmps.gui.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.zzvc.mmps.gui.file.FileException;
import com.zzvc.mmps.gui.file.GuiFileFilter;

/**
 * Swing JFrame for GuiConsole
 * @author CHB
 *
 */
public class GuiConsoleFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_MAX_LOG_LINES = 200;

	private static final int CHECK_EXISTS = 1;
	private static final int CONFIRM_OVERWRITE = 2;

	private static final int DEFAULT_FRAME_WIDTH = 640;
	private static final int DEFAULT_FRAME_HEIGHT = 480;
	
	private final GuiConsole console;
	
	private int maxLogLines = DEFAULT_MAX_LOG_LINES;

	private int consoleFrameWidth = DEFAULT_FRAME_WIDTH;
	private int consoleFrameHeight = DEFAULT_FRAME_HEIGHT;
	
	private JPanel buttonPanel;
	private JTextPane logViewTextPane;
	private Document logViewDocument;
	private JScrollPane logViewScrollPane;
	private JLabel statusBarLabel;
	
	private SimpleAttributeSet traceLogMessageAttributeSet;
	private SimpleAttributeSet infoLogMessageAttributeSet;
	private SimpleAttributeSet warnLogMessageAttributeSet;
	private SimpleAttributeSet errorLogMessageAttributeSet;
	
	private JFileChooser fDialog = new JFileChooser();
	
	public GuiConsoleFrame(GuiConsole console) {
		this.console = console;
		
		setSize(consoleFrameWidth, consoleFrameHeight);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		contentPane.add(buttonPanel, BorderLayout.PAGE_START);
		
		logViewTextPane = new JTextPane() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean getScrollableTracksViewportWidth() {
				return getUI().getPreferredSize(this).width <= getParent().getSize().width;
			}
		};
		logViewTextPane.setEditable(false);
		logViewDocument = logViewTextPane.getDocument();
		logViewScrollPane = new JScrollPane(logViewTextPane,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		logViewScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
		contentPane.add(logViewScrollPane, BorderLayout.CENTER);
		
		statusBarLabel = new JLabel();
		statusBarLabel.setPreferredSize(new Dimension(0, 25));
		statusBarLabel.setBorder(BorderFactory.createLoweredBevelBorder());
		contentPane.add(statusBarLabel, BorderLayout.PAGE_END);
		
		traceLogMessageAttributeSet = new SimpleAttributeSet();
		StyleConstants.setFontSize(traceLogMessageAttributeSet, 12);
		StyleConstants.setForeground(traceLogMessageAttributeSet, Color.GREEN);
		
		infoLogMessageAttributeSet = new SimpleAttributeSet();
		StyleConstants.setFontSize(infoLogMessageAttributeSet, 12);
		
		warnLogMessageAttributeSet = new SimpleAttributeSet();
		StyleConstants.setFontSize(warnLogMessageAttributeSet, 12);
		StyleConstants.setForeground(warnLogMessageAttributeSet, Color.BLUE);
		
		errorLogMessageAttributeSet = new SimpleAttributeSet();
		StyleConstants.setFontSize(errorLogMessageAttributeSet, 12);
		StyleConstants.setForeground(errorLogMessageAttributeSet, Color.RED);
	}

	public void init() throws HeadlessException {
		for (GuiConsoleButton button : console.getButtons()) {
			addButton(button);
		}
		
		addButton(new GuiConsoleButton(console.findText("console.gui.button.exit")) {
			@Override
			public void buttonAction() {
				console.close();
			}
		});
		
		this.setVisible(true);
	}
	
	public File selectFileForRead(String fileExtension, String fileFilterDescription) {
		return selectFile(fileExtension, fileFilterDescription, CHECK_EXISTS);
	}
	
	public File selectFileForWrite(String fileExtension, String fileFilterDescription) {
		return selectFile(fileExtension, fileFilterDescription, CONFIRM_OVERWRITE);
	}
	
	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			console.close();
		}
	}
	
	private void addButton(final GuiConsoleButton guiButton) {
		JButton button = new JButton(guiButton.getLabel());
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				(new Thread() {
					@Override
					public void run() {
						guiButton.buttonAction();
					}
				}).start();
			}
		});
		buttonPanel.add(button);
	}

	private File selectFile(String fileExtension, String fileFilterDescription, int flag) {
		fDialog.resetChoosableFileFilters();
		fDialog.addChoosableFileFilter(new GuiFileFilter(fileExtension, fileFilterDescription));
		int result = fDialog.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fDialog.getSelectedFile();
			if ((flag & CHECK_EXISTS) > 0 && !file.exists()) {
				JOptionPane.showMessageDialog(null, console.findText("console.gui.file.notexists", file.getName()));
				throw new FileException("File not exists");
			}
			if ((flag & CONFIRM_OVERWRITE) > 0 && file.exists()) {
				if (JOptionPane.showConfirmDialog(null, console.findText("console.gui.file.confirmoverwrite", file.getName()), getTitle(), JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
					throw new FileException("File already exists");
				}
			}
			return file;
		} else {
			throw new FileException("User canceled file selection");
		}
	}
	
	protected void addTrace(final String text) {
		invokeUpdateLogView(text, traceLogMessageAttributeSet);
	}
	
	protected void addInfo(final String text) {
		invokeUpdateLogView(text, infoLogMessageAttributeSet);
	}
	
	protected void addWarn(final String text) {
		invokeUpdateLogView(text, warnLogMessageAttributeSet);
	}
	
	protected void addError(final String text) {
		invokeUpdateLogView(text, errorLogMessageAttributeSet);
	}
	
	protected void setStatus(final String text) {
		statusBarLabel.setText(text);
	}
	
	protected void invokeUpdateLogView(final String logMessage, final SimpleAttributeSet logMessageAttributeSet) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					try {
						updateLog(logViewDocument, logMessage, logMessageAttributeSet);
					} catch (BadLocationException e) {
					}
					logViewTextPane.setCaretPosition(logViewDocument.getLength());
					logViewScrollPane.repaint();
				}
			});
		} catch (Exception ex) {
		}
	}
	
	synchronized private void updateLog(Document document, String logMessage, SimpleAttributeSet logMessageAttributeSet) throws BadLocationException {
		document.insertString(document.getLength(), logMessage, logMessageAttributeSet);
		
		Element root = document.getDefaultRootElement();
		while (root.getElementCount() > maxLogLines) {
			document.remove(0, root.getElement(0).getEndOffset());
		}
	}
	
	protected void setMaxLogLines(int maxLogLines) {
		this.maxLogLines = maxLogLines;
	}
}
