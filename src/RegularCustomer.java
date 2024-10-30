public class RegularCustomer extends Customer {

    public RegularCustomer(String name, String password, String rollNo) {
        super(name, password, rollNo);
    }

    @Override
    public void placeOrder(Order order) {
        // Implement order placement logic
        System.out.println("Regular customer order here");
    }

    public void accessRegularBenefits() {
        // Regular customer-specific benefits
    }
}
