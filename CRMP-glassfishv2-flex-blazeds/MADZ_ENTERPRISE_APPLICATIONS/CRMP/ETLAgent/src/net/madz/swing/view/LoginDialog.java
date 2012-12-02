package net.madz.swing.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.Socket;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.FocusManager;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import net.madz.client.etl.delegate.ETLDelegate;
import net.madz.swing.resources.ResourceFactory;
import net.madz.swing.wizard.JWizard;
import net.madz.swing.wizard.sync.SyncProductionRecordWizard;
import net.madz.utils.ServerProperties;

import org.apache.log4j.Logger;

import com.sun.appserv.security.ProgrammaticLogin;

public class LoginDialog extends JDialog implements ActionListener {

	private static Logger logger = Logger.getLogger(LoginDialog.class);

	private Container cont = null;
	private JButton _okButton = null;
	private JButton _cancelButton = null;

	JTextField nameField = null;

	JPasswordField passField = null;

	JTextField hostField = null;

	JTextField portField = null;

	final String lDomain = "AlarmResource";

	public boolean isReentrant = true;

	public LoginDialog() {
		super();
		init();

	}

	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("Couldn't use system look and feel.");
		}
		String username = "administrator";
		String servername = "madz.vicp.net";

		try {
			XMLParser parser = new XMLParser();
			username = parser.getUser();
			servername = parser.getServer();
		} catch (Exception e) {
			isReentrant = false;
		}
		cont = this.getContentPane();
		cont.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();

		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.fill = GridBagConstraints.BOTH;
		gbConstraints.insets = new Insets(5, 5, 5, 5);

		gbConstraints.anchor = GridBagConstraints.CENTER;
		gbConstraints.ipadx = 0;
		gbConstraints.ipady = 0;

		JLabel HOST = new JLabel(ResourceFactory.getInstance().getString(lDomain, "HOSTNAME_LABEL"));

		hostField = new JTextField();
		hostField.setColumns(20);
		hostField.setText(servername);

		JLabel PORT = new JLabel(ResourceFactory.getInstance().getString(lDomain, "PORT_LABEL"));
		portField = new JTextField();
		portField.setColumns(20);

		portField.setDocument(new InputFilter('0'));
		portField.setText("3700");

		JLabel NAME = new JLabel(ResourceFactory.getInstance().getString(lDomain, "USER_LABEL"));
		nameField = new JTextField();
		nameField.setColumns(20);
		nameField.setText(username);

		JLabel PASSWD = new JLabel(ResourceFactory.getInstance().getString(lDomain, "PASSWORD_LABEL"));
		passField = new JPasswordField();
		passField.setColumns(20);
		passField.setText("");
		passField.setText("Barryzdjwin5631");

		int interval = 12;
		int gap = 16;

		addComp(HOST, centerPanel, gbConstraints, 0, 0, 4, 4);
		addComp(hostField, centerPanel, gbConstraints, 0, gap, 4, 14);

		addComp(PORT, centerPanel, gbConstraints, 1 * interval, 0, 4, 4);
		addComp(portField, centerPanel, gbConstraints, 1 * interval, gap, 4, 14);

		addComp(NAME, centerPanel, gbConstraints, 2 * interval, 0, 4, 4);
		addComp(nameField, centerPanel, gbConstraints, 2 * interval, gap, 4, 14);
		addComp(PASSWD, centerPanel, gbConstraints, 3 * interval, 0, 4, 4);
		addComp(passField, centerPanel, gbConstraints, 3 * interval, gap, 4, 14);

		panel.add(centerPanel, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		_okButton = TableUtil.createTextButton(ResourceFactory.getInstance().getString(lDomain, "OK_BUTTON"));
		_cancelButton = TableUtil.createTextButton(ResourceFactory.getInstance().getString(lDomain, "CANCEL_BUTTON"));
		buttonPanel.add(_okButton);
		buttonPanel.add(_cancelButton);

		_okButton.addActionListener(this);
		_okButton.setActionCommand("ok");

		_cancelButton.addActionListener(this);
		_cancelButton.setActionCommand("cancel");

		InputMap iMap = rootPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "closeWhenReleaseEsc");
		ActionMap aMap = rootPane.getActionMap();
		aMap.put("closeWhenReleaseEsc", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
		aMap = rootPane.getActionMap();
		aMap.put("enter", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				clickOkButton();
			}
		});

		panel.add(buttonPanel, BorderLayout.SOUTH);
		cont.add(panel, BorderLayout.SOUTH);

		String iconName = ResourceFactory.getInstance().getString(lDomain, "NSN_ICON");
		ImageIcon icon = ResourceFactory.getInstance().createImageIcon(iconName);
		JLabel label = new JLabel(icon);
		JPanel iconPanel = new JPanel();
		iconPanel.add(label);
		cont.add(iconPanel, BorderLayout.CENTER);

		Dimension displayedRange = new Dimension(350, 350);
		setSize(displayedRange);
		setMaximumSize(displayedRange);
		setMinimumSize(displayedRange);
		setPreferredSize(displayedRange);
		setResizable(true);

		setTitle("Login");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		String frameIconName = ResourceFactory.getInstance().getString(lDomain, "FRAMEICON");
		icon = ResourceFactory.getInstance().createImageIcon(frameIconName);
		setIconImage(icon.getImage());
	}

	public void setFocus4PaswdField() {
		if (isReentrant) {
			passField.requestFocusInWindow();
		}
	}

	private void addComp(Component c, Container container, GridBagConstraints gbConstraints, int row, int column, int numberOfRows,
			int numberOfColumn) {
		gbConstraints.gridx = column;
		gbConstraints.gridy = row;
		gbConstraints.gridwidth = numberOfColumn;
		gbConstraints.gridheight = numberOfRows;

		container.add(c, gbConstraints);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if ("ok".equals(command)) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			clickOkButton();
		} else if ("cancel".equals(command)) {
			this.dispose();
		}
	}

	public void clickOkButton() {

		boolean needDispose = false;

		final String host = hostField.getText();
		String port = portField.getText();
		String user = nameField.getText();
		String passwdstr = String.valueOf(passField.getPassword());
		if ("".equals(host) || "".equals(port) || "".equals(user) || "".equals(passwdstr)) {
			JOptionPane.showMessageDialog(this, ResourceFactory.getInstance().getString(lDomain, "HOST_PORT_USER_NOT_EMPTY"),
					ResourceFactory.getInstance().getString(lDomain, "INPUT_ERROR"), JOptionPane.ERROR_MESSAGE);
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			return;
		}

		if (!IPChecker.ipValid(host)) {
			JOptionPane.showMessageDialog(this, ResourceFactory.getInstance().getString(lDomain, "IPADDRESS_NOT_CORRECT"), ResourceFactory
					.getInstance().getString(lDomain, "INPUT_ERROR"), JOptionPane.ERROR_MESSAGE);
			hostField.setFocusable(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			return;
		}
		Socket socket = null;
		try {
			socket = new Socket(host, Integer.parseInt(port));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Host IpAddress or Port is not correct",
					ResourceFactory.getInstance().getString(lDomain, "INPUT_ERROR"), JOptionPane.ERROR_MESSAGE);
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			return;
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception e) {

				}
			}
		}

		LoginInfo.setHostName(host);
		LoginInfo.setPort(port);
		LoginInfo.setUser(user);
		
		System.setProperty(ServerProperties.SERVICE_IP_ADDRESS, host);
		System.setProperty(ServerProperties.SERVICE_PORT_NUMBER, port);
		
		try {
			login(user, passwdstr);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("cannot be work normally " + e.getMessage());
			JOptionPane.showMessageDialog(this, ResourceFactory.getInstance().getString(lDomain, "CONNECT_ERROR"), ResourceFactory
					.getInstance().getString(lDomain, "ERROR"), JOptionPane.ERROR_MESSAGE);
		}
		ETLDelegate delegate = null;
		try {
			delegate = ETLDelegate.getInstance();
		} catch (Exception e) {
			logger.error("Can not locate service end port");
			JOptionPane.showMessageDialog(this, ResourceFactory.getInstance().getString(lDomain, "CONNECT_ERROR"), ResourceFactory
					.getInstance().getString(lDomain, "ERROR"), JOptionPane.ERROR_MESSAGE);
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			return;
		}
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		if (null != delegate) {
			this.dispose();
			JWizard wizard = new SyncProductionRecordWizard();
			wizard.initializeContext();
			wizard.open();
		}
	}

	public static void main(String[] args) {
		final LoginDialog dia = new LoginDialog();
		(new Thread(new Runnable() {
			public void run() {
				if (dia.isReentrant) {
					while (true) {
						if (dia.isVisible()) {
							Component curFComp = FocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
							if (curFComp != null) {
								if (curFComp instanceof JPasswordField) {
									break;
								} else {
									FocusManager.getCurrentKeyboardFocusManager().focusNextComponent(curFComp);
								}
							}
						}
					}
				}
			}
		})).start();
		dia.setVisible(true);
	}

	public void login(String accountName, String password) {
		ProgrammaticLogin pl = new ProgrammaticLogin();
		try {
			pl.login(accountName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
