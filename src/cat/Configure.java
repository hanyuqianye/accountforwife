package cat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Vector;

public class Configure {
	static final DateFormat dateFormat = new SimpleDateFormat(
			" yyyy - MM - dd ");

	static final Vector<String> dateColumns = new Vector<String>();
	static final Vector<String> categoryBudgetColumns = new Vector<String>();
	static final Vector<String> overDrawColumns = new Vector<String>();

	static final String LOGIN_USER = "cat";
	static {
		dateColumns.addElement("ID");
		dateColumns.addElement("���");
		dateColumns.addElement("����");
		dateColumns.addElement("���");
		dateColumns.addElement("С���");
		dateColumns.addElement("���(Ԫ)");
		dateColumns.addElement("�û�");
		dateColumns.addElement("����");
		dateColumns.addElement("��ע");
		dateColumns.addElement("����");
		dateColumns.addElement("��ɫ");

		categoryBudgetColumns.addElement("���");
		categoryBudgetColumns.addElement("���ID");
		categoryBudgetColumns.addElement("���");
		categoryBudgetColumns.addElement("Ԥ����");

		overDrawColumns.addElement("ID");
		overDrawColumns.addElement("���");
		overDrawColumns.addElement("֧������");
		overDrawColumns.addElement("֧�����");
		overDrawColumns.addElement("����");
		overDrawColumns.addElement("��ע");
		overDrawColumns.addElement("�黹����");
		overDrawColumns.addElement("�黹���");
		overDrawColumns.addElement("�黹��ע");
	}

	public static Vector<String> getDateColumns() {
		return dateColumns;
	}

	public static Vector<String> getCategoryBudgetColumns() {
		return categoryBudgetColumns;
	}

	public static Vector<String> getOverDrawColumns() {
		return overDrawColumns;
	}
}
