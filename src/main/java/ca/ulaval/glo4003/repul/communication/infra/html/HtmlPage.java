package ca.ulaval.glo4003.repul.communication.infra.html;

import ca.ulaval.glo4003.constant.Constants;
import java.util.ArrayList;
import java.util.List;

public class HtmlPage {

    private final List<String> headElements = new ArrayList<>();
    private final List<String> bodyElements = new ArrayList<>();

    public HtmlPage() {}

    public void addHeadElement(String head) {
        headElements.add(head);
    }

    public void addBodyElement(String body) {
        bodyElements.add(body);
    }

    private String buildHead() {
        StringBuilder stringBuilder = new StringBuilder(Constants.HtmlTag.START_HEAD);
        headElements.forEach(stringBuilder::append);
        stringBuilder.append(Constants.HtmlTag.END_HEAD);
        return stringBuilder.toString();
    }

    private String buildBody() {
        StringBuilder stringBuilder = new StringBuilder(Constants.HtmlTag.START_BODY);
        bodyElements.forEach(stringBuilder::append);
        stringBuilder.append(Constants.HtmlTag.END_BODY);
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(Constants.HtmlTag.START_HTML);
        String head = buildHead();
        String body = buildBody();

        stringBuilder.append(head);
        stringBuilder.append(body);
        stringBuilder.append(Constants.HtmlTag.END_HTML);

        return stringBuilder.toString();
    }
}
