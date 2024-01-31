package ca.ulaval.glo4003.lib.semester.registry;

import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.SemesterCode;
import ca.ulaval.glo4003.lib.semester.Semesters;
import ca.ulaval.glo4003.lib.semester.exception.SemesterNotFoundException;
import ca.ulaval.glo4003.lib.semester.registry.finder.ReportSemesterFinder;
import ca.ulaval.glo4003.lib.semester.registry.finder.SubscriptionSemesterFinder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class SemesterRegistry
    implements SubscriptionSemesterFinder, ReportSemesterFinder {

    private final Semesters semesters;

    public SemesterRegistry(String filepath) {
        SemesterAssembler semesterAssembler = new SemesterAssembler();
        List<SemesterJson> semestersJson = readSemesterFromJsonFile(filepath);
        semesters = semesterAssembler.fromJson(semestersJson);
    }

    @Override
    public Semester findCurrentOrNextSemester(LocalDate date) {
        try {
            return semesters.findSemesterByDate(date);
        } catch (SemesterNotFoundException e) {
            return semesters.findNextSemesterByDate(date);
        }
    }

    @Override
    public Semester getSemesterByCode(SemesterCode code) {
        return semesters.getSemesterByCode(code);
    }

    @Override
    public Semester findCurrentOrPreviousSemester(LocalDate date) {
        try {
            return semesters.findSemesterByDate(date);
        } catch (SemesterNotFoundException e) {
            return semesters.findPreviousSemesterByDate(date);
        }
    }

    private List<SemesterJson> readSemesterFromJsonFile(String filepath) {
        try {
            String json = Files.readString(Path.of(filepath), StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            return List.of(objectMapper.readValue(json, SemesterJson[].class));
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
    }
}
