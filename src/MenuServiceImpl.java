// File: MenuServiceImpl.java
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;
import java.util.stream.Collectors;

public class MenuServiceImpl implements MenuService {
    private List<MenuItem> menuItems;
    private static MenuServiceImpl instance;
    private static final String MENU_DATA_FILE = "data/menu_items.json";

    private MenuServiceImpl() {
        menuItems = new ArrayList<>();
        loadMenuItemsFromFile();
    }

    public static MenuServiceImpl getInstance() {
        if (instance == null) {
            instance = new MenuServiceImpl();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public void loadMenuItemsFromFile() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(MENU_DATA_FILE)));
            JSONArray itemsArray = new JSONArray(content);
            menuItems.clear();
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject itemJSON = itemsArray.getJSONObject(i);
                MenuItem item = MenuItem.fromJSON(itemJSON);
                menuItems.add(item);
            }
        } catch (IOException e) {
            System.out.println("Menu data file not found. Starting with default menu items.");
            menuItems = DataInitializer.initilizeMenuItems();
            saveMenuItemsToFile();
        }
    }

    public void saveMenuItemsToFile() {
        JSONArray itemsArray = new JSONArray();
        for (MenuItem item : menuItems) {
            itemsArray.put(item.toJSON());
        }
        try (FileWriter file = new FileWriter(MENU_DATA_FILE)) {
            file.write(itemsArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addMenuItem(MenuItem item) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getName().equalsIgnoreCase(item.getName())) {
                DisplayUtils.printFailure("Error: Menu item '" + item.getName() + "' already exists.");
                return false;
            }
        }
        menuItems.add(item);
        saveMenuItemsToFile();
        DisplayUtils.printSuccess("Menu item '" + item.getName() + "' added successfully.");
        return true;
    }

    @Override
    public boolean updateMenuItem(MenuItem updatedItem) {
        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getName().equals(updatedItem.getName())) {
                menuItems.set(i, updatedItem);
                saveMenuItemsToFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeMenuItem(String itemName) {
        boolean removed = menuItems.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
        if (removed) {
            saveMenuItemsToFile();
            DisplayUtils.printSuccess("Menu item '" + itemName + "' removed successfully.");
        } else {
            DisplayUtils.printFailure("Menu item '" + itemName + "' not found.");
        }
    }

    @Override
    public List<MenuItem> getAllItems() {
        return new ArrayList<>(menuItems);
    }

    @Override
    public List<MenuItem> searchItems(String keyword) {
        String lowerKeyword = keyword.toLowerCase().trim();
        if (lowerKeyword.isEmpty()) {
            return new ArrayList<>();
        }
        return menuItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItem> filterItemsByCategory(String category) {
        List<MenuItem> results = new ArrayList<>();
        for (MenuItem item : menuItems) {
            if (item.getType().equalsIgnoreCase(category)) {
                results.add(item);
            }
        }
        return results;
    }

    @Override
    public List<MenuItem> sortItemsByPrice(boolean ascending) {
        List<MenuItem> sortedItems = new ArrayList<>(menuItems);
        Collections.sort(sortedItems, (item1, item2) -> {
            if (ascending) {
                return Double.compare(item1.getPrice(), item2.getPrice());
            } else {
                return Double.compare(item2.getPrice(), item1.getPrice());
            }
        });
        return sortedItems;
    }

    @Override
    public List<MenuItem> filterItemsByAvailability(boolean available) {
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
        for (MenuItem item : menuItems) {
            if (item.isVipExclusive()) {
                results.add(item);
            }
        }
        return results;
    }
}
