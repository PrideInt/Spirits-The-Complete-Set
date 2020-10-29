package me.Pride.korra.Spirits.neutral;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.Pride.korra.Spirits.listener.AbilListener;
import me.numin.spirits.ability.api.SpiritAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class Float extends SpiritAbility implements AddonAbility {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Abilities.Neutral.Float.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private long cooldown;
	private int floatPower;
	private long duration;
	
	private int floatDuration;
	private long time;

	public Float(Player player) {
		super(player);
		
		if (bPlayer.isOnCooldown(this)) {
			return;
		}
		
		cooldown = config.getLong(path + "Cooldown");
		duration = config.getInt(path + "FloatDuration");
		floatPower = config.getInt(path + "FloatPower");
		
		floatDuration = Math.toIntExact((duration * 1000) / 50);
		time = System.currentTimeMillis();
		
		start();
	}
	
	@Override
	public boolean isHiddenAbility() {
		return ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Abilities.Neutral.Float.Disabled");
	}

	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public String getName() {
		return "Float";
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
		Element lightSpirit = SpiritElement.LIGHT_SPIRIT;
		Element darkSpirit = SpiritElement.DARK_SPIRIT;
		
		if (bPlayer.hasElement(darkSpirit)) {
			ParticleEffect.SPELL_WITCH.display(player.getLocation(), 5, 0.2F, 0.2F, 0.2F, 0.2F);
			
		} else if (bPlayer.hasElement(lightSpirit)) {
			ParticleEffect.SPELL_INSTANT.display(player.getLocation(), 5, 0.2F, 0.2F, 0.2F, 0.2F);
		} else {
			ParticleEffect.CRIT_MAGIC.display(player.getLocation(), 5, 0.2F, 0.2F, 0.2F, 0.2F);
		}
		
		if (System.currentTimeMillis() > time + duration) {
			remove();
			return;
		} else {
			player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, floatDuration, floatPower));
		}
		bPlayer.addCooldown(this);
	}
	
	@Override
	public void remove() {
		super.remove();
		if (player.hasPotionEffect(PotionEffectType.LEVITATION)) {
			player.removePotionEffect(PotionEffectType.LEVITATION);
		}
	}

	@Override
	public String getDescription() {
		return ChatColor.AQUA + "Some spirits are able to levitate and even fly through the air! The physiology "
				+ "of these spirits allow them to float for a while.";
	}
	
	@Override
	public String getInstructions() {
		return ChatColor.GOLD + "To use this ability, left click and you are able to float.";
	}

	@Override
	public String getAuthor() {
		return ChatColor.AQUA + "" + ChatColor.UNDERLINE + 
				"Prride";
	}

	@Override
	public String getVersion() {
		return ChatColor.AQUA + "" + ChatColor.UNDERLINE + 
				"VERSION 3";
	}

	@Override
	public void load() {
		ProjectKorra.log.info("SPIRITS: THE COMPLETE SET BY PRRIDE LOADED!");
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilListener(), ProjectKorra.plugin);
		
		ConfigManager.getConfig().addDefault(path + "Disabled", false);
		ConfigManager.getConfig().addDefault(path + "Cooldown", 7000);
		ConfigManager.getConfig().addDefault(path + "FloatDuration", 4500);
		ConfigManager.getConfig().addDefault(path + "FloatPower", 1);
		ConfigManager.defaultConfig.save();
	}

	@Override
	public void stop() {
		ProjectKorra.log.info(getName() + " by " + getAuthor() + " " + getVersion() + " stopped!");
	}

}
