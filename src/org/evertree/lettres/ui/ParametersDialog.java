package org.evertree.lettres.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.evertree.lettres.resource.Parameters;

public class ParametersDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private TableModel model;
	
	public ParametersDialog(JFrame owner) {
		super(owner,"Parameters", true);
		
		getContentPane().setLayout(new BorderLayout());
		
		Map<String, Object> parameters = Parameters.all;
		JTable table = new JTable(parameters.size(),3);
		table.getColumnModel().getColumn(2).setMaxWidth(0);
		model = table.getModel();
		int i=0;
		for (String key : parameters.keySet()) {
			Object value = parameters.get(key);
			model.setValueAt(key, i, 0);
			model.setValueAt(value, i, 1);
			model.setValueAt(value.getClass(), i++, 2);
		}
		getContentPane().add(table, BorderLayout.NORTH);
		
		JButton ok = new JButton("Ok");
		ok.addActionListener(this);
		getContentPane().add(ok, BorderLayout.CENTER);
		
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		Map<String, Object> parameters = Parameters.all;
		for (int i = 0; i < model.getRowCount(); i++) {
			Object value = null;
			String key = (String) model.getValueAt(i, 0);
			String string = String.valueOf(model.getValueAt(i, 1));
			Class<?> type = (Class<?>) model.getValueAt(i, 2);
			if (Long.class.equals(type)) {
				value = new Long(string);
			}else if(Integer.class.equals(type)){
				value = new Integer(string);
			}else{
				value = string;
			}
			parameters.put(key, value);
		}
		Parameters.update();
		dispose();
	}

}
