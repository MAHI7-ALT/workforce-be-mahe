package org.imaginnovate.Repository.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.imaginnovate.dto.common.employeeproject.EmployeeProjectDto;
import org.imaginnovate.entity.common.Employee;
import org.imaginnovate.entity.common.EmployeeDivision;
import org.imaginnovate.entity.common.EmployeeProject;
import org.imaginnovate.repository.common.EmployeeProjectRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class EmployeeProjectRepoTest {

    @Inject
    EmployeeProjectRepo employeeProjectRepo;

    Employee testEmployee;
    EmployeeDivision testEmployeeDivision;
    EmployeeProject testEmployeeProject;

    @BeforeEach
    @Transactional
    public void setup() {
        testEmployee = new Employee();
        testEmployee.setId(1); 
        testEmployeeDivision = new EmployeeDivision();
        testEmployeeDivision.setId(1); 
        testEmployeeDivision.setEmployee(testEmployee);

        testEmployeeProject = new EmployeeProject();
        testEmployeeProject.setId(1);
        testEmployeeProject.setEmployeeDivision(testEmployeeDivision);
    }

    @Test
    @Transactional
    public void testFindAllEmployeeProjects() {
        List<EmployeeProjectDto> employeeProjects = employeeProjectRepo.findAllEmployeeProjects();
        assertNotNull(employeeProjects);
        assertFalse(employeeProjects.isEmpty());
    }

    @Test
    public void testFindById_Int() {
        EmployeeProject foundEmployeeProject = employeeProjectRepo.findById(testEmployeeProject.getId());
        assertNotNull(foundEmployeeProject);
        assertEquals(testEmployeeProject.getId(), foundEmployeeProject.getId());
    }

    @Test
    @Transactional
    public void testDelete() {
        employeeProjectRepo.delete(testEmployeeProject);
        EmployeeProject deletedEmployeeProject = employeeProjectRepo.findById(testEmployeeProject.getId());
        assertNull(deletedEmployeeProject);
    }

    @Test
    @Transactional
    public void testPersist() {
        EmployeeProject newEmployeeProject = new EmployeeProject();
        employeeProjectRepo.persist(newEmployeeProject);
        assertNotNull(newEmployeeProject.getId());
    }

    @Test
    @Transactional
    public void testFindByEmployeeAndProject() {
        List<EmployeeProject> employeeProjects = employeeProjectRepo.findByEmployeeAndProject(testEmployeeDivision.getId(), testEmployeeProject.getId());
        assertNotNull(employeeProjects);
        assertFalse(employeeProjects.isEmpty());
    }

    @Test
    public void testFindByEmployeeId() {
        List<EmployeeProject> employeeProjects = employeeProjectRepo.findByEmployeeId(testEmployee.getId());
        assertNotNull(employeeProjects);
        assertFalse(employeeProjects.isEmpty());
    }

    @Test
    public void testIsAssignedToProject() {
        boolean isAssigned = employeeProjectRepo.isAssignedToProject(testEmployeeDivision.getId(), testEmployeeProject.getId());
        assertTrue(isAssigned);
    }

    @Test
    public void testCanApproveInProject() {
        boolean canApprove = employeeProjectRepo.canApproveInProject(testEmployee);
        assertTrue(canApprove);
    }

    @Test
    public void testCanApproveTimesheetsInSomeProject() {
        boolean canApprove = employeeProjectRepo.canApproveTimesheetsInSomeProject(testEmployee.getId(), testEmployeeProject.getId());
        assertTrue(canApprove);
    }

    @Test
    public void testFindById_EmployeeProject() {
        EmployeeProject foundEmployeeProject = employeeProjectRepo.findById(testEmployeeProject);
        assertNotNull(foundEmployeeProject);
        assertEquals(testEmployeeProject.getId(), foundEmployeeProject.getId());
    }
}
