import javax.xml.crypto.Data;
import java.util.*;
import java.util.stream.Collectors;


public class MenuServiceImpl implements MenuService{
    private List<MenuItem> menuItems;
    private static MenuServiceImpl instance;

    // private constructor as to stop external instantiation.
    private MenuServiceImpl() {
        List<MenuItem> initialMenuItems = DataInitializer.initilizeMenuItems();
        this.menuItems = new ArrayList<>(); // allocating data is a must step.
        menuItems.addAll(initialMenuItems);
//        this.menuItems = new ArrayList<>(); // if you dont want initialised data
    }

    // this is method to access universal instance of menu manager.
    public static MenuServiceImpl getInstance() {
        if (instance == null) {
            instance = new MenuServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean addMenuItem(MenuItem item){
        //checking for duplicates
        for (MenuItem menuItem : menuItems){
            if (menuItem.getName().equalsIgnoreCase(item.getName())){
                DisplayUtils.printFailure("Error: Menu item '" + item.getName() + "' already exists.");
                return false; // Indicate failure to add due to duplication
            }
        }
        menuItems.add(item);
        DisplayUtils.printSuccess("Menu item '" + item.getName() + "' added successfully.");
        return true; // Indicate successful addition
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
        boolean removed = menuItems.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
        if(removed){
            DisplayUtils.printSuccess("Menu item '" + itemName + "' removed successfully.");
        }
        else{
            DisplayUtils.printFailure("Menu item '" + itemName + "' not found.");
        }
    }

    @Override
    public List<MenuItem> getAllItems(){
        return new ArrayList<>(menuItems);  // Return a copy of the list
    }


    @Override
    public List<MenuItem> searchItems(String keyword) {
        String lowerKeyword = keyword.toLowerCase().trim();
        if(lowerKeyword.isEmpty()){
            return new ArrayList<>();
        }
        return menuItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
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

    @Override
    public List<MenuItem> filterVipExclusiveItems() {
        List<MenuItem> results = new ArrayList<>();
        for(MenuItem item : menuItems){
            if(item.isVipExclusive()){
                results.add(item);
            }
        }
        return results;
    }

}
