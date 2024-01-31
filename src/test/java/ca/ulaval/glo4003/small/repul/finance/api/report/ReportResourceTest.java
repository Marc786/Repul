package ca.ulaval.glo4003.small.repul.finance.api.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.fixture.response.ReportResponseFixture;
import ca.ulaval.glo4003.repul.finance.api.report.ReportResource;
import ca.ulaval.glo4003.repul.finance.api.report.assembler.ReportAssembler;
import ca.ulaval.glo4003.repul.finance.api.report.dto.response.ReportResponse;
import ca.ulaval.glo4003.repul.finance.application.report.ReportService;
import ca.ulaval.glo4003.repul.finance.domain.report.Report;
import jakarta.ws.rs.core.Response;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportResourceTest {

    private static final String SEMESTER_CODE = "H24";
    private final ReportResponseFixture reportResponseFixture =
        new ReportResponseFixture();
    private final ReportResponse REPORT_RESPONSE = reportResponseFixture.build();
    private final Report reportMock = mock(Report.class);
    private final ReportAssembler reportAssemblerMock = mock(ReportAssembler.class);
    private final ReportService reportServiceMock = mock(ReportService.class);
    private ReportResource reportResource;

    @BeforeEach
    void setup() {
        reportResource = new ReportResource(reportAssemblerMock, reportServiceMock);
    }

    @Test
    void getReport_returnsExpectedResponseStatus() {
        int expectedStatusCode = Response.Status.OK.getStatusCode();
        when(reportServiceMock.generateReport(Optional.of(SEMESTER_CODE)))
            .thenReturn(reportMock);
        when(reportAssemblerMock.toResponse(reportMock)).thenReturn(REPORT_RESPONSE);

        Response response = reportResource.getReport(Optional.of(SEMESTER_CODE));

        assertEquals(expectedStatusCode, response.getStatus());
    }

    @Test
    void getReport_returnsExpectedResponse() {
        when(reportServiceMock.generateReport(Optional.of(SEMESTER_CODE)))
            .thenReturn(reportMock);
        when(reportAssemblerMock.toResponse(reportMock)).thenReturn(REPORT_RESPONSE);

        Response response = reportResource.getReport(Optional.of(SEMESTER_CODE));

        assertEquals(REPORT_RESPONSE, response.getEntity());
    }

    @Test
    void getReport_callsReportService() {
        reportResource.getReport(Optional.of(SEMESTER_CODE));

        verify(reportServiceMock).generateReport(Optional.of(SEMESTER_CODE));
    }
}
