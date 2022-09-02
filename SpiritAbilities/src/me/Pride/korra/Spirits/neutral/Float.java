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

public class Float extends NeutralBase {
	
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
	public String getAbilityType() {
		return SpiritAbility.UTILITY;
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
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {
		Element lightSpirit = SpiritElement.LIGHT;
		Element darkSpirit = SpiritElement.DARK;
		
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
}
