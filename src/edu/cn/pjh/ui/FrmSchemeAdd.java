package edu.cn.pjh.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cn.pjh.PPutil;
import edu.cn.pjh.util.BaseException;

public class FrmSchemeAdd extends JDialog implements ActionListener{
private int bsnid;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelNeed = new JLabel("Ҫ���");
	private JLabel labelReduce = new JLabel("�Żݽ�");
	private JLabel labelif = new JLabel("�Ƿ����Ż�ȯ���ӣ�");
	private JTextField edtReduce = new JTextField(20);
	private JTextField edtNeed = new JTextField(20);
	private JComboBox cmbif=new JComboBox(new String[]{"��","��"});
	//private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	//private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	

	public FrmSchemeAdd(JDialog f, String s, boolean b,int bsnid) {
		super(f, s, b);
		this.bsnid=bsnid;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelNeed);
		workPane.add(edtNeed);
		workPane.add(labelReduce);
		workPane.add(edtReduce);
		workPane.add(labelif);
		workPane.add(this.cmbif);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(360, 180);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			float reduce=Float.parseFloat(this.edtReduce.getText());
			float need=Float.parseFloat(this.edtNeed.getText());
			String ifcpn=this.cmbif.getSelectedItem().toString();
			try {
				PPutil.SchemeManager.createScheme(this.bsnid,need,reduce,ifcpn);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}
