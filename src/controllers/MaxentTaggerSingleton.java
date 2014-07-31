package controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class MaxentTaggerSingleton {
	
	private static final String MODEL_LOCATION = "english-left3words-distsim.tagger";
	private static MaxentTagger tagger;
	
	
	public static MaxentTagger getInstance(){
		Logger logger = Logger.getLogger(TaggerServlet.class.getName());
		
		if(tagger == null){
			
			logger.log(Level.INFO, "initializing tagger");
			tagger = new MaxentTagger(MODEL_LOCATION);
		}
		
		return tagger;
	}
	
	public static MaxentTagger getNewInstance(){
		return new MaxentTagger(MODEL_LOCATION);
	}
}
