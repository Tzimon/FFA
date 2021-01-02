package de.tzimon.ffa.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

    private Material material;
    private String displayName;

    public ItemBuilder(Material material) {
        this.material = material;
        this.displayName = "";
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayName);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
