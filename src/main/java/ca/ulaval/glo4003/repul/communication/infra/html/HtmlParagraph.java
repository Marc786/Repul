package ca.ulaval.glo4003.repul.communication.infra.html;

public class HtmlParagraph {

    private static final String FORMAT = "<p>%s</p>";
    private final String content;

    public HtmlParagraph(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, content);
    }
}
