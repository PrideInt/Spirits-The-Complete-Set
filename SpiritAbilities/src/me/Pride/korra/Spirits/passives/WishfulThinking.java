package me.Pride.korra.Spirits.passives;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.PassiveAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.Pride.korra.Spirits.listener.AbilListener;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class WishfulThinking extends LightAbility implements AddonAbility, PassiveAbility {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Passives.Light.WishfulThinking.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private int regenPower;
	private int regenDuration;
	private long effectDuration;

	public WishfulThinking(Player player) {
		super(player);
		
		if (!bPlayer.hasElement(SpiritElement.LIGHT_SPIRIT)) {
			return;
		}
		
		regenPower = config.getInt(path + "EffectAmplifier");
		effectDuration = config.getLong(path + "EffectDuration");
		
		regenDuration = Math.toIntExact((effectDuration * 1000) / 50);
		
		boolean enabled = ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Passives.Light.WishfulThinking.Enabled");
		
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
		return "WishfulThinking";
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
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, regenDuration, regenPower));
		ParticleEffect.HEART.display(player.getLocation().add(0, 2, 0), 1, 0F, 0F, 0F, 0F);
		ParticleEffect.SPELL_INSTANT.display(player.getLocation().add(0, 1, 0), 5, 0.3F, 0.3F, 0.3F, 0.3F);
		bPlayer.addCooldown(this);
		remove();
		return;
	}

	@Override
	public boolean isInstantiable() {
		return false;
	}

	@Override
	public boolean isProgressable() {
		return true;
	}
	
	@Override
	public String getDescription() {
		return SpiritElement.DARK_SPIRIT.getColor() + "Light spirits are the embodiment of positive energy! "
				+ "With the mentality of hopeful and positive thinking, they gain positive effects! "
				+ "Everytime you get hit, there is a small chance of getting regeneration.";
	}

	@Override
	public String getAuthor() {
		return SpiritElement.DARK_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"Prride";
	}

	@Override
	public String getVersion() {
		return SpiritElement.DARK_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"VERSION 3";
	}

	@Override
	public void load() {
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilListener(), ProjectKorra.plugin);
		
		ConfigManager.getConfig().addDefault(path + "Enabled", true);
		ConfigManager.getConfig().addDefault(path + "EffectDuration", 6);
		ConfigManager.getConfig().addDefault(path + "EffectAmplifier", 0);
		ConfigManager.getConfig().addDefault(path + "Chance", 0.01);
		ConfigManager.defaultConfig.save();
	}

	@Override
	public void stop() {
		ProjectKorra.log.info(getName() + " by " + getAuthor() + " " + getVersion() + " stopped!");
	}

}
