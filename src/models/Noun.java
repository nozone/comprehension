package models;

public class Noun {

	public String word;
	
	public String tag;
	
	public int wordPosition;
	
	public Noun (String word, String tag, int wordPosition){
		this.word = word;
		this.tag = tag;
		this.wordPosition = wordPosition;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getWord_position() {
		return wordPosition;
	}

	public void setWord_position(int wordPosition) {
		this.wordPosition = wordPosition;
	}
	
}
