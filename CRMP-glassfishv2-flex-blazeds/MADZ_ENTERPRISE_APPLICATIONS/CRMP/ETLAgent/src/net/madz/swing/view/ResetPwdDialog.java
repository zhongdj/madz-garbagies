package net.madz.swing.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.nsn.biz.security.messaging.event.SecurityEvent;
import com.nsn.biz.security.persistence.entity.Account;
import com.nsn.dbconfig.PersistenceProperty;
import com.nsn.delegate.ConfigurationManagementDelegate;
import com.nsn.delegate.PersistencePropertyDelegate;
import com.nsn.delegate.SecurityDelegate;
import com.nsn.delegate.ServerProperties;
import com.nsn.messaging.recv.ClientMessageDispatcher;
import com.nsn.tnms.client.model.NETreeModel;
import com.nsn.tnms.client.model.PathTreeModel;
import com.nsn.tnms.client.model.PortConnectionTreeModel;
import com.nsn.tnms.client.resource.ResourceFactory;

public class ResetPwdDialog extends JDialog implements ActionListener {

	private Container cont = null;
	private JButton _okButton = null;
	private JButton _cancelButton = null;

	private static Logger logger = Logger.getLogger(LoginDialog.class);

	JTextField nameField = null;
	JPasswordField passField = null;
	JPasswordField newpassField1 = null;
	JPasswordField newpassField2 = null;

	final String lDomain = "AlarmResource";
	String hostName = null;
	private boolean isFromMenu = false;

	public ResetPwdDialog(JFrame dialog, boolean modal, String host) {
		super(dialog);
		this.setModal(modal);
		hostName = host;
		init();
	}
	
	public synchronized void setIsFromMenu(boolean isFromMenu) {
		this.isFromMenu = isFromMenu;
	}

	private void init() {

		cont = this.getContentPane();
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

		// JLabel NAME = new JLabel(ResourceFactory.getInstance().getString(
		// lDomain, "USER_LABEL"));
		// nameField = new JTextField();
		// nameField.setColumns(20);
		// nameField.setText(LoginInfo.getUser());

		JLabel PASSWD = new JLabel(ResourceFactory.getInstance().getString(
				lDomain, "OLD_PASSWORD_LABEL"));
		passField = new JPasswordField();
		passField.setColumns(20);

		JLabel NEWPWDLABEL = new JLabel(ResourceFactory.getInstance()
				.getString(lDomain, "NEW_PASSWORD_LABEL"));
		newpassField1 = new JPasswordField();
		newpassField1.setColumns(20);
		newpassField1.setText("");

		JLabel RENEWPWDLABEL = new JLabel(ResourceFactory.getInstance()
				.getString(lDomain, "RENEW_PASSWORD_LABEL"));
		newpassField2 = new JPasswordField();
		newpassField2.setColumns(20);
		newpassField2.setText("");

		int interval = 12;
		int gap = 16;

		// addComp(NAME, centerPanel, gbConstraints, 0, 0, 4, 4);
		// addComp(nameField, centerPanel, gbConstraints, 0, gap, 4, 14);

		addComp(PASSWD, centerPanel, gbConstraints, 1 * interval, 0, 4, 4);
		addComp(passField, centerPanel, gbConstraints, 1 * interval, gap, 4, 14);

		addComp(NEWPWDLABEL, centerPanel, gbConstraints, 2 * interval, 0, 4, 4);
		addComp(newpassField1, centerPanel, gbConstraints, 2 * interval, gap,
				4, 14);
		addComp(RENEWPWDLABEL, centerPanel, gbConstraints, 3 * interval, 0, 4,
				4);
		addComp(newpassField2, centerPanel, gbConstraints, 3 * interval, gap,
				4, 14);

		panel.add(centerPanel, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		_okButton = TableUtil.createTextButton(ResourceFactory.getInstance()
				.getString(lDomain, "OK_BUTTON"));
		_cancelButton = TableUtil.createTextButton(ResourceFactory
				.getInstance().getString(lDomain, "CANCEL_BUTTON"));
		buttonPanel.add(_okButton);
		buttonPanel.add(_cancelButton);

		_okButton.addActionListener(this);
		_okButton.setActionCommand("ok");

		_cancelButton.addActionListener(this);
		_cancelButton.setActionCommand("cancel");

		InputMap iMap = rootPane
				.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"closeWhenReleaseEsc");
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
				clickOkButton();
			}
		});

		panel.add(buttonPanel, BorderLayout.SOUTH);
		cont.add(panel);
		this.setSize(400, 220);
		this.setTitle("Reset/Change Password");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screen.width - this.getSize().width) / 2,
				(screen.height - this.getSize().height) / 2);
		this.setModal(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private void addComp(Component c, Container container,
			GridBagConstraints gbConstraints, int row, int column,
			int numberOfRows, int numberOfColumn) {
		gbConstraints.gridx = column;
		gbConstraints.gridy = row;
		gbConstraints.gridwidth = numberOfColumn;
		gbConstraints.gridheight = numberOfRows;

		container.add(c, gbConstraints);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("ok".equals(cmd)) {
			clickOkButton();
		} else if ("cancel".equals(cmd)) {
			this.dispose();
		}
	}

	private void clickOkButton() {
		String user = LoginInfo.getUser();
		String oldpwd = String.valueOf(passField.getPassword());
		String newpwd1 = String.valueOf(newpassField1.getPassword());
		String newpwd2 = String.valueOf(newpassField2.getPassword());

		if (!newpwd1.equals(newpwd2)) {
			JOptionPane.showMessageDialog(this, ResourceFactory.getInstance()
					.getString(lDomain, "PASSWD_NOT_MATCH"), ResourceFactory
					.getInstance().getString(lDomain, "INPUT_ERROR"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (oldpwd.equals(newpwd1)) {
			JOptionPane.showMessageDialog(this, ResourceFactory.getInstance()
					.getString(lDomain, "OLD_NEW_PASSWD_SAME"), ResourceFactory
					.getInstance().getString(lDomain, "INPUT_ERROR"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int pwdLength = newpwd1.getBytes().length;
		if (pwdLength < 5 || pwdLength > 15) {
			JOptionPane.showMessageDialog(this,"Password length should be between 5 and 15" , ResourceFactory
					.getInstance().getString(lDomain, "INPUT_ERROR"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		SecurityEvent event = null;
		try {
			event = SecurityDelegate.getInstance().resetPassword(user, oldpwd,
					newpwd1);
		} catch (Exception e) {

		}
		if (this.isFromMenu) {
			this.dispose();
			return;
		}
		if (SecurityEvent.PASSWORD_RESET_SUCCESSFULLY == event) {
			this.dispose();
			Properties prop = ServerProperties.getServerProperties();

			prop.put(ServerProperties.SERVICE_IP_ADDRESS, LoginInfo.getUser());
			prop.put(ServerProperties.SERVICE_PORT_NUMBER, LoginInfo.getPort());
			SecurityDelegate delegate = null;
			try {
				delegate = SecurityDelegate.getInstance();
			} catch (Exception e) {

			}
			//
			Account accout = delegate.findAccountByName(user);
			UserProperties.USERPROP = accout.getGroup().getName();
			try {
				event = delegate.login(user, newpwd1);

				if (SecurityEvent.LOGIN_SUCCESSFULLY == event) {
					XMLWriter write = new XMLWriter();
					write.buildXMLDoc(hostName, user);
					ProgressPane.getInstance().show("TNMS Initialization Progress",
							"Initializing...", this, new Runnable() {
								public void run() {
									try {
										ClientMessageDispatcher.getInstance()
												.subscribeTopics();
										ProgressPane.getInstance()
												.setOverallProgress(35);
										PersistencePropertyDelegate per = PersistencePropertyDelegate
												.getInstance();
										List<PersistenceProperty> list = per
												.findAllProperty();
										for (PersistenceProperty perProperty : list) {
											String name = perProperty
													.getPropertyName();
											String value = perProperty
													.getPropertyValue();

											if (PersistenceProperty.DB_NAME
													.equals(name)) {
												LoginInfo.set_dbName(value);
											} else if (PersistenceProperty.DB_PASS
													.equals(name)) {
												LoginInfo.set_dbPwd(value);
											} else if (PersistenceProperty.DB_URL
													.equals(name)) {
												String url = value.replaceAll("##",
														hostName);
												LoginInfo.set_dbURL(url);
											} else if (PersistenceProperty.DB_USER
													.equals(name)) {
												LoginInfo.set_dbUserName(value);
											}
										}
										ProgressPane.getInstance()
												.setOverallProgress(55);
										PathTreeModel.pre_all_path = ConfigurationManagementDelegate
												.getInstance().getAllPath();
										ProgressPane.getInstance()
												.setOverallProgress(85);
										PortConnectionTreeModel.pre_all_pc = ConfigurationManagementDelegate
												.getInstance()
												.getAllPortConnection();
										ProgressPane.getInstance()
												.setOverallProgress(90);
										NETreeModel.rootElement = ConfigurationManagementDelegate
												.getInstance().getTopoTree();
										ProgressPane.getInstance()
												.setOverallProgress(100);
										AlarmView.createAndShowGUI();
									} catch (Exception e) {
										ProgressPane.getInstance()
												.setOverallProgress(100);
										System.out
												.println("subscribeTopics failed");
										logger.error("subscribeTopics failed", e);
									}

								}
							});
					
				} else if (SecurityEvent.ACCOUNT_FREEZEN == event) {

					logger.warn("ACCOUNT_FREEZEN ");
				} else if (SecurityEvent.ACCOUNT_LOCKED == event) {
					logger.warn("ACCOUNT_LOCKED ");
				} else if (SecurityEvent.PASSWORD_NOT_VALID == event) {
					logger.warn("PASSWORD_NOT_VALID ");
				} else if (SecurityEvent.NEED_RESET_PASSWORD == event) {

				} else if (SecurityEvent.SECURITY_POLICY_CFG_ERROR == event) {
					logger.warn("SECURITY_POLICY_CFG_ERROR ");
				} else if (SecurityEvent.USER_NOT_EXIST == event) {
					logger.warn("USER_NOT_EXIST ");
				} else if (SecurityEvent.ILLEGAL_ARGUMENTS == event) {
					logger.warn("ILLEGAL_ARGUMENTS ");
				} else {

				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, ResourceFactory
						.getInstance().getString(lDomain, "CONNECT_ERROR"),
						ResourceFactory.getInstance().getString(lDomain,
								"ERROR"), JOptionPane.ERROR_MESSAGE);
			}

		} else {
			logger.error("change password fails");
		}

	}

}
