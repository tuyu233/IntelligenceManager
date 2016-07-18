package database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import entity.Record;
import util.ClassUtil;
 

//readme: 具体用法可以参考 database.TestDataBase 的测试用例
/**
 * recommend use static function instead of use non static function. 
 * @author czn
 *
 */
public class DatabaseHelper {
	
//simple query	
	private enum Command{
		query,
		delete,
	}
	private Command currentCommand;
	private Class<?> TargetClass;
	private List<Pair> whereLimit = new ArrayList<>();
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
	
	/**
	 * add query limit
	 * @param pair key-value pair
	 * @return
	 */
	public DatabaseHelper where(Pair pair){
		whereLimit.add(pair);
		return this;
	}
	
	/**
	 * 设置 条件间是与还是或，默认是与逻辑
	 * @param logic
	 * @return
	 */
	public DatabaseHelper WhereLogic(QueryLogic logic){
		whereLogic = logic;
		return this;
	}
	
	/**
	 * order the result by attribute of object with order asc/desc
	 * @param attr
	 * @param order
	 * @return
	 */
	public DatabaseHelper orderBy(String attr,SortOrder order){
		sortKey = attr;
		this.order = order;
		return this;
	}
	
	/**
	 * get result
	 * @return
	 */
	public List<?> list(){
		String hql = "from ";
		hql += ClassUtil.classToName(TargetClass);
		hql += " a ";
		if(!whereLimit.isEmpty()){
			hql += " where ";
			int size = whereLimit.size();
			for(int i=0;i<size;i++){
				Pair pair = whereLimit.get(i);
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
		for(Pair pair : whereLimit){
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
	/**
	 * search key word in field content and title with type gov/news ...
	 * @param keyWord
	 * @param type
	 * @return
	 */
	public static List<Record> search(String keyWord,SearchType type){
		Session session = HibernateUtil.getSession();
		String hql = "from Record a";
		
		hql += " where (a.content like '%"
				+ keyWord + "%' or a.title like '%" + keyWord + "%')";
		
		List<String> word = type.getWords();
		if(!word.isEmpty()){
			hql += " and (";
			
			for(int i=0; i < word.size();i++){
				hql += "a.type = '"+word.get(i)+"' ";
				if(i != word.size()-1){
					hql += " or ";
				}
			}
			hql += ")";
		}

		hql += "order by a.saveTime desc";
		//System.out.println(hql);
		Query query = session.createQuery(hql);
		List<Record> list = query.list();
		session.close();
		return list;
	}
	
	/**
	 * search keyword by year
	 * @param keyWord
	 * @param year
	 * @return
	 */
	public static List<Record> search(String keyWord,String year) {
		Session session = HibernateUtil.getSession();
		String hql = "from Record a";
		
		hql += " where (a.content like '%"
				+ keyWord + "%' or a.title like '%" + keyWord + "%')";
		
		hql += " and (";

		DateFormat  df = new SimpleDateFormat("yyyy-MM-dd");
		Date start,end;
		try {
			start = df.parse(year+"-1-1");
			end = df.parse((Integer.valueOf(year)+1)+"-1-1");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			start = new Date(0);
			end = new Date(Integer.MAX_VALUE);
			e.printStackTrace();
		}
		hql += " a.saveTime >= :start and a.saveTime < :end";
		hql += ")";
		hql += "order by a.saveTime desc";
		Query query = session.createQuery(hql);
		query.setDate("start", start);
		query.setDate("end", end);
		List<Record> list = query.list();
		session.close();
		return list;
	}
	
	/**
	 * search keyword by year and type<br/>
	 * to add multiple keywords insert space between keywords <br/>
	 * to select all record without year , argument year should be "".
	 * @param keyWords
	 * @param year
	 * @param type
	 * @return
	 */
	public static List<Record> search(String keyWords,String year,SearchType type){
		Session session = HibernateUtil.getSession();
		String hql = "from Record a";
		
		hql += " where ";
		String wordList[] = keyWords.split(" ");
		
		boolean add = false;
		for(int i=0; i < wordList.length;i++){
			String keyWord = wordList[i];
			if(keyWord.equals(""))continue;
			if(add){
				hql += " and ";
			}
			hql +=" (a.content like '%"
					+ keyWord + "%' or a.title like '%" + keyWord + "%') ";
			add = true;
		}
		List<String> word = type.getWords();
		if(!word.isEmpty()){
			hql += " and (";
			
			for(int i=0; i < word.size();i++){
				hql += "a.type = '"+word.get(i)+"' ";
				if(i != word.size()-1){
					hql += " or ";
				}
			}
			hql += ")";
		}

		Query query;
		if(year.equals("")){
			hql += "order by a.saveTime desc";
			query = session.createQuery(hql);
		}
		else {
			hql += " and (";

			DateFormat  df = new SimpleDateFormat("yyyy-MM-dd");
			Date start,end;
			try {
				start = df.parse(year+"-1-1");
				end = df.parse((Integer.valueOf(year)+1)+"-1-1");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				start = new Date(0);
				end = new Date(Integer.MAX_VALUE);
				e.printStackTrace();
			}
			hql += " a.saveTime >= :start and a.saveTime < :end";
			hql += ")";
			hql += "order by a.saveTime desc";
			query = session.createQuery(hql);
			query.setDate("start", start);
			query.setDate("end", end);
		}

		List<Record> list = query.list();
		session.close();
		return list;
	}
	
	
	/**
	 * count number of record with given conditions.
	 * @param keyWord
	 * @param type
	 * @return
	 */
	public static int count(String keyWord,SearchType type){
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
		return (int) count;
	}
	
	
	
	/**
	 * count keyWord by year
	 * @param keyWord
	 * @return
	 */
	public static HashMap<String,Integer> count(String keyWord){
		HashMap<String,Integer> map = new HashMap<>();
		SimpleDateFormat sf = new SimpleDateFormat("YYYY");
		
		Session session = HibernateUtil.getSession();
		String hql = "from Record a";
		
		hql += " where (a.content like '%"
				+ keyWord + "%' or a.title like '%" + keyWord + "%')";
		
		Query query = session.createQuery(hql);
		List<Record> list = query.list();
		session.close();
		
		for(Record record : list){
			String year = sf.format(record.getSaveTime());
			if(!map.containsKey(year)){
				map.put(year, 1);
			}
			else {
				int count = map.get(year);
				map.put(year, count+1);
			}
		}
		
		return map;
	}
	
	/**
	 * get all record
	 * @return
	 */
	public static List<Record> getAllRecord(){
		Session session = HibernateUtil.getSession();
		List<Record> list = session.createQuery("from Record a order by a.id asc").list();
		session.close();
		return list;
	}
	/**
	 * save object to database
	 * @param object
	 */
	public static void save(Object object){
		try {
			Session session = HibernateUtil.getSession();
			Transaction transaction = session.beginTransaction();
			session.save(object);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * update object in database with the same id
	 * @param object
	 */
	public static void update(Object object){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.update(object);
		transaction.commit();
		session.close();
	}
	/**
	 * delete object in database with the same id
	 * @param object
	 */
	public static void delete(Object object){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.delete(object);
		transaction.commit();
		session.close();
	}
	
	/**
	 * clear table with given class
	 * @param c
	 */
	public static void clear(Class<?> c){
		String packages[] = c.getName().split("\\.");
		String className = packages[packages.length-1];
		
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		int i= session.createQuery("delete "+className).executeUpdate();
		transaction.commit();
		session.close();
	}

	public static void main(String args[]) {
//		Session session = HibernateUtil.getSession();
//		Query query = session.createQuery("select distinct a.author  from Record a");
//		System.out.println(query.list());
//		session.close();
		
		Session session = HibernateUtil.getSession();
		List<Record> list = session.createQuery("from Record a where a.content like '%%'").list();
		System.err.println(list);
		
	}
}
