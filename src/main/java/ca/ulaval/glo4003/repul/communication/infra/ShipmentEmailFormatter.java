package ca.ulaval.glo4003.repul.communication.infra;

import ca.ulaval.glo4003.repul.communication.domain.ShipmentInfo;
import ca.ulaval.glo4003.repul.communication.infra.html.HtmlPage;
import ca.ulaval.glo4003.repul.communication.infra.html.HtmlParagraph;
import ca.ulaval.glo4003.repul.communication.infra.html.HtmlTable;
import ca.ulaval.glo4003.repul.communication.infra.html.HtmlTitle;

public class ShipmentEmailFormatter {

    private static final String TITLE_TEXT = "Cargaison de boites repas";
    private static final int TITLE_CONTENT_SIZE = 2;
    private static final String PICK_POINT_CONTENT_FORMAT = "Point de ramassage: %s";
    private static final String SHIPMENT_ID_FORMAT = "ID de la cargaison: %s";

    private static final String TABLE_MEAL_KIT_ID_TEXT = "ID de la boite repas";
    private static final String TABLE_DESTINATION_TEXT = "Destination";

    public static String toEmailResponse(ShipmentInfo shipmentInfo) {
        HtmlPage htmlPage = new HtmlPage();
        HtmlTable htmlTable = new HtmlTable();
        htmlTable.addHeader(TABLE_MEAL_KIT_ID_TEXT);
        htmlTable.addHeader(TABLE_DESTINATION_TEXT);

        shipmentInfo.shipmentItemsIdAndLocation().forEach(htmlTable::addData);

        htmlPage.addHeadElement(HtmlTable.TABLE_STYLE);

        HtmlTitle title = new HtmlTitle(TITLE_TEXT, TITLE_CONTENT_SIZE);

        String pickupPointContent = String.format(
            PICK_POINT_CONTENT_FORMAT,
            shipmentInfo.pickUpPointLocation()
        );
        HtmlParagraph pickupPointParagraph = new HtmlParagraph(pickupPointContent);

        String shipmentIdContent = String.format(
            SHIPMENT_ID_FORMAT,
            shipmentInfo.shipmentId()
        );
        HtmlParagraph shipmentIdParagraph = new HtmlParagraph(shipmentIdContent);

        htmlPage.addBodyElement(title.toString());
        htmlPage.addBodyElement(pickupPointParagraph.toString());
        htmlPage.addBodyElement(shipmentIdParagraph.toString());
        htmlPage.addBodyElement(htmlTable.toString());

        return htmlPage.toString();
    }
}
