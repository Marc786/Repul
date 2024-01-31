package ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries;

import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WeeklyProductsDetail {

    private List<ProductDetail> productsDetail;

    public WeeklyProductsDetail(List<ReportBill> bills) {
        generateProductsDetail(bills);
    }

    public List<ProductDetail> getProductsDetail() {
        return productsDetail;
    }

    private void generateProductsDetail(List<ReportBill> bills) {
        productsDetail = new ArrayList<>();

        bills.forEach(bill -> productsDetail.add(bill.getDetail()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (WeeklyProductsDetail) obj;
        return Objects.equals(this.productsDetail, that.productsDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productsDetail);
    }
}
