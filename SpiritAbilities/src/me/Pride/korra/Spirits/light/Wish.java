package me.Pride.korra.Spirits.light;

import me.numin.spirits.ability.api.SpiritAbility;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.Pride.korra.Spirits.listener.AbilListener;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class Wish extends LightBase {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Abilities.Light.Wish.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private long cooldown;
	private long chargeTime;
	private long waitDuration;
	private double healAmount;
	
	private boolean charged;
	private boolean wished;
	private long time;
	private double tickTime;

	public Wish(Player player) {
		super(player);

		if (bPlayer.isOnCooldown(this)) {
			return;
		}
		
		cooldown = config.getLong(path + "Cooldown");
		chargeTime = config.getLong(path + "ChargeTime");
		waitDuration = config.getLong(path + "WaitDuration");
		healAmount = config.getDouble(path + "HealAmount");
		
		time = System.currentTimeMillis();
		
		player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_AMBIENT, 0.5F, 1F);
		
		start();
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
		return "Wish";
	}

	@Override
	public boolean isSneakAbility() {
		return true;
	}

	@Override
	public void progress() {
		if (player.isSneaking()) {
			if (System.currentTimeMillis() > time + chargeTime) {
				if (!wished) {
					charged = true;
					ParticleEffect.SPELL_INSTANT.display(player.getLocation(), 5, 0.2F, 0.2F, 0.2F, 0.1F);
				}
			} else {
				ParticleEffect.ENCHANTMENT_TABLE.display(player.getLocation().add(0, 1, 0), 5, 0.5F, 0.5F, 0.5F, 0.2F);
			}
		} else {
			if (charged) {
				bPlayer.addCooldown(this);
				wish();
			} else {
				remove();
				return;
			}
		}
	}
	
	private void wish() {
		wished = true;
		tickTime += 0.05;
		long time = (long) (tickTime * 1000);
		if (time >= waitDuration) {
			double health = player.getHealth() + healAmount;
			if (health > player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
	            health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
	        }
			player.setHealth(health);
			
			for (int i = 0; i < 10 ; i++) {
				ParticleEffect.FIREWORKS_SPARK.display(player.getLocation().add(0, 1, 0), 5, 0.3F, 0.3F, 0.3F, 0.2F);
				ParticleEffect.ENCHANTMENT_TABLE.display(player.getLocation().add(0, 1, 0), 5, 0.3F, 0.3F, 0.3F, 0.2F);
			}
			
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1.2F);
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1F, 1F);
			
			player.sendMessage(SpiritElement.LIGHT.getColor() + "Your wish came true!");
			
			remove();
			return;
		} else if (tickTime <= 0.05) {
			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 0.3F, 1F);
			
			for (int i = 0; i < 8; i++) {
				player.getWorld().spawnEntity(player.getLocation().add(0, 2, 0), EntityType.FIREWORK);
			}
			
			player.sendMessage(SpiritElement.LIGHT.getColor() + "You made a wish!");
		}
	}

	@Override
	public String getAbilityType() {
		return SpiritAbility.UTILITY;
	}
}
