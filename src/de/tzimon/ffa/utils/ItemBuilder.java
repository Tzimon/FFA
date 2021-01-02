package de.tzimon.ffa.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ItemBuilder {

    private Material material;
    private String displayName;
    private int amount;
    private Map<Enchantment, Integer> enchantments;
    private Set<ItemFlag> itemFlags;

    public ItemBuilder(Material material) {
        this.material = material;
        this.displayName = "";
        this.amount = 1;
        this.enchantments = new HashMap<>();
        this.itemFlags = new HashSet<>();
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

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        itemFlags.add(itemFlag);
        return this;
    }

    public ItemBuilder hideAll() {
        return addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .addItemFlag(ItemFlag.HIDE_DESTROYS)
                .addItemFlag(ItemFlag.HIDE_PLACED_ON)
                .addItemFlag(ItemFlag.HIDE_POTION_EFFECTS)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE);
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayName);
        itemFlags.forEach(itemMeta::addItemFlags);

        itemStack.setItemMeta(itemMeta);

        itemStack.addUnsafeEnchantments(enchantments);

        return itemStack;
    }

}
