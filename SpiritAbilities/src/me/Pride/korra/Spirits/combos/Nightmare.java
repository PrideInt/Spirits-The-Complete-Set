package me.Pride.korra.Spirits.combos;

import java.util.ArrayList;
import java.util.Random;

import com.projectkorra.projectkorra.command.Commands;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.ability.api.DarkAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class Nightmare extends DarkAbility implements AddonAbility, ComboAbility {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Combos.Dark.Nightmare.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	private PotionEffectType[] negativeEffects = new PotionEffectType[] {
			PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION,
			PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING, PotionEffectType.WEAKNESS,
			PotionEffectType.UNLUCK };
	
	private long cooldown;
	private double radius;
	private long effectDuration;
	private int effectPower;
	private int potionDuration;
	private double damage;
	private boolean enabled;
	
	private int count;
	private double ticks;
	private double size;
	private int rotation;
	private long time;
	private boolean charged;
	
	Random rand = new Random();

	public Nightmare(Player player) {
		super(player);

		if (!bPlayer.canBendIgnoreBinds(this)) {
			return;
		}
		
		cooldown = config.getLong(path + "Cooldown");
		radius = config.getDouble(path + "Radius");
		effectDuration = config.getInt(path + "EffectDuration");
		effectPower = config.getInt(path + "EffectAmplifier");
		damage = config.getDouble(path + "Damage");
		
		potionDuration = Math.toIntExact((effectDuration / 50));
		
		time = System.currentTimeMillis();
		
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 2F, 0.5F);
		
		enabled = ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Combos.Dark.Nightmare.Disabled");
		
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), radius)) {
			if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {
				if (GeneralMethods.isRegionProtectedFromBuild(this, entity.getLocation()) || ((entity instanceof Player) && Commands.invincible.contains(((Player) entity).getName()))) {
					continue;
				}
				DamageHandler.damageEntity(entity, damage, this);
				
				entities.add(entity);
			}
		}
		
		bPlayer.addCooldown(this);
		
		start();
	}
	
	@Override
	public boolean isHiddenAbility() {
		return enabled;
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
		return "Nightmare";
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
		if (!player.isOnline() || player.isDead()) {
			remove();
			return;
		}
		
		if (System.currentTimeMillis() > time + 0L) {
			charged = true;
		}
		
		if (player.isSneaking()) {
			animateNightmare();
			if (charged) {
				ParticleEffect.SPELL_WITCH.display(player.getLocation().add(0, 1, 0), 5, 0.7F, 0.7F, 0.7F, 0.05F);
			}
		} else {
			nightmare();
			
			for (Entity entity : entities) {
				ParticleEffect.SPELL_WITCH.display(((LivingEntity) entity).getEyeLocation(), 5, 0.2F, 0.2F, 0.2F, 0.1F);
				GeneralMethods.displayColoredParticle("48217A", ((LivingEntity) entity).getEyeLocation(), 3, 0.2F, 0.2F, 0.2F);
			}
			
			if (ticks >= (effectDuration / 1000)) {
				bPlayer.addCooldown(this);
				remove();
				return;
			}
		}
	}
	
	private void nightmare() {
		count++;
		if (count % 2 == 0) {
			for (Entity entity : entities) {
				for (PotionEffectType negEffects : negativeEffects) {
					((LivingEntity) entity).addPotionEffect(new PotionEffect(negEffects, potionDuration, effectPower));
				}
			}
		}
		
		ticks += 0.05;
		
		if (ticks >= (effectDuration / 1000)) {
			remove();
			return;
		}
	}
	
	private void animateNightmare() {
		rotation++;
	
		size += 0.1;
		
		if (size >= radius) {
			return;
		}
		
		for (int i = -180; i < 180; i += 10) {
			Location playerLoc = player.getLocation();
			
	        double angle = i * 3.141592653589793D / 180.0D;
	        double x = size * Math.cos(angle + rotation);
	        double z = size * Math.sin(angle + rotation);
	        
	        Location loc = playerLoc.clone();
	        
	        loc.add(x, 0, z);
	        GeneralMethods.displayColoredParticle("48217A", loc);
    	}
	}

	@Override
	public String getDescription() {
		return SpiritElement.LIGHT_SPIRIT.getColor() + "Dark spirits are able to infect their targets with dark energy and spread "
				+ "their negative energies towards their mind. By doing so, they can cause absolute chaos towards the strengths of "
				+ "all creatures with every worst status effect.";
	}
	
	@Override
	public String getInstructions() {
		return ChatColor.GOLD + "Intoxicate (Hold sneak) > Onslaught (Release sneak) > Onslaught (Left click) > Shackle (Hold sneak)";
	}

	@Override
	public String getAuthor() {
		return SpiritElement.LIGHT_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"Prride";
	}

	@Override
	public String getVersion() {
		return SpiritElement.LIGHT_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"VERSION 3";
	}

	@Override
	public void load() {
		ConfigManager.getConfig().addDefault(path + "Disabled", false);
		ConfigManager.getConfig().addDefault(path + "Cooldown", 16000);
		ConfigManager.getConfig().addDefault(path + "Radius", 5);
		ConfigManager.getConfig().addDefault(path + "Damage", 3);
		ConfigManager.getConfig().addDefault(path + "EffectAmplifier", 4);
		ConfigManager.getConfig().addDefault(path + "EffectDuration", 8000);
		ConfigManager.defaultConfig.save();
	}

	@Override
	public void stop() {
		ProjectKorra.log.info(getName() + " by " + getAuthor() + " " + getVersion() + " stopped!");
		super.remove();
	}

	@Override
	public Object createNewComboInstance(Player player) {
		return new Nightmare(player);
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		ArrayList<AbilityInformation> combo = new ArrayList<>();
		combo.add(new AbilityInformation("Intoxicate", ClickType.SHIFT_DOWN));
		combo.add(new AbilityInformation("Onslaught", ClickType.SHIFT_UP));
		combo.add(new AbilityInformation("Onslaught", ClickType.LEFT_CLICK));
		combo.add(new AbilityInformation("Shackle", ClickType.SHIFT_DOWN));
		return combo;
	}

}
