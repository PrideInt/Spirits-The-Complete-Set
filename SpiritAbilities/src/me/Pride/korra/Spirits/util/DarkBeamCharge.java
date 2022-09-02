package me.Pride.korra.Spirits.util;

import java.util.Random;

import me.Pride.korra.Spirits.dark.DarkBase;
import me.numin.spirits.ability.api.SpiritAbility;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.numin.spirits.ability.api.DarkAbility;
import me.numin.spirits.SpiritElement;
import net.md_5.bungee.api.ChatColor;

public class DarkBeamCharge extends DarkBase {
	
	private static String path = "ExtraAbilities.Prride.Spirits.Abilities.Dark.DarkBeam.";
	FileConfiguration config = ConfigManager.getConfig();
	
	private long chargeTime;
	private long cooldown;
	
	private long time;
	public boolean charged;
	
	Random rand = new Random();

	public DarkBeamCharge(Player player) {
		super(player);
		
		chargeTime = config.getLong(path + "ChargeTime");
		cooldown = config.getLong(path + "Cooldown");
		
		time = System.currentTimeMillis();
		
		start();
	}
	
	@Override
	public boolean isHiddenAbility() {
		return true;
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
		return "DarkBeamCharge";
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
		if (!bPlayer.canBendIgnoreBinds(this)) {
			remove();
			return;
		}
		if (!player.isOnline() || player.isDead()) {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
		if (player.isSneaking()) {
			if (System.currentTimeMillis() > time + chargeTime) {
				charged = true;
				ParticleEffect.SPELL_WITCH.display(player.getLocation(), 5, 0F, 0.2F, 0F, 0.5F);
			} else {
				ParticleEffect.PORTAL.display(player.getLocation().add(0, 1, 0), 5, 0.3F, 0.3F, 0.3F, 0.1F);
			}
		} else {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
	}

	@Override
	public String getAbilityType() {
		return SpiritAbility.OFFENSE;
	}
}
