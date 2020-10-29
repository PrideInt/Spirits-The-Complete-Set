package me.Pride.korra.Spirits.light;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.Pride.korra.Spirits.listener.AbilListener;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class Safeguard extends LightAbility implements AddonAbility {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Abilities.Light.Safeguard.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private long cooldown;
	private long duration;
	
	private int rotation;
	private double y;
	private long time;
	
	private PotionEffectType[] negativeEffects = new PotionEffectType[] {
			PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.POISON,
			PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING, PotionEffectType.WEAKNESS,
			PotionEffectType.UNLUCK, PotionEffectType.WITHER };

	public Safeguard(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			return;
		}
		
		cooldown = config.getLong(path + "Cooldown");
		duration = config.getLong(path + "Duration");
		
		y = 2.5;
		
		time = System.currentTimeMillis();
		
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 1F, 1F);
		
		start();
	}
	
	@Override
	public boolean isHiddenAbility() {
		return ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Abilities.Light.Safeguard.Disabled");
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
		return "Safeguard";
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
		return true;
	}

	@Override
	public void progress() {
		if (System.currentTimeMillis() > time + duration) {
			bPlayer.addCooldown(this);
			remove();
			return;
			
		} else {
			
			animate(player);
			
			ParticleEffect.SPELL_INSTANT.display(player.getLocation().add(0, 1, 0), 3, 0F, 0F, 0F, 0.1F);
			for (PotionEffectType nEffects : negativeEffects) {
				if (player.hasPotionEffect(nEffects)) {
					player.removePotionEffect(nEffects);
				}
			}
			player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
		}
	}
	
	private void animate(Player player) {
		rotation++;
		
		y -= 0.15;
		
		if (y <= 0) {
			return;
		}
		
		for (int i = -180; i < 180; i += 20) {
			Location playerLoc = player.getLocation();
			
	        double angle = i * 3.141592653589793D / 180.0D;
	        double x = 0.9 * Math.cos(angle + rotation);
	        double z = 0.9 * Math.sin(angle + rotation);
	        
	        Location loc = playerLoc.clone();
	        
	        loc.add(x, y, z);
	        ParticleEffect.CRIT_MAGIC.display(loc, 1);
    	}
	}
	
	@Override
	public void remove() {
		super.remove();
		
		if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
			player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
		}
	}

	@Override
	public String getDescription() {
		return SpiritElement.DARK_SPIRIT.getColor() + "By creating a shield of positive light energy around themselves, "
				+ "Light spirits are able to protect themselves from negative status effects with positive status effects.";
	}
	
	@Override
	public String getInstructions() {
		return ChatColor.GOLD + "To use, tap sneak.";
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
		
		ConfigManager.getConfig().addDefault(path + "Disabled", false);
		ConfigManager.getConfig().addDefault(path + "Cooldown", 14000);
		ConfigManager.getConfig().addDefault(path + "Duration", 10000);
		ConfigManager.defaultConfig.save();
	}

	@Override
	public void stop() {
		ProjectKorra.log.info(getName() + " by " + getAuthor() + " " + getVersion() + " stopped!");
	}

}
