package database;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import entity.Record;
import util.ClassUtil;

public class DatabaseHelper {
	
//simple query	
	private enum Command{
		query,
		delete,
	}
	private Command currentCommand;
	private Class<?> TargetClass;
	private List<QueryPair> whereLimit = new ArrayList<>();
	private QueryLogic whereLogic = QueryLogic.and;
	private String sortKey = null;
	private SortOrder order = null;
	
	private DatabaseHelper(Command command,Class<?> c) {
		currentCommand = command;
		TargetClass = c;
	}
	
	public static DatabaseHelper query(Class<?> c) {
		String packages[] = c.getName().split("\\.");
		String className = packages[packages.length-1];
		return new DatabaseHelper(Command.query,c);
	}
	
	public DatabaseHelper where(QueryPair pair){
		whereLimit.add(pair);
		return this;
	}
	
	public DatabaseHelper WhereLogic(QueryLogic logic){
		whereLogic = logic;
		return this;
	}
	
	public DatabaseHelper orderBy(String attr,SortOrder order){
		sortKey = attr;
		this.order = order;
		return this;
	}
	
	public List<?> list(){
		String hql = "from ";
		hql += ClassUtil.classToName(TargetClass);
		hql += " a ";
		if(!whereLimit.isEmpty()){
			hql += " where ";
			int size = whereLimit.size();
			for(int i=0;i<size;i++){
				QueryPair pair = whereLimit.get(i);
				hql += " a."+pair.key+" = :"+pair.key+" ";
				if(i != size-1){
					hql += " "+whereLogic+" ";
				}
			}
		}
		
		if(order != null){
			hql += " order by "+sortKey+" "+order.toString();
		}
		System.out.println("hql: "+hql);
		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		for(QueryPair pair : whereLimit){
			if(pair.value instanceof String){
				query.setString(pair.key, (String)pair.value);
			}
			if(pair.value instanceof Integer){
				query.setInteger(pair.key, (Integer)pair.value);
			}
		}
		
		List<?> list = query.list();
		session.close();
		return list;
	}
	
//static
	
	public static List<Record> search(String keyWord,SearchType type){
		Session session = HibernateUtil.getSession();
		String hql = "from Record a";
		
		hql += " where (a.content like '%"
				+ keyWord + "%' or a.title like '%" + keyWord + "%')";
		
		hql += " and (";
		List<String> word = type.getWords();
		for(int i=0; i < word.size();i++){
			hql += "a.type = '"+word.get(i)+"' ";
			if(i != word.size()-1){
				hql += " or ";
			}
		}
		hql += ")";
		hql += "order by a.saveTime desc";
		//System.out.println(hql);
		Query query = session.createQuery(hql);
		List<Record> list = query.list();
		session.close();
		return list;
	}
	
	public static long count(String keyWord,SearchType type){
		Session session = HibernateUtil.getSession();
		String hql = "select count(*) from Record a";
		hql += " where (a.content like '%"
				+ keyWord + "%' or a.title like '%" + keyWord + "%')";
		
		hql += " and (";
		List<String> word = type.getWords();
		for(int i=0; i < word.size();i++){
			hql += "a.type = '"+word.get(i)+"' ";
			if(i != word.size()-1){
				hql += " or ";
			}
		}
		hql += ")";
		Query query = session.createQuery(hql);
		long count = (long) query.uniqueResult();
		session.close();
		//System.err.println(query.uniqueResult()+" "+query.uniqueResult().getClass().getName());
		return count;
	}
	
	public static void save(Object object){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(object);
		transaction.commit();
		session.close();
	}
	
	public static void update(Object object){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.update(object);
		transaction.commit();
		session.close();
	}
	
	public static void delete(Object object){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.delete(object);
		transaction.commit();
		session.close();
	}

	public static void clear(Class<?> c){
		String packages[] = c.getName().split("\\.");
		String className = packages[packages.length-1];
		
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		int i= session.createQuery("delete "+className).executeUpdate();
		transaction.commit();
		session.close();
	}
	
	
	//test
	public static void main(String args[]) {
		//clear(Record.class);
		
		//testSave();
		//testDelete();
		//testUpdate();
		//testHql();
		testSearch1();
		//testCount();
	}
	
	private static void testSave(){
		Record record = new Record();
		record.setAuthor("czn");
		record.setBaseUrl("baidu.com");
		record.setTitle("testSave");
		save(record);
	}
	
	private static void testDelete(){
		Record record = new Record();
		record.setAuthor("czn");
		record.setBaseUrl("baidu.com");
		record.setTitle("testSave");
		save(record);
		delete(record);
	}
	
	private static void testUpdate(){
		Record record = new Record();
		record.setAuthor("cznnn");
		record.setBaseUrl("baidu.com");
		record.setTitle("testSave");
		save(record);
		record.setTitle("aaa");
		update(record);
	}
	
	private static void testHql(){
		clear(Record.class);
		Record record = new Record();
		record.setAuthor("cznnn");
		record.setBaseUrl("baidu.com");
		record.setTitle("1");
		save(record);
		record.setTitle("2");
		save(record);
		
		DatabaseHelper helper = DatabaseHelper.query(Record.class);
		helper.where(new QueryPair("title",1));
		System.out.println(helper.list().size());
		
		helper = DatabaseHelper.query(Record.class);
		//helper.where(new QueryPair("title",1));
		System.out.println(helper.list().size());
		
		helper = DatabaseHelper.query(Record.class);
		helper.orderBy("title",SortOrder.asc);
		System.out.println(helper.list());
		
		helper = DatabaseHelper.query(Record.class);
		helper.orderBy("title",SortOrder.desc);
		System.out.println(helper.list());
	}
	
	private static void testSearch1(){
		clear(Record.class);
		Record record = new Record();
		record.setAuthor("cznnn");
		record.setBaseUrl("baidu.com");
		record.setContent("buaa");
		record.setTitle("1");
		record.setType("工信部");
		record.setSaveTime(new Date(1000));
		save(record);
		record.setSaveTime(new Date(999000000));
		save(record);
		
		List<Record> list = search("ua",SearchType.gov);
		System.out.println(list);
	}
	
	private static void testCount(){
		clear(Record.class);
		Record record = new Record();
		record.setAuthor("cznnn");
		record.setBaseUrl("baidu.com");
		record.setContent("buaa");
		record.setTitle("1");
		record.setType("工信部");
		save(record);
		record.setContent("aaauaa");
		save(record);
		long r  = count("ua",SearchType.gov);
		System.out.println(r);
	}
}
