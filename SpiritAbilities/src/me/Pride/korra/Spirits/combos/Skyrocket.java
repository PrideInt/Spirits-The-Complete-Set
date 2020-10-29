package me.Pride.korra.Spirits.combos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.projectkorra.projectkorra.command.Commands;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.projectkorra.util.TempBlock;

import me.numin.spirits.ability.api.SpiritAbility;
import net.md_5.bungee.api.ChatColor;

public class Skyrocket extends SpiritAbility implements AddonAbility, ComboAbility {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Combos.Neutral.Skyrocket.";
	FileConfiguration config = ConfigManager.getConfig();
	
	public static Set<Entity> fallingBlocks = new HashSet<>();
	
	private long cooldown;
	private long regenTime;
	private double speed;
	private double damage;
	private double radius;
	private boolean enabled;
	
	private TempBlock temp;
	private Vector direction;
	private Vector dir;
	Random rand = new Random();
	private FallingBlock fb;

	public Skyrocket(Player player) {
		super(player);

		if (!bPlayer.canBendIgnoreBinds(this)) {
			return;
		}
		
		if (GeneralMethods.isRegionProtectedFromBuild(player, "Skyrocket", player.getLocation())) {
			return;
		}
		
		cooldown = config.getLong(path + "Cooldown");
		regenTime = config.getLong(path + "RegenTime");
		speed = config.getDouble(path + "Speed");
		damage = config.getDouble(path + "Damage");
		radius = config.getDouble(path + "Radius");
		enabled = config.getBoolean(path + "Disabled");
		
		dir = player.getEyeLocation().getDirection();
		
		bPlayer.addCooldown(this);
		
		ParticleEffect.CRIT_MAGIC.display(player.getLocation(), 8, 0.3F, 0.3F, 0.3F, 1F);
		
		player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 0.2F, 2F);
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.5F, 1F);
		
		skyRocket();
		
		ParticleEffect.EXPLOSION_LARGE.display(player.getLocation(), 3, 0.2F, 0.2F, 0.2F);

		fallingBlocks.removeIf(e -> (e.isDead() || !e.isValid()));
		
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
		return "Skyrocket";
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
		if (player.isOnGround()) {
			Block landing = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 1.5F, 0.1F);
			slamGround(landing);
			remove();
			return;
		}
		ParticleEffect.CRIT.display(player.getLocation().add(0, 1, 0), 2, 0.2F, 0.2F, 0.2F, 0.1F);
	}
	
	private void skyRocket() {
		player.setVelocity(dir.multiply(speed));
	}

	private void slamGround(Block landing) {
		for (Block block : GeneralMethods.getBlocksAroundPoint(player.getLocation(), radius)) {
			if (GeneralMethods.isRegionProtectedFromBuild(player, "Skyrocket", block.getLocation())) {
				continue;
			}
			if(block.equals(landing)){
				continue;
			}
			if(block.getType() == Material.BEDROCK){
				continue;
			}

			direction = player.getEyeLocation().getDirection();
			double x = rand.nextDouble() * 3;
			double z = rand.nextDouble() * 3;
			double y = rand.nextDouble() * 3;

			x = (rand.nextBoolean()) ? x : -x;
			z = (rand.nextBoolean()) ? z : -z;
			y = (rand.nextBoolean()) ? y : -y;

			fb = player.getWorld().spawnFallingBlock(block.getLocation(), block.getBlockData());

			fb.setVelocity(direction.clone().add(new Vector(x, y, z)).normalize().multiply(0.9));
			fb.canHurtEntities();
			fb.setDropItem(false);

			fallingBlocks.add(fb);
			
			temp = new TempBlock(block, Material.AIR);
			temp.setRevertTime(regenTime);
			ParticleEffect.BLOCK_CRACK.display(temp.getLocation(), 3, 0.1F, 0.1F, 0.1F, 0F, block.getBlockData());
		
		}
		
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), radius)) {
			if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {
				if (GeneralMethods.isRegionProtectedFromBuild(this, entity.getLocation()) || ((entity instanceof Player) && Commands.invincible.contains(((Player) entity).getName()))) {
					continue;
				}
				DamageHandler.damageEntity(entity, damage, this);
			}
		}
	}

	@Override
	public String getDescription() {
		return ChatColor.AQUA 
				+ "Spirits are able to launch themselves in the air with such high speeds and acceleration that they are able to "
				+ "aggressively slam themselves on the ground to cause ruptures.";
	}
	
	@Override
	public String getInstructions() {
		return ChatColor.GOLD + "Agility (Tap sneak) > Agility (Tap sneak) > Agility (Left click)";
	}

	@Override
	public String getAuthor() {
		return ChatColor.AQUA + "" + ChatColor.UNDERLINE + 
				"Prride";
	}

	@Override
	public String getVersion() {
		return ChatColor.AQUA + "" + ChatColor.UNDERLINE + 
				"VERSION 3";
	}

	@Override
	public void load() {
		ConfigManager.getConfig().addDefault(path + "Disabled", false);
		ConfigManager.getConfig().addDefault(path + "Cooldown", 12000);
		ConfigManager.getConfig().addDefault(path + "RegenTime", 6000);
		ConfigManager.getConfig().addDefault(path + "Speed", 5);
		ConfigManager.getConfig().addDefault(path + "Damage", 3);
		ConfigManager.getConfig().addDefault(path + "Radius", 2.5);
		ConfigManager.defaultConfig.save();
	}

	@Override
	public void stop() {
		ProjectKorra.log.info(getName() + " by " + getAuthor() + " " + getVersion() + " stopped!");
		super.remove();
	}
	
	@Override
	public Object createNewComboInstance(Player player) {
		return new Skyrocket(player);
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		ArrayList<AbilityInformation> combo = new ArrayList<>();
		combo.add(new AbilityInformation("Agility", ClickType.SHIFT_DOWN));
		combo.add(new AbilityInformation("Agility", ClickType.SHIFT_UP));
		combo.add(new AbilityInformation("Agility", ClickType.SHIFT_DOWN));
		combo.add(new AbilityInformation("Agility", ClickType.SHIFT_UP));
		combo.add(new AbilityInformation("Agility", ClickType.LEFT_CLICK));
		return combo;
	}

}
