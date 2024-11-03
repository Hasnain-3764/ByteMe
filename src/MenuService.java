import java.util.List;

public interface MenuService {
    boolean addMenuItem(MenuItem item);
    boolean updateMenuItem(MenuItem item);
    void removeMenuItem(String itemName);
    List<MenuItem> getAllItems();  // View all items
    List<MenuItem> searchItems(String keyword);  // search items by name or keyword
    List<MenuItem> filterItemsByCategory(String category);  // filter items by category
    List<MenuItem> sortItemsByPrice(boolean ascending);  // sort items by price
    List<MenuItem> filterItemsByAvailability(boolean available); // sort based on availability

}
