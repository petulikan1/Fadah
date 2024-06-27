package info.preva1l.fadah.config;


import com.google.common.collect.ImmutableList;
import info.preva1l.fadah.Fadah;
import info.preva1l.fadah.utils.StringUtils;
import info.preva1l.fadah.utils.config.BasicConfig;
import lombok.AllArgsConstructor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@SuppressWarnings("unchecked")
public enum Menus {
    PROFILE_YOUR_LISTINGS_LORE("your-listings.lore", List.of(
            "&fClick to view & manage",
            "&f{0} active listings!"
    )),
    PROFILE_COLLECTION_BOX_ICON("collection-box.icon", "CHEST_MINECART"),
    PROFILE_COLLECTION_BOX_MODEL_DATA("collection-box.model_data", 0),
    PROFILE_COLLECTION_BOX_NAME("collection-box.name", "&e{0} Collection Box"),
    PROFILE_COLLECTION_BOX_LORE("collection-box.lore", List.of(
            "&fClick to view & claim",
            "&f{0} purchases!"
    )),
    PROFILE_EXPIRED_LISTINGS_ICON("expired-items.icon", "ENDER_CHEST"),
    PROFILE_EXPIRED_LISTINGS_MODEL_DATA("expired-items.model_data", 0),
    PROFILE_EXPIRED_LISTINGS_NAME("expired-items.name", "&c{0} Expired Listings"),
    PROFILE_EXPIRED_LISTINGS_LORE("expired-items.lore", List.of(
            "&fClick to view & claim",
            "&f{0} expired listings!"
    )),

    PROFILE_HISTORIC_ITEMS_ICON("historic-items.icon", "WRITABLE_BOOK"),
    PROFILE_HISTORIC_ITEMS_MODEL_DATA("historic-items.model_data", 0),
    PROFILE_HISTORIC_ITEMS_NAME("historic-items.name", "&c{0} History"),
    PROFILE_HISTORIC_ITEMS_LORE("historic-items.lore", List.of(
            "&fClick to view",
            "&f{0} history!"
    )),

    // To Keep
    SEARCH_TITLE("search-title", "&9&lAuction House &8> &fSearch"),

    NO_ITEM_FOUND_ICON("no-items-found.icon", "BARRIER"),
    NO_ITEM_FOUND_MODEL_DATA("no-items-found.model_data", 0),
    NO_ITEM_FOUND_NAME("no-items-found.name", "&c&lNo items found!"),
    NO_ITEM_FOUND_LORE("no-items-found.lore", Collections.emptyList()),

    BACK_BUTTON_ICON("back.icon", "FEATHER"),
    BACK_BUTTON_MODEL_DATA("back.model_data", 0),
    BACK_BUTTON_NAME("back.name", "&cGo Back"),
    BACK_BUTTON_LORE("back.lore", Collections.singletonList("&7Click to go back")),

    PREVIOUS_BUTTON_ICON("previous_page.icon", "ARROW"),
    PREVIOUS_BUTTON_MODEL_DATA("previous_page.model_data", 0),
    PREVIOUS_BUTTON_NAME("previous_page.name", "&c&lPrevious Page"),
    PREVIOUS_BUTTON_LORE("previous_page.lore", Collections.singletonList("&7Click to go to the previous page")),

    NEXT_BUTTON_ICON("next_page.icon", "ARROW"),
    NEXT_BUTTON_MODEL_DATA("next_page.model_data", 0),
    NEXT_BUTTON_NAME("next_page.name", "&a&lNext Page"),
    NEXT_BUTTON_LORE("next_page.lore", Collections.singletonList("&7Click to go to the next page")),

    SCROLL_NEXT_BUTTON_ICON("scroll_next.icon", "ARROW"),
    SCROLL_NEXT_BUTTON_MODEL_DATA("scroll_next.model_data", 0),
    SCROLL_NEXT_BUTTON_NAME("scroll_next.name", "&a&lScroll Categories Down"),
    SCROLL_NEXT_BUTTON_LORE("scroll_next.lore", Collections.singletonList("&7Click to move the categories down")),

    SCROLL_PREVIOUS_BUTTON_ICON("scroll_previous.icon", "ARROW"),
    SCROLL_PREVIOUS_BUTTON_MODEL_DATA("scroll_previous.model_data", 0),
    SCROLL_PREVIOUS_BUTTON_NAME("scroll_previous.name", "&a&lScroll Categories Up"),
    SCROLL_PREVIOUS_BUTTON_LORE("scroll_previous.lore", Collections.singletonList("&7Click to move the categories up")),
   
    CLOSE_BUTTON_ICON("close.icon", "BARRIER"),
    CLOSE_BUTTON_MODEL_DATA("close.model_data", 0),
    CLOSE_BUTTON_NAME("close.name", "&c&l✗ Close"),
    CLOSE_BUTTON_LORE("close.lore", Collections.singletonList("&7Click to close the menu")),
    
    BORDER_ICON("filler.icon", "BLACK_STAINED_GLASS_PANE"),
    BORDER_MODEL_DATA("filler.model-data", 0),
    BORDER_NAME("filler.name", "&r "),
    BORDER_LORE("filler.lore", Collections.singletonList("&8I <3 Fadah")),
    ;

    private final String path;
    private final Object defaultValue;

    public static void loadDefault() {
        BasicConfig configFile = Fadah.getINSTANCE().getMenusFile();

        for (Menus config : Menus.values()) {
            String path = config.path;
            String str = configFile.getString(path);
            if (str.equals(path)) {
                configFile.getConfiguration().set(path, config.defaultValue);
            }
        }

        configFile.save();
        configFile.load();
    }

    @Override
    public String toString() {
        String str = Fadah.getINSTANCE().getMenusFile().getString(path);
        if (str.equals(path)) {
            return defaultValue.toString();
        }
        return str;
    }

    public String toFormattedString() {
        String str = Fadah.getINSTANCE().getMenusFile().getString(path);
        if (str.equals(path)) {
            return StringUtils.colorize(defaultValue.toString());
        }
        return StringUtils.colorize(str);
    }

    public String toFormattedString(Object... replacements) {
        String str = Fadah.getINSTANCE().getMenusFile().getString(path);
        if (str.equals(path)) {
            return StringUtils.formatPlaceholders(StringUtils.colorize(defaultValue.toString()), replacements);
        }
        return StringUtils.colorize(StringUtils.formatPlaceholders(str, replacements));
    }

    public List<String> toLore() {
        List<String> str = Fadah.getINSTANCE().getMenusFile().getStringList(path);
        if (str.isEmpty() || str.get(0).equals(path)) {
            List<String> ret = new ArrayList<>();
            for (String line : (List<String>) defaultValue) ret.add(StringUtils.formatPlaceholders(line));
            return StringUtils.colorizeList(ret);
        }
        if (str.get(0).equals("null")) {
            return ImmutableList.of();
        }
        List<String> ret = new ArrayList<>();
        for (String line : str) ret.add(StringUtils.formatPlaceholders(line));
        return StringUtils.colorizeList(ret);
    }

    public List<String> toLore(Object... replacements) {
        List<String> str = Fadah.getINSTANCE().getMenusFile().getStringList(path);
        if (str.isEmpty() || str.get(0).equals(path)) {
            List<String> ret = new ArrayList<>();
            for (String line : (List<String>) defaultValue) ret.add(StringUtils.formatPlaceholders(line, replacements));
            return StringUtils.colorizeList(ret);
        }
        if (str.get(0).equals("null")) {
            return ImmutableList.of();
        }
        List<String> ret = new ArrayList<>();
        for (String line : str) {
            ret.add(StringUtils.formatPlaceholders(line, replacements));
        }
        return StringUtils.colorizeList(ret);
    }

    public int toInteger() {
        return Integer.parseInt(toString());
    }

    public Material toMaterial() {
        Material material;
        try {
            material = Material.valueOf(toString().toUpperCase());
        } catch (EnumConstantNotPresentException | IllegalArgumentException e) {
            material = Material.APPLE;
            Fadah.getConsole().severe("-----------------------------");
            Fadah.getConsole().severe("Config Incorrect!");
            Fadah.getConsole().severe("Material: " + toFormattedString());
            Fadah.getConsole().severe("Does Not Exist!");
            Fadah.getConsole().severe("Defaulting to APPLE");
            Fadah.getConsole().severe("-----------------------------");
        }
        return material;
    }
}
