package me.Pride.korra.Spirits.neutral;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.PassiveAbility;
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
                "v4";
    }

    @Override
    public String getInstructions() {
        String elementName = this.getElement().getName();
        if (this.getElement() instanceof Element.SubElement && !(this.getElement() instanceof Element.MultiSubElement)) {
            elementName = ((Element.SubElement) this.getElement()).getParentElement().getName();
        }
        if (this instanceof ComboAbility) {
            elementName = elementName + ".Combo";
        }
        return ConfigManager.languageConfig.get().contains("Abilities." + elementName + "." + this.getName() + ".Instructions") ? ConfigManager.languageConfig.get().getString("Abilities." + elementName + "." + this.getName() + ".Instructions") : "";
    }

    @Override
    public String getDescription() {
        String elementName = this.getElement().getName();
        if (this.getElement() instanceof Element.SubElement) {
            elementName = ((Element.SubElement) this.getElement()).getParentElement().getName();
        }
        if (this instanceof PassiveAbility) {
            return ConfigManager.languageConfig.get().getString("Abilities." + elementName + ".Passive." + this.getName() + ".Description");
        } else if (this instanceof ComboAbility) {
            return ConfigManager.languageConfig.get().getString("Abilities." + elementName + ".Combo." + this.getName() + ".Description");
        }
        return ConfigManager.languageConfig.get().getString("Abilities." + elementName + "." + this.getName() + ".Description");
    }
}
