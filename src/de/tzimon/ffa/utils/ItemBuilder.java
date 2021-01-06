package de.tzimon.ffa.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.*;

public class ItemBuilder {

    private Material material;
    private int amount;
    private short subId;
    private String displayName;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments;
    private Set<ItemFlag> itemFlags;

    private ItemBuilder() {
        this(Material.AIR);
    }

    public ItemBuilder(Material material) {
        this.material = material;
        this.subId = 0;
        this.amount = 1;
        this.displayName = "";
        lore = new ArrayList<>();
        this.enchantments = new HashMap<>();
        this.itemFlags = new HashSet<>();
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setSubId(short subId) {
        this.subId = subId;
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        Collections.addAll(this.lore, lore);
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

//    public ItemBuilder hideAll() {
//        return addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
//                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
//                .addItemFlag(ItemFlag.HIDE_DESTROYS)
//                .addItemFlag(ItemFlag.HIDE_PLACED_ON)
//                .addItemFlag(ItemFlag.HIDE_POTION_EFFECTS)
//                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE);
//    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount, subId);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemFlags.forEach(itemMeta::addItemFlags);

        itemStack.setItemMeta(itemMeta);

        itemStack.addUnsafeEnchantments(enchantments);

        return itemStack;
    }

    public ItemBuilder clone() {
        ItemBuilder clone = new ItemBuilder();

        for (Field field : getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                field.set(clone, field.get(this));
            } catch (IllegalAccessException ignored) {}
        }

        return clone;
    }

}
