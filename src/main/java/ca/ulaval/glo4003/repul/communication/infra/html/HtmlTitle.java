package ca.ulaval.glo4003.repul.communication.infra.html;

public class HtmlTitle {

    private static final String FORMAT = "<h%s>%s</h%s>";
    private final int size;
    private final String content;

    public HtmlTitle(String content, int size) {
        this.content = content;
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, size, content, size);
    }
}
