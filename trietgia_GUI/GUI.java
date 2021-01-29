package trietgia_GUI;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;



class GUI extends JFrame {

	
	
	public static final long serialVersionUID = -9102921349267475005L;
	public JPanel contentPane;
	public ArrayList<JLabel> forkLabelList = new ArrayList<>();
	public ArrayList<JLabel> philosopherLabelList = new ArrayList<>();
	public ArrayList<JProgressBar> progressBars = new ArrayList<>();
	public ArrayList<JLabel> zzzList = new ArrayList<>();
	public ArrayList<JLabel> finishedList = new ArrayList<>();
	//private Test1 diningApp;

	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Test_GUI frame = new Test_GUI();
//					frame.setVisible(true);
//	
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("DINING PHILOSOPHERS");
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 969, 660);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblPhilosopher_1 = new JLabel("Philosopher 1");
		lblPhilosopher_1.setOpaque(true);
		lblPhilosopher_1.setForeground(Color.WHITE);
		lblPhilosopher_1.setBackground(Color.BLACK);
		lblPhilosopher_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhilosopher_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhilosopher_1.setBounds(543, 206, 115, 70);
		philosopherLabelList.add(lblPhilosopher_1);
		contentPane.add(lblPhilosopher_1);
		
		JLabel lblPhilosopher_2 = new JLabel("Philosopher 2");
		lblPhilosopher_2.setOpaque(true);
		lblPhilosopher_2.setForeground(Color.WHITE);
		lblPhilosopher_2.setBackground(Color.BLACK);
		lblPhilosopher_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhilosopher_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhilosopher_2.setBounds(590, 402, 115, 70);
		philosopherLabelList.add(lblPhilosopher_2);
		contentPane.add(lblPhilosopher_2);
		
		JLabel lblPhilosopher_3 = new JLabel("Philosopher 3");
		lblPhilosopher_3.setOpaque(true);
		lblPhilosopher_3.setForeground(Color.WHITE);
		lblPhilosopher_3.setBackground(Color.BLACK);
		lblPhilosopher_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhilosopher_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhilosopher_3.setBounds(410, 501, 115, 71);
		philosopherLabelList.add(lblPhilosopher_3);
		contentPane.add(lblPhilosopher_3);
		
		JLabel lblPhilosopher_4 = new JLabel("Philosopher 4");
		lblPhilosopher_4.setOpaque(true);
		lblPhilosopher_4.setForeground(Color.WHITE);
		lblPhilosopher_4.setBackground(Color.BLACK);
		lblPhilosopher_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhilosopher_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhilosopher_4.setBounds(213, 402, 115, 70);
		philosopherLabelList.add(lblPhilosopher_4);
		contentPane.add(lblPhilosopher_4);
		
		JLabel lblPhilosopher = new JLabel("Philosopher 5");
		lblPhilosopher.setOpaque(true);
		lblPhilosopher.setBackground(Color.BLACK);
		lblPhilosopher.setForeground(Color.WHITE);
		lblPhilosopher.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhilosopher.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhilosopher.setBounds(257, 206, 115, 70);
		philosopherLabelList.add(lblPhilosopher);
		contentPane.add(lblPhilosopher);
		
		JLabel lblFork_1 = new JLabel("fork 1");
		lblFork_1.setBackground(new Color(205, 92, 92));
		lblFork_1.setOpaque(true);
		lblFork_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblFork_1.setBounds(571, 310, 66, 27);
		forkLabelList.add(lblFork_1);
		contentPane.add(lblFork_1);
		
		JLabel lblFork_2 = new JLabel("fork 2");
		lblFork_2.setBackground(new Color(205, 92, 92));
		lblFork_2.setOpaque(true);
		lblFork_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblFork_2.setBounds(504, 463, 66, 27);
		forkLabelList.add(lblFork_2);
		contentPane.add(lblFork_2);
		
		JLabel lblFork_3 = new JLabel("fork 3");
		lblFork_3.setBackground(new Color(205, 92, 92));
		lblFork_3.setOpaque(true);
		lblFork_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblFork_3.setBounds(346, 463, 66, 27);
		forkLabelList.add(lblFork_3);
		contentPane.add(lblFork_3);
		
		JLabel lblFork_4 = new JLabel("fork 4");
		lblFork_4.setBackground(new Color(205, 92, 92));
		lblFork_4.setOpaque(true);
		lblFork_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblFork_4.setBounds(306, 316, 66, 27);
		forkLabelList.add(lblFork_4);
		contentPane.add(lblFork_4);
		
		JLabel lblFork_5 = new JLabel("fork 5");
		lblFork_5.setBackground(new Color(205, 92, 92));
		lblFork_5.setOpaque(true);
		lblFork_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblFork_5.setBounds(425, 228, 66, 27);
		forkLabelList.add(lblFork_5);
		contentPane.add(lblFork_5);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setMaximum(50);
		progressBar_1.setBounds(630, 173, 146, 22);
		progressBars.add(progressBar_1);
		contentPane.add(progressBar_1);
		
		JProgressBar progressBar_2 = new JProgressBar();
		progressBar_2.setMaximum(50);
		progressBar_2.setBounds(630, 369, 146, 22);
		progressBars.add(progressBar_2);
		contentPane.add(progressBar_2);
		
		JProgressBar progressBar_3 = new JProgressBar();
		progressBar_3.setMaximum(50);
		progressBar_3.setBounds(397, 583, 146, 27);
		progressBars.add(progressBar_3);
		contentPane.add(progressBar_3);
		
		JProgressBar progressBar_4 = new JProgressBar();
		progressBar_4.setMaximum(50);
		progressBar_4.setBounds(102, 369, 146, 22);
		progressBars.add(progressBar_4);
		contentPane.add(progressBar_4);
		
		JProgressBar progressBar_5 = new JProgressBar();
		progressBar_5.setMaximum(50);
		progressBar_5.setBounds(158, 173, 146, 22);
		progressBars.add(progressBar_5);
		contentPane.add(progressBar_5);
		
		JTextArea txtrObserveTheDining = new JTextArea();
		txtrObserveTheDining.setEditable(false);
		//txtrObserveTheDining.setFont(new Font("Time", Font.BOLD, 13));
		txtrObserveTheDining.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txtrObserveTheDining.setLineWrap(true);
		txtrObserveTheDining.setText("- Có năm nhà triết gia, vừa suy nghĩ vừa ăn tối\r\n- Các triết gia ngồi trên một bàn tròn, trước mặt họ là các đĩa thức ăn, mỗi người một đĩa. Có 5 chiếc đũa được đặt xen kẽ giữa các triết gia \r\n"
				+ "- Thỉnh thoảng, một triết gia cảm thấy đói và cố gắng chọn hai chiếc đũa gần nhất\r\n- "
				+ "Họ chỉ có thể ăn khi mà cả 2 chiếc đũa bên trái và bên phải họ có sẵn \r\n- "
				+ "Khi một triết gia đói và có hai chiếc đũa cùng một lúc, ông ta ăn mà không đặt đũa xuống. Khi triết gia ăn xong, ông ta đặt đũa xuống và bắt đầu suy nghĩ tiếp:)\r\n-"
				+ " ");
		txtrObserveTheDining.setBounds(40, 11, 843, 141);
		contentPane.add(txtrObserveTheDining);
		
		JLabel lblZzz1 = new JLabel("zzz...");
		lblZzz1.setVisible(false);
		lblZzz1.setBounds(668, 206, 46, 14);
		zzzList.add(lblZzz1);
		contentPane.add(lblZzz1);
		
		JLabel lblZzz2 = new JLabel("zzz...");
		lblZzz2.setVisible(false);
		lblZzz2.setBounds(715, 402, 46, 14);
		zzzList.add(lblZzz2);
		contentPane.add(lblZzz2);
		
		JLabel lblZzz3 = new JLabel("zzz...");
		lblZzz3.setVisible(false);
		lblZzz3.setBounds(535, 501, 46, 14);
		zzzList.add(lblZzz3);
		contentPane.add(lblZzz3);
		
		JLabel lblZzz4 = new JLabel("zzz...");
		lblZzz4.setVisible(false);
		lblZzz4.setBounds(337, 402, 46, 14);
		zzzList.add(lblZzz4);
		contentPane.add(lblZzz4);
		
		JLabel lblZzz5 = new JLabel("zzz...");
		lblZzz5.setVisible(false);
		lblZzz5.setBounds(382, 206, 46, 14);
		zzzList.add(lblZzz5);
		contentPane.add(lblZzz5);
		
		JLabel lblFinishedEating1 = new JLabel("Finished eating");
		lblFinishedEating1.setOpaque(true);
		lblFinishedEating1.setVisible(false);
		lblFinishedEating1.setBackground(new Color(255, 20, 147));
		lblFinishedEating1.setBounds(668, 235, 108, 20);
		finishedList.add(lblFinishedEating1);
		contentPane.add(lblFinishedEating1);
		
		JLabel lblFinishedEating2 = new JLabel("Finished eating");
		lblFinishedEating2.setOpaque(true);
		lblFinishedEating2.setVisible(false);
		lblFinishedEating2.setBackground(new Color(255, 20, 147));
		lblFinishedEating2.setBounds(712, 431, 108, 20);
		finishedList.add(lblFinishedEating2);
		contentPane.add(lblFinishedEating2);
		
		JLabel lblFinishedEating3 = new JLabel("Finished eating");
		lblFinishedEating3.setOpaque(true);
		lblFinishedEating3.setVisible(false);
		lblFinishedEating3.setBackground(new Color(255, 20, 147));
		lblFinishedEating3.setBounds(529, 530, 108, 20);
		finishedList.add(lblFinishedEating3);
		contentPane.add(lblFinishedEating3);
		
		JLabel lblFinishedEating4 = new JLabel("Finished eating");
		lblFinishedEating4.setOpaque(true);
		lblFinishedEating4.setVisible(false);
		lblFinishedEating4.setBackground(new Color(255, 20, 147));
		lblFinishedEating4.setBounds(102, 431, 108, 20);
		finishedList.add(lblFinishedEating4);
		contentPane.add(lblFinishedEating4);
		
		JLabel lblFinishedEating5 = new JLabel("Finished eating");
		lblFinishedEating5.setOpaque(true);
		lblFinishedEating5.setVisible(false);
		lblFinishedEating5.setBackground(new Color(255, 20, 147));
		lblFinishedEating5.setBounds(139, 235, 108, 20);
		finishedList.add(lblFinishedEating5);
		contentPane.add(lblFinishedEating5);
		
		//startDining();
	}
	
	
	
	
	

		
	
	
//		
//		private void startDining() {
//			diningApp.start();
//			Timer timer = new Timer();
//			timer.scheduleAtFixedRate(new TimerTask() {
//				@Override
//				public void run() {
//					for(Philosopher philosopher : diningApp.getPhilosophers()){
//						if(!philosopher.isFinished()){
//							updateGui();
//						}
//						else {
//							timeForBed(philosopher);
//						}
//					}
//				}
//			}, 2000, 1000);
//		}

//		private void updateGui() {
//			int philosopherCounter = 0;
//			for(Philosopher philosopher : diningApp.getPhilosophers()){
//				progressBars.get(philosopherCounter).setValue(philosopher.getAmountEaten());
//				++philosopherCounter;
//			}
//			setPhilosopherColour();
//			checkIfSnoozing();
//		}

		
		
		
//		private void setPhilosopherColour() {
//			for(Monitor philosopher : diningApp.getPhilosophers()){
//				if(philosopher.isEating()){
//					changeColourToGreen(philosopher);
//				}else {
//					changeColourToBlack(philosopher);
//				}
//			}
//		}
	
	
	
	public void changeColourToBlack(int i) {
		switch (i) {
		case 1:
			philosopherLabelList.get(0).setBackground(Color.BLACK);
			break;
		case 2:
			philosopherLabelList.get(1).setBackground(Color.BLACK);
			break;
		case 3:
			philosopherLabelList.get(2).setBackground(Color.BLACK);
			break;
		case 4:
			philosopherLabelList.get(3).setBackground(Color.BLACK);
			break;
		case 5:
			philosopherLabelList.get(4).setBackground(Color.BLACK);
			break;
		default:
			break;
		}
	}

	public void changeColourToGreen(int i) {
		switch (i) {
		case 1:
			philosopherLabelList.get(0).setBackground(Color.GREEN);
			break;
		case 2:
			philosopherLabelList.get(1).setBackground(Color.GREEN);
			break;
		case 3:
			philosopherLabelList.get(2).setBackground(Color.GREEN);
			break;
		case 4:
			philosopherLabelList.get(3).setBackground(Color.GREEN);
			break;
		case 5:
			philosopherLabelList.get(4).setBackground(Color.GREEN);
			break;
		default:
			break;
		}
	}
	
	public void changeColourToBlue(int i) {
		switch (i) {
		case 1:
			philosopherLabelList.get(0).setBackground(Color.blue);
			break;
		case 2:
			philosopherLabelList.get(1).setBackground(Color.blue);
			break;
		case 3:
			philosopherLabelList.get(2).setBackground(Color.blue);
			break;
		case 4:
			philosopherLabelList.get(3).setBackground(Color.blue);
			break;
		case 5:
			philosopherLabelList.get(4).setBackground(Color.blue);
			break;
		default:
			break;
		}
	}
		
		public void setColourToBlack(int i) {
			philosopherLabelList.get(0).setBackground(Color.BLACK);
			philosopherLabelList.get(1).setBackground(Color.BLACK);
			philosopherLabelList.get(2).setBackground(Color.BLACK);
			philosopherLabelList.get(3).setBackground(Color.BLACK);
			philosopherLabelList.get(4).setBackground(Color.BLACK);
			
	}
		
		
//		private void setPhilosopherColour() {
//			Monitor philosopher;
//			for(int i=0; i<5; i++){
//				if(philosopher.state[i]==1){
//					changeColourToGreen(philosopher);
//				}else {
//					changeColourToBlack(philosopher);
//				}
//			}
//		}
}