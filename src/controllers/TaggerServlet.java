package controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Noun;

import com.google.gson.Gson;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.Morphology;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class TaggerServlet extends HttpServlet {

	private static final long serialVersionUID = 4037277840651580816L;

	private Logger logger = Logger.getLogger(TaggerServlet.class.getName());

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		PrintWriter out = resp.getWriter();

		Gson gson = new Gson();

		String toBeTagged = req.getParameter("text");
		logger.log(Level.INFO, "tagging --> " + toBeTagged);
		resp.setContentType("application/json");

		out.print(gson.toJson(classifyToModel(taggingMachine(toBeTagged))));
		out.flush();
	}

	private List<List<? extends HasWord>> taggingMachine(
			String toBeTaggedSentences) {
		MaxentTagger tagger = new MaxentTagger(
				"english-left3words-distsim.tagger");
		PTBTokenizerFactory.newWordTokenizerFactory(null);

		boolean outputLemmas = false;

		List<List<HasWord>> sentences;
		sentences = MaxentTagger.tokenizeText(new StringReader(
				toBeTaggedSentences), PTBTokenizerFactory
				.newWordTokenizerFactory(null));
		List<List<? extends HasWord>> taggedSentences = new ArrayList<>();
		for (List<? extends HasWord> sent : sentences) {
			Morphology morpha = (outputLemmas) ? new Morphology() : null;
			sent = tagger.tagCoreLabelsOrHasWords(sent, morpha, outputLemmas);
			taggedSentences.add(sent);
		}

		return taggedSentences;
	}

	private List<Noun> classifyToModel(
			List<List<? extends HasWord>> taggedSentences) {
		List<Noun> nouns = new ArrayList<Noun>();
		int positionCounter = 0;
		for (List<? extends HasWord> sent : taggedSentences) {
			for (HasWord word : sent) {
				TaggedWord taggedWord = ((TaggedWord) word);
				nouns.add(new Noun(taggedWord.value(), taggedWord.tag(),
						positionCounter));
				positionCounter++;
			}
		}
		return nouns;
	}
}
