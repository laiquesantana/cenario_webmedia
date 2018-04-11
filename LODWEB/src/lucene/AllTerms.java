package lucene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.BytesRef;

public class AllTerms
{
	private Map<String, Integer> allTerms;
    Integer totalNoOfDocumentInIndex;
    IndexReader indexReader;
    
    public AllTerms() throws IOException
    {    
        allTerms = new HashMap<String, Integer>();
        indexReader = IndexOpener.getIndexReader();
        totalNoOfDocumentInIndex = IndexOpener.totalDocumentInIndex();
    }
        
    public void initAllTerms() throws IOException
    {
        int pos = 0;
        for (int docId = 0; docId < totalNoOfDocumentInIndex; docId++)
        {
            Terms vector = indexReader.getTermVector(docId, Configuration.FILE_CONTENTS);
            if (vector!=null) {
                TermsEnum termsEnum = null;
                termsEnum = vector.iterator();
                BytesRef text = null;
                while ((text = termsEnum.next()) != null)
                {
                    String term = text.utf8ToString();
                    allTerms.put(term, pos++);
                }
			}

        }       
        
        //Update position
        pos = 0;
        for(Entry<String,Integer> s : allTerms.entrySet())
        {        
            //System.out.println(pos+": "+s.getKey());
            s.setValue(pos++);
        }
    }
    
    public Map<String,Integer> getAllTerms()
    {
        return allTerms;
    }
}
