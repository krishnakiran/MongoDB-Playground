package com.andiandy.m101j.week3.hw2_3;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mongodb.DB;


@RunWith(MockitoJUnitRunner.class)
public class SessionDAOTest {

	@Mock
	private DB blogDatabase;
	@InjectMocks
	private SessionDAO sessionDAO;



	@Test
	public void idGenerator() throws Exception {
		
		String idString = sessionDAO.generateSessionID();
		assertEquals(44, idString.length());
	}

}
