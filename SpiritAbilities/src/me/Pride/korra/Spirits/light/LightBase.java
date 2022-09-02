package me.Pride.korra.Spirits.light;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import me.Pride.korra.Spirits.neutral.NeutralBase;
import me.numin.spirits.SpiritElement;
import org.bukkit.entity.Player;

public abstract class LightBase extends NeutralBase {

    public LightBase(Player player) {
        super(player);
    }

    @Override
    public Element getElement() {
        return SpiritElement.LIGHT;
    }

    @Override
    public boolean isEnabled() {
        return ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Abilities.Light." + getName() + ".Enabled", true);
    }
}
