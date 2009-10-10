package cat;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Vector;


public class Constance
{
  static final DateFormat dateFormat = new SimpleDateFormat(" yyyy - MM - dd ");

  static final Vector<String> dateColumns = new Vector<String>();

  static final Vector<String> fieldColumns = new Vector<String>();

  static final Vector<String> statColumns = new Vector<String>();

  static final Vector<String> itemStatColumns = new Vector<String>();

  static final Vector<String> sourceBudgetColumns = new Vector<String>();

  static final String LOGIN_USER = "cat";
  static
  {
    dateColumns.addElement("���");
    dateColumns.addElement("���");
    dateColumns.addElement("����");
    dateColumns.addElement("��Ŀ");
    dateColumns.addElement("���");
    dateColumns.addElement("��ע");
    dateColumns.addElement("��ɫ");

    fieldColumns.addElement("Type");
    fieldColumns.addElement("Date");
    fieldColumns.addElement("Item");
    fieldColumns.addElement("Money");
    fieldColumns.addElement("Remark");
    fieldColumns.addElement("Color");

    statColumns.addElement("����");
    statColumns.addElement("֧��");
    statColumns.addElement("����");

    itemStatColumns.addElement("��Ŀ");
    itemStatColumns.addElement("���");
    itemStatColumns.addElement("�ٷֱ�(%)");

    sourceBudgetColumns.addElement("���");
    sourceBudgetColumns.addElement("��Ŀ");
    sourceBudgetColumns.addElement("Ԥ����");
  }


  public static Vector<String> getDateColumns()
  {
    return dateColumns;
  }


  public static Vector<String> getStatColumns()
  {
    return statColumns;
  }


  public static Vector<String> getItemStatColumns()
  {
    return itemStatColumns;
  }


  public static Vector<String> getFieldColumns()
  {
    return fieldColumns;
  }


  public static Vector<String> getSourceBudgetColumns()
  {
    return sourceBudgetColumns;
  }
}
