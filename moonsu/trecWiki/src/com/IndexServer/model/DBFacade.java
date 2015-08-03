package com.IndexServer.model;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.IndexServer.Bean.Content;
import com.IndexServer.socketServer.Info;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

public class DBFacade {

	private static DBFacade instace = new DBFacade();

	private BufferedWriter br = null;
	
	public static DBFacade getInstance() {

		return instace;
	}

	Mongo conn;
	WriteConcern wc;
	DB db;

	public void connection() {
		try {
			conn = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		wc = new WriteConcern(1, 2000);
		conn.setWriteConcern(wc);

		db = conn.getDB("test_trec");

	}

	public void endConnection() {
		conn.close();
	}

	public boolean addIndexWord(Content content, ArrayList<String> index_words,
			HashMap<Integer, String> index_words2) {
		boolean result = false;
		for (int num = 5; num < 51; num += 5) {
			connection();

			try {

				String dbname = "index" + num;
				DBCollection coll = db.getCollection(dbname);

				for (int i = 0; i < num; i++) {
					String word = index_words2.get(i);

					DBObject doc = new BasicDBObject();
					doc.put("word", word);
					DBCursor cur = coll.find(doc);

					JSONObject object = new JSONObject();
					object.put("id", content.getId());

					if (!cur.hasNext()) {

						JSONArray contents = new JSONArray();
						contents.add(object);
						doc.put("contents", contents);

						coll.insert(doc);
					} else {

						DBObject query = new BasicDBObject();
						query.put("$addToSet", new BasicDBObject("contents",
								object));
						coll.update(doc, query);
					}

					result = true;
				}
				System.out.println("[DBFacade] Add Index Word Success");

			} catch (MongoException e) {
				e.printStackTrace();
				System.out.println("[DBFacade] Add Index Word error");
				result = false;

			}

			if (index_words.size() != 0) {
				try {

					String dbname = "index" + num;
					DBCollection coll = db.getCollection(dbname);

					for (String word : index_words) {

						DBObject doc = new BasicDBObject();
						doc.put("word", word);
						DBCursor cur = coll.find(doc);

						JSONObject object = new JSONObject();
						object.put("id", content.getId());

						if (!cur.hasNext()) {

							JSONArray contents = new JSONArray();
							contents.add(object);
							doc.put("contents", contents);

							coll.insert(doc);
						} else {

							DBObject query = new BasicDBObject();
							query.put("$addToSet", new BasicDBObject(
									"contents", object));
							coll.update(doc, query);
						}

						result = true;
					}
					System.out.println("[DBFacade] Add Index Word Success");

				} catch (MongoException e) {
					e.printStackTrace();
					System.out.println("[DBFacade] Add Index Word error");
					result = false;

				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			endConnection();

		}
		
		return result;

	}

	public boolean addIndexWordCategory(Content content,
			ArrayList<String> index_words, HashMap<Integer, String> index_words2) {
		boolean result = false;
		for (int num = 5; num < 51; num += 5) {
			connection();

			try {

				String dbname = content.getCategory().replaceAll(" ", "")
						.trim()
						+ num;
				DBCollection coll = db.getCollection(dbname);

				for (int i = 0; i < num; i++) {
					String word = index_words2.get(i);

					DBObject doc = new BasicDBObject();
					doc.put("word", word);
					DBCursor cur = coll.find(doc);

					JSONObject object = new JSONObject();
					object.put("id", content.getId());

					if (!cur.hasNext()) {

						JSONArray contents = new JSONArray();
						contents.add(object);
						doc.put("contents", contents);

						coll.insert(doc);
					} else {

						DBObject query = new BasicDBObject();
						query.put("$addToSet", new BasicDBObject("contents",
								object));
						coll.update(doc, query);
					}

					result = true;
				}
				System.out.println("[DBFacade] Add Index Word Success");

			} catch (MongoException e) {
				e.printStackTrace();
				System.out.println("[DBFacade] Add Index Word error");
				result = false;

			}

			if (index_words.size() != 0) {
				try {

					String dbname = content.getCategory().replaceAll(" ", "")
							.trim()
							+ num;
					DBCollection coll = db.getCollection(dbname);

					for (String word : index_words) {

						DBObject doc = new BasicDBObject();
						doc.put("word", word);
						DBCursor cur = coll.find(doc);

						JSONObject object = new JSONObject();
						object.put("id", content.getId());

						if (!cur.hasNext()) {

							JSONArray contents = new JSONArray();
							contents.add(object);
							doc.put("contents", contents);

							coll.insert(doc);
						} else {

							DBObject query = new BasicDBObject();
							query.put("$addToSet", new BasicDBObject(
									"contents", object));
							coll.update(doc, query);
						}

						result = true;
					}
					System.out.println("[DBFacade] Add Index Word Success");

				} catch (MongoException e) {
					e.printStackTrace();
					System.out.println("[DBFacade] Add Index Word error");
					result = false;

				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			endConnection();

		}
		
		return result;

	}

	public boolean addinformationIndex(Content content,
			ArrayList<String> index_words) {
		boolean result = false;
		for (int num = 5; num < 51; num += 5) {
			connection();

			try {

				String dbname = "informationIndex";
				DBCollection coll = db.getCollection(dbname);

				for (String word : index_words) {

					DBObject doc = new BasicDBObject();
					doc.put("word", word);
					DBCursor cur = coll.find(doc);

					JSONObject object = new JSONObject();
					object.put("id", content.getId());

					if (!cur.hasNext()) {

						JSONArray contents = new JSONArray();
						contents.add(object);
						doc.put("contents", contents);

						coll.insert(doc);
					} else {

						DBObject query = new BasicDBObject();
						query.put("$addToSet", new BasicDBObject("contents",
								object));
						coll.update(doc, query);
					}

					result = true;
				}
				System.out.println("[DBFacade] Add Index Word Success");

			} catch (MongoException e) {
				e.printStackTrace();
				System.out.println("[DBFacade] Add Index Word error");
				result = false;

			}
			endConnection();
		}
		
	

		return result;

	}
	
	
	public ArrayList<String> findContentRanking(String keyword, int numberIndex,String category) {
		ArrayList<String> result = new ArrayList<String>();
		HashMap<String, Double> resultHashMap = new HashMap<String, Double>();

		connection();
		try {
			long start = System.currentTimeMillis();
			String dbName = "IndexRank";
			DBCollection coll = db.getCollection(dbName);

			String regex = ".*" + keyword + ".*";
			DBObject query = new BasicDBObject();
			//query.put("word", new BasicDBObject("$regex", regex));
			query.put("word", keyword);
			// query.put("user_name", user_name);
			DBCursor cur = coll.find(query);
			long end = System.currentTimeMillis();
			System.out.println("실행 시간 : " + (end - start));
		
			while (cur.hasNext()) {
				DBObject doc = new BasicDBObject();
				doc = cur.next();
				JSONObject index = new JSONObject();
				index = (JSONObject) JSONValue.parse(doc.toString());
				JSONArray jsonArray = (JSONArray) index.get("contents");
				if (jsonArray != null) {
					int len = jsonArray.size();
					for (int i = 0; i < len; i++) {
						
						String word = ((JSONObject) jsonArray.get(i)).get("id")
								.toString();
						double value = (double) ((JSONObject) jsonArray.get(i)).get("score");
						
						resultHashMap.put(word, value);
						
						
					}
				}

			}
			
			System.out.println(keyword);
			
			
			Iterator it = sortByValue(resultHashMap).iterator();
			int num =0;
			while(it.hasNext())
			{
				String word = (String) it.next();
				double value = resultHashMap.get(word);
				
				result.add(word);
				num++;
				if(num == numberIndex)
				{
					break;
				}
			}
			
			
			System.out.println("[DBFacade] Search Content ");

		} catch (MongoException e) {
			e.printStackTrace();
			System.out.println("[DBFacade] Search Content error");

		}
		endConnection();

		return result;
	}
	

	 public List sortByValue(final Map map){
	        List<String> list = new ArrayList();
	        list.addAll(map.keySet());
	         
	        Collections.sort(list,new Comparator(){
	             
	            public int compare(Object o1,Object o2){
	                Object v1 = map.get(o1);
	                Object v2 = map.get(o2);
	                 
	                return ((Comparable) v1).compareTo(v2);
	            }
	             
	        });
	        Collections.reverse(list); // 주석시 오름차순
	        return list;
	    }
	
	
	public ArrayList<String> findContent(String keyword, int indexnum) {
		ArrayList<String> result = new ArrayList<String>();

		connection();
		try {
			long start = System.currentTimeMillis();
			String dbName = "index" + indexnum;
			DBCollection coll = db.getCollection(dbName);

			String regex = ".*" + keyword + ".*";
			DBObject query = new BasicDBObject();
			//query.put("word", new BasicDBObject("$regex", regex));
			query.put("word", keyword);
			// query.put("user_name", user_name);
			DBCursor cur = coll.find(query);
			long end = System.currentTimeMillis();
			System.out.println("실행 시간 : " + (end - start));
			while (cur.hasNext()) {
				DBObject doc = new BasicDBObject();
				doc = cur.next();
				JSONObject index = new JSONObject();
				index = (JSONObject) JSONValue.parse(doc.toString());
				JSONArray jsonArray = (JSONArray) index.get("contents");
				if (jsonArray != null) {
					int len = jsonArray.size();
					for (int i = 0; i < len; i++) {
						result.add(((JSONObject) jsonArray.get(i)).get("id")
								.toString());
					}
				}

			}
			System.out.println("[DBFacade] Search Content ");

		} catch (MongoException e) {
			e.printStackTrace();
			System.out.println("[DBFacade] Search Content error");

		}
		endConnection();

		return result;
	}

	public ArrayList<String> findCategoryContent(String keyword, int indexnum,
			String category,int num) {
		ArrayList<String> result = new ArrayList<String>();
		String findtime = "result/" + category
				+num+ "CbirfindTime.csv";
		try {
			br = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(findtime, true),
					"UTF-8"));
			

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long start = System.currentTimeMillis();
		connection();
		try {
			String dbName = category + indexnum;
			DBCollection coll = db.getCollection(dbName);

			String regex = ".*" + keyword + ".*";
			DBObject query = new BasicDBObject();
			//query.put("word", new BasicDBObject("$regex", regex));
			query.put("word", keyword);
			// query.put("user_name", user_name);
			DBCursor cur = coll.find(query);
			long end = System.currentTimeMillis();
			long time = end - start;
			try {
				br.write(keyword+","+time+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("실행 시간 : " + (end - start));
			
			while (cur.hasNext()) {
				DBObject doc = new BasicDBObject();
				doc = cur.next();
				JSONObject index = new JSONObject();
				index = (JSONObject) JSONValue.parse(doc.toString());
				JSONArray jsonArray = (JSONArray) index.get("contents");
				if (jsonArray != null) {
					int len = jsonArray.size();
					for (int i = 0; i < len; i++) {
						result.add(((JSONObject) jsonArray.get(i)).get("id")
								.toString());
					}
				}

			}
			System.out.println("[DBFacade] Search Content ");

		} catch (MongoException e) {
			e.printStackTrace();
			System.out.println("[DBFacade] Search Content error");

		}
		endConnection();
		try {
			br.flush();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return result;
	}

	
	public ArrayList<String> findInformationIndex(String keyword,String category) {
		ArrayList<String> result = new ArrayList<String>();
		String findtime = "result/" + category
				+ "InfofindTime.csv";
		try {
			br = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(findtime, true),
					"UTF-8"));
			

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long start = System.currentTimeMillis();
		connection();
		try {
			
			String dbName = "informationIndex";
			DBCollection coll = db.getCollection(dbName);

			String regex = ".*" + keyword + ".*";
			DBObject query = new BasicDBObject();
			//query.put("word", new BasicDBObject("$regex", regex));
			query.put("word", keyword);
			// query.put("user_name", user_name);
			DBCursor cur = coll.find(query);
			long end = System.currentTimeMillis();
			long time = end - start;
			try {
				br.write(keyword+","+time+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("실행 시간 : " + (end - start) );
			while (cur.hasNext()) {
				DBObject doc = new BasicDBObject();
				doc = cur.next();
				JSONObject index = new JSONObject();
				index = (JSONObject) JSONValue.parse(doc.toString());
				JSONArray jsonArray = (JSONArray) index.get("contents");
				if (jsonArray != null) {
					int len = jsonArray.size();
					for (int i = 0; i < len; i++) {
						result.add(((JSONObject) jsonArray.get(i)).get("id")
								.toString());
					}
				}

			}
			System.out.println("[DBFacade] Search Content ");

		} catch (MongoException e) {
			e.printStackTrace();
			System.out.println("[DBFacade] Search Content error");

		}
		endConnection();
		try {
			br.flush();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
