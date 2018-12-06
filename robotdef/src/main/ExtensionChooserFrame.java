package main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ExtensionChooserFrame extends JDialog {

	private ButtonGroup group = new ButtonGroup();
	private JPanel container = new JPanel();
	private JButton submit = new JButton("Go !");
	private JCheckBox collisionCB = new JCheckBox("Collision");
	private String[] extension;
	private Boolean[] collision;

	public ExtensionChooserFrame(String[] ext, Boolean[] col, String title) {
		super(new JFrame(), title, true);
		setBounds(100, 100, 100, 250);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		extension = ext;
		collision = col;
		initComponents();

		add(container);
		setVisible(true);
	}

	private void initComponents() {
		JRadioButton normalBtn = new JRadioButton("Normal");
		normalBtn.setMnemonic(KeyEvent.VK_N);
		normalBtn.setActionCommand("Normal");
		normalBtn.setSelected(true);

		JRadioButton goalBtn = new JRadioButton("GoalKeeper");
		goalBtn.setMnemonic(KeyEvent.VK_K);
		goalBtn.setActionCommand("GoalKeeper");

		JRadioButton multiBtn = new JRadioButton("MultiGoal");
		multiBtn.setMnemonic(KeyEvent.VK_M);
		multiBtn.setActionCommand("MultiGoal");

		JRadioButton posInitBtn = new JRadioButton("PosInit");
		goalBtn.setMnemonic(KeyEvent.VK_I);
		goalBtn.setActionCommand("PosInit");

		JRadioButton SATBtn = new JRadioButton("SAT");
		SATBtn.setMnemonic(KeyEvent.VK_S);
		SATBtn.setActionCommand("SAT");

		JRadioButton allBtn = new JRadioButton("All");
		goalBtn.setMnemonic(KeyEvent.VK_A);
		goalBtn.setActionCommand("All");

		group.add(normalBtn);
		group.add(goalBtn);
		group.add(multiBtn);
		group.add(posInitBtn);

		add(normalBtn);
		add(goalBtn);
		add(multiBtn);
		add(posInitBtn);
		add(SATBtn);
		add(allBtn);
		add(collisionCB);
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (normalBtn.isSelected()) {
					extension[0] = normalBtn.getText();
				} else if (goalBtn.isSelected()) {
					extension[0] = goalBtn.getText();
				} else if (multiBtn.isSelected()) {
					extension[0] = multiBtn.getText();
				} else if (posInitBtn.isSelected()) {
					extension[0] = posInitBtn.getText();
				} else if (SATBtn.isSelected()) {
					extension[0] = SATBtn.getText();
				} else if (allBtn.isSelected()) {
					extension[0] = allBtn.getText();
				}
				collision[0] = collisionCB.isSelected();
				CloseDialog();
			}
		});
		add(submit);
	}

	private void CloseDialog() {
		this.dispose();
	}
}
