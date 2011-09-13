package cat.panel;

import cat.DBManager;
import cat.model.Category;
import cat.model.Item;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class EditDialog extends JDialog {
	Map<String, Category> categories;
	Map<String, Category> subcategories;

	public EditDialog(Window parent, final Item item, String type,
			final BalancePane balancePane) {
		super(parent, "�༭��Ŀ", ModalityType.APPLICATION_MODAL);

		categories = DBManager.getCategory(type);
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		JPanel pane = new JPanel();
		arrange(pane, "���ڣ�");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		JLabel date = new JLabel(sf.format(item.getTime()));
		date.setPreferredSize(new Dimension(100, 30));
		pane.add(date);
		container.add(pane);

		pane = new JPanel();
		arrange(pane, "���");
		final JComboBox categoryCombox = new JComboBox(categories.keySet()
				.toArray());
		categoryCombox.setPreferredSize(new Dimension(100, 30));
		categoryCombox.setSelectedItem(item.getParentCategoryName());
		pane.add(categoryCombox);
		container.add(pane);

		pane = new JPanel();
		arrange(pane, "С���");
		subcategories = DBManager.getSubCategory(categories.get(
				(String) categoryCombox.getSelectedItem()).getId());

		final JComboBox subCategoryCombox = new JComboBox(subcategories
				.keySet().toArray());
		subCategoryCombox.setPreferredSize(new Dimension(100, 30));
		subCategoryCombox.setSelectedItem(item.getCategoryName());
		pane.add(subCategoryCombox);
		container.add(pane);

		categoryCombox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultComboBoxModel model = (DefaultComboBoxModel) subCategoryCombox
						.getModel();
				model.removeAllElements();
				subcategories = DBManager.getSubCategory(categories.get(
						(String) categoryCombox.getSelectedItem()).getId());
				for (String name : subcategories.keySet()) {
					model.addElement(name);
				}
			}
		});

		pane = new JPanel();
		arrange(pane, "��");
		final JTextField money = new JTextField(String.valueOf(item.getMoney()));
		money.setPreferredSize(new Dimension(80, 30));
		pane.add(money, BorderLayout.CENTER);
		pane.add(new JLabel("Ԫ"));
		container.add(pane);

		pane = new JPanel();
		arrange(pane, "�û���");
		final JTextField user = new JTextField(item.getUser());
		user.setPreferredSize(new Dimension(100, 30));
		pane.add(user);
		container.add(pane);

		pane = new JPanel();
		arrange(pane, "������");
		final JTextField address = new JTextField(item.getAddress());
		address.setPreferredSize(new Dimension(100, 30));
		pane.add(address);
		container.add(pane);

		pane = new JPanel();
		arrange(pane, "��ע��");
		final JTextField remark = new JTextField(item.getRemark());
		remark.setPreferredSize(new Dimension(100, 30));
		pane.add(remark);
		container.add(pane);

		pane = new JPanel();
		JButton update = new JButton("����");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (money.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(SwingUtilities
							.getWindowAncestor(EditDialog.this), "����Ϊ�գ�",
							"�������", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// ��֤����Ƿ�Ϊ���֣�û��ʹ��JFormattedTextField
				Pattern pattern = Pattern.compile("^[0-9\\.]+$");
				Matcher m = pattern.matcher(money.getText());
				if (!m.matches()) {
					JOptionPane.showMessageDialog(SwingUtilities
							.getWindowAncestor(EditDialog.this), "��������ȷ�Ľ�",
							"�������", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// ����
				float _money = Float.valueOf(money.getText());
				Item toSaveItem = new Item();
				toSaveItem.setId(item.getId());
				toSaveItem.setTime(item.getTime());
				toSaveItem.setMoney(_money);
				toSaveItem.setCategoryID(subcategories.get(
						(String) subCategoryCombox.getSelectedItem()).getId());
				toSaveItem.setRemark(remark.getText());
				toSaveItem.setUser(user.getText());
				toSaveItem.setAddress(address.getText());
				DBManager.updateItem(toSaveItem);

				EditDialog.this.dispose();

				balancePane.refreshData();
			}
		});
		pane.add(update);
		container.add(pane);

		this.getContentPane().add(container);
		setPreferredSize(new Dimension(250, 350));
		this.pack();
		this.setLocationRelativeTo(this);
	}

	private void arrange(JPanel pane, String text) {
		JLabel label = new JLabel(text);
		label.setPreferredSize(new Dimension(50, 30));
		pane.add(label, FlowLayout.LEFT);
	}
}
