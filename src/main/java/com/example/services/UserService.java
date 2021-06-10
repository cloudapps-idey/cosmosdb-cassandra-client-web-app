package com.example.services;

import java.io.IOException;

import com.example.repository.UserRepository;
import com.example.util.CassandraUtils;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONException;

import org.springframework.stereotype.Service;

/**
 * Example class which will demonstrate following operations on Cassandra
 * Database on CosmosDB - Create Keyspace - Create Table - Insert Rows - Select
 * all data from a table - Select a row from a table
 */
@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	boolean isTableSet = false;

	public void getAllUsers() throws IOException, JSONException {
		
		try {
			//setUpUserTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LOGGER.info("Select all users");
		CassandraUtils utils = new CassandraUtils();
		Session cassandraSession = utils.getSession();
		UserRepository repo = new UserRepository(cassandraSession);
		repo.selectAllUsers();
		utils.close();

	}

	public void setUpUserTable() throws Exception {

		if (isTableSet == false) {

			try {
				
				CassandraUtils utils = new CassandraUtils();
				Session cassandraSession = utils.getSession();
				UserRepository repository = new UserRepository(cassandraSession);

				// Create keyspace in cassandra database
				repository.createKeyspace();

				// Create table in cassandra database
				repository.createTable();

				// Insert rows into user table
				PreparedStatement preparedStatement = repository.prepareInsertStatement();
				repository.insertUser(preparedStatement, 1, "LyubovK", "Bangalore");
				repository.insertUser(preparedStatement, 2, "JiriK", "Mumbai");
				repository.insertUser(preparedStatement, 3, "IvanH", "Belgum");
				repository.insertUser(preparedStatement, 4, "YuliaT", "Gurgaon");
				repository.insertUser(preparedStatement, 5, "IvanaV", "Dubai");

				// LOGGER.info("Select all users");
				// repository.selectAllUsers();

				// LOGGER.info("Select a user by id (3)");
				// repository.selectUser(3);

				isTableSet = true;
				
				utils.close();
			} finally {
				
				LOGGER.info("Please delete your table after verifying the presence of the data in portal or from CQL");
			}

		}
	}

	
	
}
