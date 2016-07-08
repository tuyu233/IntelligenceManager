package database;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
	
	/**
	 * add query limit
	 * @param pair key-value pair
	 * @return
	 */
	public DatabaseHelper where(QueryPair pair){
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
		//System.err.println(query.uniqueResult()+" "+query.uniqueResult().getClass().getName());
		return (int) count;
	}
	
	/**
	 * save object to database
	 * @param object
	 */
	public static void save(Object object){
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(object);
		transaction.commit();
		session.close();
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

}
