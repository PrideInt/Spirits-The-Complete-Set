package me.Pride.korra.Spirits.passives;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.PassiveAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.Pride.korra.Spirits.listener.AbilListener;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class Afterglow extends LightAbility implements AddonAbility, PassiveAbility {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Passives.Light.Afterglow.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private long cooldown;
	private double damage;
	private double healAmount;
	private long duration;
	
	public Location location;
	
	private long time;

	public Afterglow(Player player, Location location) {
		super(player);
		
		if (!bPlayer.hasElement(SpiritElement.LIGHT_SPIRIT)) {
			return;
		}
		
		time = System.currentTimeMillis();
		cooldown = config.getLong(path + "Cooldown");
		duration = config.getLong(path + "Duration");
		damage = config.getDouble(path + "Damage");
		healAmount = config.getDouble(path + "HealAmount");
		
		this.location = location;
		
		boolean enabled = ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Passives.Light.Afterglow.Enabled");
		
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
		return "Afterglow";
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
		if (System.currentTimeMillis() > time + duration) {
			bPlayer.addCooldown(this);
			remove();
			return;
		} else {
			ParticleEffect.ENCHANTMENT_TABLE.display(location, 1, 1F, 1F, 1F, 0F);
			ParticleEffect.SPELL_INSTANT.display(location, 1);
			
			for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 1)) {
				if (entity instanceof LivingEntity) {
					// modified snippet from Numin's Rejuvenate ability
					if (entity instanceof Player && entity.getEntityId() != player.getEntityId()) {
		                Player ePlayer = (Player) entity;
		                BendingPlayer bEntity = BendingPlayer.getBendingPlayer(ePlayer);
		                
		                Element lightSpirit = SpiritElement.LIGHT_SPIRIT;
		                Element darkSpirit = SpiritElement.DARK_SPIRIT;
		                
		                if (bEntity.hasElement(lightSpirit) && entity.getUniqueId() != player.getUniqueId()) {
	        				
		                	double health = ePlayer.getHealth() + (healAmount / 13);
		        			if (health > ePlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
		        	            health = ePlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		        	        }
		        			ePlayer.setHealth(health);
		        			
	        				ParticleEffect.HEART.display(ePlayer.getLocation().add(0, 2, 0), 1);
	        				bPlayer.addCooldown(this);
	        				remove();
	        				return;
		                }
		                if (bEntity.hasElement(darkSpirit)) {
		                	ParticleEffect.FIREWORKS_SPARK.display(location, 3, 0.2F, 0.2F, 0.2F, 0.8F);
		                    DamageHandler.damageEntity(entity, damage, this);
		                    bPlayer.addCooldown(this);
	        				remove();
	        				return;
		                }
		            } else if (entity instanceof Monster) {
		            	ParticleEffect.FIREWORKS_SPARK.display(location, 3, 0.2F, 0.2F, 0.2F, 0.8F);
		                DamageHandler.damageEntity(entity, damage, this);
		                bPlayer.addCooldown(this);
	    				remove();
	    				return;

		            }
				}
			}
		}
		return;
	}
	
	@Override
	public String getDescription() {
		return SpiritElement.DARK_SPIRIT.getColor() + "When Light spirits perish, they leave behind a piece of residual energy containing "
				+ "light. Any fellow Light spirits touching and absorbing this afterglow will gain back health and energy while "
				+ "any Dark spirit or creature will be hurt.";
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
	public boolean isInstantiable() {
		return false;
	}

	@Override
	public boolean isProgressable() {
		return true;
	}

	@Override
	public void load() {
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilListener(), ProjectKorra.plugin);
		
		ConfigManager.getConfig().addDefault(path + "Enabled", true);
		ConfigManager.getConfig().addDefault(path + "Cooldown", 8000);
		ConfigManager.getConfig().addDefault(path + "HealAmount", 3);
		ConfigManager.getConfig().addDefault(path + "Damage", 4);
		ConfigManager.getConfig().addDefault(path + "Duration", 20000);
		ConfigManager.defaultConfig.save();
	}

	@Override
	public void stop() {
		super.remove();
		ProjectKorra.log.info(getName() + " by " + getAuthor() + " " + getVersion() + " stopped!");
	}

}
