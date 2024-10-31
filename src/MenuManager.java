import java.util.*;

public class MenuManager implements MenuService{
//    private Map<String, MenuItem> menuItemsByName;
//    private Map<String, List<MenuItem>> menuItemsByType;
//    private Map<Boolean, List<MenuItem>> menuItemsByAvailability;

    private List<MenuItem> menuItems;
    public MenuManager() {
//        menuItemsByType = new HashMap<>();
//        menuItemsByName = new HashMap<>();
//        menuItemsByAvailability = new HashMap<>();
//        menuItemsByAvailability.put(true, new ArrayList<>());
//        menuItemsByAvailability.put(false, new ArrayList<>());
        this.menuItems = new ArrayList<>();
    }


    @Override
    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    @Override
    public void updateMenuItem(MenuItem item) {
        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getName().equals(item.getName())) {
                menuItems.set(i, item); // Update the item in-place
                break;
            }
        }
    }

    @Override
    public void removeMenuItem(String itemName) {
        menuItems.removeIf(item -> item.getName().equals(itemName));
    }

    @Override
    public List<MenuItem> getAllItems() {
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
    public List<MenuItem> getItemsByAvailability(boolean available){
        List<MenuItem> sortedItems = new ArrayList<>(menuItems);
        Collections.sort(sortedItems, new Comparator<MenuItem>() {
            @Override
            public int compare(MenuItem menuItem1, MenuItem menuItem2) {
                return Boolean.compare(menuItem2.isAvailable(), menuItem1.isAvailable());
            }
        });
        return sortedItems;
    }

}
