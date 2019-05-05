package com.neuedu.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Alter {

	private static Properties prop;
	static {
		prop = new Properties();
		try {
			prop.load(Object.class.getResourceAsStream("/setting.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void alter(Scanner scanner) {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("+1.添加这门课程的学生  2.删除选择这门课的学生   3.返回主界面  4.系统退出 ");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("请选择操作:");
		int nextInt = scanner.nextInt();
		switch (nextInt) {
		case 1:
			// 通过学生号,查询该学生选课信息
			addStudentPage(scanner);
			System.out.println("数据查询完毕,系统自动返回目录......");
			alter(scanner);
			break;
		case 2:
			// 2.通过课程号,打印当前课程下的学生信息
			delectStudentPage(scanner);
			System.out.println("数据修改完毕,系统自动返回目录......");
			alter(scanner);
			break;
		
		case 3:
			Client c = new Client();
			c.stuManagerIndex();
			return;
		case 4:
			System.out.println("系统退出成功");
			return;
		default:
			System.out.println("输入有误,请重新输入");
			alter(scanner);
			break;
		}

	}
	
	
	//1.添加这门课程的学生
	//添加学生界面
		public void addStudentPage(Scanner scanner) {
			System.out.println("请输入添加学生所选课程");
			int cid = scanner.nextInt();
			System.out.println("请输入添加学生的ID");
			int id = scanner.nextInt();
			System.out.println("请输入添加学生的姓名");
			String name = scanner.next();
			System.out.println("请输入添加学生的年龄");
			int age = scanner.nextInt();
			System.out.println("请输入添加学生的性别");
			String sex = scanner.next();
			
			Student stu = new Student(id, name, age, sex, cid);
			add(stu);
		}
		//增加学生方法
		public void add(Student stu) {
				PreparedStatement ps =null;
				Connection conn =null;
				try {
					//获得连接
					conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
					//预编译sql
					String sql = "INSERT INTO student VALUES(?,?,?,?,?)";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, stu.getsId());
					ps.setString(2, stu.getsName());
					ps.setDouble(3, stu.getaAge());
					ps.setString(4, stu.getsSex());
					ps.setInt(5, stu.getcId());
					//执行sql语句
					int executeUpdate = ps.executeUpdate();
					if(executeUpdate != 0) {
						System.out.println("添加成功");
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					if(null != ps) {
						try {
							ps.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(null != conn) {
						try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		
		//删除学生界面
		public void delectStudentPage(Scanner scanner) {
			System.out.println("请输入删除学生所选课程的id");
			int id = scanner.nextInt();
			delectStudent(id);
			
		}
		//删除学生
			public void delectStudent(int num) {
				
				Connection conn = null;
				PreparedStatement ps = null;
				try {
					conn = DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("username"),prop.getProperty("password"));
					String sql = "DELETE from student where cId=?";
					ps = conn.prepareStatement(sql);
					ps.setInt(1,num);
					int executeUpdate = ps.executeUpdate();
					if(executeUpdate != 0) {
						System.out.println("删除成功");
					}			
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					if(null != ps) {
						try {
							ps.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(null != conn) {
						try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
	
}
