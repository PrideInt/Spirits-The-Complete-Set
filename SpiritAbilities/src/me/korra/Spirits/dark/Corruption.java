package me.Pride.korra.Spirits.dark;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.projectkorra.util.TempBlock;

import me.Pride.korra.Spirits.listener.AbilListener;
import me.xnuminousx.spirits.ability.api.DarkAbility;
import me.xnuminousx.spirits.elements.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class Corruption extends DarkAbility implements AddonAbility {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Abilities.Dark.Corruption.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<TempBlock> tempBlocks = new ArrayList<TempBlock>();
	
	private long cooldown;
	private double radius;
	private long revertTime;
	private double duration;
	private int effectDuration;
	private int effectPower;
	private long poisonDuration;
	
	private double time;
	
	private Location origin;
	private TempBlock tempBlock;
	public Entity darkSpirit;
	
	Random rand = new Random();
	
	private Material[] plants = new Material[] {
    		Material.GRASS, Material.FERN, Material.TALL_GRASS, Material.LARGE_FERN, Material.DANDELION, Material.POPPY, 
    		Material.OXEYE_DAISY, Material.SUNFLOWER, Material.CACTUS, Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET, 
    		Material.RED_TULIP, Material.ORANGE_TULIP, Material.PINK_TULIP, Material.WHITE_TULIP, Material.LILAC, Material.ROSE_BUSH, Material.PEONY};

	public Corruption(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			return;
		}
		
		cooldown = config.getLong(path + "Cooldown");
		radius = config.getDouble(path + "Radius");
		revertTime = config.getLong(path + "RevertTime");
		duration = config.getDouble(path + "Duration");
		effectPower = config.getInt(path + "EffectAmplifier");
		poisonDuration = config.getLong(path + "EffectDuration");
		
		effectDuration = Math.toIntExact((poisonDuration * 1000) / 50);
		
		origin = player.getLocation();
		
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 0.8F, 0.3F);
		
		start();
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.Spirits.Abilities.Dark.Corruption.Enabled");
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
		return "Corruption";
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
		if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
		
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}
		
		if (player.isSneaking()) {
			startCorrupting();
			
		} else {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
		
	}
	
	private void startCorrupting() {
		time += 0.05;
		
		summonDarkSpirits();
		
		corruptBlocks();
		
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(origin, radius)) {
			if (entity instanceof LivingEntity) {
				
				if (entity instanceof Player) {
	                Player ePlayer = (Player) entity;
	                BendingPlayer bEntity = BendingPlayer.getBendingPlayer(ePlayer);
	                
	                Element darkSpirit = SpiritElement.DARK_SPIRIT;
					Element lightSpirit = SpiritElement.LIGHT_SPIRIT;
					
					if (bEntity.hasElement(darkSpirit) && entity.getUniqueId() != player.getUniqueId()) {
						((LivingEntity) entity).addPotionEffect(new PotionEffect(
								PotionEffectType.DAMAGE_RESISTANCE, effectDuration, effectPower));
						
	                } else if (bEntity.hasElement(lightSpirit) && entity.getUniqueId() != player.getUniqueId()) {
	                	((LivingEntity) entity).addPotionEffect(new PotionEffect(
	                			PotionEffectType.POISON, effectDuration, effectPower));
	                	
	                }
				}
				
				if (entity.getUniqueId() != player.getUniqueId()) {
					((LivingEntity) entity).addPotionEffect(new PotionEffect(
                			PotionEffectType.POISON, effectDuration, effectPower));
				}
			}
		}
		
		if (time >= duration) {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
	}
	
	private void corruptBlocks() {
		Location loc = origin.clone();
		loc.add((rand.nextBoolean() ? 1 : -1) * rand.nextInt((int) radius),
				(rand.nextBoolean() ? 1 : -1) * rand.nextInt((int) radius),
				(rand.nextBoolean() ? 1 : -1) * rand.nextInt((int) radius));
		
		Block block = loc.getBlock().getRelative(BlockFace.UP);
		
		if (block.getType() != Material.AIR) {
			
			ParticleEffect.DRAGON_BREATH.display(origin, 1, radius, 0F, radius, 0.05F);
			
			if (GeneralMethods.isSolid(block)) {
				tempBlock = new TempBlock(block, Material.MYCELIUM);
				tempBlock.setRevertTime(revertTime);
				ParticleEffect.SPELL_WITCH.display(block.getLocation().add(0, 1, 0), 3, 0.2F, 0.2F, 0.2F, 0.2F);
			}
			
			for (Material plants : this.plants) {
				if (block.getType() == plants) {
					tempBlock = new TempBlock(block, Material.DEAD_BUSH);
					tempBlock.setRevertTime(revertTime);
					ParticleEffect.SPELL_WITCH.display(block.getLocation().add(0, 1, 0), 3, 0.2F, 0.2F, 0.2F, 0.2F);
				}
			}
			
			tempBlocks.add(tempBlock);
		}
	}
	
	private void summonDarkSpirits() {
		if (Math.random() < 0.005) {
			Location loc = origin.clone();
			loc.add((rand.nextBoolean() ? 1 : -1) * rand.nextInt((int) radius),
					(rand.nextBoolean() ? 1 : -1) * rand.nextInt((int) radius),
					(rand.nextBoolean() ? 1 : -1) * rand.nextInt((int) radius));
			
			if (loc.getBlock().getType() == Material.AIR) {
				
				darkSpirit = player.getWorld().spawnEntity(loc, EntityType.SPIDER);
				darkSpirit.setCustomName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Dark spirit");
				
				player.getWorld().playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 0.5F);
				
				ParticleEffect.SPELL_WITCH.display(loc, 10, 0.2F, 0.2F, 0.2F, 0.2F);
				
				entities.add(darkSpirit);
			}
		}
	}
	
	@Override
	public void remove() {
		super.remove();
		
		for (Entity entity : entities) {
			ParticleEffect.SPELL_WITCH.display(entity.getLocation().add(0, 1, 0), 5, 0.2F, 0.2F, 0.2F, 0.2F);
			player.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F, 0.5F);
			entity.remove();
		}
	}

	@Override
	public String getDescription() {
		return SpiritElement.LIGHT_SPIRIT.getColor() + "Dark spirits are entities filled with rage and malevolence. "
				+ "They are able to infect and influence the area around them and imbue their negative "
				+ "energies to it. They could also summon more dark spirits within this area in order to spread their corruption. "
				+ "Mobs and land are also affected in this area of influence.";
	}
	
	@Override
	public String getInstructions() {
		return ChatColor.GOLD + "To use, hold sneak.";
	}

	@Override
	public String getAuthor() {
		return SpiritElement.LIGHT_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"Prride";
	}

	@Override
	public String getVersion() {
		return SpiritElement.LIGHT_SPIRIT.getColor() + "" + ChatColor.UNDERLINE + 
				"VERSION 1";
	}

	@Override
	public void load() {
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilListener(), ProjectKorra.plugin);
		
		ConfigManager.getConfig().addDefault(path + "Enabled", true);
		ConfigManager.getConfig().addDefault(path + "Cooldown", 12000);
		ConfigManager.getConfig().addDefault(path + "RevertTime", 18000);
		ConfigManager.getConfig().addDefault(path + "Radius", 6);
		ConfigManager.getConfig().addDefault(path + "Duration", 15);
		ConfigManager.getConfig().addDefault(path + "EffectDuration", 2);
		ConfigManager.getConfig().addDefault(path + "EffectAmplifier", 1);
		ConfigManager.defaultConfig.save();
	}

	@Override
	public void stop() {
		ProjectKorra.log.info(getName() + " by " + getAuthor() + " " + getVersion() + " stopped!");
		super.remove();
	}

}
