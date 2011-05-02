package cat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cat.panel.BalancePane;
import cat.panel.BudgetPane;
import cat.panel.CategoryDialog;
import cat.panel.QueryPane;

public class AccountPanel extends JPanel {
	public AccountPanel() {
		super(new BorderLayout());
		JPanel tabpane = new JPanel();
		final JTabbedPane tab = new JTabbedPane();
		final BalancePane expenditure = new BalancePane("Expenditure");
		tab.addTab("֧��", expenditure);
		tab.setMnemonicAt(0, KeyEvent.VK_1);

		final BalancePane income = new BalancePane("Income");
		tab.addTab("����", income);
		tab.setMnemonicAt(1, KeyEvent.VK_2);
		final BudgetPane budget = new BudgetPane();
		tab.addTab("Ԥ��", budget);
		tab.setMnemonicAt(2, KeyEvent.VK_3);

		final QueryPane queryPane = new QueryPane();
		tab.addTab("��ѯ", queryPane);
		tab.setMnemonicAt(3, KeyEvent.VK_4);

		tab.setPreferredSize(new Dimension(620, 460));
		tabpane.setLayout(new GridLayout(1, 1));
		tabpane.add(tab);

		tab.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (CategoryDialog.itemchanged) {
					expenditure.categoryReload();
					income.categoryReload();
					budget.refreshTableData();
					queryPane.categoryReload();
					CategoryDialog.itemchanged = false;
				}
				if (tab.getSelectedIndex() == 2) {
					budget.refreshSummary();
				}
			}
		});

		add(tabpane, BorderLayout.PAGE_START);

		JLabel sign = new JLabel("���Դ�����׸������������С�� {O(��_��)O~");
		sign.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(sign, BorderLayout.PAGE_END);
	}

	private static void createAndShowGUI() {
		try {
			for (LookAndFeelInfo lookAndFeel : UIManager
					.getInstalledLookAndFeels()) {
				if (lookAndFeel.getName().equalsIgnoreCase("Nimbus")) {
					UIManager.setLookAndFeel(lookAndFeel.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame("С������");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DBManager.releaseConnection();
			}
		});
		frame.getContentPane().add(new AccountPanel(), BorderLayout.CENTER);
		frame.setResizable(false);
		frame.pack();
		// frame.setLocationByPlatform(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
