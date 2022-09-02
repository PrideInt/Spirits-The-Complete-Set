package me.Pride.korra.Spirits.dark;

import me.numin.spirits.ability.api.SpiritAbility;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.Pride.korra.Spirits.listener.AbilListener;
import me.numin.spirits.ability.api.DarkAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class Onslaught extends DarkBase {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Abilities.Dark.Onslaught.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private long cooldown;
	private double speed;
	private double damage;
	private double duration;
	private int potionDuration;
	private int potionPower;
	private int invisDuration;
	private long poisonDuration;
	
	private double time;
	
	private Vector direction;

	public Onslaught(Player player) {
		super(player);
		
		cooldown = config.getLong(path + "Cooldown");
		speed = config.getDouble(path + "Speed");
		damage = config.getDouble(path + "Damage");
		duration = config.getDouble(path + "Duration");
		poisonDuration = config.getLong(path + "EffectDuration");
		potionPower = config.getInt(path + "EffectAmplifier");
		
		direction = player.getLocation().getDirection();
		
		potionDuration = Math.toIntExact((poisonDuration * 1000) / 50);
		
		Long longDuration = (long) (duration * 1000);
		invisDuration = Math.toIntExact(longDuration / 50);
		
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1F, 0.5F);
		
		ParticleEffect.SMOKE_NORMAL.display(player.getLocation(), 10, 0.2F, 0.8F, 0.2F, 0.1F);
		
		if (player.isSneaking()) {
			start();
		}
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
		return "Onslaught";
	}

	@Override
	public boolean isSneakAbility() {
		return true;
	}

	@Override
	public void progress() {
		if (player.isSneaking()) {
			ParticleEffect.SPELL_WITCH.display(player.getLocation().add(0, 1, 0), 5, 0.2F, 0.2F, 0.2F, 1F);
			ParticleEffect.DRAGON_BREATH.display(player.getLocation().add(0, 1, 0), 5, 0F, 0F, 0F, 0.05F);
			
			onslaught();
		} else {
			if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
				player.removePotionEffect(PotionEffectType.INVISIBILITY);
			}
			bPlayer.addCooldown(this);
			remove();
			return;
		}
	}
	
	private void onslaught() {
		time += 0.05;
		if (time >= duration) {
			ParticleEffect.SMOKE_NORMAL.display(player.getLocation(), 10, 0.2F, 0.8F, 0.2F, 0.1F);
			bPlayer.addCooldown(this);
			remove();
			return;
		}
		player.setVelocity(direction.multiply(speed).normalize());
		
		ParticleEffect.SMOKE_NORMAL.display(player.getLocation(), 5, 0.2F, 0.8F, 0.2F, 0.05F);
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, invisDuration, 1));
		
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), 2)) {
			if (entity.getUniqueId() != player.getUniqueId() && entity instanceof LivingEntity) {
				
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 0.4F, 0.5F);
				
				DamageHandler.damageEntity(entity, damage, this);
				
				ParticleEffect.EXPLOSION_LARGE.display(entity.getLocation(), 1);
				
				((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, potionDuration, potionPower));
				((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.POISON, potionDuration, potionPower));
			}
		}
	}

	@Override
	public String getAbilityType() {
		return SpiritAbility.OFFENSE;
	}
}
