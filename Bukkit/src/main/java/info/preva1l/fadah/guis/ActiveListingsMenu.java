package info.preva1l.fadah.guis;

import info.preva1l.fadah.cache.ListingCache;
import info.preva1l.fadah.config.Lang;
import info.preva1l.fadah.records.Listing;
import info.preva1l.fadah.utils.TimeUtil;
import info.preva1l.fadah.utils.guis.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ActiveListingsMenu extends PaginatedFastInv {
    private final Player viewer;
    private final OfflinePlayer owner;
    private final List<Listing> listings;

    public ActiveListingsMenu(Player viewer, OfflinePlayer owner) {
        super(LayoutManager.MenuType.ACTIVE_LISTINGS.getLayout().guiSize(),
                LayoutManager.MenuType.ACTIVE_LISTINGS.getLayout().formattedTitle(viewer.getUniqueId() == owner.getUniqueId()
                ? Lang.WORD_YOUR.toCapital()
                : owner.getName()+"'s", owner.getName()+"'s"), viewer, LayoutManager.MenuType.ACTIVE_LISTINGS,
                List.of(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34));
        this.viewer = viewer;
        this.owner = owner;
        this.listings = new ArrayList<>(ListingCache.getListings().values());
        listings.removeIf(listing -> !listing.isOwner(owner.getUniqueId()));

        List<Integer> fillerSlots = getLayout().fillerSlots();
        if (!fillerSlots.isEmpty()) {
            setItems(fillerSlots.stream().mapToInt(Integer::intValue).toArray(),
                    GuiHelper.constructButton(GuiButtonType.BORDER));
        }
        addNavigationButtons();
        fillPaginationItems();
        populatePage();
        addPaginationControls();
    }

    @Override
    protected void fillPaginationItems() {
        List<String> defLore = List.of(
                "&8&n---------------------------",
                "&fCategory: &e{0}",
                "&r ",
                "&fPrice: &6${1}",
                "&r ",
                "&fExpires In: &e{2}",
                "&r ",
                "&eClick To Cancel This Listing!",
                "&8&n---------------------------"
        );
        for (Listing listing : listings) {
            ItemBuilder itemStack = new ItemBuilder(listing.getItemStack().clone())
                    .addLore(getLang().getLore("lore", defLore, listing.getCategoryID(), listing.getPrice(),
                            TimeUtil.formatTimeUntil(listing.getDeletionDate())));

            addPaginationItem(new PaginatedItem(itemStack.build(), e -> {
                listing.cancel(viewer);
                updatePagination();
            }));
        }
    }

    @Override
    protected void addPaginationControls() {
        setItem(getLayout().buttonSlots().getOrDefault(LayoutManager.ButtonType.PAGINATION_CONTROL_ONE, 39),
                GuiHelper.constructButton(GuiButtonType.BORDER));
        setItem(getLayout().buttonSlots().getOrDefault(LayoutManager.ButtonType.PAGINATION_CONTROL_TWO,41),
                GuiHelper.constructButton(GuiButtonType.BORDER));
        if (page > 0) {
            setItem(getLayout().buttonSlots().getOrDefault(LayoutManager.ButtonType.PAGINATION_CONTROL_ONE, 39),
                    GuiHelper.constructButton(GuiButtonType.PREVIOUS_PAGE), e -> previousPage());
        }

        if (listings != null && listings.size() >= index + 1) {
            setItem(getLayout().buttonSlots().getOrDefault(LayoutManager.ButtonType.PAGINATION_CONTROL_TWO,41),
                    GuiHelper.constructButton(GuiButtonType.NEXT_PAGE), e -> nextPage());
        }
    }

    @Override
    protected void updatePagination() {
        this.listings.clear();
        this.listings.addAll(ListingCache.getListings().values());
        listings.removeIf(listing -> !listing.isOwner(owner.getUniqueId()));
        super.updatePagination();
    }

    private void addNavigationButtons() {
        setItem(getLayout().buttonSlots().getOrDefault(LayoutManager.ButtonType.BACK, 36),
                GuiHelper.constructButton(GuiButtonType.BACK), e ->
                        new ProfileMenu(viewer, owner).open(viewer));
    }
}