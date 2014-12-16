package rextest;

import java.io.File;
import java.io.IOException;

import javax.management.Query;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
    /**
     * ��������
     * @param filePath ��Ҫ�����������ļ��Ĵ��·��
     * @throws IOException
     */
    public static void createIndex(String filePath) throws IOException {
    	
    	IndexSearcher searcher=new IndexSearcher(filePath);
    	String newString=("�����Ļ�");
    	Term term=new Term("NEWSTITLE", "�Ϻ�");
    	TermQuery query=new TermQuery(term);
    	Hits hits=searcher.search(query);
    	try{
    	for(int i=0;i<hits.length();i++){
    		System.out.println(hits.doc(i));
    	}
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		System.out.println(e);
		}
//        //�ڵ�ǰ·���´���һ����indexDir��Ŀ¼
//        File indexDir = new File("G://luceneIndexer");
//        //��������Ŀ¼
//        Directory directory = FSDirectory.open(indexDir);
//        //����һ���ִ���
//        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
//        //��������������
//        IndexWriter indexWriterConfig = new IndexWriterConfig(Version.LUCENE_36,analyzer);
//        LogMergePolicy mergePolicy = new LogByteSizeMergePolicy();
//        //����segment����ĵ�(Document)ʱ�ĺϲ�Ƶ��
//        //ֵ��С,�����������ٶȾͽ���
//        //ֵ�ϴ�,�����������ٶȾͽϿ�,>10�ʺ�������������
//        mergePolicy.setMergeFactor(50);
//        //����segment���ϲ��ĵ�(Document)��
//        //ֵ��С������׷���������ٶ�
//        //ֵ�ϴ�,�ʺ��������������͸��������
//        mergePolicy.setMaxMergeDocs(5000);
//        //���ø���ʽ�����ļ���ʽ,�ϲ����segment
//        mergePolicy.setUseCompoundFile(true);
//        indexWriterConfig.setMergePolicy(mergePolicy);
//        //���������Ĵ�ģʽ
//        indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
//        //����������
//        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
//        File fileDir = new File(filePath);
//        for(File file : fileDir.listFiles()) {
//            //Document��Lucene���ĵ��ṹ����Ҫ�����Ķ���Ҫת��ΪDocument
//            Document document = new Document();
//            //�ļ���,�ɲ�ѯ,�ִ�,�洢���������¼��
//            document.add(new Field(��name��,getFileName(file),Store.YES,Index.ANALYZED));
//            //�ļ�·��,�ɲ�ѯ,���ִ�,�洢���������¼��
//            document.add(new Field(��path��,file.getAbsolutePath(),Store.YES,Index.NOT_ANALYZED));
//            //���ı�����,�ɲ�ѯ,���洢,ʵ���Ͽɸ����ļ�·��ȥ�ҵ��������ı�����
//            //document.add(new Field(��content��,new FileReader(file)));
//            //С�ı����ݣ����Դ洢��������¼��
//            document.add(new Field(��content��,getFileContent(file),Store.YES,Index.ANALYZED));
//            //���ĵ���ӵ�������
//            indexWriter.addDocument(document);
//        }
//        //�ύ�����������ϵ�������,�ر�������
//        indexWriter.close();
    }
}

