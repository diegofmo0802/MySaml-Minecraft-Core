package com.mysaml.mc.base;
import org.bukkit.ChatColor;
public class Base {
    public static String Color(String Message, char Alternate) {
        return ChatColor.translateAlternateColorCodes(Alternate, Message);
    }
    public static String Color(String Message) {
        return Color(Message, '&');
    }
}
