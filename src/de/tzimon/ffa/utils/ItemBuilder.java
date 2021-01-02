package de.tzimon.ffa.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class ItemBuilder {

    private Material material;
    private String displayName;
    private int amount;
    private Map<Enchantment, Integer> enchantments;

    public ItemBuilder(Material material) {
        this.material = material;
        this.displayName = "";
        this.amount = 1;
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayName);

        itemStack.setItemMeta(itemMeta);

        itemStack.addUnsafeEnchantments(enchantments);

        return itemStack;
    }

}
