import javax.xml.crypto.Data;
import java.util.*;


public class MenuServiceImpl implements MenuService{
//    private Map<String, MenuItem> menuItemsByName;
//    private Map<String, List<MenuItem>> menuItemsByType;
//    private Map<Boolean, List<MenuItem>> menuItemsByAvailability;

    private List<MenuItem> menuItems;
    public MenuServiceImpl() {
//        menuItemsByType = new HashMap<>();
//        menuItemsByName = new HashMap<>();
//        menuItemsByAvailability = new HashMap<>();
//        menuItemsByAvailability.put(true, new ArrayList<>());
//        menuItemsByAvailability.put(false, new ArrayList<>());

        List<MenuItem> initialMenuItems = DataInitializer.initilizeMenuItems();
        this.menuItems = new ArrayList<>(); // allocating data is a must step.
        menuItems.addAll(initialMenuItems);
//        this.menuItems = new ArrayList<>(); // if you dont want initialised data
    }


    @Override
    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    @Override
    public boolean updateMenuItem(MenuItem updatedItem) {
        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getName().equals(updatedItem.getName())) {
                menuItems.set(i, updatedItem); // Update the item in-place
                return true; // Update successful
            }
        }
        return false; // Item not found
    }

    @Override
    public void removeMenuItem(String itemName) {
        menuItems.removeIf(item -> item.getName().equals(itemName));
    }

    @Override
    public List<MenuItem> getAllItems(){
        return new ArrayList<>(menuItems);  // Return a copy of the list
    }


    @Override
    public List<MenuItem> searchItems(String keyword) {
        List<MenuItem> results = new ArrayList<>();
        for(MenuItem item: menuItems){
            if(item.getName().toLowerCase().contains(keyword.toLowerCase())){
                results.add(item);
            }
        }
        return results;
    }

    @Override
    public List<MenuItem> filterItemsByCategory(String category) {
        List<MenuItem> results = new ArrayList<>();
        for(MenuItem item: menuItems){
            if(item.getType().equalsIgnoreCase(category)){
                results.add(item);
            }
        }
        return results;
    }

    @Override
    public List<MenuItem> sortItemsByPrice(boolean ascending) {
        List<MenuItem> sortedItems = new ArrayList<>(menuItems);
        Collections.sort(sortedItems, new Comparator<MenuItem>(){
            @Override
            public int compare(MenuItem item1, MenuItem item2){
                if(ascending){
                    return Double.compare(item1.getPrice(), item2.getPrice());
                }
                else{
                    return Double.compare(item2.getPrice(), item1.getPrice());
                }
            }
        });
        return sortedItems;
    }

    @Override
    public List<MenuItem> filterItemsByAvailability(boolean available){
        List<MenuItem> results = new ArrayList<>();
        for (MenuItem item : menuItems) {
            if (item.isAvailable() == available) {
                results.add(item);
            }
        }
        return results;
    }

}
