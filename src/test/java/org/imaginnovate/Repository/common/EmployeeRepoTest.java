package org.imaginnovate.Repository.common;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.imaginnovate.dto.common.employee.EmployeeDto;
import org.imaginnovate.entity.common.Employee;
import org.imaginnovate.repository.common.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class EmployeeRepoTest {

    @Inject
    EmployeeRepo employeeRepo;

    Employee testEmployee;

    @BeforeEach
    @Transactional
    public void setup() {
        // Setup a test employee entity
        testEmployee = new Employee();
        testEmployee.setId(1); // assuming this employee ID exists in the database
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setGender('M');
        testEmployee.setEmail("john.doe@example.com");
        testEmployee.setDesignation("Developer");
        testEmployee.setStartDate(LocalDateTime.now().minusYears(2));
        testEmployee.setEndDate(null);
        testEmployee.setCanApproveTimesheets(true);
        testEmployee.setCreatedAt(LocalDateTime.now().minusYears(2));
        testEmployee.setModifiedAt(LocalDateTime.now().minusYears(1));
        employeeRepo.persist(testEmployee);
    }

    @Test
    @Transactional
    public void testFindAllEmployees() {
        List<EmployeeDto> employees = employeeRepo.findAllEmployees();
        assertNotNull(employees);
        assertFalse(employees.isEmpty());
    }

    @Test
    @Transactional
    public void testFindById_Int() {
        Employee foundEmployee = employeeRepo.findById(testEmployee.getId());
        assertNotNull(foundEmployee);
        assertEquals(testEmployee.getId(), foundEmployee.getId());
    }

    @Test
    @Transactional
    public void testFindByIdOptional() {
        Optional<Employee> optionalEmployee = employeeRepo.findByIdOptional(testEmployee.getId());
        assertTrue(optionalEmployee.isPresent());
        assertEquals(testEmployee.getId(), optionalEmployee.get().getId());
    }

    // @Test
    // @Transactional
    // public void testFindEmployeeDtoById_Employee() {
    //     EmployeeDtoPut1 employeeDto = employeeRepo.findEmployeeDtoById(testEmployee);
    //     assertNotNull(employeeDto);
    //     assertEquals(testEmployee.getId(), employeeDto.getId());
    // }

    // @Test
    // @Transactional
    // public void testFindEmployeeDtoById_Int() {
    //     EmployeeDtoPut1 employeeDto = employeeRepo.findById(testEmployee.getId());
    //     assertNotNull(employeeDto);
    //     assertEquals(testEmployee.getId(), employeeDto.getId());
    // }

    @Test
    @Transactional
    public void testCanApproveTimesheets() {
        boolean canApprove = employeeRepo.canApproveTimesheets(testEmployee.getId());
        assertTrue(canApprove);
    }

    @Test
    @Transactional
    public void testCanApprove() {
        boolean canApprove = employeeRepo.canApprove(testEmployee);
        assertTrue(canApprove);
    }

    @Test
    @Transactional
    public void testFindByEmployeeName() {
        Employee foundEmployee = employeeRepo.findByEmployeeName("John Doe");
        assertNotNull(foundEmployee);
        assertEquals(testEmployee.getId(), foundEmployee.getId());
    }

    @Test
    @Transactional
    public void testFindEmployeeById() {
        Employee foundEmployee = employeeRepo.findEmployeeById(testEmployee.getId());
        assertNotNull(foundEmployee);
        assertEquals(testEmployee.getId(), foundEmployee.getId());
    }
}
