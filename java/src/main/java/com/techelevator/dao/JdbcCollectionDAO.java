package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Collection;
import com.techelevator.model.User;



public class JdbcCollectionDAO implements CollectionDAO {
	
	
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcCollectionDAO(DataSource datasource) {	
		this.jdbcTemplate = new JdbcTemplate(datasource);
	};
	
	
	
	
	
	

	@Override
	public void saveCollection(Collection collection) {
		// TODO Auto-generated method stub
		String sqlSaveCollection = "INSERT INTO collection() " +
		                           "VALUES () ";
		
		jdbcTemplate.update(sqlSaveCollection, collection.getUserId(),
				collection.getName(), collection.getCollectionDescription());	
		
	}

	@Override
	public Collection getCollectionById(int collectionId) {
		
		Collection collection = new Collection();
		
		String sql = "SELECT * FROM collection WHERE collection_id = ?  ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
		if(results.next()) {
			collection = mapRowToCollection(results);
		} else {
			System.out.println("Collection not found");
		}
		
		return collection; 
		
	}


	@Override
	public List<Collection> listAllCollectionsByUserId(Long userId) {
		// TODO Auto-generated method stub
		
		List<Collection> collections = new ArrayList<>();
		
		String sqlGetCollectionsByUserId = "SELECT ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCollectionsByUserId, userId);
			while(results.next()) {
			collections.add(mapRowToCollection(results));
		}
		
			return collections;
	}

	
	@Override
	public List<Collection> listCollectionByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addComicToCollection(int comicId, int collectionId) {
		// TODO Auto-generated method stub
		String sqlAddComicToCollection = "INSERT INTO () " +
		                                 "VALUES () ";
		
		jdbcTemplate.update(sqlAddComicToCollection, comicId, collectionId);
		
	}

	@Override
	public void deleteComicFromCollection(int comicId, int collectionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCollection(int CollectionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCollection(Collection collection) {
		// TODO Auto-generated method stub
		
	}

	private Collection mapRowToCollection(SqlRowSet results) {
		// TODO Auto-generated method stub
		
		Collection collection = new Collection();
		
		collection.setCollectionId(results.getInt("collection_id"));
		collection.setUserId(results.getInt("user_id"));
		
		
		return collection;
	}

	
	
	
}
