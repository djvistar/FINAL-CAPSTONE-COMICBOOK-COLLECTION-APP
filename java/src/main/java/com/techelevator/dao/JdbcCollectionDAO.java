package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Collection;
import com.techelevator.model.User;


@Component
public class JdbcCollectionDAO implements CollectionDAO {
	
	
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcCollectionDAO(DataSource datasource) {	
		this.jdbcTemplate = new JdbcTemplate(datasource);
	};
	
	
	

	@Override
	public void saveCollection(Collection collection) {
		String sqlSaveCollection = "INSERT INTO user_collections(collection_id, user_id, collection_name) " +
		                           "VALUES (?,?,?) ";
		
		jdbcTemplate.update(sqlSaveCollection, collection.getUserId(),
				 collection.getCollectionDescription(), collection.getName());	
		
	}
	
	

	@Override
	public Collection getCollectionById(int collectionId) {
		
		Collection collection = new Collection();
		String sql = "SELECT * FROM collections WHERE collection_id = ? ";
		//String sql = "SELECT issue.issue_id, issue_name, issue_number, cover_url, volume_name  FROM issue JOIN collections on issue.issue_id = collections.issue_id WHERE collections.collection_id = ?  ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
		while(results.next()) {
			collection = mapRowToCollection(results);
//		} else {
//			System.out.println("Collection not found");
//		}
		}
		return collection; 
		
	}

	

	@Override
	public List<Collection> listAllCollectionsByUserId(Long userId) {
		
		List<Collection> collections = new ArrayList<>();
		
		String sqlGetCollectionsByUserId = "SELECT collection_name FROM user_collections WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCollectionsByUserId, userId);
			while(results.next()) {
			collections.add(mapRowToCollection(results));
		}
		
			return collections;
	}

	
	
	@Override
	public List<Collection> listCollectionByUsername(String username) {
		List<Collection> collections = new ArrayList<>();
		
		String sqlGetCollectionsByUsername = "SELECT collection_name, collection_id FROM user_collections JOIN users on user_collections.user_id = users.user_id WHERE users.username = '?'";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCollectionsByUsername, username);
			while(results.next()) {
				collections.add(mapRowToCollection(results));
			
		}
		
		return collections;
	}
	

	
	@Override
	public void addComicToCollection(int comicId, int collectionId) {//need to update arguments
		String sqlAddComicToCollection = "INSERT INTO issue(issue_id, issue_number, issue_name, volume_id, volume_name, cover_url) " +
		                                 "VALUES (?, ?, ?, ?, ?, ?); " +
				"insert into collections (inventory_id, collection_id, issue_id ) " + //if value is sequentially generated do we need to include, if not we can drop the inventory_id value
				"values(?,?,?);";
		
		jdbcTemplate.update(sqlAddComicToCollection, comicId, collectionId);
		
	}
	
	

	@Override
	public void deleteComicFromCollection(int comicId, int collectionId) {
		String sqlDeleteComicFromCollection = "DELETE FROM collections WHERE (issue_id = ? && collection_id = ?)  ";
		jdbcTemplate.update(sqlDeleteComicFromCollection, comicId, collectionId);
		
	}

	
	
	@Override
	public void deleteCollection(int collectionId) {
		String sqlDeleteCollection = "DELETE FROM collections WHERE collection_id = ?";
		jdbcTemplate.update(sqlDeleteCollection, collectionId);
	}

	@Override
	public void updateCollection(Collection collection) { //no info in db for this yet
		String sqlUpdateCollection = "UPDATE collection " +
		                             "SET user_d =?, name = ?, collection_description = ? " +
				                      "WHERE collection_id =? ";
		jdbcTemplate.update(sqlUpdateCollection, collection.getUserId(), collection.getName(),
				            collection.getCollectionDescription(), collection.getCollectionId());
		
	}

	
	
	private Collection mapRowToCollection(SqlRowSet results) {
		
		Collection collection = new Collection();
		
		collection.setCollectionId(results.getInt("collection_id"));
//		collection.setInventoryId(results.getInt("inventory_id"));
//		collection.setIssueId(results.getInt("issue_id"));
//		collection.setName(results.getString("name"));
//		collection.setCollectionDescription(results.getString("collection_description"));
//		collection.setUsername(results.getString("username"));
		
		return collection;
	}

	
	
	
}
