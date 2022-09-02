package me.Pride.korra.Spirits.passives;

import me.Pride.korra.Spirits.light.LightBase;
import me.numin.spirits.ability.api.SpiritAbility;
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

public class WishfulThinking extends LightBase implements PassiveAbility {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Passives.Light.WishfulThinking.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private int regenPower;
	private int regenDuration;
	private long effectDuration;

	public WishfulThinking(Player player) {
		super(player);
		
		if (!bPlayer.hasElement(SpiritElement.LIGHT)) {
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
	public String getAbilityType() {
		return SpiritAbility.PASSIVE;
	}
}
