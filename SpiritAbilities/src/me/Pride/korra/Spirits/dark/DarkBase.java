package me.Pride.korra.Spirits.dark;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import me.Pride.korra.Spirits.neutral.NeutralBase;
import me.numin.spirits.SpiritElement;
import org.bukkit.entity.Player;

public abstract class DarkBase extends NeutralBase {

    public DarkBase(Player player) {
        super(player);
    }

    @Override
    public Element getElement() {
        return SpiritElement.DARK;
    }

    @Override
    public boolean isEnabled() {
        return ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Abilities.Dark." + getName() + ".Enabled", true);
    }
}
