package vn.edu.fpt.mss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.edu.fpt.mss.common.exception.ResourceNotFoundException;
import vn.edu.fpt.mss.dto.request.CustomerRequest;
import vn.edu.fpt.mss.dto.response.CustomerResponse;
import vn.edu.fpt.mss.entity.Customer;
import vn.edu.fpt.mss.entity.Employee;
import vn.edu.fpt.mss.repository.CustomerRepository;
import vn.edu.fpt.mss.repository.EmployeeRepository;
import vn.edu.fpt.mss.service.impl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    @DisplayName("findById should return CustomerResponse when customer exists")
    void findById_ShouldReturnCustomer_WhenExists() {
        Customer customer = Customer.builder()
                .customerId(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        CustomerResponse response = customerService.findById(1);

        assertNotNull(response);
        assertEquals(1, response.getCustomerId());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("john.doe@example.com", response.getEmail());
    }

    @Test
    @DisplayName("findById should throw ResourceNotFoundException when customer not found")
    void findById_ShouldThrowResourceNotFoundException_WhenNotFound() {
        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.findById(999));
    }

    @Test
    @DisplayName("findAll should return list of customer responses")
    void findAll_ShouldReturnAllCustomers() {
        Customer customer = Customer.builder()
                .customerId(1)
                .firstName("Alice")
                .lastName("Smith")
                .build();

        when(customerRepository.findAll()).thenReturn(List.of(customer));

        List<CustomerResponse> responses = customerService.findAll();

        assertEquals(1, responses.size());
        assertEquals("Alice", responses.get(0).getFirstName());
    }

    @Test
    @DisplayName("create should save and return new customer")
    void create_ShouldSaveCustomer() {
        CustomerRequest request = CustomerRequest.builder()
                .firstName("Bob")
                .lastName("Dylan")
                .email("bob@music.com")
                .build();

        Customer savedCustomer = Customer.builder()
                .customerId(10)
                .firstName("Bob")
                .lastName("Dylan")
                .email("bob@music.com")
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerResponse response = customerService.create(request);

        assertNotNull(response);
        assertEquals(10, response.getCustomerId());
        assertEquals("Bob", response.getFirstName());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("delete should remove customer if found")
    void delete_ShouldDelete_WhenFound() {
        Customer customer = Customer.builder()
                .customerId(5)
                .firstName("Mark")
                .lastName("Twain")
                .build();

        when(customerRepository.findById(5)).thenReturn(Optional.of(customer));

        customerService.delete(5);

        verify(customerRepository).delete(customer);
    }
}
