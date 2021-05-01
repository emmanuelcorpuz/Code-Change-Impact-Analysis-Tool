package components;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.ParserException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;


public class Searcher {

	IndexSearcher indexSearcher;
	QueryParser queryParser;
	Query query;



	static Searcher searcher;
	private static HashMap<String, String> includeextension = new HashMap<String, String>();


	public Searcher(String indexDirectoryPath) 
			throws IOException {
		Path path = Paths.get(indexDirectoryPath);
		Directory indexDirectory = FSDirectory.open(path);
		IndexReader indexReader = DirectoryReader.open(indexDirectory);
		indexSearcher = new IndexSearcher(indexReader);
		queryParser = new QueryParser(LuceneConstants.CONTENTS,new StandardAnalyzer());
	}

	public Searcher() {
		// TODO Auto-generated constructor stub
	}

	public TopDocs search( String searchQuery) 
			throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
		query = queryParser.parse(searchQuery);
		return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
	}

	public Document getDocument(ScoreDoc scoreDoc) 
			throws CorruptIndexException, IOException {
		return indexSearcher.doc(scoreDoc.doc);	
	}

	public HashMap<FileNode, HashMap> getNodes(FileNode args, String indexpath, String includeextension) {

		String[] arrOfStr = includeextension.split(","); 
		for (String a : arrOfStr) {
			a = a.trim();
			if (!"".equals(a)) {
				this.includeextension.put(a, a);
			}
		}


		HashMap<FileNode, HashMap> hm = new HashMap<FileNode, HashMap>();
		try {

			HashMap<FileNode, FileNode> antiloop = new HashMap<FileNode, FileNode>();
			antiloop.put(args, args);


			HashMap<FileNode, HashMap> hm2 = new HashMap<FileNode, HashMap>();
			hm.put(args, hm2);

			ArrayList<HashMap> al = new ArrayList<HashMap>();

			search(args, hm, al, indexpath);

			int cnt2 = 0;
			while (al.size() > 0) {

				ArrayList<HashMap> al2 = new ArrayList<HashMap>();

				cnt2 = 0;
				while (cnt2 < al.size()) {

					HashMap<FileNode, HashMap> hmal = (HashMap<FileNode, HashMap>)al.get(cnt2);
					//HashMap<String, HashMap> hm3 = hm.get("SCHPAY_getinfo.jsp");
					Iterator hmIterator = hmal.entrySet().iterator(); 
					while (hmIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry)hmIterator.next(); 

						if (antiloop.get((FileNode)mapElement.getKey()) == null) {
							antiloop.put((FileNode)mapElement.getKey(), (FileNode)mapElement.getKey());
							search((FileNode)mapElement.getKey(), hmal, al2, indexpath);
						}
					} 
					cnt2 = cnt2 + 1;
				}


				al = new ArrayList<HashMap>();
				al = (ArrayList<HashMap>)al2.clone();

			}






			//JUST FOR PRINTING****************************************************************************


//			Iterator hmIterator = hm.entrySet().iterator(); 
//			while (hmIterator.hasNext()) {
//				Map.Entry mapElement = (Map.Entry)hmIterator.next(); 
//				System.out.println(mapElement.getKey()); 
//
//				HashMap<String, HashMap> hm21 = hm.get(mapElement.getKey());
//				if (hm21 != null) {
//					Iterator hmIterator2 = hm21.entrySet().iterator(); 
//					while (hmIterator2.hasNext()) {
//						Map.Entry mapElement2 = (Map.Entry)hmIterator2.next(); 
//						System.out.println("	" + mapElement2.getKey()); 
//
//						HashMap<String, HashMap> hm3 = hm21.get(mapElement2.getKey());
//						if (hm3 != null) {
//							Iterator hmIterator3 = hm3.entrySet().iterator(); 
//							while (hmIterator3.hasNext()) {
//								Map.Entry mapElement3 = (Map.Entry)hmIterator3.next(); 
//								System.out.println("		" + mapElement3.getKey()); 
//
//								HashMap<String, HashMap> hm4 = hm3.get(mapElement3.getKey());
//								if (hm4 != null) {
//									Iterator hmIterator4 = hm4.entrySet().iterator(); 
//									while (hmIterator4.hasNext()) {
//										Map.Entry mapElement4 = (Map.Entry)hmIterator4.next(); 
//										System.out.println("			" + mapElement4.getKey()); 
//									} 
//								}
//							} 
//						}
//					} 
//				}
//			} 














			return hm;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hm;
	}

	private void search(FileNode searchQuery, HashMap<FileNode, HashMap> hm, ArrayList<HashMap> al, String indexpath) throws IOException, ParserException, java.text.ParseException, org.apache.lucene.queryparser.classic.ParseException {
		searcher = new Searcher(indexpath);
		//		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.search("\"" + searchQuery.getName().trim() + "\"");
		//		long endTime = System.currentTimeMillis();

		HashMap<FileNode, HashMap> hm2 = hm.get(searchQuery);

		if (hits.totalHits == 0) {
			hm.put(searchQuery, null);
		}

		//System.out.println(hits.totalHits + " documents found. Time :" + (endTime - startTime));

		for(ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = searcher.getDocument(scoreDoc);

			Path p = Paths.get(doc.get(LuceneConstants.PATH));
			String filename = p.getFileName().toString();

			//System.out.println("File: " + filename);
			//System.out.println("File: " + p.toString());

			if (!searchQuery.getName().equals(FilenameUtils.removeExtension(filename))) {
				FileNode fileNode = null;
				if (includeextension.get(FilenameUtils.getExtension(filename).toLowerCase()) == null || includeextension.isEmpty()) {
					fileNode = new FileNode(FilenameUtils.removeExtension(filename), p.toString(), FilenameUtils.getExtension(filename));
				} else {
					fileNode = new FileNode(filename, p.toString(), FilenameUtils.getExtension(filename));
				}
				hm2.put(fileNode, new HashMap<FileNode, HashMap>());
			}

			//System.out.println("File: " + doc.get(LuceneConstants.PATH));

		}
		al.add(hm2);
	}

}
