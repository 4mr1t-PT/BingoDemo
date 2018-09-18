import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.fasterxml.jackson.dataformat.cbor.CBORGenerator;
import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.datatype.json.JSON;
import com.pushtechnology.diffusion.datatype.json.JSONDataType;

public class BingoBoard {

	private static List<Integer> UnusedBNumbers;
	private static List<Integer> UnusedINumbers;
	private static List<Integer> UnusedNNumbers;
	private static List<Integer> UnusedGNumbers;
	private static List<Integer> UnusedONumbers;
	static JButton btn_col0_0;
	private static JButton btn_col0_1;
	private static JButton btn_col0_2;
	private static JButton btn_col0_3;
	private static JButton btn_col0_4;
	private static JButton btn_col1_0;
	private static JButton btn_col1_1;
	private static JButton btn_col1_2;
	private static JButton btn_col1_3;
	private static JButton btn_col1_4;
	private static JButton btn_col2_0;
	private static JButton btn_col2_1;
	private static JButton btn_col2_2;
	private static JButton btn_col2_3;
	private static JButton btn_col2_4;
	private static JButton btn_col3_0;
	private static JButton btn_col3_1;
	private static JButton btn_col3_2;
	private static JButton btn_col3_3;
	private static JButton btn_col3_4;
	private static JButton btn_col4_0;
	private static JButton btn_col4_1;
	private static JButton btn_col4_2;
	private static JButton btn_col4_3;
	private static JButton btn_col4_4;
	private static JButton btn_Bingo;
	private static int[][] bingoBoard;
	// private static Subscriber subscriber;
	private static JsonPublisher publisher;
	private static JLabel lblB;
	static List<String> bingoStringsCalled;
	private static JLabel lblI;
	private static JLabel lblN;
	private static JLabel lblO;
	private static JLabel lblG;
	private JLabel greenBallLabel;
	private JLabel redBallLabel;
	private JLabel tealBalllabel;
	private JLabel yellowBalllabel;
	private JLabel blueBalllabel;
	private final CBORFactory cborFactory = new CBORFactory();
    private final JSONDataType jsonDataType = Diffusion.dataTypes().json();

	public BingoBoard() throws InterruptedException, ExecutionException, TimeoutException, IOException {
		UnusedBNumbers = generateNumberLists(1, 15);
		UnusedINumbers = generateNumberLists(16, 30);
		UnusedNNumbers = generateNumberLists(31, 45);
		UnusedGNumbers = generateNumberLists(46, 60);
		UnusedONumbers = generateNumberLists(61, 75);
        cborFactory.setCodec(new ObjectMapper());

		bingoStringsCalled = new ArrayList<>();
		generateBingoBoard();

		// subscriber = new Subscriber();

		// System.out.println(subscriber.bingoStringsPlayed());
		JFrame f = new JFrame("A JFrame");
		f.getContentPane().setBackground(new Color(0, 102, 255));
		f.setTitle("Bingo");
		// f.setUndecorated(true);
		// f.setShape(new RoundRectangle2D.Double(0,0,700,890,80,80));
		f.setSize(710, 898);
		f.setLocation(300, 200);
		f.getContentPane().setLayout(null);

		btn_col0_0 = new JButton("");
		btn_col0_0.setBackground(new Color(255, 255, 255));
		btn_col0_0.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col0_0.setBounds(96, 273, 96, 84);
		btn_col0_0.setText(bingoBoard[0][0] + "");
		btn_col0_0.setIcon(new javax.swing.ImageIcon(getClass().getResource("")));
		// btn_col0_0.setBorder(new RoundedBorder(10));
		// btn_0_0.setBackground(Color.RED);
		f.getContentPane().add(btn_col0_0);
		btn_col0_0.addActionListener(new ButtonListener());

		btn_col1_0 = new JButton("");
		btn_col1_0.setBackground(new Color(255, 255, 255));
		btn_col1_0.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col1_0.setBounds(196, 273, 96, 84);
		btn_col1_0.setText(bingoBoard[0][1] + "");
		btn_col1_0.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col1_0);

		btn_col2_0 = new JButton("");
		btn_col2_0.setBackground(new Color(255, 255, 255));
		btn_col2_0.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col2_0.setBounds(296, 273, 96, 84);
		btn_col2_0.setText(bingoBoard[0][2] + "");
		btn_col2_0.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col2_0);

		btn_col3_0 = new JButton("");
		btn_col3_0.setBackground(new Color(255, 255, 255));
		btn_col3_0.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col3_0.setBounds(396, 273, 96, 84);
		btn_col3_0.setText(bingoBoard[0][3] + "");
		btn_col3_0.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col3_0);

		btn_col4_0 = new JButton("");
		btn_col4_0.setBackground(new Color(255, 255, 255));
		btn_col4_0.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col4_0.setBounds(497, 273, 90, 84);
		btn_col4_0.setText(bingoBoard[0][4] + "");
		btn_col4_0.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col4_0);

		btn_col0_1 = new JButton("");
		btn_col0_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col0_1.setBackground(new Color(255, 255, 255));
		btn_col0_1.setBounds(96, 360, 96, 84);
		btn_col0_1.setText(bingoBoard[1][0] + "");
		btn_col0_1.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col0_1);

		btn_col1_1 = new JButton("");
		btn_col1_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col1_1.setBackground(new Color(255, 255, 255));
		btn_col1_1.setBounds(196, 360, 96, 84);
		btn_col1_1.setText(bingoBoard[1][1] + "");
		btn_col1_1.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col1_1);

		btn_col2_1 = new JButton("");
		btn_col2_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col2_1.setBackground(new Color(255, 255, 255));
		btn_col2_1.setBounds(296, 360, 96, 84);
		btn_col2_1.setText(bingoBoard[1][2] + "");
		btn_col2_1.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col2_1);

		btn_col3_1 = new JButton("");
		btn_col3_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col3_1.setBackground(new Color(255, 255, 255));
		btn_col3_1.setBounds(396, 360, 96, 84);
		btn_col3_1.setText(bingoBoard[1][3] + "");
		btn_col3_1.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col3_1);

		btn_col4_1 = new JButton("");
		btn_col4_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col4_1.setBackground(new Color(255, 255, 255));
		btn_col4_1.setBounds(497, 360, 90, 84);
		btn_col4_1.setText(bingoBoard[1][4] + "");
		btn_col4_1.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col4_1);

		btn_col0_2 = new JButton("");
		btn_col0_2.setBackground(new Color(255, 255, 255));
		btn_col0_2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col0_2.setBounds(96, 448, 96, 84);
		btn_col0_2.setText(bingoBoard[2][0] + "");
		btn_col0_2.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col0_2);

		btn_col1_2 = new JButton("");
		btn_col1_2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col1_2.setBackground(new Color(255, 255, 255));
		btn_col1_2.setBounds(196, 448, 96, 84);
		btn_col1_2.setText(bingoBoard[2][1] + "");
		btn_col1_2.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col1_2);

		btn_col2_2 = new JButton("");
		btn_col2_2.setBackground(new Color(255, 255, 255));
		btn_col2_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_col2_2.setBounds(296, 448, 96, 84);
		btn_col2_2.setText("FREE");
		btn_col2_2.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col2_2);

		btn_col3_2 = new JButton("");
		btn_col3_2.setBackground(new Color(255, 255, 255));
		btn_col3_2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col3_2.setBounds(396, 448, 96, 84);
		btn_col3_2.setText(bingoBoard[2][3] + "");
		btn_col3_2.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col3_2);

		btn_col4_2 = new JButton("");
		btn_col4_2.setBackground(new Color(255, 255, 255));
		btn_col4_2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col4_2.setBounds(497, 448, 90, 84);
		btn_col4_2.setText(bingoBoard[2][4] + "");
		btn_col4_2.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col4_2);

		btn_col0_3 = new JButton("");
		btn_col0_3.setBackground(new Color(255, 255, 255));
		btn_col0_3.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col0_3.setBounds(96, 536, 96, 84);
		btn_col0_3.setText(bingoBoard[3][0] + "");
		btn_col0_3.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col0_3);

		btn_col1_3 = new JButton("");
		btn_col1_3.setBackground(new Color(255, 255, 255));
		btn_col1_3.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col1_3.setBounds(196, 536, 96, 84);
		btn_col1_3.setText(bingoBoard[3][1] + "");
		btn_col1_3.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col1_3);

		btn_col2_3 = new JButton("");
		btn_col2_3.setBackground(new Color(255, 255, 255));
		btn_col2_3.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col2_3.setBounds(296, 536, 96, 84);
		btn_col2_3.setText(bingoBoard[3][2] + "");
		btn_col2_3.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col2_3);

		btn_col3_3 = new JButton("");
		btn_col3_3.setBackground(new Color(255, 255, 255));
		btn_col3_3.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col3_3.setBounds(396, 536, 96, 84);
		btn_col3_3.setText(bingoBoard[3][3] + "");
		btn_col3_3.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col3_3);

		btn_col4_3 = new JButton("");
		btn_col4_3.setBackground(new Color(255, 255, 255));
		btn_col4_3.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col4_3.setBounds(497, 536, 90, 84);
		btn_col4_3.setText(bingoBoard[3][4] + "");
		btn_col4_3.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col4_3);

		btn_col4_4 = new JButton("");
		btn_col4_4.setBackground(new Color(255, 255, 255));
		btn_col4_4.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col4_4.setBounds(497, 624, 90, 84);
		btn_col4_4.setText(bingoBoard[4][4] + "");
		btn_col4_4.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col4_4);

		btn_col3_4 = new JButton("");
		btn_col3_4.setBackground(new Color(255, 255, 255));
		btn_col3_4.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col3_4.setBounds(396, 624, 96, 84);
		btn_col3_4.setText(bingoBoard[4][3] + "");
		btn_col3_4.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col3_4);

		btn_col2_4 = new JButton("");
		btn_col2_4.setBackground(new Color(255, 255, 255));
		btn_col2_4.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col2_4.setBounds(296, 624, 96, 84);
		btn_col2_4.setText(bingoBoard[4][2] + "");
		btn_col2_4.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col2_4);

		btn_col1_4 = new JButton("");
		btn_col1_4.setBackground(new Color(255, 255, 255));
		btn_col1_4.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col1_4.setBounds(196, 624, 96, 84);
		btn_col1_4.setText(bingoBoard[4][1] + "");
		btn_col1_4.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col1_4);

		btn_col0_4 = new JButton("");
		btn_col0_4.setBackground(new Color(255, 255, 255));
		btn_col0_4.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btn_col0_4.setBounds(96, 624, 96, 84);
		btn_col0_4.setText(bingoBoard[4][0] + "");
		btn_col0_4.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_col0_4);

		btn_Bingo = new JButton("BINGO");
		btn_Bingo.setForeground(Color.RED);
		btn_Bingo.setFont(new Font("Tahoma", Font.PLAIN, 45));
		btn_Bingo.setBounds(256, 750, 177, 65);
		btn_Bingo.addActionListener(new ButtonListener());
		f.getContentPane().add(btn_Bingo);

		lblB = new JLabel("B");
		lblB.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblB.setBounds(45, 71, 77, 65);
		f.getContentPane().add(lblB);

		lblI = new JLabel("I");
		lblI.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblI.setBounds(185, 72, 69, 62);
		f.getContentPane().add(lblI);

		lblO = new JLabel("O");
		lblO.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblO.setBounds(578, 72, 69, 62);
		f.getContentPane().add(lblO);

		lblN = new JLabel("N");
		lblN.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblN.setBounds(315, 72, 69, 62);
		f.getContentPane().add(lblN);

		lblG = new JLabel("G");
		lblG.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblG.setBounds(442, 72, 69, 62);
		f.getContentPane().add(lblG);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(99, 152, 500, 136);
		ImageIcon icon = new ImageIcon("C:\\Users\\akandola\\amritsworkspace\\Bingo\\src\\bingo_new.png");
		Image scaleImage = icon.getImage().getScaledInstance(500, 120, Image.SCALE_DEFAULT);
		lblNewLabel.setIcon(new javax.swing.ImageIcon(scaleImage));
		f.getContentPane().add(lblNewLabel);

		greenBallLabel = new JLabel("");
		greenBallLabel.setBounds(15, 32, 133, 136);
		ImageIcon greenIcon = new ImageIcon("C:\\Users\\akandola\\amritsworkspace\\Bingo\\src\\greenball.png");
		Image scalegreenIcon = greenIcon.getImage().getScaledInstance(130, 150, Image.SCALE_DEFAULT);
		greenBallLabel.setIcon(new javax.swing.ImageIcon(scalegreenIcon));
		f.getContentPane().add(greenBallLabel);

		redBallLabel = new JLabel("");
		redBallLabel.setBounds(148, 32, 133, 136);
		ImageIcon redIcon = new ImageIcon("C:\\Users\\akandola\\amritsworkspace\\Bingo\\src\\redball.png");
		Image scaleredIcon = redIcon.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT);
		redBallLabel.setIcon(new javax.swing.ImageIcon(scaleredIcon));
		f.getContentPane().add(redBallLabel);

		tealBalllabel = new JLabel("");
		tealBalllabel.setBounds(285, 32, 133, 136);
		ImageIcon tealIcon = new ImageIcon("C:\\Users\\akandola\\amritsworkspace\\Bingo\\src\\tealball.png");
		Image scaletealIcon = tealIcon.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT);
		tealBalllabel.setIcon(new javax.swing.ImageIcon(scaletealIcon));
		f.getContentPane().add(tealBalllabel);

		yellowBalllabel = new JLabel("");
		yellowBalllabel.setBounds(414, 32, 133, 136);
		ImageIcon yellowIcon = new ImageIcon("C:\\Users\\akandola\\amritsworkspace\\Bingo\\src\\yellowball.png");
		Image scaleyellowIcon = yellowIcon.getImage().getScaledInstance(130, 135, Image.SCALE_DEFAULT);
		yellowBalllabel.setIcon(new javax.swing.ImageIcon(scaleyellowIcon));
		f.getContentPane().add(yellowBalllabel);

		blueBalllabel = new JLabel("");
		blueBalllabel.setBounds(554, 32, 133, 136);
		ImageIcon blueIcon = new ImageIcon("C:\\Users\\akandola\\amritsworkspace\\Bingo\\src\\blueball.png");
		Image scaleblueIcon = blueIcon.getImage().getScaledInstance(130, 135, Image.SCALE_DEFAULT);
		blueBalllabel.setIcon(new javax.swing.ImageIcon(scaleblueIcon));
		f.getContentPane().add(blueBalllabel);

		f.setVisible(true);

		publisher = new JsonPublisher();
		JsonPublisher.generatePossibleBingoStrings();
		publisher.updateServer();

	}

	public static boolean isBingoNumberCalled(String value) {
		return (bingoStringsCalled.contains(value));
	}

	/*
	 * public static String currentValue () { return subscriber.getCurrentValue(); }
	 */

	public static boolean letterCurrentValue(String value, String letter) {
		return (value.startsWith(letter));
	}

	public static void updateFields(String value) {
		if (letterCurrentValue(value, "B")) {
			System.out.println("Should update B");
			if (lblB != null) {
				lblB.setText(value);
			}
		} else if (letterCurrentValue(value, "I")) {
			System.out.println("Should update B");
			if (lblI != null) {
				lblI.setText(value);
			}

		} else if (letterCurrentValue(value, "N")) {
			System.out.println("Should update B");
			if (lblN != null) {
				lblN.setText(value);
			}
		} else if (letterCurrentValue(value, "G")) {
			System.out.println("Should update B");
			if (lblG != null) {
				lblG.setText(value);
			}
		} else if (letterCurrentValue(value, "O")) {
			System.out.println("Should update B");
			if (lblO != null) {
				lblO.setText(value);
			}
		}
	}

	private static class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == btn_col0_0 && isBingoNumberCalled("B" + bingoBoard[0][0])) {
				btn_col0_0.setBackground(Color.RED);
				bingoBoard[0][0] = 0;
			} else if (arg0.getSource() == btn_col0_1 && isBingoNumberCalled("B" + bingoBoard[1][0])) {
				btn_col0_1.setBackground(Color.RED);
				bingoBoard[1][0] = 0;
			} else if (arg0.getSource() == btn_col0_2 && isBingoNumberCalled("B" + bingoBoard[2][0])) {
				btn_col0_2.setBackground(Color.RED);
				bingoBoard[2][0] = 0;
			} else if (arg0.getSource() == btn_col0_3 && isBingoNumberCalled("B" + bingoBoard[3][0])) {
				btn_col0_3.setBackground(Color.RED);
				bingoBoard[3][0] = 0;
			} else if (arg0.getSource() == btn_col0_4 && isBingoNumberCalled("B" + bingoBoard[4][0])) {
				btn_col0_4.setBackground(Color.RED);
				bingoBoard[4][0] = 0;
			} else if (arg0.getSource() == btn_col1_0 && isBingoNumberCalled("I" + bingoBoard[0][1])) {
				btn_col1_0.setBackground(Color.RED);
				bingoBoard[0][1] = 0;
			} else if (arg0.getSource() == btn_col1_1 && isBingoNumberCalled("I" + bingoBoard[1][1])) {
				btn_col1_1.setBackground(Color.RED);
				bingoBoard[1][1] = 0;
			} else if (arg0.getSource() == btn_col1_2 && isBingoNumberCalled("I" + bingoBoard[2][1])) {
				btn_col1_2.setBackground(Color.RED);
				bingoBoard[2][1] = 0;
			} else if (arg0.getSource() == btn_col1_3 && isBingoNumberCalled("I" + bingoBoard[3][1])) {
				btn_col1_3.setBackground(Color.RED);
				bingoBoard[3][1] = 0;
			} else if (arg0.getSource() == btn_col1_4 && isBingoNumberCalled("I" + bingoBoard[4][1])) {
				btn_col1_4.setBackground(Color.RED);
				bingoBoard[4][1] = 0;
			} else if (arg0.getSource() == btn_col2_0 && isBingoNumberCalled("N" + bingoBoard[0][2])) {
				btn_col2_0.setBackground(Color.RED);
				bingoBoard[0][2] = 0;
			} else if (arg0.getSource() == btn_col2_1 && isBingoNumberCalled("N" + bingoBoard[1][2])) {
				btn_col2_1.setBackground(Color.RED);
				bingoBoard[1][2] = 0;
			} else if (arg0.getSource() == btn_col2_2) {
				btn_col2_2.setBackground(Color.RED);
				bingoBoard[2][2] = 0;
			} else if (arg0.getSource() == btn_col2_3 && isBingoNumberCalled("N" + bingoBoard[3][2])) {
				btn_col2_3.setBackground(Color.RED);
				bingoBoard[3][2] = 0;
			} else if (arg0.getSource() == btn_col2_4 && isBingoNumberCalled("N" + bingoBoard[4][2])) {
				btn_col2_4.setBackground(Color.RED);
				bingoBoard[4][2] = 0;
			} else if (arg0.getSource() == btn_col3_0 && isBingoNumberCalled("G" + bingoBoard[0][3])) {
				btn_col3_0.setBackground(Color.RED);
				bingoBoard[0][3] = 0;
			} else if (arg0.getSource() == btn_col3_1 && isBingoNumberCalled("G" + bingoBoard[1][3])) {
				btn_col3_1.setBackground(Color.RED);
				bingoBoard[1][3] = 0;
			} else if (arg0.getSource() == btn_col3_2 && isBingoNumberCalled("G" + bingoBoard[2][3])) {
				btn_col3_2.setBackground(Color.RED);
				bingoBoard[2][3] = 0;
			} else if (arg0.getSource() == btn_col3_3 && isBingoNumberCalled("G" + bingoBoard[3][3])) {
				btn_col3_3.setBackground(Color.RED);
				bingoBoard[3][3] = 0;
			} else if (arg0.getSource() == btn_col3_4 && isBingoNumberCalled("G" + bingoBoard[4][3])) {
				btn_col3_4.setBackground(Color.RED);
				bingoBoard[4][3] = 0;
			} else if (arg0.getSource() == btn_col4_0 && isBingoNumberCalled("O" + bingoBoard[0][4])) {
				btn_col4_0.setBackground(Color.RED);
				bingoBoard[0][4] = 0;
			} else if (arg0.getSource() == btn_col4_1 && isBingoNumberCalled("O" + bingoBoard[1][4])) {
				btn_col4_1.setBackground(Color.RED);
				bingoBoard[1][4] = 0;
			} else if (arg0.getSource() == btn_col4_2 && isBingoNumberCalled("O" + bingoBoard[2][4])) {
				btn_col4_2.setBackground(Color.RED);
				bingoBoard[2][4] = 0;
			} else if (arg0.getSource() == btn_col4_3 && isBingoNumberCalled("O" + bingoBoard[3][4])) {
				btn_col4_3.setBackground(Color.RED);
				bingoBoard[3][4] = 0;
			} else if (arg0.getSource() == btn_col4_4 && isBingoNumberCalled("O" + bingoBoard[4][4])) {
				btn_col4_4.setBackground(Color.RED);
				bingoBoard[4][4] = 0;
			} else if (arg0.getSource() == btn_Bingo) {
				if (checkBingo()) {
					List<String> winner = new ArrayList<String>();
					winner.add("WINBINGO");
					try {
						publisher.updateServerWithValue(winner);
					} catch (InterruptedException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static List<Integer> generateNumberLists(int min, int max) {
		List<Integer> UnusedNumbers = new ArrayList<>();
		for (int i = min; i <= max; i++) {
			UnusedNumbers.add(i);
		}
		return UnusedNumbers;
	}

	public static boolean checkBingo() {
		// checks the verticals and rows
		for (int col = 0; col < 5; col++) {
			for (int row = 0; row < 5; row++) {
				if (bingoBoard[row][0] == 0 && bingoBoard[row][1] == 0 && bingoBoard[row][2] == 0
						&& bingoBoard[row][3] == 0 && bingoBoard[row][4] == 0) {
					publisher.setBingo(true);
					return true;
				} else if (bingoBoard[0][col] == 0 && bingoBoard[1][col] == 0 && bingoBoard[2][col] == 0
						&& bingoBoard[3][col] == 0 && bingoBoard[4][col] == 0) {
					publisher.setBingo(true);
					return true;
				}
			}
		} 
		// checks diagonals
		if (bingoBoard[0][0] == 0 && bingoBoard[1][1] == 0 && bingoBoard[2][2] == 0 && bingoBoard[3][3] == 0
				&& bingoBoard[4][4] == 0) {
			publisher.setBingo(true);
			return true;
		} else if (bingoBoard[4][0] == 0 && bingoBoard[3][1] == 0 && bingoBoard[2][2] == 0 && bingoBoard[1][3] == 0
				&& bingoBoard[0][4] == 0) {
			publisher.setBingo(true);
			return true;
		}
		return false;

	}

	public static int randBingoInt(int column) {

		int randomIndex;
		int randomInt;
		if (column == 0) {
			randomIndex = (randInt(0, UnusedBNumbers.size() - 1));
			randomInt = UnusedBNumbers.get(randomIndex);
			UnusedBNumbers.remove(randomIndex);
			return randomInt;
		} else if (column == 1) {
			randomIndex = (randInt(0, UnusedINumbers.size() - 1));
			randomInt = UnusedINumbers.get(randomIndex);
			UnusedINumbers.remove(randomIndex);
			return randomInt;
		} else if (column == 2) {
			randomIndex = (randInt(0, UnusedNNumbers.size() - 1));
			randomInt = UnusedNNumbers.get(randomIndex);
			UnusedNNumbers.remove(randomIndex);
			return randomInt;
		} else if (column == 3) {
			randomIndex = (randInt(0, UnusedGNumbers.size() - 1));
			randomInt = UnusedGNumbers.get(randomIndex);
			UnusedGNumbers.remove(randomIndex);
			return randomInt;
		} else {
			randomIndex = (randInt(0, UnusedONumbers.size() - 1));
			randomInt = UnusedONumbers.get(randomIndex);
			UnusedONumbers.remove(randomIndex);
			return randomInt;
		}

	}

	public static String generateBingoString(String letter, int min, int max) {
		int bingoNumber = randInt(min, max);
		String bingoString = (letter + bingoNumber);
		return bingoString;
	}

	public static int[][] generateBingoBoard() {

		System.out.println("B     I     N     G     O");

		bingoBoard = new int[5][5];
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {

				bingoBoard[row][col] = randBingoInt(col);
				System.out.print("[ " + bingoBoard[row][col] + " ]");
			}
			System.out.println();
		}
		return bingoBoard;

	}
		
}
