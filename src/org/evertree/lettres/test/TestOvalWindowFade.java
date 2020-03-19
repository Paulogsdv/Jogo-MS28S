package org.evertree.lettres.test;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.sun.awt.AWTUtilities;
 
public class TestOvalWindowFade extends JFrame {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final Timer fadeTimer = new Timer(100, new ActionListener() {
 
        float alpha = 0f;
 
        public void actionPerformed(ActionEvent e) {
            alpha = Math.min(alpha + .1f, 1.0f);
 
            AWTUtilities.setWindowOpacity(
                    TestOvalWindowFade.this, alpha);
 
            if (alpha >= 1.0f) {
                alpha = 0f;
                ((Timer) e.getSource()).stop();
            }
        }
    });
 
    public TestOvalWindowFade() {
        super("Test translucent and oval-shaped window");
        setLayout(new java.awt.GridBagLayout());
        add(new JButton("test"));
        add(new JCheckBox("test"));
        add(new JRadioButton("test"));
        add(new JProgressBar(0, 100));
 
        setSize(new java.awt.Dimension(400, 300));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
 
        AWTUtilities.setWindowShape(this,
                new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
        AWTUtilities.setWindowOpacity(this, 0f);
    }
 
    public void addNotify() {
        super.addNotify();
        fadeTimer.start();
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TestOvalWindowFade().setVisible(true);
            }
        });
    }
}