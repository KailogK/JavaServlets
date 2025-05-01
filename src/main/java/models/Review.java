package models;

public class Review{

	private int id;
    private String initialText, writerText;

    public Review (String initialText) {
    	setInitialText(initialText);
    }
    
    public Review (int id, String initialText, String writerText) {
    	setId(id);
    	setInitialText(initialText);
    	setWriterText(writerText);
    }

    public void setInitialText (String initialText) {
        this.initialText = initialText;
    }

    public void setWriterText(String writerText) {
        this.writerText = writerText;
    }
    
    public void setId(int id) {
    	this.id = id;    
    }
    
    public String getInitialText() {
    	return initialText;
    }
    
    public String getWriterText() {
    	return writerText;
    }
    
    public int getId() {
    	return id;
    }
    
    @Override
    public String toString() {
    	String text;
    	text = "User: " + getInitialText() + "<br>";
    	if (getWriterText()!=null) {
    		text += "Writer: " +  getWriterText() + "<br><br>";
    	}
    	return text;
    }

}