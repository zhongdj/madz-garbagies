package net.madz.swing.view.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.Border;



public class ColumnHidingConfigureDialog extends JDialog implements
		ActionListener {
	private final static String _OK = "OK";
	private ColumnHidingConfigurePanel _configurePanel = null;
	private JScrollPane _configureScroller = null;
	private Component _parentComponent = null;

	public ColumnHidingConfigureDialog(Frame frame, Component parentComponent,
			ColumnHidingConfigurePanel configurePanel) {
		super(frame, true);
		setTitle("Show/Hide Columns");
		_configurePanel = configurePanel;

		// Create and initialize the buttons.
		JButton cancelButton = GraphicFactory.createTextButton("CANCEL");
		cancelButton.addActionListener(this);
		getRootPane().registerKeyboardAction(this,
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		//
		final JButton okButton = GraphicFactory.createTextButton(_OK);
		okButton.setActionCommand(_OK);
		okButton.addActionListener(this);
		getRootPane().setDefaultButton(okButton);

		_configureScroller = new JScrollPane(_configurePanel);
		_configureScroller.setAlignmentX(LEFT_ALIGNMENT);
		_configureScroller.setAlignmentY(TOP_ALIGNMENT);

		JPanel configurePane = new JPanel();
		configurePane.setLayout(new BoxLayout(configurePane,
				BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel("Select the columns to display in the table:");
		label.setLabelFor(_configurePanel);
		configurePane.add(label);
		configurePane.add(Box.createRigidArea(new Dimension(0, 5)));
		configurePane.add(_configureScroller);
		configurePane
				.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(okButton);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(cancelButton);

		Container contentPane = getContentPane();
		contentPane.add(configurePane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);

		_parentComponent = parentComponent;
	}

	public void actionPerformed(ActionEvent e) {
		if (_OK.equals(e.getActionCommand())) {
			_configurePanel.setConfigureToTable();
		}
		this.setVisible(false);
	}

	public void setVisible(boolean b) {
		if (b) {
			_configurePanel.refreshConfigure();

			Dimension d = _configurePanel.getPreferredSize();
			int maxHeight = Toolkit.getDefaultToolkit().getScreenSize().height * 4 / 5;
			Border border = _configureScroller.getBorder();
			if (border != null) {
				d.height += border.getBorderInsets(_configureScroller).top
						+ border.getBorderInsets(_configureScroller).bottom;
			}
			if (d.height > maxHeight) {
				d.height = maxHeight;
			}
			_configureScroller.setPreferredSize(d);

			pack();
			setLocationRelativeTo(_parentComponent);
		}
		super.setVisible(b);
	}
}