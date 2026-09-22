package vn.edu.fpt.mss.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.mss.dto.request.EmployeeRequest;
import vn.edu.fpt.mss.dto.response.EmployeeResponse;
import vn.edu.fpt.mss.service.EmployeeService;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Tag(name = "Employees", description = "CRUD operations for company employees and management hierarchy")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @Operation(summary = "Get all employees, optionally filtered by reportsToId")
    public List<EmployeeResponse> findAll(@RequestParam(required = false) Integer reportsToId) {
        return reportsToId == null ? employeeService.findAll() : employeeService.findByReportsTo(reportsToId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by id")
    public EmployeeResponse findById(@PathVariable Integer id) {
        return employeeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new employee")
    public EmployeeResponse create(@Valid @RequestBody EmployeeRequest request) {
        return employeeService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employee details")
    public EmployeeResponse update(@PathVariable Integer id, @Valid @RequestBody EmployeeRequest request) {
        return employeeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an employee")
    public void delete(@PathVariable Integer id) {
        employeeService.delete(id);
    }
}
