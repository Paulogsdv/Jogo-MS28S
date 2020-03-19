package org.evertree.lettres;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.evertree.lettres.action.Action;
import org.evertree.lettres.resource.Icons;

public class ListActionsAction extends Action {

    private Game game;

    public ListActionsAction(Game game) {
	this.game = game;
    }

    @Override
    public void execute() {
	JDialog window = createWindow();
	window.setVisible(true);
    }

    private JDialog createWindow() {
	Set<String> actionNames = game.getActionNames();

	final JDialog window = new JDialog(game.getUserInterface(), "Lettres",
		true);
	window.setIconImage(Icons.LETTRES_16);
	window.setSize(250, 500);
	window.setResizable(false);
	window.setLocationRelativeTo(null);
	((JPanel) window.getContentPane()).setBorder(BorderFactory
		.createLineBorder(window.getBackground(), 5));
	((BorderLayout) window.getContentPane().getLayout()).setVgap(5);

	JPanel listPanel = new JPanel(new GridLayout(actionNames.size(), 1, 5,
		5));
	for (String actionName : actionNames) {
	    listPanel.add(new JLabel(" " + actionName));
	}

	JScrollPane scroller = new JScrollPane(listPanel);

	JButton button = new JButton("CLOSE");
	button.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		window.dispose();		
	    }
	});

	window.add(new JLabel("The possible words are:"), BorderLayout.NORTH);
	window.add(scroller, BorderLayout.CENTER);
	window.add(button, BorderLayout.SOUTH);

	return window;
    }

}
