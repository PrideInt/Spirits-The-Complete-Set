package me.Pride.korra.Spirits.passives;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.PassiveAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;

import me.Pride.korra.Spirits.listener.AbilListener;
import me.numin.spirits.ability.api.DarkAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class DarkAlliance extends DarkAbility implements AddonAbility, PassiveAbility {

	public DarkAlliance(Player player) {
		super(player);
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
		return "DarkAlliance";
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
		
	}

	@Override
	public boolean isInstantiable() {
		return false;
	}

	@Override
	public boolean isProgressable() {
		return false;
	}
	
	@Override
	public String getDescription() {
		return SpiritElement.LIGHT_SPIRIT.getColor() + "Dark spirits and mobs alike are mutual with one another! "
				+ "Their alliance compels monsters to not target the dark spirits.";
	}

	@Override
	public String getAuthor() {
		return SpiritElement.LIGHT_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"Prride";
	}

	@Override
	public String getVersion() {
		return SpiritElement.LIGHT_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"VERSION 3";
	}

	@Override
	public void load() {
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilListener(), ProjectKorra.plugin);
		
		ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Dark.DarkAlliance.Enabled", true);
		ConfigManager.defaultConfig.save();
	}

	@Override
	public void stop() {
		ProjectKorra.log.info(getName() + " by " + getAuthor() + " " + getVersion() + " stopped!");
	}

}
