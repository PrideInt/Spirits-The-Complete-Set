package me.Pride.korra.Spirits.combos;

import java.util.ArrayList;
import java.util.Random;

import com.projectkorra.projectkorra.command.Commands;
import me.Pride.korra.Spirits.dark.DarkBase;
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
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.ability.api.DarkAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class Pandemonium extends DarkBase implements ComboAbility {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Combos.Dark.Pandemonium.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private long cooldown;
	private double radius;
	private double pull;
	private long duration;
	private int slowPower;
	private int slowDuration;
	private long effectDuration;
	
	private long time;
	private double size;
	private int rotation;
	
	private Location origin;
	private Location location;
	private Vector pullDirection;
	Random rand = new Random();

	public Pandemonium(Player player) {
		super(player);

		if (!bPlayer.canBendIgnoreBinds(this)) {
			return;
		}

		if (GeneralMethods.isRegionProtectedFromBuild(this, player.getLocation())) {
			return;
		}
		
		cooldown = config.getLong(path + "Cooldown");
		radius = config.getDouble(path + "Radius");
		pull = config.getDouble(path + "Pull");
		duration = config.getLong(path + "Duration");
		slowPower = config.getInt(path + "EffectAmplifier");
		effectDuration = config.getLong(path + "EffectDuration");
		
		slowDuration = Math.toIntExact((effectDuration * 1000) / 50);
		time = System.currentTimeMillis();
		origin = player.getLocation();
		size = radius;
		
		player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 2F, 0.5F);
		
		boolean enabled = ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Combos.Dark.Pandemonium.Enabled");
		
		if (enabled) {
			start();
		}
	}

	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public String getName() {
		return "Pandemonium";
	}

	@Override
	public boolean isSneakAbility() {
		return true;
	}

	@Override
	public void progress() {
		if (!player.isOnline() || player.isDead()) {
			remove();
			return;
		}
		if (System.currentTimeMillis() > time + duration) {
			remove();
			return;
		} else {
			pandemonium();
			bPlayer.addCooldown(this);
		}
	}
	
	private void pandemonium() {
		rotation++;
		
		if (rotation % 3 == 0) {
			for (Entity entity : GeneralMethods.getEntitiesAroundPoint(origin, radius)) {
				if (entity.getUniqueId() != player.getUniqueId() && entity instanceof LivingEntity) {
					if (GeneralMethods.isRegionProtectedFromBuild(this, entity.getLocation()) || ((entity instanceof Player) && Commands.invincible.contains(((Player) entity).getName()))) {
						continue;
					}
					if (GeneralMethods.locationEqualsIgnoreDirection(origin, entity.getLocation())) {
						continue;
					}
					pullDirection = GeneralMethods.getDirection(entity.getLocation(), origin);
					entity.setVelocity(pullDirection.multiply(pull));
					
					((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, slowDuration, slowPower));
				}
			}
		}
		
		if (size <= 0) {
			size = radius;
			
			player.getWorld().playSound(origin, Sound.BLOCK_BEACON_AMBIENT, 2F, 0.5F);
			
			if (rand.nextInt(2) == 0) {
				player.getWorld().playSound(location, Sound.BLOCK_PORTAL_TRAVEL, 1F, 1F);
			}
		} else {
			size -= 0.3;
			for (int i = -180; i < 180; i += 90) {
		        double angle = i * 3.141592653589793D / 180.0D;
		        double x = size * Math.cos(angle + rotation);
		        double z = size * Math.sin(angle + rotation);
		        Location loc = origin.clone();
		        loc.add(x, 0, z);
		        ParticleEffect.SMOKE_NORMAL.display(loc, 3, 0.2F, 0.2F, 0.2F, 0F);
		    
	    	}
			
			for (int j = -180; j < 180; j += 20) {
	        	double angle = j * 3.141592653589793D / 180.0D;
	        	double x = size * Math.cos(angle + rotation);
	        	double z = size * Math.sin(angle + rotation);
	        	Location loc = origin.clone();
	        	loc.add(x, 0, z);
	        	ParticleEffect.SPELL_WITCH.display(loc, 3, 0.1F, 0.1F, 0.1F, 0.1F);
	        	
	        	for (Entity entity : GeneralMethods.getEntitiesAroundPoint(loc, 1.5)) {
					if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId()) {
						if (GeneralMethods.isRegionProtectedFromBuild(this, entity.getLocation()) || ((entity instanceof Player) && Commands.invincible.contains(((Player) entity).getName()))) {
							continue;
						}
						ParticleEffect.SPELL_WITCH.display(entity.getLocation(), 1, 0.3F, 0.3F, 0.3F, 0.05F);
					}
				}
	        }
		}
	}

	@Override
	public Object createNewComboInstance(Player player) {
		return new Pandemonium(player);
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		ArrayList<AbilityInformation> combo = new ArrayList<>();
		combo.add(new AbilityInformation("Intoxicate", ClickType.SHIFT_DOWN));
		combo.add(new AbilityInformation("Intoxicate", ClickType.SHIFT_UP));
		combo.add(new AbilityInformation("Intoxicate", ClickType.SHIFT_DOWN));
		combo.add(new AbilityInformation("Shackle", ClickType.SHIFT_UP));
		return combo;
	}

	@Override
	public String getAbilityType() {
		return UTILITY;
	}
}
