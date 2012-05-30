package org.gescobar.wayra.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.gescobar.wayra.entity.User;
import org.gescobar.wayra.entity.UserService;
import org.gescobar.wayra.service.UserStore;

/**
 * A {@link UserStore} implementation based on JDBC.
 * 
 * @author German Escobar
 */
public class JdbcUserStore implements UserStore {
	
	private DataSource dataSource;

	@Override
	public void create(User user) {
		user.setCreationTime(new Date());
		
		String sql = "INSERT INTO usser (id, name, creation_time) VALUES (?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = dataSource.getConnection();
		
			ps = conn.prepareStatement(sql);
			ps.setLong(1, user.getId());
			ps.setString(2, user.getName());
			ps.setDate(3,  new java.sql.Date(user.getCreationTime().getTime()));
			
			ps.execute();
			
		} catch (SQLException e) {
			
			throw new RuntimeException(e);
			
		} finally {
			
			if (ps != null) {
				try { ps.close(); } catch (SQLException e) {}
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) {}
			}
			
		}

	}

	@Override
	public User load(long id) {
		
		String sql = "SELECT * FROM usser where id = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			conn = dataSource.getConnection();
			
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				
				User user = new User();
				user.setId(id);
				user.setName( rs.getString("name") );
				user.setCreationTime(new Date(rs.getDate("creation_time").getTime()) );
				
				user.setServices( getUserServices(conn, user.getId()) );
				
				return user;
			}
			
			return null;
		
		} catch (SQLException e) {
			
			throw new RuntimeException(e);
		
		} finally {
			
			if (rs != null){
				try { rs.close(); } catch (SQLException e) {}
			}
			if (ps != null) {
				try { ps.close(); } catch (SQLException e) {}
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) {}
			}
			
		}

	}
	
	private List<UserService> getUserServices(Connection conn, long userId) throws SQLException {
		
		List<UserService> userServices = new ArrayList<UserService>(); 
		
		String sql = "SELECT * FROM user_service where user_id = ? ";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = conn.prepareStatement(sql);
			ps.setLong(1, userId);
			
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				
				UserService userService = new UserService();
				userService.setId( rs.getLong("id") );
				userService.setName( rs.getString("name") );
				userService.setData( rs.getString("data") );
				userService.setCreationTime( new Date(rs.getDate("creation_time").getTime()) );
				
				userServices.add(userService);
				
			}
			
		} finally {
			if (rs != null){
				try { rs.close(); } catch (SQLException e) {}
			}
			if (ps != null) {
				try { ps.close(); } catch (SQLException e) {}
			}
		}
		
		return userServices;
		
	}

	@Override
	public void activate(UserService userService) {
		
		userService.setCreationTime( new Date() );
		
		String sql = "INSERT INTO user_service (user_id, name, data, creation_time) VALUES (?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = dataSource.getConnection();
		
			ps = conn.prepareStatement(sql);
			ps.setLong( 1, userService.getUserId() );
			ps.setString( 2, userService.getName() );
			ps.setString( 3, userService.getData() );
			ps.setDate(4, new java.sql.Date(userService.getCreationTime().getTime()) );
			
			ps.execute();
			
		} catch (SQLException e) {
			
			throw new RuntimeException(e);
		} finally {
			
			if (ps != null) {
				try { ps.close(); } catch (SQLException e) {}
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) {}
			}
			
		}
		
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
