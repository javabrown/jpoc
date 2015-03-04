package com.jbrown.swingpoc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;

public class EventWatch {

	public static void main(String[] args) {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		new EventWatch();
	}

	public EventWatch() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}

				JFrame frame = new JFrame("Testing");
				frame.setSize(400, 400);
				frame.setMaximumSize(new Dimension(400, 400));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
				
				frame.getContentPane().setLayout(new BorderLayout());
				frame.getContentPane().add(new JScrollPane(new EventViewer()), BorderLayout.CENTER);
				
				frame.setAlwaysOnTop(true);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	public class EventViewer extends JPanel {
		static final int PIX_H_FACTOR = 5;
		static final int PIX_W_FACTOR = 5;

		public EventViewer() {
			setLayout(new GridBagLayout());
			setBorder(new EtchedBorder(EtchedBorder.RAISED));
			GridBagConstraints gbc = new GridBagConstraints();
			int height = Toolkit.getDefaultToolkit().getScreenSize().height;
			int width = Toolkit.getDefaultToolkit().getScreenSize().width;

			int max_row = (int) height / PIX_H_FACTOR;
			int max_col = (int) width / PIX_W_FACTOR;

			boolean flag = false;

			for (int row = 0; row < max_row; row++) {
				for (int col = 0; col < max_col; col++) {
					gbc.gridx = col;
					gbc.gridy = row;

					EventCell eventCell = new EventCell(row, col);
					Border border = null;
					border = flag ? new MatteBorder(1, 1, 0, 0, Color.GRAY) :
				       new MatteBorder(1, 1, 0, 1, Color.GRAY);
					flag = !flag;

					//border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
					// if (row < 4) {
					// if (col < 4) {
					// border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
					// } else {
					// border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
					// }
					// } else {
					// if (col < 4) {
					// border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
					// } else {
					// border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
					// }
					// }
					eventCell.setBorder(new MatteBorder(1, 1, 0, 0, Color.GRAY));
					add(eventCell, gbc);
					System.out.printf("%s:%s\n", row, col);
				}
			}
		}
	}

	public class EventCell extends JPanel {
		static final int PIX_H_FACTOR = 5;
		static final int PIX_W_FACTOR = 5;
		
		private int _x; 
		private int _y;

		private Color defaultBackground;

		public EventCell(int x, int y) {
			_x = x;
			_y = y;
			String tooltip = String.format("<html>x=%s <br/> y=%s</html>",_x, _y);

			this.setToolTipText(tooltip);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					defaultBackground = getBackground();
					setBackground(Color.BLUE);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setBackground(defaultBackground);
				}
			});
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(PIX_W_FACTOR, PIX_H_FACTOR);
		}
	}
}