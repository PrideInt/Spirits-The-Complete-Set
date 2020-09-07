package me.Pride.korra.Spirits.light;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.Pride.korra.Spirits.listener.AbilListener;
import me.numin.spirits.SpiritElement;
import me.numin.spirits.ability.api.LightAbility;
import net.md_5.bungee.api.ChatColor;

public class Enlightenment extends LightAbility implements AddonAbility {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Abilities.Light.Enlightenment.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private final List<Entity> entities = new ArrayList<Entity>();
	private Map<Entity, List<Entity>> enlighteners = new HashMap<Entity, List<Entity>>();
	
	private long cooldown;
	private double radius;
	private int potionPower;
	private int potionDuration;
	private float absorptionHealth;
	private double chargeTime;
	private long effectDuration;
	private double shieldRange;
	private double damage;
	private double repel;
	private boolean enableForcefield;
	
	private double size;
	private int rotation;
	private double y;
	private double time;
	private boolean charged;
	private double counter;
	private boolean advanced;
	private int currPoint;
	private double shieldSize;
	
	Random random = new Random();

	public Enlightenment(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			return;
		}
		
		if (bPlayer.isOnCooldown(this)) {
			return;
		}
		
		cooldown = config.getLong(path + "Cooldown");
		radius = config.getDouble(path + "EnlightenRadius");
		potionPower = config.getInt(path + "EffectAmplifier");
		effectDuration = config.getLong(path + "EffectDuration");
		absorptionHealth = config.getInt(path + "AbsorptionHealth");
		chargeTime = config.getDouble(path + "ChargeTime");
		enableForcefield = config.getBoolean(path + "Forcefield.Enabled");
		shieldRange = config.getDouble(path + "Forcefield.ShieldRadius");
		damage = config.getDouble(path + "Forcefield.Damage");
		repel = config.getDouble(path + "Forcefield.Repel");
		
		y = 0;
		
		player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 0.3F, 1F);
		
		potionDuration = Math.toIntExact((effectDuration * 1000) / 50);
		
		start();
	}
	
	@Override
	public boolean isHiddenAbility() {
		return !ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Abilities.Light.Enlightenment.Enabled");
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
		return "Enlightenment";
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
		
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}
		
		if (player.isSneaking() && !advanced) {
			
			time += 0.05;
			
			if (time >= chargeTime) {
				charged = true;
				
				animate();
			}
			
			if (size >= radius) {
				size = 0;
			}
			
			if (size == 0) {
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
			}
			
			for (Entity e : this.entities) {
				if (enlighteners.containsKey(e)) {
					if (e.getLocation().distance(player.getLocation()) > radius) {
						enlighteners.remove(e);
					}
				}
			}
			
			size += 0.1;
			
			makeCircle(player.getLocation(), size);
			
			for (Entity entity : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), radius)) {
				if (entity.getLocation().distance(player.getLocation()) < radius) {
					if (entity.getUniqueId() != player.getUniqueId() && entity instanceof LivingEntity) {
						
						if (entity instanceof Player) {
			                Player ePlayer = (Player) entity;
			                BendingPlayer bEntity = BendingPlayer.getBendingPlayer(ePlayer);
			                
			                Element lightSpirit = SpiritElement.LIGHT_SPIRIT;
			                Element darkSpirit = SpiritElement.LIGHT_SPIRIT;
			                Element spirit = SpiritElement.SPIRIT;
			                
			                if (bEntity.hasElement(lightSpirit) || bEntity.hasElement(spirit) && !bEntity.hasElement(darkSpirit)) {
			                	makeCircle(entity.getLocation(), size);
			                	
			                	entities.add(entity);
			                	enlighteners.put(entity, entities);
			                }
						}
						
						if (entity instanceof Sheep) {
							makeCircle(entity.getLocation(), size);
							
		                	entities.add(entity);
		                	enlighteners.put(entity, entities);
						}
					}
				} else {
					for (Entity e : this.entities) {
						if (enlighteners.containsKey(e)) {
							enlighteners.remove(e);
						}
					}
				}
			}
		}
		
		if (!player.isSneaking() || advanced) {
			
			if (charged) {
				
				advanced = true;
				
				if (enableForcefield) {
					shieldSize += 0.325;
					
					if (shieldSize <= shieldRange) {
						shieldBurst(player.getLocation(), shieldSize);
					}
				}
				
				counter += 0.05;
				if (counter <= 0.08) {
					enlighten();
					
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1F, 0.5F);
					
					ParticleEffect.BLOCK_CRACK.display(player.getLocation().add(0, 2, 0), 3, 0.2F, 0.2F, 0.2F, 0F, Material.GLOWSTONE.createBlockData());
					player.getWorld().playSound(player.getLocation().add(0, 2, 0), Sound.BLOCK_GLASS_BREAK, 1F, 1F);
				}
				
				bPlayer.addCooldown(this);
				
				if (counter >= effectDuration) {
					remove();
					return;
				}
			} else {
				remove();
				return;
			}
		}
	}
	
	private void shieldBurst(Location location, double size) {
		Location l = location.clone();
        double r = size;
        for(double phi = 0; phi <= Math.PI; phi += Math.PI / 15) {
            double y = r * Math.cos(phi) + size;
            for(double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 30) {
                double x = r * Math.cos(theta) * Math.sin(phi);
                double z = r * Math.sin(theta) * Math.sin(phi);

                l.add(x, y, z);
    	        
    	        if (this.random.nextInt(10) == 0) {
    	        	ParticleEffect.SPELL_MOB_AMBIENT.display(l, 1, 0.0F, 0.0F, 0.0F, 0.0F);
    	        }
    	        
    	        for (Entity entity : GeneralMethods.getEntitiesAroundPoint(l, 1.85)) {
					if (GeneralMethods.locationEqualsIgnoreDirection(l, entity.getLocation())) {
						continue;
					}
					if (GeneralMethods.isRegionProtectedFromBuild(this, entity.getLocation())) {
						continue;
					}
					
					if (entity.getUniqueId() != player.getUniqueId() && entity instanceof LivingEntity) {
						if (entity instanceof Player) {
			                Player ePlayer = (Player) entity;
			                BendingPlayer bEntity = BendingPlayer.getBendingPlayer(ePlayer);
			                
			                Element lightSpirit = SpiritElement.LIGHT_SPIRIT;
			                Element darkSpirit = SpiritElement.LIGHT_SPIRIT;
			                Element spirit = SpiritElement.SPIRIT;
			                
			                if (bEntity.hasElement(lightSpirit) || bEntity.hasElement(spirit) && !bEntity.hasElement(darkSpirit)) {
			                	Vector direction = GeneralMethods.getDirection(l, entity.getLocation());
		    		    		direction.setY(0.5);
		    		    		
		    		    		entity.setVelocity(entity.getVelocity().normalize().add(direction.clone().normalize().multiply(repel)));
		    		    		
			                } else if (bEntity.hasElement(darkSpirit)) {
			                	Vector direction = GeneralMethods.getDirection(l, entity.getLocation());
		    		    		direction.setY(0.5);
			                	
			                	DamageHandler.damageEntity(entity, damage, this);
		    		    		
		    		    		entity.setVelocity(entity.getVelocity().normalize().add(direction.clone().normalize().multiply(repel)));
			                }
						}
    	        	}
    	        }
    	        
                l.subtract(x, y, z);
                
            }
        }
	}
	
	private void makeCircle(Location location, double size) {
		rotation++;
		for (int i = -180; i < 180; i += 20) {
	        double angle = i * 3.141592653589793D / 180.0D;
	        double x = size * Math.cos(angle + rotation);
	        double z = size * Math.sin(angle + rotation);
	        Location loc = location.clone();
	        loc.add(x, 0, z);
	        ParticleEffect.CRIT_MAGIC.display(loc, 1, 0F, 0F, 0F, 0F);
    	}
	}
	
	private void animate() {
		y += 0.05;
		
		if (y >= 1.25) {
			y = 0;
		}
		
		for (int i = 0; i < 0.25; i++) {
			
			currPoint += 360 / 60;
			if (currPoint > 360) {
				currPoint = 0;
			}
			Location baseLoc = player.getLocation().clone();
			double angle = currPoint * 3.141592653589793D / 180.0D;
			double x = 1.15F * Math.cos(angle);
			double z = 1.15F * Math.sin(angle);
			Location loc = baseLoc.add(x, y, z);
			Location negLoc = baseLoc.add(-x, y, -z);
			
			ParticleEffect.SPELL_INSTANT.display(loc, 1, 0F, 0F, 0F, 0.025F);
			ParticleEffect.SPELL_INSTANT.display(negLoc, 1, 0F, 0F, 0F, 0.025F);
		}
	}
	
	private void enlighten() {
		if (containsEntities()) {
			addEffects(player, potionDuration, potionPower);
			
			GeneralMethods.setAbsorbationHealth(player, absorptionHealth);
			
			for (Entity e : enlighteners.keySet()) {
				addEffects((LivingEntity) e, potionDuration, potionPower - 2);
			}
		} else {
			addEffects(player, potionDuration, potionPower);
			
			GeneralMethods.setAbsorbationHealth(player, absorptionHealth);
		}
	}
	
	private void addEffects(LivingEntity entity, int potionDuration, int potionPower) {
		entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, potionDuration, potionPower));
		entity.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, potionDuration, potionPower));
		entity.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, potionDuration, potionPower));
		entity.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, potionDuration, potionPower));
		entity.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, potionDuration, potionPower));
	}
	
	private boolean containsEntities() {
		if (enlighteners.size() == 1) {
			final int potDuration = potionDuration + 50;
			final int potPower = potionPower + 1;
			final float absHealth = absorptionHealth * 2;
			
			potionDuration = potDuration;
			potionPower = potPower;
			absorptionHealth = absHealth;
			
			return true;
			
		} else if (enlighteners.size() >= 2) {
			final int potDuration = potionDuration + 100;
			final int potPower = potionPower + 1;
			final float absHealth = absorptionHealth * 2;
			
			potionDuration = potDuration;
			potionPower = potPower;
			absorptionHealth = absHealth;
			
			return true;
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		return SpiritElement.DARK_SPIRIT.getColor() + "Enlightenment allows the user to gain buffs and positive effects "
				+ "through the use of spiritual knowledge! With the help of other spirits and light spirits, buffs are "
				+ "more stronger and effective and you are able to share your enlightenment! After gaining enlightenment, your light "
				+ "attacks become stronger and you produce a temporary forcefield to ward out dark spirits.";
	}
	
	@Override
	public String getInstructions() {
		return ChatColor.GOLD + "To use, hold sneak until a certain time and release. If close to other spirits or light spirits, "
				+ "your buffs increase and you are able to enlighten them as well.";
	}

	@Override
	public String getAuthor() {
		return SpiritElement.DARK_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"Prride";
	}

	@Override
	public String getVersion() {
		return SpiritElement.DARK_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"VERSION 4";
	}

	@Override
	public void load() {
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilListener(), ProjectKorra.plugin);
		
		ConfigManager.getConfig().addDefault(path + "Enabled", true);
		ConfigManager.getConfig().addDefault(path + "Cooldown", 10000);
		ConfigManager.getConfig().addDefault(path + "ChargeTime", 4.5);
		ConfigManager.getConfig().addDefault(path + "EnlightenRadius", 1.5);
		ConfigManager.getConfig().addDefault(path + "EffectAmplifier", 3);
		ConfigManager.getConfig().addDefault(path + "EffectDuration", 12);
		ConfigManager.getConfig().addDefault(path + "AbsorptionHealth", 3);
		ConfigManager.getConfig().addDefault(path + "Forcefield.Enabled", true);
		ConfigManager.getConfig().addDefault(path + "Forcefield.ShieldRadius", 3.5);
		ConfigManager.getConfig().addDefault(path + "Forcefield.Damage", 3);
		ConfigManager.getConfig().addDefault(path + "Forcefield.Repel", 0.4);
		
		ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Enlightenment.DeathMessage", "{victim} was repelled by {attacker}'s {ability} shield!");
		
		ConfigManager.defaultConfig.save();
	}

	@Override
	public void stop() {
		ProjectKorra.log.info(getName() + " by " + getAuthor() + " " + getVersion() + " stopped!");
	}

}
