package me.Pride.korra.Spirits.passives;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.PassiveAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;

import me.Pride.korra.Spirits.listener.AbilListener;
import me.xnuminousx.spirits.ability.api.LightAbility;
import me.xnuminousx.spirits.elements.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class SuperLuck extends LightAbility implements AddonAbility, PassiveAbility {

	public SuperLuck(Player player) {
		super(player);
		
		if (!bPlayer.hasElement(SpiritElement.LIGHT_SPIRIT)) {
			return;
		}
		boolean enabled = ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Passives.Light.SuperLuck.Enabled");
		
		if (enabled) {
			start();
		}
	}

	@Override
	public long getCooldown() {
		return 0;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public String getName() {
		return "SuperLuck";
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
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {
		if (bPlayer.hasElement(SpiritElement.LIGHT_SPIRIT)) {
			int luckDuration = ConfigManager.getConfig().getInt("ExtraAbilities.Prride.Spirits.Passives.Light.SuperLuck.LuckDuration");
			int luckAmplifier = ConfigManager.getConfig().getInt("ExtraAbilities.Prride.Spirits.Passives.Light.SuperLuck.LuckAmplifier");
			
			player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, luckDuration, luckAmplifier));
			
		} else {
			if (!bPlayer.hasElement(SpiritElement.LIGHT_SPIRIT)) {
				if (!player.isOnline() || player.isDead()) {
					remove();
					return;
				}
				remove();
				return;
			}
		}
	}
	
	@Override
	public void remove() {
		super.remove();
		
		if (player.hasPotionEffect(PotionEffectType.LUCK)) {
			player.removePotionEffect(PotionEffectType.LUCK);
		}
	}

	@Override
	public boolean isInstantiable() {
		return true;
	}

	@Override
	public boolean isProgressable() {
		return true;
	}
	
	@Override
	public String getDescription() {
		return SpiritElement.DARK_SPIRIT.getColor() + "Light spirits tend to gain much fortune "
				+ "and luck with their consistent flow of positive energy and feng shui!";
	}

	@Override
	public String getAuthor() {
		return SpiritElement.DARK_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"Prride";
	}

	@Override
	public String getVersion() {
		return SpiritElement.DARK_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"VERSION 1";
	}

	@Override
	public void load() {
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilListener(), ProjectKorra.plugin);
		
		ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Light.SuperLuck.Enabled", true);
		ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Light.SuperLuck.LuckDuration", 7777777);
		ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Light.SuperLuck.LuckAmplifier", 7);
		ConfigManager.defaultConfig.save();
	}

	@Override
	public void stop() {
		ProjectKorra.log.info(getName() + " by " + getAuthor() + " " + getVersion() + " stopped!");
	}

}
