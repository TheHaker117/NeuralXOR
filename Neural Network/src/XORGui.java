import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class XORGui extends JFrame implements ActionListener {

	private JTextField tf_val00, tf_val01, tf_errmin;
	private JLabel lbl_xor, lbl_errmin, lbl_epochs;
	private JSpinner spn_epochs;
	private JButton btn_train, btn_test, btn_clean, btn_close;
	private JTextArea ta_log;
	private JScrollPane sp_log;

	private NeuralXOR neuralXOR;

	public XORGui(){
		super();
		config();
		init();
	}

	public static void main(String[] args){

		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run(){
				XORGui xor = new XORGui();
				xor.setVisible(true);
			}

		});
	}

	private void config(){
		this.setSize(400, 550);
		this.setTitle("XOR - Neural Network");
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setFont(new Font("Arial", Font.PLAIN, 14));

		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception exc){
			exc.printStackTrace();
		}
	}

	private void init(){

		lbl_errmin = new JLabel("Error mínimo:");
		lbl_errmin.setBounds(20, 20, 100, 25);
		lbl_errmin.setFont(this.getFont());

		tf_errmin = new JTextField();
		tf_errmin.setBounds(110, 20, 60, 25);
		tf_errmin.setFont(this.getFont());

		lbl_epochs = new JLabel("Épocas:");
		lbl_epochs.setBounds(20, 60, 80, 25);
		lbl_epochs.setFont(this.getFont());

		SpinnerModel model = new SpinnerNumberModel(5000, 5000, 100000, 1);

		spn_epochs = new JSpinner();
		spn_epochs.setModel(model);
		spn_epochs.setBounds(90, 60, 80, 25);
		spn_epochs.setFont(this.getFont());

		btn_train = new JButton("TRAIN");
		btn_train.setBounds(20, 100, 80, 30);
		btn_train.addActionListener(this);
		btn_train.setFont(this.getFont());

		tf_val00 = new JTextField();
		tf_val00.setBounds(220, 60, 35, 25);
		tf_val00.setFont(this.getFont());

		lbl_xor = new JLabel("XOR");
		lbl_xor.setBounds(260, 60, 30, 25);
		lbl_xor.setFont(this.getFont());

		tf_val01 = new JTextField();
		tf_val01.setBounds(295, 60, 35, 25);
		tf_val01.setFont(this.getFont());

		btn_test = new JButton("TEST");
		btn_test.setBounds(220, 100, 80, 30);
		btn_test.addActionListener(this);
		btn_test.setFont(this.getFont());

		ta_log = new JTextArea();
		ta_log.setEditable(false);
		sp_log = new JScrollPane(ta_log);
		sp_log.setBounds(20, 150, 350, 300);

		btn_clean = new JButton("Limpiar");
		btn_clean.setBounds(60, 470, 80, 30);
		btn_clean.addActionListener(this);
		btn_clean.setFont(this.getFont());

		btn_close = new JButton("Cerrar");
		btn_close.setBounds(250, 470, 80, 30);
		btn_close.addActionListener(this);
		btn_close.setFont(this.getFont());

		neuralXOR = new NeuralXOR(ta_log);

		this.add(tf_val00);
		this.add(lbl_xor);
		this.add(tf_val01);
		this.add(lbl_errmin);
		this.add(tf_errmin);
		this.add(lbl_epochs);
		this.add(spn_epochs);
		this.add(btn_train);
		this.add(btn_test);
		this.add(sp_log);
		this.add(btn_clean);
		this.add(btn_close);

	}

	@Override
	public void actionPerformed(ActionEvent evnt){

		if(evnt.getSource().equals(btn_train))
			neuralXOR.train((int) spn_epochs.getValue(), Double.valueOf(tf_errmin.getText()));

		if(evnt.getSource().equals(btn_test))
			neuralXOR.test(Integer.valueOf(tf_val00.getText()), Integer.valueOf(tf_val01.getText()));

		if(evnt.getSource().equals(btn_clean))
			ta_log.setText("");

		if(evnt.getSource().equals(btn_close))
			System.exit(0);

	}

}
