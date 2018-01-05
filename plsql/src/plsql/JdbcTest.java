package plsql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

public class JdbcTest {
	private String driverClass="oracle.jdbc.driver.OracleDriver";
	private String jdbcUrl="jdbc:oracle:thin:@192.168.21.128:1521:orcl";
	private String user="scott";
	private String password="root";
	
	@Test
	public void findAllEmp(){
		try {
			Class.forName(driverClass);
			Connection con = DriverManager.getConnection(jdbcUrl, user, password);
			PreparedStatement pst = con.prepareStatement("select * from emp");
			ResultSet rst = pst.executeQuery();
			while(rst.next()){
				System.out.println(rst.getInt(1)+"---"+rst.getString("ename"));
			}
			rst.close();
			pst.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@Test
	public void callAddSal(){
		try {
			Class.forName(driverClass);
			Connection con = DriverManager.getConnection(jdbcUrl, user, password);
			CallableStatement pst = con.prepareCall("{call add_sal(?)}");
			pst.setInt(1, 7369);
			pst.executeQuery();
			pst.close();
			pst.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Test
	public void countSal(){
		try {
			Class.forName(driverClass);
			Connection con = DriverManager.getConnection(jdbcUrl, user, password);
			CallableStatement pst = con.prepareCall("{call count_sal(?,?)}");
			pst.setInt(1,7369);
			pst.registerOutParameter(2, OracleTypes.NUMBER);
			pst.executeQuery();
			int count = pst.getInt(2);
			System.out.println("年薪为"+count);
			pst.close();
			pst.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	@Test
	public void deptEmp(){
		try {
			Class.forName(driverClass);
			Connection con = DriverManager.getConnection(jdbcUrl, user, password);
			CallableStatement pst = con.prepareCall("{call dept_emp(?,?)}");
			pst.setInt(1,10);
			pst.registerOutParameter(2, OracleTypes.CURSOR);
			pst.execute();
			OracleCallableStatement ost=(OracleCallableStatement)pst;
			ResultSet rst = ost.getCursor(2);
			while(rst.next()){
				System.out.println(rst.getInt(1)+"---"+rst.getString("ename"));
			}
			pst.close();
			pst.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	@Test
	public void functionCountSal(){
		try {
			Class.forName(driverClass);
			Connection con = DriverManager.getConnection(jdbcUrl, user, password);
			CallableStatement pst = con.prepareCall("{?=call function_count_sal(?)}");
			pst.setInt(2,7369);
			pst.registerOutParameter(1, OracleTypes.NUMBER);
			pst.execute();
			int count = pst.getInt(1);
			System.out.println(count);
			pst.close();
			pst.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
