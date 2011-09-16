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
		dateColumns.addElement("序号");
		dateColumns.addElement("日期");
		dateColumns.addElement("类别");
		dateColumns.addElement("小类别");
		dateColumns.addElement("金额(元)");
		dateColumns.addElement("用户");
		dateColumns.addElement("场所");
		dateColumns.addElement("备注");
		dateColumns.addElement("类型");
		dateColumns.addElement("颜色");

		categoryBudgetColumns.addElement("序号");
		categoryBudgetColumns.addElement("类别ID");
		categoryBudgetColumns.addElement("类别");
		categoryBudgetColumns.addElement("预算金额");

		overDrawColumns.addElement("ID");
		overDrawColumns.addElement("序号");
		overDrawColumns.addElement("支付日期");
		overDrawColumns.addElement("支付金额");
		overDrawColumns.addElement("场所");
		overDrawColumns.addElement("备注");
		overDrawColumns.addElement("归还日期");
		overDrawColumns.addElement("归还金额");
		overDrawColumns.addElement("归还备注");
		overDrawColumns.addElement("结余");
		overDrawColumns.addElement("完成");
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
