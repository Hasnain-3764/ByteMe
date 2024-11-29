// File: SystemTest.java

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class SystemTest {

    @Before
    public void setUp() {
        // Reset singletons or clear data as needed
        // Resetting AuthenticationManager
        AuthenticationManager.resetInstance();
        // Resetting MenuServiceImpl
        MenuServiceImpl.resetInstance();
        // Resetting CustomerService
        CustomerService.resetInstance();
        // Resetting OrderManagerImpl
        OrderManagerImpl.resetInstance();
    }

    @Test
    public void testOrderUnavailableItem() {
        CustomerService customerService = CustomerService.getInstance();
        MenuService menuService = MenuServiceImpl.getInstance();
        MenuItem unavailableItem = new MenuItem("Unavailable Item", 100, "Snack", false, false);
        menuService.addMenuItem(unavailableItem);

        RegularCustomer customer = new RegularCustomer("TestUser", "password", "TestID");
        OrderItem orderItem = new OrderItem(unavailableItem, 1);
        List<OrderItem> items = new ArrayList<>();
        items.add(orderItem);
        Order order = new Order(customer.getLoginID(), Order.Priority.NORMAL, items, "");

        try {
            customerService.placeOrder(customer, order);
            fail("Expected DishNotAvailableException to be thrown");
        } catch (DishNotAvailableException e) {
            assertEquals("Dish Unavailable Item is not available.", e.getMessage());
        }
    }

    @Test
    public void testInvalidLogin() {
        AuthenticationManager authenticator = AuthenticationManager.getInstance();
        try {
            authenticator.login("NonExistentUser", "wrongPassword");
            fail("Expected InvalidLoginException to be thrown");
        } catch (InvalidLoginException e) {
            assertEquals("Invalid ID or password.", e.getMessage());
        }
    }

    // New Test 1: Test Successful Login
    @Test
    public void testSuccessfulLogin() {
        AuthenticationManager authenticator = AuthenticationManager.getInstance();
        // Create a test user and sign up
        RegularCustomer testUser = new RegularCustomer("TestUser", "password", "TestID");
        authenticator.signup(testUser);

        try {
            User user = authenticator.login("TestID", "password");
            assertNotNull("User should not be null after successful login", user);
            assertEquals("Logged in user should be the test user", "TestUser", user.getName());
        } catch (InvalidLoginException e) {
            fail("Did not expect InvalidLoginException to be thrown");
        }
    }

    // New Test 2: Test Placing Order with Available Item
    @Test
    public void testPlaceOrderWithAvailableItem() {
        CustomerService customerService = CustomerService.getInstance();
        MenuService menuService = MenuServiceImpl.getInstance();

        // Create an available menu item
        MenuItem availableItem = new MenuItem("Available Item", 50, "Snack", true, false);
        menuService.addMenuItem(availableItem);

        // Create a test customer
        RegularCustomer customer = new RegularCustomer("TestUser", "password", "TestID");

        // Create an order with the available item
        OrderItem orderItem = new OrderItem(availableItem, 2);
        List<OrderItem> items = new ArrayList<>();
        items.add(orderItem);
        Order order = new Order(customer.getLoginID(), Order.Priority.NORMAL, items, "");

        try {
            customerService.placeOrder(customer, order);
            // Since no exception is thrown, the test passes
            assertTrue("Order should be placed successfully", true);
        } catch (DishNotAvailableException e) {
            fail("Did not expect DishNotAvailableException to be thrown");
        }
    }

    @After
    public void tearDown() {
        // Clean up if necessary
    }
}
