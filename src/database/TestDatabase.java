package database;
  
import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.*;

public class TestDatabase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//testSave();
	}

	public static void testSave(){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction(); 
		Record record = new Record();
		record.setAuthor("czn");
		record.setBaseUrl("baidu.com");
		record.setTitle("testSave");
		session.save(record);
		transaction.commit();
		session.close();
		//System.out.println(TestDatabase.class.toString());
	}
}
