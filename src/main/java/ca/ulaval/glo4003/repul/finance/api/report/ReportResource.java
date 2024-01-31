package ca.ulaval.glo4003.repul.finance.api.report;

import ca.ulaval.glo4003.middleware.auth.annotation.RBAC;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.finance.api.report.assembler.ReportAssembler;
import ca.ulaval.glo4003.repul.finance.api.report.dto.response.ReportResponse;
import ca.ulaval.glo4003.repul.finance.application.report.ReportService;
import ca.ulaval.glo4003.repul.finance.domain.report.Report;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Optional;

@Path("/report")
@Produces(MediaType.APPLICATION_JSON)
public class ReportResource {

    private final ReportAssembler reportAssembler;
    private final ReportService reportService;

    public ReportResource(ReportAssembler reportAssembler, ReportService reportService) {
        this.reportAssembler = reportAssembler;
        this.reportService = reportService;
    }

    @GET
    @RBAC(roles = { Role.ADMIN })
    public Response getReport(@QueryParam("semesterCode") Optional<String> semesterCode) {
        Report report = reportService.generateReport(semesterCode);
        ReportResponse reportResponse = reportAssembler.toResponse(report);

        return Response.ok().entity(reportResponse).build();
    }
}
