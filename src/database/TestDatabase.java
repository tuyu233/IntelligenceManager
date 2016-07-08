package database;

import static org.junit.Assert.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Record;

public class TestDatabase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		DatabaseHelper.clear(Record.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSave() {
		Record record = new Record();
		record.setAuthor("czn");
		record.setBaseUrl("baidu.com");
		record.setTitle("testSave");
		DatabaseHelper.save(record);
		assertEquals(1,countAll());
	}
	@Test
	public void testDelete(){
		Record record = new Record();
		record.setAuthor("czn");
		record.setBaseUrl("baidu.com");
		record.setTitle("testSave");
		DatabaseHelper.save(record);
		DatabaseHelper.delete(record);
		assertEquals(0,countAll());
	}
	
	@Test
	public void testUpdate(){
		Record record = new Record();
		record.setAuthor("cznnn");
		record.setBaseUrl("baidu.com");
		record.setTitle("testSave");
		DatabaseHelper.save(record);
		record.setTitle("aaa");
		DatabaseHelper.update(record);
		Session session = HibernateUtil.getSession();
		Record r = (Record) session.createQuery("from Record").setMaxResults(1).list().get(0);
		assertEquals("aaa", r.getTitle());
		
	}
	@Test
	public void testHql(){
		Record record = new Record();
		record.setAuthor("cznnn");
		record.setBaseUrl("baidu.com");
		record.setTitle("1");
		DatabaseHelper.save(record);
		record.setTitle("2");
		DatabaseHelper.save(record);
		
		DatabaseHelper helper = DatabaseHelper.query(Record.class);
		helper.where(new QueryPair("title",1));
		assertEquals(1,helper.list().size());
		
		helper = DatabaseHelper.query(Record.class);
		assertEquals(2,helper.list().size());
		
		helper = DatabaseHelper.query(Record.class);
		helper.orderBy("title",SortOrder.asc);
		assertEquals("1",((Record)(helper.list().get(0))).getTitle());
		
		helper = DatabaseHelper.query(Record.class);
		helper.orderBy("title",SortOrder.desc);
		assertEquals("2",((Record)(helper.list().get(0))).getTitle());
	}
	@Test
	public void testSearch1(){
		Record record = new Record();
		record.setAuthor("cznnn");
		record.setBaseUrl("baidu.com");
		record.setContent("buaa");
		record.setTitle("1");
		record.setType("工信部");
		record.setSaveTime(new Date(1000));
		DatabaseHelper.save(record);
		record.setSaveTime(new Date(999000000));
		DatabaseHelper.save(record);
		
		List<Record> list = DatabaseHelper.search("ua",SearchType.gov);
		assertEquals(2, list.size());
	}	
	@Test
	public void testCount(){
		Record record = new Record();
		record.setAuthor("cznnn");
		record.setBaseUrl("baidu.com");
		record.setContent("buaa");
		record.setTitle("1");
		record.setType("工信部");
		DatabaseHelper.save(record);
		record.setContent("aaauaa");
		DatabaseHelper.save(record);
		long r  = DatabaseHelper.count("ua",SearchType.gov);
		assertEquals(2, r);
	}
	
	@Test
	public void testCountByYear() throws ParseException{
		Record record = new Record();
		record.setTitle("1"); 
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-mm-dd");
		record.setSaveTime(sf.parse("2010-1-2"));
		DatabaseHelper.save(record);
		record.setSaveTime(sf.parse("2010-1-2"));
		DatabaseHelper.save(record);
		record.setSaveTime(sf.parse("2011-1-2"));
		DatabaseHelper.save(record);
		
		Map<String,Integer> map = DatabaseHelper.count("1");
		assertTrue(2 == map.get("2010"));
		assertTrue(1 == map.get("2011"));
		
	}
	
	private int countAll(){
		Session session = HibernateUtil.getSession();
		int r = session.createQuery("from Record").list().size();
		return r;
	}

}
