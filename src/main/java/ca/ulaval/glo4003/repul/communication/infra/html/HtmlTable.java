package ca.ulaval.glo4003.repul.communication.infra.html;

import ca.ulaval.glo4003.constant.Constants;
import ca.ulaval.glo4003.repul.communication.domain.ShipmentItemAndLocation;
import java.util.ArrayList;
import java.util.List;

public class HtmlTable {

    public static final String TABLE_STYLE =
        "<style>" +
        "        table {" +
        "            border-collapse: collapse;" +
        "            width: 100%;" +
        "        }" +
        "        th, td {" +
        "            border: 1px solid #dddddd;" +
        "            text-align: left;" +
        "            padding: 8px;" +
        "        }" +
        "        th {" +
        "            background-color: #f2f2f2;" +
        "        }" +
        "    </style>";

    private static final String HEAD_FORMAT = "<th>%s</th>";
    private static final String DATA_FORMAT = "<td>%s</td>";
    private final List<String> headers = new ArrayList<>();
    private final List<ShipmentItemAndLocation> mealKitShipmentItems = new ArrayList<>();

    public void addHeader(String header) {
        headers.add(header);
    }

    public void addData(ShipmentItemAndLocation itemAndLocation) {
        mealKitShipmentItems.add(itemAndLocation);
    }

    private String buildHeaders() {
        StringBuilder stringBuilder = new StringBuilder(
            Constants.HtmlTag.START_TABLE_HEAD + Constants.HtmlTag.START_TABLE_ROW
        );
        headers.forEach(header -> stringBuilder.append(String.format(HEAD_FORMAT, header))
        );
        stringBuilder.append(
            Constants.HtmlTag.END_TABLE_HEAD + Constants.HtmlTag.END_TABLE_ROW
        );
        return stringBuilder.toString();
    }

    private String buildRow(ShipmentItemAndLocation shipmentItem) {
        return (
            Constants.HtmlTag.START_TABLE_ROW +
            String.format(DATA_FORMAT, shipmentItem.shipmentItemId()) +
            String.format(DATA_FORMAT, shipmentItem.shipmentItemLocation()) +
            Constants.HtmlTag.END_TABLE_ROW
        );
    }

    private String buildBody() {
        StringBuilder stringBuilder = new StringBuilder(
            Constants.HtmlTag.START_TABLE_BODY
        );
        mealKitShipmentItems.forEach(shipmentItem ->
            stringBuilder.append(buildRow(shipmentItem))
        );
        stringBuilder.append(Constants.HtmlTag.END_TABLE_BODY);
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(Constants.HtmlTag.START_TABLE);
        String header = buildHeaders();
        String body = buildBody();

        stringBuilder.append(header);
        stringBuilder.append(body);
        stringBuilder.append(Constants.HtmlTag.END_TABLE);

        return stringBuilder.toString();
    }
}
