package models;

public class SearchResult {
    private String content;
    private String title;

    public SearchResult() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Title:" + this.title + " Cont:" +  this.content;
    }
}
