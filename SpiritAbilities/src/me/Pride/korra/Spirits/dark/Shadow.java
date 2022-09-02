package me.Pride.korra.Spirits.dark;

import me.numin.spirits.ability.api.SpiritAbility;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.util.Collision;
import com.projectkorra.projectkorra.airbending.AirBlast;
import com.projectkorra.projectkorra.airbending.AirSwipe;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.earthbending.EarthBlast;
import com.projectkorra.projectkorra.firebending.FireBlast;
import com.projectkorra.projectkorra.firebending.combustion.Combustion;
import com.projectkorra.projectkorra.firebending.lightning.Lightning;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.projectkorra.waterbending.Torrent;
import com.projectkorra.projectkorra.waterbending.WaterManipulation;

import me.Pride.korra.Spirits.listener.AbilListener;
import me.numin.spirits.ability.api.DarkAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class Shadow extends DarkBase {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Abilities.Dark.Shadow.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private long cooldown;
	private long time;
	private long duration;
	private double collisionRadius;
	private double range;
	
	public boolean isShadow;
	
	private Location location;
	
	public Shadow(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			return;
		}
		
		cooldown = config.getLong(path + "Cooldown");
		duration = config.getLong(path + "Duration");
		collisionRadius = config.getDouble(path + "CollisionRadius");
		range = config.getDouble(path + "TeleportRange");
		
		time = System.currentTimeMillis();
		
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
		return "Shadow";
	}

	@Override
	public boolean isSneakAbility() {
		return true;
	}

	@Override
	public void progress() {
		if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
			remove();
			return;
		}
		if (player.isSneaking()) {
			if (System.currentTimeMillis() > time + duration) {
				bPlayer.addCooldown(this);
				remove();
				return;
			} else {
				isShadow = true;
				
				collision();
				
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1F, 0.5F);
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 90));
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 2));
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 5));
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 5));
				
				ParticleEffect.SMOKE_NORMAL.display(player.getLocation(), 20, 0.2F, 0.8F, 0.2F, 0.05F);
			}
		} else {
			if (isShadow) {
				location = GeneralMethods.getTargetedLocation(player, range);
				player.teleport(location);
				ParticleEffect.SMOKE_NORMAL.display(player.getLocation(), 20, 0.2F, 0.8F, 0.2F, 0.1F);
				bPlayer.addCooldown(this);
				remove();
				return;
			} else {
				bPlayer.addCooldown(this);
				remove();
				return;
			}
		}
	}
	
	private void collision() {
		//Main Ability
		CoreAbility mainAbil = CoreAbility.getAbility(Shadow.class);
		
		// Small abilities
		CoreAbility fireBlast = CoreAbility.getAbility(FireBlast.class);
		CoreAbility earthBlast = CoreAbility.getAbility(EarthBlast.class);
		CoreAbility waterManip = CoreAbility.getAbility(WaterManipulation.class);
		CoreAbility torrent = CoreAbility.getAbility(Torrent.class);
		CoreAbility airSwipe = CoreAbility.getAbility(AirSwipe.class);
		CoreAbility airBlast = CoreAbility.getAbility(AirBlast.class);
		CoreAbility combustion = CoreAbility.getAbility(Combustion.class);
		CoreAbility lightning = CoreAbility.getAbility(Lightning.class);
		
		CoreAbility[] smallAbilities = { airSwipe, earthBlast, waterManip, torrent, fireBlast, airBlast, 
				combustion, lightning };
		
		for (CoreAbility smallAbil : smallAbilities) {
			ProjectKorra.getCollisionManager().addCollision(new Collision(mainAbil, smallAbil, true, true));
		}
	}
	
	@Override
	public void remove() {
		super.remove();
		
		if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
		
		if (player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
			player.removePotionEffect(PotionEffectType.BLINDNESS);
		}
		
		if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		}
		
		if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
			player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
		}
		
		ParticleEffect.SMOKE_LARGE.display(player.getLocation(), 5, 0.2F, 0.8F, 0.2F, 0.08F);
	}
	
	@Override
	public double getCollisionRadius() {
		return collisionRadius;
	}
	
	@Override
	public void handleCollision(Collision collision) {
		super.handleCollision(collision);
		
		if (collision.isRemovingFirst()) {
			ParticleEffect.SMOKE_NORMAL.display(player.getLocation(), 5, 0.2F, 0.8F, 0.2F, 0.08F);
			bPlayer.addCooldown(this);
			remove();
		}
		if (collision.isRemovingSecond()) {
			ParticleEffect.SMOKE_NORMAL.display(player.getLocation(), 5, 0.2F, 0.8F, 0.2F, 0.08F);
			bPlayer.addCooldown(this);
			remove();
		}
	}

	@Override
	public String getAbilityType() {
		return SpiritAbility.UTILITY;
	}
}
