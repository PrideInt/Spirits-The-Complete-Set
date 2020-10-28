package me.Pride.korra.Spirits.combos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class Sanctuary extends LightAbility implements AddonAbility, ComboAbility {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Combos.Light.Sanctuary.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private PotionEffectType[] negativeEffects = new PotionEffectType[] {
			PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.POISON,
			PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING, PotionEffectType.WEAKNESS,
			PotionEffectType.UNLUCK, PotionEffectType.WITHER};
	
	private long cooldown;
	private double radius;
	private long duration;
	private double damage;
	private double repel;
	private int resistanceDuration;
	private int resistancePower;
	private long effectDuration;
	
	private double size;
	private int rotation;
	private long time;
	
	private Location origin;
	private Location location;
	private Vector direction;
	Random rand = new Random();

	private Set<Entity> repelled = new HashSet<>();

	public Sanctuary(Player player) {
		super(player);

		if (!bPlayer.canBendIgnoreBinds(this)) {
			return;
		}

		if (GeneralMethods.isRegionProtectedFromBuild(this, player.getLocation())) {
			return;
		}
		
		cooldown = config.getLong(path + "Cooldown");
		radius = config.getDouble(path + "Radius");
		duration = config.getLong(path + "Duration");
		effectDuration = config.getLong(path + "EffectDuration");
		resistancePower = config.getInt(path + "EffectAmplifier");
		damage = config.getDouble(path + "Damage");
		repel = config.getDouble(path + "Repel");
		
		time = System.currentTimeMillis();
		origin = player.getLocation();
		
		resistanceDuration = Math.toIntExact((effectDuration * 1000) / 50);
		
		bPlayer.addCooldown(this);
		
		boolean enabled = ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Combos.Light.Sanctuary.Enabled");
		
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
		return origin;
	}

	@Override
	public String getName() {
		return "Sanctuary";
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
		if (!player.isOnline() || player.isDead()) {
			remove();
			return;
		}
		if (System.currentTimeMillis() > time + duration) {
			remove();
			return;
		} else {
			sanctuary();
		}
	}
	
	private void sanctuary() {
		rotation++;
		if (size >= radius) {
			size = 0;
			player.getWorld().playSound(origin, Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 1F, 1F);
			if (rand.nextInt(2) == 0) {
				player.getWorld().playSound(location, Sound.BLOCK_PORTAL_AMBIENT, 1F, 1F);
			}
			repelled.clear();
		} else {
			size += 0.3;
			for (int i = -180; i < 180; i += 90) {
		        double angle = i * 3.141592653589793D / 180.0D;
		        double x = size * Math.cos(angle + rotation);
		        double z = size * Math.sin(angle + rotation);
		        Location loc = origin.clone();
		        loc.add(x, 0, z);
		        ParticleEffect.SPELL_INSTANT.display(loc, 3, 0F, 0F, 0F, 0F);
		        ParticleEffect.ENCHANTMENT_TABLE.display(loc, 3);
	    	}
			expandBarriers(size);
		}		
	}
	
	private void expandBarriers(double size) {
		for (int i = -180; i < 180; i += 20) {
			// layer 1
	        double angle = i * 3.141592653589793D / 180.0D;
	        double x = size * Math.cos(angle + rotation);
	        double z = size * Math.sin(angle + rotation);
	        Location loc = origin.clone();
	        loc.add(x, 1, z);
	        ParticleEffect.CRIT_MAGIC.display(loc, 3, 0.1F, 0.1F, 0.1F, 0F);
	        
	        // layer 2
	        double angle1 = i * 3.141592653589793D / 180.0D;
	        double x1 = size * Math.cos(angle1 + rotation);
	        double z1 = size * Math.sin(angle1 + rotation);
	        Location loc1 = origin.clone();
	        loc1.add(x1, 2, z1);
	        ParticleEffect.CRIT_MAGIC.display(loc1, 3, 0.1F, 0.1F, 0.1F, 0F);
	        
	        // layer 3
	        double angle2 = i * 3.141592653589793D / 180.0D;
	        double x2 = size * Math.cos(angle2 + rotation);
	        double z2 = size * Math.sin(angle2 + rotation);
	        Location loc2 = origin.clone();
	        loc2.add(x2, 3, z2);
	        ParticleEffect.CRIT_MAGIC.display(loc2, 3, 0.1F, 0.1F, 0.1F, 0F);
	        
	        Location locations[] = { loc, loc1, loc2 };
	        for (Location location : locations) {
	        	for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 2.5)) {
					if (GeneralMethods.locationEqualsIgnoreDirection(location, entity.getLocation())) {
						continue;
					}
					if (GeneralMethods.isRegionProtectedFromBuild(this, entity.getLocation())) {
						continue;
					}
	        		direction = GeneralMethods.getDirection(location, entity.getLocation());
					if(GeneralMethods.locationEqualsIgnoreDirection(location, entity.getLocation())){
						continue;
					}
		    		direction.setY(0.5);
		    		this.location = location;
		    		if (entity instanceof LivingEntity) {
						// modified snippet from Numin's Rejuvenate ability
						if (entity instanceof Player && entity.getEntityId() != player.getEntityId()) {
			                Player ePlayer = (Player) entity;
			                BendingPlayer bEntity = BendingPlayer.getBendingPlayer(ePlayer);
			                
			                Element lightSpirit = SpiritElement.LIGHT_SPIRIT;
			                Element darkSpirit = SpiritElement.DARK_SPIRIT;
			                
			                if (entity.getUniqueId() != player.getUniqueId()) {
		                		if (!bEntity.hasElement(lightSpirit)) {
		                			repel(ePlayer);
		                			repel(entity);
		                		}
							}
			                
			                if (bEntity.hasElement(lightSpirit)) {
			                	((LivingEntity) entity).addPotionEffect(new PotionEffect(
										PotionEffectType.DAMAGE_RESISTANCE, resistanceDuration, resistancePower));
								
								for (PotionEffectType negativeEffects : this.negativeEffects) {
									((LivingEntity) entity).removePotionEffect(negativeEffects);
								}
								continue;
			                }
			                if (bEntity.hasElement(darkSpirit)) {
			                    DamageHandler.damageEntity(entity, damage, this);
			                }
			            } else if (entity instanceof Monster) {
			                DamageHandler.damageEntity(entity, damage, this);
			                repel(entity);

			            }
					}
				}
	        }
    	}
	}
	
	private void repel(Entity entity) {
		if(!repelled.contains(entity)) {
			GeneralMethods.setVelocity(entity, entity.getVelocity().add(direction.clone().normalize().multiply(repel)));
			repelled.add(entity);
		}
	}
	
	@Override
	public String getDescription() {
		return SpiritElement.DARK_SPIRIT.getColor() + "" + ChatColor.BOLD + "EXPECTO PATRONUM!!" +
				SpiritElement.DARK_SPIRIT.getColor()
				+ "\nLight spirits are able to generate a protective "
				+ "barrier that expands from their location and repel enemies away while boosting their "
				+ "light spirit allies with damage protection!";
	}
	
	@Override
	public String getInstructions() {
		return ChatColor.GOLD + "Alleviate (Tap sneak) > Alleviate (Hold sneak) > Shelter (Release sneak)";
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
		ConfigManager.getConfig().addDefault(path + "Enabled", true);
		ConfigManager.getConfig().addDefault(path + "Cooldown", 18000);
		ConfigManager.getConfig().addDefault(path + "Duration", 9500);
		ConfigManager.getConfig().addDefault(path + "Radius", 8);
		ConfigManager.getConfig().addDefault(path + "Repel", 0.3);
		ConfigManager.getConfig().addDefault(path + "Damage", 3);
		ConfigManager.getConfig().addDefault(path + "EffectDuration", 2.5);
		ConfigManager.getConfig().addDefault(path + "EffectAmplifier", 1);
		ConfigManager.defaultConfig.save();
	}

	@Override
	public void stop() {
		ProjectKorra.log.info(getName() + " by " + getAuthor() + " " + getVersion() + " stopped!");
		super.remove();
	}

	@Override
	public Object createNewComboInstance(Player player) {
		return new Sanctuary(player);
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		ArrayList<AbilityInformation> combo = new ArrayList<>();
		combo.add(new AbilityInformation("Alleviate", ClickType.SHIFT_DOWN));
		combo.add(new AbilityInformation("Alleviate", ClickType.SHIFT_UP));
		combo.add(new AbilityInformation("Alleviate", ClickType.SHIFT_DOWN));
		combo.add(new AbilityInformation("Shelter", ClickType.SHIFT_UP));
		return combo;
	}

}
