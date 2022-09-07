package com.ffait.report;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.ffait.util.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;

public class ReportFrame {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/*
	 * 
	 * textName textInfo textReport lblCrame btnPrint btnExit lblPhoto
	 */

	static FaceService fs = new FaceService();
	static JFrame frame;
	static JTextArea textName;
	static JTextArea textInfo;
	static JTextArea textReport;
	static JLabel lblCrame;
	static JButton btnPrint;
	static JButton btnExit;
	static JLabel lblPhoto;
	static JLabel message;
	static JLabel lblbackPhoto;
	static int flag = 0;
	// 当前工作工作状态 true :人脸识别 false:报告分析展示
	static boolean state = true;

	static BufferedImage showImg;
	static String backgroundPath = "C:\\parameter\\okBack.jpeg";
	static String facePath = "C:\\parameter\\faceImage.jpg";
	static VideoCapture camera;
	
	static Timer timer = new Timer("退出打印界面");
    static TimerTask timerTask;

	public ReportFrame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("培训报告打印");
		try {
			frame.setIconImage(ImageIO.read(new File("C:\\parameter\\nicola.jpg")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setBounds(0, 0, 1024, 960);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		lblPhoto = new JLabel("");
		lblPhoto.setBounds(850, 21, 141, 178);
		frame.add(lblPhoto);

		lblbackPhoto = new JLabel("");
		lblbackPhoto.setBounds(0, 0, 1024, 744);
		BufferedImage backPhoto=null;

		// 设置透明
		lblbackPhoto.setOpaque(false);
		frame.add(lblbackPhoto);
        //人员名称
		textName = new JTextArea();
		textName.setEditable(false);
		textName.setOpaque(false);
		textName.setBounds(390, 158, 120, 41);
		textName.setFont(new Font("黑体", Font.PLAIN, 25));
		textName.setBackground(new Color(238, 238, 238));
		frame.add(textName);
		textName.setColumns(10);
		//考核信息
		textInfo = new JTextArea();
		textInfo.setEditable(false);
		textInfo.setOpaque(false);
		textInfo.setBounds(105, 209, 699, 300);
		textInfo.setFont(new Font("黑体", Font.PLAIN, 25));
		textInfo.setLineWrap(true); // 激活自动换行功能
		textInfo.setWrapStyleWord(true); // 激活断行不断字功能
		textInfo.setBackground(new Color(238, 238, 238));
		frame.add(textInfo);
		textInfo.setColumns(10);
		//报告分析
		textReport = new JTextArea();
		textReport.setOpaque(false);
		textReport.setEditable(false);
		textReport.setBounds(340-50, 350, 878, 211);

		textReport.setFont(new Font("黑体", Font.PLAIN, 20));
		textReport.setBackground(new Color(238, 238, 238));
		frame.add(textReport);
		textReport.setColumns(10);
	

		// 提示信息栏
		message = new JLabel("培训结果分析", JLabel.CENTER);
		message.setFont(new Font("黑体", Font.PLAIN, 30));
		message.setForeground(Color.RED);
		message.setBounds(20, 50, 1024, 50);
		frame.add(message);

		lblCrame = new JLabel("");
		lblCrame.setBounds(32, 380, 960, 540);
		frame.add(lblCrame);

		btnPrint = new JButton("打印");
		btnPrint.setBackground(new Color(37, 102, 68));
		btnPrint.setForeground(Color.WHITE);
		btnPrint.setFont(new Font("黑体", Font.PLAIN, 35));
		// 打印按钮
		btnPrint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPrint.setVisible(false);
				btnExit.setVisible(false);
				lblCrame.setVisible(false);
				message.setVisible(false);
				try {
					NewPDF.createPDF(backgroundPath, facePath, textName.getText(), textInfo.getText(),
							textReport.getText());
					message.setText(PrintFrame.PrintPdf());
					message.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					btnPrint.setVisible(true);
					btnExit.setVisible(true);
				}
			}
		});
		btnPrint.setBounds(212, 750, 200, 80);
		frame.add(btnPrint);

		btnExit = new JButton("返回");
		btnExit.setBackground(new Color(37, 102, 68));
		btnExit.setForeground(Color.WHITE);
		btnExit.setFont(new Font("黑体", Font.PLAIN, 35));
		btnExit.setBounds(612, 750, 200, 80);
		// 返回按钮
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//取消任务并进入验证
				timerTask.cancel();
				state = true;
				setVisible(false);
				clearInfo();
				message.setText("");
			}
		});

		frame.add(btnExit);
		int x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - frame.getWidth()) / 2;
		int y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - frame.getHeight()) / 2;
		frame.setLocation(x, 0);
		// 设置部分组件可见性
		setVisible(false);
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportFrame window = new ReportFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		long pretime = System.currentTimeMillis();
		camera = new VideoCapture(0);
		if (!camera.isOpened()) {
			System.out.println("Camera Error");
		} else {
			Mat frame = new Mat();
			while (flag == 0) {
				camera.read(frame);
				BufferedImage bufferedImage = ImageBlur.gausssianBlur(frame);
				BufferedImage bi = fs.mat2BI(frame);
				long currenttime = System.currentTimeMillis();
				if (currenttime - pretime > 10000 && state) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							// long a=System.currentTimeMillis();
								if (true) {
								message.setText("人脸识别中，正在为您输出培训结果！");
								String s = fs.judgeMember(bi);
								
								if("noFace".equals(s)) {
									message.setText("未检测到人脸!");
								}
								else if ("noUser".equals(s)) {
									message.setText("当前用户未注册!");
								} else if (null == s || "" == s) {
									message.setText("未检测到人脸!");
									// 返回值 !null：验证通过
								}
								// 人脸验证通过
								else if (null != s && "" != s) {
									// 不在新增线程进行人脸识别，点击退出后再开启人脸识别
									System.out.println(s);
									state = false;
									int f1 = s.indexOf('_');
									int f2 = s.indexOf('_', f1 + 1);
									int f3 = s.indexOf('_', f2 + 1);
									int f4 = s.indexOf('_', f3 + 1);
									int f5 = s.indexOf('_', f4 + 1);
									String userID = s.substring(0, f1);
									String userCode = s.substring(f1 + 1, f2);
									String userName = s.substring(f2 + 1, f3);
									String roleId = s.substring(f3 + 1, f4);
									String photoUrl = s.substring(f4 + 1, f5);
									String projects = s.substring(f5 + 1);

									// 展示用的数据
									int avg = 0;
									String type = "";
									String projectNames = ParameterOperate.extract("projectNames");
									String projectIds = ParameterOperate.extract("projectIds");
									projectNames = projectNames.replace("\"", "");
									projectNames = projectNames.replace(" ", "");
									
									// 成绩展示用
									String[] pros = projectNames.split("_");
									// 构建tInfo
									projectNames = projectNames.replace('_', ',');

									String res = "";
									// 获取考核相关数据
									res = fs.reportAnalysis(userID, projectIds);

									BufferedImage photo = DownloadFromUrl.downloadBufferedImageFromUrl(
											ParameterOperate.extract("mainService") + photoUrl, "jpg");

									// 获取项目列表
									ArrayList<ExamResult> results = new ArrayList<ExamResult>();
									ExamResult result=null;
									try {
										results = (ArrayList<ExamResult>) JsonToObject.getExamresult(res);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									Date d = new Date();
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy年 MM 月 dd 日");
									String date = sdf.format(d);

									String tName = userName;
									String tInfo = "    " + date + "参加" + projectNames
											+ "培训考核，经考核合格，具备相应的专业知识和技能!";

									StringBuilder sBuilder = new StringBuilder();
									if (results != null && results.size() > 0) {
										for (int i = 0; i < results.size(); i++) {

											sBuilder.append(pros[i] + ":\t");
											if(pros[i].length()<=7) {
												sBuilder.append("\t");
												if(pros[i].length()<=3) {
													sBuilder.append("\t");
												}
											}
											sBuilder.append(results.get(i).getPoint() + "\n");
											avg += results.get(i).getPoint();
										}
										
										avg = Math.round((float)avg / (float)results.size());
										
										if (avg >= 80) {
											type = "优秀";
										} else if (avg >= 60) {
											type = "及格";
										} else if (avg < 60) {
											tInfo = date + "参加" + projectNames + "培训考核，考核未通过!";
											type = "不及格";
										}

									}

									sBuilder.append("\n总评平均成绩：" + avg + "分，" + type);
									String tReport = sBuilder.toString();

									

									textName.setText(tName);
									textInfo.setText(tInfo);
									textReport.setText(tReport);

									// 将需要打印的照片写入文件
									try {
										File outputfile = new File(facePath);
										ImageIO.write(photo, "jpg", outputfile);
									} catch (IOException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}

									// 转换部分组件可见性
									setVisible(true);

									lblPhoto.setIcon(
											new ImageIcon(photo.getScaledInstance(141, 178, Image.SCALE_DEFAULT)));
                                    
									message.setText("10秒后将自动返回，请打印成绩");
									message.setVisible(true);

									
									{
										timerTask = new TimerTask() {
									        public void run() {
									        	state = true;
												setVisible(false);
												clearInfo();
									        }
									    };
									    timer.schedule(timerTask,10000);
									}
									
									
//									try {
//										System.out.println(currenttime+"   sleep前->   " +(System.currentTimeMillis() - currenttime));
//
//										Thread.sleep(10000);
//										System.out.println(currenttime+"   sleep后->   " +(System.currentTimeMillis() - currenttime));
//
//										
//									} catch (InterruptedException e1) {
//										// TODO Auto-generated catch block
//										e1.printStackTrace();
//									}finally {
//										state = true;
//										setVisible(false);
//										clearInfo();
//									}
								
								}
								

							}
						}

					}).start();
					pretime = currenttime;
				}
				
				showImg = ImageUtils.deepCopy(bi);
				ImageBlur.drawFace(bufferedImage,showImg.getSubimage(350,190,270,340));
				lblCrame.setIcon(new ImageIcon(bufferedImage));
			}
		}
	}

	public static void setVisible(boolean state) {
		textName.setVisible(state);
		textInfo.setVisible(state);
		textReport.setVisible(state);
		btnPrint.setVisible(state);
		btnExit.setVisible(state);
		lblPhoto.setVisible(state);
		lblbackPhoto.setVisible(state);

		lblCrame.setVisible(!state);
		message.setVisible(!state);
	}

	public static void clearInfo() {

		textName.setText("");
		textInfo.setText("");
		textReport.setText("");
		lblPhoto.setIcon(null);
	}

}
