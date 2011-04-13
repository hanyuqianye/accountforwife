package cat.panel;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import cat.DBManager;
import cat.model.Category;

public class SubCategoryDialog extends JDialog {
	Logger log = Logger.getLogger("SubCategoryPane");

	private DefaultListModel listModel;
	private JList list;
	private boolean itemchanged;

	public SubCategoryDialog(Window frame, final String type) {
		super(frame, "С�������", Dialog.ModalityType.DOCUMENT_MODAL);
		setLayout(new BorderLayout());

		final Map<String, Category> cates = DBManager.getCategory(type);
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nameLabel = new JLabel("���");
		nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 23));
		itemPanel.add(nameLabel);
		final JComboBox categoryName = new JComboBox(cates.keySet().toArray());
		categoryName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listModel.clear();
				final Map<String, Category> subCates = DBManager
						.getSubCategory(cates.get(
								categoryName.getSelectedItem().toString())
								.getId());
				for (String cate : subCates.keySet()) {
					Category category = subCates.get(cate);
					listModel.addElement(category.getName() + "  [ "
							+ category.getDisplayOrder() + " ]");
				}
			}
		});
		categoryName.setPreferredSize(new Dimension(80, 25));
		itemPanel.add(categoryName);
		leftPanel.add(itemPanel);

		itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel subCameLabel = new JLabel("���ƣ�");
		subCameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 23));
		itemPanel.add(subCameLabel);
		final JTextField subCategoryName = new JTextField();
		subCategoryName.setPreferredSize(new Dimension(80, 25));
		itemPanel.add(subCategoryName);
		leftPanel.add(itemPanel);

		itemPanel = new JPanel();
		itemPanel.add(new JLabel("��ʾ˳��"));
		final JTextField displayOrder = new JTextField();
		displayOrder.setToolTipText("��ֵԽ��Խ��ǰ��ʾ");
		displayOrder.setPreferredSize(new Dimension(80, 25));
		itemPanel.add(displayOrder);
		leftPanel.add(itemPanel);

		JButton add = new JButton("���");
		add.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = listModel.getSize();
				String cate = categoryName.getSelectedItem().toString().trim();
				String subCate = subCategoryName.getText().trim();
				int dispOrder = Integer.valueOf(displayOrder.getText().trim());
				if (subCate.equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(SwingUtilities
							.getWindowAncestor(SubCategoryDialog.this),
							"������ӿհ�����!", "��Ӵ���", JOptionPane.ERROR_MESSAGE);
					return;
				}
				DBManager.saveSubCategory(cates.get(cate).getId(), type,
						subCate, dispOrder);
				listModel.addElement(subCate + "  [ " + dispOrder + " ]");
				list.setSelectedIndex(index);
				list.ensureIndexIsVisible(index);
				itemchanged = true;
			}
		});

		leftPanel.add(add);
		leftPanel.add(Box.createVerticalStrut(150));

		JButton close = new JButton("�ر�");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (itemchanged)
					JOptionPane.showMessageDialog(SwingUtilities
							.getWindowAncestor(SubCategoryDialog.this),
							"������������������!", "С�������",
							JOptionPane.INFORMATION_MESSAGE);

				SubCategoryDialog.this.setVisible(false);
				SubCategoryDialog.this.dispose();
			}
		});
		leftPanel.add(close);

		listModel = new DefaultListModel();
		final Map<String, Category> subCates = DBManager.getSubCategory(cates
				.get(categoryName.getSelectedItem().toString()).getId());
		for (String cate : subCates.keySet()) {
			Category category = subCates.get(cate);
			listModel.addElement(category.getName() + "  [ "
					+ category.getDisplayOrder() + " ]");
		}

		list = new JList(listModel);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);

		list.setVisibleRowCount(10);
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setPreferredSize(new Dimension(100, 200));
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(listScrollPane, BorderLayout.PAGE_START);

		JButton delete = new JButton("ɾ��");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index == -1) {
					JOptionPane.showMessageDialog(SwingUtilities
							.getWindowAncestor((JButton) e.getSource()),
							"�б��κ�����!");
					return;
				}

				String subCateName = (String) list.getSelectedValue();
				subCateName = subCateName.replaceAll("^(.*)\\[.*$", "$1")
						.trim();
				DBManager.deleteCategory(subCates.get(subCateName).getId());
				listModel.removeElementAt(index);
				if (index == 0) {
					index = 0;
				} else {
					index--;
				}
				list.setSelectedIndex(index);
				list.ensureIndexIsVisible(index);
				itemchanged = true;
			}
		});

		rightPanel.add(delete, BorderLayout.PAGE_END);
		leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(leftPanel, BorderLayout.LINE_START);
		add(rightPanel, BorderLayout.LINE_END);
		pack();
	}
}
