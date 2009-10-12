package cat;


import cat.panel.Budget;
import cat.panel.Income;
import cat.panel.Payout;
import cat.panel.Statistic;
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


public class AccountPanel
    extends JPanel
{
  public AccountPanel()
  {
    super(new BorderLayout());
    JPanel tabpane = new JPanel();
    JTabbedPane tab = new JTabbedPane();
    Payout payout = new Payout();
    tab.addTab("Ԥ��", new Budget(payout));
    tab.setMnemonicAt(0, KeyEvent.VK_1);
    tab.addTab("֧��", payout);
    tab.setMnemonicAt(1, KeyEvent.VK_2);
    tab.addTab("����", new Income());
    tab.setMnemonicAt(2, KeyEvent.VK_3);
    tab.addTab("ͳ��", new Statistic());
    tab.setMnemonicAt(3, KeyEvent.VK_4);
    tab.setSelectedIndex(1);
    tab.setPreferredSize(new Dimension(620, 460));
    tabpane.setLayout(new GridLayout(1, 1));
    tabpane.add(tab);
    add(tabpane, BorderLayout.PAGE_START);

    JLabel sign = new JLabel("���Դ�����׸������������С�� ... ����");
    sign.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    add(sign, BorderLayout.PAGE_END);
  }


  private static void createAndShowGUI()
  {
    try
    {
      for (LookAndFeelInfo lookAndFeel : UIManager.getInstalledLookAndFeels())
      {
        if (lookAndFeel.getName().equalsIgnoreCase("Nimbus"))
        {
          UIManager.setLookAndFeel(lookAndFeel.getClassName());
          break;
        }
      }
      if (UIManager.getLookAndFeel().getName().equalsIgnoreCase("Metal"))
      {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    JFrame frame = new JFrame("С������");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing(WindowEvent e)
      {
        DBManager.releaseConnection();
      }
    });
    frame.getContentPane().add(new AccountPanel(), BorderLayout.CENTER);
    frame.setResizable(false);
    frame.pack();
    //    frame.setLocationByPlatform(true);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }


  public static void main(String[] args)
  {
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        createAndShowGUI();
      }
    });
  }
}
