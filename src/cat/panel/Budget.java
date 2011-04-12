package cat.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import cat.Configure;
import cat.DBManager;
import cat.editor.MoneyCellEditor;
import cat.model.Category;

public class Budget extends JPanel {
	static Logger log = Logger.getLogger("Budget");

	JComboBox year;

	JComboBox month;
	JTable budgetTable;
	DefaultTableModel model = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			if (column == 0 || column == 1) {
				return false;
			}
			return true;
		}
	};

	public Budget() {
		super(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

		// ��𲿷�
		JPanel categorySetPane = new JPanel();
		categorySetPane.setLayout(new BoxLayout(categorySetPane,
				BoxLayout.LINE_AXIS));
		categorySetPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black), "�������"));
		final Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put("����", "Income");
		typeMap.put("֧��", "Expenditure");

		final JComboBox typeCombox = new JComboBox(typeMap.keySet().toArray());
		final JButton categoryButton = new JButton("�������");
		categoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CategoryDialog panel = new CategoryDialog(SwingUtilities
						.getWindowAncestor((JButton) e.getSource()), typeMap
						.get(typeCombox.getSelectedItem().toString()));
				panel.setLocationRelativeTo(Budget.this);
				panel.setVisible(true);
			}
		});

		final JButton subCategoryButton = new JButton("С�������");
		subCategoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubCategoryDialog panel = new SubCategoryDialog(SwingUtilities
						.getWindowAncestor((JButton) e.getSource()), typeMap
						.get(typeCombox.getSelectedItem().toString()));
				panel.setLocationRelativeTo(Budget.this);
				panel.setVisible(true);
			}
		});

		typeCombox.setPreferredSize(new Dimension(80, 30));
		typeCombox.setMaximumSize(new Dimension(80, 30));
		categorySetPane.add(typeCombox);
		categorySetPane.add(Box.createHorizontalStrut(10));
		categorySetPane.add(categoryButton);
		categorySetPane.add(Box.createHorizontalStrut(10));
		categorySetPane.add(subCategoryButton);
		categorySetPane.add(Box.createHorizontalGlue());

		add(categorySetPane, BorderLayout.PAGE_START);

		// Ԥ�㲿��
		JPanel choose = new JPanel();
		choose.setLayout(new BoxLayout(choose, BoxLayout.PAGE_AXIS));
		choose.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.black), "Ԥ������"));

		Calendar c = Calendar.getInstance();

		year = new JComboBox(new Object[] { 2011, 2012, 2013, 2014, 2015 });
		month = new JComboBox(new Object[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
				12 });
		JPanel yearMonth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		yearMonth.add(new JLabel("ѡ�����£�"));
		year.setSelectedItem(c.get(Calendar.YEAR));
		month.setSelectedItem(c.get(Calendar.MONTH) + 1);

		yearMonth.add(year);
		yearMonth.add(new JLabel("��"));
		yearMonth.add(month);
		yearMonth.add(new JLabel("��"));
		choose.add(yearMonth);

		year.addItemListener(new SelectChangeListenter());
		month.addItemListener(new SelectChangeListenter());

		JLabel title = new JLabel("֧��Ԥ���");
		title.setFont(title.getFont().deriveFont(Font.BOLD, 14));
		title.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		JPanel tmp = new JPanel();
		tmp.add(title);
		choose.add(tmp);

		// Ԥ����
		budgetTable = new JTable(model);
		refreshTableData();

		JScrollPane scrollPane = new JScrollPane(budgetTable);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(5, 5, 0, 5), scrollPane.getBorder()));
		scrollPane.setPreferredSize(new Dimension(350, 180));
		budgetTable.setAutoCreateRowSorter(true);
		// sourceTable.getRowSorter().toggleSortOrder(0);
		choose.add(scrollPane);

		JButton save = new JButton("����");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (budgetTable.isEditing()) {
					budgetTable.getCellEditor().stopCellEditing();
				}

				Map map = new HashMap<Integer, String>();
				for (int row = 0; row < budgetTable.getRowCount(); row++) {
					int categoryID = Integer.valueOf(model.getValueAt(row, 1)
							.toString());
					float money = Float.valueOf(model.getValueAt(row, 3)
							.toString());
					if (money != -1) {
						map.put(categoryID, money);
					}
				}
				DBManager.saveBudget((Integer) year.getSelectedItem(),
						(Integer) month.getSelectedItem(), map);

				JOptionPane.showMessageDialog(Budget.this, "����ɹ���", "Ԥ������",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JPanel saveButtonPane = new JPanel();
		saveButtonPane.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		saveButtonPane.add(save);
		choose.add(saveButtonPane);
		add(choose, BorderLayout.PAGE_END);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TableCellEditor editor = budgetTable.getCellEditor();
				if (editor != null) {
					editor.stopCellEditing();
				}
			}
		});
	}

	private void categoryConfig(JPanel pane, String title, final String type) {
		final JButton payoutEdit = new JButton(title);
		payoutEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CategoryDialog panel = new CategoryDialog(SwingUtilities
						.getWindowAncestor((JButton) e.getSource()), type);
				panel.setLocationRelativeTo(Budget.this);
				panel.setVisible(true);
			}
		});
		pane.add(payoutEdit);
	}

	private void refreshTableData() {
		Vector<Vector> items = DBManager.getBudgetItems((Integer) year
				.getSelectedItem(), (Integer) month.getSelectedItem());
		model.setDataVector(items, Configure.getCategoryBudgetColumns());

		budgetTable.getColumnModel().getColumn(3).setCellEditor(
				new MoneyCellEditor());
		budgetTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						if (value.toString().trim().equals("-1")) {
							value = "--";
						}
						return super.getTableCellRendererComponent(table,
								value, isSelected, hasFocus, row, column);
					}
				});

		budgetTable.setPreferredScrollableViewportSize(new Dimension(450, 170));
		budgetTable.setRowHeight(22);
		TableColumn idCol = budgetTable.getColumnModel().getColumn(1);
		idCol.setMaxWidth(0);
		idCol.setMinWidth(0);
		idCol.setPreferredWidth(0);
	}

	/**
	 * �����ձ仯ʱ������Ԥ�����ݡ�
	 */
	class SelectChangeListenter implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			refreshTableData();
		}
	}
}
