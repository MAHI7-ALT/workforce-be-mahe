package org.imaginnovate.Repository.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.imaginnovate.dto.common.employeedivision.EmployeeDivisionDto;
import org.imaginnovate.entity.common.Division;
import org.imaginnovate.entity.common.Employee;
import org.imaginnovate.entity.common.EmployeeDivision;
import org.imaginnovate.repository.common.EmployeeDivisionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class EmployeeDivisionRepoTest {

    @Inject
    EmployeeDivisionRepo employeeDivisionRepo;

    Employee testEmployee;
    Division testDivision;
    EmployeeDivision testEmployeeDivision;

    @BeforeEach
    @Transactional
    public void setup() {
        testEmployee = new Employee();
        testEmployee.setId(2);

        testDivision = new Division();
        testDivision.setId(2);

        testEmployeeDivision = new EmployeeDivision();
        testEmployeeDivision.setId(1); 
        testEmployeeDivision.setEmployee(testEmployee);
        testEmployeeDivision.setDivision(testDivision);
    }

    @Test
    public void testFindAllEmployeeDivisions() {
        List<EmployeeDivisionDto> employeeDivisions = employeeDivisionRepo.findAllEmployeeDivisions();
        assertNotNull(employeeDivisions);
        assertFalse(employeeDivisions.isEmpty());
    }

    // @Test
    // public void testFindByEmployeeDivisionId() {
    //     EmployeeDivision foundEmployeeDivision = employeeDivisionRepo.findByEmployeeDivisionId(testEmployee);
    //     assertNotNull(foundEmployeeDivision);
    //     assertEquals(testEmployee.getId(), foundEmployeeDivision.getEmployee().getId());
    // }

    @Test
    public void testFindById_Int() {
        EmployeeDivision foundEmployeeDivision = employeeDivisionRepo.findById(testEmployeeDivision.getId());
        assertNotNull(foundEmployeeDivision);
        assertEquals(testEmployeeDivision.getId(), foundEmployeeDivision.getId());
    }

    // @Test
    // @Transactional
    // public void testPersist() {
    //     EmployeeDivision newEmployeeDivision = new EmployeeDivision();
    //     newEmployeeDivision.setCreatedAt(null);
    //     employeeDivisionRepo.persist(newEmployeeDivision);
    //     assertNotNull(newEmployeeDivision.getId());
    // }

    @Test
    public void testFindByEmployeeId() {
        List<EmployeeDivision> employeeDivisions = employeeDivisionRepo.findByEmployeeId(testEmployee.getId());
        assertNotNull(employeeDivisions);
        assertFalse(employeeDivisions.isEmpty());
    }

    @Test
    public void testExistsByDivisionIdAndEmployeeId() {
        boolean exists = employeeDivisionRepo.existsByDivisionIdAndEmployeeId(testDivision.getId(), testEmployee.getId());
        assertTrue(exists);
    }

    @Test
    public void testCanApproveInDivision() {
        boolean canApprove = employeeDivisionRepo.canApproveInDivision(testEmployee);
        assertTrue(canApprove);
    }

    @Test
    public void testFindById_EmployeeDivision() {
        EmployeeDivision foundEmployeeDivision = employeeDivisionRepo.findById(testEmployeeDivision);
        assertNotNull(foundEmployeeDivision);
        assertEquals(testEmployeeDivision.getId(), foundEmployeeDivision.getId());
    }

    @Test
    public void testCanApproveTimesheetsInSomeDivisions() {
        boolean canApprove = employeeDivisionRepo.canApproveTimesheetsInSomeDivisions(testEmployee.getId(), testDivision);
        assertTrue(canApprove);
    }

    @Test
    public void testExistsByEmployeeIdAndDivisionId() {
        boolean exists = employeeDivisionRepo.existsByEmployeeIdAndDivisionId(testEmployee.getId(), testDivision);
        assertTrue(exists);
    }
}
