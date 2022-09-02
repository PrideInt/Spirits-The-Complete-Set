package me.Pride.korra.Spirits.neutral;

import com.projectkorra.projectkorra.configuration.ConfigManager;
import me.Pride.korra.Spirits.util.NotAPlugin;
import me.numin.spirits.SpiritElement;
import me.numin.spirits.ability.api.SpiritAbility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public abstract class NeutralBase extends SpiritAbility {

    public NeutralBase(Player player) {
        super(player);
    }

    @Override
    public void load() {
        super.load();

        NotAPlugin.initialize();
    }

    @Override
    public boolean isEnabled() {
        return ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Abilities.Neutral." + getName() + ".Enabled", true);
    }

    @Override
    public boolean isExplosiveAbility() {
        return false;
    }

    @Override
    public boolean isHarmlessAbility() {
        return false;
    }

    @Override
    public boolean isIgniteAbility() {
        return false;
    }

    @Override
    public String getAuthor() {
        return SpiritElement.DARK.getColor() + "" + ChatColor.UNDERLINE +
                "Prride";
    }

    @Override
    public String getVersion() {
        return SpiritElement.DARK.getColor() + "" + ChatColor.UNDERLINE +
                "Version 4";
    }
}
