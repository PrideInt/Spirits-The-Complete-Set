package me.Pride.korra.Spirits.light;

import me.numin.spirits.ability.api.SpiritAbility;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.Pride.korra.Spirits.listener.AbilListener;
import me.numin.spirits.ability.api.LightAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class LightBeam extends LightBase {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Abilities.Light.LightBeam.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private long cooldown;
	private long duration;
	private double range;
	private double damage;
	
	private long time;
	
	private Location location;
	private Vector direction;

	public LightBeam(Player player) {
		super(player);
		
		cooldown = config.getLong(path + "Cooldown");
		duration = config.getLong(path + "Duration");
		range = config.getDouble(path + "Range");
		damage = config.getDouble(path + "Damage");
		
		time = System.currentTimeMillis();
		
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
		return location;
	}

	@Override
	public String getName() {
		return "LightBeam";
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {
		if (!bPlayer.canBendIgnoreBinds(this)) {
			remove();
			return;
		}
		if (!player.isOnline() || player.isDead()) {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
		if (System.currentTimeMillis() > time + duration) {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
		if (player.isSneaking()) {
			createBeam();
		} else {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
	}
	
	private void createBeam() {
		location = player.getLocation().add(0, 1, 0);
		direction = location.getDirection();
		for (double i = 0; i < range; i += 0.5) {
			location = location.add(direction.multiply(0.5).normalize());

			if (GeneralMethods.isRegionProtectedFromBuild(player, "LightBeam", location)) {
				return;
			}
			
			ParticleEffect.CRIT_MAGIC.display(location, 4, (float) Math.random() / 3, 0.8F, (float) Math.random() / 3, 0.5F);

			for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 0.5)) {
				if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId() && !(entity instanceof ArmorStand)) {
					if (entity instanceof LivingEntity) {
						DamageHandler.damageEntity(entity, damage, this);
						
						if (((LivingEntity) entity).getNoDamageTicks() > 6) {
							((LivingEntity) entity).setNoDamageTicks(6);
						}
					}
				}
			}
			if (GeneralMethods.isSolid(location.getBlock())) {
				return;
			}
		}
	}

	@Override
	public String getAbilityType() {
		return SpiritAbility.OFFENSE;
	}
}
