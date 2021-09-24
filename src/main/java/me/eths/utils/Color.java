package me.eths.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class Color {

    public String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
