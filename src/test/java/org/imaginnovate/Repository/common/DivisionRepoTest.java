package org.imaginnovate.Repository.common;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.imaginnovate.dto.common.division.DivisionDto;
import org.imaginnovate.dto.common.division.DivisionDtoPost;
import org.imaginnovate.entity.common.Division;
import org.imaginnovate.repository.common.DivisionRepo;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class DivisionRepoTest {

    @Inject
    DivisionRepo divisionRepo;

    @Test
    public void testFindAllDivisions() {
        List<DivisionDto> divisions = divisionRepo.findAllDivisions();
        assertNotNull(divisions);
        assertFalse(divisions.isEmpty(), "Divisions not found");;
    }

    @Test
    public void testFindByDivisionId() {
        int divisionId = 4;
        List<Division> divisions = divisionRepo.findByDivisionId(divisionId);
        assertNotNull(divisions);
        assertFalse(divisions.isEmpty());
    }

    @Test
    public void testFindDivisionById() {
        int id = 4; 
        Optional<DivisionDtoPost> division = divisionRepo.findDivisionById(id);
        assertTrue(division.isPresent());
        assertNotNull(division.get());
    }

    @Test
    public void testFindByName() {
        String name = "Division-1"; 
        Division division = divisionRepo.findByName(name);
        assertNotNull(division);
    }

    @Test
    public void testFindById() {
        Division division = new Division(); 
        division.setId(1); 
        Division foundDivision = divisionRepo.findById(division);
        assertNotNull(foundDivision);
    }
}